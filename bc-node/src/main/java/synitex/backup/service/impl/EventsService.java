package synitex.backup.service.impl;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.stereotype.Service;
import synitex.backup.service.IEventsService;

import java.util.concurrent.Executors;

@Service
public class EventsService implements IEventsService {

    private final AsyncEventBus eventBus;

    public EventsService() {
        eventBus = new AsyncEventBus(Executors.newFixedThreadPool(3));
    }

    @Override
    public void register(Object listener) {
        eventBus.register(listener);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }



}
