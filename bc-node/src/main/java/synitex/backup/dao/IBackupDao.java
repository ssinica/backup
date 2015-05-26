package synitex.backup.dao;

import synitex.backup.db.tables.records.BackupHistoryRecord;
import synitex.backup.event.BackupFinishedEvent;

import java.util.List;

public interface IBackupDao {

    void saveBackup(BackupFinishedEvent event);

    List<BackupHistoryRecord> list(String sourceId, int offset, int limit);

    int count();

}
