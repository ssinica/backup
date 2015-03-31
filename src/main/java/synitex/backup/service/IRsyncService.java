package synitex.backup.service;

import synitex.backup.prop.BackupItem;

public interface IRsyncService {

    void rsync(BackupItem item);

}
