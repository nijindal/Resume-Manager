package com.ResumeManager;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Anouncements_db extends SQLiteOpenHelper{

	final static String com_name="company";
	final static String db_name="database";
	final static String identity="identity";
	final static String announcement_table="announcement_table";
	final static String date="date";
	final static String time="time";
	final static String body="body";
	final static String user="user";
	final static String unique_id="_id";
	final static int DATABASE_VERSION = 2;
	private ArrayList<Announcement_struct> announcements;
	private Context ctx;
	public static SQLiteDatabase db;
	
	public static String query_fetch = "SELECT _id, identity, company, date, time, body, user FROM announcement_table order by identity DESC";
	
	public static String fetch_id = "SELECT _id, identity, company, date, time, body, user FROM announcement_table WHERE identity=10"; 
	public static String query_delete = "DELETE FROM announcement_table WHERE identity=";
	public String CREATE_TABLE_ANNOUNCE = "CREATE TABLE "+ announcement_table + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + identity + " INTEGER, " +com_name + " TEXT NOT NULL," +
	date + " TEXT NOT NULL," + time + " TEXT NOT NULL," + body + " TEXT NOT NULL," + user +" TEXT NOT NULL);";
	
	public static String CREATE_TABLE_RECRUIT = "CREATE TABLE "+ Recruiters_db.recruiters_table + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
	
	+ Recruiters_db.identity + " INTEGER, " 
	+ Recruiters_db.grade + " TEXT NOT NULL, "
	+ Recruiters_db.rec_name + " TEXT NOT NULL," 
	+ Recruiters_db.date + " TEXT NOT NULL," 
	+ Recruiters_db.branches_be +" TEXT, " 
	+ Recruiters_db.branches_me +" TEXT, "
	+ Recruiters_db.branches_intern +" TEXT, "
	+ Recruiters_db.pkg_be +" TEXT, "
	+ Recruiters_db.pkg_me +" TEXT, "
	+ Recruiters_db.pkg_intern +" TEXT, "
	+ Recruiters_db.cutoff_be + " TEXT, " 
	+ Recruiters_db.cutoff_me +" TEXT, "
	+ Recruiters_db.cutoff_intern + " TEXT);";
	

public Anouncements_db(Context context, ArrayList<Announcement_struct> new_announcements){
		super(context, db_name, null, DATABASE_VERSION);
		ctx = context;
		Log.d("Anouncementsdb","consructor");
		announcements = new_announcements;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("Anouncementsdb","in oncreate");
		db.execSQL(CREATE_TABLE_ANNOUNCE);
		db.execSQL(CREATE_TABLE_RECRUIT);

		
//we need to declare all the tables to be used in a database together...because later in onCreate is never
//		called for same database...... 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS announce_table;");
        onCreate(db);		
	}
	
	
	public void update_last_id(int rcvd_id){
		if(Announcements_Page.last_annnouncement<rcvd_id){
			Announcements_Page.last_annnouncement = rcvd_id;
			Log.d("updated the value of last announcement", new Integer(rcvd_id).toString());
			}
	}

	public void add_new_to_db(){
		
		Log.d("Anouncementsdb ","addtodb");
		
		db = this.getWritableDatabase();
		Iterator<Announcement_struct> Iter = announcements.iterator();
		Announcement_struct temp = new Announcement_struct();
		long status;
		while(Iter.hasNext()){
			temp = Iter.next();
			ContentValues content = new ContentValues();
			content.put(identity, temp.id);
			update_last_id(temp.id);
			Log.d("add new to db","the iteraation loop");
			content.put(com_name,temp.com_name);
			content.put(date,temp.date);
			content.put(time,temp.time);
			content.put(body,temp.body);
			content.put(user,temp.user);
			status = db.insert(announcement_table,null,content);
			Log.w((new Long(status)).toString(),"add new to db");
			Log.d("later on stage","the iteraation loop");		
		}
	
	}
	
	public Cursor fetch_all(){
		db = this.getWritableDatabase();
		Log.d("fetch all","announcementdb");
		Cursor cursor=null;
		if(db!=null)
		cursor = db.rawQuery(query_fetch, null);
		
//we must have an column called _id in the table if you want to use cursor adapter on the given database and that
//_id should be unique i.e., primary key ...and auto increment 		
//        Log.d(cursor.getString(cursor.getColumnIndex("company")),cursor.getString(cursor.getColumnIndex("date")));
        if(cursor!=null)
        {
        	Log.d("fetch all","not null");
        	cursor.moveToFirst();
        	return cursor;
        }
        else
        	Log.d("fetch all","NULLL");
		return null;
	}
	
	public void delete_row_by_id(int local) {

		Log.d("Delete row by iD","In announcements DB");
		db = this.getWritableDatabase();
		Cursor c = db.rawQuery(query_delete+local, null);
//		Cursor c = db.rawQuery(fetch_id, null);
		c.moveToFirst();
		Log.d("The count is:  " , new Integer(c.getCount()).toString());
	}
}