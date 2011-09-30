package com.ResumeManager;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;

public class Recruiters extends ListActivity {

	public ArrayList<String> recruiters = new ArrayList<String>();
	private ArrayList<Recruiter_struct> recruiters_list = new ArrayList<Recruiter_struct>();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.d("in on create","Recruiters");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recruiters_page);
		refresh_list();
	
	}
	
	void refresh_list(){
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
	
	void save_to_db(){
		
		Recruitersdb recruiters_db = new Recruitersdb(this, recruiters_list);
		recruiters_db.add_new_to_db();
		Cursor data_db = recruiters_db.fetch_all();
		
		startManagingCursor(data_db);
  
		String[] from = new String[] {Recruitersdb.grade,Recruitersdb.rec_name,Recruitersdb.date,Recruitersdb.eligibilty,
				Recruitersdb.branches_be,Recruitersdb.pkg_be, Recruitersdb.cutoff_be,
				Recruitersdb.branches_me,Recruitersdb.pkg_me, Recruitersdb.cutoff_me,
				Recruitersdb.branches_intern, Recruitersdb.pkg_intern, Recruitersdb.cutoff_intern};

		int[] to = new int[] {R.id.rec_name, R.id.date_rec, R.id.branches_be, R.id.branches_me, R.id.branches_intern};

		MyCustomRecruitCursor adapter = new MyCustomRecruitCursor(this, R.layout.row_recruiter,data_db,from,to);
		setListAdapter(adapter);
	}
	
	private class Retrieve_recruiters extends AsyncTask<Void,Void,Void>{


		@Override
		protected Void doInBackground(Void... params) {
			Log.d("in background","recru iters");
			recruiters_list = BackgroundProcess.Recruiters_data();
			// TODO Auto-generated method stub
			return null;
		}
		
		protected void onPostExecute(Void result)
		{
			Log.d("onpostexecute","announcement");
			super.onPostExecute(result);
			save_to_db();
		}
	}
}
