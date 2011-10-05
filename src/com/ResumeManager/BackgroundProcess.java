package com.ResumeManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.google.gson.Gson;

public class BackgroundProcess {
	
	static String result[];
	static int Number_of_stmt = 0;

	public static String makeConnection(String username, String password) {
		// Piggyback the connection and socket timeout parameters onto the
		// HttpPost request
		// try{
		// HttpParams httpParameters = new BasicHttpParams();
		// int timeoutConnection = 10000;
		// HttpConnectionParams.setConnectionTimeout(httpParameters,
		// timeoutConnection);
		// int timeoutSocket = 25000;
		// HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		String URL = "http://10.42.43.1/Android/authetication_page.php";
		//ArrayList<String> data_received = new ArrayList<String>();
		//data_received.clear();
		
		try {
			System.out.println("reached the background class");
			HttpClient client = new DefaultHttpClient();
			HttpPost PostRequest = new HttpPost(URL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));

			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
			PostRequest.setEntity(ent);

			HttpResponse ResponsePost = client.execute(PostRequest);// connection
																	// established
																	// actuallly....
			int Response_code = ResponsePost.getStatusLine().getStatusCode();

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
							System.out.print("in read line" + line);
							result.append(line);
							}
					in.close();
					String Auth_response = new String(); 
					Auth_response = result.toString();
					return Auth_response;
				}
				return null;
			 } 
		  else
			{
				result[0] = "server_error";
				return null;
			}
		}catch (Exception e) {
			result[0] = "Error";
			e.printStackTrace();
		}
		return null;
		
}
	
	
public static ArrayList<Announce> Announcements_data(int number){
		
		String URL = "http://10.42.43.1/Android/announcements_page.php";
		ArrayList<String> Announcements_list = new ArrayList<String>();
		Announce_struct list;
		ArrayList<Announce> Announcements_struct = new ArrayList<Announce>();
		Announcements_list.clear();
		
		
		try {
			System.out.println("reached the background class");
			HttpClient client = new DefaultHttpClient();
			HttpPost PostRequest = new HttpPost(URL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
	
			params.add(new BasicNameValuePair("number",new Integer(number).toString()));
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
			PostRequest.setEntity(ent);
			HttpResponse ResponsePost = client.execute(PostRequest);// connection established actuallly....
			
			int Response_code = ResponsePost.getStatusLine().getStatusCode();
			System.out.println("In Announcememts Retrieval process" + Response_code);
			
			
			if (Response_code == HttpStatus.SC_OK) 
			{
				if (ResponsePost.getEntity() != null) 
				{
					Log.d("connection done", "some data received");
					StringBuilder str_rcvd = new StringBuilder();
					InputStream in = ResponsePost.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));		
					String line = null;
					
					while ((line = reader.readLine()) != null) {
							Announcements_list.add(line);
					}
					System.out.println("in announcements" + Announcements_list + "size" +Announcements_list.size());
					Announcements_struct= structure_announcements(Announcements_list);
					Iterator<Announce> iter = Announcements_struct.iterator(); 
					System.out.println("in announcements structs" + iter.next().user );
					return Announcements_struct;
//					return null;
				}
				else
					return null;
			}
			else
				return null;
//			
		}catch(Exception ex){
			ex.printStackTrace();
			}
		return null;
}

