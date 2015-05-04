package edu.auburn.eng.csse.comp3710.team13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import edu.auburn.eng.csse.comp3710.team13.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	//private List<String> _listDataHeader; // header titles
	private ArrayList<HashMap<String,String>> _listDataHeader; // header titles

	// child data in format of header title, child title
	//private HashMap<String, List<String>> _listDataChild;
	private HashMap<HashMap<String,String>, ArrayList<HashMap<String,String>>> _listDataChild;

	public ExpandableListAdapter(Context context, ArrayList<HashMap<String,String>> listDataHeader,
	                             HashMap<HashMap<String,String>, ArrayList<HashMap<String,String>>> listChildData) {
		this._context = context;
		//this._listDataHeader = listDataHeader;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
	                         boolean isLastChild, View convertView, ViewGroup parent) {

		final String descText = ((HashMap<String,String>) getChild(groupPosition, childPosition)).get("desc");
		final String amountText = ((HashMap<String,String>) getChild(groupPosition, childPosition)).get("amount");
		final String categoryText = ((HashMap<String,String>) getChild(groupPosition, childPosition)).get("category");

		final String transactionPortionIdString = ((HashMap<String,String>) getChild(groupPosition, childPosition)).get("id");
		int transactionPortionId = Integer.parseInt(transactionPortionIdString);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}

		/*TextView txtListChild = (TextView) convertView
				.findViewById(R.id.lblListItem);

		txtListChild.setText(childText);*/

		TextView descTextV = (TextView) convertView.findViewById(R.id.description_text);
		descTextV.setText(descText);
		TextView amountTextV = (TextView) convertView.findViewById(R.id.amount_text);
		amountTextV.setText(amountText);
		TextView categoryTextV = (TextView) convertView.findViewById(R.id.category_text);
		categoryTextV.setText(categoryText);

		convertView.setTag(transactionPortionId);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
	                         View convertView, ViewGroup parent) {
		String dateText = ((HashMap<String,String>) getGroup(groupPosition)).get("date");
		String timeText = ((HashMap<String,String>) getGroup(groupPosition)).get("time");
		String nameText = ((HashMap<String,String>) getGroup(groupPosition)).get("name");
		String totalText = ((HashMap<String,String>) getGroup(groupPosition)).get("total");

		final String transactionIdString = ((HashMap<String,String>) getGroup(groupPosition)).get("id");
		int transactionId = Integer.parseInt(transactionIdString);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.listview_layout, null);
		}

		/*TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);*/

		TextView dateTextV = (TextView) convertView.findViewById(R.id.date_text);
		dateTextV.setText(dateText);

		TextView timeTextV = (TextView) convertView.findViewById(R.id.time_text);
		timeTextV.setText(timeText);

		TextView nameTextV = (TextView) convertView.findViewById(R.id.name_text);
		nameTextV.setText(nameText);

		TextView totalTextV = (TextView) convertView.findViewById(R.id.total_text);
		totalTextV.setText(totalText);

		convertView.setTag(transactionId);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}