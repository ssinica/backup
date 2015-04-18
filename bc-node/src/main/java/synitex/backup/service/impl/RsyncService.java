package synitex.backup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;
import synitex.backup.prop.AppProperties;
import synitex.backup.prop.BackupDestination;
import synitex.backup.prop.BackupItem;
import synitex.backup.prop.RsyncProperties;
import synitex.backup.service.IBackupDestinationProvider;
import synitex.backup.service.IRsyncService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static synitex.backup.Application.appLog;

@Service
public class RsyncService implements IRsyncService {

    private static final Logger log = LoggerFactory.getLogger(RsyncService.class);

    private final RsyncProperties rsyncProperties;
    private final IBackupDestinationProvider destinationProvider;
    private final AppProperties appProperties;

    @Autowired
    public RsyncService(RsyncProperties rsyncProperties, AppProperties appProperties, IBackupDestinationProvider destinationProvider) {
        this.rsyncProperties = rsyncProperties;
        this.appProperties = appProperties;
        this.destinationProvider = destinationProvider;
    }

    @Override
    public void rsync(BackupItem item) {
        String destinationId = item.getDestination();
        BackupDestination destination = destinationProvider.find(destinationId);

        if(destination == null) {
            appLog().error("Failed to rsync {}, because destination {} is not registered.", item, destinationId);
        } else {
            switch (destination.getType()) {
                case SSH:
                    sshRsync(item, destination);
                    return;
                case LOCAL:
                    localRsync(item, destination);
                    return;
                default:
                    appLog().warn("Backup destination {} is not supported yet!", destination.getType().name());
            }
        }
    }

    private void localRsync(BackupItem item, BackupDestination destination) {
        List<String> command = getCommonRsyncCommand(item.getTimeout());
        command.add(item.getSource());
        command.add(String.format("%s/%s", destination.getDir(), appProperties.getId()));
        rsync(command, item, destination);
    }

    private void sshRsync(BackupItem item, BackupDestination destination) {

        List<String> command = getCommonRsyncCommand(item.getTimeout());

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

        command.add(item.getSource());
        command.add("-e");
        command.add(sshArgs);
        command.add(sshDestination);

        rsync(command, item, destination);
    }

    private List<String> getCommonRsyncCommand(int timeout) {
        return new ArrayList<>(Arrays.asList(
            rsyncProperties.getCmd(),
            "-v",
            "--stats",
            "-zrltD",
            "--chmod=ug=rw,Dug+x,o-xwr",
            String.format("--timeout=%s", timeout)));
    }

    private void rsync(List<String> command, BackupItem item, BackupDestination destination) {
        try {

            log.info("Starting to rsync {}...", item);

            if(log.isDebugEnabled()) {
                log.debug("Rsync command: {}", command.stream().collect(Collectors.joining(" ")));
            }

            Future<ProcessResult> future = new ProcessExecutor()
                    .command(command.toArray(new String[command.size()]))
                    .redirectError(Slf4jStream.of(getClass()).asError())
                    .readOutput(true)
                    .start()
                    .getFuture();

            ProcessResult res = future.get();
            String out = res.outputUTF8();
            int exitCode = res.getExitValue();

            log.info("Rsync for {} finished with code {}. \n Output: {}", item, exitCode, out);

        } catch (IOException|InterruptedException| ExecutionException e) {
            log.error(String.format("Failed to rsync %s.", item), e);
        }
    }

}
