package bible.lexicon.core.debug;

import bible.lexicon.config.Environment;
import android.util.Log;




/**
 * Debug
 * 
 * Debug interface class
 * 
 * @author ResKoky
 * @year 2015
 *
 */
public class Debug
{
	static public void log(String title, String message)
	{
		Log.d(title, message + getLocation());
	}
	
	
	
	static public void exception(Exception e, Object o)
	{
		exception(e, o.getClass().toString(), null, false);
	}
	
	static public void exception(Exception e, Object o, boolean display)
	{
		exception(e, o.getClass().toString(), null, display);
	}	
	
	static public void exception(Exception e, Object o, String message)
	{
		exception(e, o.getClass().toString(), message, false);
	}
	
	static public void exception(Exception e, Object o, String message, boolean display)
	{
		exception(e, o.getClass().toString(), message, display);
	}
     
	static public void exception(Exception e, String o, String message, boolean display)
	{
		if(message==null) {
			message = e.getMessage();
		}
		
		log(o, message);
		
		if(display || Environment.isDebug) {
			// @todo popup exception info
		}
	}  
	




	/**
	 * getLocation()
	 * 
	 * @return String
	 */
	static public String getLocation()
	{
		return getClassName()+"."+getMethod()+":"+getLine();
	}
	
	
	static public String getFile()
	{
		return new Throwable().getStackTrace()[1].getFileName();
	}
	
	static public String getClassName()
	{
		return new Throwable().getStackTrace()[1].getClassName();
	}	
	
	static public String getMethod()
	{
		return new Throwable().getStackTrace()[1].getMethodName();
	}
	
	static public int getLine()
	{
		return new Throwable().getStackTrace()[1].getLineNumber();
	}	
}
