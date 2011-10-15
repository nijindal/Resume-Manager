package com.ResumeManager;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
	View footer;
	View header;
	views rec_detail_view;
	ProgressBar progress = null;
	ImageView logo=null;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ctx = this;
		Log.d("in recruiter_details","on create");
		setContentView(R.layout.rec_detail);
		Bundle data = getIntent().getExtras();
		MyParcelable old_data = data.getParcelable("old_data");
		Log.d(old_data.com_id,old_data.cutoff);
		fetchandshow(old_data);
//		refresh_data();
	}
	
	public void fetchandshow(MyParcelable oldData){
		
		all_details = new Rec_details();
		all_details.com_id = oldData.com_id;
		all_details.grade = oldData.grade;
		all_details.com_name = oldData.com_name;
		all_details.branches_be = oldData.branches_be;
		all_details.branches_me = oldData.branches_me;
		all_details.branches_intern = oldData.branches_intern;
		all_details.rec_desc = "";
		all_details.visit_date = oldData.visit_date;
		all_details.cutoff = oldData.cutoff;
		all_details.job_desc = "";
		all_details.ctc = oldData.ctc;
		all_details.can_apply = "no";
		set_data_onview();

		
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
		
		groups.clear();
		children.clear();
		
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
		
		if(all_details.branches_intern.length()!=0)
		{

			HashMap m = new HashMap();
			ArrayList temp = new ArrayList();
			m.put("groups","Intern");
			groups.add(m);
			char[] branches = null;
			branches = all_details.branches_intern.toCharArray();
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
	}
	
	void refresh_data(){
		
		Retrieve_data get_data = new Retrieve_data();
		get_data.execute();
		progress.setVisibility(View.INVISIBLE);	
	}
	
	void put_refresh_data(){
		put_branches();
		
		RelativeLayout layout =  (RelativeLayout)footer.findViewById(R.id.button_rel);
		if(all_details.can_apply.equals("yes"))
		{
			Button sub_button = new Button(footer.getContext());
			LayoutParams params = new RelativeLayout.LayoutParams(-2,-2);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			params.topMargin=5;
			sub_button.setLayoutParams(params);
			sub_button.setText("Submit");
			
			layout.addView(sub_button);
		}
		else if(all_details.can_apply.equals("no"))
		{
			Log.d("Notification....","you are not eligible  for the cmpany");
		}
	

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
	void set_data_onview(){
		
		groups = new ArrayList();
		children = new ArrayList();
		
		put_branches();
		
		rec_detail_view = new views();

		LayoutInflater factory = getLayoutInflater();
		header = factory.inflate(R.layout.header, null);
		footer = factory.inflate(R.layout.footer, null);
		
		logo = (ImageView) header.findViewById(R.id.Logo);
		
		rec_detail_view.recruiter = (TextView) header.findViewById(R.id.recruiter);
		rec_detail_view.rec_desc = (TextView) header.findViewById(R.id.rec_desc);
		rec_detail_view.date = (TextView) header.findViewById(R.id.visiting_date);
		rec_detail_view.ctc = (TextView) footer.findViewById(R.id.ctc_data);
		rec_detail_view.cutoff = (TextView) footer.findViewById(R.id.cutoff_data);
		rec_detail_view.job_desc = (TextView) footer.findViewById(R.id.job_desc_data);		
		
		progress = (ProgressBar) header.findViewById(R.id.progressbar_rec_det);
	

		if(all_details.grade.equals("S"))
			logo.setImageResource(R.drawable.s);
		else if(all_details.grade.equals("A+"))
			logo.setImageResource(R.drawable.aplus);
		else if(all_details.grade.equals("A"))
			logo.setImageResource(R.drawable.a);
		
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
		this.getExpandableListView().setAdapter(expListAdapter);

		rec_detail_view.recruiter.setText(all_details.com_name);
		rec_detail_view.date.setText(all_details.visit_date);
		rec_detail_view.ctc.setText(all_details.ctc);
		rec_detail_view.cutoff.setText(all_details.cutoff);
		
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

		@Override
		protected Void doInBackground(Void... params) {
			SharedPreferences myPrefs = Recruiter_detail.this.getSharedPreferences("unique_user_id", MODE_WORLD_READABLE);
			int user_id = myPrefs.getInt("user_id",-1);
			Log.d("so the   found user id is:  ",new Integer(user_id).toString());
			all_details = BackgroundProcess.get_recruiter_details(user_id);
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.d("in post_execute " + all_details.com_name,all_details.visit_date);
			System.out.print(all_details);
			put_refresh_data();

			
		}

	}
}
	
