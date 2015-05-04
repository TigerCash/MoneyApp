package edu.auburn.eng.csse.comp3710.team13.database.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TransactionPortionTable {

	// Database table
	public static final String TABLE_TRANSACTION_PORTION = "transaction_portions";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_CATEGORY_ID = "category_id";
	public static final String COLUMN_TRANSACTION_ID = "transaction_id";

	// Database creation SQL statement
	private static final String TABLE_CREATE = "create table "
			+ TABLE_TRANSACTION_PORTION
			+ " ("
			+ COLUMN_ID + " integer primary key, "
			+ COLUMN_DESCRIPTION + " text not null, "
			+ COLUMN_AMOUNT + " text not null, "
			+ COLUMN_CATEGORY_ID + " integer not null, "
			+ COLUMN_TRANSACTION_ID + " integer not null, "
			+ "FOREIGN KEY(" + COLUMN_CATEGORY_ID + ") REFERENCES " + CategoryTable.TABLE_CATEGORY + "(" + CategoryTable.COLUMN_ID + "), "
			+ "FOREIGN KEY(" + COLUMN_TRANSACTION_ID + ") REFERENCES " + TransactionTable.TABLE_TRANSACTION + "(" + TransactionTable.COLUMN_ID + ")"
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	                             int newVersion) {
		Log.w(TransactionTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION_PORTION);
		onCreate(database);
	}


}
