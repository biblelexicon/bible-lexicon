package bible.lexicon.core.database;

import bible.lexicon.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DatabaseInternal
 * 
 * Store private data on the device memory
 * http://developer.android.com/guide/topics/data/data-storage.html#filesInternal
 * 
 * @author ResKoky
 * @year 2015
 *
 */
public class DatabaseInternal extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;

	public Database		db;

	public DatabaseInternal()
	{
		super(Application.getInstance(), null, null, DATABASE_VERSION);

//		this.db = new Database(this.getWritableDatabase());
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db)
	{
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}
}
