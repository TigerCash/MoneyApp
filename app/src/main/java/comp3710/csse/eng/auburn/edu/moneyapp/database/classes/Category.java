package comp3710.csse.eng.auburn.edu.moneyapp.database.classes;


public class Category {

	private String _name;

	public Category(String _name) {
		this._name = _name;
	}


	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String toString() {
		return this._name;
	}
}