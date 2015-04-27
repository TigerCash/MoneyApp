package comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.ScreenSlidePagerActivity;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChooseCategoriesDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChooseCategoriesDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseCategoriesDialogFragment extends DialogFragment
		implements AddNewCategoryDialogFragment.OnAddCategoryListener {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_TYPE = "type";

	private String mType;

	private OnFragmentInteractionListener mListener;
	private LinearLayout checkboxLinearLayout;
	private Button addNewCategoryButton;


	public static ChooseCategoriesDialogFragment newInstance(String type) {
		ChooseCategoriesDialogFragment fragment = new ChooseCategoriesDialogFragment();
		Bundle args = new Bundle();
		args.putString(ARG_TYPE, type);
		fragment.setArguments(args);
		return fragment;
	}

	public ChooseCategoriesDialogFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mType = getArguments().getString(ARG_TYPE);
		}
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		View v = inflater.inflate(R.layout.fragment_choose_categories_dialog, null);

		// Get LinearLayout object that will contain checkboxes from xml
		checkboxLinearLayout = (LinearLayout) v.findViewById(R.id.checkbox_linear_layout);

		// Get reference to add new category button from xml
		addNewCategoryButton = (Button) v.findViewById(R.id.add_new_category_button);
		addNewCategoryButton.setOnClickListener(addNewCategoryHandler);

		// Pass null as the parent view because its going in the dialog layout
		builder.setView(v)
				// Add action buttons
				.setPositiveButton("positive", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						ArrayList<String> chosenCategories = new ArrayList<String>();

						// Launch Add New Category Dialog for each category selected
						int numberCheckboxes = checkboxLinearLayout.getChildCount();
						for (int i = 0; i < numberCheckboxes; i++) {
							CheckBox cb = (CheckBox) checkboxLinearLayout.getChildAt(i);
							if (cb.isChecked()) {
								chosenCategories.add(cb.getText().toString());
							}
						}

						Intent intent = new Intent(getActivity(), ScreenSlidePagerActivity.class);
						//EditText editText = (EditText) findViewById(R.id.edit_message);
						//String message = editText.getText().toString();
						intent.putExtra(ScreenSlidePagerActivity.NUM_PAGES, chosenCategories.size());
						intent.putExtra(ScreenSlidePagerActivity.TYPE, mType);

						intent.putExtra("test", "test");

						/*ArrayList<String> names = new ArrayList<String>();

						for (int i = 0; i < chosenCategories.size(); i++) {
							names.add(chosenCategories.get(i));
						}

						// Object class does not implement Serializable interface
						Bundle extraNames = new Bundle();
						extraNames.putSerializable("names", names);
						intent.putExtra("extraNames", extraNames);*/


						// getActivity().startActivity(intent);
						//startActivity(intent);

						if (mListener != null) {
							mListener.onFragmentInteraction2(chosenCategories, mType);
						}

						/*for (int i = 0; i < chosenCategories.size(); i++) {
							DialogFragment populateTransactionDialog =
									PopulateTransactionDialogFragment.newInstance(
											chosenCategories.get(i), "type");
							populateTransactionDialog.show(getActivity().getSupportFragmentManager(), "PopulateTransactionDialogFragment");
						}*/
					}
				})
				.setNegativeButton("negative", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						ChooseCategoriesDialogFragment.this.getDialog().cancel();
					}
				});
		return builder.create();
	}

	View.OnClickListener addNewCategoryHandler = new View.OnClickListener() {
		public void onClick(View v) {

			// Create an instance of the dialog fragment and show it
			DialogFragment dialog = new AddNewCategoryDialogFragment();
			dialog.setTargetFragment(ChooseCategoriesDialogFragment.this, 1);
			dialog.show(getActivity().getSupportFragmentManager(), "AddNewCategoryDialogFragment");
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
	public void onResume() {
		super.onResume();
		Log.d("chooseCategories", "onResume");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d("chooseCategories", "onStart");
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
		public void onFragmentInteraction2(ArrayList<String> chosenCategories, String type);
	}

}
