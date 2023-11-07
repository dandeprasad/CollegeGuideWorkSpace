package org.ClgAndroidWorkspace.FireBaseTokenWorkspace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


public class FirebaseTokenAccess1 {


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
		  String userid=user;


			
	        try
	        {
	          Class.forName("org.mariadb.jdbc.Driver");
	          
	          Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/collegeguideworkspace", "root", "Welcome@02");
	          
	          PreparedStatement stmt = con.prepareStatement("insert into  users_login values(?,?)");
	          stmt.setString(1, tokentosend);
	          stmt.setString(2, userid);
	         
	          //ResultSet rs = stmt.executeQuery();	
	          int i=stmt.executeUpdate();  

	          System.out.println(i+" records inserted");  
	          
	          con.close();
	        }
	        catch(Exception e){
	        	}
	        }
	
		}


}}
