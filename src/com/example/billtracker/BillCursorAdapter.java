package com.example.billtracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.billtracker.BillView.OnBillChangeListener;

/**
 * A class that functions similarly to BillListAdapter, but instead uses a Cursor.
 * A Cursor is a list of rows from a database that acts as a medium between the database and
 * a ViewGroup (in this case, a SQLite database table containing rows of bills and
 * a ListView containing BillViews).
 * 
 * @author Josiah
 *
 */
public class BillCursorAdapter extends CursorAdapter {
	
	/** The OnBillChangeListener that should be connected to each of the
	 * BillViews created/managed by this Adapter. */
	 private OnBillChangeListener m_listener;

	public BillCursorAdapter(Context context, Cursor billCursor, int flags) {
		super(context, billCursor, flags);
		this.m_listener = null;
	}

	public void setOnBillChangeListener(OnBillChangeListener mListener) {
		this.m_listener = mListener;
	}
	/**
	 * Binds an existing view to an underlying data object ( in this case, a BillView to
	 * a Bill obtained from the row from a Cursor).  The Cursor is assumed to already
	 * point to the correct row.
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		Bill bill = new Bill(cursor.getLong(BillTable.BILL_COL_ID),
				cursor.getString(BillTable.BILL_COL_TYPE_TEXT));
		((BillView)view).setOnBillChangeListener(null);
		((BillView)view).setBill(bill);
		((BillView)view).setOnBillChangeListener(this.m_listener);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {		
		Bill bill = new Bill(cursor.getLong(BillTable.BILL_COL_ID),
				cursor.getString(BillTable.BILL_COL_TYPE_TEXT));
		BillView billView = new BillView(context, bill);
		billView.setOnBillChangeListener(this.m_listener);
		return billView;
	}
	
}
