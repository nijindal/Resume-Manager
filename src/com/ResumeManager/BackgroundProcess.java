package com.ResumeManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import android.util.Log;

public class BackgroundProcess {

	private static String URL;
	
	static String result[];
	static int Number_of_stmt = 0;

	public static ArrayList<String> makeConnection(String username, String password) {
		// Piggyback the connection and socket timeout parameters onto the
		// HttpPost request
		// try{
		// HttpParams httpParameters = new BasicHttpParams();
		// int timeoutConnection = 10000;
		// HttpConnectionParams.setConnectionTimeout(httpParameters,
		// timeoutConnection);
		// int timeoutSocket = 25000;
		// HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		URL = "http://10.42.43.1/Android/authetication_page.php";
		ArrayList<String> data_received = new ArrayList<String>();
		data_received.clear();
		
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
					String receivedCode = "SUCC";
					Log.d("connection done", "some data received");
					InputStream in = ResponsePost.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					String line = null;
					while ((line = reader.readLine()) != null) {
							data_received.add(line);
							}
					in.close();
						// result[0] = str.toString();
						// System.out.print(result);
						//System.out.print(result);
				}
				return data_received;
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
	
	public static ArrayList<String> Recruiters_data(){
		
		String URL;
		URL = "http://10.42.43.1/Android/recruiters_list.php";
		ArrayList<String> recruiters_list = new ArrayList<String>();
		recruiters_list.clear();
		
		try {
			System.out.println("reached the background class");
			HttpClient client = new DefaultHttpClient();
			HttpPost PostRequest = new HttpPost(URL);
			HttpResponse ResponsePost = client.execute(PostRequest);// connection established actuallly....
			int Response_code = ResponsePost.getStatusLine().getStatusCode();
			System.out.println("In recruiters Data" + Response_code);

			if (Response_code == HttpStatus.SC_OK) 
			{
				if (ResponsePost.getEntity() != null) 
				{
					String receivedCode = "SUCCESS";
					Log.d("connection done", "some data received");
					InputStream in = ResponsePost.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));		
					String line = null;
					
					while ((line = reader.readLine()) != null) {
							recruiters_list.add(line);
					}
					return recruiters_list;
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

}
