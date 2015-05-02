package comp3710.csse.eng.auburn.edu.moneyapp.database.classes;

import java.util.ArrayList;


public class Transaction {

	private int _id;

	private String _date;
	private String _time;
	private String _name;
	private String _type;
	private ArrayList<TransactionPortion> _transaction_portions;


	public Transaction(){
	}

	public Transaction(int id, String date, String time, String name, String type, ArrayList<TransactionPortion> transactionPortions) {
		this._id = id;
		this._date = date;
		this._time = time;
		this._name = name;
		this._type = type;
		this._transaction_portions = transactionPortions;
	}

	public Transaction(String date, String time, String name, String type, ArrayList<TransactionPortion> transactionPortions) {
		this._date = date;
		this._time = time;
		this._name = name;
		this._type = type;
		this._transaction_portions = transactionPortions;
	}

	public int getId() { return this._id; }
	public void setId(int id) { this._id = id; }

	public String getDate() { return this._date; }
	public void setDate(String date) { this._date = date; }

	public String getTime() { return this._time; }
	public void setTime(String time) { this._time = time; }

	public String getName() { return this._name; }
	public void setName(String name) { this._name = name; }

	public String getType() { return this._type; }
	public void setType(String type) { this._type = type; }

	public ArrayList<TransactionPortion> getTransactionPortions() {
		return _transaction_portions;
	}

	public void setTransactionPortions(ArrayList<TransactionPortion> _transaction_portions) {
		this._transaction_portions = _transaction_portions;
	}

	public void addTransactionPortion(TransactionPortion transactionPortion) {
		this._transaction_portions.add(transactionPortion);
	}
/*
	public static final String COMPLETE = "complete";
	public static final String PARTIALLY_COMPLETE = "partial";
	public static final String INCOMPLETE = "incomplete";

	public ArrayList<String> isComplete() {
		String isComplete = COMPLETE;

		ArrayList<String> attributeStatus = new ArrayList<>();

		if (this._name == null || this._name.equals("")) {
			isComplete = INCOMPLETE;
			attributeStatus.add("Name");
		}
		if (this._amount == 0) {
			isComplete = INCOMPLETE;
			attributeStatus.add("Amount");
		}
		if (!isComplete.equals(INCOMPLETE))
		{
			if (this._date == null || this._date.equals(""))
			{
				isComplete = PARTIALLY_COMPLETE;
				attributeStatus.add("Date");
			}

			if (this._time == null || this._time.equals(""))
			{
				isComplete = PARTIALLY_COMPLETE;
				attributeStatus.add("Time");
			}
		}

		attributeStatus.add(0, isComplete);

		return attributeStatus;
	}*/

	@Override
	public String toString() {
		return "\nid: " + _id
			+ "\nDate: " + _date
			+ "\nTime: " + _time
			+ "\nName: " + _name
			+ "\nType: " + _type;
	}
}