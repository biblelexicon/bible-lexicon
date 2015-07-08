/**
 * 
 */
package bible.lexicon.core.modules.drivers;

import bible.lexicon.core.database.file.DatabaseFile;
import bible.lexicon.core.utils.File;

/**
 * DriverSqlite
 * 
 * Driver for operating SQLite data files
 * 
 * @author ResKoky
 * @year 2015
 *
 */
public class SqliteDriver extends Driver
{
	private DatabaseFile 		db;

	/**
	 * DriverSqlite()
	 * 
	 * @param file
	 */
	public SqliteDriver(File file)
	{
		super(file);
		
		this.db = new DatabaseFile(file);
	}

	/**
	 * open
	 * 
	 * @param file
	 * @see bible.lexicon.core.modules.drivers.Driver#open(bible.lexicon.core.utils.File)
	 */
	@Override
	protected void open(File file)
	{
		
	}

	/**
	 * close
	 * 
	 * @see bible.lexicon.core.modules.drivers.Driver#close()
	 */
	@Override
	public void close()
	{

	}

}
