package synitex.backup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synitex.backup.model.Destination;
import synitex.backup.prop.DestinationProperties;
import synitex.backup.service.IDestinationService;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService implements IDestinationService {

    private static final Logger log = LoggerFactory.getLogger(DestinationService.class);

    private final DestinationProperties destinationProperties;

    @Autowired
    public DestinationService(DestinationProperties destinationProperties) {
        this.destinationProperties = destinationProperties;
    }

    @Override
    public Destination find(String id) {
        Optional<Destination> item = destinationProperties.getItems()
                .stream()
                .filter(d -> id.equals(d.getId()))
                .findFirst();
        return item.isPresent() ? item.get() : null;
    }

    @Override
    public List<Destination> list() {
        return destinationProperties.getItems();
    }

}
