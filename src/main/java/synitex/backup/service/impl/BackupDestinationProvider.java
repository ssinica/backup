package synitex.backup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synitex.backup.prop.BackupDestination;
import synitex.backup.prop.DestinationProperties;
import synitex.backup.service.IBackupDestinationProvider;

import java.util.Optional;

@Service
public class BackupDestinationProvider implements IBackupDestinationProvider {

    private static final Logger log = LoggerFactory.getLogger(BackupDestinationProvider.class);

    private final DestinationProperties destinationProperties;

    @Autowired
    public BackupDestinationProvider(DestinationProperties destinationProperties) {
        this.destinationProperties = destinationProperties;
    }

    @Override
    public BackupDestination find(String id) {
        Optional<BackupDestination> item = destinationProperties.getItems()
                .stream()
                .filter(d -> id.equals(d.getId()))
                .findFirst();
        return item.isPresent() ? item.get() : null;
    }

}
