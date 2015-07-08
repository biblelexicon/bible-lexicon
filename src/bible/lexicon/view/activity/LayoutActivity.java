package bible.lexicon.view.activity;

import bible.lexicon.Application;
import bible.lexicon.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;



public class LayoutActivity extends Activity
{
	static private LayoutActivity		instance;
	
	private Application		application;
	private View			content;
	private ActionBar		bar;
	
	/**
	 * getInstance()
	 * 
	 * Get instance of LayoutActivity
	 * 
	 * @return LayoutActivity
	 */
	static public LayoutActivity getInstance()
	{		
		return LayoutActivity.instance;		
	}	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        LayoutActivity.instance = this;        
        this.application = Application.getInstance();
        int layout = R.layout.layout;        
        
        this.content = this.application.getInflater().inflate(layout, null);
        this.setContentView(this.content);
        
        this.bar = new ActionBar(this);
    }
	
	/**
	 * getContent()
	 * 
	 * Get activity view
	 * 
	 * @return View
	 */
	public View getContent()
	{
		return this.content;
	}
}
