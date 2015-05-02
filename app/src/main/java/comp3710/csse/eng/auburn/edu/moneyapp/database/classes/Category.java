package comp3710.csse.eng.auburn.edu.moneyapp.database.classes;


public class Category {

	private int _id;

	private String _name;

	public Category() {};

	public Category(int id, String _name) {
		this._id = id;
		this._name = _name;
	}

	public Category(String _name) {
		this._name = _name;
	}


	public int getId() {
		return _id;
	}

	public void setId(int _id) {
		this._id = _id;
	}


	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}


	public String toString() {
		return "\nId:" + this._id +
				"\nName: " + this._name;
	}
}