package comp3710.csse.eng.auburn.edu.moneyapp.addTransaction;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListTransactionCategoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListTransactionCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListTransactionCategoriesFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;
	private TableLayout mTable;
	private Button mCompleteTransactionsButton;

	private ArrayList<Transaction> mTransactionCategories = new ArrayList<>();

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ListTransactionCategoriesFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ListTransactionCategoriesFragment newInstance(String param1, String param2) {
		ListTransactionCategoriesFragment fragment = new ListTransactionCategoriesFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public ListTransactionCategoriesFragment() {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_list_transaction_categories, container, false);

		mCompleteTransactionsButton = (Button) v.findViewById(R.id.complete_transactions_button);
		mCompleteTransactionsButton.setOnClickListener(completeTransactionsHandler);


		if (mListener != null) {
			mTransactionCategories = mListener.onFragmentInteraction8();
			//Log.d("listTrans", mListener.onFragmentInteraction8().get(0));
		}

		mTable = (TableLayout) v.findViewById(R.id.category_table);

		TableRow tableRow;
		TextView textView;

		for (int i = 0; i < mTransactionCategories.size(); i++) {

			tableRow = new TableRow(getActivity());
			tableRow.setTag(i);

			textView = new TextView(getActivity());
			textView.setText(mTransactionCategories.get(i).getCategory().getName());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			textView = new TextView(getActivity());
			textView.setText(mTransactionCategories.get(i).getName());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			textView = new TextView(getActivity());
			int amount = mTransactionCategories.get(i).getAmount();
			textView.setText(Integer.toString(amount));
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			textView = new TextView(getActivity());
			textView.setText(mTransactionCategories.get(i).getDate());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

			textView = new TextView(getActivity());
			textView.setText(mTransactionCategories.get(i).getTime());
			tableRow.addView(textView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));


			String transactionName = mTransactionCategories.get(i).getName();
			int transactionAmount = mTransactionCategories.get(i).getAmount();

			String transactionDate = mTransactionCategories.get(i).getDate();
			String transactionTime = mTransactionCategories.get(i).getTime();
			Resources res = getResources();
			if (transactionName == null || transactionName.equals("")
			 || transactionAmount == 0) {
				tableRow.setBackgroundColor(res.getColor(R.color.translucent_red));
			}
			else {
				if (transactionDate == null || transactionDate.equals("")
						|| transactionTime == null || transactionTime.equals("")) {
					tableRow.setBackgroundColor(res.getColor(R.color.translucent_blue));
				}
				else {
					tableRow.setBackgroundColor(res.getColor(R.color.translucent_green));
				}
			}


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
						mListener.onEditTransaction(transactionRowIndex);
					}
				}
			});

			mTable.addView(tableRow);

		}



		/*if(checkboxLinearLayout.getChildCount() > 0)
			checkboxLinearLayout.removeAllViews();

		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());
		ArrayList<Category> categories = helper.getAllCategories();

		CheckBox box;
		for (int i = 0; i < categories.size(); i++) {
			box = new CheckBox(this.getActivity());
			box.setText(categories.get(i).getName());
			checkboxLinearLayout.addView(box);
		}*/

		// Inflate the layout for this fragment
		return v;
	}


	View.OnClickListener completeTransactionsHandler = new View.OnClickListener() {
		public void onClick(View v) {
			// it was the 1st button
			if (mListener != null) {
				mListener.onCompleteTransactions();
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
		public ArrayList<Transaction> onFragmentInteraction8();
		public void onEditTransaction(int transactionIndex);
		public void onCompleteTransactions();
	}

}
