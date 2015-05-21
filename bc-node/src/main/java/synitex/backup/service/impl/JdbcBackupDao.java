package synitex.backup.service.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synitex.backup.event.BackupFinishedEvent;
import synitex.backup.service.IBackupDao;

import static synitex.backup.db.tables.BackupHistory.BACKUP_HISTORY;

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
                .set(BACKUP_HISTORY.STARTEDAT, event.getStartedAt())
                .set(BACKUP_HISTORY.ENDEDAT, event.getFinishedAt())
                .set(BACKUP_HISTORY.EXITCODE, event.getResult().getExitCode())
                .execute();
    }

}
