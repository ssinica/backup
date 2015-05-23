/**
 * This class is generated by jOOQ
 */
package synitex.backup.db.tables;


import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import synitex.backup.db.Backup;
import synitex.backup.db.Keys;
import synitex.backup.db.tables.records.BackupHistoryRecord;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


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
public class BackupHistory extends TableImpl<BackupHistoryRecord> {

	private static final long serialVersionUID = 595721648;

	/**
	 * The reference instance of <code>BACKUP.BACKUP_HISTORY</code>
	 */
	public static final BackupHistory BACKUP_HISTORY = new BackupHistory();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<BackupHistoryRecord> getRecordType() {
		return BackupHistoryRecord.class;
	}

	/**
	 * The column <code>BACKUP.BACKUP_HISTORY.ID</code>.
	 */
	public final TableField<BackupHistoryRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>BACKUP.BACKUP_HISTORY.DESTINATION_ID</code>.
	 */
	public final TableField<BackupHistoryRecord, String> DESTINATION_ID = createField("DESTINATION_ID", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "");

	/**
	 * The column <code>BACKUP.BACKUP_HISTORY.SOURCE_ID</code>.
	 */
	public final TableField<BackupHistoryRecord, String> SOURCE_ID = createField("SOURCE_ID", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "");

	/**
	 * The column <code>BACKUP.BACKUP_HISTORY.STARTED_AT</code>.
	 */
	public final TableField<BackupHistoryRecord, Long> STARTED_AT = createField("STARTED_AT", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>BACKUP.BACKUP_HISTORY.FINISHED_AT</code>.
	 */
	public final TableField<BackupHistoryRecord, Long> FINISHED_AT = createField("FINISHED_AT", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>BACKUP.BACKUP_HISTORY.EXIT_CODE</code>.
	 */
	public final TableField<BackupHistoryRecord, Integer> EXIT_CODE = createField("EXIT_CODE", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>BACKUP.BACKUP_HISTORY.TRANSFERED_FILES_SIZE</code>.
	 */
	public final TableField<BackupHistoryRecord, Long> TRANSFERED_FILES_SIZE = createField("TRANSFERED_FILES_SIZE", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>BACKUP.BACKUP_HISTORY.FILES_SIZE</code>.
	 */
	public final TableField<BackupHistoryRecord, Long> FILES_SIZE = createField("FILES_SIZE", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>BACKUP.BACKUP_HISTORY.TOTAL_TRANSFERED_SIZE</code>.
	 */
	public final TableField<BackupHistoryRecord, Long> TOTAL_TRANSFERED_SIZE = createField("TOTAL_TRANSFERED_SIZE", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * Create a <code>BACKUP.BACKUP_HISTORY</code> table reference
	 */
	public BackupHistory() {
		this("BACKUP_HISTORY", null);
	}

	/**
	 * Create an aliased <code>BACKUP.BACKUP_HISTORY</code> table reference
	 */
	public BackupHistory(String alias) {
		this(alias, BACKUP_HISTORY);
	}

	private BackupHistory(String alias, Table<BackupHistoryRecord> aliased) {
		this(alias, aliased, null);
	}

	private BackupHistory(String alias, Table<BackupHistoryRecord> aliased, Field<?>[] parameters) {
		super(alias, Backup.BACKUP, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<BackupHistoryRecord, Long> getIdentity() {
		return Keys.IDENTITY_BACKUP_HISTORY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<BackupHistoryRecord> getPrimaryKey() {
		return Keys.SYS_PK_10113;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<BackupHistoryRecord>> getKeys() {
		return Arrays.<UniqueKey<BackupHistoryRecord>>asList(Keys.SYS_PK_10113);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BackupHistory as(String alias) {
		return new BackupHistory(alias, this);
	}

	/**
	 * Rename this table
	 */
	public BackupHistory rename(String name) {
		return new BackupHistory(name, null);
	}
}
