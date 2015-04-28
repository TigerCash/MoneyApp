package comp3710.csse.eng.auburn.edu.moneyapp;

//import android.app.ActionBar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class ScreenSlidePagerActivity extends FragmentActivity
		implements ScreenSlidePageFragment.OnScreenSlidePageFragmentInteractionListener {
	/**
	 * The number of pages (wizard steps) to show in this demo.
	 */
	//private static final int NUM_PAGES = 5;
	private int mNumPages;
	private String mType;
	private ArrayList<String> mCategoryNames;

	public static final String NUM_PAGES = "num_pages";
	public static final String TYPE = "type";
	public static final String NAMES = "names";

	/**
	 * The pager widget, which handles animation and allows swiping horizontally to access previous
	 * and next wizard steps.
	 */
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;

	private ArrayList<Fragment> screenSlidePageFragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen_slide_pager);

		Intent intent = getIntent();

		mNumPages = intent.getIntExtra(NUM_PAGES, 0);
		mType = intent.getStringExtra(TYPE);
		mCategoryNames = intent.getStringArrayListExtra(NAMES);



		// Check whether we're recreating a previously destroyed instance
		if (savedInstanceState != null) {
			// Restore value of members from saved state
			mNumPages = savedInstanceState.getInt(ScreenSlidePagerActivity.NUM_PAGES);
			mType = savedInstanceState.getString(ScreenSlidePagerActivity.TYPE);
			Bundle extra = getIntent().getBundleExtra("extraNames");
			mCategoryNames = (ArrayList<String>) extra.getSerializable("names");
			Log.d("pager", mType);
		} else {
			// Probably initialize members with default values for a new instance
		}



		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.pager);
		//mPager = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), mCategoryNames, mType);
		//mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);

	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (mPager.getCurrentItem() == 0) {
			// If the user is currently looking at the first step, allow the system to handle the
			// Back button. This calls finish() on this activity and pops the back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
	}

	public void removePage(Fragment fragment) {
		screenSlidePageFragments.remove(fragment);
		mPagerAdapter.notifyDataSetChanged();
		/*
		if ((position >= 0) && (position < getSize()) && (getSize() > 1)) {
			if (position == mPager.getCurrentItem()) {
				if(position == (getSize()-1)) {
					mPager.setCurrentItem(position-1);
				} else if (position == 0){
					mPager.setCurrentItem(1);
				}
			}
			screenSlidePageFragments.remove(position);
			mPagerAdapter.notifyDataSetChanged();
		}*/
	}

	public int getSize() {
		return screenSlidePageFragments.size();
	}

	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
	 * sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {


		public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<String> categoryNames, String type) {
			super(fm);

			for (int i = 0; i < categoryNames.size(); i++) {
				Fragment f = ScreenSlidePageFragment.newInstance(categoryNames.get(i), type);
				screenSlidePageFragments.add(f);
			}
		}

		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}


		@Override
		public Fragment getItem(int position) {
			//return new ScreenSlidePageFragment();
			return screenSlidePageFragments.get(position);
		}

		@Override
		public CharSequence getPageTitle (int position) {
			return ((ScreenSlidePageFragment) screenSlidePageFragments.get(position)).getName();
		}

		@Override
		public int getCount() {
			return mNumPages;
		}
	}
}