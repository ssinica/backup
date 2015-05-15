package synitex.backup.service;

import synitex.backup.model.BackupSource;
import synitex.backup.model.BackupSourceStats;
import synitex.backup.model.Destination;
import synitex.backup.model.DestinationStats;

public interface IStatsService {

    DestinationStats getDestinationStats(Destination destination);

    BackupSourceStats getBackupSourceStats(BackupSource source);

}
