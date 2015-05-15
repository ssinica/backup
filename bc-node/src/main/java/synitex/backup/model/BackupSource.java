package synitex.backup.model;

import org.springframework.core.style.ToStringCreator;

public class BackupSource {

    private String name;
    private String path;
    private String destination;
    private String schedule;
    private int timeout = 600;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("name", name)
                .append("path", path)
                .append("destination", destination)
                .append("schedule", schedule)
                .toString();
    }
}
