package comp3710.csse.eng.auburn.edu.moneyapp.buildTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.TransactionPortion;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditTransactionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTransactionFragment extends Fragment {

	private OnFragmentInteractionListener mListener;

	private Button mCompleteTransactionButton;
	private TextView mAddTransactionPortionText;
	private EditText mNameEditText;
	private EditText mDateEditText;
	private EditText mTimeEditText;
	private TableLayout mTransactionPortionsTable;



	// TODO: Rename and change types and number of parameters
	public static EditTransactionFragment newInstance(TransactionPortion transactionPortion) {
		EditTransactionFragment fragment = new EditTransactionFragment();
		Bundle args = new Bundle();

		args.putInt("id", transactionPortion.getId());
		args.putString("description", transactionPortion.getDescription());
		args.putString("amount", transactionPortion.getAmount());
		args.putInt("category_id", transactionPortion.getCategoryId());
		args.putInt("transaction_id", transactionPortion.getTransactionId());

		fragment.setArguments(args);
		return fragment;
	}

	/*public static EditTransactionFragment newInstance(Transaction transaction) {
		EditTransactionFragment fragment = new EditTransactionFragment();
		Bundle args = new Bundle();

		args.putInt("id", transactionPortion.getId());
		args.putString("description", transactionPortion.getDescription());
		args.putString("amount", transactionPortion.getAmount());
		args.putInt("category_id", transactionPortion.getCategoryId());
		args.putInt("transaction_id", transactionPortion.getTransactionId());

		fragment.setArguments(args);
		return fragment;
	}*/

	public static EditTransactionFragment newInstance(String type) {
		EditTransactionFragment fragment = new EditTransactionFragment();
		Bundle args = new Bundle();

		args.putString("type", type);

		fragment.setArguments(args);
		return fragment;
	}

	public EditTransactionFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TransactionPortion transactionPortion = new TransactionPortion();

		Bundle args = getArguments();
		if (args != null) {

			if (args.getString("type") != null) {
				((BuildTransactionActivity) getActivity()).buildTransaction.setType(args.getString("type"));
			}

			transactionPortion.setId(args.getInt("id"));
			transactionPortion.setDescription(args.getString("description"));
			transactionPortion.setAmount(args.getString("amount"));
			transactionPortion.setCategoryId(args.getInt("category_id"));
			transactionPortion.setTransactionId(args.getInt("transaction_id"));

			((BuildTransactionActivity)getActivity()).buildTransaction.addTransactionPortion(transactionPortion);

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_edit_transaction, container, false);

		mCompleteTransactionButton = (Button) v.findViewById(R.id.complete_transaction_button);
		mCompleteTransactionButton.setOnClickListener(completeTransactionListener);

		mAddTransactionPortionText = (TextView) v.findViewById(R.id.add_transaction_portion_text);
		mAddTransactionPortionText.setOnClickListener(addTransactionPortionListener);

		Transaction transaction = ((BuildTransactionActivity)getActivity()).buildTransaction;

		// Fill Name
		mNameEditText = (EditText) v.findViewById(R.id.name_edit_text);
		mNameEditText.setText(transaction.getName());

		// Fill Date and Time
		mDateEditText = (EditText) v.findViewById(R.id.date_edit_text);
		String date = transaction.getDate();
		mDateEditText.setText(transaction.getDate());
		mTimeEditText = (EditText) v.findViewById(R.id.time_edit_text);
		String time = transaction.getTime();
		mTimeEditText.setText(transaction.getTime());

		// Fill tablelayout with transactionPortions
		mTransactionPortionsTable = (TableLayout) v.findViewById(R.id.transaction_portions_table);
		ArrayList<TransactionPortion> transactionPortions = ((BuildTransactionActivity)getActivity()).buildTransaction.getTransactionPortions();



		if (transactionPortions != null) {
			TableRow tableRow;
			TextView textView;

			for (int i = 0; i < transactionPortions.size(); i++) {

				tableRow = new TableRow(getActivity());
				tableRow.setTag(i);

				textView = new TextView(getActivity());
				textView.setText(transactionPortions.get(i).getDescription());
				tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

				textView = new TextView(getActivity());
				textView.setText(transactionPortions.get(i).getAmount());
				tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

				textView = new TextView(getActivity());
				MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getBaseContext());
				Category category = helper.getCategory(transactionPortions.get(i).getCategoryId());
				textView.setText(category.getName());
				tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));


				tableRow.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						TableRow tableRow = ((TableRow) v);
						TextView nameTextView = (TextView) tableRow.getChildAt(0);
						Log.d("list", "The name is" + nameTextView.getText().toString());
						int transactionRowIndex = (int) tableRow.getTag();
						Log.d("list", Integer.toString(transactionRowIndex));
						// Populate Transaction
						if (mListener != null) {
							//mListener.onEditTransaction(transactionRowIndex);

							// Delete this transactionPortion object from BuildTransaction buildTransaction's transactionPortions
							// It will be re-added when it is updated in the editTransactionPortionFragment
							// Build transactionPortion
							TransactionPortion transactionPortion = new TransactionPortion();

							String description = ((TextView) tableRow.getChildAt(0)).getText().toString();
							String amount = ((TextView) tableRow.getChildAt(1)).getText().toString();
							String category = ((TextView) tableRow.getChildAt(2)).getText().toString();
							MoneyAppDatabaseHelper helper1 = new MoneyAppDatabaseHelper(getActivity().getBaseContext());
							int category_id = helper1.getCategory(category).getId();
							transactionPortion.setDescription(description);
							transactionPortion.setAmount(amount);
							transactionPortion.setCategoryId(category_id);

							ArrayList<TransactionPortion> transactionPortions1 = ((BuildTransactionActivity)getActivity()).buildTransaction.getTransactionPortions();
							transactionPortions1.remove(transactionPortion);
							((BuildTransactionActivity)getActivity()).buildTransaction.setTransactionPortions(transactionPortions1);

							// Edit this transactionPortion
							mListener.onEditTransactionPortion(transactionPortion);

						}
					}
				});

				mTransactionPortionsTable.addView(tableRow);

			}
		}

		return v;
	}

	View.OnClickListener completeTransactionListener = new View.OnClickListener() {
		public void onClick(View v) {

			//validate fields


			//if valid
			// put together buildTransaction
			Transaction transaction = ((BuildTransactionActivity)getActivity()).buildTransaction;
			View view = v.getRootView();
			transaction.setName(((EditText)view.findViewById(R.id.name_edit_text)).getText().toString());
			transaction.setDate(((EditText)view.findViewById(R.id.date_edit_text)).getText().toString());
			transaction.setTime(((EditText)view.findViewById(R.id.time_edit_text)).getText().toString());

			// buildTransaction portions should already be added in this buildTransaction

			MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
			// If buildTransaction has id, update buildTransaction and transactionPortions
			if (transaction.getId() != 0) {
				helper.updateTransaction(transaction);
				helper.updateTransactionPortions(transaction.getTransactionPortions());

			}
			// Else, add buildTransaction and transactionPortions to DB
			else {

				// Insert buildTransaction and get id of this buildTransaction in the table
				int transactionId = helper.addTransaction(transaction);

				// Set the transaction_id for each transactionPortion in this buildTransaction
				ArrayList<TransactionPortion> transactionPortions = transaction.getTransactionPortions();
				for (int i = 0; i < transactionPortions.size(); i++) {
					transactionPortions.get(i).setTransactionId(transactionId);
				}

				helper.addTransactionPortions(transactionPortions);
			}

			// Call listener out
			if (mListener != null) {
				mListener.onCompleteTransaction(transaction);
			}
		}
	};

	View.OnClickListener addTransactionPortionListener = new View.OnClickListener() {
		public void onClick(View v) {



			if (mListener != null) {
				mListener.onAddTransactionPortion();
			}
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
		public void onCompleteTransaction(Transaction transaction);
		public void onAddTransactionPortion();
		public void onEditTransactionPortion(TransactionPortion transactionPortion);
	}

}
