package comp3710.csse.eng.auburn.edu.moneyapp.database.helpers;

import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppContentProvider;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;


public final class CategoryHelper {


	private static final Uri CONTENT_URI = MoneyAppContentProvider.CONTENT_URI_CATEGORY;

	private CategoryHelper() {
	}

	public static int addCategory(Category category, ContentResolver contentResolver) {
		return 0;
	}

	public static Category getCategory(int id, ContentResolver contentResolver) {
		return new Category();
	}

	public static void deleteCategory(int id, ContentResolver contentResolver) {

	}

	public static void deleteCategory(Category category, ContentResolver contentResolver) {

	}

	public static int updateCategory(Category category, ContentResolver contentResolver) {
		return 0;
	}
}
