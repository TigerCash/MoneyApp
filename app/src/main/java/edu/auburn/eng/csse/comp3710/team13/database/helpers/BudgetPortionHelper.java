package edu.auburn.eng.csse.comp3710.team13.database.helpers;

import android.content.ContentResolver;
import android.net.Uri;

import edu.auburn.eng.csse.comp3710.team13.database.MoneyAppContentProvider;
import edu.auburn.eng.csse.comp3710.team13.database.classes.BudgetPortion;


public final class BudgetPortionHelper {


	private static final Uri CONTENT_URI = MoneyAppContentProvider.CONTENT_URI_BUDGET_PORTION;

	private BudgetPortionHelper() {
	}

	public static int addBudgetPortion(BudgetPortion budgetPortion, ContentResolver contentResolver) {
		return 0;
	}

	public static BudgetPortion getBudgetPortion(int id, ContentResolver contentResolver) {
		return new BudgetPortion();
	}

	public static void deleteBudgetPortion(int id, ContentResolver contentResolver) {

	}

	public static void deleteBudgetPortion(BudgetPortion budgetPortion, ContentResolver contentResolver) {

	}

	public static int updateBudgetPortion(BudgetPortion budgetPortion, ContentResolver contentResolver) {
		return 0;
	}
}
