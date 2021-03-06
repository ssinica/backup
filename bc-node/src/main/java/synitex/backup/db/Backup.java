/**
 * This class is generated by jOOQ
 */
package synitex.backup.db;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import synitex.backup.db.tables.BackupHistory;
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
public class Backup extends SchemaImpl {

	private static final long serialVersionUID = -2136407868;

	/**
	 * The reference instance of <code>BACKUP</code>
	 */
	public static final Backup BACKUP = new Backup();

	/**
	 * No further instances allowed
	 */
	private Backup() {
		super("BACKUP");
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
			BackupHistory.BACKUP_HISTORY,
			SizeHistory.SIZE_HISTORY);
	}
}
