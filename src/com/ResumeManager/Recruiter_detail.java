package com.ResumeManager;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class Recruiter_detail extends ExpandableListActivity {

	ProgressDialog pleasewait;
	private Rec_details all_details;
	Context ctx;
	ExpandableListView listView;
	ArrayList groups;
	ArrayList children;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		listView = getExpandableListView();
		ctx = this;
		pleasewait = ProgressDialog.show(this, "","Retrieving Recruiter Details...Please wait");
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
	
	void put_branches(){
		if(all_details.branches_be.length()!=0)
		{
				HashMap m = new HashMap();
				ArrayList temp = new ArrayList();
				m.put("groups","B.E/B.Tech");
				groups.add(m);
				char[] branches = null;
				branches = all_details.branches_be.toCharArray();
				for(int i=0;i<branches.length;i++)	
				{
						if(branches[i] == '1'){
						Log.d(MyCustomRecruitCursor.BE_branches[i],"doing test");
						m = new HashMap();
						m.put("children",MyCustomRecruitCursor.BE_branches[i]);
						temp.add(m);
						}
				}		
				children.add(temp);
		}
						
		
		if(all_details.branches_me.length()!=0)
		{

			HashMap m = new HashMap();
			ArrayList temp = new ArrayList();
			m.put("groups","M.E/M.Tech");
			groups.add(m);
			char[] branches = null;
			branches = all_details.branches_be.toCharArray();
			for(int i=0;i<branches.length;i++)	
			{
					if(branches[i] == '1'){
					Log.d(MyCustomRecruitCursor.ME_branches[i],"doing test");
					m = new HashMap();
					m.put("children",MyCustomRecruitCursor.ME_branches[i]);
					temp.add(m);
					}
			}		
			children.add(temp);
		}
	}
	
	void set_data_onview(){
		
		groups = new ArrayList();
		children = new ArrayList();
		
		put_branches();
		
		views rec_detail_view = new views();

		LayoutInflater factory = getLayoutInflater();
		View header = factory.inflate(R.layout.header, null);
		View footer = factory.inflate(R.layout.footer, null);
		
		rec_detail_view.recruiter = (TextView) header.findViewById(R.id.recruiter);
		rec_detail_view.rec_desc = (TextView) header.findViewById(R.id.rec_desc);
		rec_detail_view.date = (TextView) header.findViewById(R.id.visiting_date);
		rec_detail_view.ctc = (TextView) footer.findViewById(R.id.ctc_data);
		rec_detail_view.cutoff = (TextView) footer.findViewById(R.id.cutoff_data);
		rec_detail_view.job_desc = (TextView) footer.findViewById(R.id.job_desc_data);		
//		rec_detail_view.sub_button = (Button) footer.findViewById(R.id.sub_button);
		
		RelativeLayout layout =  (RelativeLayout)footer.findViewById(R.id.button_rel);
		if(all_details.can_apply.equals("yes"))
		{
			Button sub_button = new Button(footer.getContext());
			LayoutParams params = new RelativeLayout.LayoutParams(-2,-2);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//			params.leftMargin=100;
			params.topMargin=5;
			sub_button.setLayoutParams(params);
			sub_button.setText("Submit");
			
			layout.addView(sub_button);
		}
		else if(all_details.can_apply.equals("no"))
		{
			Log.d("Notification....","you are not eligible  for the cmpany");
		}
		
		SimpleExpandableListAdapter expListAdapter = 
			new SimpleExpandableListAdapter( 
					this, 
					groups,
					R.layout.group_layout,
					new String[] {"groups"},
					new int[]{R.id.tvGroup},
					children,
					R.layout.child_layout,
					new String[]{"children"},
					new int[]{R.id.tvChild}
				);
		
			
		this.getExpandableListView().addHeaderView(header);
		this.getExpandableListView().addFooterView(footer);

//		ExpandableListView listView = (ExpandableListView) findViewById(R.id.expand_list);
//		MyExpandableListAdapter adapter = new MyExpandableListAdapter(this, new ArrayList<String>(),
//                new ArrayList<ArrayList<String>>());
//
//		listView.addHeaderView(header);
//		listView.addFooterView(footer);
		setListAdapter(expListAdapter);

		pleasewait.dismiss();
	
///For adding click listener there............		
		rec_detail_view.rec_desc.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				
				Log.d("recruiter_detail","on click"+ v.getId());
				change_layout(v.getId());
			}
			
		});	

		rec_detail_view.recruiter.setText(all_details.com_name);
		rec_detail_view.rec_desc.setText(all_details.rec_desc);
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
		Button sub_button;
		
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
	
