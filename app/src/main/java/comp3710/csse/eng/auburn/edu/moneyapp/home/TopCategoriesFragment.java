package comp3710.csse.eng.auburn.edu.moneyapp.home;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import comp3710.csse.eng.auburn.edu.moneyapp.ExpandableListAdapter;
import comp3710.csse.eng.auburn.edu.moneyapp.R;
import comp3710.csse.eng.auburn.edu.moneyapp.TopCategoriesListAdapter;
import comp3710.csse.eng.auburn.edu.moneyapp.database.MoneyAppDatabaseHelper;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Transaction;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopCategoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopCategoriesFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private static final int NUMBER_OF_CATEGORIES = 5;

	TextView mAllCategoriesText;

	TopCategoriesListAdapter adapter2;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment TopCategoriesFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static TopCategoriesFragment newInstance(String param1, String param2) {
		TopCategoriesFragment fragment = new TopCategoriesFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public TopCategoriesFragment() {
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
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_top_categories, container, false);

		setupListView(v);

		mAllCategoriesText = (TextView) v.findViewById(R.id.all_categories_text);
		mAllCategoriesText.setOnClickListener(onAllCategoriesListener);

		return v;
	}

	public void setupListView(View v) {
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());

		ArrayList<Category> topCategories = helper.getTopCategories(NUMBER_OF_CATEGORIES);
		ArrayList<Double> totals = new ArrayList<>();


		for (Category c : topCategories) {
			totals.add(helper.getCategoryAmount(c.getId()));
		}

		ListView lv=(ListView) v.findViewById(R.id.category_list_view);

		adapter2 = new TopCategoriesListAdapter(getActivity().getBaseContext(), topCategories, totals);
		lv.setAdapter(adapter2);
		//lv.setAdapter(new TopCategoriesListAdapter(getActivity().getBaseContext(), topCategories, totals));
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
		public void onAllCategories();
	}

	View.OnClickListener onAllCategoriesListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onAllCategories();
			}
		}
	};

	public void dataSetChanged() {
		setupListView(getView().findViewById(R.id.category_list_view));
		adapter2.notifyDataSetChanged();
	}

}
