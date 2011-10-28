package com.ResumeManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

public class Announcements_Page extends ListActivity {
	
	ProgressDialog pleaseWait;
	static Anouncements_db announce_db=null;
	public ArrayList<String> Announcements;
	private verify_database verify_var = null;
	ArrayList<Announcement_struct> announce_list = null;
	public static int last_annnouncement;
	ProgressBar progress_bar = null;
	ImageView refresh_button = null;
	static ListView listview=null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		announce_list = null;
		setContentView(R.layout.announcement);

		progress_bar = (ProgressBar)findViewById(R.id.progressbar_ann);
		refresh_button = (ImageView) findViewById(R.id.refresh_announce);
		Log.d("in on of create","Announcements");
		announce_db = new Anouncements_db(this, null);
		fetchandshow();
		refresh_list(refresh_button);

	}
	
	public void refresh_list(View v){
	
//			verify_var.cancel(true);
			progress_bar.setVisibility(View.VISIBLE);
			(new Retrieve_Announcements()).execute();
			Log.d("announcementpage"," refresh list");

//Need to check whether the old announcements are still there in the database or not. Make a separate Activity that 
// will be called after refreshing and it will take all the announcements identity in the database and look for their
// availability in the server database....and delete some of the if reqd in my local db. 
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
			Log.d("menu button pressed","recruiters");
			return true;
			
		case R.id.Recruit_menu_ann:
			Log.d("menu button pressed","recruiters");
			Intent intent = new Intent();
			intent.setClassName("com.ResumeManager","com.ResumeManager.Recruiters_Page");
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
		Log.d(new Integer(data_db.getCount()).toString(), new Integer(data_db.getColumnCount()).toString());

		String[] from = new String[] {Anouncements_db.com_name,Anouncements_db.date,Anouncements_db.time,Anouncements_db.body,Anouncements_db.user};
		int[] to = new int[] { R.id.com_name, R.id.date, R.id.time, R.id.body, R.id.user};
		//the above two arrays are used for mapping.   .........
		
		if(data_db.getCount()>0){
			Log.d("YEAH the count is grater than 0","Fetch and show");
			data_db.moveToFirst();
			last_annnouncement = data_db.getInt(data_db.getColumnIndex("identity"));
		}
		
//		progress_bar.setVisibility(View.INVISIBLE);
		data_db.moveToFirst();
        Log.d("ID OF LAST ANNOUNCEMENT IS : ", new Integer(last_annnouncement).toString());
        SimpleCursorAdapter adapter = new MyCustomAnnounceCursorAdap(this, R.layout.row_announce,data_db,from,to);
        
        setListAdapter(adapter);
        
		progress_bar.setVisibility(View.INVISIBLE);        
//will be zero initially        
//        if(data_db.getCount() != 0)
//        {
//        	verify_var = new verify_database();
//        	verify_var.execute();
//		}
	}
	
	//Structure the data present in the string arraylist to the arraylist<info>, where info class provides the structure
	//for the data available to be shown in each structure...
	
	public void savetodb(){
		Log.d("announcementspage","savetodb");
		announce_db = new Anouncements_db(this, announce_list);
		announce_db.add_new_to_db();
//		verify_var = new verify_database();
//    	verify_var.execute();
		fetchandshow();
	}
	
		
	private class Retrieve_Announcements extends AsyncTask<Void,Void,Void>
	{
		@Override
		protected Void doInBackground(Void... params) {
			announce_list = BackgroundProcess.Announcements_data(last_annnouncement);
//			if(announce_list!=null)
//			number_of_announcements+=announce_list.size();
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
				progress_bar.setVisibility(View.VISIBLE);
			}
		}
	}
	
	

	private class verify_database extends AsyncTask<Void,Void,Void>
	{
		public Anouncements_db  announce_db = null;
		public ArrayList<Integer> rcvd_num = null;
		public ArrayList<Integer> local_num = new ArrayList<Integer>();

		
		public verify_database() {
					announce_db = Announcements_Page.announce_db;
					rcvd_num =  new ArrayList<Integer>();
					local_num = new ArrayList<Integer>();
				}

			@Override
			protected Void doInBackground(Void... params) {
				Log.d("verify db class","in   background");
				Cursor local_data = announce_db.fetch_all();
				local_data.moveToFirst();
				System.out.println("the vlaue of local numbers is" + local_data.getInt(local_data.getColumnIndex(Anouncements_db.identity)));
				
				local_data.moveToFirst();
				while(!local_data.isAfterLast()) {
				    local_num.add(local_data.getInt(local_data.getColumnIndex(Anouncements_db.identity)));
				    local_data.moveToNext();
				}

				System.out.println("the vlaue of local numbers is" + local_num);
				
				try {
					HttpClient client = new DefaultHttpClient();
					HttpPost PostRequest = new HttpPost(BackgroundProcess.domain + "verify_db_ann.php");
//					Log.d("URL is: ","http://10.42.43.1/Android/verify_db_ann.php" );
					HttpResponse ResponsePost = client.execute(PostRequest);
					int Response_code = ResponsePost.getStatusLine().getStatusCode();
					Log.d("after  the authetication",new Integer(Response_code).toString());
					System.out.println("After the request" + Response_code);

					if (Response_code == HttpStatus.SC_OK) 
					{
						Log.d("connection done", "in Background process");
						if (ResponsePost.getEntity() != null) 
						{
							Log.d("connection done", "some data rceived");
							InputStream in = ResponsePost.getEntity().getContent();
							BufferedReader reader = new BufferedReader(new InputStreamReader(in));
							StringBuilder result = new StringBuilder();
							String line = null;
							while ((line = reader.readLine()) != null) {
									rcvd_num.add(new Integer(line));
							}
							in.close();
						}
						System.out.println("the vlaue of remote numbers is" + rcvd_num);
					}
				}catch(Exception ex){}
				return null;
			}	
			
			protected void onPostExecute(Void result)
			{
				//compare both of these and make them same.....
				Log.d("onpostexecute","announcement");
				super.onPostExecute(result);
				int local=0;
				int remote=0;
				Iterator<Integer> Iter_local = local_num.iterator();
				Iterator<Integer> Iter_remote = rcvd_num.iterator();
				if(Iter_local.hasNext() && Iter_remote.hasNext())
				{
					local = (int) Iter_local.next();
					remote = (int) Iter_remote.next();
				}

//				if(local == remote){
					while(Iter_local.hasNext()) {
						if(local == remote)
						{
							Log.d("Verify DB","equal");
							local = (int) Iter_local.next();
							remote = (int) Iter_remote.next();			
						}
					else if(local>remote)
						{
							Log.d("VERIFY DB","local is greater");
						Announcements_Page.announce_db.delete_row_by_id(local);
							//delete the row from databse with this ID, becaus eit doesnt exist anymore.....
						local = (int) Iter_local.next();
//						AnnouncementsPage.fetchandshow();
						
						}
					else
					{
						Log.d("VERIFY DB","remote is greater");
						remote = (int) Iter_remote.next();
					}
				}
					
					fetchandshow();
//			}
		}
	}
}
