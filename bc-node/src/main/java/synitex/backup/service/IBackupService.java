package synitex.backup.service;

import synitex.backup.model.BackupResult;
import synitex.backup.model.BackupSource;

public interface IBackupService {

    BackupResult backup(BackupSource item);

}
