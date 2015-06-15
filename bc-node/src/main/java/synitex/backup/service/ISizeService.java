package synitex.backup.service;

import synitex.backup.model.BackupSource;
import synitex.backup.model.Destination;

public interface ISizeService {

    long size(String path);

    long size(Destination destination);

    long size(BackupSource backupSource);

    long size(BackupSource backupSource, Destination destination);

}
