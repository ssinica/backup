package synitex.backup.model;

import com.google.common.base.MoreObjects;

public class BackupTask {

    private String source;
    private String destination;
    private String schedule;
    private int timeout = 600;
    private int heartbeat = 10;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(int heartbeat) {
        this.heartbeat = heartbeat;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("source", source)
                .add("destination", destination)
                .add("schedule", schedule)
                .toString();
    }
}
