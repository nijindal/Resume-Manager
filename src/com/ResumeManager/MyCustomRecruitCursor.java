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
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MyCustomRecruitCursor extends SimpleCursorAdapter {

	ViewBundle bundle = null;// new ViewBundle();
	Recruiter_struct data = null;// = new Recruiter_struct();
	Cursor cursor;
	LayoutInflater inflator;
	String[] from;
	int[] to;
	RelativeLayout boundry=null;
	Context ctx;
	final static public String[] BE_branches= {
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
										};
	
	
	
	final static public String[] ME_branches = {
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
							 	 		"VLSI &  Embedded Systems"
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
		data = null;
		data = new Recruiter_struct();
		bundle = new ViewBundle();
		this.cursor = cursor;
		bundle.rec_name = (TextView)view.findViewById(to[0]);
		bundle.date = (TextView)view.findViewById(to[1]);
		bundle.branches_be = (TextView)view.findViewById(to[2]);
		bundle.branches_me = (TextView)view.findViewById(to[3]);
		bundle.branches_intern = (TextView)view.findViewById(to[4]);
	
		bundle.grade_icon = (ImageView) view.findViewById(R.id.Logo_list);
		
		data.id = Integer.parseInt((cursor.getString(cursor.getColumnIndex(from[0]))));	
		data.grade = cursor.getString(cursor.getColumnIndex(from[1]));		
		data.rec_name = cursor.getString(cursor.getColumnIndex(from[2]));
		data.date = cursor.getString(cursor.getColumnIndex(from[3]));
		data.branches_be = cursor.getString(cursor.getColumnIndex(from[4]));
		data.pkg_be = cursor.getString(cursor.getColumnIndex(from[5]));
		data.cutoff_be = cursor.getString(cursor.getColumnIndex(from[6]));
		data.branches_me = cursor.getString(cursor.getColumnIndex(from[7]));
		data.pkg_me = cursor.getString(cursor.getColumnIndex(from[8]));
		data.cutoff_me = cursor.getString(cursor.getColumnIndex(from[9]));
		data.branches_intern = cursor.getString(cursor.getColumnIndex(from[10]));
		data.pkg_intern = cursor.getString(cursor.getColumnIndex(from[11]));
		data.cutoff_intern = cursor.getString(cursor.getColumnIndex(from[12]));
		
//		Log.d("Branches Intern",data.branches_be);
		
		boundry = new RelativeLayout(context);

		boundry = (RelativeLayout) view.findViewById(R.id.header);
		boundry.setClickable(true);
		boundry.setTag(data);

		
		boundry.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {


				///some Unique id for the specific recruiter needs to be passed...... this id will be used to 
				Log.d("custom adapter","on click"+ v.getId());
			
				Log.d("value of id received is:","in system.out");
				Object o = v.getTag();
				Recruiter_struct ob;
				ob = (Recruiter_struct) o;
				System.out.print("MTECH IS: " + ob.branches_me);
				
				String cutoff = ((ob.cutoff_be.length()==0)?"":ob.cutoff_be + "(B.E.)") + " " + ((ob.cutoff_me.length()==0)?"":ob.cutoff_me + "(M.E.)") + 
								((ob.cutoff_intern.length()==0)?"":ob.cutoff_intern + "(Intern)");
				
				String ctc = ((ob.pkg_be.length()==0)?"":ob.pkg_be + "(B.E.)") + " " + ((ob.pkg_me.length()==0)?"":ob.pkg_me + "(M.E.)") + 
				((ob.pkg_intern.length()==0)?"":ob.pkg_intern + "(Intern)");
				
				Intent to_details = new Intent();
				MyParcelable parcel = new MyParcelable(new Integer(ob.id).toString(), ob.grade, ob.rec_name, ob.branches_be, ob.branches_me, ob.branches_intern, ob.date, cutoff, ctc);
				to_details.putExtra("old_data", parcel);
				to_details.setClassName("com.ResumeManager", "com.ResumeManager.Recruiter_detail");
				ctx.startActivity(to_details);
			}	

		});

//		Log.d("in bind view  data.branches_me :" + data.branches_me + "com_name is : " + data.rec_name, "pkg me : "+ data.pkg_me);

//NOTE:  Do initialize all the views in the row else scrolling will show redundant data.....		
		bundle.branches_be.setVisibility(View.INVISIBLE);
		bundle.branches_me.setVisibility(View.INVISIBLE);
		bundle.branches_intern.setVisibility(View.INVISIBLE);

		
		setViewText(bundle.rec_name, data.rec_name);
		setViewText(bundle.date, data.date);
		
		
		
		if(data.grade.equals("S"))
			bundle.grade_icon.setImageResource(R.drawable.s);
		else if(data.grade.equals("A+"))
			bundle.grade_icon.setImageResource(R.drawable.aplus);
		else if(data.grade.equals("A"))
			bundle.grade_icon.setImageResource(R.drawable.a);
		
		Log.d("Branches Intern",data.branches_intern);
		addTextviews();
		
	}
	
	
