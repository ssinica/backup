package synitex.backup.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import synitex.backup.db.tables.records.BackupHistoryRecord;

public interface IBackupHistoryService {

    Page<BackupHistoryRecord> findBySource(String sourceId, Pageable pageable);

}
