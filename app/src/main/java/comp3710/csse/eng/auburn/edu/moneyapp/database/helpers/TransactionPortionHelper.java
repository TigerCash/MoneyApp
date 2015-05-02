package comp3710.csse.eng.auburn.edu.moneyapp.database.helpers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppContentProvider;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.TransactionPortion;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.TransactionPortionTable;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.TransactionTable;


public final class TransactionPortionHelper {

	private static final Uri CONTENT_URI = MoneyAppContentProvider.CONTENT_URI_TRANSACTION_PORTION;

	private TransactionPortionHelper() {
	}





	public static int addTransactionPortion(TransactionPortion transactionPortion, ContentResolver contentResolver) {
		ContentValues values = new ContentValues();

		//values.put(TransactionTable.COLUMN_ID, transaction.getId());
		// Removed above statement because it will always insert a 0 - we dont want this
		values.put(TransactionPortionTable.COLUMN_DESCRIPTION, transactionPortion.getDescription());
		values.put(TransactionPortionTable.COLUMN_AMOUNT, transactionPortion.getAmount());
		values.put(TransactionPortionTable.COLUMN_CATEGORY_ID, transactionPortion.getCategoryId());
		values.put(TransactionPortionTable.COLUMN_TRANSACTION_ID, transactionPortion.getTransactionId());

		contentResolver.insert(CONTENT_URI, values);

		return transactionPortion.getId();
	}

	public static TransactionPortion getTransaction(int id, ContentResolver contentResolver) {

		String[] projection = {TransactionPortionTable.COLUMN_ID, TransactionPortionTable.COLUMN_DESCRIPTION,
				TransactionPortionTable.COLUMN_AMOUNT, TransactionPortionTable.COLUMN_CATEGORY_ID,
				TransactionPortionTable.COLUMN_TRANSACTION_ID};

		String selection = TransactionPortionTable.COLUMN_ID + "=" + id;



		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, selection, null,
				null);

		cursor.moveToFirst();

		Log.d("db5", DatabaseUtils.dumpCursorToString(cursor));

		TransactionPortion transactionPortion = new TransactionPortion();


		return getTransactionPortion(cursor);
	}

	private static TransactionPortion getTransactionPortion(Cursor cursor) {
		TransactionPortion transactionPortion = new TransactionPortion();

		transactionPortion.setId(cursor.getInt(cursor.getColumnIndex(TransactionPortionTable.COLUMN_ID)));
		transactionPortion.setDescription(cursor.getString(cursor.getColumnIndex(TransactionPortionTable.COLUMN_DESCRIPTION)));
		transactionPortion.setAmount(cursor.getString(cursor.getColumnIndex(TransactionPortionTable.COLUMN_AMOUNT)));
		transactionPortion.setCategoryId(cursor.getInt(cursor.getColumnIndex(TransactionPortionTable.COLUMN_CATEGORY_ID)));
		transactionPortion.setTransactionId(cursor.getInt(cursor.getColumnIndex(TransactionPortionTable.COLUMN_TRANSACTION_ID)));

		return transactionPortion;
	}

	/*public static ArrayList<Transaction> getAllTransactions(ContentResolver contentResolver) {
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

		String selection = TransactionTable.COLUMN_ID + " = " + transaction.getId();

		numReplacedRows = contentResolver.update(CONTENT_URI, values, selection, null);

		return numReplacedRows;
	}*/
}
