package comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import comp3710.csse.eng.auburn.edu.moneyapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChooseCategoriesDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChooseCategoriesDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseCategoriesDialogFragment extends DialogFragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;
	private LinearLayout checkboxLinearLayout;
	private Button addNewCategoryButton;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ChooseCategoriesDialogFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ChooseCategoriesDialogFragment newInstance(String param1, String param2) {
		ChooseCategoriesDialogFragment fragment = new ChooseCategoriesDialogFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
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
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
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
						// sign in the user ...
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
			// it was the 1st button
			// Create an instance of the dialog fragment and show it
			DialogFragment dialog = new AddNewCategoryDialogFragment();
			dialog.show(getActivity().getSupportFragmentManager(), "AddNewCategoryDialogFragment");
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// use loop
		CheckBox box = new CheckBox(this.getActivity());
		checkboxLinearLayout.addView(box);

		// TODO: Get categories
		// TODO: Add checkbox for each category
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction2(uri);
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
		public void onFragmentInteraction2(Uri uri);
	}

}
