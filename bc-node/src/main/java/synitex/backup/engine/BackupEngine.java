package synitex.backup.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import synitex.backup.prop.BackupItem;
import synitex.backup.prop.BackupProperties;
import synitex.backup.service.IBackupDestinationProvider;
import synitex.backup.service.IRsyncService;

import java.util.List;

@Component
public class BackupEngine implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(BackupEngine.class);

    private final IRsyncService rsyncService;
    private final BackupProperties backupProperties;
    private final IBackupDestinationProvider destinationProvider;

    private ScheduledTaskRegistrar taskRegistrar;

    @Autowired
    public BackupEngine(BackupProperties backupProperties,
                        IRsyncService rsyncService,
                        IBackupDestinationProvider destinationProvider) {
        this.backupProperties = backupProperties;
        this.rsyncService = rsyncService;
        this.destinationProvider = destinationProvider;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
        List<BackupItem> items = backupProperties.getItems();
        log.info("{} backup items are detected.", items.size());
        items.stream().forEach(this::schedulleBackupItem);
    }

    private void schedulleBackupItem(BackupItem item) {
        taskRegistrar.addCronTask(new BackupTask(item, rsyncService), item.getSchedule());
        log.info("Schedulled {}.", item);
    }

}
