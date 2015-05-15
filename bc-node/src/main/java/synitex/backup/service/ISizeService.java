package synitex.backup.service;

import synitex.backup.model.Destination;
import synitex.backup.model.BackupSource;
import synitex.backup.model.SizeTimed;

public interface ISizeService {

    SizeTimed size(String path);

    SizeTimed size(Destination destination);

    SizeTimed size(BackupSource backupSource);

    SizeTimed size(BackupSource backupSource, Destination destination);

}
