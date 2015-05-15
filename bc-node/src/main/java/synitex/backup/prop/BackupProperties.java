package synitex.backup.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import synitex.backup.model.BackupSource;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "backup")
public class BackupProperties {

    private List<BackupSource> sources = new ArrayList<>();

    public List<BackupSource> getSources() {
        return sources;
    }

    public void setSources(List<BackupSource> sources) {
        this.sources = sources;
    }

}
