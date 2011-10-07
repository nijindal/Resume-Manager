package com.ResumeManager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class Recruiter_detail extends Activity {

	private Rec_details all_details;
	Context ctx;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ctx = this;
		Log.d("in recruiter_details","on create");
		setContentView(R.layout.rec_detail);
	
		Retrieve_data get_data = new Retrieve_data();
		get_data.execute();

	}
	
	public void change_layout(int id){
	
		TextView view = (TextView) findViewById(id);
		Log.d("fnc function","recruiter details");
		ViewGroup.LayoutParams params = view.getLayoutParams();

		if(params.height!=-2)
		{
			Log.d(new Integer(params.height).toString(),new Integer(params.width).toString());
			params.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
			params.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
			view.setLayoutParams(params);
		}
		else
		{
			params.height = 20;
			view.setLayoutParams(params);
		}
	}
	
	void set_data_onview(){
		
		views rec_detail_view = new views();

		rec_detail_view.recruiter = (TextView) findViewById(R.id.recruiter);
		rec_detail_view.rec_desc = (TextView) findViewById(R.id.rec_desc);
		rec_detail_view.date = (TextView) findViewById(R.id.visiting_date);
		
		
		ExpandableListView listView = (ExpandableListView) findViewById(R.id.expand_list);
		
		MyExpandableListAdapter adapter = new MyExpandableListAdapter(this, new ArrayList<String>(),
                new ArrayList<ArrayList<String>>());
		
		listView.setAdapter(adapter);
		
		rec_detail_view.ctc = (TextView) findViewById(R.id.ctc_data);
		rec_detail_view.cutoff = (TextView) findViewById(R.id.cutoff_data);
		rec_detail_view.job_desc = (TextView) findViewById(R.id.job_desc_data);
		
		rec_detail_view.recruiter.setText(all_details.com_name);
	
//For adding click listener there............		
		rec_detail_view.rec_desc.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				
				Log.d("recruiter_detail","on click"+ v.getId());
				change_layout(v.getId());
			}
			
		});	
		
		rec_detail_view.date.setText(all_details.visit_date);
		rec_detail_view.ctc.setText(all_details.ctc);
		rec_detail_view.cutoff.setText(all_details.cutoff);
		rec_detail_view.job_desc.setText(all_details.job_desc);
	}
	
	private class views{
		
		TextView rec_desc;
		TextView recruiter;
		TextView date;
		TextView ctc;
		TextView cutoff;
		TextView job_desc;
		
	}
	
	private class Retrieve_data extends AsyncTask<Void, Void, Void> {

		String response;
		@Override
		protected Void doInBackground(Void... params) {
			
			all_details = BackgroundProcess.get_recruiter_details(10);
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.d("in post_execute " + all_details.com_name,all_details.visit_date);
			System.out.print(all_details);
			set_data_onview();

			
		}

	}
}
	
