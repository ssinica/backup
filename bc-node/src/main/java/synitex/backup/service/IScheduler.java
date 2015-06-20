package synitex.backup.service;

public interface IScheduler {

    void adhocBackupTask(String sourceId, String destinationId);

}
