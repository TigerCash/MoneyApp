package comp3710.csse.eng.auburn.edu.moneyapp.database.classes;

import java.util.ArrayList;

public class Budget {

	private int _id;

	private String _start_date;
	private String _end_date;
	private int _amount;
	private ArrayList<Category> _categories;
	private ArrayList<Integer> _category_percentages;

	public Budget(){
	}

	public Budget(int id, String startDate, String endDate, int amount, ArrayList<Category> categories, ArrayList<Integer> categoryPercentages) {
		this._id = id;
		this._start_date = startDate;
		this._end_date = endDate;
		this._amount = amount;
		this._categories = categories;
		this._category_percentages = categoryPercentages;
	}

	public Budget(String startDate, String endDate, int amount, ArrayList<Category> categories, ArrayList<Integer> categoryPercentages) {
		this._start_date = startDate;
		this._end_date = endDate;
		this._amount = amount;
		this._categories = categories;
		this._category_percentages = categoryPercentages;
	}

	public String getStartDate() {
		return _start_date;
	}

	public void setStartDate(String _start_date) {
		this._start_date = _start_date;
	}

	public String getEndDate() {
		return _end_date;
	}

	public void setEndDate(String _end_date) {
		this._end_date = _end_date;
	}

	public int getAmount() {
		return _amount;
	}

	public void setAmount(int _amount) {
		this._amount = _amount;
	}

	public ArrayList<Category> getCategories() {
		return _categories;
	}

	public void setCategories(ArrayList<Category> _categories) {
		this._categories = _categories;
	}

	public ArrayList<Integer> getCategoryPercentages() {
		return _category_percentages;
	}

	public void setCategoryPercentages(ArrayList<Integer> _category_percentages) {
		this._category_percentages = _category_percentages;
	}

}