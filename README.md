Current Progress:
	* Its an android app for Placement Portal of my College.
	* All students in college have an Account on this portal.This app provides easy access to 
	  all live update, new companies visiting, Application Submissions etc.
	* For this, I have written some Server-side APIs which takes data in raw form from main server,
	  encodes it in json. This json-encoded data is sent to app, which is further decoded on device.
	* Manual updation of comapies data and announcements. 
	* It uses Sqlite DB to store information on device, once retrieved from Server.

For future:
	* Adding push updates about any new announcement or company.
	* Pull to Refresh is also under process.
	* Currently, it is specific to portal of my college only, But I am thinking of making it generalised. 
	 so that, it can be used for any of placement portals.
