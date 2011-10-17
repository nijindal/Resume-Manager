package com.ResumeManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class ResumeManagerActivity extends Activity {

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

	
	public void BuildAlertDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(ResumeManagerActivity.this);
		builder.setMessage("Login Failed try again");
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
	private class PerformAsync extends AsyncTask<Void, Void, Void> {

		int unique_id;
		@Override
		protected Void doInBackground(Void... params) {
			System.out.println("in the Async  doInBckground");
			unique_id = BackgroundProcess.makeConnection(username, password);
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(unique_id>0)		//autheticated
			{
				System.out.println( "in post execute" + unique_id);
				SharedPreferences myPrefs = ResumeManagerActivity.this.getSharedPreferences("unique_user_id", MODE_WORLD_READABLE);				
				SharedPreferences.Editor prefsEditor = myPrefs.edit();
				prefsEditor.putInt("user_id", unique_id);
				prefsEditor.commit();
//we have put the value of unique iser id so obtained in shared pref ...will be used in case of Recruiter_details
				
				Intent Announcement = new Intent();
				Announcement.setClassName("com.ResumeManager","com.ResumeManager.Announcements_Page");
				pleasewait.dismiss();
				startActivity(Announcement);
			}
			else 
				BuildAlertDialog();
				
	}
}
}