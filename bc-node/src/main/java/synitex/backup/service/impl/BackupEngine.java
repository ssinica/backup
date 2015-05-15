package synitex.backup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import synitex.backup.model.BackupSource;
import synitex.backup.prop.BackupProperties;
import synitex.backup.service.IBackupService;
import synitex.backup.service.IBackupSourceService;
import synitex.backup.service.IDestinationService;

import java.util.List;

@Component
public class BackupEngine implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(BackupEngine.class);

    private final IBackupService backupService;
    private final BackupProperties backupProperties;
    private final IDestinationService destinationProvider;
    private final IBackupSourceService backupItemService;

    private ScheduledTaskRegistrar taskRegistrar;

    @Autowired
    public BackupEngine(BackupProperties backupProperties,
                        IBackupService backupService,
                        IDestinationService destinationProvider,
                        IBackupSourceService backupItemService) {
        this.backupProperties = backupProperties;
        this.backupService = backupService;
        this.destinationProvider = destinationProvider;
        this.backupItemService = backupItemService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
        List<BackupSource> items = backupItemService.list();
        log.info("{} backup items are detected.", items.size());
        items.stream().forEach(this::schedulleBackupItem);
    }

    private void schedulleBackupItem(BackupSource item) {
        taskRegistrar.addCronTask(new BackupTask(item, backupService), item.getSchedule());
        log.info("Schedulled {}.", item);
    }

}
