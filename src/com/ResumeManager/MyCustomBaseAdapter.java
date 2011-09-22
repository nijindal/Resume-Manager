package com.ResumeManager;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyCustomBaseAdapter extends BaseAdapter {
	 private ArrayList<info> list;
	 
	 private LayoutInflater mInflater;

	 public MyCustomBaseAdapter(Context context, ArrayList<info> results) {
		 Log.d("My Custom","constructor");
		 list = results;
		 mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }

	 public int getCount() {
	  return list.size();
	 }

	 public Object getItem(int position) {
	  return list.get(position);
	 }

	 public long getItemId(int position) {
	  return position;
	 }
	 
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	  ViewHolder holder;
	  Log.d("My Custom","getview");
	  if (convertView == null) {
	   convertView = mInflater.inflate(R.layout.row_announce, null);
	   holder = new ViewHolder();
	   holder.company = (TextView) convertView.findViewById(R.id.com_name);
//	   holder.Announcement = (TextView) convertView.findViewById(R.id.announce);
	   convertView.setTag(holder);
	  } 
	  
	  else {
	  holder = (ViewHolder) convertView.getTag();
	  }
	  
	  holder.company.setText(list.get(position).company);
	  holder.Announcement.setText(list.get(position).Announcement);
	  return convertView;
	 }
	 
	 static class ViewHolder {
		  TextView company;
		  TextView Announcement;
		 }
	}