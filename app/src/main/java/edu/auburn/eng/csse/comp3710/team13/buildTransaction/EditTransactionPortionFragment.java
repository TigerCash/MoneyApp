package edu.auburn.eng.csse.comp3710.team13.buildTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team13.R;
import edu.auburn.eng.csse.comp3710.team13.database.MoneyAppDatabaseHelper;
import edu.auburn.eng.csse.comp3710.team13.database.classes.Category;
import edu.auburn.eng.csse.comp3710.team13.database.classes.TransactionPortion;
import edu.auburn.eng.csse.comp3710.team13.dialogFragments.AddNewCategoryDialogFragment;

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

	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> mCategoryNames;

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

		mDescriptionEditText = (EditText) v.findViewById(R.id.description_edit_text);
		mAmountEditText = (EditText) v.findViewById(R.id.amount_edit_text);

		mCategorySpinner = (Spinner) v.findViewById(R.id.category_spinner);
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
		ArrayList<Category> categories = helper.getAllCategories();
		mCategoryNames = new ArrayList<String>();
		for (int i = 0; i < categories.size(); i++) {
			mCategoryNames.add(categories.get(i).getName());
		}
		mAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, mCategoryNames);
		mCategorySpinner.setAdapter(mAdapter);

		if (mTransactionPortion != null) {
			mDescriptionEditText.setText(mTransactionPortion.getDescription());
			mAmountEditText.setText(mTransactionPortion.getAmount());
		}

		return v;
	}

	View.OnClickListener acceptTransactionPortionListener = new View.OnClickListener() {
		public void onClick(View v) {

			//validate fields
			boolean validFields = checkValidFields(v);

			//if valid
			if (validFields) {

				// if valid
				// put together transactionportion
				if (mTransactionPortion == null)
					mTransactionPortion = new TransactionPortion();
				View view = v.getRootView();
				mTransactionPortion.setDescription(((EditText) view.findViewById(R.id.description_edit_text)).getText().toString());
				mTransactionPortion.setAmount(((EditText) view.findViewById(R.id.amount_edit_text)).getText().toString());
				String categoryName = ((Spinner) view.findViewById(R.id.category_spinner)).getSelectedItem().toString();
				MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
				int categoryId = helper.getCategory(categoryName).getId();
				mTransactionPortion.setCategoryId(categoryId);
				//mTransactionPortion.setTransactionId(((BuildTransactionActivity)getActivity()).buildTransaction.getId());
				if (mListener != null) {
					//((BuildTransactionActivity)getActivity()).buildTransaction.addTransactionPortion(mTransactionPortion);
					mListener.onCompleteTransactionPortion(mTransactionPortion);
				}
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
		ArrayAdapter<String> aa = mAdapter;
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
		ArrayList<Category> categories = helper.getAllCategories();
		mCategoryNames = new ArrayList<String>();
		mAdapter.clear();
		for (int i = 0; i < categories.size(); i++) {
			mAdapter.add(categories.get(i).getName());
		}
		//mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mCategoryNames);
		//mAdapter.notifyDataSetInvalidated();

		//mAdapter.add(mCategoryNames);
		mAdapter.notifyDataSetChanged();

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

		public boolean checkValidFields(View v) {
			boolean valid = true;
			View view = v.getRootView();

			String description = ((EditText)view.findViewById(R.id.description_edit_text)).getText().toString();
			String amount = ((EditText)view.findViewById(R.id.amount_edit_text)).getText().toString();

			Spinner spinner = ((Spinner)view.findViewById(R.id.category_spinner));
			String spinnerString = (String) spinner.getSelectedItem();


			if (description == null || description.equals("")) {
				valid = false;
				Toast.makeText(getActivity().getApplicationContext(), "Invalid Description",
						Toast.LENGTH_SHORT).show();
			}
			else if (amount == null || amount.equals("")) {
				valid = false;
				Toast.makeText(getActivity().getApplicationContext(), "Invalid Amount",
						Toast.LENGTH_SHORT).show();
			}
			else if (spinnerString == null || spinnerString.equals("")) {
				valid = false;
				Toast.makeText(getActivity().getApplicationContext(), "Invalid Spinner Choice",
						Toast.LENGTH_SHORT).show();
			}

			try {
				Double.parseDouble(amount);
			} catch (Exception e) {
				valid = false;
				Toast.makeText(getActivity().getApplicationContext(), "Invalid Amount",
						Toast.LENGTH_SHORT).show();
			}


			return valid;
		}

}
