package comp3710.csse.eng.auburn.edu.moneyapp.allTransactions;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.buildTransaction.BuildTransactionActivity;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.TransactionPortion;
import comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments.ChooseTransactionTypeDialogFragment;
import comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments.EditTransactionDialogFragment;
import comp3710.csse.eng.auburn.edu.moneyapp.home.RecentTransactionsFragment;

public class AllTransactionsActivity extends ActionBarActivity
		implements AllTransactionsFragment.OnFragmentInteractionListener {

	TextView balance_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_transactions);

		/*MoneyAppDatabaseHelper help = new MoneyAppDatabaseHelper(getApplicationContext());
		help.onUpgrade(help.getWritableDatabase(), 1, 1);*/
		/*CategoryTable t = new CategoryTable();
		t.onUpgrade(help.getWritableDatabase(), 1, 1);*/

		if (savedInstanceState == null) {

			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

			AllTransactionsFragment fragment = new AllTransactionsFragment();
			fragmentTransaction.add(R.id.widget_fragment_container, fragment, "all_transactions");
			fragmentTransaction.commit();
		}

		//MoneyAppDatabaseHelper help = new MoneyAppDatabaseHelper(getApplicationContext());
		//help.onUpgrade(help.getWritableDatabase(), 1, 1);
		/*Transaction t = new Transaction("date", "time", "na3", 5, new Category("cat"), "type");
		help.addTransaction(t);

		Transaction newt = help.getTransaction(t.getId());
		if (newt != null) {
			Log.d("db", newt.getName());
			Log.d("db", String.valueOf(t.getId()));
		}
		else {
			Log.d("db", "no buildTransaction found");
		}

		ArrayList<Transaction> transactions = help.getAllTransactions();

		for (Transaction buildTransaction : transactions)
			Log.d("db6", buildTransaction.toString());*/

		setBalanceText();

	}

	View.OnClickListener addTransactionButtonHandler = new View.OnClickListener() {
		public void onClick(View v) {
			/*Intent intent = new Intent(HomeActivity.this, AddTransactionActivity.class);

			intent.putExtra("type", "withdrawal");
			startActivity(intent);*/

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
		//balance_text.setText("$999");
	}

	public void onDeleteTransaction() {
		Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("recent_transactions");
		FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
		fragTransaction.detach(currentFragment);
		fragTransaction.attach(currentFragment);
		fragTransaction.commit();

		setBalanceText();
	}



	public void onFragmentInteraction3(Uri uri) {

	}


	public void onEditTransaction(Transaction transaction) {

		/*MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(this);

		helper.updateTransaction(buildTransaction);

		ArrayList<Transaction> trans = helper.getAllTransactions();

		Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("recent_transactions");
		FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
		fragTransaction.detach(currentFragment);
		fragTransaction.attach(currentFragment);
		fragTransaction.commit();

		setBalanceText();*/
	}


	public void balanceChanged() {
		setBalanceText();
	}



	public void editTransaction(Transaction transaction) {
		/*int i = 0;
		Transaction t = buildTransaction;
		DialogFragment newFragment = EditTransactionDialogFragment.newInstance(buildTransaction);
		newFragment.show(this.getSupportFragmentManager(), "edit_transaction");*/

		Intent intent = new Intent(AllTransactionsActivity.this, BuildTransactionActivity.class);

		intent.putExtra("transaction", transaction);
		startActivity(intent);
	}

	public void editTransactionPortion(TransactionPortion transactionPortion) {
		Intent intent = new Intent(AllTransactionsActivity.this, BuildTransactionActivity.class);

		intent.putExtra("transactionPortion", transactionPortion);
		startActivity(intent);
	}
}
