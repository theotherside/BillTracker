package com.example.billtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class BillAddActivity extends SherlockFragmentActivity {

	/** Information pertaining to the selected date. */
	int selectedYear;
	int selectedMonth;
	int selectedDayOfMonth;
	
	String billTypeText;
	String billDescText;
	String billAmountText;
	String billDueDateText;
	
	// Will use these later for result checking
	//private static final int VIEW_BILL_TO_ADD_BILL = 1010;
	//private static final int ADD_BILL_TO_VIEW_BILL = 2020;

	/** Save button */
	protected Button btnSave;

	/** Cancel button. */
	protected Button btnCancel;

	/** To pre-populate the EditText for Due date */
	protected EditText date;

	/** Spinner for BillType. */
	protected Spinner m_vwBillTypeSpinner;

	/** EditText for BillDesc */
	protected EditText m_vwBillDesc;

	/** EditText for BillAmount */
	protected EditText m_vwBillAmount;

	/** EditText for BillDueDate */
	protected EditText m_vwBillDueDate;

	/** Formated date using the passed month/year/dayofmonth values. */
	protected String strFormatedDate;

	/**
	 * Keys used for storing and retrieving the text in the various text fields.
	 */
	protected static final String SAVED_BILL_TYPE_TEXT = "m_vwBillTypeSpinner";

	protected static final String SAVED_BILL_DESC_TEXT = "m_vwBillDesc";

	protected static final String SAVED_BILL_AMOUNT_TEXT = "m_vwBillAmount";

	protected static final String SAVED_BILL_DUE_DATE_INT = "m_vwBillDueDate";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_bill);

		// reading information passed to this activity...
		// Get the intent that started this activity
		Intent intent = getIntent();

		// returns 0000 if not initialized by activity, should always be
		// initialized
		selectedYear = intent.getIntExtra("selectedYear", 0000);
		selectedMonth = intent.getIntExtra("selectedMonth", 00);
		selectedDayOfMonth = intent.getIntExtra("selectedDayOfMonth", 00);

		strFormatedDate = (selectedMonth + 1) + "/" + selectedDayOfMonth + "/"
				+ selectedYear;

		date = (EditText) findViewById(R.id.bill_due_date_text);

		date.setText(strFormatedDate);

		// Reference the Form fields and buttons
		this.m_vwBillTypeSpinner = (Spinner) this.findViewById(R.id.bill_type_spinner);
		this.m_vwBillDesc = (EditText) this.findViewById(R.id.bill_desc_edit_text);
		this.m_vwBillAmount = (EditText) this.findViewById(R.id.bill_amount_edit_text);
		this.m_vwBillDueDate = (EditText) this.findViewById(R.id.bill_due_date_text);
		this.btnSave = (Button) this.findViewById(R.id.btn_add_bill_confirm);
		this.btnCancel = (Button) findViewById(R.id.btn_add_bill_cancel);
		
		// Code for canceling, which will simply bring the user back to
		// the previous activity (BillViewActivity).

		initAddBillListeners();
		Log.w("DOES DB EXIST", "CHECKDATABASE RETURNS: " + checkDataBase());
		this.btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnToViewBill();
			}
		});

	}

	/**
	 * Method used to encapsulate the code that initializes and sets the Event
	 * Listeners which will respond to requests to "Add" a new Bill to the list.
	 */
	protected void initAddBillListeners() {
		this.btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (billDescText != null) {
					m_vwBillDesc.setText("");
				}
				
				if (billAmountText != null && !m_vwBillAmount.equals("")) {
					m_vwBillAmount.setText("");
				}
				
				if (billDueDateText != null && !m_vwBillDueDate.equals("")) {
					m_vwBillDueDate.setText("");
				}
				
				// Hide Soft Keyboard if it isn't already hidden, just in case ;)
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(m_vwBillDesc.getWindowToken(), 0);
				

				returnDataToViewBill();
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();

		/** Preserve the text in the EditText's entry field. */
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		prefs.edit()
				.putString(SAVED_BILL_TYPE_TEXT,
						m_vwBillTypeSpinner.getSelectedItem().toString())
				.commit();
		prefs.edit()
				.putString(SAVED_BILL_DESC_TEXT,
						m_vwBillDesc.getText().toString()).commit();
		prefs.edit()
				.putString(SAVED_BILL_AMOUNT_TEXT,
						m_vwBillAmount.getText().toString()).commit();
		prefs.edit()
				.putString(SAVED_BILL_DUE_DATE_INT,
						m_vwBillDueDate.getText().toString()).commit();
	}

	public void returnDataToViewBill() {
		/**
		 * Retrieving and packaging up all the entered Bill info.
		 */
		String billTypeText = m_vwBillTypeSpinner.getSelectedItem().toString();
		String billDescText = m_vwBillDesc.getText().toString();
		String billAmountText = m_vwBillAmount.getText().toString();
		String billDueDateText = m_vwBillDueDate.getText().toString();
		
		Intent launchViewBill = new Intent();
		
		/**
		 * Attaching the bill info to the intent.
		 */
		launchViewBill.putExtra("BILLTYPE", billTypeText).toString();
		launchViewBill.putExtra("BILLDESC", billDescText).toString();
		launchViewBill.putExtra("BILLAMOUNT", billAmountText).toString();
		launchViewBill.putExtra("BILLDUEDATE", billDueDateText).toString();
		
		setResult(2, launchViewBill);
		finish();
	}	
	
	private void returnToViewBill() {
		Intent resumeViewBill = new Intent(this, BillViewActivity.class);
		startActivity(resumeViewBill);
		finish();
	}
	
	private boolean checkDataBase() {
	    SQLiteDatabase checkDB = null;
	    try {
	        checkDB = SQLiteDatabase.openDatabase(("/data/data/com.example.billtracker/databases/billdatabase.db"), null,
	                SQLiteDatabase.OPEN_READONLY);
	        checkDB.close();
	    } catch (SQLiteException e) {
	        // database doesn't exist yet.
	    }
	    return checkDB != null ? true : false;
	}
}
