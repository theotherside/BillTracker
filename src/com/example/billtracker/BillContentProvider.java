package com.example.billtracker;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class BillContentProvider extends ContentProvider {

	/** The Bill database. */
	private BillDatabaseHelper database;

	/** Values for the URIMatcher. */
	private static final int BILL_ID = 1;

	/** The authority for this content provider. */
	private static final String AUTHORITY = "com.example.billtracker.contentprovider";

	/**
	 * The database table to read from and write to, and also the root path for
	 * use in the URI matcher. This is essentially a label to a two-dimensional
	 * array in the database filled with rows of bills whose columns contain
	 * bill data.
	 */
	private static final String BASE_PATH = "bill_table";

	/**
	 * This provider's content location. Used by accessing applications to
	 * interact with this provider.
	 */
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	/**
	 * Matches content URIs requested by accessing applications with possible
	 * expected content URI formats to take specific actions in this provider.
	 */
	private static final UriMatcher sURI_MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/bills/#", BILL_ID);
	}

	@Override
	public boolean onCreate() {
		this.database = new BillDatabaseHelper(getContext(),
				BillDatabaseHelper.DATABASE_NAME, null,
				BillDatabaseHelper.DATABASE_VERSION);
		return false;
	}

	/**
	 * Removes a row from the bill table. Given a specific URI containing a bill
	 * ID, removes rows in the table that match the ID and returns the number of
	 * rows removed. Since IDs are automatically incremented on insertion, this
	 * will only ever remove a single row from the bill table.<br>
	 * <br>
	 * 
	 * Overrides the built-in version of <b>delete(...)</b> provided by
	 * ContentProvider.<br>
	 * <br>
	 * 
	 * */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		/** Open the database for writing. Deletion is a write operation. */
		SQLiteDatabase sqlDB = this.database.getWritableDatabase();

		/** Keep track of the number of deleted rows for the return value. */
		int rowsDeleted = 0;

		/** Match the passed-in URI to an expected URI format. */
		int uriType = sURI_MATCHER.match(uri);
		switch (uriType) {

		/** Remove a row from the grocery item table with the matching ID. */
		case BILL_ID:

			String id = uri.getLastPathSegment();

			/** Perform the actual deletion in the table. */
			rowsDeleted = sqlDB.delete(BillTable.DATABASE_TABLE_BILL,
					BillTable.BILL_KEY_ID + "=" + id, null);

			break;

		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);

		}

		if (rowsDeleted > 0) {

			/**
			 * Alert any watchers of an underlying data change for content/view
			 * refreshing.
			 */
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return rowsDeleted;
	}

	/** We don't really care about this method for this application. */
	@Override
	public String getType(Uri uri) {
		return null;
	}

	/**
	 * Inserts a bill into the bill table. Given a specific URI that contains a
	 * bill and the values of that bill, writes a new row in the table filled
	 * with that bill's information and gives the bill a new ID, then returns a
	 * URI containing the ID of the inserted bill.<br>
	 * <br>
	 * 
	 * Overrides the built-in version of <b>insert(...)</b> provided by
	 * ContentProvider.<br>
	 * <br>
	 * 
	 * */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		/** Open the database for writing. */
		SQLiteDatabase sqlDB = this.database.getWritableDatabase();

		/** Will contain the ID of the inserted grocery item. */
		long id = 0;

		/** Match the passed-in URI to an expected URI format. */
		int uriType = sURI_MATCHER.match(uri);

		switch (uriType) {

		/**
		 * Expects a bill ID, but we will do nothing with the passed-in ID since
		 * the database will automatically handle ID assignment and
		 * incrementation. IMPORTANT: bill ID cannot be set to -1 in passed-in
		 * URI; -1 is not interpreted as a numerical value by the URIMatcher.
		 */
		case BILL_ID:
			id = sqlDB.insert(BillTable.DATABASE_TABLE_BILL, null, values);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		/**
		 * Alert any watchers of an underlying data change for content/view
		 * refreshing.
		 */
		getContext().getContentResolver().notifyChange(uri, null);

		return Uri.parse(BASE_PATH + "/" + id);
	}

	/**
	 * Fetches rows from the bill table. Given a specified URI that contains a
	 * filter, returns a list of bills from the bill table matching that filter
	 * in the form of a Cursor.<br>
	 * <br>
	 * 
	 * Overrides the built-in version of <b>query(...)</b> provided by
	 * ContentProvider.<br>
	 * <br>
	 * 
	 * */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		/** Use a helper class to perform a query for us. */
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		/** Make sure the projection is proper before querying. */
		checkColumns(projection);

		/** Set up helper to query our grocery items table. */
		queryBuilder.setTables(BillTable.DATABASE_TABLE_BILL);

		/** Perform the database query. */
		SQLiteDatabase db = this.database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, null,
				null, null, null);

		/**
		 * Set the cursor to automatically alert listeners for content/view
		 * refreshing.
		 */
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	/**
	 * Updates a row in the grocery item table. Given a specific URI containing
	 * a grocery item ID and the new grocery item values, updates the values in
	 * the row with the matching ID in the table. Since IDs are automatically
	 * incremented on insertion, this will only ever update a single row in the
	 * grocery item table.<br>
	 * <br>
	 * 
	 * Overrides the built-in version of <b>update(...)</b> provided by
	 * ContentProvider.<br>
	 * <br>
	 * 
	 * */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		/** Open the database for writing. */
		SQLiteDatabase sqlDB = database.getWritableDatabase();

		/** Keep track of the number of updated rows for the return value. */
		int rowsUpdated = 0;

		/** Match the passed-in URI to an expected URI format. */
		int uriType = sURI_MATCHER.match(uri);

		switch (uriType) {
		/** Update a row in the grocery item table with the matching ID. */
		case BILL_ID:
			String id = uri.getLastPathSegment();
			if (!TextUtils.isEmpty(selection)) {
				/** Perform the actual update in the table. */
				rowsUpdated = sqlDB.update(BillTable.DATABASE_TABLE_BILL,
						values, BillTable.BILL_KEY_ID + "=" + id + " AND "
								+ selection, null);
			} else {
				/** Perform the actual update in the table. */
				rowsUpdated = sqlDB.update(BillTable.DATABASE_TABLE_BILL,
						values, BillTable.BILL_KEY_ID + "=" + id, null);
			}

			break;

		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		if (rowsUpdated > 0) {

			/**
			 * Alert any watchers of an underlying data change for content/view
			 * refreshing.
			 */
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return rowsUpdated;
	}

	/**
	 * Verifies the correct set of columns to return data from when performing a
	 * query.
	 * 
	 * @param projection
	 *            The set of columns about to be queried.
	 */
	private void checkColumns(String[] projection) {
//		String[] available = { BillTable.BILL_KEY_ID, 
//										  BillTable.BILL_KEY_TYPE_TEXT, 
//										  BillTable.BILL_KEY_DESC_TEXT, 
//										  BillTable.BILL_KEY_AMOUNT_TEXT, 
//										  BillTable.BILL_KEY_DUE_DATE_TEXT };
		String[] available = { BillTable.BILL_KEY_ID, 
				  BillTable.BILL_KEY_TYPE_TEXT};

		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(
					Arrays.asList(available));

			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException(
						"Unknown columns in projection");
			}
		}
	}
}
