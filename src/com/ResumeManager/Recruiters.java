package com.ResumeManager;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class Recruiters extends ListActivity {

	public ArrayList<String> recruiters;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.mainpage);
	recruiters = getIntent().getStringArrayListExtra("recruiters");
	fill_data();
	
	}
	
	public void fill_data()
	{
		String[] recruits = (String[])recruiters.toArray();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.id.recruit_name,recruits);
		setListAdapter(adapter);	
	}
}
