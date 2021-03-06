/**
 * This class is generated by jOOQ
 */
package synitex.backup.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import synitex.backup.db.Backup;
import synitex.backup.db.Keys;
import synitex.backup.db.tables.records.SizeHistoryRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SizeHistory extends TableImpl<SizeHistoryRecord> {

	private static final long serialVersionUID = 2081121071;

	/**
	 * The reference instance of <code>BACKUP.SIZE_HISTORY</code>
	 */
	public static final SizeHistory SIZE_HISTORY = new SizeHistory();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<SizeHistoryRecord> getRecordType() {
		return SizeHistoryRecord.class;
	}

	/**
	 * The column <code>BACKUP.SIZE_HISTORY.ID</code>.
	 */
	public final TableField<SizeHistoryRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>BACKUP.SIZE_HISTORY.SIZE_HISTORY_ID</code>.
	 */
	public final TableField<SizeHistoryRecord, String> SIZE_HISTORY_ID = createField("SIZE_HISTORY_ID", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "");

	/**
	 * The column <code>BACKUP.SIZE_HISTORY.STARTED_AT</code>.
	 */
	public final TableField<SizeHistoryRecord, Long> STARTED_AT = createField("STARTED_AT", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>BACKUP.SIZE_HISTORY.SIZE</code>.
	 */
	public final TableField<SizeHistoryRecord, Long> SIZE = createField("SIZE", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * Create a <code>BACKUP.SIZE_HISTORY</code> table reference
	 */
	public SizeHistory() {
		this("SIZE_HISTORY", null);
	}

	/**
	 * Create an aliased <code>BACKUP.SIZE_HISTORY</code> table reference
	 */
	public SizeHistory(String alias) {
		this(alias, SIZE_HISTORY);
	}

	private SizeHistory(String alias, Table<SizeHistoryRecord> aliased) {
		this(alias, aliased, null);
	}

	private SizeHistory(String alias, Table<SizeHistoryRecord> aliased, Field<?>[] parameters) {
		super(alias, Backup.BACKUP, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<SizeHistoryRecord, Long> getIdentity() {
		return Keys.IDENTITY_SIZE_HISTORY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<SizeHistoryRecord> getPrimaryKey() {
		return Keys.SYS_PK_10117;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<SizeHistoryRecord>> getKeys() {
		return Arrays.<UniqueKey<SizeHistoryRecord>>asList(Keys.SYS_PK_10117);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SizeHistory as(String alias) {
		return new SizeHistory(alias, this);
	}

	/**
	 * Rename this table
	 */
	public SizeHistory rename(String name) {
		return new SizeHistory(name, null);
	}
}
