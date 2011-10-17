package com.ResumeManager;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Recruiters_db extends SQLiteOpenHelper {

	public SQLiteDatabase db;
	final static String db_name="database";
	final static String recruiters_table="recruiters_table";
	final static String identity="identity";
	final static String rec_name="rec_name";
	
	final static String unique_id="_id";
	final static String grade="grade";
	final static String date="date";
	final static String branches_be="branches_be";

	final static String branches_me="branches_me";
	final static String branches_intern="branches_intern";
	final static String pkg_be="pkg_be";
	final static String pkg_me="pkg_me";
	final static String pkg_intern="pkg_intern";
	final static String cutoff_be="cutoff_be";
	final static String cutoff_me="cutoff_me";
	final static String cutoff_intern="cutoff_intern";
	
	final static int DATABASE_VERSION = 2;
	private ArrayList<Recruiter_struct> recruiters;
	private Context ctx;
	
	public static String query = "SELECT _id, identity, rec_name, grade, date, branches_be," +
			" branches_me, branches_intern, pkg_be, pkg_me, pkg_intern, cutoff_be, cutoff_me," +
			" cutoff_intern FROM recruiters_table ORDER BY identity DESC";
	
	
	
	public Recruiters_db(Context context, ArrayList<Recruiter_struct> new_Recruiters){
		super(context, db_name, null, DATABASE_VERSION);
//		db = Anouncementsdb.db;
		recruiters = new_Recruiters; 
		db = this.getWritableDatabase();	
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("in on create","recruiters  db");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("in on upgrade","recr uiters");
		db.execSQL("DROP TABLE IF EXISTS recruiters_table;");
        onCreate(db);			
	}
	
public void update_last_rec(int last_id)
{
	if(Recruiters_Page.last_recruiter<last_id)
		Recruiters_Page.last_recruiter = last_id;
}
	
public void add_new_to_db(){
	
		Log.d("Recruitersdb ","addtodb");
		db = this.getWritableDatabase();
		Iterator<Recruiter_struct> Iter = recruiters.iterator();
		Recruiter_struct temp = new Recruiter_struct();
		long status;
		while(Iter.hasNext()){
			temp = Iter.next();
			ContentValues content = new ContentValues();
			Log.d("add new to db","recruitersdb");
			
			if((temp.branches_intern)==null)
				Log.d("the value of temp.branches_me is" + temp.branches_me,"NULL Value has been received");
			
			content.put(identity, temp.id);
			update_last_rec(temp.id);
			content.put(rec_name,temp.rec_name);
			content.put(date,temp.date);
			content.put(grade,temp.grade);
			content.put(branches_be,temp.branches_be);
			content.put(branches_me,temp.branches_me);
			content.put(branches_intern,temp.branches_intern);
			content.put(pkg_be,temp.pkg_be);
			content.put(pkg_me,temp.pkg_me);
			content.put(pkg_intern,temp.pkg_intern);
			content.put(cutoff_be, temp.cutoff_be);
			content.put(cutoff_me, temp.cutoff_me);
			content.put(cutoff_intern,temp.cutoff_intern);
			
			status = db.insert(recruiters_table,null,content);
			Log.w((new Long(status)).toString(),"add new to db");
			Log.d("later on stage","the iteraation loop");		
		}
	
	}

public Cursor fetch_all(){
	Log.d("fetch all","recruitersdb");
	Cursor cursor = db.rawQuery(query, null);
//we must have an column called _id in the table if you want to use cursor adapter on the given database and that
//_id should be unique i.e., primary key ...and auto increment 		
//  Log.d(cursor.getString(cursor.getColumnIndex("company")),cursor.getString(cursor.getColumnIndex("date")));
    if(cursor!=null)
    {
    	Log.d("fetch all","not null");
    	cursor.moveToFirst();
    	return cursor;
    }
	return null;
  }

	
}
