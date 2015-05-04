package edu.auburn.eng.csse.comp3710.team13.database.helpers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team13.database.MoneyAppContentProvider;
import edu.auburn.eng.csse.comp3710.team13.database.classes.Category;
import edu.auburn.eng.csse.comp3710.team13.database.tables.CategoryTable;


public final class CategoryHelper {


	private static final Uri CONTENT_URI = MoneyAppContentProvider.CONTENT_URI_CATEGORY;

	private CategoryHelper() {
	}

	public static String addCategory(Category category, ContentResolver contentResolver) {
		ContentValues values = new ContentValues();

		values.put(CategoryTable.COLUMN_NAME, category.getName());

		contentResolver.insert(CONTENT_URI, values);

		return category.getName();
	}

	public static Category getCategory(int id, ContentResolver contentResolver) {
		String[] projection = {CategoryTable.COLUMN_ID, CategoryTable.COLUMN_NAME};

		String selection = CategoryTable.COLUMN_ID + " = " + id;

		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, selection, null,
				null);

		cursor.moveToFirst();

		return getCategory(cursor);
	}

	public static Category getCategory(String name, ContentResolver contentResolver) {

		String[] projection = {CategoryTable.COLUMN_ID, CategoryTable.COLUMN_NAME};

		String selection = CategoryTable.COLUMN_NAME+ "='" + name + "'";

		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, selection, null,
				null);

		cursor.moveToFirst();

		return getCategory(cursor);
	}

	private static Category getCategory(Cursor cursor) {
		Category category = new Category();

		category.setId(cursor.getInt(cursor.getColumnIndex(CategoryTable.COLUMN_ID)));
		category.setName(cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_NAME)));

		return category;
	}

	public static ArrayList<Category> getAllCategories(ContentResolver contentResolver) {
		String[] projection = {CategoryTable.COLUMN_ID, CategoryTable.COLUMN_NAME};

		Cursor cursor = contentResolver.query(CONTENT_URI,
				projection, null, null,
				null);

		ArrayList<Category> categoryList = new ArrayList<Category>();

		while (cursor.moveToNext()) {
			Category category = getCategory(cursor);
			categoryList.add(category);
		}

		cursor.close();
		return categoryList;
	}


	public static void deleteCategory(int id, ContentResolver contentResolver) {

	}

	public static void deleteCategory(Category category, ContentResolver contentResolver) {

	}

	public static int updateCategory(Category category, ContentResolver contentResolver) {
		int numReplacedRows;

		ContentValues values = new ContentValues();

		//values.put(TransactionTable.COLUMN_ID, buildTransaction.getId());
		// Removed above statement because it will always insert a 0 - we dont want this
		values.put(CategoryTable.COLUMN_NAME, category.getName());

		String selection = CategoryTable.COLUMN_ID + " = " + category.getId();

		numReplacedRows = contentResolver.update(CONTENT_URI, values, selection, null);

		return numReplacedRows;
	}
}
