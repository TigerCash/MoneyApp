package comp3710.csse.eng.auburn.edu.moneyapp.buildTransaction;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
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
				((BuildTransactionActivity) getActivity()).transaction.setType(args.getString("type"));
			}
			else {
				transactionPortion.setId(args.getInt("id"));
				transactionPortion.setDescription(args.getString("description"));
				transactionPortion.setAmount(args.getString("amount"));
				transactionPortion.setCategoryId(args.getInt("category_id"));
				transactionPortion.setTransactionId(args.getInt("transaction_id"));

				((BuildTransactionActivity)getActivity()).transaction.addTransactionPortion(transactionPortion);
			}
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

		Transaction transaction = ((BuildTransactionActivity)getActivity()).transaction;

		// Fill Name
		mNameEditText = (EditText) v.findViewById(R.id.name_edit_text);
		mNameEditText.setText(transaction.getName());

		// Fill Date and Time
		mDateEditText = (EditText) v.findViewById(R.id.date_edit_text);
		mDateEditText.setText(transaction.getDate());
		mTimeEditText = (EditText) v.findViewById(R.id.time_edit_text);
		mDateEditText.setText(transaction.getTime());

		// Fill tablelayout with transactionPortions
		mTransactionPortionsTable = (TableLayout) v.findViewById(R.id.transaction_portions_table);




		return v;
	}

	View.OnClickListener completeTransactionListener = new View.OnClickListener() {
		public void onClick(View v) {

			//put together transaction

			//validate

			//if valid

			if (mListener != null) {
				//mListener.onCompleteTransaction(transaction);
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
	}

}