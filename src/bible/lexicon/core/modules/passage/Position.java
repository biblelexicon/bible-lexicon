package bible.lexicon.core.modules.passage;

import bible.lexicon.core.modules.Module;

class Position
{
	static public int INDEX_BOOK 	= 1000000;
	static public int INDEX_CHAPTER = 1000;
	static public int INDEX_VERSE 	= 1;
	
	static public int NT_POSITION	= 40001000;
	static public int OT_POSITION	= 1001000;
	
	
	private Book 		book;
	private Chapter 	chapter;
	private Verse		verse;
	private Module		module;

	private class Book
	{
		int number;
		String nameFull;
		String nameShort;
	}
	
	private class Chapter
	{
		int number;
	}
	
	private class Verse
	{
		int number;
	}
	
	
	
	
	public Position(int position, Module module)
	{
		this.module = module;
		this.book = new Book();
		this.chapter = new Chapter();
		this.verse = new Verse();
		
//		this.book.na
	}
	
	
	public int getBook()
	{
		return this.book.number;
	}
	
	public String getBookName()
	{
		return this.book.nameFull;
	}
	
	public String getBookNameShort()
	{
		return this.book.nameShort;
	}
	
	public int getChapter()
	{
		return this.chapter.number;
	}
	
	public int getVerse()
	{
		return this.verse.number;
	}
}