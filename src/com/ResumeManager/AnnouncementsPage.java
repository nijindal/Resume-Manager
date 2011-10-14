package com.ResumeManager;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.SimpleCursorAdapter;

public class AnnouncementsPage extends ListActivity {
	
	ProgressDialog pleaseWait;
	static Anouncementsdb announce_db=null;
	public ArrayList<String> Announcements;
	public ArrayList<info> structured_Announcements = new ArrayList<info>();
	ArrayList<Announce> announce_list = null;
	public static int number_of_announcements=0;
	ProgressBar progress_bar = null;
	ImageView refresh_button = null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		announce_list = null;
		setContentView(R.layout.announcement);
		progress_bar = (ProgressBar)findViewById(R.id.progressbar_ann);
		refresh_button = (ImageView) findViewById(R.id.refresh_announce);
		Log.d("in on of create","Announcements");
		announce_db = new Anouncementsdb(this, null);
		fetchandshow();
		refresh_list(refresh_button);

	}
	
	public void refresh_list(View v){
	
		progress_bar.setVisibility(View.VISIBLE);
		(new Retrieve_Announcements()).execute();
		Log.d("announcementpage","refresh list");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.menuoptions_ann, menu);
	    Log.d("on createoptions","announcements");
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem Item)

	{
		super.onOptionsItemSelected(Item);
		Log.d("on item selected","announcements");
		switch(Item.getItemId())
		{
		case R.id.Refresh_menu_ann:
//			refresh_list();
			Log.d("menu button pressed","recruiters");
			
			return true;
			
		case R.id.Recruit_menu_ann:
			Log.d("menu button pressed","recruiters");
			Intent intent = new Intent();
			intent.setClassName("com.ResumeManager","com.ResumeManager.Recruiters");
			startActivity(intent);
			return true;
			
		case R.id.Logout_menu_ann:
			Log.d("menu button pressed","recruiters");
			Intent intent1 = new Intent();
			intent1.setClassName("com.ResumeManager","com.ResumeManager.ResumeManagerActivity");
			startActivity(intent1);
			Log.d("menu button pressed","recruiters");
			return true;
		default:
			Log.d("menu button pressed","recruiters");
			return super.onOptionsItemSelected(Item);
		}
	}
	
	public void fetchandshow(){
		
		Log.d("fetch and show","announcements");
		Cursor data_db = announce_db.fetch_all();
		Log.d("fetch and show","afterwards");
        startManagingCursor(data_db);
        number_of_announcements = data_db.getCount();
		Log.d(new Integer(data_db.getCount()).toString(), new Integer(data_db.getColumnCount()).toString());
		String[] from = new String[] {Anouncementsdb.com_name,Anouncementsdb.date,Anouncementsdb.time,Anouncementsdb.body,Anouncementsdb.user};
        int[] to = new int[] { R.id.com_name, R.id.date, R.id.time, R.id.body, R.id.user};
//the above two arrays are used for mapping.   .........
        Log.d("fetch and show","checking for visibility thing");
        progress_bar.setVisibility(View.INVISIBLE);
        SimpleCursorAdapter adapter = new MyCustomAnnounceCursorAdap(this, R.layout.row_announce,data_db,from,to);
        
        setListAdapter(adapter);
	}
	
	//Structure the data present in the string arraylist to the arraylist<info>, where info class provides the structure
	//for the data available to be shown in each structure...
	
	public void savetodb(){
		Log.d("announcementspage","savetodb");
		announce_db = new Anouncementsdb(this, announce_list);
		announce_db.add_new_to_db();
		fetchandshow();
	}
	
	private class Retrieve_Announcements extends AsyncTask<Void,Void,Void>
	{
		@Override
		protected Void doInBackground(Void... params) {
			Log.d("in background","Annnouncememnts retrieval"+number_of_announcements);
			announce_list = BackgroundProcess.Announcements_data(number_of_announcements);
			if(announce_list!=null)
			number_of_announcements+=announce_list.size();
			return null;
		}
		
		protected void onPostExecute(Void result)
		{
			Log.d("onpostexecute","announcement");
			super.onPostExecute(result);
			if(announce_list != null)
			{
				Log.d("Announcements Page...","saving to db");
				savetodb();
			}
			else
			{
				Log.d("Announcemnts pAge.","it came out to be null");
				progress_bar.setVisibility(1);
			}
		}
	}
}
