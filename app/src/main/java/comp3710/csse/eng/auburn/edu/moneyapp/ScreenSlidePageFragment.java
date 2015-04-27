package comp3710.csse.eng.auburn.edu.moneyapp;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments.DatePickerFragment;
import comp3710.csse.eng.auburn.edu.moneyapp.dialogFragments.TimePickerFragment;

public class ScreenSlidePageFragment extends Fragment {

	private String mType;
	private String mName;

	private Button time_button;
	private Button date_button;

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

		time_button = (Button) rootView.findViewById(R.id.time_button);
		date_button = (Button) rootView.findViewById(R.id.date_button);
		time_button.setOnClickListener(timePickerHandler);
		date_button.setOnClickListener(datePickerHandler);



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
			// it was the 1st button
			// Create an instance of the dialog fragment and show it
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
		}
	};

	public String getName() {
		return mName;
	}

	public String getType() {
		return mType;
	}
}