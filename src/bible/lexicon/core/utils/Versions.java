package bible.lexicon.core.utils;

import android.os.Build;

/**
 * Versions
 * 
 * Android version check class
 * 
 * @author Michal Schejbal
 * @year 2015
 *
 */
public class Versions
{
	public static final int VERSION = android.os.Build.VERSION.SDK_INT;
	
	public static boolean isHoneycombAbove()
	{
		return Build.VERSION_CODES.HONEYCOMB <= VERSION;
	}
	
	public static boolean isLolipop()
	{
		return false;
	}
}
