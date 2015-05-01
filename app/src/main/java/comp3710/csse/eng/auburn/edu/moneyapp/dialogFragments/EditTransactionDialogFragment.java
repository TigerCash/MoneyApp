package comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;


public class EditTransactionDialogFragment extends DialogFragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private EditText name_text;
	private EditText amount_text;
	private EditText time_text;
	private EditText date_text;

	private TextView category_name_text;
	private TextView type_text;

	private Transaction mTransaction;

	private OnEditTransactionListener mEditListener;
	private EditText mNameEditText;

	private SimpleDateFormat dateFormatter;
	private DatePickerDialog datePickerDialog;
	private TimePickerDialog timePickerDialog;

	public static EditTransactionDialogFragment newInstance(Transaction transaction) {
		EditTransactionDialogFragment fragment = new EditTransactionDialogFragment();
		Bundle args = new Bundle();
		args.putInt("id", transaction.getId());
		args.putString("date", transaction.getDate());
		args.putString("time", transaction.getTime());
		args.putString("name", transaction.getName());
		args.putInt("amount", transaction.getAmount());
		args.putString("category", transaction.getCategory().getName());
		args.putString("type", transaction.getType());

		fragment.setArguments(args);

		return fragment;
	}

	public EditTransactionDialogFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTransaction = new Transaction();

		Bundle args = getArguments();
		if (args != null) {
			mTransaction.setId(args.getInt("id"));
			mTransaction.setDate(args.getString("date"));
			mTransaction.setTime(args.getString("time"));
			mTransaction.setName(args.getString("name"));
			mTransaction.setAmount(args.getInt("amount"));
			mTransaction.setCategory(new Category(args.getString("category")));
			mTransaction.setType(args.getString("type"));
		}

		try {
			mEditListener = (OnEditTransactionListener) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException("Calling Fragment must implement OnEditTransactionListener");
		}
	}



	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		View v = inflater.inflate(R.layout.fragment_edit_transaction_dialog, null);

		category_name_text = (TextView) v.findViewById(R.id.category_name_text);
		type_text = (TextView) v.findViewById(R.id.type_text);
		category_name_text.setText(mTransaction.getCategory().getName());
		type_text.setText(mTransaction.getType());

		name_text = (EditText) v.findViewById(R.id.name);
		name_text.setText(mTransaction.getName());
		amount_text = (EditText) v.findViewById(R.id.amount);
		int amount = mTransaction.getAmount();
		if (amount != 0)
			amount_text.setText(Integer.toString(amount));

		time_text = (EditText) v.findViewById(R.id.time_text);
		time_text.setText(mTransaction.getTime());
		date_text = (EditText) v.findViewById(R.id.date_text);
		date_text.setText(mTransaction.getDate());

		time_text.setOnClickListener(timePickerHandler);
		date_text.setOnClickListener(datePickerHandler);

		dateFormatter = new SimpleDateFormat("MM.dd.yy", Locale.US);


		mNameEditText = (EditText) v.findViewById(R.id.name_edit_text);

		// Pass null as the parent view because its going in the dialog layout
		builder.setView(v)
				// Add action buttons
				.setPositiveButton("positive", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						if (mEditListener != null) {
							Transaction t = new Transaction();

							t.setName(name_text.getText().toString());
							try {
								t.setAmount(Integer.valueOf(amount_text.getText().toString()));
							} catch (Exception e) {
								Log.d("Ex", "Invalid int");
							}
							t.setDate(date_text.getText().toString());
							t.setTime(time_text.getText().toString());

							t.setType(mTransaction.getType());
							t.setCategory(mTransaction.getCategory());

							t.setId(mTransaction.getId());

							mEditListener.onEditTransaction(t);
							//mListener.onCompleteTransaction(transaction, mIndex);
						}
					}
				})
				.setNegativeButton("negative", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						EditTransactionDialogFragment.this.getDialog().cancel();
					}
				});
		return builder.create();
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


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mEditListener = (OnEditTransactionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnEditTransactionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mEditListener = null;
	}


	public interface OnEditTransactionListener {
		public void onEditTransaction(Transaction transaction);
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
