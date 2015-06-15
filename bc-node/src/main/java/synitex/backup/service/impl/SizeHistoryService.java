package synitex.backup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import synitex.backup.dao.ISizeHistoryDao;
import synitex.backup.db.tables.records.SizeHistoryRecord;
import synitex.backup.model.BackupSource;
import synitex.backup.model.Destination;
import synitex.backup.model.SizeTimed;
import synitex.backup.service.ISizeHistoryService;

@Component
public class SizeHistoryService implements ISizeHistoryService {

    private final ISizeHistoryDao sizeHistoryDao;

    @Autowired
    public SizeHistoryService(ISizeHistoryDao sizeHistoryDao) {
        this.sizeHistoryDao = sizeHistoryDao;
    }

    @Override
    public void save(String id, SizeTimed size) {
        sizeHistoryDao.save(id, size.getSize(), size.getTime());
    }

    @Override
    public String generateId(BackupSource source) {
        return source.getId();
    }

    @Override
    public String generateId(BackupSource source, Destination destination) {
        return source.getId() + "-" + destination.getId();
    }

    @Override
    public SizeTimed getlastSuccessSize(BackupSource source) {
        String id = generateId(source);
        SizeHistoryRecord rec = sizeHistoryDao.findLast(id);
        return rec == null ? SizeTimed.UNDEFINED : new SizeTimed(rec.getSize(), rec.getStartedAt());
    }

    @Override
    public SizeTimed getLastSuccessSize(BackupSource source, Destination destination) {
        String id = generateId(source, destination);
        SizeHistoryRecord rec = sizeHistoryDao.findLast(id);
        return rec == null ? SizeTimed.UNDEFINED : new SizeTimed(rec.getSize(), rec.getStartedAt());
    }

}
