package comp3710.csse.eng.auburn.edu.moneyapp.database.tables;

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
			+ COLUMN_BUDGET_ID + " text not null"
				+ " FOREIGN KEY REFERENCES " + BudgetTable.TABLE_BUDGET + "(" + BudgetTable.COLUMN_ID + "),"
			+ COLUMN_CATEGORY_NAME + " text not null"
				+ " FOREIGN KEY REFERENCES " + CategoryTable.TABLE_CATEGORY + "(" + CategoryTable.COLUMN_NAME + "),"
			+ COLUMN_PERCENTAGE + "integer not null"
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