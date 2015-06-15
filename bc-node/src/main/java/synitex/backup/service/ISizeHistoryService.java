package synitex.backup.service;

import synitex.backup.model.BackupSource;
import synitex.backup.model.Destination;
import synitex.backup.model.SizeTimed;

public interface ISizeHistoryService {

    void save(String id, SizeTimed size);

    String generateId(BackupSource source);

    String generateId(BackupSource source, Destination destination);

    SizeTimed getlastSuccessSize(BackupSource source);

    SizeTimed getLastSuccessSize(BackupSource source, Destination destination);

}
