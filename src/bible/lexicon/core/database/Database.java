package bible.lexicon.core.database;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import bible.lexicon.core.debug.Debug;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Database
 *
 * - If extending you must inicialize this.db
 * - If is not extended, dont forget to call setDB() method
 *
 * @author Michal Schejbal
 * @year 2015
 *
 */
public class Database
{
	protected SQLiteDatabase	db;
	protected String 			strDBPath;
	protected String 			strDBName;	
	
	private String 				defaulttablename = null;
	private ArrayList<String>	columns	  = new ArrayList<String>();	
	private ArrayList<String>	tables    = new ArrayList<String>();	
	private ArrayList<String>	joins     = new ArrayList<String>();	
	private ArrayList<String>	wheres    = new ArrayList<String>();
	private ArrayList<String>	groups    = new ArrayList<String>();	
	private ArrayList<String>	orders    = new ArrayList<String>();
	private int 				from      = 0;
	private int 				count     = 0;

	private Map<String, String>	values	= new HashMap<String, String>();

	private String				lastQuery = null;
	private boolean				exceptions = true;
	
	private boolean				isOpened;			// Database state
	

	public Database()
	{	
		this(null);	
	}
	
	public Database(SQLiteDatabase db)
	{
		this.setSQLiteDatabase(db);
		this.isOpened = true;		
		this.reset();
	}


	
	public void close()
	{
		// Probably the best way is to not close the db but rather let it be GCed
		//if(this.db!=null) this.db.close();
		this.isOpened = false;		
	}
	
	public void closeForced()
	{
		if(this.db!=null) this.db.close();
		this.isOpened = false;		
	}
	
	public boolean isOpened()
	{
		return this.isOpened;
	}
	
	/**
	 * setSQLiteDatabase()
	 *
	 * Set SQLiteDatabase database instance
	 * 
	 * @param db
	 */
	public void setSQLiteDatabase(SQLiteDatabase db)
	{
		this.db = db;
	}

	/**
	 * Escapes value
	 * 
	 * @param String value
	 * @return String
	 */
	public String e(String value)
	{
		if(value!=null) {
//			value = value.replaceAll("(['\"]{1,})", "#$1"); 
			value = value.replace("'", "\'");
			value = value.replace("\"", "\'"); 
			
			return value;
		} else {
			return null;
		}
	}

	/**
	 * Quotes value and do escaping
	 * 
	 * @param String value
	 * @param boolean addPercents
	 * @return String
	 */
	public String q(String value, boolean addPercents)
	{
		if(value!=null) {
			if(value.matches("[-+]?\\d*\\.?\\d+")) {
				return value;
			} else {
				value = this.e(value);	// Do escaping
				if(addPercents) value = "%"+value+"%";
				value = "\""+value+"\"";
        
				return value;	
			}
		} else {
			return null;
		}
	}
	/**
	 * Quotes value and do escaping
	 * 
	 * @param String value
	 * @return String
	 */
	public String q(String value)
	{
		return q(value, false);
	}


	/**
	 * isTable()
	 * 
	 * Check for an existence of given table
	 * @param table to check for existence
	 * @return true is exists
	 */
	public boolean isTable(String table)
	{
		if(this.db == null) return false;
		
		String sqlquery = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+table+"'";
		Cursor cursor = this.db.rawQuery(sqlquery, null);
		
		if(cursor!=null)
		{
	        if(cursor.moveToFirst())
	        {
	        	cursor.close();
	        	return true;
	        }
	        else
	        	cursor.close();
		}
	    
	    return false;
	}
	
	/**
	 * isColumn()
	 * 
	 * Check for an existence of table column
	 * 
	 * @param column
	 * @param table
	 * @return true is exists
	 */
	public boolean isColumn(String column, String table)
	{
		if(this.db == null) return false;
		
		String sqlquery = "PRAGMA table_info("+table+")";
		Cursor cursor = this.db.rawQuery(sqlquery, null);
		
		if(cursor!=null)
		{
			cursor.moveToFirst();
			
			do
			{
				String c = this.getString(cursor, "name");
				if(c.equals(column))
				{
					cursor.close();
					return true;
				}
			}
	        while(cursor.moveToNext());

			cursor.close();
		}
	    
	    return false;
	}
	

	
	
