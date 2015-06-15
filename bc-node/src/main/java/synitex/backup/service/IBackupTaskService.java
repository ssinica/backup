package synitex.backup.service;

import synitex.backup.model.BackupTask;

import java.util.List;

public interface IBackupTaskService {

    List<BackupTask> list();

}
