package comp3710.csse.eng.auburn.edu.moneyapp.database;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.TransactionPortion;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.BudgetPortionTable;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.BudgetTable;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.CategoryTable;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.TransactionPortionTable;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.TransactionTable;

public class MoneyAppContentProvider extends ContentProvider {

	// database
	private MoneyAppDatabaseHelper database;

	// used for the UriMacher
	private static final int TRANSACTIONS = 1;
	private static final int TRANSACTION_ID = 2;
	private static final int BUDGETS = 3;
	private static final int BUDGET_ID = 4;
	private static final int BUDGET_PORTIONS = 5;
	private static final int BUDGET_PORTION_ID = 6;
	private static final int CATEGORIES = 7;
	private static final int CATEGORY_ID = 8;
	private static final int TRANSACTION_PORTIONS = 9;
	private static final int TRANSACTION_PORTION_ID = 10;

	private static final String AUTHORITY = "comp3710.csse.eng.auburn.edu.moneyapp.provider";

	private static final String BASE_PATH_TRANSACTION = TransactionTable.TABLE_TRANSACTION;
	private static final String BASE_PATH_TRANSACTION_PORTION = TransactionPortionTable.TABLE_TRANSACTION_PORTION;
	private static final String BASE_PATH_BUDGET = BudgetTable.TABLE_BUDGET;
	private static final String BASE_PATH_BUDGET_PORTION = BudgetPortionTable.TABLE_BUDGET_PORTION;
	private static final String BASE_PATH_CATEGORY = CategoryTable.TABLE_CATEGORY;

