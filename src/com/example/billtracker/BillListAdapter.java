package com.example.billtracker;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * This class binds the visual BillViews and the data behind them (Bills). 
 * 
 * @author Josiah
 *
 */

public class BillListAdapter extends BaseAdapter {

	/** The application Context in which this BillListAdapter is being used. */
	private Context m_context;
	
	/** The data set to which this BillListAdapter is bound. */
	private List<Bill> m_billItemList;
	
	/**
	 * Parameterized constructor that takes in the application context in which it is being used and the
	 * Collection of Bill objects to which it is bound.  m_nSelectedPosition will be initialized to 
	 * Adapter.NO_SELECTION
	 * 
	 * @param context
	 * 			The application Context in which this BillListAdapter is being used.
	 * 
	 * @param billItemList
	 * 			The Collection of Bill objects to which this BillListAdapter is bound.
	 */
	public BillListAdapter(Context context, List<Bill> billItemList) {
		this.m_context = context;
		this.m_billItemList = billItemList;
	}
	
	@Override
	public int getCount() {
		return this.m_billItemList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.m_billItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BillView billView = null;
		
		if (convertView == null) {
			billView = new BillView(m_context, this.m_billItemList.get(position));
		}
		else {
			billView = (BillView)convertView;
		}
		billView.setBill(this.m_billItemList.get(position));
		return billView;
	}

}
