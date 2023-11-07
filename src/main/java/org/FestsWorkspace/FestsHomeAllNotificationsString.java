package org.FestsWorkspace	;


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

import org.HomeWorkspace.MyUtility;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;




@WebServlet("/FestsHomeAllNotificationsString")
public class FestsHomeAllNotificationsString extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  

		  response.addHeader("Access-Control-Allow-Origin", "*");
	        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");

		PrintWriter out = response.getWriter(); 
	//	String filename = "newFile.txt";
	//	String workingDirectory = System.getProperty("user.dir");
	//	String absoluteFilePath = "";
		//Scanner s = new Scanner(new File("/home/colle3qj/ex.txt"));
		//absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
		//absoluteFilePath = workingDirectory + File.separator + filename;

	//	System.out.println("Final filepath : " + absoluteFilePath);
		MyUtility values = new MyUtility();
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		
		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");
		
		if (functionId.equalsIgnoreCase("GET_FESTS_NOTIFICATIONS")&& actionId.equalsIgnoreCase("GET_ALL_FESTNEWS_STRING")){   

		  String geoId ="ALL_NEWS";
		
		  
		  
		  Configuration config = null;
			try {
				config = new PropertiesConfiguration("ConfigurePath.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_FestsNewsNotificationsAdaptor");


			String Driver = config.getString("Driver");
			String ConnectionSpace = config.getString("ConnectionSpace");
			String Db_user = config.getString("Db_user");
			String Db_password = config.getString("Db_password");
			
	        try
	        {
	          Class.forName(Driver);
	          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);


				PreparedStatement stmt=con.prepareStatement("select * from fests_data where start_date between CURDATE() and  DATE_ADD(CURDATE(), INTERVAL 180 DAY) ORDER BY start_date ASC LIMIT 0,25"); 
				
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
				while (rs.next()) {
					
					
					String CLGID = values.Nullcheck(rs.getString("fest_id"));
					String COLLEGE_NAME = values.Nullcheck(rs.getString("fest_name"));
					Date CLG_FEST_DATE = rs.getDate("start_date");
					String CLG_FEST_INFO = values.Nullcheck(rs.getString("fest_description"));
					String CLG_LOCATION = values.Nullcheck(rs.getString("fest_reach"));
					String FEST_WEBSITE = values.Nullcheck(rs.getString("fest_website"));
					Date START_DATE =rs.getDate("start_date");
					Date END_DATE = rs.getDate("end_date");
					String FEST_IMAGE =FilePath+ values.Nullcheck(rs.getString("banner_logos"));
			
					
					JSONObject Jsonloc1= new JSONObject();
					Jsonloc1.put("CLGID", CLGID);
					Jsonloc1.put("COLLEGE_NAME", COLLEGE_NAME);
					Jsonloc1.put("CLG_FEST_DATE", CLG_FEST_DATE);
					Jsonloc1.put("END_DATE", END_DATE);
					Jsonloc1.put("START_DATE", START_DATE);
					Jsonloc1.put("FEST_WEBSITE", FEST_WEBSITE);
					Jsonloc1.put("CLG_FEST_INFO", CLG_FEST_INFO);
					Jsonloc1.put("FEST_IMAGE", FEST_IMAGE);
					Jsonloc1.put("CLG_LOCATION", CLG_LOCATION);
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
		  
		if (functionId.equalsIgnoreCase("GET_FESTS_NOTIFICATIONS")&& actionId.equalsIgnoreCase("GET_FEST_FILTER_STRING")){   

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
			
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_FestsNewsNotificationsAdaptor");

			String Driver = config.getString("Driver");
			String ConnectionSpace = config.getString("ConnectionSpace");
			String Db_user = config.getString("Db_user");
			String Db_password = config.getString("Db_password");
			
	        try
	        {
	          Class.forName(Driver);
	          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);
	          PreparedStatement stmt;

 if (filterkey.equalsIgnoreCase("TECHNICAL") || filterkey.equalsIgnoreCase("CULTURAL") ){
	 stmt=con.prepareStatement("select * from fests_data where INTERNAL_FESTTYPE= '"+filterkey+"' OR INTERNAL_FESTTYPE='ALL'  ORDER BY start_date DESC LIMIT "+SnoOfElements+","+EnoOfElements);
}
else if (filterkey.equalsIgnoreCase("NIT") || filterkey.equalsIgnoreCase("IIT") || filterkey.equalsIgnoreCase("IIIT") || filterkey.equalsIgnoreCase("DEEMED") ){
	 stmt=con.prepareStatement("select * from fests_data where INTERNAL_COLLEGE_TYPE= '"+filterkey+"'  ORDER BY start_date DESC LIMIT "+SnoOfElements+","+EnoOfElements);
}
else if (filterkey.equalsIgnoreCase("CSE") || filterkey.equalsIgnoreCase("CIVIL") || filterkey.equalsIgnoreCase("ECE") || filterkey.equalsIgnoreCase("EEE") ||  filterkey.equalsIgnoreCase("MECH") ){
	 stmt=con.prepareStatement("select * from fests_data where INTERNAL_DEPT_TYPE= '"+filterkey+"' OR INTERNAL_DEPT_TYPE='ALL' ORDER BY start_date DESC LIMIT "+SnoOfElements+","+EnoOfElements);
}
else {
	 
	 stmt=con.prepareStatement("select * from fests_data  ORDER BY start_date DESC LIMIT "+SnoOfElements+","+EnoOfElements);
}
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
				while (rs.next()) {
					String CLGID = values.Nullcheck(rs.getString("fest_id"));
					String COLLEGE_NAME = values.Nullcheck(rs.getString("fest_college_name"));
					Date CLG_FEST_DATE = rs.getDate("start_date");
					String CLG_FEST_INFO = values.Nullcheck(rs.getString("fest_description"));
					String CLG_LOCATION = values.Nullcheck(rs.getString("fest_reach"));
					String FEST_WEBSITE = values.Nullcheck(rs.getString("fest_website"));
					Date START_DATE =rs.getDate("start_date");
					Date END_DATE = rs.getDate("end_date");
					String FEST_IMAGE =FilePath+ values.Nullcheck(rs.getString("banner_logos"));
					JSONObject Jsonloc1= new JSONObject();
					Jsonloc1.put("CLGID", CLGID);
					Jsonloc1.put("COLLEGE_NAME", COLLEGE_NAME);
					Jsonloc1.put("CLG_FEST_DATE", CLG_FEST_DATE);
					Jsonloc1.put("CLG_FEST_INFO", CLG_FEST_INFO);
					Jsonloc1.put("FEST_IMAGE", FEST_IMAGE);
					Jsonloc1.put("CLG_LOCATION", CLG_LOCATION);
					
					Jsonloc1.put("FEST_WEBSITE", FEST_WEBSITE);
					Jsonloc1.put("START_DATE", START_DATE);
					Jsonloc1.put("END_DATE", END_DATE);
					
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

	
	

}}