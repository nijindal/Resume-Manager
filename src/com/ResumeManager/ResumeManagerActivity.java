package com.ResumeManager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ResumeManagerActivity extends Activity {
	/** Called when the activity is first created. */

	private String username;
	private String password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("in oncreate", "process started");
		setContentView(R.layout.indexpage);
		Log.d("after contenview", "process started");
	}

	public void authenticate(View login_button) {

		Log.d("check", "in the autheticate");
		username = ((EditText) findViewById(R.id.txt_username)).getText()
				.toString();
		password = ((EditText) findViewById(R.id.txt_password)).getText()
				.toString();
		PerformAsync authentic_var = new PerformAsync();
		authentic_var.execute();
	}

	private class PerformAsync extends AsyncTask<Void, Void, Void> {

		ArrayList<String> output;
		@Override
		protected Void doInBackground(Void... params) {
			System.out.println("in the doInBckground");
			output=BackgroundProcess.makeConnection(username, password);
			//Response = BackgroundProcess.result;
			// TODO Auto-generated method stub////
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
//			output = BackgroundProcess.data_received;
			System.out.println( "in perform sync" + output);
			System.out.println("post execute");
			Intent Announcement = new Intent();
			Announcement.setClassName("com.ResumeManager","com.ResumeManager.AnnouncementsPage");
			Announcement.putStringArrayListExtra("announcements", output);
			startActivity(Announcement);
			
		}

	}
}