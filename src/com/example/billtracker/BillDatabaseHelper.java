package com.example.billtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class that hooks up to the BillContentProvider for initialization and
 * maintenance. Uses BillTable for assistance.
 */
public class BillDatabaseHelper extends SQLiteOpenHelper {
	
	/** Table name */
	public static final String DATABASE_TABLE_BILL = "bill_table";
	/** The name of the database. */
	public static final String DATABASE_NAME = "billdatabase.db";
	
	/** The starting database version. */
	public static final int DATABASE_VERSION = 8;
	
	/**
	 * Create a helper object to create, open, and/or manage a database.
	 * 
	 * @param context
	 * 					The application context.
	 * @param name
	 * 					The name of the database.
	 * @param factory
	 * 					Factory used to create a cursor. Set to null for default behavior.
	 * @param version
	 * 					The starting database version.
	 */
	public BillDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/** Create the actual database. */
	@Override
	public void onCreate(SQLiteDatabase db) {
		BillTable.onCreate(db);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		BillTable.onUpgrade(db, oldVersion, newVersion);
		db.execSQL("drop table if exists " + DATABASE_TABLE_BILL);
		onCreate(db);
		
	}
}
