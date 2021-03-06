package edu.auburn.eng.csse.comp3710.team13.database.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BudgetPortionTable {

	// Database table
	public static final String TABLE_BUDGET_PORTION = "budget_portion";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_BUDGET_ID = "budget_id";
	public static final String COLUMN_CATEGORY_NAME = "category_name";
	public static final String COLUMN_PERCENTAGE = "percentage";

	// Database creation SQL statement
	private static final String TABLE_CREATE = "create table "
			+ TABLE_BUDGET_PORTION
			+ "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_BUDGET_ID + " text not null, "
			+ COLUMN_CATEGORY_NAME + " text not null, "
			+ COLUMN_PERCENTAGE + "integer not null, "
			+ "FOREIGN KEY(" + COLUMN_BUDGET_ID + ") REFERENCES " + BudgetTable.TABLE_BUDGET + "(" + BudgetTable.COLUMN_ID + "), "
			+ "FOREIGN KEY(" + COLUMN_CATEGORY_NAME + ") REFERENCES " + CategoryTable.TABLE_CATEGORY + "(" + CategoryTable.COLUMN_NAME + ")"
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	                             int newVersion) {
		Log.w(TransactionTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET_PORTION);
		onCreate(database);
	}
}