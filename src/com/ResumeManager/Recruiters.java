package com.ResumeManager;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class Recruiters extends ListActivity {

	public ArrayList<String> recruiters = new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	Log.d("in on create","without any data");
	super.onCreate(savedInstanceState);
	setContentView(R.layout.mainpage);
	recruiters = getIntent().getStringArrayListExtra("recruiters");
	fill_data();
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
	
	public void fill_data()
	{
		Log.d("fill data","recruiters");
		String[] recruits = new String[recruiters.size()];
		recruits = (String[])recruiters.toArray(recruits);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.row_recruiter,recruits);
		setListAdapter(adapter);	
	}
	
	private class Retrieve_recruiters extends AsyncTask<Void,Void,Void>{

		ArrayList<String> recruiters_list = new ArrayList<String>();
		@Override
		protected Void doInBackground(Void... params) {
			Log.d("in background","going to recruiters");
			recruiters_list = BackgroundProcess.Recruiters_data();
			// TODO Auto-generated method stub
			return null;
		}
		
		protected void onPostExecute(Void result)
		{
			Log.d("onpostexecute","announcement");
			super.onPostExecute(result);
			Intent to_recruiters = new Intent();
			
			to_recruiters.setClassName("com.ResumeManager","com.ResumeManager.Recruiters");
			to_recruiters.putStringArrayListExtra("recruiters", recruiters_list);
			startActivity(to_recruiters);

			
		}
	}
}
