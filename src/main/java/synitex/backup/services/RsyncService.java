package synitex.backup.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;
import synitex.backup.props.RsyncProperties;

import javax.annotation.PostConstruct;
import java.util.concurrent.Future;

@Service
public class RsyncService implements IRsyncService {

    private static final Logger log = LoggerFactory.getLogger(RsyncService.class);

    private final RsyncProperties rsyncProperties;

    @Autowired
    public RsyncService(RsyncProperties rsyncProperties) {
        this.rsyncProperties = rsyncProperties;
    }

    @PostConstruct
    public void init() {
        try {
            rsync();
        } catch (Exception e) {
            log.error("Rsync test failed", e);
        }
    }

    // rsync -v --stats -zrltD
    // --partial --partial-dir=/data/backup/partial
    // --chmod=ug=r,Dug+x,o-xwr
    // --timeout=300
    // --log-file=c:/tmp/rsync.log
    // /cygdrive/c/my/tmp
    // -e 'ssh -i c:/users/PC/.ssh/pi.sinica.me__bc -o StrictHostKeyChecking=false' bc@pi:/data/backup/ssinica-noute

    // rsync -v --stats -zrltD --chmod=ug=r,Dug+x,o-xwr /cygdrive/c/tmp /cygdrive/d/rsync

    private void rsync() throws Exception {
        Future<ProcessResult> future = new ProcessExecutor()
                .command("rsync", "-v", "--stats", "-zrltD", "--chmod=ug=r,Dug+x,o-xwr", "/cygdrive/c/tmp", "/cygdrive/d/rsync")
                .redirectError(Slf4jStream.of(getClass()).asError())
                .readOutput(true)
                .start()
                .getFuture();

        ProcessResult res = future.get();
        String out = res.outputUTF8();
        int exitCode = res.getExitValue();

        log.info("Exit code: {}", exitCode);
        log.info("output: {}", out);
    }

}
