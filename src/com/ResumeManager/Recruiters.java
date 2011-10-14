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

public class Recruiters extends ListActivity {

	public static int number_of_recruiters=0;
	Recruitersdb recruiters_db = null;
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
		recruiters_db = new Recruitersdb(this,null);
		progress_bar = (ProgressBar)findViewById(R.id.progressbar_rec);
		refresh_button = (ImageView) findViewById(R.id.refresh_rec);
		fetchandshow();
		refresh_list(refresh_button);
	}
	
	public void refresh_list(View v){
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
	
	public void fetchandshow(){
		Cursor data_db = recruiters_db.fetch_all();
		
		Log.d("fetch and show","recruiters");
		startManagingCursor(data_db);
  
		String[] from = new String[] {Recruitersdb.identity, Recruitersdb.grade,Recruitersdb.rec_name,Recruitersdb.date,
				Recruitersdb.branches_be,Recruitersdb.pkg_be, Recruitersdb.cutoff_be,
				Recruitersdb.branches_me,Recruitersdb.pkg_me, Recruitersdb.cutoff_me,
				Recruitersdb.branches_intern, Recruitersdb.pkg_intern, Recruitersdb.cutoff_intern};

		int[] to = new int[] {R.id.rec_name, R.id.date_rec, R.id.branches_be, R.id.branches_me, R.id.branches_intern};

		progress_bar.setVisibility(View.INVISIBLE);
		MyCustomRecruitCursor adapter = new MyCustomRecruitCursor(this, R.layout.row_recruiter,data_db,from,to);
		setListAdapter(adapter);
		Log.d("back to save to db","recriters");
	}
	
	void save_to_db(){
		
		recruiters_db = new Recruitersdb(this, recruiters_list);
		recruiters_db.add_new_to_db();
		fetchandshow();
		
	}	
	
	private class Retrieve_recruiters extends AsyncTask<Void,Void,Void>{


		@Override
		protected Void doInBackground(Void... params) {
			Log.d("in background","recruiters");
			recruiters_list = BackgroundProcess.Recruiters_data();
			if(recruiters_list!=null)
				number_of_recruiters += recruiters_list.size();
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
