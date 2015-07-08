/**
 * 
 */
package bible.lexicon.core.database.file;

import java.util.ArrayList;
import java.util.Iterator;

import android.database.sqlite.SQLiteDatabase;
import bible.lexicon.Application;
import bible.lexicon.core.debug.Debug;
import bible.lexicon.core.utils.File;

/**
 * DatabaseFileCache
 * 
 * @author Michal Schejbal
 * @year 2015
 *
 */
public class DatabaseFileCache
{
	static private DatabaseFileCache		instance;	
	private ArrayList<Item> 				items = null;
	
	private class Item
	{
		public SQLiteDatabase	db;
		public File 			file;
		public int 				count;		// Used cound
		
		public Item(File file, SQLiteDatabase db)
		{
			this.db 	= db;
			this.file 	= file;
			this.count 	= 1;
		}
	}
	
	/**
	 * getInstance()
	 * 
	 * Get instance of DatabaseFileCache
	 * 
	 * @return DatabaseFileCache
	 */
	static public DatabaseFileCache getInstance()
	{
		if(DatabaseFileCache.instance==null) {
			DatabaseFileCache.instance = new DatabaseFileCache();
		}
		
		return DatabaseFileCache.instance;		
	}	
	
	public DatabaseFileCache()
	{
		this.items = new ArrayList<Item>();
	}
	
	
	public Item add(File file, SQLiteDatabase database)
	{
		Item item = this.get(file);
		
		if(item!=null) {
			item.count++;
		} else {
			item = new Item(file, database);
		}

		Debug.log("Cache", "`"+item.file.getAbsolutePath()+"` opened, used="+item.count);
		
		return item;
	}
	
	/**
	 * close()
	 * 
	 * Closes file usage
	 * 
	 * @param file
	 * @return boolean if no usage returns true, otherwise false
	 */
	public boolean close(File file)
	{
		Item item = this.get(file);
		
		if(item!=null) {
			item.count--;
		}
		
		Debug.log("Cache", "`"+item.file.getAbsolutePath()+"` closed, used="+item.count);
		
		return item.count==0;
	}
	
	
	/**
	 * getCount()
	 * 
	 * Get number of opened usages
	 * 
	 * @param file
	 * @return int
	 */
	public int getCount(File file)
	{
		Item item = this.get(file);
		
		return item!=null ? item.count : 0;
	}
	
	
	/**
	 * has()
	 * 
	 * Is file already cached?
	 * 
	 * @param file
	 * @return boolean
	 */
	public boolean has(File file)
	{
		return this.get(file)!=null;
	}
	
	/**
	 * get()
	 * 
	 * Get cache item
	 * 
	 * @param file
	 * @return Item
	 */
	public Item get(File file)
	{
		Iterator<Item> i = this.items.iterator();
		while(i.hasNext()) {
			Item item = i.next();
			if(item.file.getAbsolutePath().equals(file.getAbsolutePath())) {
				return item;
			}
		}

		return null;			
	}	
}
