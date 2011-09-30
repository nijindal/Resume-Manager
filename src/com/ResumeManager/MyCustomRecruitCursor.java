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

		System.out.println(from);
		this.cursor = cursor;
		bundle.rec_name = (TextView)view.findViewById(to[0]);
		bundle.date = (TextView)view.findViewById(to[1]);
		bundle.branches_be = (TextView)view.findViewById(to[2]);
		bundle.branches_me = (TextView)view.findViewById(to[3]);
		bundle.branches_intern = (TextView)view.findViewById(to[4]);

	
		RelativeLayout boundry = (RelativeLayout) view.findViewById(R.id.header);
		
		boundry.setClickable(true);
		boundry.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {

				Log.d("custom adapter","on click"+ v.getId());
			
				AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
				builder.setTitle("Hey....it worked");
				Intent to_details = new Intent();
				to_details.setClassName("com.ResumeManager", "com.ResumeManager.Recruiter_detail");
				startActivity(to_details);
//				builder.setItems(items, new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int item) {
//
//					}
//				});
				AlertDialog alert = builder.create();
				alert.show();
			}	

		});

		data.grade = cursor.getString(cursor.getColumnIndex(from[0]));		
		data.rec_name = cursor.getString(cursor.getColumnIndex(from[1]));
		data.date = cursor.getString(cursor.getColumnIndex(from[2]));
		data.eligibilty = cursor.getString(cursor.getColumnIndex(from[3]));
			
		check_branches(data.eligibilty);

		setViewText(bundle.rec_name, data.rec_name);
		setViewText(bundle.date, data.date);
	
	}
	
	
	public void check_branches(String eligibility){
		
		String[] eligible = eligibility.split(" ");
		int size = eligible.length;
		int i;
		System.out.println(size + "this is the system.out output");
		Log.d("in check branches","custom adapter");
		for(i=0;i<size;i++)
		{
			if(eligible[i].equals("BE"))
				{
					Log.d("check bran ches","BE");
					data.branches_be = cursor.getString(cursor.getColumnIndex(from[4]));
					data.pkg_be = cursor.getString(cursor.getColumnIndex(from[5]));
					data.cutoff_be = cursor.getString(cursor.getColumnIndex(from[6]));
					TextView BE = bundle.branches_be;
					BE.setText("BE/B.Tech");
					BE.setClickable(true);
					makeDialog(BE);
					
				}
			else if(eligible[i].equals("ME"))
				{
					Log.d("check  branches","ME");
					data.branches_me = cursor.getString(cursor.getColumnIndex(from[7]));
					data.pkg_me = cursor.getString(cursor.getColumnIndex(from[8]));
					data.cutoff_me = cursor.getString(cursor.getColumnIndex(from[9]));
					TextView ME = bundle.branches_me;
					ME.setText("ME/M.Tech");
					ME.setClickable(true);
					makeDialog(ME);
				}
				
			else if(eligible[i].equals("INTERN"))
				{
				///will do this later.........
				}
		}
	}
	
	
	public void makeDialog(TextView view_received){
		
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
		if(view.getText().equals("BE/B.Tech"))
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
		else if(view.getText().equals("ME/M.Tech"))
		{
			Log.d("fillinto dialog","ME/MTTECH"+data.branches_me);
			branches = data.branches_me.toCharArray();
			eligibile_list.add("pkg: " + data.pkg_me);
			eligibile_list.add("cutoff: " + data.cutoff_me);
			
			for(i=0;i<branches.length;i++)	
			{
				if(branches[i] == '1')
					eligibile_list.add(ME_branches[i]);
			}
		}
		
		String str [] = (String []) eligibile_list.toArray (new String [eligibile_list.size ()]);
		
		return str;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		Log.d("custom","new view recruiters");
		View view = inflator.inflate(R.layout.row_recruiter, null);	
		bindView(view,context,cursor);
		return view;
	}

	private class ViewBundle{
		
		TextView grade;
		TextView rec_name;
		TextView date;
		TextView eligibility;
		TextView branches_be;
		TextView branches_me;
		TextView branches_intern;
	}
	
}