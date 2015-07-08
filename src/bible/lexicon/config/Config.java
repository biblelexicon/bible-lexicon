/**
 * 
 */
package bible.lexicon.config;

import bible.lexicon.Application;
import bible.lexicon.core.utils.File;

/**
 * Config
 * 
 * @author Michal Schejbal
 * @year 2015
 *
 */
public class Config
{
	static private Config	instance;

	/**
	 * getInstance()
	 * 
	 * Get instance of Config
	 * 
	 * @return Config
	 */
	static public Config getInstance()
	{		
		if(Config.instance==null) {
			Config.instance = new Config();
		}
		
		return Config.instance;		
	}	
	
	public Config()
	{
		
	}
	
	
	public File getBaseDirectory()
	{
		return null;
	}
}
