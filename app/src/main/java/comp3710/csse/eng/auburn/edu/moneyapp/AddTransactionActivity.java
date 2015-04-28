package comp3710.csse.eng.auburn.edu.moneyapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments.AddNewCategoryDialogFragment;


public class AddTransactionActivity extends ActionBarActivity
		implements ChooseCategoriesFragment.OnFragmentInteractionListener,
		AddNewCategoryDialogFragment.OnFragmentInteractionListener,
		ListTransactionCategoriesFragment.OnFragmentInteractionListener {

	public ArrayList<String> transactionCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_transaction);

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		ChooseCategoriesFragment fragment = new ChooseCategoriesFragment();
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

	public void onAcceptTransactionCategories(ArrayList<String> chosenCategories) {
		transactionCategories = chosenCategories;

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

	public ArrayList<String> onFragmentInteraction8() {
		return transactionCategories;
	}


}
