package edu.auburn.eng.csse.comp3710.team13.home;

import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.auburn.eng.csse.comp3710.team13.R;

import edu.auburn.eng.csse.comp3710.team13.allCategories.AllCategoriesActivity;
import edu.auburn.eng.csse.comp3710.team13.allTransactions.AllTransactionsActivity;
import edu.auburn.eng.csse.comp3710.team13.buildTransaction.BuildTransactionActivity;
import edu.auburn.eng.csse.comp3710.team13.database.MoneyAppDatabaseHelper;
import edu.auburn.eng.csse.comp3710.team13.database.classes.Category;
import edu.auburn.eng.csse.comp3710.team13.database.classes.Transaction;
import edu.auburn.eng.csse.comp3710.team13.database.classes.TransactionPortion;
import edu.auburn.eng.csse.comp3710.team13.dialogFragments.ChooseTransactionTypeDialogFragment;
import edu.auburn.eng.csse.comp3710.team13.dialogFragments.EditCategoryDialogFragment;
import edu.auburn.eng.csse.comp3710.team13.dialogFragments.EditTransactionDialogFragment;


public class HomeActivity extends ActionBarActivity
		implements RecentTransactionsFragment.OnFragmentInteractionListener,
		EditTransactionDialogFragment.OnEditTransactionListener,
		ChooseTransactionTypeDialogFragment.OnNewTransactionListener,
		TopCategoriesFragment.OnFragmentInteractionListener,
		EditCategoryDialogFragment.OnFragmentInteractionListener {

	TextView balance_text;
	Button add_transaction_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		/*MoneyAppDatabaseHelper help = new MoneyAppDatabaseHelper(getApplicationContext());
		help.onUpgrade(help.getWritableDatabase(), 1, 1);*/
		/*CategoryTable t = new CategoryTable();
		t.onUpgrade(help.getWritableDatabase(), 1, 1);*/

		if (savedInstanceState == null) {

			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

			RecentTransactionsFragment fragment = new RecentTransactionsFragment();
			fragmentTransaction.add(R.id.widget_fragment_container, fragment, "recent_transactions");
			fragmentTransaction.commit();

			fragmentManager = getSupportFragmentManager();
			fragmentTransaction = fragmentManager.beginTransaction();

			TopCategoriesFragment fragment2 = new TopCategoriesFragment();
			fragmentTransaction.add(R.id.widget_fragment_container2, fragment2, "top_categories");
			fragmentTransaction.commit();

		}
		setBalanceText();

		add_transaction_button = (Button) findViewById(R.id.add_transaction_button);
		add_transaction_button.setOnClickListener(addTransactionButtonHandler);

	}

	View.OnClickListener addTransactionButtonHandler = new View.OnClickListener() {
		public void onClick(View v) {

			DialogFragment newFragment = new ChooseTransactionTypeDialogFragment();
			newFragment.show(getSupportFragmentManager(), "chooseTransactionType");

		}
	};



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void setBalanceText() {
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getApplicationContext());
		balance_text = (TextView) findViewById(R.id.balance_text);
		balance_text.setText(helper.getBalance());
	}




	public void onAllTransactions() {
		Intent intent = new Intent(HomeActivity.this, AllTransactionsActivity.class);

		startActivity(intent);
	}

	public void onAllCategories() {
		Intent intent = new Intent(HomeActivity.this, AllCategoriesActivity.class);

		startActivity(intent);
	}

	public void onEditTransaction(Transaction transaction) {

	}


	public void balanceChanged() {
		setBalanceText();
	}


	public void newTransaction(String type) {
		Intent intent = new Intent(HomeActivity.this, BuildTransactionActivity.class);

		intent.putExtra("type", type);
		startActivity(intent);
	}

	public void editTransaction(Transaction transaction) {
		Intent intent = new Intent(HomeActivity.this, BuildTransactionActivity.class);

		intent.putExtra("transaction", transaction);
		startActivity(intent);
	}

	public void editTransactionPortion(TransactionPortion transactionPortion) {
		Intent intent = new Intent(HomeActivity.this, BuildTransactionActivity.class);

		intent.putExtra("transactionPortion", transactionPortion);
		startActivity(intent);
	}

	public void editCategory(Category category) {
		// Edit Category
		// Create an instance of the dialog fragment and show it
		DialogFragment dialog = EditCategoryDialogFragment.newInstance(category.getName());
		dialog.show(getSupportFragmentManager(), "EditCategoryDialogFragment");
	}

	public void onEditCategory() {
		RecentTransactionsFragment recentTransactionsFragment = (RecentTransactionsFragment) getSupportFragmentManager().findFragmentByTag("recent_transactions");
		recentTransactionsFragment.dataSetChanged();

		TopCategoriesFragment topCategoriesFragment = (TopCategoriesFragment) getSupportFragmentManager().findFragmentByTag("top_categories");
		topCategoriesFragment.dataSetChanged();
	}


	public void transactionsChanged() {
		TopCategoriesFragment topCategoriesFragment = (TopCategoriesFragment) getSupportFragmentManager().findFragmentByTag("top_categories");
		topCategoriesFragment.dataSetChanged();


	}
}
