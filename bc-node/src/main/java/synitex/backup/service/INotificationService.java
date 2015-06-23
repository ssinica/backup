package synitex.backup.service;

import synitex.backup.event.BackupFinishedEvent;

public interface INotificationService {

    void notify(BackupFinishedEvent event);

}
