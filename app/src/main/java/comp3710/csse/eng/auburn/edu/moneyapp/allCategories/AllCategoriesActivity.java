package comp3710.csse.eng.auburn.edu.moneyapp.allCategories;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.TopCategoriesListAdapter;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.home.RecentTransactionsFragment;
import comp3710.csse.eng.auburn.edu.moneyapp.home.TopCategoriesFragment;


public class AllCategoriesActivity extends ActionBarActivity
		implements AllCategoriesFragment.OnFragmentInteractionListener {

	TextView balance_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_categories);

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		AllCategoriesFragment fragment = new AllCategoriesFragment();
		fragmentTransaction.add(R.id.widget_fragment_container, fragment, "all_categories");
		fragmentTransaction.commit();


		setBalanceText();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_all_categories, menu);
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
		NumberFormat formatter = new DecimalFormat("#0.00");
		balance_text.setText(formatter.format(helper.getBalance()));
		//balance_text.setText("$999");
	}

	public void onFragmentInteraction(Uri uri) {

	}
}
