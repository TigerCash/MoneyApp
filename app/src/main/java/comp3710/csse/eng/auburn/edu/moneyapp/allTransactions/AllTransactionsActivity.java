package comp3710.csse.eng.auburn.edu.moneyapp.allTransactions;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;

public class AllTransactionsActivity extends ActionBarActivity {

	private TableLayout mTable;
	private ActionMode mActionMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_transactions);


		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(this);

		ArrayList<Transaction> allTransactions = helper.getAllTransactions();

		mTable = (TableLayout) findViewById(R.id.recent_transactions_table);

		TableRow tableRow;
		TextView textView;

		for (int i = 0; i < allTransactions.size(); i++) {

			tableRow = new TableRow(this);
			tableRow.setTag(i);

			textView = new TextView(this);
			textView.setText(allTransactions.get(i).getDate());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			textView = new TextView(this);
			textView.setText(allTransactions.get(i).getTime());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			textView = new TextView(this);
			textView.setText(allTransactions.get(i).getName());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));


			textView = new TextView(this);
			int amount = allTransactions.get(i).getAmount();
			String amountString = "";
			String type = allTransactions.get(i).getType();
			if (type.equals("withdrawal")) {
				amountString = "-" + Integer.toString(amount);
			}
			else if (type.equals("deposit")) {
				amountString = "+" + Integer.toString(amount);
			}
			textView.setText(amountString);
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			textView = new TextView(this);
			textView.setText(allTransactions.get(i).getCategory().getName());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			tableRow.setOnLongClickListener(onLongClickListener);

			tableRow.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					TableRow tableRow = ((TableRow) v);
					TextView nameTextView = (TextView) tableRow.getChildAt(0);
					Log.d("list", "The name is" + nameTextView.getText().toString());
					int transactionRowIndex = (int) tableRow.getTag();
					Log.d("list", Integer.toString(transactionRowIndex));

				}
			});

			mTable.addView(tableRow);
		}

	}

	View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {

			if (mActionMode != null) {
				return false;
			}

			// Start the CAB using the ActionMode.Callback defined above
			mActionMode = startActionMode(mActionModeCallback);
			v.setSelected(true);

			return true;
		}
	};

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.action_bar, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
				/*case R.id.menu_share:
					shareCurrentItem();
					mode.finish(); // Action picked, so close the CAB
					return true;*/
				default:
					return false;
			}
		}

		// Called when the user exits the action mode
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
		}
	};


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_all_transactions, menu);
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
}
