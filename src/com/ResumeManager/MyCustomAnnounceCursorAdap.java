package com.ResumeManager;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class MyCustomAnnounceCursorAdap extends SimpleCursorAdapter {

	Cursor cursor;
	LayoutInflater inflator;
	public MyCustomAnnounceCursorAdap(Context context, Cursor c) {
		super(context, 0, c, null, null);
		Log.d("custom","constructor");
		cursor = c;
		inflator = LayoutInflater.from(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		Log.d("custom","bind view");
		ViewBundle bundle = new ViewBundle();
		Announce data = new Announce();
		
		bundle.com_name = (TextView)view.findViewById(R.id.com_name);
		bundle.date = (TextView)view.findViewById(R.id.date);
		bundle.time = (TextView)view.findViewById(R.id.time);
		bundle.body = (TextView)view.findViewById(R.id.body);
		bundle.user = (TextView)view.findViewById(R.id.user);
	
		data.com_name = cursor.getString(1);
		data.date = cursor.getString(2);
		data.time = cursor.getString(3);
		data.body = cursor.getString(4);
		data.user = cursor.getString(5);
		
		bundle.com_name.setText(data.com_name);
		bundle.date.setText(data.date);
		bundle.time.setText(data.time);
		bundle.body.setText(data.body);
		bundle.user.setText(data.user);
	
	}
	

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		Log.d("custom","new view");
		View view = inflator.inflate(R.layout.row_announce, null);	
		bindView(view,context,cursor);
		return view;
	}

	private class ViewBundle{
		
		TextView com_name;
		TextView date;
		TextView time;
		TextView body;
		TextView user;
	}
	
}
