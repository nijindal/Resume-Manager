package com.ResumeManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Announcement_detail extends Activity{

	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle data = getIntent().getExtras();
		
		Announcement_struct rcvd_data = new Announcement_struct(); 
		rcvd_data.com_name = data.getString("com_name");
		rcvd_data.date = data.getString("date");
		rcvd_data.time = data.getString("time");
		rcvd_data.body = data.getString("body");
		rcvd_data.user = data.getString("user");
			
		Log.d("in on create","ANNOUNCEMENT DETAILS");
		setContentView(R.layout.announcement_detail);
		
		TextView temp = (TextView)findViewById(R.id.announcement_title);
		temp.setText(rcvd_data.com_name);
		
		temp = (TextView)findViewById(R.id.datetime_announce);
		temp.setText(rcvd_data.date + " (" +rcvd_data.time +")");
		
		temp = (TextView)findViewById(R.id.body_announce);
		temp.setText(rcvd_data.body);
		
		temp = (TextView)findViewById(R.id.user_announce);
		temp.setText(rcvd_data.user);

	}
}
