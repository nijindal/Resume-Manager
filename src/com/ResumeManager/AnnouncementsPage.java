package com.ResumeManager;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


//Announcement Page it is........
public class AnnouncementsPage extends ListActivity {
	
	public ArrayList<String> Announcements;
	public ArrayList<info> structured_Announcements = new ArrayList<info>();
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		Log.d("in on of create","Announcements");
		Announcements = getIntent().getStringArrayListExtra("announcements");
		structure_data();					//structure the data obtained in arraylist<String>.....								
		show_data();						//Just for dubugging purpose......
		fill_data();						//Fill Data in the rows of the Android ListView.....
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.menuoptions, menu);
	    return true;
	}
	
	
	public boolean OnOptionItemSelected(MenuItem Item)
	{
		switch(Item.getItemId())
		{
		case R.id.Announce_menu:
			///Announcements....
			return true;
			
		case R.id.Recruit_menu:
			Retrieve_recruiters handle = new Retrieve_recruiters();
			handle.execute();
			//Recruiters
			return true;
			
		case R.id.Logout_menu:
			//Logout
			return true;
		default:
			return super.onOptionsItemSelected(Item);
		}
	}
	
	//Structure the data present in the string arraylist to the arraylist<info>, where info class provides the structure
	//for the data available to be shown in each structure
	public void structure_data(){		
		Iterator<String> iter = Announcements.iterator();
		iter.next();
		while(iter.hasNext())
		{
			//Log.d("has next","strutct");
			//iter.next();
			info temp = new info();
			temp.company = iter.next();
				
				if(iter.hasNext())
				{
					temp.Announcement = iter.next();
					Log.d(temp.company,temp.Announcement);
					structured_Announcements.add(temp);
					
				}
		}
	}
	
	public void show_data(){
		Iterator<info> iter = structured_Announcements.iterator();
		info temp = new info();
		Log.d("show data","showing");
		while(iter.hasNext())
		{
				temp = iter.next();
				Log.d(temp.company,temp.Announcement);
		}
		
	}
	
	private void fill_data(){

//		ListView lv = (ListView) findViewById(R.layout.mainpage);
		
		//Log.d("filldata",structured_Announcements.toString());
		setListAdapter(new MyCustomBaseAdapter(this, structured_Announcements));

	}

private class Retrieve_recruiters extends AsyncTask<Void,Void,Void>{

	ArrayList<String> recruiters_list;
	@Override
	protected Void doInBackground(Void... params) {
		
		recruiters_list = BackgroundProcess.Recruiters_data();
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void onPostExecute(Void result)
	{
		super.onPostExecute(result);
		Intent to_recruiters = new Intent();
		to_recruiters.setClassName("com.ResumeManager","com.ResumeManager.Recruiters");
		to_recruiters.putStringArrayListExtra("recruiters", recruiters_list);
		startActivity(to_recruiters);
		
	}
}
}
