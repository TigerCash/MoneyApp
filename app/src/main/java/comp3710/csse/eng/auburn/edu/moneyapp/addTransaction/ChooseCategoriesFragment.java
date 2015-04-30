package comp3710.csse.eng.auburn.edu.moneyapp.addTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
import comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments.AddNewCategoryDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChooseCategoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChooseCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseCategoriesFragment extends Fragment
		implements AddNewCategoryDialogFragment.OnAddCategoryListener {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String TYPE_PARAM = "type";

	// TODO: Rename and change types of parameters
	private String mType;

	private LinearLayout checkboxLinearLayout;
	private Button addNewCategoryButton;
	private Button acceptTransactionCategoriesButton;

	private OnFragmentInteractionListener mListener;


	public static ChooseCategoriesFragment newInstance(String type) {
		ChooseCategoriesFragment fragment = new ChooseCategoriesFragment();
		Bundle args = new Bundle();
		args.putString(TYPE_PARAM, type);
		fragment.setArguments(args);
		return fragment;
	}

	public ChooseCategoriesFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mType = getArguments().getString(TYPE_PARAM);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_choose_categories, container, false);

		// Get LinearLayout object that will contain checkboxes from xml
		checkboxLinearLayout = (LinearLayout) v.findViewById(R.id.checkbox_linear_layout);

		// Get reference to add new category button from xml
		addNewCategoryButton = (Button) v.findViewById(R.id.add_new_category_button);
		addNewCategoryButton.setOnClickListener(addNewCategoryHandler);

		acceptTransactionCategoriesButton = (Button) v.findViewById(R.id.accept_categories);
		acceptTransactionCategoriesButton.setOnClickListener(acceptTransactionCategoriesHandler);

		// Inflate the layout for this fragment
		return v;
	}

	View.OnClickListener addNewCategoryHandler = new View.OnClickListener() {
		public void onClick(View v) {

			// Create an instance of the dialog fragment and show it
			DialogFragment dialog = new AddNewCategoryDialogFragment();
			dialog.setTargetFragment(ChooseCategoriesFragment.this, 1);
			dialog.show(getActivity().getSupportFragmentManager(), "AddNewCategoryDialogFragment");
		}
	};

	View.OnClickListener acceptTransactionCategoriesHandler = new View.OnClickListener() {
		public void onClick(View v) {
			if (mListener != null) {
				ArrayList<String> chosenCategories = new ArrayList<String>();

				ArrayList<Transaction> categoryTransactions = new ArrayList<>();

				int numberCheckboxes = checkboxLinearLayout.getChildCount();
				for (int i = 0; i < numberCheckboxes; i++) {
					CheckBox cb = (CheckBox) checkboxLinearLayout.getChildAt(i);
					if (cb.isChecked()) {
						Transaction t = new Transaction();
						t.setCategory(new Category(cb.getText().toString()));
						t.setType(mType);
						categoryTransactions.add(t);
						//chosenCategories.add(cb.getText().toString());
					}
				}

				//mListener.onAcceptTransactionCategories(chosenCategories);
				mListener.onAcceptTransactionCategories(categoryTransactions);
			}
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		refresh();
	}

	public void refresh() {
		if(checkboxLinearLayout.getChildCount() > 0)
			checkboxLinearLayout.removeAllViews();

		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
		ArrayList<Category> categories = helper.getAllCategories();

		CheckBox box;
		for (int i = 0; i < categories.size(); i++) {
			box = new CheckBox(this.getActivity());
			box.setText(categories.get(i).getName());
			checkboxLinearLayout.addView(box);
		}
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
		public void onAcceptTransactionCategories(ArrayList<Transaction> categoryTransactions);
	}

}
