package synitex.backup.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "destination")
public class DestinationProperties {

    private List<BackupDestination> items = new ArrayList<>();

    public List<BackupDestination> getItems() {
        return items;
    }

    public void setItems(List<BackupDestination> items) {
        this.items = items;
    }
}
