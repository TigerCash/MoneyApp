package comp3710.csse.eng.auburn.edu.moneyapp;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TransactionTable {

	// Database table
	public static final String TABLE_TRANSACTION = "transaction";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_TYPE = "type";

	// Database creation SQL statement
	private static final String TABLE_CREATE = "create table "
			+ TABLE_TRANSACTION
			+ "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_DATE + " text not null"
			+ COLUMN_NAME + " text not null"
			+ COLUMN_AMOUNT + "integer not null"
			+ COLUMN_CATEGORY + " text not null, "
		    + COLUMN_TYPE + " text not null"
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	                             int newVersion) {
		Log.w(TransactionTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
		onCreate(database);
	}
}