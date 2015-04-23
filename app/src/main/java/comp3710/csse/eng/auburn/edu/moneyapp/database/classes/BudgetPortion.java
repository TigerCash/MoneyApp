package comp3710.csse.eng.auburn.edu.moneyapp.database.classes;

import java.util.ArrayList;

public class BudgetPortion {

	private int _id;

	private Budget _budget;
	private Category _category;
	private int _percentage;

	public BudgetPortion(){
	}

	public BudgetPortion(int id, Budget budget, Category category, int percentage) {
		this._id = id;
		this._budget = budget;
		this._category = category;
		this._percentage = percentage;
	}

	public BudgetPortion(Budget budget, Category category, int percentage) {
		this._budget = budget;
		this._category = category;
		this._percentage = percentage;
	}



	public int getId() {
		return _id;
	}

	public void setId(int _id) {
		this._id = _id;
	}

	public Budget getBudget() {
		return _budget;
	}

	public void setBudget(Budget _budget) {
		this._budget = _budget;
	}

	public Category getCategory() {
		return _category;
	}

	public void setCategory(Category _category) {
		this._category = _category;
	}

	public int getPercentage() {
		return _percentage;
	}

	public void setPercentage(int _percentage) {
		this._percentage = _percentage;
	}
}