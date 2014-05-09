package com.example.billtracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.billtracker.BillView.OnBillChangeListener;

public class BillViewActivity extends SherlockFragmentActivity implements OnBillChangeListener, LoaderCallbacks<Cursor> {

	/** Request codes for data transfer, should only need one of these.
	 * Data will only be transfered from the Add bill page to the view bill page. */
//	private static final int VIEW_BILL_TO_ADD_BILL = 1010;
//	private static final int ADD_BILL_TO_VIEW_BILL = 2020;
	
	/** The Add Bill button at the bottom of the Add Bill activity. */
	protected ImageButton addButton;

	/** Adapter used to bind an AdapterView to the List of Bills. */
	protected BillCursorAdapter m_billCursorAdapter;

	/** ViewGroup that is responsible for holding and displaying the list of Bill items. */
	protected ListView m_vwBillLayout;
	
	/**
	 * The ID of the CursorLoader to be initialized in the LoaderManager and
	 * used to load a Cursor.
	 */
	
	private static final int LOADER_ID = 1;

	int selectedYear;
	int selectedMonth;
	int selectedDayOfMonth;
	
	/** Date of the clicked CalendarView day */
	protected TextView date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initLayout();

		/** Create a new adapter. */
		this.m_billCursorAdapter = new BillCursorAdapter(this, null, 0);
		this.m_billCursorAdapter.setOnBillChangeListener(this);
		
		this.getSupportLoaderManager().initLoader(LOADER_ID, null, this);
		
		date = (TextView) findViewById(R.id.view_date);
		
		// reading information passed to this activity
		// Get the intent that started this activity
		Intent intent = getIntent();
		
		// returns 0000 if not initialized by activity, should always be initialized
		selectedYear = intent.getIntExtra("year", 0000);
		selectedMonth = intent.getIntExtra("month", 00);
		selectedDayOfMonth =  intent.getIntExtra("dayOfMonth", 00);
	
		date.setText((selectedMonth + 1)  + "/" + selectedDayOfMonth + "/" + selectedYear);
		
		date.setGravity(Gravity.CENTER);

		this.addButton = (ImageButton)findViewById(R.id.btn_add_bill);

		this.addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startAddBill();
				
			}
		});
		
		
	}
	protected void initLayout() {
		setContentView(R.layout.view_bill);
		this.m_vwBillLayout = (ListView)this.findViewById(R.id.bill_list_view);
		this.m_vwBillLayout.setAdapter(m_billCursorAdapter);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2){
			if (null!=data){
				String billTypeText = data.getStringExtra("BILLTYPE");
				String billDescText = data.getStringExtra("BILLDESC");
				String billAmountText = data.getStringExtra("BILLAMOUNT");
				String billDueDateText = data.getStringExtra("BILLDUEDATE");

				addBill(new Bill(billTypeText, billDescText, billAmountText, billDueDateText));


			}
		}
	}
	

	public void startAddBill() {
		Intent launchAddBill = new Intent(this, BillAddActivity.class);
		/**
		 * Attaching the date information to the intent.
		 */
		launchAddBill.putExtra("selectedYear", selectedYear);
		launchAddBill.putExtra("selectedMonth", selectedMonth);
		launchAddBill.putExtra("selectedDayOfMonth", selectedDayOfMonth);
		
		startActivityForResult(launchAddBill, 2);
	}	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			returnToCalendar();
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	private void returnToCalendar() {
		Intent launchCalendar = new Intent(this, CalendarActivity.class);
		
		startActivity(launchCalendar);
		finish();
	}	
	
	private void fillData() {
		this.getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
		this.m_vwBillLayout.setAdapter(m_billCursorAdapter);
	}


	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Change out the Cursor currently attached to the adapter and replace
		// it with the newly initialized one.
		this.m_billCursorAdapter.swapCursor(cursor);
		this.m_billCursorAdapter.setOnBillChangeListener(this);
	}

	/**
	 * Handles the creation and initializes the automatic maintenance of a
	 * CursorLoader, which will automatically handle the Cursor. This method
	 * guarantees that onLoadFinished(..) will be called afterwards with the
	 * loaded Cursor.
	 */
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// Create a list of column names to use as a filter for queries that the
		// Cursor will make.
		String[] projection = { BillTable.BILL_KEY_ID, BillTable.BILL_KEY_TYPE_TEXT };

		// Create a URI for initial Cursor querying after the CursorLoader
		// finishes loading it.
		Uri uri = Uri.parse(BillContentProvider.CONTENT_URI + "/bills/" + id);

		// Instantiate the CursorLoader.
		CursorLoader cursorLoader = new CursorLoader(this, uri, projection, null, null, null);
		return cursorLoader;
	}

	/**
	 * Called when the designated Loader (CursorLoader in this case) needs to be
	 * restarted. Used to destroy references to the loaded object, making the
	 * data unavailable while the loader is being reset.
	 * 
	 * Specified by onLoaderReset(...) in LoaderManager.LoaderCallbacks.
	 */
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		this.m_billCursorAdapter.swapCursor(null);

	}

	@Override
	public void onBillChanged(BillView view, Bill bill) {
		/** Set up the URI to indicate which bill needs changing. */
		Uri uri = Uri.parse(BillContentProvider.CONTENT_URI + "/bills/" + bill.getID());

		/** Prepare all of the new Bill values. */
		ContentValues cv = new ContentValues();
		cv.put(BillTable.BILL_KEY_TYPE_TEXT, bill.getBillAmount());
		
		/** Perform the actual update in the bill table. */
		getContentResolver().update(uri, cv, null, null);
		
		this.m_billCursorAdapter.setOnBillChangeListener(null);
		fillData();
	}
	
	private void addBill(Bill bill) {
		Uri uri = Uri.parse(BillContentProvider.CONTENT_URI + "/bills/"
				+ bill.getID());

		ContentValues cv = new ContentValues();

		cv.put(BillTable.BILL_KEY_TYPE_TEXT, bill.getBill() + ", Amount = $" + bill.getBillAmount() + ", Due on: " + bill.getBillDate());
		
		uri = getContentResolver().insert(uri, cv);
		int id = Integer.parseInt(uri.getLastPathSegment());
		bill.setID(id);
		
		fillData();
		
	}	
}
