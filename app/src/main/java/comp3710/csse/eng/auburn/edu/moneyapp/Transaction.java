package comp3710.csse.eng.auburn.edu.moneyapp;

public class Transaction {

	//private variables
	int _id;

	String _date;
	String _name;
	int _amount;
	String _category;
	String _type;

	// Empty constructor
	public Transaction(){
	}

	public Transaction(int id, String date, String name, int amount, String category, String type) {
		this._id = id;
		this._date = date;
		this._name = name;
		this._amount = amount;
		this._category = category;
		this._type = type;
	}

	public Transaction(String date, String name, int amount, String category, String type) {
		this._date = date;
		this._name = name;
		this._amount = amount;
		this._category = category;
		this._type = type;
	}

	public int getId() { return this._id; }
	public void setId(int id) { this._id = id; }

	public String getDate() { return this._date; }
	public void setDate(String date) { this._date = date; }

	public String getName() { return this._name; }
	public void setName(String name) { this._name = name; }

	public int getAmount() { return this._amount; }
	public void setAmount(int amount) { this._amount = amount; }

	public String getCategory() { return this._category; }
	public void setCategory(String category) { this._category = category; }

	public String getType() { return this._type; }
	public void setType(String type) { this._type = type; }
}