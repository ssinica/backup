package synitex.backup.service;

import synitex.backup.prop.BackupDestination;

public interface IBackupDestinationProvider {

    BackupDestination find(String id);

}
