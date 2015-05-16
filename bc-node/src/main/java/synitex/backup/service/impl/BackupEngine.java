package synitex.backup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import synitex.backup.model.BackupSource;
import synitex.backup.service.IBackupService;
import synitex.backup.service.IBackupSourceService;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.IEventsService;

import java.util.List;

@Component
public class BackupEngine implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(BackupEngine.class);

    private final IBackupService backupService;
    private final IDestinationService destinationService;
    private final IBackupSourceService backupItemService;
    private final IEventsService eventsService;

    private ScheduledTaskRegistrar taskRegistrar;

    @Autowired
    public BackupEngine(IBackupService backupService,
                        IDestinationService destinationService,
                        IBackupSourceService backupItemService,
                        IEventsService eventsService) {
        this.backupService = backupService;
        this.destinationService = destinationService;
        this.backupItemService = backupItemService;
        this.eventsService = eventsService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
        List<BackupSource> items = backupItemService.list();
        log.info("{} backup items are detected.", items.size());
        items.stream().forEach(this::schedulleBackupItem);
    }

    private void schedulleBackupItem(BackupSource source) {
        BackupTask task = new BackupTask(source, backupService, eventsService, destinationService);
        taskRegistrar.addCronTask(task, source.getSchedule());
        log.info("Schedulled {}.", source);
    }

}
