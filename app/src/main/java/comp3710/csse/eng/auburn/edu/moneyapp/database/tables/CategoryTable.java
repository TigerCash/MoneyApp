package comp3710.csse.eng.auburn.edu.moneyapp.database.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class CategoryTable {

	// Database table
	public static final String TABLE_CATEGORY = "category";
	public static final String COLUMN_NAME = "name";

	// Database creation SQL statement
	private static final String TABLE_CREATE = "create table "
			+ TABLE_CATEGORY
			+ "("
			+ COLUMN_NAME + " text primary key"
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	                             int newVersion) {
		Log.w(TransactionTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		onCreate(database);
	}
}