package bible.lexicon.core.utils;

import java.io.FileWriter;
import java.io.IOException;

import bible.lexicon.core.debug.Debug;

/**
 * File
 * 
 * @author Michal Schejbal
 * @year 2015
 *
 */
public class File extends java.io.File
{
	/**
	 * File()
	 * 
	 * @param path
	 */
	public File(String path)
	{
		super(path);
	}

	public String getExtension()
	{
		return this.getPath().substring(this.getPath().indexOf("."), this.getPath().length());
	}

	public boolean create()
	{
    	try {
			FileWriter f = new FileWriter(this.getPath());
			f.close();	
			
			return true;
		} catch(IOException e) {
			Debug.exception(e, this, this.getPath());
			
			return false;
		}		
	}
}
