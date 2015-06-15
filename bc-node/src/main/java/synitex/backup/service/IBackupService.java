package synitex.backup.service;

import synitex.backup.model.BackupResult;
import synitex.backup.model.BackupSource;
import synitex.backup.model.BackupTask;
import synitex.backup.model.Destination;

public interface IBackupService {

    BackupResult backup(BackupSource item, Destination destination, BackupTask task);

}
