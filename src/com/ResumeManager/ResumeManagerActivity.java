package com.ResumeManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class ResumeManagerActivity extends Activity {
	/** Called when the activity is first created. */

	private ProgressDialog pleasewait;
	private String username = null;
	private String password = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indexpage);
	
		Log.d("in oncreate", "process started");
		
		Log.d("after contenview", "process started");
	}

	public void authenticate(View login_button) {

		Log.d("check", "in the autheticate");
		username = ((EditText) findViewById(R.id.txt_username)).getText()
				.toString();
		password = ((EditText) findViewById(R.id.txt_password)).getText()
				.toString();
		pleasewait = ProgressDialog.show(ResumeManagerActivity.this, "", "Logging in, please wait..", true);
		PerformAsync authentic_var = new PerformAsync();
		authentic_var.execute();
	}

	private class PerformAsync extends AsyncTask<Void, Void, Void> {

		String response;
		@Override
		protected Void doInBackground(Void... params) {
			System.out.println("in the Async  doInBckground");
			response = BackgroundProcess.makeConnection(username, password);
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(response.equals("SUCCESS"))		//autheticated
				System.out.println( "in post execute" + response);
			Intent Announcement = new Intent();
			Announcement.setClassName("com.ResumeManager","com.ResumeManager.AnnouncementsPage");
//			Announcement.putStringArrayListExtra("announcements", output);
			pleasewait.dismiss();
			startActivity(Announcement);
			
		}

	}
}