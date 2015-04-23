package comp3710.csse.eng.auburn.edu.moneyapp.database.classes;

import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;

public class Transaction {

	private int _id;

	private String _date;
	private String _name;
	private int _amount;
	private Category _category;
	private String _type;

	public Transaction(){
	}

	public Transaction(int id, String date, String name, int amount, Category category, String type) {
		this._id = id;
		this._date = date;
		this._name = name;
		this._amount = amount;
		this._category = category;
		this._type = type;
	}

	public Transaction(String date, String name, int amount, Category category, String type) {
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

	public Category getCategory() { return this._category; }
	public void setCategory(Category category) { this._category = category; }

	public String getType() { return this._type; }
	public void setType(String type) { this._type = type; }
}