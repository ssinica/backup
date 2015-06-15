package synitex.backup.service.impl;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import synitex.backup.dao.IBackupDao;
import synitex.backup.db.tables.records.BackupHistoryRecord;
import synitex.backup.event.BackupFinishedEvent;
import synitex.backup.event.BackupStartedEvent;
import synitex.backup.service.IBackupHistoryService;
import synitex.backup.service.IEventsService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class BackupHistoryService implements IBackupHistoryService {

    private static final Logger log = LoggerFactory.getLogger(BackupHistoryService.class);

    private final IEventsService eventsService;
    private final IBackupDao backupDao;

    @Autowired
    public BackupHistoryService(IEventsService eventsService, IBackupDao backupDao) {
        this.eventsService = eventsService;
        this.backupDao = backupDao;
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

        backupDao.saveBackup(event);
    }

    @Override
    public Page<BackupHistoryRecord> findBySource(String sourceId, Pageable pageable) {
        List<BackupHistoryRecord> records = backupDao.list(sourceId, pageable.getOffset(), pageable.getPageSize());
        int totalCount = backupDao.count();
        return new PageImpl<>(records, pageable, totalCount);
    }

    @Override
    public List<BackupHistoryRecord> findBySourceForLastWeek(String sourceId, String destinationId) {
        long weekAgo = LocalDateTime.now().minusDays(7).toEpochSecond(ZoneOffset.UTC);
        return backupDao.list(sourceId, destinationId, weekAgo);
    }
}
