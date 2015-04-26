package comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.app.Fragment;
import android.widget.Button;

import comp3710.csse.eng.auburn.edu.moneyapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PopulateTransactionDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PopulateTransactionDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopulateTransactionDialogFragment extends DialogFragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;
	private Button time_button;
	private Button date_button;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment PopulateTransactionDialogFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static PopulateTransactionDialogFragment newInstance(String param1, String param2) {
		PopulateTransactionDialogFragment fragment = new PopulateTransactionDialogFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public PopulateTransactionDialogFragment() {
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

	View.OnClickListener timePickerHandler = new View.OnClickListener() {
		public void onClick(View v) {
			// it was the 1st button
			// Create an instance of the dialog fragment and show it
			DialogFragment newFragment = new TimePickerFragment();
			newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
		}
	};

	View.OnClickListener datePickerHandler = new View.OnClickListener() {
		public void onClick(View v) {
			// it was the 1st button
			// Create an instance of the dialog fragment and show it
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
		}
	};

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_populate_transaction_dialog, container, false);



		return v;
	}*/


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		View v = inflater.inflate(R.layout.fragment_populate_transaction_dialog, null);

		time_button = (Button) v.findViewById(R.id.time_button);
		date_button = (Button) v.findViewById(R.id.date_button);
		time_button.setOnClickListener(timePickerHandler);
		date_button.setOnClickListener(datePickerHandler);

		// Pass null as the parent view because its going in the dialog layout
		builder.setView(v)
				// Add action buttons
				.setPositiveButton("positive", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						//
					}
				})
				.setNegativeButton("negative", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						PopulateTransactionDialogFragment.this.getDialog().cancel();
					}
				});
		return builder.create();
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction3(uri);
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
		public void onFragmentInteraction3(Uri uri);
	}

}
