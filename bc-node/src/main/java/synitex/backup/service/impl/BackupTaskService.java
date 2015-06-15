package synitex.backup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import synitex.backup.model.BackupTask;
import synitex.backup.prop.BackupProperties;
import synitex.backup.service.IBackupTaskService;

import java.util.List;

@Component
public class BackupTaskService implements IBackupTaskService {

    private final BackupProperties backupProperties;

    @Autowired
    public BackupTaskService(BackupProperties backupProperties) {
        this.backupProperties = backupProperties;
    }

    @Override
    public List<BackupTask> list() {
        return backupProperties.getTasks();
    }

}
