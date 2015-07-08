package bible.lexicon.core.modules;

import java.util.ArrayList;

import bible.lexicon.config.Config;
import bible.lexicon.core.database.file.DatabaseFile;

/**
 * ModulesList
 * 
 * @author Michal Schejbal
 * @year 2015
 *
 */
public class ModulesList
{
	private Config				config;
	private ArrayList<Module> 	items;
	private DatabaseFile		db;

	public ModulesList()
	{
		this.config = Config.getInstance();
		this.db = new DatabaseFile(this.config.getBaseDirectory());
	}
	
	/**
	 *
	 * 
	 * 
	 */
	public void ini()
	{
		
	}
	
	public void add(Module module)
	{
	
	}
	
	public void delete(Module module)
	{
		
	}
}
