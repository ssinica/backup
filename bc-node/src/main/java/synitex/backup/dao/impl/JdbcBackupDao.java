package synitex.backup.dao.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synitex.backup.dao.IBackupDao;
import synitex.backup.db.tables.records.BackupHistoryRecord;
import synitex.backup.event.BackupFinishedEvent;

import java.util.List;

import static synitex.backup.db.tables.BackupHistory.BACKUP_HISTORY;

/**
 * http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-sql
 * http://www.jooq.org/doc/3.6/manual-single-page/#getting-started
 */
@Service
public class JdbcBackupDao implements IBackupDao {

    private final DSLContext dsl;

    @Autowired
    public JdbcBackupDao(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    @Transactional
    public void saveBackup(BackupFinishedEvent event) {
        dsl.insertInto(BACKUP_HISTORY)
                .set(BACKUP_HISTORY.DESTINATION_ID, event.getDestination().getId())
                .set(BACKUP_HISTORY.SOURCE_ID, event.getSource().getName())
                .set(BACKUP_HISTORY.STARTED_AT, event.getStartedAt())
                .set(BACKUP_HISTORY.FINISHED_AT, event.getFinishedAt())
                .set(BACKUP_HISTORY.EXIT_CODE, event.getResult().getExitCode())
                .set(BACKUP_HISTORY.FILES_SIZE, event.getResult().getFilesSize())
                .set(BACKUP_HISTORY.TRANSFERED_FILES_SIZE, event.getResult().getTransferedFilesSize())
                .set(BACKUP_HISTORY.TOTAL_TRANSFERED_SIZE, event.getResult().getTotalTransferedSize())
                .execute();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BackupHistoryRecord> list(String sourceId, int offset, int limit) {
        return dsl.select()
                .from(BACKUP_HISTORY)
                .where(BACKUP_HISTORY.SOURCE_ID.eq(sourceId))
                .orderBy(BACKUP_HISTORY.STARTED_AT.desc())
                .limit(offset, limit)
                .fetch()
                .into(BACKUP_HISTORY);
    }

    @Override
    @Transactional(readOnly = true)
    public int count() {
        return dsl.fetchCount(BACKUP_HISTORY);
    }

}
