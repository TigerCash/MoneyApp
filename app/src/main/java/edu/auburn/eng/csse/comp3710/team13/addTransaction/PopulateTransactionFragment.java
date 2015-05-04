package edu.auburn.eng.csse.comp3710.team13.addTransaction;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.auburn.eng.csse.comp3710.team13.R;
import edu.auburn.eng.csse.comp3710.team13.database.classes.Transaction;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PopulateTransactionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PopulateTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopulateTransactionFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private Transaction transaction;
	private int mIndex;

	private OnFragmentInteractionListener mListener;
	private EditText name_text;
	private EditText amount_text;
	private EditText time_text;
	private EditText date_text;

	private TextView category_name_text;
	private TextView type_text;

	private Button complete_transaction_button;
	private DatePickerDialog datePickerDialog;
	private TimePickerDialog timePickerDialog;

	private SimpleDateFormat dateFormatter;


	public static PopulateTransactionFragment newInstance(Transaction transaction, int index) {
		PopulateTransactionFragment fragment = new PopulateTransactionFragment();
		/*Bundle args = new Bundle();
		args.putInt("index", index);
		args.putString("date", buildTransaction.getDate());
		args.putString("time", buildTransaction.getTime());
		args.putString("name", buildTransaction.getName());
		args.putInt("amount", buildTransaction.getAmount());
		args.putString("category", buildTransaction.getCategory().getName());
		args.putString("type", buildTransaction.getType());

		fragment.setArguments(args);*/

		return fragment;
	}


	public PopulateTransactionFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		transaction = new Transaction();

		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}

		Bundle args = getArguments();
		if (args != null) {
			/*mIndex = args.getInt("index");

			buildTransaction.setDate(args.getString("date"));
			buildTransaction.setTime(args.getString("time"));
			buildTransaction.setName(args.getString("name"));
			buildTransaction.setAmount(args.getInt("amount"));
			buildTransaction.setCategory(new Category(args.getString("category")));
			buildTransaction.setType(args.getString("type"));*/
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_populate_transaction, container, false);

		/*category_name_text = (TextView) v.findViewById(R.id.category_name_text);
		type_text = (TextView) v.findViewById(R.id.type_text);
		category_name_text.setText(buildTransaction.getCategory().getName());
		type_text.setText(buildTransaction.getType());

		name_text = (EditText) v.findViewById(R.id.name);
		name_text.setText(buildTransaction.getName());
		amount_text = (EditText) v.findViewById(R.id.amount);
		int amount = buildTransaction.getAmount();
		if (amount != 0)
			amount_text.setText(Integer.toString(amount));

		time_text = (EditText) v.findViewById(R.id.time_text);
		time_text.setText(buildTransaction.getTime());
		date_text = (EditText) v.findViewById(R.id.date_text);
		date_text.setText(buildTransaction.getDate());
		time_text.setOnClickListener(timePickerHandler);
		date_text.setOnClickListener(datePickerHandler);

		complete_transaction_button = (Button) v.findViewById(R.id.complete_transaction_button);
		complete_transaction_button.setOnClickListener(completeTransactionHandler);

		dateFormatter = new SimpleDateFormat("MM.dd.yy", Locale.US);*/

		return v;
	}


	View.OnClickListener timePickerHandler = new View.OnClickListener() {
		public void onClick(View v) {
			// it was the 1st button
			// Create an instance of the dialog fragment and show it

			Calendar newCalendar = Calendar.getInstance();
			timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					String am_pm = "";
					Calendar newTime = Calendar.getInstance();
					newTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
					newTime.set(Calendar.MINUTE, minute);

					if (newTime.get(Calendar.AM_PM) == Calendar.AM)
						am_pm = "AM";
					else if (newTime.get(Calendar.AM_PM) == Calendar.PM)
						am_pm = "PM";

					String strHrsToShow = (newTime.get(Calendar.HOUR) == 0) ?"12":newTime.get(Calendar.HOUR)+"";
					String strMinToShow = (newTime.get(Calendar.MINUTE) < 10) ? "0"+newTime.get(Calendar.MINUTE):newTime.get(Calendar.MINUTE)+"";

					time_text.setText(strHrsToShow+":"+strMinToShow+" "+am_pm);
				}
			},newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));

			timePickerDialog.show();
		}
	};

	View.OnClickListener datePickerHandler = new View.OnClickListener() {
		public void onClick(View v) {
			// it was the 1st button
			// Create an instance of the dialog fragment and show it

			Calendar newCalendar = Calendar.getInstance();
			datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					Calendar newDate = Calendar.getInstance();
					newDate.set(year, monthOfYear, dayOfMonth);
					date_text.setText(dateFormatter.format(newDate.getTime()));
				}

			},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

			datePickerDialog.show();
		}
	};

	View.OnClickListener completeTransactionHandler = new View.OnClickListener() {
		public void onClick(View v) {
			// it was the 1st button
			if (mListener != null) {
				Transaction t = new Transaction();

				t.setName(name_text.getText().toString());
				try {
					//t.setAmount(Integer.valueOf(amount_text.getText().toString()));
				} catch (Exception e) {
					Log.d("Ex", "Invalid int");
				}
				t.setDate(date_text.getText().toString());
				t.setTime(time_text.getText().toString());

				t.setType(transaction.getType());
				//t.setCategory(buildTransaction.getCategory());

				mListener.onCompleteTransaction(t, mIndex);
				//mListener.onCompleteTransaction(buildTransaction, mIndex);
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
		public void onCompleteTransaction(Transaction transaction, int index);
	}

}
