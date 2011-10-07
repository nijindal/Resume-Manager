package com.ResumeManager;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MyCustomRecruitCursor extends SimpleCursorAdapter {

	ViewBundle bundle = new ViewBundle();
	Recruiter_struct data = new Recruiter_struct();
	Cursor cursor;
	LayoutInflater inflator;
	String[] from;
	int[] to;
	RelativeLayout boundry;
	Context ctx;
	final public String[] BE_branches= {
										"Biotechnology",		
								 		"Civil",		
								 		"Computer",		
								 		"Electrical",		
								 		"Electronics & Communication",		
								 		"Environmental",		
								 		"Information Technology",		
								 		"Mechanical",		
								 		"Polymer Science and Chemical Technology",		
								 		"Production",		
										"Polymer Science and Chemical Technology"
	 									};
	
	final public String[] ME_branches = {
										"BioInformatics",
										"Computer Technology & Applications",
										"Control & Instrumentation",
										"Electronics & Communication",
										"Environmental",
										"GeoTech",
										"Information Systems",
										"Microwave & Optical Communication",
										"Nano Science and Technology",
										"Power Systems",
							 	 		"Production",
							 	 		"Signal Processing and Design",
							 	 		"Software Engineering",
							 	 		"Structural",
							 	 		"Thermal",
							 	 		"VLSI & Embedded Systems"
							 	 		};
											
	public MyCustomRecruitCursor(Context context, int layout, Cursor c,String[] from , int[] to ) {
		super(context, 0, c, from, to);
		Log.d("custom","constructor");
		ctx = context;
		cursor = c;
		inflator = LayoutInflater.from(context);
		this.from = from;
		this.to = to;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		Log.d("custom","bind view");

		this.cursor = cursor;
		bundle.rec_name = (TextView)view.findViewById(to[0]);
		bundle.date = (TextView)view.findViewById(to[1]);
		bundle.branches_be = (TextView)view.findViewById(to[2]);
		bundle.branches_me = (TextView)view.findViewById(to[3]);
		bundle.branches_intern = (TextView)view.findViewById(to[4]);

	
		boundry = (RelativeLayout) view.findViewById(R.id.header);
		
		boundry.setClickable(true);
		boundry.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {

				Log.d("custom adapter","on click"+ v.getId());
			
				AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
//				builder.setTitle("Hey....it worked");

				Intent to_details = new Intent();
				to_details.setClassName("com.ResumeManager", "com.ResumeManager.Recruiter_detail");
				ctx.startActivity(to_details);
				AlertDialog alert = builder.create();
				alert.show();
			}	

		});

		
		
		data.grade = cursor.getString(cursor.getColumnIndex(from[0]));		
		data.rec_name = cursor.getString(cursor.getColumnIndex(from[1]));
		data.date = cursor.getString(cursor.getColumnIndex(from[2]));
		data.branches_be = cursor.getString(cursor.getColumnIndex(from[3]));
		data.pkg_be = cursor.getString(cursor.getColumnIndex(from[4]));
		data.cutoff_be = cursor.getString(cursor.getColumnIndex(from[5]));
		data.branches_me = cursor.getString(cursor.getColumnIndex(from[6]));
		data.pkg_me = cursor.getString(cursor.getColumnIndex(from[7]));
		data.cutoff_me = cursor.getString(cursor.getColumnIndex(from[8]));
		data.branches_intern = cursor.getString(cursor.getColumnIndex(from[9]));
		data.pkg_intern = cursor.getString(cursor.getColumnIndex(from[10]));
		data.cutoff_intern = cursor.getString(cursor.getColumnIndex(from[11]));
		
		addTextviews();
		
		setViewText(bundle.rec_name, data.rec_name);
		setViewText(bundle.date, data.date);
	
	}
	
	
public void addTextviews(){

	if(data.branches_be.length() != 0)
	{
		if(data.branches_be == "")
		Log.d("in add text BE part views","this shows it was empty earler" );
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2,-2);
		
		params.addRule(RelativeLayout.RIGHT_OF, R.id.Logo_list);
		params.addRule(RelativeLayout.BELOW, bundle.rec_name.getId());

		
		bundle.branches_be.setLayoutParams(params);
		bundle.branches_be.setText("B.Tech");
		bundle.branches_be.setTextSize(15);
		bundle.branches_be.setPadding(5, 0, 0, 0);
		makeDialog(bundle.branches_be);

	}
	if(data.branches_me.length()!=0)
	{
		Log.d("data branch ME",data.branches_me);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2,-2);
		
		if(data.branches_be!="")
		params.addRule(RelativeLayout.RIGHT_OF, bundle.branches_be.getId());
		
		params.addRule(RelativeLayout.BELOW, R.id.rec_name);

		
		bundle.branches_me.setLayoutParams(params);
		bundle.branches_me.setText("M.Tech");
		bundle.branches_me.setTextSize(15);
		bundle.branches_me.setPadding(5, 0, 0, 0);
		makeDialog(bundle.branches_me);
	}
	
	if(data.branches_intern.length() != 0)
	{
		Log.d("branch if  intern is: ",new Integer(data.branches_intern.length()).toString());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2,-2);
		
		if(data.branches_me!="")
			params.addRule(RelativeLayout.RIGHT_OF, bundle.branches_me.getId());
			
			else if(data.branches_be!="")
				params.addRule(RelativeLayout.RIGHT_OF, bundle.branches_be.getId());

			else	
				params.addRule(RelativeLayout.RIGHT_OF, R.id.Logo_list);
				

		params.addRule(RelativeLayout.BELOW, R.id.rec_name);
		
		bundle.branches_intern.setLayoutParams(params);
		bundle.branches_intern.setText("INTERN");
		bundle.branches_intern.setTextSize(15);
		bundle.branches_intern.setPadding(5, 0, 0, 0);
	}
}	
	
	
public void makeDialog(TextView view_received){
		
		Log.d("makeDialog", "going");
		view_received.setClickable(true);
		final TextView view = view_received;
		
		view.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {

				Log.d("custom adapter","on click");
				
				String[] items = fillDialogItems(view);

				AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
				builder.setTitle("Details  " + view.getText());
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {

					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}	

		});
	}

	public String[] fillDialogItems(TextView view){
		
		char[] branches = null;
		int i=0;
		Log.d("filldialogitems",(String)view.getText());
		ArrayList<String> eligibile_list = new ArrayList<String>(); 
		if(view.getText().equals("B.Tech"))
		{
				branches = data.branches_be.toCharArray();
				eligibile_list.add("pkg: " + data.pkg_be + " LPA");
				eligibile_list.add("cutoff: " + data.cutoff_be);
				for(i=0;i<branches.length;i++)	
				{
					if(branches[i] == '1')
						eligibile_list.add(BE_branches[i]);
				}
		}
		else if(view.getText().equals("M.Tech"))
		{
			Log.d("in  else","this one is for M.Tech");
			branches = data.branches_me.toCharArray();
			eligibile_list.add("pkg: " + data.pkg_me);
			eligibile_list.add("cutoff: " + data.cutoff_me);
			
			for(i=0;i<branches.length;i++)	
			{
				if(branches[i] == '1')
					eligibile_list.add(ME_branches[i]);
			}
		}
		
		String str [] = (String []) eligibile_list.toArray (new String [eligibile_list.size()]);
		
		return str;
	}
	


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = inflator.inflate(R.layout.row_recruiter, null);	
		bindView(view,context,cursor);
		return view;
	}

	private class ViewBundle{
		
		TextView grade;
		TextView rec_name;
		TextView date;
		TextView branches_be;
		TextView branches_me;
		TextView branches_intern;
	}
	
}