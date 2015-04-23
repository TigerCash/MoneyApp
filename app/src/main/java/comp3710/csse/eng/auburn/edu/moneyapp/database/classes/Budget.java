package comp3710.csse.eng.auburn.edu.moneyapp.database.classes;

import java.util.ArrayList;

public class Budget {

	private int _id;

	private String _start_date;
	private String _end_date;
	private int _amount;

	public Budget(){
	}

	public Budget(int id, String startDate, String endDate, int amount) {
		this._id = id;
		this._start_date = startDate;
		this._end_date = endDate;
		this._amount = amount;
	}

	public Budget(String startDate, String endDate, int amount) {
		this._start_date = startDate;
		this._end_date = endDate;
		this._amount = amount;
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

}