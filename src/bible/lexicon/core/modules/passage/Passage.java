package bible.lexicon.core.modules.passage;

import bible.lexicon.core.debug.Debug;
import bible.lexicon.core.modules.Module;


/**
 * Passage
 * 
 * @author Michal Schejbal
 * @year 2015
 *
 */
class Passage
{
	private String		title;
	private Position 	from;
	private Position 	to;
	private Module 		module;
	

	public Passage(int book, int chapter, int verse, Module module)
	{
		this.from	= new Position(book*Position.INDEX_BOOK + chapter*Position.INDEX_CHAPTER + verse*Position.INDEX_VERSE, module);
		this.to 	= null;		
		this.title	= this.from.getBookName()+" "+this.from.getChapter()+":"+this.from.getVerse();		
	}
	
//	public Passage(String passage, Module module)
//	{
//		if(passage!=null && passage.length()>0) {
//
//			if(passage.matches("^[0-9]{0,1} {0,1}([A-Za-z]{1,} {0,1}){1,3} {0,1}[0-9]{0,2}.{0,}")) {
//				if(module!=null) passage = String.valueOf(module.is ? Position.NT_POSITION : Position.OT_POSITION);
//				else passage = String.valueOf(Config.NT_DEF_POSITION);
//			}
//			
//			try {
//				if(passage.contains(";"))
//				{
//					String[] parts = passage.split(";");
//					this.from = Integer.parseInt(parts[0]);
//					this.to = Integer.parseInt(parts[1]);
//					
//					if(this.to<=0) this.to = -1;				
//					this.isRange = this.to!=-1;
//				}
//				else
//				{
//					this.from = Integer.parseInt(passage);
//					this.to = -1;
//					this.isRange = false;
//				}
//				
//				this.iniParts();
//				if(this.isRange && this.to == this.from && !this.toParts.isVerse)
//				{
//					this.to += CHAPTER_INDEX;
//					this.iniParts();
//				}						
//
//				this.obtainTitle(module);
//			} catch(Exception e) {
//				Debug.exception(e, this, passage);
//			}
//		}
//	}
//	
//	
//	public Passage(int from, int to, Module module)
//	{			
//		this.input = from +" "+to;
//		this.from = from;
//		this.to = to;
//		if(this.to<=0) this.to = -1;	
//		this.isRange = this.to!=-1;
//		
//		this.iniParts();
//		if(this.isRange && this.to == this.from && !this.toParts.isVerse)
//		{
//			this.to += CHAPTER_INDEX;
//			this.iniParts();
//		}			
//		
//		if(this.from!=0) this.obtainTitle(module);
//		
//		return this;
//	}	
	
	
	public String getTitle()
	{
		return this.title;
	}
	
	public Position getFrom()
	{
		return this.from;
	}
	
	public Position getTo()
	{
		return this.to;
	}
	
	public boolean isNT()
	{
		return false;
	}
	
	public boolean isOT()
	{
		return false;
	}
	
	
	
	public void moveToNextChapter()
	{
		
	}	
}