public void addTextviews(){

	if(data.branches_be.length() != 0)
	{
		Log.d("BE, filling up the textviews",data.rec_name + "Branch BE length is not zero");

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2,-2);
		
		params.addRule(RelativeLayout.RIGHT_OF, R.id.Logo_list);
		params.addRule(RelativeLayout.BELOW, bundle.rec_name.getId());

		bundle.branches_be.setVisibility(View.VISIBLE);
		bundle.branches_be.setLayoutParams(params);
		bundle.branches_be.setTextSize(20);
		bundle.branches_be.setPadding(10, 0, 0, 0);
		makeDialog(bundle.branches_be);

	}
	if(data.branches_me.length()!=0)
	{
		Log.d("ME, filling up the textviews    ",data.rec_name + "Branch me length is not zero  "  + data.branches_me );
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2,-2);
		
		if(data.branches_be!="")
		params.addRule(RelativeLayout.RIGHT_OF, bundle.branches_be.getId());
		
		params.addRule(RelativeLayout.BELOW, R.id.rec_name);

		bundle.branches_me.setLayoutParams(params);
		bundle.branches_me.setVisibility(View.VISIBLE);

		bundle.branches_me.setTextSize(20);
		bundle.branches_me.setPadding(10, 0, 0, 0);
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
		
		bundle.branches_intern.setVisibility(View.VISIBLE);		
		bundle.branches_intern.setLayoutParams(params);
		bundle.branches_intern.setTextSize(20);
		bundle.branches_intern.setPadding(10, 0, 0, 0);
		makeDialog(bundle.branches_intern);

	}
}	
	
	
public void makeDialog(TextView view_received){
		
		Log.d("makeDialog", "going" + view_received.getText());
		view_received.setClickable(true);
		final TextView view = view_received;

		Log.d("pkg is " + data.pkg_be, "Branches BE is "+ data.branches_be);
		view.setOnClickListener(new save_data(data,view));
	}

private class save_data implements OnClickListener {

	private Recruiter_struct com_data;
	TextView t_view;
	public save_data(Recruiter_struct data, TextView view) {
			com_data = data;
			t_view = view;
	}

	@Override
	public void onClick(View v) {
		
		Log.d("custom adapter","on   click");
		Log.d("pkg is " + com_data.pkg_be, "Branches BE is "+ com_data.branches_be);	
		String[] items = fillDialogItems(t_view,com_data);

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("Details  " + t_view.getText());
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
}


	public String[] fillDialogItems(TextView view,Recruiter_struct com_data){
		
		char[] branches = null;
		int i=0;
		Log.d("filldialogitems",(String)view.getText());
		ArrayList<String> eligibile_list = new ArrayList<String>(); 
		if(view.getText().equals("B.Tech"))
		{
				Log.d("FILL DIALOG ITEMS pkg is " + com_data.pkg_be, "Branches BE is "+ com_data.branches_be);	
				branches = com_data.branches_be.toCharArray();
				eligibile_list.add("pkg: " + com_data.pkg_be + " LPA");
				eligibile_list.add("cutoff: " + com_data.cutoff_be);
				for(i=0;i<branches.length;i++)	
				{
					if(branches[i] == '1')
						eligibile_list.add(BE_branches[i]);
				}
		}
		else if(view.getText().equals("M.Tech"))
		{
			Log.d("in  else","this one is for M.Tech" + com_data.pkg_me + " Branches" + com_data.branches_me );
			branches = com_data.branches_me.toCharArray();
			eligibile_list.add("pkg: " + com_data.pkg_me);
			eligibile_list.add("cutoff: " + com_data.cutoff_me);
			
			for(i=0;i<branches.length;i++)	
			{
				if(branches[i] == '1')
					eligibile_list.add(ME_branches[i]);
			}
		}
		
		else if(view.getText().equals("Intern"))
		{
			Log.d("in  else","this one is for Intern" + com_data.pkg_intern + " Branches" + com_data.branches_intern );
			branches = com_data.branches_intern.toCharArray();
			eligibile_list.add("pkg: " + com_data.pkg_intern);
			eligibile_list.add("cutoff: " + com_data.cutoff_intern);
			
			for(i=0;i<branches.length;i++)	
			{
				if(branches[i] == '1')
					eligibile_list.add(BE_branches[i]);
			}
		}
		
		String str [] = (String []) eligibile_list.toArray (new String [eligibile_list.size()]);
		
		return str;
	}
	


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		Log.d("NEW VIEW","JUST CREATED");
		View view = inflator.inflate(R.layout.row_recruiter, null);	
//		bindView(view,context,cursor);
		return view;
	}

	private class ViewBundle{
		
		ImageView grade_icon;
		TextView rec_name;
		TextView date;
		TextView branches_be;
		TextView branches_me;
		TextView branches_intern;
	}
	
}