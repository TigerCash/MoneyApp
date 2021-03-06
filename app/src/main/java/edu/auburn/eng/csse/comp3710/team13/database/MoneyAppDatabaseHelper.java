package edu.auburn.eng.csse.comp3710.team13.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import edu.auburn.eng.csse.comp3710.team13.database.classes.Budget;
import edu.auburn.eng.csse.comp3710.team13.database.classes.BudgetPortion;
import edu.auburn.eng.csse.comp3710.team13.database.classes.Category;
import edu.auburn.eng.csse.comp3710.team13.database.classes.Transaction;
import edu.auburn.eng.csse.comp3710.team13.database.classes.TransactionPortion;
import edu.auburn.eng.csse.comp3710.team13.database.helpers.BudgetHelper;
import edu.auburn.eng.csse.comp3710.team13.database.helpers.BudgetPortionHelper;
import edu.auburn.eng.csse.comp3710.team13.database.helpers.CategoryHelper;
import edu.auburn.eng.csse.comp3710.team13.database.helpers.TransactionHelper;
import edu.auburn.eng.csse.comp3710.team13.database.helpers.TransactionPortionHelper;
import edu.auburn.eng.csse.comp3710.team13.database.tables.BudgetPortionTable;
import edu.auburn.eng.csse.comp3710.team13.database.tables.BudgetTable;
import edu.auburn.eng.csse.comp3710.team13.database.tables.CategoryTable;
import edu.auburn.eng.csse.comp3710.team13.database.tables.TransactionPortionTable;
import edu.auburn.eng.csse.comp3710.team13.database.tables.TransactionTable;

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
		TransactionPortionTable.onCreate(database);
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
		TransactionPortionTable.onUpgrade(database, oldVersion, newVersion);
		BudgetTable.onUpgrade(database, oldVersion, newVersion);
		BudgetPortionTable.onUpgrade(database, oldVersion, newVersion);
		CategoryTable.onUpgrade(database, oldVersion, newVersion);
	}

	public String getBalance() {

		double balance = 0;

		ArrayList<Transaction> transactions = TransactionHelper.getAllTransactions(contentResolver);
		Transaction transaction;
		for (int i = 0; i < transactions.size(); i++) {
			transaction = transactions.get(i);
			transaction.setTransactionPortions(TransactionPortionHelper.getTransactionPortions(transaction.getId(), contentResolver));
			balance += Double.parseDouble(transaction.getTotal());
		}

		NumberFormat formatter = new DecimalFormat("#0.00");
		String balanceString = formatter.format(balance);


		return balanceString;

	}

	// CRUD methods - stub here and call external helper file per table

	public ArrayList<Transaction> getAllTransactions() {
		ArrayList<Transaction> transactions = TransactionHelper.getAllTransactions(contentResolver);

		for (int i = 0; i < transactions.size(); i++) {
			ArrayList<TransactionPortion> transactionPortions = getTransactionPortions(transactions.get(i).getId());
			transactions.get(i).setTransactionPortions(transactionPortions);
		}

		return transactions;
	}

	public ArrayList<Transaction> getRecentTransactions(int numberOfTransactions) {
		ArrayList<Transaction> transactions = TransactionHelper.getRecentTransactions(numberOfTransactions, contentResolver);

		for (int i = 0; i < transactions.size(); i++) {
			ArrayList<TransactionPortion> transactionPortions = getTransactionPortions(transactions.get(i).getId());
			transactions.get(i).setTransactionPortions(transactionPortions);
		}

		return transactions;
	}

	// Transaction CRUD
	public int addTransaction(Transaction transaction) {
		return TransactionHelper.addTransaction(transaction, contentResolver);
	}

	public Transaction getTransaction(int transactionId) {
		return TransactionHelper.getTransaction(transactionId, contentResolver);
	}
