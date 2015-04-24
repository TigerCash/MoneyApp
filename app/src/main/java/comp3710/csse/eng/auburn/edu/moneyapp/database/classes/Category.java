package comp3710.csse.eng.auburn.edu.moneyapp.database.classes;


public class Category {

	private String _name;

	public Category() {};

	public Category(String _name) {
		this._name = _name;
	}


	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}


	public String toString() {
		return this._name;
	}
}