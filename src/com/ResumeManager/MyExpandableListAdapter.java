package com.ResumeManager;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter 
{
	private ArrayList<String> groups = new ArrayList<String>();

    private ArrayList<ArrayList<String>> children = new ArrayList<ArrayList<String>>();
//
//    private String[] groups = { "People Names", "Dog Names", "Cat Names", "Fish Names" };
//    private String[][] children = {
//            { "Arnold", "Barry", "Chuck", "David" },
//            { "Ace", "Bandit", "Cha-Cha", "Deuce" },
//            { "Fluffy", "Snuggles" },
//            { "Goldy", "Bubbles" }
//           };
    private Context ctx;
	
	public MyExpandableListAdapter(Recruiter_detail recruiterDetail,
			ArrayList<String> arrayList, ArrayList<ArrayList<String>> arrayList2) {
	ctx = recruiterDetail;	
	groups.add("B.E/B.Tech");
	groups.add("M.E/M.Tech");
	
	ArrayList<String> temp = new ArrayList<String>();
	temp.add("Computers");
	temp.add("Polymer");
	temp.add("Civil");
	temp.add("Information Technology");
	children.add(temp);
	temp = new ArrayList<String>();
	temp.add("Information security");
	temp.add("Embedded Systems");
	temp.add("Computer and Electrical");
	temp.add("Computer Networks");
	children.add(temp);
	
		
	
	}   
	
	public Object getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
    	return children.get(groupPosition).size();
    }

 
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
    	String child = (String)getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_layout, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tvChild);
        tv.setText(child);
        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
    	String group = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_layout, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
        tv.setText(group);
        return convertView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }

}