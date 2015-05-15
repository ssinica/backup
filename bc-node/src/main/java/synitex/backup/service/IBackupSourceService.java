package synitex.backup.service;

import synitex.backup.model.BackupSource;

import java.util.List;

public interface IBackupSourceService {

    List<BackupSource> list();

    BackupSource find(String name);

}
