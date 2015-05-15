package synitex.backup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import synitex.backup.model.BackupSource;
import synitex.backup.service.IBackupService;

import java.util.concurrent.TimeUnit;

public class BackupTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(BackupTask.class);

    private final BackupSource item;
    private final IBackupService backupService;

    public BackupTask(BackupSource item, IBackupService backupService) {
        this.item = item;
        this.backupService = backupService;
    }

    @Override
    public void run() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        backupService.backup(item);
        stopWatch.stop();

        long time = stopWatch.getTotalTimeMillis();
        log.info("Backup of {} finished in {} {}.", item,
                time < 1000L ? time : TimeUnit.MILLISECONDS.toSeconds(time),
                time < 1000L ? "ms." : "sec.");
    }

}
