package com.ResumeManager;

import android.os.Parcel;
import android.os.Parcelable;

//simple class that just has one member property as an example
public class MyParcelable implements Parcelable {

	public String com_id;
	public String grade;
	public String com_name;
	public String branches_be;
	public String branches_me;
	public String branches_intern;
	public String visit_date;
	public String cutoff;
	public String ctc;
    /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
    	String[] array = new String[]{this.com_id, this.grade, this.com_name, this.branches_be, this.branches_me, this.branches_intern,
    						this.visit_date, this.cutoff, this.ctc};
    	out.writeStringArray(array);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<MyParcelable> CREATOR = new Parcelable.Creator<MyParcelable>() {
        public MyParcelable createFromParcel(Parcel in) {
            return new MyParcelable(in);
        }

        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };

    public MyParcelable(String com_id, String grade, String com_name, String branches_be, String branches_me, String branches_intern,
    		String visit_date, String cutoff, String ctc){
    	
    		this.com_id = com_id;
    		this.grade = grade; 
    		this.com_name = com_name;
    		this.branches_be = branches_be;
    		this.branches_me = branches_me;
    		this.branches_intern = branches_intern;
    		this.visit_date = visit_date;
    		this.cutoff = cutoff;
    		this.ctc = ctc;
    		
    }
    // example constructor that takes a Parcel and gives you an object populated with it's values
    public MyParcelable(Parcel in) {
    	
    	String[] data = new String[9];
    	in.readStringArray(data);
    	this.com_id = data[0];
    	this.grade = data[1];
    	this.com_name = data[2];
    	this.branches_be = data[3];
    	this.branches_me = data[4];
    	this.branches_intern = data[5];
    	this.visit_date = data[6];
    	this.cutoff = data[7];
    	this.ctc = data[8];
    }
}
