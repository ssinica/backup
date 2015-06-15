package synitex.backup.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import synitex.backup.model.BackupSource;
import synitex.backup.model.BackupTask;
import synitex.backup.model.Destination;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "backup")
public class BackupProperties {

    private List<Destination> destinations = new ArrayList<>();
    private List<BackupSource> sources = new ArrayList<>();
    private List<BackupTask> tasks = new ArrayList<>();

    public List<BackupSource> getSources() {
        return sources;
    }

    public void setSources(List<BackupSource> sources) {
        this.sources = sources;
    }

    public List<BackupTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<BackupTask> tasks) {
        this.tasks = tasks;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }
}
