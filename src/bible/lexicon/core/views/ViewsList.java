package bible.lexicon.core.views;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.res.Configuration;

/**
 * ViewsList
 * 
 * Contains all of application's views
 * 
 * @author Michal Schejbal
 * @year 2015
 *
 */
public class ViewsList
{
	static private ViewsList	instance;
	private ArrayList<Item> 	items;
	
	public class Item
	{
		public View 	view = null;
		public boolean	selected = false;
	}
	
	static public ViewsList getInstance()
	{
		if(ViewsList.instance==null) {
			ViewsList.instance = new ViewsList();
		}
		return ViewsList.instance;
	}
	
	public ViewsList()
	{
		this.items = new ArrayList<Item>();
	}
	
	
	/**
	 * add()
	 * 
	 * Add new view
	 * 
	 * @param view
	 * @return View
	 */
	public View add(View view)
	{
		Item item = new Item();
		
		item.view = view;
		
		items.add(item);
		item.view.onCreated();
		
		return item.view;
	}
	
	/**
	 * change()
	 * 
	 * Called when app configuration has changed
	 * 
	 * @param config
	 */
	public void change(Configuration config)
	{
		 Iterator<Item> i = this.items.iterator();
		 while(i.hasNext()) {
			 Item item = i.next();
			 item.view.onChange(config);
		 }		
	}
	
	/**
	 * close()
	 * 
	 * Close view
	 * 
	 * @param view
	 */
	public void close(View view)
	{
		Item item = this.get(view);
		item.view.onClose();
		this.items.remove(item);
	}
	
	
	/**
	 * hide()
	 * 
	 * Hide view
	 * 
	 * @param view
	 */
	public void hide(View view)
	{
		Item item = this.get(view);
		item.view.onHide();		
	}
	
	/**
	 * show()
	 * 
	 * Show view
	 * 
	 * @param view
	 */
	public void show(View view)
	{
		Item item = this.get(view);
		item.view.onSelected();			
	}
	
	/**
	 * get()
	 * 
	 * Get view item
	 * 
	 * @param view
	 * @return
	 */
	public Item get(View view)
	{
		 Iterator<Item> i = this.items.iterator();
		 while(i.hasNext()) {
			 Item item = i.next();
			 if(item.view == view) {
				 return item;
			 }
		 }
		 
		 return null;
	}
	
}
