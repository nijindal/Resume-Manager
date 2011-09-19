package com.ResumeManager;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

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
		//String rand = new Integer((Announcements.size())).toString();
		String rand = Announcements.get(0);
		Log.d("in on create",rand);
		structure_data();
		show_data();
		fill_data();
	}
	
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
//			Log.d("has next","struct");
			//iter.next();
				temp = iter.next();
				Log.d(temp.company,temp.Announcement);
		}
		
	}
	
	public void fill_data(){

//		ListView lv = (ListView) findViewById(R.layout.mainpage);
		
		//Log.d("filldata",structured_Announcements.toString());
		setListAdapter(new MyCustomBaseAdapter(this, structured_Announcements));
		
		//MergeAdapter adapter = new MergeAdapter(); 
		//ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this,R.layout.rowtext,Announcements);
		//ArrayAdapter<String> arr_adapter2 = new ArrayAdapter<String>(this,R.layout.textheading,Announcements);

		//adapter.addAdapter(arr_adapter);
		
	}
}