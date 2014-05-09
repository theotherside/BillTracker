package com.example.billtracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.billtracker.BillView.OnBillChangeListener;

/**
 * The Activity that displays a list of Bills that may be removed or edited from the list. <br><br>
 * 
 * Extends SherlockFragmentActivity class to provide LoaderManager functionality.<br><br>
 * 
 * 
 * @author Josiah
 *
 */
public class BillListActivity extends SherlockFragmentActivity implements OnBillChangeListener, LoaderCallbacks<Cursor>{

	
	/** ViewGroup used for maintaining a list of Views that each display Bills and Bill Information. */
	protected TextView m_vwBillLayout;
	
	protected EditText m_vwBillItemEditText;

	protected Button m_vwBillItemButton;

	protected Menu m_vmMenu;

	protected static final String SAVED_EDIT_TEXT = "m_vwBillItemEditText";

	protected long m_nID;

	protected static final String SAVED_BILL_ITEM_ID = "m_nID";

	private static final int LOADER_ID = 1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//TODO Initialize Layouts here
		
		
		/** Initialize the LoaderManager, causing it to set up a CursorLoader. */
		this.getSupportLoaderManager().initLoader(LOADER_ID, null, this);
		
		/** Initialize the data obtained from BillAddActivity. */
		//TODO
		
		
	}

	/**
	 * Method used for encapsulating the code that initializes and sets the
	 * Layout for this Activity.
	 */
	protected void initLayout() {
		this.setContentView(R.layout.view_bill);
		this.m_vwBillLayout = (TextView)this.findViewById(R.id.bill_list_view);
		
		/** Set the ListView's adapter to the BillCursorAdapter. */
		//TODO
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBillChanged(BillView view, Bill bill) {
		// TODO Auto-generated method stub
		
	}
	
	/**  */
}
