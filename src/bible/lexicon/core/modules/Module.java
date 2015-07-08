package bible.lexicon.core.modules;

import bible.lexicon.core.modules.drivers.Driver;
import bible.lexicon.core.modules.drivers.DriverFactory;
import bible.lexicon.core.utils.File;

public class Module
{
	private Driver	driver;
	
	
	public Module(File file)
	{
		this.open(file);
	}
	
	public void open(File file)
	{
		this.driver = (new DriverFactory()).create(file);
	}

	public void close()
	{
		this.driver.close();
	}	
	
	public File getFile()
	{
		return this.driver.getFile();
	}
	

}
