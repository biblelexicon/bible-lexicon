/**
 * 
 */
package bible.lexicon.core.modules.drivers;

import bible.lexicon.core.utils.File;

/**
 * Driver
 * 
 * @author ResKoky
 * @year 2015
 *
 */
public abstract class Driver
{
	protected File 		file;
	
	
	public Driver(File file)
	{
		this.file = file;
		this.open(this.file);
	}
	
	/**
	 * open()
	 * 
	 * Open source file for the driver
	 * 
	 * @param file
	 */
	abstract protected void open(File file);
	
	/**
	 * close()
	 * 
	 * Close driver
	 */
	abstract public void close();
	

	/**
	 * getFile()
	 * 
	 * Get driver source file
	 * 
	 * @return File
	 */
	public File getFile()
	{
		return this.file;
	}
	
	/**
	 * getType()
	 * 
	 * Get type of driver
	 * All types are defined at DriverFactory class
	 * 
	 * @return int
	 */
	public int getType()
	{
		return DriverFactory.getTypeByExtension(this.file.getExtension());
	}
}
