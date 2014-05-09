package com.example.billtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BillView extends LinearLayout implements OnCheckedChangeListener {
	
	/** Displays the bill category text. */
	private TextView m_vwBillText;

	/** String to match the date with the current date. */
	protected String billDate;
	
	/** The TextView which contains the Title Date, used to compare in our db. */
	protected TextView billDateTextView;
	
	/** The delete Bill button at the bottom of the Add Bill activity. */
	protected ImageButton deleteButton;
	
	/** The data version of this View, containing the bill's information. */
	private Bill m_bill;

	/** The Bill database. */
	private BillDatabaseHelper database;

	
	/** Interface between the BillView and the database it's stored in. */
	private OnBillChangeListener m_onBillChangeListener;
	
	public BillView (Context context, Bill bill) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_bill_item_holder, this, true);
		this.m_vwBillText = (TextView)findViewById(R.id.bill_holder_text_view);
		
		this.setBill(bill);
		
		this.deleteButton = (ImageButton)findViewById(R.id.btn_delete);
		this.deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				removeBill();
				
			}
		});
		
		this.m_onBillChangeListener = null;
	}
	
	
	/**
	 * Mutator method for changing Bill object this View displays.  This View will
	 * be updated to display the correct contents of the new Bill.
	 * @param bill
	 */
	public void setBill (Bill bill) {
		this.m_bill = bill;
		this.m_vwBillText.setText(m_bill.getBill());
		
	}
	
	public Bill getBill() {
		return this.m_bill;
	}
	
	public void setOnBillChangeListener(OnBillChangeListener listener) {
		this.m_onBillChangeListener = listener;
	}
	
	protected void notifyOnBillChangeListener() {
		if (m_onBillChangeListener != null) {
			m_onBillChangeListener.onBillChanged(this, m_bill);
		}
	}
	
	public static interface OnBillChangeListener {
		
		/**
		 * Called when the underlying Bill in a BillView object changes state.
		 * 
		 */
		public void onBillChanged(BillView view, Bill bill);
	}


	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		/** Do nothing. */
	}
	private void removeBill() {
		TextView billData = (TextView) findViewById(R.id.bill_holder_text_view);
		String billText = billData.getText().toString();

		/** Open the database for writing. Deletion is a write operation. */
		SQLiteDatabase sqlDB = this.database.getWritableDatabase();
		
		String sqlDelete = "DELETE FROM" + BillTable.DATABASE_TABLE_BILL + " WHERE bill_type_text = " + billText;
		
		sqlDB.execSQL(sqlDelete);
	}
}