	public static final Uri CONTENT_URI_TRANSACTION = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_TRANSACTION);
	public static final Uri CONTENT_URI_TRANSACTION_PORTION = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_TRANSACTION_PORTION);
	public static final Uri CONTENT_URI_BUDGET = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_BUDGET);
	public static final Uri CONTENT_URI_BUDGET_PORTION = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_BUDGET_PORTION);
	public static final Uri CONTENT_URI_CATEGORY = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_CATEGORY);


	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TRANSACTION, TRANSACTIONS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TRANSACTION + "/#", TRANSACTION_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TRANSACTION, TRANSACTION_PORTIONS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TRANSACTION + "/#", TRANSACTION_PORTION_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_BUDGET, BUDGETS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_BUDGET + "/#", BUDGET_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_BUDGET_PORTION, BUDGET_PORTIONS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_BUDGET_PORTION + "/#", BUDGET_PORTION_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_CATEGORY, CATEGORIES);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_CATEGORY + "/#", CATEGORY_ID);
	}

	@Override
	public boolean onCreate() {
		database = new MoneyAppDatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
	                    String[] selectionArgs, String sortOrder) {

		// Uisng SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();


		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
			case TRANSACTIONS:
				queryBuilder.setTables(TransactionTable.TABLE_TRANSACTION);
				break;
			case TRANSACTION_ID:
				// adding the ID to the original query
				queryBuilder.appendWhere(TransactionTable.COLUMN_ID + "="
						+ uri.getLastPathSegment());
				break;

			case TRANSACTION_PORTIONS:
				queryBuilder.setTables(TransactionPortionTable.TABLE_TRANSACTION_PORTION);
				break;
			case TRANSACTION_PORTION_ID:
				// adding the ID to the original query
				queryBuilder.appendWhere(TransactionPortionTable.COLUMN_ID + "="
						+ uri.getLastPathSegment());
				break;

			case BUDGETS:
				queryBuilder.setTables(BudgetTable.TABLE_BUDGET);
				break;
			case BUDGET_ID:
				queryBuilder.appendWhere(BudgetTable.COLUMN_ID + "="
						+ uri.getLastPathSegment());
				break;

			case BUDGET_PORTIONS:
				queryBuilder.setTables(BudgetPortionTable.TABLE_BUDGET_PORTION);
				break;
			case BUDGET_PORTION_ID:
				queryBuilder.appendWhere(BudgetPortionTable.COLUMN_ID + "="
						+ uri.getLastPathSegment());
				break;

			case CATEGORIES:
				queryBuilder.setTables(CategoryTable.TABLE_CATEGORY);
				break;
			case CATEGORY_ID:
				queryBuilder.appendWhere(CategoryTable.COLUMN_NAME + "="
						+ uri.getLastPathSegment());
				break;

			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);
		// make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = 0;
		Uri new_row;
		switch (uriType) {
			case TRANSACTIONS:
				id = sqlDB.insert(TransactionTable.TABLE_TRANSACTION, null, values);
				new_row = Uri.parse(BASE_PATH_TRANSACTION + "/" + id);
				break;
			case TRANSACTION_PORTIONS:
				id = sqlDB.insert(TransactionPortionTable.TABLE_TRANSACTION_PORTION, null, values);
				new_row = Uri.parse(BASE_PATH_TRANSACTION_PORTION + "/" + id);
				break;
			case BUDGETS:
				id = sqlDB.insert(BudgetTable.TABLE_BUDGET, null, values);
				new_row = Uri.parse(BASE_PATH_BUDGET + "/" + id);
				break;
			case BUDGET_PORTIONS:
				id = sqlDB.insert(BudgetPortionTable.TABLE_BUDGET_PORTION, null, values);
				new_row = Uri.parse(BASE_PATH_BUDGET_PORTION + "/" + id);
				break;
			case CATEGORIES:
				id = sqlDB.insert(CategoryTable.TABLE_CATEGORY, null, values);
				new_row = Uri.parse(BASE_PATH_CATEGORY + "/" + id);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);

		return new_row;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		String id;
		switch (uriType) {
			case TRANSACTIONS:
				rowsDeleted = sqlDB.delete(TransactionTable.TABLE_TRANSACTION, selection,
						selectionArgs);
				break;
			case TRANSACTION_ID:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(TransactionTable.TABLE_TRANSACTION,
							TransactionTable.COLUMN_ID + "=" + id,
							null);
				} else {
					rowsDeleted = sqlDB.delete(TransactionTable.TABLE_TRANSACTION,
							TransactionTable.COLUMN_ID + "=" + id
									+ " and " + selection,
							selectionArgs);
				}
				break;

			case TRANSACTION_PORTIONS:
				rowsDeleted = sqlDB.delete(TransactionPortionTable.TABLE_TRANSACTION_PORTION, selection,
						selectionArgs);
				break;
			case TRANSACTION_PORTION_ID:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(TransactionPortionTable.TABLE_TRANSACTION_PORTION,
							TransactionPortionTable.COLUMN_ID + "=" + id,
							null);
				} else {
					rowsDeleted = sqlDB.delete(TransactionPortionTable.TABLE_TRANSACTION_PORTION,
							TransactionPortionTable.COLUMN_ID + "=" + id
									+ " and " + selection,
							selectionArgs);
				}
				break;

			case BUDGETS:
				rowsDeleted = sqlDB.delete(BudgetTable.TABLE_BUDGET, selection,
						selectionArgs);
				break;
			case BUDGET_ID:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(BudgetTable.TABLE_BUDGET,
							BudgetTable.COLUMN_ID + "=" + id,
							null);
				} else {
					rowsDeleted = sqlDB.delete(BudgetTable.TABLE_BUDGET,
							BudgetTable.COLUMN_ID + "=" + id
									+ " and " + selection,
							selectionArgs);
				}
				break;

			case BUDGET_PORTIONS:
				rowsDeleted = sqlDB.delete(BudgetPortionTable.TABLE_BUDGET_PORTION, selection,
						selectionArgs);
				break;
			case BUDGET_PORTION_ID:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(BudgetPortionTable.TABLE_BUDGET_PORTION,
							BudgetPortionTable.COLUMN_ID + "=" + id,
							null);
				} else {
					rowsDeleted = sqlDB.delete(BudgetPortionTable.TABLE_BUDGET_PORTION,
							BudgetPortionTable.COLUMN_ID + "=" + id
									+ " and " + selection,
							selectionArgs);
				}
				break;

			case CATEGORIES:
				rowsDeleted = sqlDB.delete(CategoryTable.TABLE_CATEGORY, selection,
						selectionArgs);
				break;
			case CATEGORY_ID:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(CategoryTable.TABLE_CATEGORY,
							CategoryTable.COLUMN_NAME + "=" + id,
							null);
				} else {
					rowsDeleted = sqlDB.delete(CategoryTable.TABLE_CATEGORY,
							CategoryTable.COLUMN_NAME + "=" + id
									+ " and " + selection,
							selectionArgs);
				}
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
	                  String[] selectionArgs) {

		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		String id;
		switch (uriType) {
			case TRANSACTIONS:
				rowsUpdated = sqlDB.update(TransactionTable.TABLE_TRANSACTION,
						values,
						selection,
						selectionArgs);
				break;
			case TRANSACTION_ID:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsUpdated = sqlDB.update(TransactionTable.TABLE_TRANSACTION,
							values,
							TransactionTable.COLUMN_ID + "=" + id,
							null);
				} else {
					rowsUpdated = sqlDB.update(TransactionTable.TABLE_TRANSACTION,
							values,
							TransactionTable.COLUMN_ID + "=" + id
									+ " and "
									+ selection,
							selectionArgs);
				}
				break;

			case TRANSACTION_PORTIONS:
				rowsUpdated = sqlDB.update(TransactionTable.TABLE_TRANSACTION,
						values,
						selection,
						selectionArgs);
				break;
			case TRANSACTION_PORTION_ID:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsUpdated = sqlDB.update(TransactionPortionTable.TABLE_TRANSACTION_PORTION,
							values,
							TransactionPortionTable.COLUMN_ID + "=" + id,
							null);
				} else {
					rowsUpdated = sqlDB.update(TransactionPortionTable.TABLE_TRANSACTION_PORTION,
							values,
							TransactionPortionTable.COLUMN_ID + "=" + id
									+ " and "
									+ selection,
							selectionArgs);
				}
				break;

			case BUDGETS:
				rowsUpdated = sqlDB.update(BudgetTable.TABLE_BUDGET,
					values,
					selection,
					selectionArgs);
				break;
			case BUDGET_ID:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsUpdated = sqlDB.update(BudgetTable.TABLE_BUDGET,
							values,
							BudgetTable.COLUMN_ID + "=" + id,
							null);
				} else {
					rowsUpdated = sqlDB.update(BudgetTable.TABLE_BUDGET,
							values,
							BudgetTable.COLUMN_ID + "=" + id
									+ " and "
									+ selection,
							selectionArgs);
				}
				break;

			case BUDGET_PORTIONS:
				rowsUpdated = sqlDB.update(BudgetPortionTable.TABLE_BUDGET_PORTION,
						values,
						selection,
						selectionArgs);
				break;
			case BUDGET_PORTION_ID:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsUpdated = sqlDB.update(BudgetPortionTable.TABLE_BUDGET_PORTION,
							values,
							BudgetPortionTable.COLUMN_ID + "=" + id,
							null);
				} else {
					rowsUpdated = sqlDB.update(BudgetPortionTable.TABLE_BUDGET_PORTION,
							values,
							BudgetPortionTable.COLUMN_ID + "=" + id
									+ " and "
									+ selection,
							selectionArgs);
				}
				break;

			case CATEGORIES:
				rowsUpdated = sqlDB.update(CategoryTable.TABLE_CATEGORY,
						values,
						selection,
						selectionArgs);
				break;
			case CATEGORY_ID:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsUpdated = sqlDB.update(CategoryTable.TABLE_CATEGORY,
							values,
							CategoryTable.COLUMN_NAME + "=" + id,
							null);
				} else {
					rowsUpdated = sqlDB.update(CategoryTable.TABLE_CATEGORY,
							values,
							CategoryTable.COLUMN_NAME + "=" + id
									+ " and "
									+ selection,
							selectionArgs);
				}
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

}