	/**
	 * getLastId()
	 * 
	 * Get last inserted id to given table
	 * 
	 * @param tablename
	 * @return
	 */
	private int getLastId(String tablename)
	{
		if(this.db == null) return 0;

		String sql = "SELECT `seq` FROM `sqlite_sequence` WHERE `name` = "+this.q(tablename);
				
		if(!this.isTable("sqlite_sequence"))
		{
			this.log("`sqlite_sequence` doesnt exists");
			return 0;
		}

		this.showQuery(sql);
		Cursor cursor = this.db.rawQuery(sql, null);
					
		if(cursor!=null)
		{
			if(cursor.moveToFirst())
			{
				int id = cursor.getInt(0);
				cursor.close();
				
				return id;
			}
		}
		
		return 0;
	}		
	

	// ---------------------------------------------------------------------------------------------------------------------------------------	
	// SELECT functions
	// ---------------------------------------------------------------------------------------------------------------------------------------	
	
	/**
	 * Add column to query
	 * @param column
	 */
	public void addColumn(String column){this.columns.add(column);}
	
	/**
	 * Add table to query
	 * @param jointable
	 * @param joincolumn
	 * @param ontable
	 * @param oncolumn
	 */
	public void addTable(String jointable, String joincolumn, String ontable, String oncolumn)
	{
		this.addColumn(jointable + ".*");
		this.addWhere(jointable + "." + joincolumn + " = " + ontable + "." + oncolumn);
		this.tables.add(jointable);
	}
	public void addTableSubQuery(String jointable, String query)
	{
		this.addColumn(jointable + ".*");
		this.tables.add("("+query+") "+jointable);
	}
	
	/**
	 * Add table via join to query
	 * @param jointable table to join
	 * @param joincolumn joining column
	 * @param ontable table to join on
	 * @param oncolumn joining column
	 */
	public void addJoin(String jointable, String joincolumn, String ontable, String oncolumn)
	{
		this.addColumn(jointable + ".*");
		this.joins.add("LEFT JOIN " + jointable + " ON " + jointable + "." + joincolumn + " = " + ontable + "." + oncolumn);
	}
	public void addJoinSubQuery(String jointable, String query, String joincolumn,  String ontable, String oncolumn)
	{
		this.addColumn(jointable + ".*");
		this.joins.add("LEFT JOIN ("+query+") " + jointable + " ON " + jointable + "." + joincolumn + " = " + ontable + "." + oncolumn);
	}
	
	/**
	 * Add restriction to query
	 * @param condition
	 * @param conjuction AND|OR
	 */
	public void addWhere(String condition, String conjuction){this.wheres.add((this.wheres.size()>0 ? conjuction + " " : "") + condition);}

	/**
	 * Add restriction to query
	 * @param condition
	 */
	public void addWhere(String condition){this.addWhere(condition, "AND");}
	
	/**
	 * Add group by to query
	 * @param condition
	 */
	public void addGroupBy(String condition){this.groups.add(condition);}

	/**
	 * Add order by to query
	 * @param column
	 * @param ascension
	 */
	public void addOrderBy(String column, String ascension){this.orders.add(column + " " + ascension);}
	/**
	 * Add order by to query
	 * @param column
	 */
	public void addOrderBy(String column){this.addOrderBy(column, "DESC");}

	/**
	 * Set items limit for query
	 * @param count
	 * @param from
	 */
	public void setLimit(int count, int from){this.count = count; this.from  = from;}	
	/**
	 * Set items limit for query
	 * @param count
	 */
	public void setLimit(int count){this.count = count;this.from  = 0;}
	
	/**
	 * Show current query string
	 */
	public void showQuery(){this.log(this.builQuery());}
	/**
	 * Show custom query string
	 * @param query
	 */
	public void showQuery(String query){this.log(query);}
	/**
	 * Get query string
	 */
	public String getQuery(){return this.builQuery();}
	public String getLastQuery(){return this.lastQuery;}
	
	/**
	 * Check if column was added to selection 
	 * @param column
	 * @return true if was
	 */
	public boolean hasColumn(String column)
	{
		for(int i=0; i<this.columns.size(); i++)
		{
			if(column.equals(this.columns.get(i)))
				return true;	// break and return
		}
		return false;
	}	
	
	/**
	 * Check if table was added to selection
	 * @param column
	 * @return
	 */
	public boolean hasTable(String tablename)
	{
		for(int i=0; i<this.tables.size(); i++)
		{
			if(tablename.equals(this.tables.get(i)))
				return true;	// break and return
		}
		return false;
	}		
	
