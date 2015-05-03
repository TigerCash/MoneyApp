package comp3710.csse.eng.auburn.edu.moneyapp.allTransactions;

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


public class AllTransactionsFragment extends Fragment {
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

	ExpandableListAdapter adapter2;
	ExpandableListView listView;




	public static AllTransactionsFragment newInstance(String param1, String param2) {
		AllTransactionsFragment fragment = new AllTransactionsFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public AllTransactionsFragment() {
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
		View v = inflater.inflate(R.layout.fragment_all_transactions, container, false);

		setupExpandableListView(v);

		return v;
	}

	public void setupExpandableListView(View v) {
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity());

		ArrayList<Transaction> allTransactions = helper.getAllTransactions();


		// Each row in the list stores date, time, name, totaled amount
		ArrayList<HashMap<String,String>> transactionList = new ArrayList<HashMap<String,String>>();


		for (int i = 0; i < allTransactions.size(); i++) {
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("date", allTransactions.get(i).getDate());
			hm.put("time", allTransactions.get(i).getTime());
			hm.put("name", allTransactions.get(i).getName());

			Double total = Double.parseDouble(allTransactions.get(i).getTotal());

			if (total < 0) {
				hm.put("total", "(" + Double.toString(total).substring(1) + ")");
			}
			else {
				hm.put("total", Double.toString(total));
			}

			hm.put("id", String.valueOf(allTransactions.get(i).getId()));
			transactionList.add(hm);
		}

		HashMap<HashMap<String,String>, ArrayList<HashMap<String,String>>> transactionListPortion = new  HashMap<HashMap<String,String>, ArrayList<HashMap<String,String>>>();


		for (int i = 0; i < transactionList.size(); i++) {

			ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String,String>>();
			ArrayList<TransactionPortion> transactionPortions = allTransactions.get(i).getTransactionPortions();
			for (int j = 0; j < transactionPortions.size(); j++) {
				HashMap<String,String> hm = new HashMap<String,String>();
				hm.put("desc", transactionPortions.get(j).getDescription());
				if (allTransactions.get(i).getType().equals("Withdrawal")) {
					hm.put("amount", "(" + transactionPortions.get(j).getAmount() + ")");
				}
				else {
					hm.put("amount", transactionPortions.get(j).getAmount());
				}
				Category category = helper.getCategory(transactionPortions.get(j).getCategoryId());
				hm.put("category", category.getName());
				hm.put("id", String.valueOf(transactionPortions.get(j).getId()));
				al.add(hm);
			}
			transactionListPortion.put(transactionList.get(i), al);

		}



		// Each row in the list stores country name, currency and flag
		ArrayList<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();



		// get the listview
		listView = (ExpandableListView) v.findViewById(R.id.all_transactions_list_view);


		adapter2 = new ExpandableListAdapter(getActivity().getBaseContext(), transactionList, transactionListPortion);

		// setting list adapter
		listView.setAdapter(adapter2);


		listView.setOnItemLongClickListener(onItemLongClickListener);
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
					if (mChildView != null)
						deleteTransactionPortion(mChildView, mParentView);
					else
						deleteTransaction(mParentView);

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

	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void editTransaction(Transaction transaction);
		public void editTransactionPortion(TransactionPortion transactionPortion);
		public void balanceChanged();
	}

	public void editTransactionPortion(LinearLayout childView, LinearLayout parentView) {
		// Get transactionPortion so it can be edited
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
		TransactionPortion transactionPortion = helper.getTransactionPortion((int) childView.getTag());

		mListener.editTransactionPortion(transactionPortion);
	}

	public void editTransaction(LinearLayout parentView) {
		// Get buildTransaction so it can be edited
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
		Transaction transaction = helper.getTransaction((int) parentView.getTag());

		mListener.editTransaction(transaction);

	}

	public void deleteTransactionPortion(LinearLayout childView, LinearLayout parentView) {
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
		int transactionId = helper.getTransactionPortion((int) childView.getTag()).getTransactionId();
		helper.deleteTransactionPortion((int) childView.getTag());

		// Get number of transaction portions remaining for this transaction after this deletion
		int remainingTransactionPortions = helper.getTransactionPortions(transactionId).size();
		// If no transaction portions left, delete transaction
		if (remainingTransactionPortions == 0) {
			helper.deleteTransaction(transactionId);
		}

		setupExpandableListView(parentView.getRootView());
		adapter2.notifyDataSetChanged();
		mListener.balanceChanged();
	}

	public void deleteTransaction(LinearLayout parentView) {
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
		helper.deleteTransaction((int) parentView.getTag());

		setupExpandableListView(parentView.getRootView());
		adapter2.notifyDataSetChanged();
		mListener.balanceChanged();
	}
}
