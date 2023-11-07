package org.QuestAndAnswers;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;

@WebServlet("/PostQuestionAnswer")
public class PostQuestionAnswer extends HttpServlet {

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
		String actionIdda = geoObject.getString("user_info");

		
		
		
		 if (functionId.equalsIgnoreCase("USER_QUESTION"))
	      {
			 if(actionId.equalsIgnoreCase("POST_QUESTION")){
				 Random rand = new Random();
				 int max = 100000;
				 int min = 0;
				 int randomNum = rand.nextInt((max - min) + 1) + min;
				 long time = System.currentTimeMillis();
				 String uniqueValue ="CE"+randomNum+time;
				 
				 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				 Date date = new Date();
				String Timestamp=dateFormat.format(date);
				 JSONObject usr_data = new JSONObject(actionIdda);
	        String StringToSend = null;
	        
	        StringToSend = actionId;
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ConfigurePath.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String Driver = config.getString("Driver");
				String ConnectionSpace = config.getString("ConnectionSpace");
				String Db_user = config.getString("Db_user");
				String Db_password = config.getString("Db_password");
				
		        try
		        {
		          Class.forName(Driver);
		          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);
	          PreparedStatement stmt = con.prepareStatement("insert into  users_question values(?,?,?,?,?,?,?,?,?)");
	          stmt.setString(1, uniqueValue);
	          stmt.setString(4, usr_data.getString("QUESTION"));
	          stmt.setString(2, usr_data.getString("USER_POSTED_NAME"));
	          stmt.setString(3, usr_data.getString("USER_POSTED_MAIL"));
	          stmt.setString(5, "");
	          stmt.setString(6, Timestamp);
	          stmt.setInt(7, 0);
	          stmt.setInt(8, 0);
	          stmt.setInt(9, 0);
	         // stmt.setString(3,usr_data.getString("MAILID"));
	          //ResultSet rs = stmt.executeQuery();	
	          int i=stmt.executeUpdate();  

	          System.out.println(i+" records inserted");  
	          if(i>=1){
	          JSONObject Jsonloc= new JSONObject();
				Jsonloc.put("UPDATE_INFO", "SUCCESS");
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(Jsonloc.toString());  }
	          con.close();
	        }
	        
	        catch (Exception e2)
	        {
	          System.out.println(e2);
	        }
	      }
			 if(actionId.equalsIgnoreCase("FEED_BACK_FORM")){
				 Random rand = new Random();
				 int max = 100000;
				 int min = 0;
				 int randomNum = rand.nextInt((max - min) + 1) + min;
				 long time = System.currentTimeMillis();
				 String uniqueValue ="CE"+randomNum+time;
				 
				 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				 Date date = new Date();
				String Timestamp=dateFormat.format(date);
				 JSONObject usr_data = new JSONObject(actionIdda);
	        String StringToSend = null;
	        
	        StringToSend = actionId;
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ConfigurePath.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String Driver = config.getString("Driver");
				String ConnectionSpace = config.getString("ConnectionSpace");
				String Db_user = config.getString("Db_user");
				String Db_password = config.getString("Db_password");
				
		        try
		        {
		          Class.forName(Driver);
		          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);
	          PreparedStatement stmt = con.prepareStatement("insert into  users_feedback_form values(?,?,?,?,?,?)");
	          stmt.setString(1, uniqueValue);
	          stmt.setString(4, usr_data.getString("ABOUT"));
	          stmt.setString(2, usr_data.getString("USER_POSTED_NAME"));
	          stmt.setString(3, usr_data.getString("USER_MAIL"));
	          stmt.setString(5, usr_data.getString("ABOUT_DESCRIPTION"));
	          stmt.setString(6, Timestamp);
	      
	         // stmt.setString(3,usr_data.getString("MAILID"));
	          //ResultSet rs = stmt.executeQuery();	
	          int i=stmt.executeUpdate();  

	          System.out.println(i+" records inserted");  
	          if(i>=1){
	          JSONObject Jsonloc= new JSONObject();
				Jsonloc.put("UPDATE_INFO", "SUCCESS");
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(Jsonloc.toString());  }
	          con.close();
	        }
	        
	        catch (Exception e2)
	        {
	          System.out.println(e2);
	        }
	      }
			 if(actionId.equalsIgnoreCase("SUBMIT_QUESTION_ANSWER")){

				 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				 Date date = new Date();
				String Timestamp=dateFormat.format(date);
				 JSONObject usr_data = new JSONObject(actionIdda);
	        String StringToSend = null;
	        
	        StringToSend = actionId;
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ConfigurePath.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String Driver = config.getString("Driver");
				String ConnectionSpace = config.getString("ConnectionSpace");
				String Db_user = config.getString("Db_user");
				String Db_password = config.getString("Db_password");
				
		        try
		        {
		          Class.forName(Driver);
		          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);
	          PreparedStatement stmt = con.prepareStatement("insert into  users_answers values(?,?,?,?,?,?,?)");
	         
	          stmt.setString(1, usr_data.getString("QUESTION_ID"));
	          stmt.setString(2, usr_data.getString("USER_ANSWERED_NAME"));
	          stmt.setString(3, usr_data.getString("USER_ANSWERED_MAIL"));
	          stmt.setString(4, usr_data.getString("ANSWER"));
	          stmt.setString(5, Timestamp);
	          stmt.setInt(6, 0);
	          stmt.setInt(7, 0);
	         // stmt.setString(3,usr_data.getString("MAILID"));
	          //ResultSet rs = stmt.executeQuery();	
	          int i=stmt.executeUpdate();  

	          System.out.println(i+" records inserted");  
	          if(i>=1){
	          JSONObject Jsonloc= new JSONObject();
				Jsonloc.put("UPDATE_INFO", "SUCCESS");
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(Jsonloc.toString());  }
	          con.close();
	        }
	        
	        catch (Exception e2)
	        {
	          System.out.println(e2);
	        }
	      }}
	}

}