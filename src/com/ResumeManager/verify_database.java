package com.ResumeManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class verify_database extends AsyncTask<Void,Void,Void>
{
	public Anouncementsdb  announce_db = null;
	public ArrayList<Integer> rcvd_num = null;
	public ArrayList<Integer> local_num = new ArrayList<Integer>();

	
	public verify_database() {
				announce_db = AnnouncementsPage.announce_db;
				rcvd_num =  new ArrayList<Integer>();
				local_num = new ArrayList<Integer>();
			}

		@Override
		protected Void doInBackground(Void... params) {
			Log.d("verify db class","in   background");
			Cursor local_data = announce_db.fetch_all();
			local_data.moveToFirst();
			System.out.println("the vlaue of local numbers is" + local_data.getInt(local_data.getColumnIndex(Anouncementsdb.identity)));
			
			local_data.moveToFirst();
			while(!local_data.isAfterLast()) {
			    local_num.add(local_data.getInt(local_data.getColumnIndex(Anouncementsdb.identity)));
			    local_data.moveToNext();
			}

			System.out.println("the vlaue of local numbers is" + local_num);
			
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost PostRequest = new HttpPost("http://10.42.43.1/Android/verify_db_ann.php");
				Log.d("URL is: ","http://10.42.43.1/Android/verify_db_ann.php" );
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
			
		}
}
	
	