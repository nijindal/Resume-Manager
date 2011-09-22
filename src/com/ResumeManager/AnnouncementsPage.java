package com.ResumeManager;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;


//Announcement Page it is........
public class AnnouncementsPage extends ListActivity {
	
	public ArrayList<String> Announcements;
	public ArrayList<info> structured_Announcements = new ArrayList<info>();
	ArrayList<Announce> recruiters_list = new ArrayList<Announce>();
	public static int number_of_announcements;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		Log.d("in on of create","Announcements");
//		Announcements = getIntent().getStringArrayListExtra("announcements");
		refresh_list();
//		structure_data();					//structure the data obtained in arraylist<String>.....								
//		show_data();						//Just for dubugging purpose......
//		fill_data();						//Fill Data in the rows of the Android ListView.....
	}
	
	public void refresh_list(){
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
			///Announcements....
			Log.d("menu button pressed","recruiters");
			
			return true;
			
		case R.id.Recruit_menu_ann:
			Log.d("menu button pressed","recruiters");
//			Retrieve_recruiters handle = new Retrieve_recruiters();
//			handle.execute();
			//Recruiters
			return false;
			
		case R.id.Logout_menu_ann:
			//Logout
			Log.d("menu button pressed","recruiters");
			return true;
		default:
			Log.d("menu button pressed","re cruiters");
			return super.onOptionsItemSelected(Item);
		}
	}
	
	//Structure the data present in the string arraylist to the arraylist<info>, where info class provides the structure
	//for the data available to be shown in each structure...
	
	public void savetodb(){
		Log.d("announcementspage","savetodb");
		Anouncementsdb announce_db = new Anouncementsdb(this, recruiters_list);
		announce_db.add_new_to_db();
		Cursor data_db = announce_db.fetch_all();	
        startManagingCursor(data_db);
        number_of_announcements = data_db.getCount();
		Log.d(new Integer(data_db.getCount()).toString(), new Integer(data_db.getColumnCount()).toString());
   
		String[] from = new String[] {Anouncementsdb.com_name,Anouncementsdb.date,Anouncementsdb.time,Anouncementsdb.body,Anouncementsdb.user};
        int[] to = new int[] { R.id.com_name, R.id.date, R.id.time, R.id.body, R.id.user};
        
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_announce,data_db,from,to);
        
        setListAdapter(adapter);

	}
	
	private class Retrieve_Announcements extends AsyncTask<Void,Void,Void>
	{
		@Override
		protected Void doInBackground(Void... params) {
			Log.d("in background","Annnouncememnts retrieval");
			recruiters_list = BackgroundProcess.Announcements_data(number_of_announcements);
			// TODO Auto-generated method stub
			return null;
		}
		
		protected void onPostExecute(Void result)
		{
			Log.d("onpostexecute","announcement");
			super.onPostExecute(result);
			savetodb();
		}
	}
}
