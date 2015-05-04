package edu.auburn.eng.csse.comp3710.team13.database.helpers;

import android.content.ContentResolver;
import android.net.Uri;

import edu.auburn.eng.csse.comp3710.team13.database.MoneyAppContentProvider;
import edu.auburn.eng.csse.comp3710.team13.database.classes.Budget;


public final class BudgetHelper {


	private static final Uri CONTENT_URI = MoneyAppContentProvider.CONTENT_URI_BUDGET;

	private BudgetHelper() {
	}

	public static int addBudget(Budget budget, ContentResolver contentResolver) {
		return 0;
	}

	public static Budget getBudget(int id, ContentResolver contentResolver) {
		return new Budget();
	}

	public static void deleteBudget(int id, ContentResolver contentResolver) {

	}

	public static void deleteBudget(Budget budget, ContentResolver contentResolver) {

	}

	public static int updateBudget(Budget budget, ContentResolver contentResolver) {
		return 0;
	}
}
