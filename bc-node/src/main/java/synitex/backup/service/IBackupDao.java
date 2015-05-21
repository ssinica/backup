package synitex.backup.service;

import synitex.backup.event.BackupFinishedEvent;

public interface IBackupDao {

    void saveBackup(BackupFinishedEvent event);

}
