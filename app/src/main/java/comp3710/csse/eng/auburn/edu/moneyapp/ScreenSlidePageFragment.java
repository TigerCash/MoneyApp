package comp3710.csse.eng.auburn.edu.moneyapp;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments.TimePickerFragment;

public class ScreenSlidePageFragment extends Fragment implements View.OnClickListener {

	private String mType;
	private String mName;

	private EditText time_text;
	private EditText date_text;

	private DatePickerDialog datePickerDialog;
	private TimePickerDialog timePickerDialog;

	private SimpleDateFormat dateFormatter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mType = getArguments().getString("type");
			mName = getArguments().getString("name");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_screen_slide_page, container, false);

		TextView categoryName = (TextView) rootView.findViewById(R.id.category_name);
		TextView transactionType = (TextView) rootView.findViewById(R.id.transaction_type);
		categoryName.setText(mName);
		transactionType.setText(mType);

		time_text = (EditText) rootView.findViewById(R.id.time_text);
		date_text = (EditText) rootView.findViewById(R.id.date_text);
		/*time_text.setOnClickListener(timePickerHandler);
		date_text.setOnClickListener(datePickerHandler);*/
		time_text.setOnClickListener(this);
		date_text.setOnClickListener(this);

		//dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		dateFormatter = new SimpleDateFormat("MM.dd.yyyy", Locale.US);

		Calendar newCalendar = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				date_text.setText(dateFormatter.format(newDate.getTime()));
			}

		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		timePickerDialog = new TimePickerDialog(getActivity(), new OnTimeSetListener() {

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


		return rootView;
	}

	static ScreenSlidePageFragment newInstance(String name, String type) {
		ScreenSlidePageFragment f = new ScreenSlidePageFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putString("name", name);
		args.putString("type", type);
		f.setArguments(args);

		return f;
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
			/*// it was the 1st button
			// Create an instance of the dialog fragment and show it
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");*/


		}
	};

	@Override
	public void onClick(View view) {
		if(view == date_text) {
			datePickerDialog.show();
		} else if(view == time_text) {
			timePickerDialog.show();
		}
	}

	public String getName() {
		return mName;
	}

	public String getType() {
		return mType;
	}
}