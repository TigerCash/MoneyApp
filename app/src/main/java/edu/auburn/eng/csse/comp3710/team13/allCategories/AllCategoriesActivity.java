package edu.auburn.eng.csse.comp3710.team13.allCategories;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import edu.auburn.eng.csse.comp3710.team13.R;
import edu.auburn.eng.csse.comp3710.team13.database.MoneyAppDatabaseHelper;
import edu.auburn.eng.csse.comp3710.team13.database.classes.Category;
import edu.auburn.eng.csse.comp3710.team13.dialogFragments.EditCategoryDialogFragment;


public class AllCategoriesActivity extends ActionBarActivity
		implements AllCategoriesFragment.OnFragmentInteractionListener,
		EditCategoryDialogFragment.OnFragmentInteractionListener {

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
		balance_text.setText(helper.getBalance());
		//balance_text.setText("$999");
	}


	public void editCategory(Category category) {
		// Edit Category
		// Create an instance of the dialog fragment and show it
		DialogFragment dialog = EditCategoryDialogFragment.newInstance(category.getName());
		dialog.show(getSupportFragmentManager(), "EditCategoryDialogFragment");
	}

	public void onEditCategory() {
		Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("all_categories");
		FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
		fragTransaction.detach(currentFragment);
		fragTransaction.attach(currentFragment);
		fragTransaction.commit();
	}
}
