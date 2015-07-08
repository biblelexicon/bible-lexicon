package bible.lexicon.core.utils;

/**
 * RunnableParams
 * 
 * @author Michal Schejbal
 * @year 2015
 *
 */
public class RunnableParams implements Runnable
{
	public Object params;
	
	public RunnableParams(){this(null);}
	public RunnableParams(Object params){this.params = params;}

	@Override
	public void run(){}
}
