package comp3710.csse.eng.auburn.edu.moneyapp.database.helpers;

import android.content.ContentResolver;
import android.content.ContentUris;
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
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.TransactionPortion;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.TransactionTable;


public final class TransactionHelper {

	private static final Uri CONTENT_URI = MoneyAppContentProvider.CONTENT_URI_TRANSACTION;

	private TransactionHelper() {
	}

/*	public static String getBalance(ContentResolver contentResolver) {
		int balance = 0;
		*//*ArrayList<Transaction> withdrawalTransactions = getAllTransactionsOfType(contentResolver, "withdrawal");
		for (int i = 0; i < withdrawalTransactions.size(); i++) {
			balance -= Integer.parseInt(withdrawalTransactions.get(i).getTotal());
		}
		ArrayList<Transaction> depositTransactions = getAllTransactionsOfType(contentResolver, "deposit");
		for (int i = 0; i < depositTransactions.size(); i++) {
			balance += Integer.parseInt(depositTransactions.get(i).getTotal());
		}*//*

		ArrayList<Transaction> transactions = getAllTransactions(contentResolver);
		for (int i = 0; i < transactions.size(); i++) {

			balance += Integer.parseInt(transactions.get(i).getTotal());
		}

		return Integer.toString(balance);
	}*/

	public static ArrayList<Transaction> getAllTransactionsOfType(ContentResolver contentResolver, String type) {
		String[] projection = {TransactionTable.COLUMN_ID, TransactionTable.COLUMN_DATE,
				TransactionTable.COLUMN_TIME, TransactionTable.COLUMN_NAME,
				TransactionTable.COLUMN_TYPE};

		String selection = TransactionTable.COLUMN_TYPE + " = '" + type + "'";

		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, selection, null, null);

		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

		while (cursor.moveToNext()) {
			Transaction buildTransaction = getTransaction(cursor);
			transactionList.add(buildTransaction);
		}

		cursor.close();
		return transactionList;
	}

	public static int addTransaction(Transaction transaction, ContentResolver contentResolver) {
		ContentValues values = new ContentValues();

		values.put(TransactionTable.COLUMN_DATE, transaction.getDate());
		values.put(TransactionTable.COLUMN_TIME, transaction.getTime());
		values.put(TransactionTable.COLUMN_NAME, transaction.getName());
		values.put(TransactionTable.COLUMN_TYPE, transaction.getType());

		Uri uri = contentResolver.insert(CONTENT_URI, values);

		return (int)ContentUris.parseId(uri);
	}

	public static Transaction getTransaction(int id, ContentResolver contentResolver) {
		String[] projection = {TransactionTable.COLUMN_ID, TransactionTable.COLUMN_DATE,
				TransactionTable.COLUMN_TIME, TransactionTable.COLUMN_NAME,
				TransactionTable.COLUMN_TYPE};

		String selection = TransactionTable.COLUMN_ID + "=" + id;


		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, selection, null,
				null);

		cursor.moveToFirst();

		Log.d("db5", DatabaseUtils.dumpCursorToString(cursor));


		return getTransaction(cursor);
	}

	private static Transaction getTransaction(Cursor cursor) {
		Transaction transaction = new Transaction();

		transaction.setId(cursor.getInt(cursor.getColumnIndex(TransactionTable.COLUMN_ID)));
		transaction.setDate(cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_DATE)));
		transaction.setTime(cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_TIME)));
		transaction.setName(cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_NAME)));
		transaction.setType(cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_TYPE)));



		return transaction;
	}

	public static ArrayList<Transaction> getAllTransactions(ContentResolver contentResolver) {
		String[] projection = {TransactionTable.COLUMN_ID, TransactionTable.COLUMN_DATE,
				TransactionTable.COLUMN_TIME, TransactionTable.COLUMN_NAME,
				TransactionTable.COLUMN_TYPE};

		String selection = null;
		String sortOrder = TransactionTable.COLUMN_DATE + " DESC, "
				+ TransactionTable.COLUMN_TIME + " DESC ";

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

	public static ArrayList<Transaction> getRecentTransactions(int numberOfTransactions, ContentResolver contentResolver) {
		String[] projection = {TransactionTable.COLUMN_ID, TransactionTable.COLUMN_DATE,
		TransactionTable.COLUMN_TIME, TransactionTable.COLUMN_NAME,
		TransactionTable.COLUMN_TYPE};

		String selection = null;
		String sortOrder = TransactionTable.COLUMN_DATE + " DESC, "
				         + TransactionTable.COLUMN_TIME + " DESC "
						 + "LIMIT " + numberOfTransactions + ";";

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

		String selection = TransactionTable.COLUMN_ID + " = " + id;

		int rowsDeleted = contentResolver.delete(CONTENT_URI, selection, null);

		if (rowsDeleted > 0)
			result = true;

		return result;
	}
/*
	public static boolean deleteTransaction(Transaction buildTransaction, ContentResolver contentResolver) {
		boolean result = false;

		String selection = TransactionTable.COLUMN_ID + "= \"" + buildTransaction.getId() + "\"";

		int rowsDeleted = contentResolver.delete(CONTENT_URI, selection, null);

		if (rowsDeleted > 0)
			result = true;

		return result;
	}
*/
	public static int updateTransaction(Transaction transaction, ContentResolver contentResolver) {
		int numReplacedRows;

		ContentValues values = new ContentValues();

		values.put(TransactionTable.COLUMN_DATE, transaction.getDate());
		values.put(TransactionTable.COLUMN_TIME, transaction.getTime());
		values.put(TransactionTable.COLUMN_NAME, transaction.getName());
		values.put(TransactionTable.COLUMN_TYPE, transaction.getType());

		String selection = TransactionTable.COLUMN_ID + " = " + transaction.getId();

		numReplacedRows = contentResolver.update(CONTENT_URI, values, selection, null);

		return numReplacedRows;
	}
}
