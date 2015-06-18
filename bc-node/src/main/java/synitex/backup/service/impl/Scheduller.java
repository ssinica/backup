package synitex.backup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import synitex.backup.model.BackupSource;
import synitex.backup.model.BackupTask;
import synitex.backup.model.Destination;
import synitex.backup.model.SizeTimed;
import synitex.backup.service.IBackupService;
import synitex.backup.service.IBackupSourceService;
import synitex.backup.service.IBackupTaskService;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.IEventsService;
import synitex.backup.service.ISizeHistoryService;
import synitex.backup.service.ISizeService;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Scheduller implements SchedulingConfigurer, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(Scheduller.class);

    private final IBackupService backupService;
    private final IEventsService eventsService;
    private final IDestinationService destinationService;
    private final IBackupSourceService backupSourceService;
    private final IBackupTaskService taskService;
    private final ISizeService sizeService;
    private final ISizeHistoryService sizeHistoryService;

    private ScheduledExecutorService threadPool;

    @Autowired
    public Scheduller(IBackupService backupService,
                      IDestinationService destinationService,
                      IBackupSourceService backupSourceService,
                      IEventsService eventsService,
                      IBackupTaskService taskService,
                      ISizeService sizeService,
                      ISizeHistoryService sizeHistoryService) {
        this.backupService = backupService;
        this.destinationService = destinationService;
        this.backupSourceService = backupSourceService;
        this.eventsService = eventsService;
        this.taskService = taskService;
        this.sizeService = sizeService;
        this.sizeHistoryService = sizeHistoryService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        threadPool = Executors.newScheduledThreadPool(10);
        taskRegistrar.setScheduler(threadPool);
        registerBackupTasks(taskRegistrar);
        registerSizeTasks(taskRegistrar);
    }

    @Override
    public void destroy() throws Exception {
        if(threadPool != null) {
            log.info("Trying to shutdown thread pool...");
            threadPool.shutdown();
            log.info("Will wait for thread poll termination for 10 sec...");
            if(!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                log.info("Timeout is out. Will force thread pool shutdown!");
                threadPool.shutdownNow();
            } else {
                log.info("Thread pool is shutdown!");
            }
        }
    }

    private void registerBackupTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<BackupTask> tasks = taskService.list();
        log.info("{} backup tasks are detected.", tasks.size());
        tasks.stream().forEach(task -> schedulleBackupItem(task, taskRegistrar));
    }

    private void registerSizeTasks(ScheduledTaskRegistrar taskRegistrar) {
        backupSourceService.list().stream().forEach(source -> scheduleSourceSize(source, taskRegistrar));
        taskService.list().stream().forEach(task -> scheduleTaskSize(task, taskRegistrar));
    }

    private void schedulleBackupItem(BackupTask task, ScheduledTaskRegistrar taskRegistrar) {
        BackupJob job = new BackupJob(task, backupService, eventsService, destinationService, backupSourceService);
        taskRegistrar.addCronTask(job, task.getSchedule());
        log.info("Schedulled {}.", task);
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