	/**
	 * Reset current query parameters
	 * This must be done before setting new query
	 */
	public void reset()
	{
		this.defaulttablename = null;
		this.columns.clear();
		this.tables.clear();
		this.joins.clear();
		this.wheres.clear();
		this.groups.clear();
		this.orders.clear();
		
		this.from  = 0;
		this.count = 0;	
	}	
	
	/**
	 * Build query string
	 * @return built query string
	 */
	private String builQuery()
	{
		String sql = "SELECT";

		// Columns
		if(!this.hasColumn(this.defaulttablename+".*")) this.addColumn(this.defaulttablename + ".*");
		if(this.columns.size()>0)
		{
			String strcolumns = "";			
			for(int i=0; i<this.columns.size(); i++)
				strcolumns += (i!=0 ? ", " : " ") + this.columns.get(i);	
			
			sql += strcolumns;
		}


		// Tables
		if(!this.hasTable(this.defaulttablename))
		{
			if(this.defaulttablename!=null)
				this.tables.add(this.defaulttablename);		// Main table
		}
		if(this.tables.size()>0)
		{
			String strtables = "";			
			for(int i=0; i<this.tables.size(); i++)
				strtables += (strtables.length()==0 ? "" : ", ") + this.tables.get(i);
			
			sql += " FROM " + strtables;
		}		
		
		// Join
		if(this.joins.size()>0)
		{
			String strjoin = "";			
			for(int i=0; i<this.joins.size(); i++)
				strjoin += " " + this.joins.get(i);
			
			sql += strjoin;
		}
		

		// Where
		if(this.wheres.size()>0)
		{
			String strwhere = "";			
			for(int i=0; i<this.wheres.size(); i++)
				strwhere += " " + this.wheres.get(i);

			sql += " WHERE " + strwhere;
		}
		

		// Group
		if(this.groups.size()>0)
		{
			String strgroup = "";			
			for(int i=0; i<this.groups.size(); i++)
				strgroup += (strgroup.length()==0 ? "" : ", ") + this.groups.get(i);

			sql += " GROUP BY " + strgroup;
		}
		
	
		// Order
		if(this.orders.size()>0)
		{
			String strorder = "";
			for(int i=0; i<this.orders.size(); i++)
				strorder += (strorder.length()==0 ? "" : ", ") + this.orders.get(i);

			sql += " ORDER BY " + strorder;
		}
		
		
		// Limit
		if(this.count!=0)
			sql += " LIMIT " + (this.from!=0 ? this.from + ", " : "") + this.count;

		return sql;
	}	
	
	/**
	 * Perform query to database. Only return queries
	 * @param sql string
	 * @return results cursor
	 */
	public Cursor query(String sql)
	{
		if(this.db == null) return null;		
		if(!this.isOpened()) {
			this.log("Unable to perform query `"+sql+"` because `"+this.db.getPath()+"` was already closed.");
			return null;
		}
		
		if(sql!=null && sql.length()>0) {
			Cursor cursor = null;			
			
			if(this.exceptions) {
				try {
					cursor = this.db.rawQuery(sql, null);
				} catch(Exception e) {
					Debug.exception(e, this, sql+"\n\nDatabase: "+this.db.getPath());
				}
			} else {
				cursor = this.db.rawQuery(sql, null);
			}
			
			//this.showQuery();
			this.lastQuery = sql;
			this.reset();
			
			if(cursor!=null) {
				try {				
					if(!cursor.moveToFirst())
						return null;
				} catch(Exception e) {
					Debug.exception(e, this, sql+"\n\nDatabase: "+this.db.getPath());
					return null;
				}				
			}
			
			return cursor;
		}
		else
			return null;		
	}
	
	/**
	 * Perform query to database
	 * @param sql string
	 */	
	public void execute(String sql)
	{
		this.lastQuery = sql;
		this.showQuery(sql);		
		this.db.execSQL(sql);
	}	
	
	/**
	 * Get query items
	 * @param tablename
	 * @return query string
	 */
	public String getItemsQuery(String tablename)
	{
		this.defaulttablename = tablename;
		String sql = this.builQuery();
		this.reset();
		
		return sql;
	}	
		
	/**
	 * Get items
	 * @param tablename
	 * @return cursor of items
	 */
	public Cursor getItems(String tablename)
	{
		this.defaulttablename = tablename;
		return this.query(this.builQuery());
	}
	
	/**
	 * Get items by ID
	 * @param id
	 * @param idcolumnname 
	 * @param tablename
	 * @return cursor of items
	 */
	public Cursor getItemsById(int id, String idcolumnname, String tablename)
	{
		this.defaulttablename = tablename;
		this.addWhere(idcolumnname+" = "+id);		
		
		return this.query(this.builQuery());
	}	
	
