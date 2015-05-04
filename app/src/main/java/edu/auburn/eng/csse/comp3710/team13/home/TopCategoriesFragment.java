package edu.auburn.eng.csse.comp3710.team13.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team13.R;
import edu.auburn.eng.csse.comp3710.team13.TopCategoriesListAdapter;
import edu.auburn.eng.csse.comp3710.team13.database.MoneyAppDatabaseHelper;
import edu.auburn.eng.csse.comp3710.team13.database.classes.Category;

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
	ListView lv;

	LinearLayout mListViewLayout;

	private ActionMode mActionMode;

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

		lv=(ListView) v.findViewById(R.id.category_list_view);

		adapter2 = new TopCategoriesListAdapter(getActivity().getBaseContext(), topCategories, totals);
		lv.setAdapter(adapter2);

		lv.setOnItemLongClickListener(onItemLongClickListener);

		//lv.setAdapter(new TopCategoriesListAdapter(getActivity().getBaseContext(), topCategories, totals));
	}

	AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {

		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		int pos, long id) {
			// TODO Auto-generated method stub

			Log.v("long clicked","pos: " + pos);

			if (mActionMode != null) {
				return false;
			}

			// Start the CAB using the ActionMode.Callback defined above
			ActionBarActivity activity=(ActionBarActivity)getActivity();
			activity.startSupportActionMode(mActionModeCallback);

			mListViewLayout = (LinearLayout) arg1;

			return true;
		}
	};


	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.action_bar_category, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
				/*case R.id.menu_share:
					shareCurrentItem();
					mode.finish(); // Action picked, so close the CAB
					return true;*/
				case R.id.action_edit:
					//editTransaction(mSelectedTableRow);
					editCategory(mListViewLayout);
					mode.finish();
					return true;
				case R.id.action_delete:

					// Do not allow deletion
					mode.finish();
					return true;
				default:
					return false;
			}
		}

		// Called when the user exits the action mode
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
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
		public void onAllCategories();
		public void editCategory(Category category);
	}


	View.OnClickListener onAllCategoriesListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onAllCategories();
			}
		}
	};

	public void editCategory(LinearLayout view) {
		// Get buildTransaction so it can be edited
		MoneyAppDatabaseHelper helper = new MoneyAppDatabaseHelper(getActivity().getApplicationContext());

		Category category = helper.getCategory((int) view.getTag());

		mListener.editCategory(category);

	}

	public void dataSetChanged() {
		setupListView(getView().findViewById(R.id.category_list_view));
		adapter2.notifyDataSetChanged();
	}

}
