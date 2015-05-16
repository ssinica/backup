package synitex.backup.service.impl;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synitex.backup.event.BackupFinishedEvent;
import synitex.backup.event.BackupStartedEvent;
import synitex.backup.service.IBackupHistoryService;
import synitex.backup.service.IEventsService;

@Service
public class BackupHistoryService implements IBackupHistoryService {

    private static final Logger log = LoggerFactory.getLogger(BackupHistoryService.class);

    private final IEventsService eventsService;

    @Autowired
    public BackupHistoryService(IEventsService eventsService) {
        this.eventsService = eventsService;
        this.eventsService.register(this);
    }

    @Subscribe
    public void onBackupStarted(BackupStartedEvent event) {
        log.info(String.format("Backup started: source = [%s], destination = [%s]",
                event.getSource(),
                event.getDestination()));
    }

    @Subscribe
    public void onBackupFinished(BackupFinishedEvent event) {
        log.info(String.format("Backup finished: source = [%s], destination = [%s], result = [%s]",
                event.getSource(),
                event.getDestination(),
                event.getResult()));
    }

}
