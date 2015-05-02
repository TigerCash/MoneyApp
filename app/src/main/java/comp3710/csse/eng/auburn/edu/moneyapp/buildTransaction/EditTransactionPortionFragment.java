package comp3710.csse.eng.auburn.edu.moneyapp.buildTransaction;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.TransactionPortion;
import comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments.AddNewCategoryDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditTransactionPortionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditTransactionPortionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTransactionPortionFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	private Button mAcceptButton;
	private TextView mAddNewCategoryText;
	private EditText mDescriptionEditText;
	private EditText mAmountEditText;
	private Spinner mCategorySpinner;

	private TransactionPortion mTransactionPortion;


	public static EditTransactionPortionFragment newInstance(TransactionPortion transactionPortion) {
		EditTransactionPortionFragment fragment = new EditTransactionPortionFragment();
		Bundle args = new Bundle();

		args.putInt("id", transactionPortion.getId());
		args.putString("description", transactionPortion.getDescription());
		args.putString("amount", transactionPortion.getAmount());
		args.putInt("category_id", transactionPortion.getCategoryId());
		args.putInt("transaction_id", transactionPortion.getTransactionId());

		fragment.setArguments(args);
		return fragment;
	}

	public EditTransactionPortionFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TransactionPortion transactionPortion = new TransactionPortion();

		Bundle args = getArguments();
		if (args != null) {
			transactionPortion.setId(args.getInt("id"));
			transactionPortion.setDescription(args.getString("description"));
			transactionPortion.setAmount(args.getString("amount"));
			transactionPortion.setCategoryId(args.getInt("category_id"));
			transactionPortion.setTransactionId(args.getInt("transaction_id"));

			mTransactionPortion = transactionPortion;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_edit_transaction_portion, container, false);

		mAcceptButton = (Button) v.findViewById(R.id.accept_button);
		mAcceptButton.setOnClickListener(acceptTransactionPortionListener);

		mAddNewCategoryText = (TextView) v.findViewById(R.id.add_new_category_button);
		mAddNewCategoryText.setOnClickListener(addNewCategoryListener);

		if (mTransactionPortion != null) {
			mDescriptionEditText = (EditText) v.findViewById(R.id.description_edit_text);
			mDescriptionEditText.setText(mTransactionPortion.getDescription());

			mAmountEditText = (EditText) v.findViewById(R.id.amount_edit_text);
			mAmountEditText.setText(mTransactionPortion.getAmount());

			mCategorySpinner = (Spinner) v.findViewById(R.id.category_spinner);
			// TODO: Set selected category
		}

		return v;
	}

	View.OnClickListener acceptTransactionPortionListener = new View.OnClickListener() {
		public void onClick(View v) {



			// validate

			// if valid
			if (mListener != null) {
				mListener.onCompleteTransactionPortion(mTransactionPortion);
			}
		}
	};

	View.OnClickListener addNewCategoryListener = new View.OnClickListener() {
		public void onClick(View v) {

			// Launch AddNewCategoryDialogFragment
			// Create an instance of the dialog fragment and show it
			DialogFragment dialog = new AddNewCategoryDialogFragment();
			dialog.setTargetFragment(EditTransactionPortionFragment.this, 0);
			dialog.show(getActivity().getSupportFragmentManager(), "AddNewCategoryDialogFragment");
		}
	};

	public void onAddNewCategory() {
		// Refresh category spinner
	}

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
		public void onCompleteTransactionPortion(TransactionPortion transactionPortion);
	}

}
