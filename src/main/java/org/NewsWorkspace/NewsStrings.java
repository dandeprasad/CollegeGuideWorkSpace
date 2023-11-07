package org.NewsWorkspace;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.HomeWorkspace.MyUtility;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;




@WebServlet("/NewsStrings")
public class NewsStrings extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  
		  response.addHeader("Access-Control-Allow-Origin", "*");
	        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");

		PrintWriter out = response.getWriter(); 
	    Configuration imgconfig = null;
		try {
			imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String FilePath = imgconfig.getString("img_NewsFragmentAdaptor");
	//	String filename = "newFile.txt";
	//	String workingDirectory = System.getProperty("user.dir");
	//	String absoluteFilePath = "";
		//Scanner s = new Scanner(new File("/home/colle3qj/ex.txt"));
		//absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
		//absoluteFilePath = workingDirectory + File.separator + filename;

	//	System.out.println("Final filepath : " + absoluteFilePath);
		

		JSONObject JSOBVAL= new JSONObject();	
		MyUtility values = new MyUtility();
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		
		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");
		
		
		if (functionId.equalsIgnoreCase("GET_NEWS_ONLY")&& actionId.equalsIgnoreCase("GET_ALL_NEWS_STRING")){   

			String filterkey = geoObject.getString("FILTER_KEY");
		
		
			int SnoOfElements = geoObject.getInt("SROW_INDEX");
			int EnoOfElements = geoObject.getInt("EROW_INDEX");
		  
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
	          PreparedStatement stmt;
if(filterkey.equalsIgnoreCase("ALL")){
	stmt=con.prepareStatement("select * from news_workspace ORDER BY POSITION DESC LIMIT "+SnoOfElements+","+EnoOfElements);		 
}
else{
	 
	 
	 stmt=con.prepareStatement("select * from news_workspace where FILTER_TYPE= '"+filterkey+"' ORDER BY POSITION DESC LIMIT "+SnoOfElements+","+EnoOfElements);
}
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
				while (rs.next()) {
					String NEWSID = values.Nullcheck(rs.getString("NEWSID"));
					String NEWS_HEADER = values.Nullcheck(rs.getString("NEWS_HEADER"));
					Date POSTED_DATE=rs.getDate("POSTED_DATE");
					String TAG_TYPE=values.Nullcheck(rs.getString("TAG_TYPE"));
					String TAG_COLOR=values.Nullcheck(rs.getString("TAG_COLOR"));
					String FULL_IMAGE=values.Nullcheck(rs.getString("FULL_IMAGE"));
					String NEWS_DETAILS=values.Nullcheck(rs.getString("NEWS_DETAILS"));
					String NEWS_IMAGE=values.Nullcheck(rs.getString("NEWS_IMAGE"));
					String WEBSITE_LINKS=values.Nullcheck(rs.getString("WEBSITE_LINKS"));
					JSONObject Jsonloc1= new JSONObject();
					Jsonloc1.put("NEWSID", NEWSID);
					Jsonloc1.put("NEWS_HEADER", NEWS_HEADER);
					Jsonloc1.put("POSTED_DATE", POSTED_DATE.toString());
					Jsonloc1.put("TAG_TYPE", TAG_TYPE);
					Jsonloc1.put("TAG_COLOR", TAG_COLOR);
					Jsonloc1.put("FULL_IMAGE", FULL_IMAGE);
					Jsonloc1.put("NEWS_DETAILS", NEWS_DETAILS);
					Jsonloc1.put("WEBSITE_LINKS", WEBSITE_LINKS);
					Jsonloc1.put("NEWS_IMAGE",FilePath+NEWS_IMAGE);
					String x1  = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;
					
				}
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(JSOBVAL.toString());     
				    con.close();   
				}catch (Exception e2) {System.out.println(e2);}  
				          
				out.close(); 
				
			}
		  
		if (functionId.equalsIgnoreCase("GET_NEWS_ONLY")&& actionId.equalsIgnoreCase("GET_STRING_COLLEGEFILTER")){   

			String filterkey = geoObject.getString("FILTER_KEY");
		
			int SnoOfElements = geoObject.getInt("SROW_INDEX");
			int EnoOfElements = geoObject.getInt("EROW_INDEX");
	
		  
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
	          PreparedStatement stmt;

	 
	 stmt=con.prepareStatement("select * from news_workspace where TAG_TYPE= '"+filterkey+"' ORDER BY POSITION DESC LIMIT "+SnoOfElements+","+EnoOfElements);

				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
				while (rs.next()) {
					String NEWSID = values.Nullcheck(rs.getString("NEWSID"));
					String NEWS_HEADER =values.Nullcheck( rs.getString("NEWS_HEADER"));
					Date POSTED_DATE=rs.getDate("POSTED_DATE");
					String TAG_TYPE=values.Nullcheck(rs.getString("TAG_TYPE"));
					String TAG_COLOR=values.Nullcheck(rs.getString("TAG_COLOR"));
					String FULL_IMAGE=values.Nullcheck(rs.getString("FULL_IMAGE"));
					String NEWS_DETAILS=values.Nullcheck(rs.getString("NEWS_DETAILS"));
					String NEWS_IMAGE=values.Nullcheck(rs.getString("NEWS_IMAGE"));
					String WEBSITE_LINKS=values.Nullcheck(rs.getString("WEBSITE_LINKS"));
					JSONObject Jsonloc1= new JSONObject();
					Jsonloc1.put("NEWSID", NEWSID);
					Jsonloc1.put("NEWS_HEADER", NEWS_HEADER);
					Jsonloc1.put("POSTED_DATE", POSTED_DATE.toString());
					Jsonloc1.put("TAG_TYPE", TAG_TYPE);
					Jsonloc1.put("TAG_COLOR", TAG_COLOR);
					Jsonloc1.put("FULL_IMAGE", FULL_IMAGE);
					Jsonloc1.put("NEWS_DETAILS", NEWS_DETAILS);
					Jsonloc1.put("WEBSITE_LINKS", WEBSITE_LINKS);
					Jsonloc1.put("NEWS_IMAGE",FilePath+NEWS_IMAGE);
					String x1  = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;
					
				}
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(JSOBVAL.toString());     
				    con.close();   
				}catch (Exception e2) {System.out.println(e2);}  
				          
				out.close(); 
				
			}
		if (functionId.equalsIgnoreCase("GET_NEWS_ONLY")&& actionId.equalsIgnoreCase("GET_CLG_NEWS_STRING")){   

			String collegeid = geoObject.getString("COLLEGE_ID");
			
			int SnoOfElements = geoObject.getInt("SROW_INDEX");
			int EnoOfElements = geoObject.getInt("EROW_INDEX");
			  
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


					PreparedStatement stmt=con.prepareStatement("select * from news_workspace where TAG_TYPE='"+collegeid+"' ORDER BY POSITION DESC LIMIT "+SnoOfElements+","+EnoOfElements); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String NEWSID = values.Nullcheck(FilePath+ rs.getString("NEWSID"));
						String NEWS_HEADER =values.Nullcheck( rs.getString("NEWS_HEADER"));
						Date POSTED_DATE=rs.getDate("POSTED_DATE");
						String TAG_TYPE=values.Nullcheck(rs.getString("TAG_TYPE"));
						String TAG_COLOR=values.Nullcheck(rs.getString("TAG_COLOR"));
						String FULL_IMAGE=values.Nullcheck(rs.getString("FULL_IMAGE"));
						String NEWS_DETAILS=values.Nullcheck(rs.getString("NEWS_DETAILS"));
						String WEBSITE_LINKS=values.Nullcheck(rs.getString("WEBSITE_LINKS"));
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("NEWSID", NEWSID);
						Jsonloc1.put("NEWS_HEADER", NEWS_HEADER);
						Jsonloc1.put("POSTED_DATE", POSTED_DATE.toString());
						Jsonloc1.put("TAG_TYPE", TAG_TYPE);
						Jsonloc1.put("TAG_COLOR", TAG_COLOR);
						Jsonloc1.put("FULL_IMAGE", FULL_IMAGE);
						Jsonloc1.put("NEWS_DETAILS", NEWS_DETAILS);
						Jsonloc1.put("WEBSITE_LINKS", WEBSITE_LINKS);
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					out.close(); 
					
				}
			  
		if (functionId.equalsIgnoreCase("GET_NEWS_DETAILS")&& actionId.equalsIgnoreCase("GET_NEWS_DETAILSID")){   

			String NEWS_ID = escapechar(geoObject.getString("NEWS_ID"));
			
			  
			  
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


					PreparedStatement stmt=con.prepareStatement("select * from news_workspace where NEWSID='"+NEWS_ID+"'"); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String NEWSID = values.Nullcheck(rs.getString("NEWSID"));
						String NEWS_IMAGE=values.Nullcheck(rs.getString("NEWS_IMAGE"));
						
						String NEWS_HEADER =values.Nullcheck( rs.getString("NEWS_HEADER"));
						Date POSTED_DATE=rs.getDate("POSTED_DATE");
						String TAG_TYPE=values.Nullcheck(rs.getString("TAG_TYPE"));
						String TAG_COLOR=values.Nullcheck(rs.getString("TAG_COLOR"));
						String FULL_IMAGE=values.Nullcheck(rs.getString("FULL_IMAGE"));
						String NEWS_DETAILS=values.Nullcheck(rs.getString("NEWS_DETAILS"));
						String WEBSITE_LINKS=values.Nullcheck(rs.getString("WEBSITE_LINKS"));
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("NEWSID", NEWSID);
						Jsonloc1.put("NEWS_IMAGE", FilePath+NEWS_IMAGE);
						Jsonloc1.put("NEWS_HEADER", NEWS_HEADER);
						Jsonloc1.put("POSTED_DATE", POSTED_DATE.toString());
						Jsonloc1.put("TAG_TYPE", TAG_TYPE);
						Jsonloc1.put("TAG_COLOR", TAG_COLOR);
						Jsonloc1.put("FULL_IMAGE", FULL_IMAGE);
						Jsonloc1.put("NEWS_DETAILS", NEWS_DETAILS);
						Jsonloc1.put("WEBSITE_LINKS", WEBSITE_LINKS);
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					out.close(); 
					
				}
		if (functionId.equalsIgnoreCase("GET_NEWS_COUNT")&& actionId.equalsIgnoreCase("GET_NEWS_COUNT")){   

			
			
			  
			  
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


					PreparedStatement stmt=con.prepareStatement("select count(1) as NEWS_COUNT from news_workspace"); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String NEWS_COUNT = values.Nullcheck(rs.getString("NEWS_COUNT"));
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("NEWS_COUNT", NEWS_COUNT);
						
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					out.close(); 
					
				}
		

}
	
	 public String  escapechar(Object object ) {
		 return object.toString().replace("'", "\\'");
	 }
	 
}