/*
	public Transaction getTransaction(int id) {
		return TransactionHelper.getTransaction(id, contentResolver);
	}

	public boolean deleteTransaction(int id) {
		return TransactionHelper.deleteTransaction(id, contentResolver);
	}

	public boolean deleteTransaction(Transaction buildTransaction) {
		return TransactionHelper.deleteTransaction(buildTransaction, contentResolver);
	}
*/
	public boolean deleteTransaction(int transactionId) {
		ArrayList<TransactionPortion> transactionPortions = TransactionPortionHelper.getTransactionPortions(transactionId, contentResolver);
		for (TransactionPortion tp : transactionPortions) {
			TransactionPortionHelper.deleteTransactionPortion(tp.getId(), contentResolver);
		}
		return TransactionHelper.deleteTransaction(transactionId, contentResolver);
	}

	public int updateTransaction(Transaction transaction) {
		return TransactionHelper.updateTransaction(transaction, contentResolver);
	}


	// TransactionPortion CRUD
	public void addTransactionPortions(ArrayList<TransactionPortion> transactionPortions) {
		TransactionPortionHelper.addTransactionPortions(transactionPortions, contentResolver);
	}

	public void updateTransactionPortions(ArrayList<TransactionPortion> transactionPortions) {
		TransactionPortionHelper.updateTransactionPortions(transactionPortions, contentResolver);
	}

	public void updateTransactionPortion(TransactionPortion transactionPortion) {
		TransactionPortionHelper.updateTransactionPortion(transactionPortion, contentResolver);
	}

	public ArrayList<TransactionPortion> getTransactionPortions(int transactionId) {
		return TransactionPortionHelper.getTransactionPortions(transactionId, contentResolver);
	}

	public ArrayList<TransactionPortion> getTransactionPortionsCategoryId(int categoryId) {
		return TransactionPortionHelper.getTransactionPortionsCategoryId(categoryId, contentResolver);
	}

	public TransactionPortion getTransactionPortion(int transactionPortionId) {
		return TransactionPortionHelper.getTransactionPortion(transactionPortionId, contentResolver);
	}

	public boolean deleteTransactionPortion(int transactionPortionId) {
		return TransactionPortionHelper.deleteTransactionPortion(transactionPortionId, contentResolver);
	}

	public boolean deleteTransactionPortions(int transactionId) {
		return TransactionPortionHelper.deleteTransactionPortions(transactionId, contentResolver);
	}


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

	public Category getCategory(String name) { return CategoryHelper.getCategory(name, contentResolver); }

	public ArrayList<Category> getAllCategories() {
		return CategoryHelper.getAllCategories(contentResolver);
	}

	public ArrayList<Category> getTopCategories(int numberCategories) {

		HashMap<Double, Category> hm = new HashMap<>();
		ArrayList<Category> allCategories = getAllCategories();

		for (Category c : allCategories) {
			ArrayList<TransactionPortion> transactionPortions = getTransactionPortionsCategoryId(c.getId());


			double total = 0;
			for (TransactionPortion t : transactionPortions) {
				total += Double.parseDouble(t.getAmount());
			}
			hm.put(total, c);
		}

		Map<Double, Category> map = new TreeMap<Double, Category>(Collections.reverseOrder());
		map.putAll(hm);

		ArrayList<Category> topCategories = new ArrayList<>();

		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		int i = 0;
		while(iterator.hasNext() && i < numberCategories) {
			Map.Entry me2 = (Map.Entry)iterator.next();
			topCategories.add((Category)me2.getValue());
		}

		return topCategories;
	}

	public double getCategoryAmount(int id) {
		Category category = getCategory(id);

		ArrayList<TransactionPortion> transactionPortions = getTransactionPortionsCategoryId(category.getId());

		double total = 0;
		for (TransactionPortion t : transactionPortions) {
			int transactionId = t.getTransactionId();
			if (transactionId > 0) {
				Transaction transaction = getTransaction(transactionId);
				if (transaction != null) {
					String type = transaction.getType();
					if (type.equals("Withdrawal"))
						total -= Double.parseDouble(t.getAmount());
					else
						total += Double.parseDouble(t.getAmount());
				}
			}
		}

		return total;
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
