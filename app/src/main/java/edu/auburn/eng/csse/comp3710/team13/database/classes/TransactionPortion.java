package edu.auburn.eng.csse.comp3710.team13.database.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TransactionPortion implements Parcelable {

	private int _id;

	private String _description;
	private String _amount;
	private int _category_id;
	private int _transaction_id;


	public TransactionPortion(){
	}

	public TransactionPortion(int id, String description, String amount, int category_id, int transaction_id) {
		this._id = id;
		this._description = description;
		this._amount = amount;
		this._category_id = category_id;
		this._transaction_id = transaction_id;
	}

	public TransactionPortion(String description, String amount, int category_id, int transaction_id) {
		this._description = description;
		this._amount = amount;
		this._category_id = category_id;
		this._transaction_id = transaction_id;
	}


	public int getId() {
		return _id;
	}

	public void setId(int _id) {
		this._id = _id;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String _description) {
		this._description = _description;
	}

	public String getAmount() {
		return _amount;
	}

	public void setAmount(String _amount) {
		NumberFormat formatter = new DecimalFormat("#0.00");
		this._amount = formatter.format(Double.parseDouble(_amount));
	}

	public int getCategoryId() {
		return _category_id;
	}

	public void setCategoryId(int _category_id) {
		this._category_id = _category_id;
	}

	public int getTransactionId() {
		return _transaction_id;
	}

	public void setTransactionId(int _transaction_id) {
		this._transaction_id = _transaction_id;
	}


	/*public static final String COMPLETE = "complete";
	public static final String PARTIALLY_COMPLETE = "partial";
	public static final String INCOMPLETE = "incomplete";

	public ArrayList<String> isComplete() {
		String isComplete = COMPLETE;

		ArrayList<String> attributeStatus = new ArrayList<>();

		if (this._name == null || this._name.equals("")) {
			isComplete = INCOMPLETE;
			attributeStatus.add("Name");
		}
		if (this._amount == null || this.equals("")) {
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


		if (_description == null || _description.equals("")) {
			valid = false;
			values[1] = "Description Invalid";
		}
		else if (_amount == null || _amount.equals("")) {
			valid = false;
			values[1] = "Amount Invalid";
		}
		else if (_category_id == 0) {
			valid = false;
			values[1] = "Category Invalid";
		}

		values[0] = valid;

		return values;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		TransactionPortion other = (TransactionPortion) obj;
		if (_id != other._id)
			return false;
		if (!_description.equals(other._description))
			return false;
		if (!_amount.equals(other._amount))
			return false;
		if (_category_id != other._category_id)
			return false;
		if (_transaction_id != other._transaction_id)
			return true;

		return true;
	}

	@Override
	public String toString() {
		return "\nid: " + _id
				+ "\nDesc: " + _description
				+ "\nAmt: " + _amount
				+ "\nCatId: " + _category_id
				+ "\nTransId: " + _transaction_id;
	}



	// Parcelable Methods
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(_id);
		out.writeString(_description);
		out.writeString(_amount);
		out.writeInt(_category_id);
		out.writeInt(_transaction_id);
	}

	public static final Parcelable.Creator<TransactionPortion> CREATOR
			= new Parcelable.Creator<TransactionPortion>() {
		public TransactionPortion createFromParcel(Parcel in) {
			return new TransactionPortion(in);
		}

		public TransactionPortion[] newArray(int size) {
			return new TransactionPortion[size];
		}
	};

	private TransactionPortion(Parcel in) {
		_id = in.readInt();
		_description = in.readString();
		_amount = in.readString();
		_category_id = in.readInt();
		_transaction_id = in.readInt();
	}

}
