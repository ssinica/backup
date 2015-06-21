package synitex.backup.service;

import synitex.backup.db.tables.records.BackupHistoryRecord;

import java.util.List;

public interface IBackupHistoryService {

    List<BackupHistoryRecord> findBySourceForLastWeek(String sourceId, String destinationId);

    BackupHistoryRecord findLast(String sourceId, String destinationId);
}
