package comp3710.csse.eng.auburn.edu.moneyapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;


public class DatabaseHelper extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "transactionsManager";

	// Contacts table name
	public static final String TABLE_TRANSACTIONS = "transactions";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_DATE = "date";
	private static final String KEY_NAME = "name";
	private static final String KEY_AMOUNT = "amount";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_TYPE = "type";


	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_DBTRANSACTIONS_TABLE = "CREATE TABLE" + TABLE_TRANSACTIONS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
				+ KEY_NAME + " TEXT," + KEY_AMOUNT + " INTEGER,"
				+ KEY_CATEGORY + " TEXT," + KEY_TYPE + " TEXT" + ")";
		db.execSQL(CREATE_DBTRANSACTIONS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);

		// Create tables again
		onCreate(db);
	}

	void addTransaction(Transaction transaction) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DATE, transaction.getDate());
		values.put(KEY_NAME, transaction.getName());
		values.put(KEY_AMOUNT, transaction.getAmount());
		values.put(KEY_CATEGORY, transaction.getCategory().toString());
		values.put(KEY_TYPE, transaction.getType());

		db.insert(TABLE_TRANSACTIONS, null, values);
		db.close();
	}

	Transaction getTransaction(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_TRANSACTIONS, new String[] { KEY_ID,
						KEY_DATE, KEY_NAME, KEY_AMOUNT, KEY_CATEGORY, KEY_TYPE } , KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		Transaction transaction = null;

		if (cursor != null) {
			cursor.moveToFirst();

			transaction = new Transaction((Integer.parseInt(cursor.getString(0))),
					cursor.getString(1), cursor.getString(2), cursor.getInt(3),
					new Category(cursor.getString(4)), cursor.getString(5));
		}

		return transaction;
	}

	public List<Transaction> getAllTransactions() {
		List<Transaction> transactionList = new ArrayList<Transaction>();

		String selectQuery = "SELECT * FROM " + TABLE_TRANSACTIONS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Transaction transaction = new Transaction();
				transaction.setId(cursor.getInt(0));
				transaction.setDate(cursor.getString(1));
				transaction.setName(cursor.getString(2));
				transaction.setAmount(cursor.getInt(3));
				transaction.setCategory(new Category(cursor.getString(4)));
				transaction.setType(cursor.getString(5));

				transactionList.add(transaction);
			} while (cursor.moveToNext());
		}

		return transactionList;
	}

	public void deleteTransaction(Transaction transaction) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TRANSACTIONS, KEY_ID + " =?",
				new String[] { String.valueOf(transaction.getId())});
		db.close();
	}

}