package synitex.backup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import synitex.backup.event.BackupFinishedEvent;
import synitex.backup.event.BackupStartedEvent;
import synitex.backup.model.BackupResult;
import synitex.backup.model.BackupSource;
import synitex.backup.model.BackupTask;
import synitex.backup.model.Destination;
import synitex.backup.service.IBackupService;
import synitex.backup.service.IBackupSourceService;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.IEventsService;

public class BackupJob implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(BackupJob.class);

    private final IBackupService backupService;
    private final IEventsService eventsService;
    private final IDestinationService destinationService;
    private final IBackupSourceService sourceService;
    private final BackupTask task;

    public BackupJob(BackupTask task,
                     IBackupService backupService,
                     IEventsService eventsService,
                     IDestinationService destinationService,
                     IBackupSourceService sourceService) {
        this.task = task;
        this.backupService = backupService;
        this.eventsService = eventsService;
        this.destinationService = destinationService;
        this.sourceService = sourceService;
    }

    @Override
    public void run() {
        Destination destination = destinationService.find(task.getDestination());
        BackupSource source = sourceService.find(task.getSource());


        long startedAt = System.currentTimeMillis();
        eventsService.post(new BackupStartedEvent(source, destination, startedAt));

        BackupResult result = backupService.backup(source, destination, task);

        long finishedAt = System.currentTimeMillis();
        eventsService.post(new BackupFinishedEvent(source, destination, startedAt, finishedAt, result));
    }

}
