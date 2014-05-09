package com.example.billtracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarActivity extends Activity {

	private static final int START_VIEW = 1010;

	/** The main CalendarView on app startup */
	protected CalendarView cal;
	
	/** TextView used for maintaining a list of Views that each display Bills. */
	protected TextView m_vwBillLayout;
	
	/** Adapter used to bind an AdapterView to the List of Bills. */
	protected BillCursorAdapter m_billAdapter;

	private int year;

	private int month;

	private int dayOfMonth;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_calendar);
		
		Toast.makeText(getBaseContext(), "Tap a date to modify bills",
				Toast.LENGTH_LONG).show();

		cal = (CalendarView) findViewById(R.id.calendar_main);

		cal.setOnDateChangeListener(new OnDateChangeListener() {

			@Override
			public void onSelectedDayChange(CalendarView view, int year,
					int month, int dayOfMonth) {
				
				initLayout(view, year, month, dayOfMonth);
				finish();
			}
		});

	}
	
	/**
	 * Method used to encapsulate the code that initializes and sets
	 * the Layout for this Activity.
	 */
	protected void initLayout(CalendarView view, int year, int month, int dayOfMonth) {
		
		this.year = year;
		this.month = month;
		this.dayOfMonth = dayOfMonth;
		startViewBill();
		
		this.m_vwBillLayout = (TextView)this.findViewById(R.id.bill_list_view);
		
		/** Set the ListView's adapter to the BillCursorAdapter. */
		//this.m_vwBillLayout.setAdapter(m_billAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);

		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == START_VIEW && resultCode == RESULT_OK ) {
			year = data.getExtras().getInt("year");
			month = data.getExtras().getInt("month");
			dayOfMonth = data.getExtras().getInt("dayOfMonth");
		}
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	
	private void startViewBill() {
		Intent launchViewBill = new Intent(this, BillViewActivity.class);
		
		/**
		 * Attaching the date information to the intent.
		 */
		launchViewBill.putExtra("year", year);
		launchViewBill.putExtra("month", month);
		launchViewBill.putExtra("dayOfMonth", dayOfMonth);
		
		startActivityForResult(launchViewBill, START_VIEW);
	}
}
