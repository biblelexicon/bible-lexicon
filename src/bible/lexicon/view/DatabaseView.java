package bible.lexicon.view;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;





import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class DatabaseView extends TabView
{

	/**
	 * DatabaseView()
	 * 
	 * @param layout
	 */
	public DatabaseView(int layout)
	{
		super(layout);
		// TODO Auto-generated constructor stub
	}
	
//	private ImageView			btSearch;
//	private ProgressBar			pbSearch;
//	private EditText			evText;
//	private TableLayout			tlContent;
//	
//	private File[]				files;	
//	private DBSdCard 			db;
//	
//	private String[] 			headerColumns;
//	private String				table;
//	
//	private static int			ROW_PADDING_LEFT	= 8;
//	private static int			ROW_PADDING_RIGHT 	= 8;
//	private static int			COLUMN_STR_MAX		= Utils.isTablet() ? 50 : 20;
//	
//	private final String[]		queries = 
//	{
//		"SELECT * FROM `info`",
//		"SELECT * FROM `data` LIMIT 0,5",
//		"SELECT * FROM `vocabulary` LIMIT 0,50",
//		"SELECT * FROM `versenotes` LIMIT 0,30",		
//		"SELECT * FROM `data` WHERE book = 40 AND chapter = 1",
//		"SELECT * FROM `data` GROUP BY book ORDER BY book ASC LIMIT 0,70",
//		"SELECT * FROM android_metadata",
//		"SELECT * FROM sqlite_master WHERE type='table'"
//	};
//	
//	private class IdValue
//	{
//		public String idName;
//		public String idValue;
//		public String table;		
//		public String column;
//	}
//
//
//
//	public DatabaseView(Context context, String sql)
//    {
//		super(context, R.layout.dbviewer);  
//        
////      this.bUseActionMode = false;
//        
//        this.headerColumns = null;
//        
//        this.iniDatabases();        
//        this.iniSearch();        
//        this.iniContent();	  
//        
//        if(sql != null) this.evText.setText(sql);
//	}
//	public DatabaseView(Context context){this(context, null);}
//
//	
//    public void iniSearch()
//    {
//    	this.btSearch = (ImageView) this.content.findViewById(R.id.idDBViewerSearchButton);
//    	this.pbSearch = (ProgressBar) this.content.findViewById(R.id.idDBViewerSearchProgress);
//    	    	
//    	this.content.findViewById(R.id.idDBViewerFilebrowser).setOnClickListener(new OnClickListener()
//    	{			
//			@Override
//			public void onClick(View v)
//			{	
//            	FilePicker picker = new FilePicker(context);
//
//            	picker.setRoot(Config.appDir);
//            	picker.setSelectMultiple(false);
//            	//picker.setDirectoriesOnly();
//            	picker.setFilePickerListener(new FilePicker.FilePickerListener()
//            	{	
//					@Override
//					public void onSelected(ArrayList<File> files)
//					{
//			        	File file = null;                  
//			            if(files.size()>0) file = files.get(0);
//
//			            if(file!=null)
//			            {
//							if(db!=null) db.close();
//							db = new DBSdCard(context, file.getParentFile(), file.getName());			            	
//			            }
//					}
//				});
//            	
//            	picker.show();				
//			}
//		});
//    	
//    	
//    	this.btSearch.setOnClickListener(new OnClickListener()
//    	{
//			@Override
//			public void onClick(View v)
//			{
//				search();
//			}
//		});
//   
//    	this.evText = (EditText) this.content.findViewById(R.id.idDBViewerSearchText);
//    	this.evText.setText(this.queries[0]);    	
//        this.evText.setOnKeyListener(new OnKeyListener()
//        {
//			public boolean onKey(View v, int keyCode, KeyEvent event)
//			{
//				// If the event is a key-down event on the "enter" button
//				if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
////					if(event.getAction() == KeyEvent.ACTION_UP)
//				{
//					search();
//				}
//				
//				return false;
//			}
//        });
//    	
//
//    	this.content.findViewById(R.id.idDBViewerSearchClear).setOnClickListener(new OnClickListener()
//    	{
//			@Override
//			public void onClick(View v)
//			{
//				evText.setText("");
//			}
//		});
//    	
//	
//		if(this.files != null)
//		{
//			SpinnerEx spDatabases = new SpinnerEx(this.context, this.content.findViewById(R.id.idDBViewerSearchDatabases));
//			
//			for(int i=0; i<this.files.length; i++)
//			{
//				spDatabases.add(this.files[i].getName(), this.files[i]);
//			}
//			
//			
//			spDatabases.setOnItemListener(new SpinnerEx.OnSpinnerExItemSelectedListener()
//	        {
//				@Override
//				public void onItemSelected(int position, Object value)
//				{	
//					File file = (File) value; 
//					if(db!=null) db.close();
//					db = new DBSdCard(context, file.getParentFile(), file.getName()); 
//				}
//			});			
//		}
//		
//		
//		SpinnerEx spQueries = new SpinnerEx(this.context, this.content.findViewById(R.id.idDBViewerSearchQueries));
//		for(int i=0; i<this.queries.length; i++)
//		{
//			spQueries.add(this.queries[i], this.queries[i]);
//		}		
//		
//		
//		spQueries.setOnItemListener(new SpinnerEx.OnSpinnerExItemSelectedListener()
//        {
//			@Override
//			public void onItemSelected(int position, Object value)
//			{	
//				evText.setText((String) value);
//			}
//		});			
//    }    
//    
//    public void iniContent()
//    {
//    	this.tlContent = (TableLayout) this.content.findViewById(R.id.idDBViewerContentLayout);
//    	
//    	if(this.files!=null && this.files.length!=0)
//    		this.db = new DBSdCard(context, this.files[0].getParentFile(), this.files[0].getName());  	
//    }
//    
//    public void iniDatabases()
//    {
//    	this.files = null;
//    	
//        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
//        { 	
//	    	File sdcard = Environment.getExternalStorageDirectory();
//	    	File root	= new File(sdcard.getAbsolutePath() + File.separator + Config.appIdentifier + File.separator);
//	
//	    	if(!root.exists())
//			{        
//	    		Toast.makeText(context, "No databases found", Toast.LENGTH_LONG).show();
//	    		return;
//			}
//	    	
//			this.files = root.listFiles(new ModuleFileFilter());
//			
//			if(files.length==0) Toast.makeText(context, "No databases found", Toast.LENGTH_LONG).show();
//			else Arrays.sort(this.files);
//        }    	
//    }
// 
//    
//    private void search()
//    {
//		setSearching(true);
//		new QueryTask().execute(evText.getText().toString());    	
//    }
//    
//	private class ModuleFileFilter implements FileFilter
//	{
//		private String[] extensions = {".db", ".dat"};
//		
//		@Override
//	    public boolean accept(File pathname)
//		{
//	        if(pathname.isDirectory() || pathname.isHidden())
//	            return false;
//
//	        for(String ext : this.extensions)
//	        {
//	            if(pathname.getName().toLowerCase().endsWith(ext))
//	                return true;
//	        }
//
//	        return false;
//	    }		
//	}
//	
//	private void clear()
//	{
//		this.tlContent.removeAllViews();
//	}
//	
//	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//	private void addHeader(Cursor c)
//	{
//		TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);			
//		TableRow row = new TableRow(this.tlContent.getContext());
//		row.setLayoutParams(params);		
//		
//		this.tlContent.addView(row);	
//
//		
//		this.headerColumns = c.getColumnNames();
//		for(int n=0; n<this.headerColumns.length; n++)
//		{
//			TextView col = new TextView(row.getContext());
//			
//			String name = this.headerColumns[n];
//			if(name!=null)
//			{
//				//name = name.replace("_", " ");
//			}
//			else
//				name = "";
//			
//			col.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//			col.setTypeface(null, Typeface.BOLD);
//			col.setText(name);		
//			
//			ShapeDrawable border = new ShapeDrawable(new RectShape());
//			border.getPaint().setStyle(Style.STROKE);
//			border.getPaint().setColor(Color.GRAY);
//			if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN) col.setBackground(border);
//			else col.setBackgroundDrawable(border);
//			
//			col.setPadding(Utils.dp2px(ROW_PADDING_LEFT), Utils.dp2px(2), Utils.dp2px(ROW_PADDING_RIGHT), Utils.dp2px(2));
//
//			row.addView(col);
//		}
//		
//	}	
//	
//	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//	private void addRow(Cursor c)
//	{
//		TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);			
//		TableRow row = new TableRow(this.tlContent.getContext());
//		row.setLayoutParams(params);		
//		
//		this.tlContent.addView(row);	
//
//		String idValue = "";
//		for(int i=0; i<c.getColumnCount(); i++)
//		{
//				String value = null;
//				switch(c.getType(i))
//				{
//					case Cursor.FIELD_TYPE_NULL: 
//						break;
//					case Cursor.FIELD_TYPE_INTEGER:
//						value = String.valueOf(c.getInt(i));
//						break;
//					case Cursor.FIELD_TYPE_FLOAT:
//						value = String.valueOf(c.getFloat(i));
//						break;
//					case Cursor.FIELD_TYPE_STRING:
//						value = c.getString(i);
//						break;
//					case Cursor.FIELD_TYPE_BLOB: 	
//						break;
//				}
//				
//				if(i==0) idValue = value;
//				
//				String text = "";
//				if(value !=null)
//				{
//					text = (value.length()>COLUMN_STR_MAX ? value.substring(0, COLUMN_STR_MAX)+"�" : value);
//					
//				}
//				
//				TextView col = new TextView(row.getContext());
//				
//				col.setSingleLine();
//				
//				col.setText(text);	
//				col.setBackgroundColor(Color.GRAY);
//				
//				ShapeDrawable border = new ShapeDrawable(new RectShape());
//				border.getPaint().setStyle(Style.STROKE);
//				border.getPaint().setColor(Color.GRAY);
//				if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN) col.setBackground(border);
//				else col.setBackgroundDrawable(border);				
//				
//				col.setPadding(Utils.dp2px(ROW_PADDING_LEFT), Utils.dp2px(2), Utils.dp2px(ROW_PADDING_RIGHT), Utils.dp2px(2));
//								
//				IdValue idvalue = new IdValue();
//				idvalue.column = c.getColumnName(i);
//				idvalue.idName = c.getColumnName(0);
//				idvalue.idValue = idValue;
//				idvalue.table = this.table;
//								
//				col.setTag(idvalue);
//				col.setOnClickListener(new OnClickListener()
//				{					
//					@Override
//					public void onClick(View v){new EditBox((IdValue) v.getTag()).show();}
//				});
//				
//				row.addView(col);			
//		}
//	}
//
//	
//	public class QueryTask extends AsyncTask<String, String, String>
//	{
//		private String 		sql;			
//		private Cursor		cursor;
//		private boolean		result;
//		private boolean		locked;	
//		private boolean 	condition;
//		
//		private boolean		hasHeader;
//
//		@Override
//		protected void onPreExecute()
//		{
//			super.onPreExecute();
//
//			this.hasHeader = false;
//			
//			this.result = false;
//			this.locked = false;
//			this.condition = true;
//			
//			clear();
//		}
//
//		@Override
//		protected String doInBackground(String... expression)
//		{
//			this.sql = expression[0];
//			table = getTableFromSQL(sql);
//	    	this.cursor = db.query(sql);	    	
//	       	
//	    	
//			if(this.cursor != null)
//			{
//				do
//				{
//						
//					if(!this.locked)
//					{
//						this.locked = true;
//						this.publishProgress();				
//					}
//	
//				}while(this.condition);
//				
//				this.cursor.close();	
//				this.result = true;							
//			}	    	
//	    	
//	
//	        return null;
//		}
//
//		protected void onProgressUpdate(String... progress)
//		{
//			if(!this.hasHeader)
//			{
//				addHeader(this.cursor);
//				this.hasHeader = true;
//			}
//			if(!this.cursor.isClosed()) 
//				addRow(this.cursor);
//				
//		
//			this.condition = this.cursor.moveToNext();
//			this.locked = false;			
//		}
//
//		@Override
//		protected void onPostExecute(String arg)
//		{
//			setSearching(false);
//			if(!this.result)
//			{
//				Toast.makeText(context, "No results found", Toast.LENGTH_SHORT).show();
//			}
//		}
//	}	
//	
//    public void setSearching(boolean running)
//    {
//		this.btSearch.setVisibility(running ? View.INVISIBLE : View.VISIBLE);		
//		this.pbSearch.setVisibility(running ? View.VISIBLE : View.INVISIBLE);
//    } 
//    
//    @Override
//    protected void iniBackgroundColor()
//    {
//    	this.content.findViewById(R.id.idDBViewerSearchLayout).setBackgroundResource(Config.isNightMode ? R.color.search_layout_bg_night : R.color.search_layout_bg);
//    }     
//    
//    public class EditBox
//    {
//    	private AlertDialog dialog;
//    	private IdValue idvalue;
//    	
//    	private TextView	tvContent;
//    	private EditText	etValue;
//    	
//    	public EditBox(IdValue idv)
//    	{
//    		this.dialog = null;
//    		this.idvalue = idv;
//    		
//        	if(idvalue!=null && idvalue.idValue.length()!=0)
//        	{
//	    		
//	      		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.DialogBoxesCustom));
//	    		builder.setPositiveButton(context.getString(R.string.dialogBoxesSave), new DialogInterface.OnClickListener()
//	    		{
//	    			@Override
//	    			public void onClick(DialogInterface dialog, int which)
//	    			{
//	    				if(!db.db.isReadOnly())
//	    				{
//	    					db.addValue(idvalue.column, etValue.getText().toString());
//	    					db.update(idvalue.idValue, idvalue.idName, idvalue.table);
//	    					db.log();
//	    				    					
//	    					Toast.makeText(context, "Value has been updated", Toast.LENGTH_SHORT).show();
//	    				}
//	    				else
//	    					Toast.makeText(context, "Database is readonly", Toast.LENGTH_SHORT).show();
//	    				
//	    				dialog.dismiss();
//	    			}			
//	    		});
//	    		builder.setNegativeButton(context.getString(R.string.dialogBoxesClose), new DialogInterface.OnClickListener()
//	    		{
//	    			@Override
//	    			public void onClick(DialogInterface dialog, int which)
//	    			{
//	    				dialog.dismiss();
//	    			}			
//	    		});   
//	    		
//	    		builder.setTitle("Edit row with id `"+idvalue.idValue+"`");
//	    		
//	    		
//	    		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	    		View v = inflater.inflate(R.layout.dbviewer_edit, null);
//	    		
//	    		this.tvContent = (TextView) v.findViewById(R.id.idDBViewerEditContent);
//	    		this.etValue = (EditText) v.findViewById(R.id.idDBViewerEditValue);
//	    		
//	    		Cursor c = db.getItemById(idvalue.idValue, idvalue.idName, idvalue.table);
//	    		if(c!=null)
//	    		{
//	    			this.tvContent.setText(DBHandler.getString(c, idvalue.column));
//	    			this.etValue.setText(DBHandler.getString(c, idvalue.column));
//		        	
//		        	c.close();
//	    		}    		
//	    		
//	    		builder.setView(v);    		
//	    		this.dialog = builder.create();  
//        	}
//    	}
//    	
//    	public void show()
//    	{
//    		if(this.dialog!=null) this.dialog.show();
//    	}
//    }
//
//    
//    private String getTableFromSQL(String sql)
//    {
//    	if(sql!=null)
//    	{
//    		String[] parts = sql.split("(FROM|WHERE|LIMIT|GROUP)");
//    	
//    		if(parts!=null && parts.length>1)
//    		{
//    			return parts[1].replace("`","").trim();
//    		}
//    		else
//    			return null;
//    	}
//    	else
//    		return null;
//    }
}
