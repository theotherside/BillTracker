package com.example.billtracker;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class that provides helpful database table accessor variables and manages
 * basic required database functionality.  
 */
public class BillTable {

	/** Bill table name in the database. */
	public static final String DATABASE_TABLE_BILL = "bill_table";

	/** Bill table column names and IDs for database access. */
	public static final String BILL_KEY_ID = "_id";
	public static final int BILL_COL_ID = 0;
	
	public static final String BILL_KEY_TYPE_TEXT = "bill_type_text";
	public static final int BILL_COL_TYPE_TEXT = BILL_COL_ID + 1;
	
	public static final String BILL_KEY_DESC_TEXT = "bill_desc_text";
	public static final int BILL_COL_DESC_TEXT = BILL_COL_TYPE_TEXT + 1;
	
	public static final String BILL_KEY_AMOUNT_TEXT = "bill_amount_text";
	public static final int BILL_COL_AMOUNT_TEXT = BILL_COL_DESC_TEXT + 1;
	
	public static final String BILL_KEY_DUE_DATE_TEXT = "bill_due_date_text";
	public static final int BILL_COL_DUE_DATE_TEXT = BILL_COL_AMOUNT_TEXT + 1;
	
	/** SQLite database creation statement. Auto-increments IDs of inserted bills.
	 * Bill IDs are set after insertion into the database. */
//	public static final String DATABASE_CREATE = 
//			"create table " + DATABASE_TABLE_BILL + " (" +
//			BILL_KEY_ID + " integer primary key, " + 
//			BILL_KEY_TYPE_TEXT + " text, " + 
//			BILL_KEY_DESC_TEXT + " text, " +
//			BILL_KEY_AMOUNT_TEXT + " text, " +
//			BILL_KEY_DUE_DATE_TEXT +	" text);";
	
//	public static final String DATABASE_CREATE = "CREATE TABLE bill_table (_id integer primary key autoincrement, bill_type_text text, bill_desc_text text, bill_ amount_text text, bill_due_date_text text);";
	public static final String DATABASE_CREATE = "CREATE TABLE bill_table (_id integer primary key autoincrement, bill_type_text text);";
	
	/** SQLite database table removal statement. Only used if upgrading database. */
	public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE_BILL;
	
	/**
	 * Initializes the database.
	 * 
	 * @param database
	 * 				The database to initialize.	
	 */
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	/**
	 * Upgrades the database to a new version.
	 * 
	 * @param database
	 * 					The database to upgrade.
	 * @param oldVersion
	 * 					The old version of the database.
	 * @param newVersion
	 * 					The new version of the database.
	 */
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(BillTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion);
		database.execSQL(DATABASE_DROP);
		
		onCreate(database);

	}
	
}

