package comp3710.csse.eng.auburn.edu.moneyapp.database.classes;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class Transaction implements Parcelable {

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
		if (this._transaction_portions == null)
			this._transaction_portions = new ArrayList<TransactionPortion>();
		this._transaction_portions.add(transactionPortion);
	}

	public String getTotal() {
		double total = 0;
		if (this._transaction_portions != null) {
			for (int i = 0; i < this._transaction_portions.size(); i++) {
				total += Double.parseDouble(this._transaction_portions.get(i).getAmount());
			}
		}
		if (_type.equals("Withdrawal")) {
			total *= -1;
		}

		NumberFormat formatter = new DecimalFormat("#0.00");

		String output = formatter.format(total);



		return output;
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

	public Object[] isValid() {
		Object[] values = new Object[2];

		boolean valid = true;

		ArrayList<TransactionPortion> transactionPortions = this.getTransactionPortions();

		if (transactionPortions == null || transactionPortions.size() == 0) {
			valid = false;
			values[1] = "No Transaction Portions";
		}
		else {
			for (int i = 0; i < transactionPortions.size(); i++) {
				if (!(boolean) (transactionPortions.get(i).isValid())[0]) {
					valid = false;
					values[1] = "Invalid Transaction Portion";
				}
			}
		}
		values[0] = valid;

		return values;
	}

	@Override
	public String toString() {
		return "\nid: " + _id
			+ "\nDate: " + _date
			+ "\nTime: " + _time
			+ "\nName: " + _name
			+ "\nType: " + _type;
	}


	// Parcelable Methods
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(_id);
		out.writeString(_date);
		out.writeString(_time);
		out.writeString(_name);
		out.writeString(_type);
	}

	public static final Parcelable.Creator<Transaction> CREATOR
			= new Parcelable.Creator<Transaction>() {
		public Transaction createFromParcel(Parcel in) {
			return new Transaction(in);
		}

		public Transaction[] newArray(int size) {
			return new Transaction[size];
		}
	};

	private Transaction(Parcel in) {
		_id = in.readInt();
		_date = in.readString();
		_time = in.readString();
		_name = in.readString();
		_type = in.readString();
	}
}