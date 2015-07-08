package bible.lexicon;

import bible.lexicon.config.Config;
import bible.lexicon.config.Environment;
import bible.lexicon.core.views.View;
import bible.lexicon.core.views.ViewsList;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;



/**
 * Application
 * 
 * Application singleton
 * 
 * @author ResKoky
 * @year 2015
 *
 */
public class Application extends android.app.Application
{
	static private Application		instance;
	
	private Config 					config;	
	private ViewsList				views;
	
	
	
	/**
	 * getInstance()
	 * 
	 * Get instance of Application
	 * 
	 * @return Application
	 */
	static public Application getInstance()
	{		
		return Application.instance;		
	}
	

 
	@Override
	public void onCreate()
	{
		super.onCreate();
		Application.instance = this;
		
		// Load config
		this.config = Config.getInstance();
		
		// Load views list
		this.views = ViewsList.getInstance();
	}
 
	@Override
	public void onLowMemory()
	{
		super.onLowMemory();
	}
	
	@Override
	public void onConfigurationChanged(Configuration config)
	{
		super.onConfigurationChanged(config);
		this.views.change(config);
	}	
 
	@Override
	public void onTerminate()
	{
		super.onTerminate();
	}
	
	/**
	 * getInflater()
	 * 
	 * @return LayoutInflater
	 */
	public LayoutInflater getInflater()
	{
		return (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/**
	 * getConfig()
	 * 
	 * Get application config
	 * 
	 * @return Config
	 */
	public Config getConfig()
	{
		return this.config;
	}

}
