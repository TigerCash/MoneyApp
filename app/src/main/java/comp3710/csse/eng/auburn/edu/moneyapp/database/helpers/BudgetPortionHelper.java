package comp3710.csse.eng.auburn.edu.moneyapp.database.helpers;

import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppContentProvider;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.BudgetPortion;


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
