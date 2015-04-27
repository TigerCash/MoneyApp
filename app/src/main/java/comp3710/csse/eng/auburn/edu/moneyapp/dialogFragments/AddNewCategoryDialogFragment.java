package comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.R;


public class AddNewCategoryDialogFragment extends DialogFragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;
	private OnAddCategoryListener mAddListener;
	private EditText mNameEditText;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ChooseCategoriesDialogFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static AddNewCategoryDialogFragment newInstance(String param1, String param2) {
		AddNewCategoryDialogFragment fragment = new AddNewCategoryDialogFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public AddNewCategoryDialogFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}

		try {
			mAddListener = (OnAddCategoryListener) getTargetFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException("Calling Fragment must implement OnAddFriendListener");
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

		// Pass null as the parent view because its going in the dialog layout
		builder.setView(v)
				// Add action buttons
				.setPositiveButton("positive", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						String name = mNameEditText.getText().toString();

						if (name.equals("")) {
							warnDialogEmptyName();
							// TODO: Shouldn't close addNewCategoryDialogFragment after warning
						}
						/*
						// TODO: Reject duplicate category name
						// Duplicate Category Name.
						// Add method that queries database (category table) and checks if category
						// name (case insensitive) already exists
						else if () {
							// Reject because duplicate category name
						}*/
						else {
							if (mListener != null) {
								mListener.addNewCategory(name);
								mAddListener.refresh();
							}
						}
					}
				})
				.setNegativeButton("negative", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						AddNewCategoryDialogFragment.this.getDialog().cancel();
					}
				});
		return builder.create();
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


	public interface OnFragmentInteractionListener {
		public void addNewCategory(String name);
	}

	public interface OnAddCategoryListener {
		public void refresh();
	}

	private void warnDialogEmptyName() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Add the buttons
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button

			}
		});
		// Set other dialog properties
		builder.setMessage("No Name Entered")
				.setTitle("Error");

		// Create the AlertDialog
		AlertDialog warningDialog = builder.create();

		warningDialog.show();
	}

}
