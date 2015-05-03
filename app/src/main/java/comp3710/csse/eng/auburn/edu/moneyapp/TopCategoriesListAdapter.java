package comp3710.csse.eng.auburn.edu.moneyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import comp3710.csse.eng.auburn.edu.moneyapp.database.classes.Category;
import comp3710.csse.eng.auburn.edu.moneyapp.home.HomeActivity;

public class TopCategoriesListAdapter extends BaseAdapter {


	ArrayList<Category> mCategories;
	Context mContext;
	ArrayList<Double> mTotals;
	private static LayoutInflater inflater=null;


	public TopCategoriesListAdapter(Context context, ArrayList<Category> categories, ArrayList<Double> totals) {
		// TODO Auto-generated constructor stub
		mCategories = categories;
		mTotals = totals;
		mContext=context;
		inflater = ( LayoutInflater )mContext.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCategories.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class Holder
	{
		TextView tv;
		TextView tv2;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder=new Holder();
		View rowView;
		rowView = inflater.inflate(R.layout.list_top_category_item, null);
		holder.tv=(TextView) rowView.findViewById(R.id.category_name_text);
		holder.tv2=(TextView) rowView.findViewById(R.id.total_text);
		holder.tv.setText(mCategories.get(position).getName());
		NumberFormat formatter = new DecimalFormat("#0.00");
		holder.tv2.setText(formatter.format(mTotals.get(position)));
		/*rowView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "You Clicked "+ mCategories.get(position).getName(), Toast.LENGTH_LONG).show();
			}
		});*/
		rowView.setTag(mCategories.get(position).getId());
		return rowView;
	}

}