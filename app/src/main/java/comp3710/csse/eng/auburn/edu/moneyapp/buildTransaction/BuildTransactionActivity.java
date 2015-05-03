package comp3710.csse.eng.auburn.edu.moneyapp.buildTransaction;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.TransactionPortion;
import comp3710.csse.eng.auburn.edu.moneyapp.home.HomeActivity;

public class BuildTransactionActivity extends ActionBarActivity
		implements EditTransactionFragment.OnFragmentInteractionListener,
				   EditTransactionPortionFragment.OnFragmentInteractionListener{

	public Transaction buildTransaction = new Transaction();




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_build_transaction);

		if (savedInstanceState == null) {

			Intent intent = getIntent();

			Transaction transaction = intent.getParcelableExtra("transaction");
			TransactionPortion transactionPortion = intent.getParcelableExtra("transactionPortion");
			if (transaction != null) {
				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

				MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getApplicationContext());

				buildTransaction = transaction;
				buildTransaction.setTransactionPortions(helper.getTransactionPortions(buildTransaction.getId()));
				helper.deleteTransaction(buildTransaction.getId());
				helper.deleteTransactionPortions(buildTransaction.getId());
				buildTransaction.setId(0);
				for (int i = 0; i < buildTransaction.getTransactionPortions().size(); i++) {
					buildTransaction.getTransactionPortions().get(i).setId(0);
				}
				EditTransactionFragment fragment = new EditTransactionFragment();
				fragmentTransaction.add(R.id.widget_fragment_container, fragment, "edit_transaction");
				fragmentTransaction.commit();
			} else if (transactionPortion != null) {
				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

				EditTransactionPortionFragment fragment = EditTransactionPortionFragment.newInstance(transactionPortion);
				fragmentTransaction.add(R.id.widget_fragment_container, fragment, "edit_transaction_portion");
				fragmentTransaction.commit();
			} else {


				String type = intent.getStringExtra("type");
				buildTransaction.setType(type);


				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

				EditTransactionFragment fragment = new EditTransactionFragment();
				fragmentTransaction.add(R.id.widget_fragment_container, fragment, "edit_transaction");
				fragmentTransaction.commit();
			}
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_build_transaction, menu);
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


	public void onCompleteTransaction(Transaction transaction) {



		// Launch HomeActivity
		Intent intent = new Intent(BuildTransactionActivity.this, HomeActivity.class);
		startActivity(intent);

	}

	public void onAddTransactionPortion() {

		// Create fragment and give it an argument specifying the article it should show
		EditTransactionPortionFragment f = new EditTransactionPortionFragment();

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this fragment,
		// and add the buildTransaction to the back stack so the user can navigate back
		transaction.replace(R.id.widget_fragment_container, f);
		transaction.addToBackStack(null);

		// Commit the buildTransaction
		transaction.commit();
	}

	public void onCompleteTransactionPortion(TransactionPortion transactionPortion) {

		if (transactionPortion != null) {
			if (transactionPortion.getId() != 0) {
				// If transactionPortion has ID, then we update transactionPortion in DB
				MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getApplicationContext());
				helper.updateTransactionPortion(transactionPortion);

				// and MUST return to Home Activity
				Intent intent = new Intent(BuildTransactionActivity.this, HomeActivity.class);
				startActivity(intent);


			} else {
				// If transactionPortion doesn't have ID, then return to EditTransactionFragment

				// Launch EditTransactionFragment and pass transactionPortion

				// Create fragment and give it an argument specifying the article it should show
				EditTransactionFragment f = EditTransactionFragment.newInstance(transactionPortion);

				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

				// Replace whatever is in the fragment_container view with this fragment,
				// and add the buildTransaction to the back stack so the user can navigate back
				transaction.replace(R.id.widget_fragment_container, f);
				transaction.addToBackStack(null);

				// Commit the buildTransaction
				transaction.commit();
			}
		}
	}

	public void onEditTransactionPortion(TransactionPortion transactionPortion) {
		// Launch EditTransactionPortionFragment(transactionPortion)

		// Create fragment and give it an argument specifying the article it should show
		EditTransactionPortionFragment f = EditTransactionPortionFragment.newInstance(transactionPortion);

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this fragment,
		// and add the buildTransaction to the back stack so the user can navigate back
		transaction.replace(R.id.widget_fragment_container, f);
		transaction.addToBackStack(null);

		// Commit the buildTransaction
		transaction.commit();
	}
}
