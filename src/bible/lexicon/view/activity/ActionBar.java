/**
 * 
 */
package bible.lexicon.view.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import bible.lexicon.R;
import bible.lexicon.core.utils.Versions;

/**
 * ActionBar
 * 
 * @author ResKoky
 * @year 2015
 *
 */
public class ActionBar
{
	private LayoutActivity	activity;
	private View			content;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public ActionBar(LayoutActivity activity)
	{
		this.activity = activity;
		this.content = this.activity.getContent().findViewById(R.id.idLayoutActionBar);
		
		// Hide native action bar (we do not need it)
		if(isActionBar() && this.activity.getActionBar()!=null) {
			this.activity.getActionBar().hide();
		}
	}
	
	/**
	 * isActionBar()
	 * 
	 * Check if native action bar is available
	 * 
	 * @return boolean
	 */
	public static boolean isActionBar()
	{
		return Versions.isHoneycombAbove();
	}
}
