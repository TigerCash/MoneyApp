package edu.auburn.eng.csse.comp3710.team13.database.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class BudgetTable {

	// Database table
	public static final String TABLE_BUDGET = "budget";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_START_DATE = "start_date";
	public static final String COLUMN_END_DATE = "end_date";
	public static final String COLUMN_AMOUNT = "amount";

	// Database creation SQL statement
	private static final String TABLE_CREATE = "create table "
			+ TABLE_BUDGET
			+ "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_START_DATE + " text not null, "
			+ COLUMN_END_DATE + " text not null, "
			+ COLUMN_AMOUNT + "integer not null"
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	                             int newVersion) {
		Log.w(TransactionTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
		onCreate(database);
	}
}