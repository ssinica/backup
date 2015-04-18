package synitex.backup.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import synitex.backup.prop.BackupItem;
import synitex.backup.service.IRsyncService;

import java.util.concurrent.TimeUnit;

import static synitex.backup.Application.appLog;

public class BackupTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(BackupTask.class);

    private final BackupItem item;
    private final IRsyncService rsyncService;

    public BackupTask(BackupItem item, IRsyncService rsyncService) {
        this.item = item;
        this.rsyncService = rsyncService;
    }

    @Override
    public void run() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        rsyncService.rsync(item);
        stopWatch.stop();

        long time = stopWatch.getTotalTimeMillis();
        appLog().info("Backup of {} finished in {} {}.", item,
                time < 1000L ? time : TimeUnit.MILLISECONDS.toSeconds(time),
                time < 1000L ? "ms." : "sec.");
    }

}
