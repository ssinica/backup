package synitex.backup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synitex.backup.model.BackupSource;
import synitex.backup.prop.BackupProperties;
import synitex.backup.service.IBackupSourceService;

import java.util.List;
import java.util.Optional;

@Service
public class BackupSourceService implements IBackupSourceService {

    private final BackupProperties backupProperties;

    @Autowired
    public BackupSourceService(BackupProperties backupProperties) {
        this.backupProperties = backupProperties;
    }

    @Override
    public List<BackupSource> list() {
        return backupProperties.getSources();
    }

    @Override
    public BackupSource find(String id) {
        Optional<BackupSource> item = backupProperties.getSources().stream()
                .filter(d -> id.equals(d.getId()))
                .findFirst();
        return item.isPresent() ? item.get() : null;
    }

}
