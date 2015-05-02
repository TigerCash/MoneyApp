package comp3710.csse.eng.auburn.edu.moneyapp.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Budget;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.BudgetPortion;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
import comp3710.csse.eng.auburn.edu.moneyapp.database.helpers.BudgetHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.helpers.BudgetPortionHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.helpers.CategoryHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.helpers.TransactionHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.BudgetPortionTable;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.BudgetTable;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.CategoryTable;
import comp3710.csse.eng.auburn.edu.moneyapp.database.tables.TransactionTable;

public class MoneyAppDatabaseHelper extends SQLiteOpenHelper {

	private ContentResolver contentResolver;

	private static final String DATABASE_NAME = "moneyapp.db";
	private static final int DATABASE_VERSION = 1;

	public MoneyAppDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		contentResolver = context.getContentResolver();
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		TransactionTable.onCreate(database);
		BudgetTable.onCreate(database);
		BudgetPortionTable.onCreate(database);
		CategoryTable.onCreate(database);
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
	                      int newVersion) {
		TransactionTable.onUpgrade(database, oldVersion, newVersion);
		BudgetTable.onUpgrade(database, oldVersion, newVersion);
		BudgetPortionTable.onUpgrade(database, oldVersion, newVersion);
		CategoryTable.onUpgrade(database, oldVersion, newVersion);
	}

	/*public int getBalance() {
		return TransactionHelper.getBalance(contentResolver);
	}

	// CRUD methods - stub here and call external helper file per table

	public ArrayList<Transaction> getAllTransactions() {
		return TransactionHelper.getAllTransactions(contentResolver);
	}

	public ArrayList<Transaction> getRecentTransactions(int numberOfTransactions) {
		return TransactionHelper.getRecentTransactions(numberOfTransactions, contentResolver);
	}

	// Transaction CRUD
	public int addTransaction(Transaction transaction) {
		return TransactionHelper.addTransaction(transaction, contentResolver);
	}

	public Transaction getTransaction(int id) {
		return TransactionHelper.getTransaction(id, contentResolver);
	}

	public boolean deleteTransaction(int id) {
		return TransactionHelper.deleteTransaction(id, contentResolver);
	}

	public boolean deleteTransaction(Transaction transaction) {
		return TransactionHelper.deleteTransaction(transaction, contentResolver);
	}

	public int updateTransaction(Transaction transaction) {
		return TransactionHelper.updateTransaction(transaction, contentResolver);
	}*/


	// Budget CRUD
	public int addBudget(Budget budget) {
		return BudgetHelper.addBudget(budget, contentResolver);
	}

	public Budget getBudget(int id) {
		return BudgetHelper.getBudget(id, contentResolver);
	}

	public void deleteBudget(int id) {
		BudgetHelper.deleteBudget(id, contentResolver);
	}

	public void deleteBudget(Budget budget) {
		BudgetHelper.deleteBudget(budget, contentResolver);
	}

	public int updateBudget(Budget budget) {
		return BudgetHelper.updateBudget(budget, contentResolver);
	}


	// BudgetPortion CRUD
	public int addBudgetPortion(BudgetPortion budgetPortion) {
		return BudgetPortionHelper.addBudgetPortion(budgetPortion, contentResolver);
	}

	public BudgetPortion getBudgetPortion(int id) {
		return BudgetPortionHelper.getBudgetPortion(id, contentResolver);
	}

	public void deleteBudgetPortion(int id) {
		BudgetPortionHelper.deleteBudgetPortion(id, contentResolver);
	}

	public void deleteBudgetPortion(BudgetPortion budgetPortion) {
		BudgetPortionHelper.deleteBudgetPortion(budgetPortion, contentResolver);
	}

	public int updateBudgetPortion(BudgetPortion budgetPortion) {
		return BudgetPortionHelper.updateBudgetPortion(budgetPortion, contentResolver);
	}


	// Category CRUD
	public String addCategory(Category category) {
		return CategoryHelper.addCategory(category, contentResolver);
	}

	public Category getCategory(int id) {
		return CategoryHelper.getCategory(id, contentResolver);
	}

	public ArrayList<Category> getAllCategories() {
		return CategoryHelper.getAllCategories(contentResolver);
	}

	public void deleteCategory(int id) {
		CategoryHelper.deleteCategory(id, contentResolver);
	}

	public void deleteCategory(Category category) {
		CategoryHelper.deleteCategory(category, contentResolver);
	}

	public int updateCategory(Category category) {
		return CategoryHelper.updateCategory(category, contentResolver);
	}
}
