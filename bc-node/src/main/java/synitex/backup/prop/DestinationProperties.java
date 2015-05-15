package synitex.backup.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import synitex.backup.model.Destination;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "destination")
public class DestinationProperties {

    private List<Destination> items = new ArrayList<>();

    public List<Destination> getItems() {
        return items;
    }

    public void setItems(List<Destination> items) {
        this.items = items;
    }
}
