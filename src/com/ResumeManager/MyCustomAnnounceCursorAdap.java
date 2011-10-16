package com.ResumeManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class MyCustomAnnounceCursorAdap extends SimpleCursorAdapter {

	Cursor cursor;
	LayoutInflater inflator;
	String[] from;
	int[] to;
	Context ctx;
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
		
		RelativeLayout layout =  (RelativeLayout)view.findViewById(R.id.relative_announce);
		layout.setClickable(true);
		
		data.com_name = cursor.getString(cursor.getColumnIndex(from[0]));
		data.date = cursor.getString(cursor.getColumnIndex(from[1]));
		data.time = cursor.getString(cursor.getColumnIndex(from[2]));
		data.body = cursor.getString(cursor.getColumnIndex(from[3]));
		data.user = cursor.getString(cursor.getColumnIndex(from[4]));
		
		final Bundle data_collect = new Bundle();
		data_collect.putString("com_name", data.com_name);
		data_collect.putString("body", data.body);
		data_collect.putString("date", data.date);
		data_collect.putString("time", data.time);
		data_collect.putString("user", data.user);
		
		
		layout.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {

			Intent to_details = new Intent();
			to_details.putExtras(data_collect);
			to_details.setClassName("com.ResumeManager", "com.ResumeManager.Announcement_detail");
			ctx.startActivity(to_details);
		}
		});
		
		bundle.com_name = (TextView)view.findViewById(to[0]);
		bundle.date = (TextView)view.findViewById(to[1]);
		bundle.time = (TextView)view.findViewById(to[2]);
		bundle.body = (TextView)view.findViewById(to[3]);
		bundle.user = (TextView)view.findViewById(to[4]);
	
		setViewText(bundle.com_name, data.com_name);
		setViewText(bundle.date, data.date);
		setViewText(bundle.time,data.time);
		
		String temp = Html.fromHtml(data.body).toString();

		bundle.body.setText(Html.fromHtml(Html.fromHtml(data.body).toString()));

		bundle.body.setMovementMethod(LinkMovementMethod.getInstance());
		setViewText(bundle.user,data.user);
	
	}
	

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		Log.d("custom","new view");
		ctx = context;
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
