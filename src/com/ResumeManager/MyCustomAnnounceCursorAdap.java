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
	String[] from;
	int[] to;
	public MyCustomAnnounceCursorAdap(Context context, int layout, Cursor c,String[] from , int[] to ) {
		super(context, 0, c, from, to);
		Log.d("custom","constructor");
		cursor = c;
		inflator = LayoutInflater.from(context);
		this.from = from;
		this.to = to;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		Log.d("custom","bind view");
		ViewBundle bundle = new ViewBundle();
		Announce data = new Announce();
		
		bundle.com_name = (TextView)view.findViewById(to[0]);
		bundle.date = (TextView)view.findViewById(to[1]);
		bundle.time = (TextView)view.findViewById(to[2]);
		bundle.body = (TextView)view.findViewById(to[3]);
		bundle.user = (TextView)view.findViewById(to[4]);
	
		data.com_name = cursor.getString(cursor.getColumnIndex(from[0]));
		data.date = cursor.getString(cursor.getColumnIndex(from[1]));
		data.time = cursor.getString(cursor.getColumnIndex(from[2]));
		data.body = cursor.getString(cursor.getColumnIndex(from[3]));
		data.user = cursor.getString(cursor.getColumnIndex(from[4]));
		
		setViewText(bundle.com_name, data.com_name);
		setViewText(bundle.date, data.date);
		setViewText(bundle.time,data.time);
		setViewText(bundle.body,data.body);
		setViewText(bundle.user,data.user);
	
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
