package synitex.backup.dao;

import synitex.backup.db.tables.records.SizeHistoryRecord;

public interface ISizeHistoryDao {

    void save(String id, long size, long time);

    SizeHistoryRecord findLast(String id);

}
