package com.ResumeManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BackgroundProcess {
	
	
	static String result[];
	static int Number_of_stmt = 0;
//	static String domain = "http://resumemanager.x10.mx/";
	static String domain = "http://10.42.43.1/Android/";
	
	public static int makeConnection(String username, String password) {
		
		
		 HttpParams httpParameters = new BasicHttpParams();
		 int timeoutConnection = 10000;
		 HttpConnectionParams.setConnectionTimeout(httpParameters,
		 timeoutConnection);
		 int timeoutSocket = 25000;
		 HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		 
		 String URL = domain + "authentication_page.php";
	
		try {
			System.out.println("reached the background class  with   value of " + domain);
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
							System.out.print("in read line" + line);
							result.append(line);
							}
					in.close();
					String Auth_response = new String(); 
					Auth_response = result.toString();
					return Integer.parseInt(Auth_response);
				}
				return -1;
			 } 
		  else
			{
				result[0] = "server_error";
				return -1;
			}
		}catch (Exception e) {
			result[0] = "Error";
			e.printStackTrace();
		}
		return -1;
		
}
	
	
public static ArrayList<Announcement_struct> Announcements_data(int number){
		
		String URL = domain + "announcements_page.php";
		ArrayList<String> Announcements_list = new ArrayList<String>();
		ArrayList<Announcement_struct> Announcements_struct = new ArrayList<Announcement_struct>();
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
					Log.d("connection done", "some  data received");
					StringBuilder str_rcvd = new StringBuilder();
					InputStream in = ResponsePost.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));		
					String line = null;
					
					while ((line = reader.readLine()) != null) {
//							Announcements_list.add(line);
							str_rcvd.append(line);
					}
					
					String str = str_rcvd.toString();
					Log.d("Received string is",str);
//using Gson to parse the obtained JSON data from the server.....here data obtained is nothing but array of 
//announce structs...which can be directly put into classes either by array of classes but that is not efficient..
//so, we are using arrayllist and the second parameter is class name, which needs to be obtained using the
//TypeToken function.......
					
					Gson gson = new Gson();
					Type listType = new TypeToken<ArrayList<Announcement_struct>>(){}.getType();
					Announcements_struct = (ArrayList<Announcement_struct>) gson.fromJson(str,listType);

					return Announcements_struct;
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
	
public static ArrayList<Recruiter_struct> Recruiters_data(int number){
		
		String URL;
		URL = domain + "recruiters_list.php";
		ArrayList<String> recruiters_list = new ArrayList<String>();
		ArrayList<Recruiter_struct> recruit_struct;
		recruiters_list.clear();
		
		try {
			System.out.println("reached the background class of recruiters");
			HttpClient client = new DefaultHttpClient();
			HttpPost PostRequest = new HttpPost(URL);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("number",new Integer(number).toString()));
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
			PostRequest.setEntity(ent);
			
			HttpResponse ResponsePost = client.execute(PostRequest);// connection established actuallly....
			int Response_code = ResponsePost.getStatusLine().getStatusCode();
			System.out.println("In recruiters Data" + Response_code);

			if (Response_code == HttpStatus.SC_OK) 
			{
				if (ResponsePost.getEntity() != null) 
				{
					Log.d("connection done", "some  data rceived");
					InputStream in = ResponsePost.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));		
					String line = null;
					StringBuilder str_rcvd = new StringBuilder();
					
					while ((line = reader.readLine()) != null) {
						str_rcvd.append(line);
					}

					String str = str_rcvd.toString();
					Gson gson = new Gson();
					Type listType = new TypeToken<ArrayList<Recruiter_struct>>(){}.getType();
					recruit_struct = (ArrayList<Recruiter_struct>) gson.fromJson(str,listType);

					Log.d(recruit_struct.get(0).branches_be,recruit_struct.get(1).pkg_be);
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


static public Rec_details_struct get_recruiter_details(int company_id){
	
	String URL = domain + "recruiter_detail.php";
	Rec_details_struct array;
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
				array = gson.fromJson(string_rcvd, Rec_details_struct.class);
				if(array.com_name == "")
					Log.d("the company name is nulll revd","checked it just now");
				else
					Log.d(array.job_desc,array.branches_me);
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


