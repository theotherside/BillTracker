package com.example.billtracker;

/**
 * Bill POJO for encapsulating the data pertaining to a Bill
 * @author Josiah Mortenson
 *
 */
public class Bill {

	/**
	 * Contains the unique ID for this Bill.
	 */
	public long m_nBillID;
	
	/**
	 * Contains the type of this Bill.
	 */
	public String m_strBillType;
	
	/**
	 * Contains the (optional) description of this Bill.
	 */
	public String m_strBillDesc;
	
	/**
	 * Contains the dollar amount related to this Bill.
	 */
	public String m_strBillAmount;
	
	/**
	 * Contains the date that this Bill is due.  (Will be an integer between 1-31)
	 */
	public String m_strBillDate;
	
	/**
	 * Initializes an empty bill.
	 */
	public Bill() {
		this.m_strBillType = "";
		this.m_nBillID = 0;
		this.m_strBillAmount = "";
		this.m_strBillDate = "";
		this.m_strBillDesc = "";
		
	}
	
	/**
	 * Initializes a bill passed in.
	 * 
	 * This constructor should be used when instantiating a bill
	 * from the database.
	 * 
	 * @param strBillType
	 * 			Bill String representing what type of bill this is.
	 * 
	 */
	public Bill(String strBillType) {
		this.m_strBillType = strBillType;
		this.m_nBillID = 0;
		this.m_strBillAmount = "";
		this.m_strBillDate = "";
		this.m_strBillDesc = "";
	}
	
	/**
	 * Initializes a bill with all data except for description passed in.
	 * 
	 * This constructor should be used when instantiating a bill
	 * from the database.
	 * 
	 * @param strBillType
	 * 			Bill String representing what type of bill this is.
	 * 
	 * @param id
	 * 			Long representing the unique ID of the bill.
	 * 
	 * @param strBillAmount
	 * 			Long representing the amount that this bill is due for.
	 * 
	 * @paramstrBillDate
	 * 			Integer representing what day of the month this bill is due.
	 * 
	 */
	public Bill(long id, String strBillType, String strBillAmount, String strBillDate) {
		this.m_strBillType = strBillType;
		this.m_nBillID = id ;
		this.m_strBillAmount = strBillAmount;
		this.m_strBillDate = strBillDate;
		this.m_strBillDesc = "";
	}
	
	/**
	 * Initializes a bill with all data except for description passed in.
	 * 
	 * FOR TESTING
	 * 
	 * This constructor should be used when instantiating a bill
	 * from the database.
	 * 
	 * @param strBillType
	 * 			Bill String representing what type of bill this is.
	 * 
	 * @param id
	 * 			Long representing the unique ID of the bill.
	 * 
	 */
	public Bill(long id, String strBillType) {
		this.m_strBillType = strBillType;
		this.m_nBillID = id ;
		this.m_strBillAmount = "";
		this.m_strBillDate = "";
		this.m_strBillDesc = "";
	}
	
	/**
	 * Initializes a bill with all data passed in, including description
	 * 
	 * This constructor should be used when instantiating a bill from
	 * the database.
	 * 
	 * @param strBillType
	 * 			Bill String representing what type of bill this is.
	 * 
	 * @param id
	 * 			Long representing the unique ID of the bill.
	 * 
	 * @param strBillAmount
	 * 			Long representing the amount that this bill is due for.
	 * 
	 * @param strBillDate
	 * 			Integer representing what day of the month this bill is due.
	 * 
	 * @param strBillDesc
	 * 			String representing the [optional] description of this bill.
	 */

	public Bill(long id, String strBillType,  String strBillDesc, String strBillAmount, String strBillDate) {
		this.m_strBillType = strBillType;
		this.m_nBillID = id ;
		this.m_strBillAmount = strBillAmount;
		this.m_strBillDate = strBillDate;
		this.m_strBillDesc = strBillDesc;
	}
	/**
	 * Initializes a bill with all data passed in, including description
	 * 
	 * This constructor should be used when instantiating a bill from
	 * the database.
	 * 
	 * @param strBillType
	 * 			Bill String representing what type of bill this is.
	 * 
	 * @param id
	 * 			Long representing the unique ID of the bill.
	 * 
	 * @param strBillAmount
	 * 			Long representing the amount that this bill is due for.
	 * 
	 * @param strBillDate
	 * 			Integer representing what day of the month this bill is due.
	 * 
	 * @param strBillDesc
	 * 			String representing the [optional] description of this bill.
	 */

	public Bill(String strBillType, String strBillDesc, String strBillAmount, String strBillDate) {
		this.m_strBillType = strBillType;
		this.m_nBillID = 0;
		this.m_strBillAmount = strBillAmount;
		this.m_strBillDate = strBillDate;
		this.m_strBillDesc = strBillDesc;
	}
	// Getters and Setters
	public String getBill() {
		return this.m_strBillType;
	}
	
	public void setBill(String strBill) {
		this.m_strBillType = strBill;
	}
	
	public long getID() {
		return this.m_nBillID;
	}

	public void setID(long nBillID) {
		this.m_nBillID = nBillID;
	}

	public String getBillType() {
		return this.m_strBillType;
	}

	public void setBillType(String strBillType) {
		this.m_strBillType = strBillType;
	}

	public String getBillDesc() {
		return m_strBillDesc;
	}

	public void setBillDesc(String strBillDesc) {
		this.m_strBillDesc = strBillDesc;
	}

	public String getBillAmount() {
		return m_strBillAmount;
	}

	public void setBillAmount(String nBillAmount) {
		this.m_strBillAmount = nBillAmount;
	}

	public String getBillDate() {
		return m_strBillDate;
	}

	public void setBillDate(String strBillDate) {
		this.m_strBillDate = strBillDate;
	}
	
}
