package synitex.backup.dao.impl;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synitex.backup.dao.ISizeHistoryDao;
import synitex.backup.db.tables.records.SizeHistoryRecord;

import static synitex.backup.db.tables.SizeHistory.SIZE_HISTORY;

@Service
public class JdbcSizeHistoryDao extends AbstractJdbcDao implements ISizeHistoryDao {

    @Autowired
    public JdbcSizeHistoryDao(DSLContext dsl) {
        super(dsl);
    }

    @Override
    @Transactional
    public void save(String id, long size, long time) {
        getDsl().insertInto(SIZE_HISTORY)
                .set(SIZE_HISTORY.SIZE_HISTORY_ID, id)
                .set(SIZE_HISTORY.SIZE, size)
                .set(SIZE_HISTORY.STARTED_AT, time)
                .execute();
    }

    @Override
    @Transactional(readOnly = true)
    public SizeHistoryRecord findLast(String id) {
        Record r = getDsl().select().from(SIZE_HISTORY)
                .where(SIZE_HISTORY.SIZE_HISTORY_ID.eq(id))
                .and(SIZE_HISTORY.SIZE.greaterThan(0L))
                .orderBy(SIZE_HISTORY.STARTED_AT.desc())
                .limit(1)
                .fetchOne();
        return r == null ? null : r.into(SIZE_HISTORY);
    }
}
