package synitex.backup.event;

import synitex.backup.model.BackupResult;
import synitex.backup.model.BackupSource;
import synitex.backup.model.Destination;

public class BackupFinishedEvent {

    private BackupSource source;
    private Destination destination;
    private long finishedAt;
    private BackupResult result;

    public BackupFinishedEvent(BackupSource source, Destination destination, long finishedAt, BackupResult result) {
        this.source = source;
        this.destination = destination;
        this.finishedAt = finishedAt;
        this.result = result;
    }

    public BackupSource getSource() {
        return source;
    }

    public Destination getDestination() {
        return destination;
    }

    public long getFinishedAt() {
        return finishedAt;
    }

    public BackupResult getResult() {
        return result;
    }
}
