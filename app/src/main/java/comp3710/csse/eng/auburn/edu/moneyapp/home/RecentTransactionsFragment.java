package comp3710.csse.eng.auburn.edu.moneyapp.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
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

	private ActionMode mActionMode;

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

		TextView all_transactions_text = (TextView) v.findViewById(R.id.all_transactions_text);
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
		}

		return v;
	}

	View.OnClickListener onAllTransactionsListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onAllTransactions();
			}
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
					
					mode.finish();
					return true;
				case R.id.action_delete:
					deleteTransaction(mSelectedTableRow);
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
		public void onDeleteTransaction();
		public void onAllTransactions();
	}

	public void deleteTransaction(TableRow selectedTableRow) {
		promptUserValidation();
	}

	public void promptUserValidation() {
		DialogFragment newFragment = new ValidateDeleteDialogFragment();
		newFragment.setTargetFragment(this, 1);
		newFragment.show(getActivity().getSupportFragmentManager(), "validate");
	}

	public void onValidateDeleteDialogPositiveClick() {
		int transactionId = (int)mSelectedTableRow.getTag(R.id.transaction_id);
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity());

		helper.deleteTransaction(transactionId);

		mListener.onDeleteTransaction();
	}

	public void onValidateDeleteDialogNegativeClick() {
		return;
	}
}
