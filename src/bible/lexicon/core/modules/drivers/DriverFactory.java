/**
 * 
 */
package bible.lexicon.core.modules.drivers;

import bible.lexicon.core.utils.File;


/**
 * DriverFactory
 * 
 * Create particular driver according to file extension
 * 
 * @author ResKoky
 * @year 2015
 *
 */
public class DriverFactory
{
	static public final int TYPE_UNDEFINED 		= 0;
	static public final int TYPE_DB 			= 1;
	
	

	public Driver create(File file)
	{		
		Driver driver = null;
		switch(DriverFactory.getTypeByExtension(file.getExtension())) {
			case TYPE_DB:
				driver = new SqliteDriver(file);
				break;
			
			case TYPE_UNDEFINED:
				break;
		}
		
		return driver;
	}
	
	
	static public int getTypeByExtension(String extension)
	{
		if(extension.equals("db")) {
			return TYPE_DB;
		} else {
			return TYPE_UNDEFINED;
		}
	}
}
