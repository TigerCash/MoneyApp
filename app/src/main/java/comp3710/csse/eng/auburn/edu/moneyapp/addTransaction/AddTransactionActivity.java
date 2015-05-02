package comp3710.csse.eng.auburn.edu.moneyapp.addTransaction;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
import comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments.AddNewCategoryDialogFragment;
import comp3710.csse.eng.auburn.edu.moneyapp.home.HomeActivity;


public class AddTransactionActivity extends ActionBarActivity
		implements ChooseCategoriesFragment.OnFragmentInteractionListener,
		ListTransactionCategoriesFragment.OnFragmentInteractionListener,
		PopulateTransactionFragment.OnFragmentInteractionListener {

	public ArrayList<Transaction> transactionCategories;
	private String mType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_transaction);

		Intent intent = getIntent();
		mType = intent.getStringExtra("type");

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		ChooseCategoriesFragment fragment = ChooseCategoriesFragment.newInstance(mType);
		fragmentTransaction.add(R.id.fragment_container, fragment);
		fragmentTransaction.commit();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_add_transaction, menu);
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

	public void onAcceptTransactionCategories(ArrayList<Transaction> categoryTransactions) {
		transactionCategories = categoryTransactions;

		// Create fragment and give it an argument specifying the article it should show
		ListTransactionCategoriesFragment f = new ListTransactionCategoriesFragment();

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack so the user can navigate back
		transaction.replace(R.id.fragment_container, f);
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();
	}

	public void addNewCategory(String name) {
		Log.d("home", "HomeActivity passed name: " + name);
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getApplicationContext());
		helper.addCategory(new Category(name));

		//((ChooseCategoriesDialogFragment) getSupportFragmentManager().findFragmentById(R.id.choose_categories_dialog)).refresh();
	}

	public ArrayList<Transaction> onFragmentInteraction8() {
		return transactionCategories;
	}

	public void onEditTransaction(int transactionIndex) {

		Transaction t = transactionCategories.get(transactionIndex);

		// Create fragment and give it an argument specifying the article it should show
		PopulateTransactionFragment f = PopulateTransactionFragment.newInstance(t, transactionIndex);

		/*Bundle args = new Bundle();
		args.putInt("index", transactionIndex);
		args.putString("category", t.getCategory().getName());
		args.putString("type", t.getType());

		f.setArguments(args);*/


		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack so the user can navigate back
		transaction.replace(R.id.fragment_container, f);
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();
	}

	public void onCompleteTransaction(Transaction t, int index) {

		int i = 0;

		transactionCategories.set(index, t);

		// Create fragment and give it an argument specifying the article it should show
		ListTransactionCategoriesFragment f = new ListTransactionCategoriesFragment();

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack so the user can navigate back
		transaction.replace(R.id.fragment_container, f);
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();
	}

	public void onCompleteTransactions() {
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getApplicationContext());

		for (int i = 0; i < transactionCategories.size(); i++) {
			//helper.addTransaction(transactionCategories.get(i));
		}

		//ArrayList<Transaction> transactions = helper.getAllTransactions();

		//for (Transaction transaction : transactions)
		//	Log.d("db9", transaction.toString());

		Intent intent = new Intent(AddTransactionActivity.this, HomeActivity.class);

		//intent.putExtra("type", "withdrawal");
		startActivity(intent);

	}



}