	/**
	 * Get item by ID
	 * @param id
	 * @param idcolumnname 
	 * @param tablename
	 * @return
	 */
	public Cursor getItemById(int id, String idcolumnname, String tablename)
	{
		this.defaulttablename = tablename;		
		this.addWhere(idcolumnname+" = "+id);
		this.setLimit(1);
		
		return this.query(this.builQuery());		
	}
	/**
	 * Get item by ID
	 * @param id
	 * @param tablename
	 * @return
	 */
	public Cursor getItemById(int id, String tablename){return this.getItemById(id, "id", tablename);}

	public Cursor getItemById(String id, String idcolumnname, String tablename)
	{
		if(id!=null)
		{
			this.defaulttablename = tablename;		
			this.addWhere(idcolumnname+" = "+this.q(id));
			this.setLimit(1);
			
			return this.query(this.builQuery());
		}
		else
			return null;
	}

	
	// ---------------------------------------------------------------------------------------------------------------------------------------
	// INSERT & UPDATE functions
	// ---------------------------------------------------------------------------------------------------------------------------------------		
	/**
	 * Insert into table
	 * @param tablename
	 * @return
	 */
	public int insert(String tablename)
	{
		if(this.db == null) return 0;
		if(this.values.size()==0) return 0;
		
		String columnsStr = "";
		String valuesStr = "";
		
		Iterator<?> it	= this.values.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
			columnsStr += (columnsStr.length()>0 ? ", " : "")+"`"+(String)entry.getKey()+"`";
			valuesStr += (valuesStr.length()>0 ? ", " : "")+this.q((String)entry.getValue());				
		}
	
		
		this.execute("INSERT INTO "+tablename+" ("+columnsStr+") VALUES ("+valuesStr+")");
		// Can be also done with this.db.insert returning last id

		// Reset
		this.values.clear();
		
