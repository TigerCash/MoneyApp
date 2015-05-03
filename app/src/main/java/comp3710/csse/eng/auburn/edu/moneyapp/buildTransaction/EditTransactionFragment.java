package comp3710.csse.eng.auburn.edu.moneyapp.buildTransaction;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.TransactionPortion;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditTransactionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTransactionFragment extends Fragment {

	private OnFragmentInteractionListener mListener;

	private Button mCompleteTransactionButton;
	private TextView mAddTransactionPortionText;
	private EditText mNameEditText;
	private EditText mDateEditText;
	private EditText mTimeEditText;
	private TableLayout mTransactionPortionsTable;

	private SimpleDateFormat dateFormatter;
	private DatePickerDialog datePickerDialog;
	private TimePickerDialog timePickerDialog;




	// TODO: Rename and change types and number of parameters
	public static EditTransactionFragment newInstance(TransactionPortion transactionPortion) {
		EditTransactionFragment fragment = new EditTransactionFragment();
		Bundle args = new Bundle();

		args.putInt("id", transactionPortion.getId());
		args.putString("description", transactionPortion.getDescription());
		args.putString("amount", transactionPortion.getAmount());
		args.putInt("category_id", transactionPortion.getCategoryId());
		args.putInt("transaction_id", transactionPortion.getTransactionId());

		fragment.setArguments(args);
		return fragment;
	}

	/*public static EditTransactionFragment newInstance(Transaction transaction) {
		EditTransactionFragment fragment = new EditTransactionFragment();
		Bundle args = new Bundle();

		args.putInt("id", transactionPortion.getId());
		args.putString("description", transactionPortion.getDescription());
		args.putString("amount", transactionPortion.getAmount());
		args.putInt("category_id", transactionPortion.getCategoryId());
		args.putInt("transaction_id", transactionPortion.getTransactionId());

		fragment.setArguments(args);
		return fragment;
	}*/

	public static EditTransactionFragment newInstance(String type) {
		EditTransactionFragment fragment = new EditTransactionFragment();
		Bundle args = new Bundle();

		args.putString("type", type);

		fragment.setArguments(args);
		return fragment;
	}

	public EditTransactionFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TransactionPortion transactionPortion = new TransactionPortion();

		Bundle args = getArguments();
		if (args != null) {

			if (args.getString("type") != null) {
				((BuildTransactionActivity) getActivity()).buildTransaction.setType(args.getString("type"));
			}

			transactionPortion.setId(args.getInt("id"));
			transactionPortion.setDescription(args.getString("description"));
			transactionPortion.setAmount(args.getString("amount"));
			transactionPortion.setCategoryId(args.getInt("category_id"));
			transactionPortion.setTransactionId(args.getInt("transaction_id"));

			((BuildTransactionActivity)getActivity()).buildTransaction.addTransactionPortion(transactionPortion);

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_edit_transaction, container, false);

		mCompleteTransactionButton = (Button) v.findViewById(R.id.complete_transaction_button);
		mCompleteTransactionButton.setOnClickListener(completeTransactionListener);

		mAddTransactionPortionText = (TextView) v.findViewById(R.id.add_transaction_portion_text);
		mAddTransactionPortionText.setOnClickListener(addTransactionPortionListener);


		Transaction transaction = ((BuildTransactionActivity)getActivity()).buildTransaction;

		// Fill Name
		mNameEditText = (EditText) v.findViewById(R.id.name_edit_text);
		mNameEditText.setText(transaction.getName());

		// Fill Date and Time
		mDateEditText = (EditText) v.findViewById(R.id.date_edit_text);
		mDateEditText.setText(transaction.getDate());
		mDateEditText.setOnClickListener(datePickerHandler);

		mTimeEditText = (EditText) v.findViewById(R.id.time_edit_text);
		mTimeEditText.setText(transaction.getTime());
		mTimeEditText.setOnClickListener(timePickerHandler);

		dateFormatter = new SimpleDateFormat("MM.dd.yy", Locale.US);


		// Fill tablelayout with transactionPortions
		mTransactionPortionsTable = (TableLayout) v.findViewById(R.id.transaction_portions_table);
		ArrayList<TransactionPortion> transactionPortions = ((BuildTransactionActivity)getActivity()).buildTransaction.getTransactionPortions();



		if (transactionPortions != null) {
			TableRow tableRow;
			TextView textView;

			for (int i = 0; i < transactionPortions.size(); i++) {

				tableRow = new TableRow(getActivity());
				tableRow.setTag(i);

				textView = new TextView(getActivity());
				textView.setText(transactionPortions.get(i).getDescription());
				tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

				textView = new TextView(getActivity());
				textView.setText(transactionPortions.get(i).getAmount());
				textView.setGravity(Gravity.CENTER_HORIZONTAL);
				tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

				textView = new TextView(getActivity());
				final MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getBaseContext());
				Category category = helper.getCategory(transactionPortions.get(i).getCategoryId());
				textView.setText(category.getName());
				textView.setGravity(Gravity.RIGHT);
				tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));


				tableRow.setTag(transaction.getTransactionPortions().get(i).getId());

				tableRow.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						TableRow tableRow = ((TableRow) v);
						TextView nameTextView = (TextView) tableRow.getChildAt(0);
						Log.d("list", "The name is" + nameTextView.getText().toString());
						int transactionRowIndex = (int) tableRow.getTag();
						Log.d("list", Integer.toString(transactionRowIndex));
						// Populate Transaction
						if (mListener != null) {
							//mListener.onEditTransaction(transactionRowIndex);


							TransactionPortion transactionPortion;
							// If transactionPortion already had an id (set as tag for tablerow)
							// update and use this transactionPortion
							if (((int)tableRow.getTag()) != 0) {
								 transactionPortion = helper.getTransactionPortion((int)tableRow.getTag());
							}
							else {
								// Build transactionPortion
								transactionPortion = new TransactionPortion();

								String description = ((TextView) tableRow.getChildAt(0)).getText().toString();
								String amount = ((TextView) tableRow.getChildAt(1)).getText().toString();
								String category = ((TextView) tableRow.getChildAt(2)).getText().toString();
								MoneyAppDatabaseHelper helper1 = new MoneyAppDatabaseHelper(getActivity().getBaseContext());
								int category_id = helper1.getCategory(category).getId();
								transactionPortion.setDescription(description);
								transactionPortion.setAmount(amount);
								transactionPortion.setCategoryId(category_id);
							}

							// Delete this transactionPortion object from BuildTransaction buildTransaction's transactionPortions
							// It will be re-added when it is updated in the editTransactionPortionFragment
							ArrayList<TransactionPortion> transactionPortions1 = ((BuildTransactionActivity)getActivity()).buildTransaction.getTransactionPortions();
							transactionPortions1.remove(transactionPortion);
							((BuildTransactionActivity)getActivity()).buildTransaction.setTransactionPortions(transactionPortions1);

							// Edit this transactionPortion
							mListener.onEditTransactionPortion(transactionPortion);

						}
					}
				});

				mTransactionPortionsTable.addView(tableRow);

			}
		}

		return v;
	}

	View.OnClickListener completeTransactionListener = new View.OnClickListener() {
		public void onClick(View v) {

			//validate fields
			boolean validFields = checkValidFields(v);

			Transaction transaction = ((BuildTransactionActivity) getActivity()).buildTransaction;
			boolean validTransaction = checkValidTransaction(transaction);

			//if valid
			if (validFields && validTransaction) {
				// put together buildTransaction

				View view = v.getRootView();
				transaction.setName(((EditText) view.findViewById(R.id.name_edit_text)).getText().toString());
				transaction.setDate(((EditText) view.findViewById(R.id.date_edit_text)).getText().toString());
				transaction.setTime(((EditText) view.findViewById(R.id.time_edit_text)).getText().toString());

				// buildTransaction portions should already be added in this buildTransaction

				MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
				// If buildTransaction has id, update buildTransaction and transactionPortions
				if (transaction.getId() != 0) {
					helper.updateTransaction(transaction);
					helper.updateTransactionPortions(transaction.getTransactionPortions());

				}
				// Else, add buildTransaction and transactionPortions to DB
				else {

					// Insert buildTransaction and get id of this buildTransaction in the table
					int transactionId = helper.addTransaction(transaction);

					// Set the transaction_id for each transactionPortion in this buildTransaction
					ArrayList<TransactionPortion> transactionPortions = transaction.getTransactionPortions();
					for (int i = 0; i < transactionPortions.size(); i++) {
						transactionPortions.get(i).setTransactionId(transactionId);
					}

					helper.addTransactionPortions(transactionPortions);
				}

				// Call listener out
				if (mListener != null) {
					mListener.onCompleteTransaction(transaction);
				}
			}
		}
	};

	View.OnClickListener addTransactionPortionListener = new View.OnClickListener() {
		public void onClick(View v) {

			View view = v.getRootView();
			((BuildTransactionActivity)getActivity()).buildTransaction.setName(((EditText) view.findViewById(R.id.name_edit_text)).getText().toString());
			((BuildTransactionActivity)getActivity()).buildTransaction.setDate(((EditText)view.findViewById(R.id.date_edit_text)).getText().toString());
			((BuildTransactionActivity)getActivity()).buildTransaction.setTime(((EditText)view.findViewById(R.id.time_edit_text)).getText().toString());

			if (mListener != null) {
				mListener.onAddTransactionPortion();
			}
		}
	};

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

					mTimeEditText.setText(strHrsToShow+":"+strMinToShow+" "+am_pm);
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
					mDateEditText.setText(dateFormatter.format(newDate.getTime()));
				}

			},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

			datePickerDialog.show();
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
		public void onCompleteTransaction(Transaction transaction);
		public void onAddTransactionPortion();
		public void onEditTransactionPortion(TransactionPortion transactionPortion);
	}


	public boolean checkValidFields(View v) {
		boolean valid = true;
		View view = v.getRootView();
		String name = ((EditText)view.findViewById(R.id.name_edit_text)).getText().toString();
		String date = ((EditText)view.findViewById(R.id.date_edit_text)).getText().toString();
		String time = ((EditText)view.findViewById(R.id.time_edit_text)).getText().toString();

		if (name == null || name.equals("")) {
			valid = false;
			Toast.makeText(getActivity().getApplicationContext(), "Invalid Name",
					Toast.LENGTH_SHORT).show();
		}
		else if (date == null || date.equals("")) {
			valid = false;
			Toast.makeText(getActivity().getApplicationContext(), "Invalid Date",
					Toast.LENGTH_SHORT).show();
		}
		else if (time == null || time.equals("")) {
			valid = false;
			Toast.makeText(getActivity().getApplicationContext(), "Invalid Time",
					Toast.LENGTH_SHORT).show();
		}

		return valid;
	}

	public boolean checkValidTransaction(Transaction transaction) {
		boolean valid = (boolean)transaction.isValid()[0];

		if (!valid) {
			String errorMessage = (String)transaction.isValid()[1];
			Toast.makeText(getActivity().getApplicationContext(), errorMessage,
					Toast.LENGTH_SHORT).show();
		}

		return valid;
	}



}
