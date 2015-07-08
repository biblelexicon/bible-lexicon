package bible.lexicon.core.views;

import java.util.ArrayList;

import bible.lexicon.core.views.tabs.TabView;

/**
 * GridLayout
 * 
 * @author ResKoky
 * @year 2015
 *
 */
public class GridLayout extends View
{
	protected ArrayList<View> items;
	
	
	/**
	 * GridLayout()
	 * 
	 * @param layout
	 */
	public GridLayout(int layout)
	{
		super(layout);

		this.items = new ArrayList<View>();
	}
	
	public View add()
	{
		return null;
	}

}
