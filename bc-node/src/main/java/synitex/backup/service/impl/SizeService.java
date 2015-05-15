package synitex.backup.service.impl;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;
import synitex.backup.model.Destination;
import synitex.backup.model.DestinationType;
import synitex.backup.model.BackupSource;
import synitex.backup.model.SizeTimed;
import synitex.backup.model.SizeMeasure;
import synitex.backup.prop.AppProperties;
import synitex.backup.service.ISizeService;
import synitex.backup.util.CmdUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class SizeService implements ISizeService {

    private static final String DU_START = "du -bd 0";

    private static final Logger log = LoggerFactory.getLogger(SizeService.class);

    private final AppProperties appProperties;

    @Autowired
    public SizeService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public SizeTimed size(String path) {
        return duBasedSize(String.format("%s %s", DU_START, path));
    }

    @Override
    public SizeTimed size(Destination destination) {
        DestinationType destinationType = destination.getType();
        switch (destinationType) {
            case LOCAL: return size(String.format("%s/%s", destination.getDir(), appProperties.getId()));
            case SSH: return duBasedSize(String.format("ssh -i %s -o StrictHostKeyChecking=false %s@%s %s %s/%s",
                    destination.getKey(),
                    destination.getUser(),
                    destination.getHost(),
                    DU_START,
                    destination.getDir(),
                    appProperties.getId()));
            default: throw new UnsupportedOperationException(String.format("Destination type %s is not supported.", destinationType.name()));
        }
    }

    @Override
    public SizeTimed size(BackupSource backupSource) {
        return size(backupSource.getPath());
    }

    @Override
    public SizeTimed size(BackupSource backupSource, Destination destination) {
        List<Path> sourcePaths = Lists.newArrayList(Paths.get(URI.create(backupSource.getPath())).iterator());
        String sourceDir = sourcePaths.get(sourcePaths.size() - 1).toString();
        return duBasedSize(String.format("ssh -i %s -o StrictHostKeyChecking=false %s@%s %s %s/%s/%s",
                destination.getKey(),
                destination.getUser(),
                destination.getHost(),
                DU_START,
                destination.getDir(),
                appProperties.getId(),
                sourceDir));
    }

    private SizeTimed duBasedSize(String command) {
        try {

            List<String> cmd = CmdUtils.parse(command);

            Future<ProcessResult> future = new ProcessExecutor()
                    .command(cmd)
                    .redirectError(Slf4jStream.of(getClass()).asError())
                    .readOutput(true)
                    .start()
                    .getFuture();

            ProcessResult res = future.get();
            String out = res.outputUTF8();
            int exitCode = res.getExitValue();

            if(exitCode != 0 || "".equals(out)) {
                log.error(String.format("Failed to calculate size with command: %s", command));
                return null;
            }

            if(log.isDebugEnabled()) {
                log.debug(String.format("parse '%s' finished with exit code '%s' and output '%s'",
                        command,
                        exitCode,
                        out));
            }

            String sizeAsString = new StringTokenizer(out, "\t").nextToken();
            Long sizeInBytes = Long.valueOf(sizeAsString);
            return new SizeTimed(sizeInBytes, SizeMeasure.BYTES);

        } catch (IOException | InterruptedException | ExecutionException | NumberFormatException e) {
            log.error(String.format("Failed to calculate size with command: %s", command), e);
            return null;
        }
    }

}