		// Return inserted id
		return this.getLastId(tablename);
	}	
	
	/**
	 * Add column with its value to insert
	 * @param column
	 * @param value
	 */
	public void addValue(String column, String value)
	{
		this.values.put(column, value);
	}
	public void addValue(String column, int value){this.addValue(column, String.valueOf(value));}
	public void addValue(String column, long value){this.addValue(column, String.valueOf(value));}
	public void addValue(String column, boolean value){this.addValue(column, String.valueOf(value));}
	public void addValue(String column, float value){this.addValue(column, String.valueOf(value));}

	
	/**
	 * Update db record
	 * @param id
	 * @param idName
	 * @param tablename
	 * @return
	 */
	public int update(int id, String idcolumnname, String tablename)
	{
		if(this.db == null) return 0;
		if(id==0) return 0;				// No id to update set
		if(this.values.size()==0) return 0;
		
		String updateString = "";

		Iterator<?> it	= this.values.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
			updateString += (updateString.length()>0 ? ", " : "")+"`"+(String)entry.getKey()+"` = "+this.q((String) entry.getValue());			
		}		


		this.execute("UPDATE "+tablename+" SET "+updateString+" WHERE `"+idcolumnname+"` = "+id);				
	
		this.values.clear();
		
		return id; 
	}
	/**
	 * Update 
	 * @param id
	 * @param tablename
	 * @return
	 */
	public int update(int id, String tablename){return this.update(id, "id", tablename);}
	
	/**
	 * Update db record
	 * @param id
	 * @param idName
	 * @param tablename
	 * @return
	 */
	public String update(String id, String idcolumnname, String tablename)
	{
		if(this.db == null) return null;
		if(id!=null && id.length()==0) return null;				// No id to update set
		if(this.values.size()==0) return null;
		
		String updateString = "";

		Iterator<?> it	= this.values.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
			updateString += (updateString.length()>0 ? ", " : "")+"`"+(String)entry.getKey()+"` = "+this.q((String) entry.getValue());			
		}		

		this.execute("UPDATE "+tablename+" SET "+updateString+" WHERE `"+idcolumnname+"` = "+this.q(id));			
	
		this.values.clear();
		
		return id; 
	}	
	
	/**
	 * Update db record by raw
	 * @param id
	 * @param idName
	 * @param tablename
	 * @return
	 */
	public void updateRaw(String updateStr, String tablename)
	{
		if(this.db == null) return;
		if(updateStr==null) return;	// Update string not set

		this.execute("UPDATE "+tablename+" SET "+updateStr);		
	}
	

	// ---------------------------------------------------------------------------------------------------------------------------------------	
	// CREATE & ALTER table function
	// ---------------------------------------------------------------------------------------------------------------------------------------	
	/**
	 * Create table
	 * @param tablename
	 * @param columns
	 */
	public void create(String tablename, String columns)
	{
		if(this.db == null) return;
		
		String sql = null;
		if(!this.isTable(tablename)) {
			this.execute("CREATE TABLE `"+tablename+"` ("+columns+")");			
		} else {
			// http://www.sqlite.org/lang_altertable.html
			// It is not possible to rename a column, remove a column, or add or remove constraints from a table.
			
			// Creating table data backup
			String tablenameOld = tablename+"_old";
			
			if(this.isTable(tablenameOld)) this.drop(tablenameOld);
			this.execute("ALTER TABLE `"+tablename+"` RENAME TO "+tablenameOld);

			// Creating new table
			this.execute("CREATE TABLE `"+tablename+"` ("+columns+")");			
			
			// Fill up the old data to the new table
			try {
				Cursor cursor = this.query("SELECT COUNT(*) AS `count` FROM "+tablenameOld);
				
				int count = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));
				int size = 500;
				for(int i=0; i<=count; i+=size) {
					this.setLimit(size, i);
					Cursor items = this.getItems(tablenameOld);
					if(items != null) {
						do {	
							for(int c=0; c<items.getColumnCount(); c++) {
								this.addValue(items.getColumnName(c), items.getString(c));
							}
							
							this.insert(tablename);
						} while(items.moveToNext());
					}
				}
				
				this.drop(tablenameOld);
			} catch(Exception e) {
				Debug.exception(e, this);
			}	
					
		}
	}
	
	/**
	 * Create fulltext search table
	 * @param tablename
	 * @param columns
	 */
	public void createFTS(String tablename, String columns)
	{
		if(this.db == null) return;
		
		this.execute("CREATE VIRTUAL TABLE `"+tablename+"` USING fts4("+columns+")");
	}	
	
	/**
	 * Drop table
	 * @param tablename
	 */
	public void drop(String tablename)
	{
		if(this.db == null) return;
		
		if(this.isTable(tablename))
		{
			this.execute("DROP TABLE `"+tablename+"`");
		}
	}	
	
	/**
	 * Gets table structure in sql string
	 * @param tablename
	 * @return
	 */
	public String getTableStuctureSql(String tablename)
	{
		this.addWhere("type = 'table'");
		Cursor cursor = this.getItemById(tablename, "name", "sqlite_master");
		
		if(cursor != null)
		{
			return cursor.getString(cursor.getColumnIndex("sql"));
		}		

		return null;
	}	
	
	// ---------------------------------------------------------------------------------------------------------------------------------------		
	// REMOVE functions
	// ---------------------------------------------------------------------------------------------------------------------------------------		
	/**
	 * Remove all items
	 * @param id
	 * @param tablename
	 */
	public void removeAll(String tablename)
	{
		this.query("DELETE FROM `"+tablename+"`");
	}	
	/**
	 * Remove by id
	 * @param id
	 * @param tablename
	 */
	public void removeById(int id, String tablename)
	{
		this.removeById(id, "id", tablename);
	}
	/**
	 * Remove by id with specified column
	 * @param id
	 * @param idcolumnname
	 * @param tablename
	 */
	public void removeById(int id, String idcolumnname, String tablename)
	{
		if(this.db == null) return;
		
		this.execute("DELETE FROM `"+tablename+"` WHERE "+idcolumnname+" = "+id);		
	}

	/**
	 * Remove by ids
	 * @param ids
	 * @param idcolumnname
	 * @param tablename
	 */
	public void removeByIds(ArrayList<Integer> ids, String idcolumnname, String tablename)
	{
		if(this.db == null) return;
		
		String idsstr = null;
		for(int i=0; i<ids.size(); i++)
			idsstr += (idsstr.length()>0 ? ", " : "")+ids.get(i);

		this.execute("DELETE FROM `"+tablename+"` WHERE "+idcolumnname+" IN ("+idsstr+")");		
	}
	public void removeByIds(ArrayList<Integer> ids, String tablename){this.removeByIds(ids, "id", tablename);}
	
	/**
	 * Delete by custom condition
	 * - set condition string to 1, to delete whole table
	 * @param condition
	 * @param tablename
	 */
	public void removeByCondition(String condition, String tablename)
	{
		if(this.db == null) return;
		
		this.execute("DELETE FROM `"+tablename+"` WHERE "+condition);		
	}
	
	/**
	 * Truncate table
	 * Table will be first dropped and then recreated
	 * @param tablename
	 */	
	public void truncate(String tablename)
	{
		if(this.db == null) return;
		
		if(this.isTable(tablename))
		{
			String tableSql = this.getTableStuctureSql(tablename);
			this.execute("DROP TABLE `"+tablename+"`");
			this.execute(tableSql);
		}
	}	
	

	// ---------------------------------------------------------------------------------------------------------------------------------------		
	// INDEX functions
	// ---------------------------------------------------------------------------------------------------------------------------------------	
	/**
	 * Creates index on column on specified table
	 * @param column
	 * @param table
	 */
	public void createIndex(String column, String table)
	{
		if(this.db == null) return;
		
		this.execute("CREATE INDEX i_"+table+"_"+column+" ON "+table+" (`"+column+"`)");				
	}
	/**
	 * Drop index on column on specified table
	 * @param column
	 * @param table
	 */	
	public void dropIndex(String column, String table)
	{
		if(this.db == null) return;
		
		String index = "i_"+table+"_"+column;
		
		if(this.isIndex(column, table)) this.execute("DROP INDEX "+index);
		else Debug.log("SQL", "index `"+index+"` does not exists");
	}
	/**
	 * Drop index by name
	 * @param name
	 */	
	public void dropIndex(String name)
	{
		if(this.db == null) return;
		
		this.execute("DROP INDEX "+name);
	}	
	/**
	 * Check index existence
	 * @param column
	 */	
	public boolean isIndex(String column, String table)
	{
		if(this.db == null) return false;
		
		String sqlquery = "PRAGMA index_info(i_"+table+"_"+column+")";
		Cursor cursor = this.db.rawQuery(sqlquery, null);
		
		if(cursor!=null)
		{
			if(cursor.moveToFirst())
			{
				cursor.close();
				return true;
			}
		}	    
	    return false;		
	}	
	


	// ---------------------------------------------------------------------------------------------------------------------------------------		
	// Cursor get functions
	// ---------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * getValue()
	 * 
	 * Get column value from cursor
	 * 
	 * http://developer.android.com/reference/android/database/Cursor.html
	 * 
	 * @param Cursor c
	 * @param String column
	 * @param Object defaultValue
	 * @return Object
	 */
	static public Object getValue(Cursor c, String column, Object defaultValue)
	{
		int i = c.getColumnIndex(column);
		if(i!=-1) {
			switch(c.getType(i)) {
				default:
				case Cursor.FIELD_TYPE_STRING:
					String s = c.getString(i);
					if(s.length()==0) {
						return defaultValue;
					} else {
						return s;
					}
					
				case Cursor.FIELD_TYPE_INTEGER:
					return c.getInt(i);
					
				case Cursor.FIELD_TYPE_FLOAT:
					return c.getFloat(i);
					
				case Cursor.FIELD_TYPE_NULL:
					return defaultValue;
			}
		} else {
			return defaultValue;
		}	
	}

	static public String getString(Cursor c, String column, String defaultValue)
	{
		return (String) Database.getValue(c, column, defaultValue);
	}
	static public String getString(Cursor c, String column)
	{
		return Database.getString(c, column, null);
	}
	
	
	static public int getInt(Cursor c, String column, int defaultValue)
	{
		return (Integer) Database.getValue(c, column, defaultValue);
	}
	static public int getInt(Cursor c, String column)
	{
		return Database.getInt(c, column, 0);
	}
	
	
	static public float getFloat(Cursor c, String column, float defaultValue)
	{
		return (Float) Database.getValue(c, column, defaultValue);		
	}
	static public float getFloat(Cursor c, String column)
	{
		return Database.getFloat(c, column, 0);
	}


	static public boolean getBool(Cursor c, String column, boolean defaultValue)
	{
		int value = (Integer) Database.getValue(c, column, defaultValue ? 1 : 0);	
		return value==1;
	}
	static public boolean getBool(Cursor c, String column)
	{
		return Database.getBool(c, column, false);
	}	



	// ---------------------------------------------------------------------------------------------------------------------------------------		
	// Misc functions
	// ---------------------------------------------------------------------------------------------------------------------------------------

	public void log(String query)
	{
		Debug.log("SQL `"+this.strDBName+"`", query);
	}

	public void log()
	{
		this.log(this.getLastQuery());
	}
}
