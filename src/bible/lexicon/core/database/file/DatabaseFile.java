package bible.lexicon.core.database.file;


import bible.lexicon.core.database.Database;
import bible.lexicon.core.debug.Debug;
import bible.lexicon.core.utils.File;
import bible.lexicon.core.utils.Versions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;



/**
 * DatabaseFile
 * 
 * Store public data on the shared external storage
 * http://developer.android.com/guide/topics/data/data-storage.html#filesExternal
 * 
 * Caching:
 * - files are by default cached => there is only one physical connection to database file
 * - alter USE_CACHED_FILES to change default behaviour
 * - you can manualy control caching with useCachedFiles constructor parameter
 * 
 * @author Michal Schejbal
 * @year 2015
 *
 */
public class DatabaseFile extends Database
{
	private boolean				USE_CACHED_FILES	= true;
	private File 				file;	
	private boolean				isInitialized;
	
	private DatabaseFileCache	cache;
	

	/**
	 * DatabaseFile()
	 * 
	 * @param file
	 */
	public DatabaseFile(File file)
	{
		this.ini(file, false, false, USE_CACHED_FILES);
	}
	
	public DatabaseFile(File file, boolean create)
	{
		this.ini(file, create, false, USE_CACHED_FILES);
	}
	
	public DatabaseFile(File file, boolean create, boolean readOnly)
	{
		this.ini(file, create, readOnly, USE_CACHED_FILES);
	}
	
	public DatabaseFile(File file, boolean create, boolean readOnly, boolean useCachedFiles)
	{
		this.ini(file, create, readOnly, useCachedFiles);
	}
	
	
	
	/**
	 * ini()
	 * 
	 * Initialization
	 * 
	 * @param file
	 * @param create
	 * @param readOnly
	 * @param useCachedFiles
	 * @return boolean
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private boolean ini(File file, boolean create, boolean readOnly, boolean useCachedFiles)
	{
		this.file = file;
		this.cache = DatabaseFileCache.getInstance();
		

		
		if(!this.file.exists()) {
			if(create) {
	    		if(!this.file.create()) {
					Debug.log("DBSdCard", "Database file `"+this.file.getAbsolutePath()+"` could not be created.");
					return this.isInitialized = false;
				}
			} else {
				Debug.log("DBSdCard", "Database file `"+this.file.getAbsolutePath()+"` was not found.");				
				return this.isInitialized = false;
			}
		}


		try	{
			
			this.db = SQLiteDatabase.openDatabase(
				this.file.getAbsolutePath(),
				null, 
				(readOnly ? SQLiteDatabase.OPEN_READONLY : SQLiteDatabase.OPEN_READWRITE) |
				SQLiteDatabase.NO_LOCALIZED_COLLATORS	// If this is set, setLocale(Locale) will do nothing				
			);			
		
//			if(Versions.isHoneycombAbove())
//			{
//				this.db = SQLiteDatabase.openDatabase(
//					this.file.getAbsolutePath(),
//					null, 
//					(readOnly ? SQLiteDatabase.OPEN_READONLY : SQLiteDatabase.OPEN_READWRITE) |
//					SQLiteDatabase.NO_LOCALIZED_COLLATORS,	// If this is set, setLocale(Locale) will do nothing.
//					new DatabaseErrorHandler()
//					{
//						@Override
//						public void onCorruption(SQLiteDatabase d)
//						{
//							
//							Debug.exception(
//								this, 
//								new SQLiteDatabaseCorruptException(), 
//								true, 
//								"DatabaseErrorHandler.onCorruption()", 
//								true
//							);
//							
//						}
//					}					
//				);					
//			}
//			else
//			{		
//			}

				

//			this.db.setVersion(3);							
		
			
			this.cache.add(this.file, this.db);
			
			return this.isInitialized = true;
		}
		catch(Exception e)
		{
			Debug.exception(e, this.getClass().toString(), e.getMessage(), true);
			return this.isInitialized = false;
		}
			

	} 


	/**
	 * isInitialized()
	 * 
	 * Is database successfully initialized?
	 * 
	 * @return boolean
	 */
	public boolean isInitialized()
	{
		return this.isInitialized;
	}	


	/**
	 * getFile()
	 * 
	 * Get database file
	 * 
	 * @return File
	 */
	public File getFile()
	{
		return this.file;
	}

	/**
	 * close()
	 * 
	 * Close database
	 * 
	 * @see bible.lexicon.core.database.Database#close()
	 */
	public void close()
	{
		if(!USE_CACHED_FILES) {
			super.close();
		} else {
			if(this.cache.close(this.file)) {
				super.close();				
			}
		}
	}	
}