private static ArrayList<Announce> structure_announcements(ArrayList<String> Announcements){

	
	
	ArrayList<Announce> structured_Announcements= new ArrayList<Announce>();
	Iterator<String> iter = Announcements.iterator();
//	iter.next();						//first one is always empty....skipping that.....
	
	while(iter.hasNext())
	{
		Announce temp = new Announce();			//we need fresh memory allocation for all the elements of the arraylist<Announce>..
		temp.id = iter.next();
		temp.com_name = iter.next();
		temp.date = iter.next();
		temp.time = iter.next();
		temp.body = iter.next();
		temp.user = iter.next();
		structured_Announcements.add(temp);
	}
	return structured_Announcements;
}

	
public static ArrayList<Recruiter_struct> Recruiters_data(){
		
		String URL;
		URL = "http://10.42.43.1/Android/recruiters_list.php";
		ArrayList<String> recruiters_list = new ArrayList<String>();
		ArrayList<Recruiter_struct> recruit_struct;
		recruiters_list.clear();
		
		try {
			System.out.println("reached the background class of recruiters");
			HttpClient client = new DefaultHttpClient();
			HttpPost PostRequest = new HttpPost(URL);
			HttpResponse ResponsePost = client.execute(PostRequest);// connection established actuallly....
			int Response_code = ResponsePost.getStatusLine().getStatusCode();
			System.out.println("In recruiters Data" + Response_code);

			if (Response_code == HttpStatus.SC_OK) 
			{
				if (ResponsePost.getEntity() != null) 
				{
					Log.d("connection done", "some data rceived");
					InputStream in = ResponsePost.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));		
					String line = null;
					
					while ((line = reader.readLine()) != null) {
							recruiters_list.add(line);
					}

					System.out.println("in recruiters" + recruiters_list + "size" + recruiters_list.size());

					recruit_struct = structure_recruiters(recruiters_list);
					 
					return recruit_struct;
				}
				else
					return null;
			}
			else
				return null;
			
		}catch(Exception ex){
			ex.printStackTrace();
			}
		return null;
		}

static public ArrayList<Recruiter_struct> structure_recruiters(ArrayList<String> recruiters){
	
	ArrayList<Recruiter_struct> recruit_struct = new ArrayList<Recruiter_struct>();
	recruit_struct.clear();
	Iterator<String> Iter = recruiters.iterator();

	while(Iter.hasNext())
	{
		Recruiter_struct temp = new Recruiter_struct();
		temp.id = Iter.next();
		temp.grade = Iter.next();
		temp.rec_name = Iter.next();
		temp.date = Iter.next();
		temp.eligibilty = Iter.next();
		assign_details(temp, Iter);
		System.out.print("in strucutring" + temp);
		recruit_struct.add(temp);
	}
	return recruit_struct;
}

static public void assign_details(Recruiter_struct temp, Iterator<String> Iter ){
	Log.d("in assign", "details to");
	String[] paritions = temp.eligibilty.split(" ");
	int size = paritions.length;
	
	int i;
	for(i=0;i<size;i++)
	{
	if(paritions[i].equals("BE"))
		{
		Log.d("BE","assign");
		temp.branches_be = Iter.next();
		temp.pkg_be = Iter.next();
		temp.cutoff_be = Iter.next();
		}
	else if(paritions[i].equals("ME"))
		{
		Log.d("ME","assign");
		temp.branches_me = Iter.next();
		temp.pkg_me = Iter.next();
		temp.cutoff_me = Iter.next();
		System.out.print("Assignment is correct or not" + temp.branches_me);
		}
		
	else if(paritions[i].equals("INTERN"))
		{
		Log.d("INTERN","assign");
		temp.branches_intern = Iter.next();
		temp.pkg_intern = Iter.next();
		temp.cutoff_intern = Iter.next();
	}
	}

  }

static public Rec_details get_recruiter_details(int company_id){
	
	String URL = "http://10.42.43.1/Android/recruiter_detail.php";
	Rec_details array;
	try {
		System.out.println("reached the recruiter details background class");
		HttpClient client = new DefaultHttpClient();
		HttpPost PostRequest = new HttpPost(URL);
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("company_id", new Integer(company_id).toString()));

		UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
		PostRequest.setEntity(ent);

		HttpResponse ResponsePost = client.execute(PostRequest);// connection
																// established
																// actuallly....
		int Response_code = ResponsePost.getStatusLine().getStatusCode();

		System.out.println("After the request" + Response_code);

		if (Response_code == HttpStatus.SC_OK) 
		{
			Log.d("connection done", "in Background process");
			if (ResponsePost.getEntity() != null) 
			{
				Log.d("connection done", "JSON  IS RECIEVED");
				InputStream in = ResponsePost.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String string_rcvd = reader.readLine();
				Gson gson = new Gson();
				array = gson.fromJson(string_rcvd, Rec_details.class);
				if(array.com_name == "")
					Log.d("the company name is nulll revd","checked it just now");
				else
					Log.d(array.job_desc,array.com_id);
				return array;
			}	
				
			return null;
	
		}
	}catch(Exception ex)
	{
		ex.printStackTrace();
	}
	return null;
  }
}


