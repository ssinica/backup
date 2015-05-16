package synitex.backup.event;

import synitex.backup.model.BackupSource;
import synitex.backup.model.Destination;

public class BackupStartedEvent {

    private BackupSource source;
    private Destination destination;
    private long startedAt;

    public BackupStartedEvent(BackupSource source, Destination destination, long startedAt) {
        this.source = source;
        this.destination = destination;
        this.startedAt = startedAt;
    }

    public BackupSource getSource() {
        return source;
    }

    public Destination getDestination() {
        return destination;
    }

    public long getStartedAt() {
        return startedAt;
    }
}
