package bible.lexicon.config;


/**
 * Environment
 * 
 * Contains constants that can not be altered by user's settings
 * 
 * @author Michal Schejbal
 * @year 2015
 *
 */
public class Environment
{
	static public boolean			isDebug = true;
	

	/**
	 * app
	 * 
	 * Application information
	 * 
	 */
	static public class app
	{
		static public final 	String		identifier 		= "biblelexicon";
		static public final 	String		version 		= "5.0.0";
		static public			String		name 			= "Bible lexicon";	
		static public final 	String		email			= "andbiblelexicon@gmail.com";
		static public final 	String		web				= "http://biblelexicon.net";
		static public final 	String		developers		= "Michal Schejbal";	
		static public final 	String		year			= "2015";							// developed since 2013		
	}

	/**
	 * Default directory on the inner sdcard
	 */
	public static String			directory				= Environment.app.identifier;
}
