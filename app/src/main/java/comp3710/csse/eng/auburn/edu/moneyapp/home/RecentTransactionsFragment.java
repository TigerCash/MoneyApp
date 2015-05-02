package comp3710.csse.eng.auburn.edu.moneyapp.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comp3710.csse.eng.auburn.edu.moneyapp.ExpandableListAdapter;
import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.TransactionPortion;
import comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments.ValidateDeleteDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecentTransactionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecentTransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentTransactionsFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	private static final int NUMBER_OF_TRANSACTIONS = 5;

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;
	private TableLayout mTable;
	private TableRow mSelectedTableRow;

	private LinearLayout mChildView;
	private LinearLayout mParentView;

	private ActionMode mActionMode;

	// Array of strings storing country names
	String[] countries = new String[] {
			"India",
			"Pakistan",
			"Sri Lanka",
			"China",
			"Bangladesh",
			"Nepal",
			"Afghanistan",
			"North Korea",
			"South Korea",
			"Japan"
	};

	// Array of integers points to images stored in /res/drawable-ldpi/
	int[] flags = new int[]{

	};

	// Array of strings to store currencies
	String[] currency = new String[]{
			"Indian Rupee",
			"Pakistani Rupee",
			"Sri Lankan Rupee",
			"Renminbi",
			"Bangladeshi Taka",
			"Nepalese Rupee",
			"Afghani",
			"North Korean Won",
			"South Korean Won",
			"Japanese Yen"
	};

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment RecentTransactionsFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static RecentTransactionsFragment newInstance(String param1, String param2) {
		RecentTransactionsFragment fragment = new RecentTransactionsFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public RecentTransactionsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_recent_transactions, container, false);

		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity());

		ArrayList<Transaction> recentTransactions = helper.getRecentTransactions(NUMBER_OF_TRANSACTIONS);


		// Each row in the list stores date, time, name, totaled amount
		ArrayList<HashMap<String,String>> transactionList = new ArrayList<HashMap<String,String>>();

		/*for (int i = 0; i < recentTransactions.size(); i++) {
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("date", "Date: " + recentTransactions.get(i).getDate());
			hm.put("time", "Time: " + recentTransactions.get(i).getTime());
			hm.put("name", "Name: " + recentTransactions.get(i).getName());
			hm.put("total", "Total: " + recentTransactions.get(i).getTotal());
			transactionList.add(hm);
		}*/

		for (int i = 0; i < recentTransactions.size(); i++) {
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("date", recentTransactions.get(i).getDate());
			hm.put("time", recentTransactions.get(i).getTime());
			hm.put("name", recentTransactions.get(i).getName());
			hm.put("total", recentTransactions.get(i).getTotal());
			hm.put("id", String.valueOf(recentTransactions.get(i).getId()));
			transactionList.add(hm);
		}

		HashMap<HashMap<String,String>, ArrayList<HashMap<String,String>>> transactionListPortion = new  HashMap<HashMap<String,String>, ArrayList<HashMap<String,String>>>();


		/*for (int i = 0; i < transactionList.size(); i++) {

			ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String,String>>();
			ArrayList<TransactionPortion> transactionPortions = recentTransactions.get(i).getTransactionPortions();
			for (int j = 0; j < transactionPortions.size(); j++) {
				HashMap<String,String> hm = new HashMap<String,String>();
				hm.put("desc", "Description: " + transactionPortions.get(j).getDescription());
				hm.put("amount", "Amount: " + transactionPortions.get(j).getAmount());
				Category category = helper.getCategory(transactionPortions.get(j).getCategoryId());
				hm.put("category", "Category: " + category.getName());
				al.add(hm);
			}
			transactionListPortion.put(transactionList.get(i), al);

		}*/

		for (int i = 0; i < transactionList.size(); i++) {

			ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String,String>>();
			ArrayList<TransactionPortion> transactionPortions = recentTransactions.get(i).getTransactionPortions();
			for (int j = 0; j < transactionPortions.size(); j++) {
				HashMap<String,String> hm = new HashMap<String,String>();
				hm.put("desc", transactionPortions.get(j).getDescription());
				hm.put("amount", transactionPortions.get(j).getAmount());
				Category category = helper.getCategory(transactionPortions.get(j).getCategoryId());
				hm.put("category", category.getName());
				hm.put("id", String.valueOf(transactionPortions.get(j).getId()));
				al.add(hm);
			}
			transactionListPortion.put(transactionList.get(i), al);

		}




		// Each row in the list stores country name, currency and flag
		ArrayList<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();


		for(int i=0;i<10;i++){
			HashMap<String, String> hm = new HashMap<String,String>();
			hm.put("txt", "Country : " + countries[i]);
			hm.put("cur","Currency : " + currency[i]);
			hm.put("flag", countries[i] );
			aList.add(hm);
		}

		// Keys used in Hashmap
		String[] from = { "flag","txt","cur" };

		// Ids of views in listview_layout
		//int[] to = { R.id.txt,R.id.txt,R.id.cur};

		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		//SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.listview_layout, from, to);

		// Getting a reference to listview of main.xml layout file
		//ListView listView = ( ListView ) v.findViewById(R.id.recent_transactions_list_view);

		// Setting the adapter to the listView
		//listView.setAdapter(adapter);


		// get the listview
		ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.recent_transactions_list_view);

		// preparing list data
		//prepareListData();

		ExpandableListAdapter adapter2;


		ArrayList<String> listDataHeader = new ArrayList<String>();
		HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Top 250");
		listDataHeader.add("Now Showing");
		listDataHeader.add("Coming Soon..");

		// Adding child data
		List<String> top250 = new ArrayList<String>();
		top250.add("The Shawshank Redemption");
		top250.add("The Godfather");
		top250.add("The Godfather: Part II");
		top250.add("Pulp Fiction");
		top250.add("The Good, the Bad and the Ugly");
		top250.add("The Dark Knight");
		top250.add("12 Angry Men");

		List<String> nowShowing = new ArrayList<String>();
		nowShowing.add("The Conjuring");
		nowShowing.add("Despicable Me 2");
		nowShowing.add("Turbo");
		nowShowing.add("Grown Ups 2");
		nowShowing.add("Red 2");
		nowShowing.add("The Wolverine");

		List<String> comingSoon = new ArrayList<String>();
		comingSoon.add("2 Guns");
		comingSoon.add("The Smurfs 2");
		comingSoon.add("The Spectacular Now");
		comingSoon.add("The Canyons");
		comingSoon.add("Europa Report");

		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		listDataChild.put(listDataHeader.get(1), nowShowing);
		listDataChild.put(listDataHeader.get(2), comingSoon);

		//adapter2 = new ExpandableListAdapter(getActivity().getBaseContext(), listDataHeader, listDataChild);

		//ArrayList<HashMap<String,String>>
		HashMap<HashMap<String,String>, ArrayList<HashMap<String,String>>> aListDataChild = new  HashMap<HashMap<String,String>, ArrayList<HashMap<String,String>>>();
		aListDataChild.put(aList.get(0), aList);





		adapter2 = new ExpandableListAdapter(getActivity().getBaseContext(), transactionList, transactionListPortion);

		// setting list adapter
		listView.setAdapter(adapter2);

		listView.setOnItemLongClickListener(onItemLongClickListener);

		/*TextView all_transactions_text = (TextView) v.findViewById(R.id.all_transactions_text);
		all_transactions_text.setOnClickListener(onAllTransactionsListener);

		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity());

		ArrayList<Transaction> recentTransactions = helper.getRecentTransactions(NUMBER_OF_TRANSACTIONS);

		mTable = (TableLayout) v.findViewById(R.id.recent_transactions_table);

		TableRow tableRow;
		TextView textView;

		for (int i = 0; i < recentTransactions.size(); i++) {

			tableRow = new TableRow(getActivity());
			tableRow.setTag(R.id.row_index, i);
			tableRow.setTag(R.id.transaction_id, recentTransactions.get(i).getId());

			textView = new TextView(getActivity());
			textView.setText(recentTransactions.get(i).getDate());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			textView = new TextView(getActivity());
			textView.setText(recentTransactions.get(i).getTime());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			textView = new TextView(getActivity());
			textView.setText(recentTransactions.get(i).getName());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));


			textView = new TextView(getActivity());
			int amount = recentTransactions.get(i).getAmount();
			String amountString = "";
			String type = recentTransactions.get(i).getType();
			if (type.equals("withdrawal")) {
				amountString = "-" + Integer.toString(amount);
			}
			else if (type.equals("deposit")) {
				amountString = "+" + Integer.toString(amount);
			}
			textView.setText(amountString);
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			textView = new TextView(getActivity());
			textView.setText(recentTransactions.get(i).getCategory().getName());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			tableRow.setOnLongClickListener(onLongClickListener);

			tableRow.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					TableRow tableRow = ((TableRow) v);
					TextView nameTextView = (TextView) tableRow.getChildAt(0);
					Log.d("list", "The name is" + nameTextView.getText().toString());
					int transactionRowIndex = (int) tableRow.getTag(R.id.row_index);
					Log.d("list", Integer.toString(transactionRowIndex));
					// Populate Transaction
					if (mListener != null) {
						//mListener.onEditTransaction(transactionRowIndex);
					}
				}
			});

			mTable.addView(tableRow);
		}*/

		return v;
	}


	AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View childView, int flatPos, long id) {
			if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
				final ExpandableListAdapter adapter = (ExpandableListAdapter) ((ExpandableListView) parent).getExpandableListAdapter();
				long packedPos = ((ExpandableListView) parent).getExpandableListPosition(flatPos);
				int groupPosition = ExpandableListView.getPackedPositionGroup(packedPos);
				int childPosition = ExpandableListView.getPackedPositionChild(packedPos);

				//do your child callback here
				Log.d("recent", "onitemlongclicklistern child");

				if (mActionMode != null) {
					return false;
				}

				// Start the CAB using the ActionMode.Callback defined above
				ActionBarActivity activity=(ActionBarActivity)getActivity();
				activity.startSupportActionMode(mActionModeCallback);

				mChildView = (LinearLayout) childView;
				mParentView = (LinearLayout) parent.getChildAt(groupPosition);

				/*mSelectedTableRow = (TableRow) v;

				v.setSelected(true);*/

				return true;

			} else if(ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
				final ExpandableListAdapter adapter = (ExpandableListAdapter) ((ExpandableListView) parent).getExpandableListAdapter();
				long packedPos = ((ExpandableListView) parent).getExpandableListPosition(flatPos);
				int groupPosition = ExpandableListView.getPackedPositionGroup(packedPos);

				//do your group callback here
				Log.d("recent", "onitemlongclicklistern group");

				if (mActionMode != null) {
					return false;
				}

				// Start the CAB using the ActionMode.Callback defined above
				ActionBarActivity activity=(ActionBarActivity)getActivity();
				activity.startSupportActionMode(mActionModeCallback);

				mChildView = null;
				mParentView = (LinearLayout) parent.getChildAt(groupPosition);

				/*mSelectedTableRow = (TableRow) v;

				v.setSelected(true);*/

				return true; //true if we consumed the click, false if not

			} else {
				// null item; we don't consume the click
				return false;
			}

		}
	};

	View.OnClickListener onAllTransactionsListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			/*if (mListener != null) {
				//mListener.onAllTransactions();
			}*/
		}
	};

	View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {

			if (mActionMode != null) {
				return false;
			}

			// Start the CAB using the ActionMode.Callback defined above
			ActionBarActivity activity=(ActionBarActivity)getActivity();
			activity.startSupportActionMode(mActionModeCallback);
			//mActionMode = getActivity().startSupportActionMode(mActionModeCallback);

			mSelectedTableRow = (TableRow) v;

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
				case R.id.action_edit:
					//editTransaction(mSelectedTableRow);
					if (mChildView != null)
						editTransactionPortion(mChildView, mParentView);
					else
						editTransaction(mParentView);
					mode.finish();
					return true;
				case R.id.action_delete:
					//deleteTransaction(mSelectedTableRow);
					mode.finish();
					return true;
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
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p/>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		/*public void onDeleteTransaction();
		public void onAllTransactions();*/
		public void editTransaction(Transaction transaction);
		public void editTransactionPortion(TransactionPortion transactionPortion);
	}

	public void editTransactionPortion(LinearLayout childView, LinearLayout parentView) {
		// Get transactionPortion so it can be edited
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
		TransactionPortion transactionPortion = helper.getTransactionPortion((int) childView.getTag());

		/*int t1 = (int) childView.getTag();
		int t2 = (int) parentView.getTag();*/
		mListener.editTransactionPortion(transactionPortion);
	}

	public void editTransaction(LinearLayout parentView) {

	}

	public void editTransaction(TableRow selectedTableRow) {

		int transactionId = (int)selectedTableRow.getTag(R.id.transaction_id);
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity());

		//Transaction transaction = helper.getTransaction(transactionId);

		//mListener.editTransaction(transaction);
	}

	public void deleteTransaction(TableRow selectedTableRow) {
		promptUserValidation();
	}

	public void promptUserValidation() {
		DialogFragment newFragment = new ValidateDeleteDialogFragment();
		//newFragment.setTargetFragment(this, 1);
		newFragment.show(getActivity().getSupportFragmentManager(), "validate");
	}

	public void onValidateDeleteDialogPositiveClick() {
		int transactionId = (int)mSelectedTableRow.getTag(R.id.transaction_id);
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity());

		//helper.deleteTransaction(transactionId);

		//mListener.onDeleteTransaction();
	}

	public void onValidateDeleteDialogNegativeClick() {
		return;
	}
}
