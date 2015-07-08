package bible.lexicon.core.views;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import bible.lexicon.Application;
import bible.lexicon.config.Config;
import bible.lexicon.config.Environment;

abstract public class View
{
	protected Application 			application;
	protected Config				config;
	
	protected LayoutInflater 		inflater;
	protected int					layout;
	protected android.view.View		content;
	
	
	public View(int layout)
	{
		this.layout 		= layout;
		this.application 	= Application.getInstance();
		this.config 		= Config.getInstance();		
		this.inflater 		= this.application.getInflater();		
	}
	
	
	public void onCreated()
	{
		
	}
	
	public void onChange(Configuration config)
	{
		
	}	
	
	public void onHide()
	{
		
	}
	
	public void onShow()
	{
		
	}
	
	public void onSelected()
	{
		this.onShow();
	}
	
	public void onClose()
	{
		
	}
	
	public android.view.View getContent()
	{
		return this.content;	
	}
	
	public Application getApplication()
	{
		return this.application;
	}
}
