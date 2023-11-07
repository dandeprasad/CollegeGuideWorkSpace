package org.ClgAndroidWorkspace.FireBaseTokenWorkspace;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.sql.*;
import java.time.ZoneId;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;




@WebServlet("/FirebaseTokenAccess")
public class FirebaseTokenAccess extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");  
		//PrintWriter out = response.getWriter(); 
		
		
		
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		String workspaceId = geoObject.getString("WORKSPACE_ID");
		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");
		String token = geoObject.getString("token");
		String user = geoObject.getString("user");
		if(workspaceId.equalsIgnoreCase("HOME_WORKSPACE")){
			
		
		if (functionId.equalsIgnoreCase("TOKEN_FIREBASE")&& actionId.equalsIgnoreCase("USER_TOKEN")){   

		  String tokentosend =token;
		  String[] usrdata = user.split("\\|");
		  String username =null;
		  String email = null;
		  String location = null;
		  if(usrdata!=null && usrdata.length>0) {
			  if(usrdata.length>0)
			  username =  usrdata[0];
			  if(usrdata.length>1)
			  email=usrdata[1];
			  if(usrdata.length>2)
			  location=usrdata[2];
		  }
		  
		  Configuration config = null;
			try {
				config = new PropertiesConfiguration("ConfigurePath.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			 java.util.Date dt = new java.util.Date();

			 java.text.SimpleDateFormat sdf = 
					new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 ZoneId singaporeZoneId = ZoneId.of("Asia/Kolkata");
			 sdf.setTimeZone(TimeZone.getTimeZone(singaporeZoneId));
			 String currentTime = sdf.format(dt);

				
					

			String Driver = config.getString("Driver");
			String ConnectionSpace = config.getString("ConnectionSpace");
			String Db_user = config.getString("Db_user");
			String Db_password = config.getString("Db_password");
			
	        try
	        {
	          Class.forName(Driver);
	          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);
	          //Connection con = DriverManager.getConnection(Connection);

	  		if(email==null) {
				
		          PreparedStatement stmt = con.prepareStatement("insert into  firebase_token values(?,?,?,?,?)");
		          stmt.setString(1, "NOTSIGNEDIN");
		          stmt.setString(2, username);
		          stmt.setString(3, tokentosend);
		          stmt.setString(4, currentTime);
		          stmt.setString(5, location);
		          
		         
		          //ResultSet rs = stmt.executeQuery();	
		          int i=stmt.executeUpdate();  

		          System.out.println(i+" records inserted"); 
				}
	  		else {
	          
	          
	          PreparedStatement stmt1;

	          
	        		  
	stmt1=con.prepareStatement("select distinct(USER_MAIL) from firebase_token where USER_MAIL='"+email+"'");		 

				ResultSet rs=stmt1.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
			if (rs.next() == false) {
		          PreparedStatement stmt = con.prepareStatement("insert into  firebase_token values(?,?,?,?,?)");
		          stmt.setString(1, email);
		          stmt.setString(2, username);
		          stmt.setString(3, tokentosend);
		          stmt.setString(4, currentTime);
		          stmt.setString(5, location);
		          
		         
		          //ResultSet rs = stmt.executeQuery();	
		          int i=stmt.executeUpdate();  

		          System.out.println(i+" records inserted"); 
		          
				}else {

			
					
					if(rs.getString("USER_MAIL")!=null) {
					
				          PreparedStatement stmt = con.prepareStatement("UPDATE firebase_token set TOKEN_ID='"+tokentosend+"',USER_LOCATION='"+location+"' where USER_MAIL='"+email+"'");
			 
				          //ResultSet rs = stmt.executeQuery();	
				          int i=stmt.executeUpdate();  

				          System.out.println(i+" records inserted");
						
					
					}
					
					
				}
	          
	          con.close();
	        }}
	        catch(Exception e){
	        	 System.out.println(e);  
	        	}
	        }
	
		}
	}}