package synitex.backup.service;

import synitex.backup.model.Destination;

import java.util.List;

public interface IDestinationService {

    Destination find(String id);

    List<Destination> list();

}
