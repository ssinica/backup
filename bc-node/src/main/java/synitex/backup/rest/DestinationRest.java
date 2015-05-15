package synitex.backup.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synitex.backup.model.Destination;
import synitex.backup.service.IDestinationService;

import java.util.List;

@RestController
@RequestMapping("/destination")
public class DestinationRest {

    private IDestinationService destinationProvider;

    @Autowired
    public DestinationRest(IDestinationService destinationProvider) {
        this.destinationProvider = destinationProvider;
    }

    @RequestMapping("/list")
    public List<Destination> list() {
        return destinationProvider.list();
    }

}
