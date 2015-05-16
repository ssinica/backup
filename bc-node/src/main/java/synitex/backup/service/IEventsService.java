package synitex.backup.service;

public interface IEventsService {

    void register(Object listener);

    void post(Object event);

}
