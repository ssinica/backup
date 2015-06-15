package synitex.backup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import synitex.backup.model.BackupSource;
import synitex.backup.model.BackupTask;
import synitex.backup.model.Destination;
import synitex.backup.model.SizeTimed;
import synitex.backup.service.IBackupSourceService;
import synitex.backup.service.IBackupTaskService;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.IEventsService;
import synitex.backup.service.ISizeHistoryService;
import synitex.backup.service.ISizeService;

import java.util.concurrent.TimeUnit;

@Component
public class SizeScheduller implements SchedulingConfigurer {

    private final IDestinationService destinationService;
    private final IBackupSourceService backupSourceService;
    private final ISizeService sizeService;
    private final IEventsService eventsService;
    private final ISizeHistoryService sizeHistoryService;
    private final IBackupTaskService taskService;

    @Autowired
    public SizeScheduller(IDestinationService destinationService,
                          IBackupSourceService backupSourceService,
                          ISizeService sizeService,
                          IEventsService eventsService,
                          ISizeHistoryService sizeHistoryService,
                          IBackupTaskService taskService) {
        this.destinationService = destinationService;
        this.backupSourceService = backupSourceService;
        this.sizeService = sizeService;
        this.eventsService = eventsService;
        this.sizeHistoryService = sizeHistoryService;
        this.taskService = taskService;
    }

    @Override
    public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
        backupSourceService.list().stream()
                .forEach(backupSource -> scheduleSourceSize(backupSource, taskRegistrar));
        taskService.list().stream()
                .forEach(task -> scheduleTaskSize(task, taskRegistrar));
    }

    private void scheduleTaskSize(BackupTask task, ScheduledTaskRegistrar taskRegistrar) {
        long period = TimeUnit.MINUTES.toMillis(task.getHeartbeat());
        BackupSource source = backupSourceService.find(task.getSource());
        Destination destination = destinationService.find(task.getDestination());
        taskRegistrar.addFixedRateTask(() -> collectSizeOf(source, destination), period);
    }

    private void scheduleSourceSize(BackupSource backupSource, ScheduledTaskRegistrar taskRegistrar) {
        long period = TimeUnit.MINUTES.toMillis(backupSource.getHeartbeat());
        taskRegistrar.addFixedRateTask(() -> collectSizeOf(backupSource), period);
    }

    private void collectSizeOf(BackupSource backupSource) {
        long size = sizeService.size(backupSource);
        String sizeHistoryId = sizeHistoryService.generateId(backupSource);
        sizeHistoryService.save(sizeHistoryId, new SizeTimed(size));
    }

    private void collectSizeOf(BackupSource source, Destination destination) {
        long size = sizeService.size(source, destination);
        String sizeHistoryId = sizeHistoryService.generateId(source, destination);
        sizeHistoryService.save(sizeHistoryId, new SizeTimed(size));
    }

}
