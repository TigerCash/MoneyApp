package comp3710.csse.eng.auburn.edu.moneyapp.database.helpers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppContentProvider;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.TransactionTable;


public final class TransactionHelper {

	private static final Uri CONTENT_URI = MoneyAppContentProvider.CONTENT_URI_TRANSACTION;

	private TransactionHelper() {
	}

	public static int addTransaction(Transaction transaction, ContentResolver contentResolver) {
		ContentValues values = new ContentValues();

		//values.put(TransactionTable.COLUMN_ID, transaction.getId());
		// Removed above statement because it will always insert a 0 - we dont want this
		values.put(TransactionTable.COLUMN_DATE, transaction.getDate());
		values.put(TransactionTable.COLUMN_TIME, transaction.getTime());
		values.put(TransactionTable.COLUMN_NAME, transaction.getName());
		values.put(TransactionTable.COLUMN_AMOUNT, transaction.getAmount());
		values.put(TransactionTable.COLUMN_CATEGORY_NAME, transaction.getCategory().toString());
		values.put(TransactionTable.COLUMN_TYPE, transaction.getType());

		contentResolver.insert(CONTENT_URI, values);

		return transaction.getId();
	}

	public static Transaction getTransaction(int id, ContentResolver contentResolver) {
		String[] projection = {TransactionTable.COLUMN_ID, TransactionTable.COLUMN_DATE,
				TransactionTable.COLUMN_TIME,
				TransactionTable.COLUMN_NAME, TransactionTable.COLUMN_AMOUNT,
				TransactionTable.COLUMN_CATEGORY_NAME, TransactionTable.COLUMN_TYPE};

		String selection = TransactionTable.COLUMN_ID + "=" + id;

		selection = null;

		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, selection, null,
				null);

		cursor.moveToFirst();

		Log.d("db5", DatabaseUtils.dumpCursorToString(cursor));

		Transaction transaction = new Transaction();

		/*if (cursor.moveToFirst()) {
			cursor.moveToFirst();
			transaction.setId(cursor.getInt(0));
			transaction.setDate(cursor.getString(1));
			transaction.setName(cursor.getString(2));
			transaction.setAmount(cursor.getInt(3));
			transaction.setCategory(new Category(cursor.getString(4)));
			transaction.setType(cursor.getString(5));
			cursor.close();
		} else {
			transaction = null;
		}*/

		//return transaction;

		return getTransaction(cursor);
	}

	private static Transaction getTransaction(Cursor cursor) {
		Transaction transaction = new Transaction();

		transaction.setId(cursor.getInt(cursor.getColumnIndex(TransactionTable.COLUMN_ID)));
		transaction.setDate(cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_DATE)));
		transaction.setTime(cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_TIME)));
		transaction.setName(cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_NAME)));
		transaction.setAmount(cursor.getInt(cursor.getColumnIndex(TransactionTable.COLUMN_AMOUNT)));
		transaction.setCategory(new Category(cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_CATEGORY_NAME))));
		transaction.setType(cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_TYPE)));

		return transaction;
	}

	public static ArrayList<Transaction> getAllTransactions(ContentResolver contentResolver) {
		String[] projection = {TransactionTable.COLUMN_ID, TransactionTable.COLUMN_DATE,
				TransactionTable.COLUMN_TIME,
				TransactionTable.COLUMN_NAME, TransactionTable.COLUMN_AMOUNT,
				TransactionTable.COLUMN_CATEGORY_NAME, TransactionTable.COLUMN_TYPE};

		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, null, null,
				null);

		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

		while (cursor.moveToNext()) {
			Transaction transaction = getTransaction(cursor);
			transactionList.add(transaction);
		}

		cursor.close();
		return transactionList;
	}

	public static ArrayList<Transaction> getRecentTransactions(int numberOfTransactions, ContentResolver contentResolver) {
		String[] projection = {TransactionTable.COLUMN_ID, TransactionTable.COLUMN_DATE,
				TransactionTable.COLUMN_TIME,
				TransactionTable.COLUMN_NAME, TransactionTable.COLUMN_AMOUNT,
				TransactionTable.COLUMN_CATEGORY_NAME, TransactionTable.COLUMN_TYPE};

		String selection = null;
		String sortOrder = TransactionTable.COLUMN_DATE + " DESC, " + TransactionTable.COLUMN_TIME + " DESC;";

		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, selection, null, sortOrder);

		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

		while (cursor.moveToNext()) {
			Transaction transaction = getTransaction(cursor);
			transactionList.add(transaction);
		}

		cursor.close();
		return transactionList;

	}

	public static boolean deleteTransaction(int id, ContentResolver contentResolver) {
		boolean result = false;

		String selection = TransactionTable.COLUMN_ID + "= \"" + id + "\"";

		int rowsDeleted = contentResolver.delete(CONTENT_URI, selection, null);

		if (rowsDeleted > 0)
			result = true;

		return result;
	}

	public static boolean deleteTransaction(Transaction transaction, ContentResolver contentResolver) {
		boolean result = false;

		String selection = TransactionTable.COLUMN_ID + "= \"" + transaction.getId() + "\"";

		int rowsDeleted = contentResolver.delete(CONTENT_URI, selection, null);

		if (rowsDeleted > 0)
			result = true;

		return result;
	}

	public static int updateTransaction(Transaction transaction, ContentResolver contentResolver) {
		int numReplacedRows;

		ContentValues values = new ContentValues();

		values.put(TransactionTable.COLUMN_DATE, transaction.getDate());
		values.put(TransactionTable.COLUMN_TIME, transaction.getTime());
		values.put(TransactionTable.COLUMN_NAME, transaction.getName());
		values.put(TransactionTable.COLUMN_AMOUNT, transaction.getAmount());
		values.put(TransactionTable.COLUMN_CATEGORY_NAME, transaction.getCategory().toString());
		values.put(TransactionTable.COLUMN_TYPE, transaction.getType());

		numReplacedRows = contentResolver.update(CONTENT_URI, values, null, null);

		return numReplacedRows;
	}
}
