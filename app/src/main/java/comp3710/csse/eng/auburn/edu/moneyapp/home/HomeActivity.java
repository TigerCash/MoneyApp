package comp3710.csse.eng.auburn.edu.moneyapp.home;

import android.content.Intent;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.addTransaction.AddTransactionActivity;
import comp3710.csse.eng.auburn.edu.moneyapp.R;

import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
import comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments.PopulateTransactionDialogFragment;


public class HomeActivity extends ActionBarActivity
		implements RecentTransactionsFragment.OnFragmentInteractionListener,
		PopulateTransactionDialogFragment.OnFragmentInteractionListener {

	Button withdrawal_button;
	Button deposit_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		RecentTransactionsFragment fragment = new RecentTransactionsFragment();
		fragmentTransaction.add(R.id.widget_fragment_container, fragment);
		fragmentTransaction.commit();

		/*MoneyAppDatabaseHelper help = new MoneyAppDatabaseHelper(getApplicationContext());
		help.onUpgrade(help.getWritableDatabase(), 1, 1);
		Transaction t = new Transaction("date", "time", "na3", 5, new Category("cat"), "type");
		help.addTransaction(t);

		Transaction newt = help.getTransaction(t.getId());
		if (newt != null) {
			Log.d("db", newt.getName());
			Log.d("db", String.valueOf(t.getId()));
		}
		else {
			Log.d("db", "no transaction found");
		}

		ArrayList<Transaction> transactions = help.getAllTransactions();

		for (Transaction transaction : transactions)
			Log.d("db6", transaction.toString());*/


		withdrawal_button = (Button) findViewById(R.id.withdrawal_button);
		deposit_button = (Button) findViewById(R.id.deposit_button);
		withdrawal_button.setOnClickListener(withdrawalTransactionHandler);
		deposit_button.setOnClickListener(depositTransactionHandler);

	}

	View.OnClickListener withdrawalTransactionHandler = new View.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(HomeActivity.this, AddTransactionActivity.class);

			intent.putExtra("type", "withdrawal");
			startActivity(intent);
		}
	};

	View.OnClickListener depositTransactionHandler = new View.OnClickListener() {
		public void onClick(View v) {
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

	public void onFragmentInteraction(Uri uri) {

	}

	/*public void onFragmentInteraction2(ArrayList<String> chosenCategories, String type) {
		ArrayList<String> names = new ArrayList<String>();

		for (int i = 0; i < chosenCategories.size(); i++) {
			names.add(chosenCategories.get(i));
		}

		Intent intent = new Intent(this, ScreenSlidePagerActivity.class);
		intent.putExtra(ScreenSlidePagerActivity.NUM_PAGES, chosenCategories.size());
		intent.putExtra(ScreenSlidePagerActivity.TYPE, type);

		intent.putStringArrayListExtra(ScreenSlidePagerActivity.NAMES, names);

		startActivity(intent);
	}*/

	public void onFragmentInteraction3(Uri uri) {

	}


}
