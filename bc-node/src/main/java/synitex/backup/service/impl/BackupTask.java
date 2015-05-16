package synitex.backup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import synitex.backup.event.BackupFinishedEvent;
import synitex.backup.event.BackupStartedEvent;
import synitex.backup.model.BackupResult;
import synitex.backup.model.BackupSource;
import synitex.backup.model.Destination;
import synitex.backup.service.IBackupService;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.IEventsService;

public class BackupTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(BackupTask.class);

    private final BackupSource source;
    private final IBackupService backupService;
    private final IEventsService eventsService;
    private final IDestinationService destinationService;

    public BackupTask(BackupSource source,
                      IBackupService backupService,
                      IEventsService eventsService,
                      IDestinationService destinationService) {
        this.source = source;
        this.backupService = backupService;
        this.eventsService = eventsService;
        this.destinationService = destinationService;
    }

    @Override
    public void run() {
        Destination destination = destinationService.find(source.getDestination());
        eventsService.post(new BackupStartedEvent(source, destination, System.currentTimeMillis()));

        BackupResult result = backupService.backup(source);

        eventsService.post(new BackupFinishedEvent(source, destination, System.currentTimeMillis(), result));
    }

}
