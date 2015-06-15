package synitex.backup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;
import synitex.backup.model.BackupResult;
import synitex.backup.model.BackupSource;
import synitex.backup.model.BackupTask;
import synitex.backup.model.Destination;
import synitex.backup.prop.AppProperties;
import synitex.backup.prop.RsyncProperties;
import synitex.backup.service.IBackupService;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.IRsyncOutputParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class RsyncBackupService implements IBackupService {

    private static final Logger log = LoggerFactory.getLogger(RsyncBackupService.class);

    private final RsyncProperties rsyncProperties;
    private final IDestinationService destinationProvider;
    private final AppProperties appProperties;
    private final IRsyncOutputParser rsyncOutputParser;

    @Autowired
    public RsyncBackupService(RsyncProperties rsyncProperties,
                              AppProperties appProperties,
                              IDestinationService destinationProvider,
                              IRsyncOutputParser rsyncOutputParser) {
        this.rsyncProperties = rsyncProperties;
        this.appProperties = appProperties;
        this.destinationProvider = destinationProvider;
        this.rsyncOutputParser = rsyncOutputParser;
    }

    @Override
    public BackupResult backup(BackupSource item, Destination destination, BackupTask task) {
        switch (destination.getType()) {
            case SSH:
                return sshRsync(item, destination, task);
            case LOCAL:
                return localRsync(item, destination, task);
            default:
                log.warn("Backup destination {} is not supported yet!", destination.getType().name());
                return null;
        }
    }

    private BackupResult localRsync(BackupSource source, Destination destination, BackupTask task) {
        List<String> command = getCommonRsyncCommand(task.getTimeout());
        command.add(source.getPath());
        command.add(String.format("%s/%s", destination.getDir(), appProperties.getId()));
        return rsync(command, source);
    }

    private BackupResult sshRsync(BackupSource source, Destination destination, BackupTask task) {

        List<String> command = getCommonRsyncCommand(task.getTimeout());

        // optional partial dir
        if(!StringUtils.isEmpty(destination.getPartialDir())) {
            command.add("--partial");
            command.add("--partial-dir=" + destination.getPartialDir());
        }

        // ssh destination
        String sshDestination = String.format("%s@%s:%s/%s",
                destination.getUser(),
                destination.getHost(),
                destination.getDir(),
                appProperties.getId());
        String sshArgs = String.format("\"ssh -i %s -o StrictHostKeyChecking=false\"",
                destination.getKey());

        command.add(source.getPath());
        command.add("-e");
        command.add(sshArgs);
        command.add(sshDestination);

        return rsync(command, source);
    }

    private List<String> getCommonRsyncCommand(int timeout) {
        return new ArrayList<>(Arrays.asList(
            rsyncProperties.getCmd(),
            //"-v",
            "--stats",
            "-zrltD",
            "--chmod=ug=rw,Dug+x,o-xwr",
            String.format("--timeout=%s", timeout)));
    }

    private BackupResult rsync(List<String> command, BackupSource source) {
        try {

            Future<ProcessResult> future = new ProcessExecutor()
                    .command(command.toArray(new String[command.size()]))
                    .redirectError(Slf4jStream.of(getClass()).asError())
                    .readOutput(true)
                    .start()
                    .getFuture();

            ProcessResult res = future.get();
            String out = res.outputUTF8();
            int exitCode = res.getExitValue();

            if(log.isDebugEnabled()) {
                log.debug("Rsync for {} finished with code {}. \n Output: {}. Command: {}", source, exitCode, out, command);
            }

            return rsyncOutputParser.parse(exitCode, out);

        } catch (IOException |InterruptedException| ExecutionException e) {
            log.error(String.format("Failed to rsync %s.", source), e);
            return BackupResult.RUNTIME_EXCEPTION;
        }
    }

}
