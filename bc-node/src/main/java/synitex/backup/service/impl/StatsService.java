package synitex.backup.service.impl;

import org.springframework.stereotype.Service;
import synitex.backup.model.BackupSource;
import synitex.backup.model.BackupSourceStats;
import synitex.backup.model.Destination;
import synitex.backup.model.DestinationStats;
import synitex.backup.service.IStatsService;

@Service
public class StatsService implements IStatsService {

    @Override
    public DestinationStats getDestinationStats(Destination destination) {
        return null;
    }

    @Override
    public BackupSourceStats getBackupSourceStats(BackupSource source) {
        return null;
    }

}
