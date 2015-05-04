package edu.auburn.eng.csse.comp3710.team13.dialogFragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team13.R;
import edu.auburn.eng.csse.comp3710.team13.database.MoneyAppDatabaseHelper;
import edu.auburn.eng.csse.comp3710.team13.database.classes.Category;


public class EditCategoryDialogFragment extends DialogFragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	/*private OnFragmentInteractionListener mListener;*/
	private OnFragmentInteractionListener mListener;
	private EditText mNameEditText;


	public static EditCategoryDialogFragment newInstance(String name) {
		EditCategoryDialogFragment fragment = new EditCategoryDialogFragment();
		Bundle args = new Bundle();
		args.putString("name", name);
		fragment.setArguments(args);
		return fragment;
	}

	public EditCategoryDialogFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString("name");
		}
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		View v = inflater.inflate(R.layout.fragment_add_new_category_dialog, null);

		mNameEditText = (EditText) v.findViewById(R.id.name_edit_text);
		mNameEditText.setText(mParam1);

		// Pass null as the parent view because its going in the dialog layout
		builder.setView(v)
				// Add action buttons
				.setPositiveButton("positive", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						String name = mNameEditText.getText().toString();

						// Validate
						boolean valid = true;
						if (name == null || name.equals("")) {
							valid = false;
							Toast.makeText(getActivity().getApplicationContext(), "Invalid Category Name",
									Toast.LENGTH_SHORT).show();
							EditCategoryDialogFragment.this.getDialog().cancel();
						}

						MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
						ArrayList<Category> categories = helper.getAllCategories();
						for (Category c : categories) {
							String t = name.toLowerCase();
							String t2 = c.getName().toLowerCase();
							if (name.toLowerCase().equals(c.getName().toLowerCase())) {
								valid = false;
								Toast.makeText(getActivity().getApplicationContext(), "Duplicate Category",
										Toast.LENGTH_SHORT).show();
								EditCategoryDialogFragment.this.getDialog().cancel();
								break;
							}
						}

						// If valid
						if (valid) {
							// Add category to db
							Category category = helper.getCategory(mParam1);
							category.setName(name);
							helper.updateCategory(category);
							// Call onAddNewCategory on EditTransactionPortionFragment
							mListener.onEditCategory();
						}
					}
				})
				.setNegativeButton("negative", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						EditCategoryDialogFragment.this.getDialog().cancel();
					}
				});
		return builder.create();
	}

	public interface OnFragmentInteractionListener {
		public void onEditCategory();
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

}
