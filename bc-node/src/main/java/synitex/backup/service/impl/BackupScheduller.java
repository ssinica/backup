package synitex.backup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import synitex.backup.model.BackupTask;
import synitex.backup.service.IBackupService;
import synitex.backup.service.IBackupSourceService;
import synitex.backup.service.IBackupTaskService;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.IEventsService;

import java.util.List;

@Component
public class BackupScheduller implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(BackupScheduller.class);

    private final IBackupService backupService;
    private final IDestinationService destinationService;
    private final IBackupSourceService backupSourceService;
    private final IEventsService eventsService;
    private final IBackupTaskService taskService;

    private ScheduledTaskRegistrar taskRegistrar;

    @Autowired
    public BackupScheduller(IBackupService backupService,
                            IDestinationService destinationService,
                            IBackupSourceService backupSourceService,
                            IEventsService eventsService,
                            IBackupTaskService taskService) {
        this.backupService = backupService;
        this.destinationService = destinationService;
        this.backupSourceService = backupSourceService;
        this.eventsService = eventsService;
        this.taskService = taskService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
        List<BackupTask> tasks = taskService.list();
        log.info("{} backup tasks are detected.", tasks.size());
        tasks.stream().forEach(this::schedulleBackupItem);
    }

    private void schedulleBackupItem(BackupTask task) {
        BackupJob job = new BackupJob(task, backupService, eventsService, destinationService, backupSourceService);
        taskRegistrar.addCronTask(job, task.getSchedule());
        log.info("Schedulled {}.", task);
    }

}
