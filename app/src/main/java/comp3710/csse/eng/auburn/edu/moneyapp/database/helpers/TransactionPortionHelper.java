package comp3710.csse.eng.auburn.edu.moneyapp.database.helpers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.util.Log;

import java.net.URI;
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




	public static void addTransactionPortions(ArrayList<TransactionPortion> transactionPortions, ContentResolver contentResolver) {
		for (int i = 0; i < transactionPortions.size(); i++) {
			addTransactionPortion(transactionPortions.get(i), contentResolver);
		}
	}

	public static int addTransactionPortion(TransactionPortion transactionPortion, ContentResolver contentResolver) {
		ContentValues values = new ContentValues();

		//values.put(TransactionTable.COLUMN_ID, buildTransaction.getId());
		// Removed above statement because it will always insert a 0 - we dont want this
		values.put(TransactionPortionTable.COLUMN_DESCRIPTION, transactionPortion.getDescription());
		values.put(TransactionPortionTable.COLUMN_AMOUNT, transactionPortion.getAmount());
		values.put(TransactionPortionTable.COLUMN_CATEGORY_ID, transactionPortion.getCategoryId());
		values.put(TransactionPortionTable.COLUMN_TRANSACTION_ID, transactionPortion.getTransactionId());

		contentResolver.insert(CONTENT_URI, values);

		return transactionPortion.getId();
	}


	public static TransactionPortion getTransactionPortion(int id, ContentResolver contentResolver) {

		String[] projection = {TransactionPortionTable.COLUMN_ID, TransactionPortionTable.COLUMN_DESCRIPTION,
				TransactionPortionTable.COLUMN_AMOUNT, TransactionPortionTable.COLUMN_CATEGORY_ID,
				TransactionPortionTable.COLUMN_TRANSACTION_ID};

		String selection = TransactionPortionTable.COLUMN_ID + "=" + id;



		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, selection, null,
				null);

		cursor.moveToFirst();

		Log.d("db5", DatabaseUtils.dumpCursorToString(cursor));


		return getTransactionPortion(cursor);
	}

	public static ArrayList<TransactionPortion> getTransactionPortionsCategoryId(int categoryId, ContentResolver contentResolver) {
		String[] projection = {TransactionPortionTable.COLUMN_ID, TransactionPortionTable.COLUMN_DESCRIPTION,
				TransactionPortionTable.COLUMN_AMOUNT, TransactionPortionTable.COLUMN_CATEGORY_ID,
				TransactionPortionTable.COLUMN_TRANSACTION_ID};

		String selection = TransactionPortionTable.COLUMN_CATEGORY_ID + "=" + categoryId;

		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, selection, null,
				null);

		ArrayList<TransactionPortion> transactionPortions = new ArrayList<TransactionPortion>();

		while (cursor.moveToNext()) {
			TransactionPortion transactionPortion = getTransactionPortion(cursor);
			transactionPortions.add(transactionPortion);
		}

		cursor.close();

		return transactionPortions;
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

	public static ArrayList<TransactionPortion> getTransactionPortions(int transactionId, ContentResolver contentResolver) {
		String[] projection = {TransactionPortionTable.COLUMN_ID, TransactionPortionTable.COLUMN_DESCRIPTION,
				TransactionPortionTable.COLUMN_AMOUNT, TransactionPortionTable.COLUMN_CATEGORY_ID,
				TransactionPortionTable.COLUMN_TRANSACTION_ID};

		String selection = TransactionPortionTable.COLUMN_TRANSACTION_ID + "=" + transactionId;

		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, selection, null,
				null);

		ArrayList<TransactionPortion> transactionPortions = new ArrayList<TransactionPortion>();

		while (cursor.moveToNext()) {
			TransactionPortion transactionPortion = getTransactionPortion(cursor);
			transactionPortions.add(transactionPortion);
		}

		cursor.close();

		return transactionPortions;
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
			Transaction buildTransaction = getTransaction(cursor);
			transactionList.add(buildTransaction);
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
			Transaction buildTransaction = getTransaction(cursor);
			transactionList.add(buildTransaction);
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

	public static boolean deleteTransaction(Transaction buildTransaction, ContentResolver contentResolver) {
		boolean result = false;

		String selection = TransactionTable.COLUMN_ID + "= \"" + buildTransaction.getId() + "\"";

		int rowsDeleted = contentResolver.delete(CONTENT_URI, selection, null);

		if (rowsDeleted > 0)
			result = true;

		return result;
	}
*/
	public static boolean deleteTransactionPortions(int transactionId, ContentResolver contentResolver) {
		ArrayList<TransactionPortion> transactionPortions = getTransactionPortions(transactionId, contentResolver);
		for (int i = 0; i < transactionPortions.size(); i++) {
			deleteTransactionPortion(transactionPortions.get(i).getId(), contentResolver);
		}
		return true;
	}

	public static boolean deleteTransactionPortion(int transactionPortionId, ContentResolver contentResolver) {
		boolean result = false;

		String selection = TransactionPortionTable.COLUMN_ID + "=" + transactionPortionId;

		int rowsDeleted = contentResolver.delete(CONTENT_URI, selection, null);

		if (rowsDeleted > 0)
			result = true;

		return result;
	}

	public static void updateTransactionPortions(ArrayList<TransactionPortion> transactionPortions, ContentResolver contentResolver) {
		for (int i = 0; i < transactionPortions.size(); i++) {
			updateTransactionPortion(transactionPortions.get(i), contentResolver);
		}
	}

	public static int updateTransactionPortion(TransactionPortion transactionPortion, ContentResolver contentResolver) {
		int numReplacedRows;

		ContentValues values = new ContentValues();

		//values.put(TransactionTable.COLUMN_ID, buildTransaction.getId());
		// Removed above statement because it will always insert a 0 - we dont want this
		values.put(TransactionPortionTable.COLUMN_DESCRIPTION, transactionPortion.getDescription());
		values.put(TransactionPortionTable.COLUMN_AMOUNT, transactionPortion.getAmount());
		values.put(TransactionPortionTable.COLUMN_CATEGORY_ID, transactionPortion.getCategoryId());
		values.put(TransactionPortionTable.COLUMN_TRANSACTION_ID, transactionPortion.getTransactionId());

		String selection = TransactionPortionTable.COLUMN_ID + " = " + transactionPortion.getId();

		Uri test = CONTENT_URI;

		numReplacedRows = contentResolver.update(CONTENT_URI, values, selection, null);

		return numReplacedRows;
	}
}
