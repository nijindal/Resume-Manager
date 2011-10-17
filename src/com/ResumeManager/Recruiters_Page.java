package com.ResumeManager;

import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Recruiters_Page extends ListActivity {

//	public static int number_of_recruiters=0;
	public static int last_recruiter = 0;
	static Recruiters_db recruiters_db = null;
	public ArrayList<String> recruiters = new ArrayList<String>();
	private ArrayList<Recruiter_struct> recruiters_list = new ArrayList<Recruiter_struct>();
	ProgressBar progress_bar = null;
	ImageView refresh_button = null;

	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.d("in on create","Recruiters");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recruiters_page);
		recruiters_db = new Recruiters_db(this,null);
		progress_bar = (ProgressBar)findViewById(R.id.progressbar_rec);
		refresh_button = (ImageView) findViewById(R.id.refresh_rec);
		fetchandshow();
		refresh_list();
	}
	
	public void refresh_list(){
		progress_bar.setVisibility(View.VISIBLE);
		new Retrieve_recruiters().execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.menuoptions_r, menu);
	    return true;
	}
	
	
	public boolean OnOptionItemSelected(MenuItem Item)
	{
		switch(Item.getItemId())
		{
		case R.id.refresh_menu_R:
			///Announcements....
			return true;
			
		case R.id.announce_menu_R:
			//Retrieve_recruiters handle = new Retrieve_recruiters();
			//handle.execute();
			//Recruiters
			return true;
			
		case R.id.Logout_menu:
			//Logout
			return true;
		default:
			return super.onOptionsItemSelected(Item);
		}
	}
	
	private void fetchandshow(){
		Cursor data_db = recruiters_db.fetch_all();
		Log.d("fetch and show","recruiters");
		startManagingCursor(data_db);
  	
		String[] from = new String[] {Recruiters_db.identity, Recruiters_db.grade,Recruiters_db.rec_name,Recruiters_db.date,
				Recruiters_db.branches_be,Recruiters_db.pkg_be, Recruiters_db.cutoff_be,
				Recruiters_db.branches_me,Recruiters_db.pkg_me, Recruiters_db.cutoff_me,
				Recruiters_db.branches_intern, Recruiters_db.pkg_intern, Recruiters_db.cutoff_intern};

		int[] to = new int[] {R.id.rec_name, R.id.date_rec, R.id.branches_be, R.id.branches_me, R.id.branches_intern};

		if(data_db.getCount()>0){
			Log.d("YEAH the count is grater than 0","Fetch and show");
			data_db.moveToFirst();
			last_recruiter = data_db.getInt(data_db.getColumnIndex("identity"));
		}
		data_db.moveToFirst();
		
		Log.d("ID OF LAST ANNOUNCEMENT IS : ", new Integer(last_recruiter).toString());
		progress_bar.setVisibility(View.INVISIBLE);
		MyCustomRecruitCursor adapter = new MyCustomRecruitCursor(this, R.layout.row_recruiter,data_db,from,to);
		setListAdapter(adapter);
		Log.d("back to save to db","recriters");
	}
	
	private void save_to_db(){
		
		recruiters_db = new Recruiters_db(this, recruiters_list);
		recruiters_db.add_new_to_db();
		fetchandshow();
	}	
	
	private class Retrieve_recruiters extends AsyncTask<Void,Void,Void>{


		@Override
		protected Void doInBackground(Void... params) {
			Log.d("in background","recruiters");
			recruiters_list = BackgroundProcess.Recruiters_data(last_recruiter);
//			if(recruiters_list!=null)
//				number_of_recruiters += recruiters_list.size();
			return null;
		}
		
		protected void onPostExecute(Void result)
		{
			Log.d("onpostexecute","announcement");
			super.onPostExecute(result);
			if(recruiters_list!=null)
				save_to_db();
			else
				progress_bar.setVisibility(View.INVISIBLE);				
		}
	}
}
