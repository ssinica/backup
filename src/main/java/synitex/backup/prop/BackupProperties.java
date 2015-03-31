package synitex.backup.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "backup")
public class BackupProperties {

    private List<BackupItem> items = new ArrayList<>();

    public List<BackupItem> getItems() {
        return items;
    }

    public void setItems(List<BackupItem> items) {
        this.items = items;
    }

}
