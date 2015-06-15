/**
 * This class is generated by jOOQ
 */
package synitex.backup.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import synitex.backup.db.tables.SizeHistory;


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
public class SizeHistoryRecord extends UpdatableRecordImpl<SizeHistoryRecord> implements Record4<Long, String, Long, Long> {

	private static final long serialVersionUID = 1577945947;

	/**
	 * Setter for <code>BACKUP.SIZE_HISTORY.ID</code>.
	 */
	public void setId(Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>BACKUP.SIZE_HISTORY.ID</code>.
	 */
	public Long getId() {
		return (Long) getValue(0);
	}

	/**
	 * Setter for <code>BACKUP.SIZE_HISTORY.SIZE_HISTORY_ID</code>.
	 */
	public void setSizeHistoryId(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>BACKUP.SIZE_HISTORY.SIZE_HISTORY_ID</code>.
	 */
	public String getSizeHistoryId() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>BACKUP.SIZE_HISTORY.STARTED_AT</code>.
	 */
	public void setStartedAt(Long value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>BACKUP.SIZE_HISTORY.STARTED_AT</code>.
	 */
	public Long getStartedAt() {
		return (Long) getValue(2);
	}

	/**
	 * Setter for <code>BACKUP.SIZE_HISTORY.SIZE</code>.
	 */
	public void setSize(Long value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>BACKUP.SIZE_HISTORY.SIZE</code>.
	 */
	public Long getSize() {
		return (Long) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Long> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Long, String, Long, Long> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Long, String, Long, Long> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field1() {
		return SizeHistory.SIZE_HISTORY.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return SizeHistory.SIZE_HISTORY.SIZE_HISTORY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field3() {
		return SizeHistory.SIZE_HISTORY.STARTED_AT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field4() {
		return SizeHistory.SIZE_HISTORY.SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getSizeHistoryId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value3() {
		return getStartedAt();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value4() {
		return getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SizeHistoryRecord value1(Long value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SizeHistoryRecord value2(String value) {
		setSizeHistoryId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SizeHistoryRecord value3(Long value) {
		setStartedAt(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SizeHistoryRecord value4(Long value) {
		setSize(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SizeHistoryRecord values(Long value1, String value2, Long value3, Long value4) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached SizeHistoryRecord
	 */
	public SizeHistoryRecord() {
		super(SizeHistory.SIZE_HISTORY);
	}

	/**
	 * Create a detached, initialised SizeHistoryRecord
	 */
	public SizeHistoryRecord(Long id, String sizeHistoryId, Long startedAt, Long size) {
		super(SizeHistory.SIZE_HISTORY);

		setValue(0, id);
		setValue(1, sizeHistoryId);
		setValue(2, startedAt);
		setValue(3, size);
	}
}