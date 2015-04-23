package comp3710.csse.eng.auburn.edu.moneyapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.TransactionTable;

public class MoneyAppDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "moneyapp.db";
	private static final int DATABASE_VERSION = 1;

	public MoneyAppDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		TransactionTable.onCreate(database);    // Create Transaction Table
		// Create other tables...
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
	                      int newVersion) {
		TransactionTable.onUpgrade(database, oldVersion, newVersion);   // Upgrade Transaction Table
		// Upgrade other tables...
	}
}
