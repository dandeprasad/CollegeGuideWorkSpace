package org.FestsWorkspace;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
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


@WebServlet("/Festsdata")
public class Festsdata extends HttpServlet {

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
		
		if (functionId.equalsIgnoreCase("GET_FESTS_OVERVIEW")&& actionId.equalsIgnoreCase("GET_FESTS_OVERVIEW")){   
			String festid = geoObject.getString("ACTION_ID");
			
			festid= geoObject.getString("FEST_ID");
			
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_Fests_data");

		  
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


				PreparedStatement stmt=con.prepareStatement("select fest_id,fest_college_name,college_website,fest_name,fest_caption,fest_type,fest_description,fest_depart,start_date,end_date ,banner_logos from fests_data where fest_id = '"+festid+"'"); 
				
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
		
				while (rs.next()) {
					
					String fest_id =values.Nullcheck(rs.getString("fest_id"));
					String fest_college_name =values.Nullcheck( rs.getString("fest_college_name"));
					String college_website = values.Nullcheck(rs.getString("college_website"));
					String fest_name = values.Nullcheck(rs.getString("fest_name"));
					String fest_caption =values.Nullcheck( rs.getString("fest_caption"));
					String fest_type = values.Nullcheck(rs.getString("fest_type"));
					String fest_description =values.Nullcheck( rs.getString("fest_description"));
					String fest_depart =values.Nullcheck( rs.getString("fest_depart"));
					
					Date date = rs.getDate("start_date");
					Date date1 = rs.getDate("end_date");
					SimpleDateFormat dt1 = new SimpleDateFormat("yyyyy/MM/dd");
					String startdate = dt1.format(date);
					String enddate = dt1.format(date1);
					String start_date = startdate;
					String end_date = enddate;
					
					String banner_logos = FilePath+rs.getString("banner_logos");
					JSONObject Jsonloc1= new JSONObject();
				
					Jsonloc1.put("fest_id", fest_id);
					
					Jsonloc1.put("fest_college_name", fest_college_name);
					Jsonloc1.put("college_website", college_website);
					Jsonloc1.put("fest_name", fest_name);
					Jsonloc1.put("fest_caption", fest_caption);
					Jsonloc1.put("fest_type", fest_type);
					Jsonloc1.put("fest_description", fest_description);
					Jsonloc1.put("fest_depart", fest_depart);
					
					Jsonloc1.put("start_date", start_date);
					Jsonloc1.put("end_date", end_date);
					Jsonloc1.put("banner_logos", banner_logos);
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
		  
		
		else	if (functionId.equalsIgnoreCase("GET_FESTS_EVENTDETAILS")&& actionId.equalsIgnoreCase("GET_FESTS_EVENTDETAILS")){   
			String festid = geoObject.getString("ACTION_ID");
			festid= geoObject.getString("FEST_ID");
			
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_Fests_data");

		  
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


				PreparedStatement stmt=con.prepareStatement("select fest_event,fest_workshop,fest_paper from fests_data where fest_id = '"+festid+"'"); 
				
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
		
				while (rs.next()) {
					
					String fest_event = values.Nullcheck(rs.getString("fest_event"));
					String fest_workshop = values.Nullcheck(rs.getString("fest_workshop"));
					String fest_paper =values.Nullcheck( rs.getString("fest_paper"));

					JSONObject Jsonloc1= new JSONObject();
				
					Jsonloc1.put("fest_event", fest_event);
					Jsonloc1.put("fest_workshop", fest_workshop);
					Jsonloc1.put("fest_paper", fest_paper);
			
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
		else if (functionId.equalsIgnoreCase("GET_FESTS_REGIS_LINKS")&& actionId.equalsIgnoreCase("GET_FESTS_REGIS_LINKS")){   
			String festid = geoObject.getString("ACTION_ID");
			festid= geoObject.getString("FEST_ID");
			
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_Fests_data");

		  
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


				PreparedStatement stmt=con.prepareStatement("select fest_links,fest_website,college_website,fest_regis_url from fests_data where fest_id = '"+festid+"'"); 
				
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
		
				while (rs.next()) {
					
					String fest_links =values.Nullcheck( rs.getString("fest_links"));
					String fest_website =values.Nullcheck( rs.getString("fest_website"));
					String college_website = values.Nullcheck(rs.getString("college_website"));
					String fest_regis_url =values.Nullcheck( rs.getString("fest_regis_url"));
				
					JSONObject Jsonloc1= new JSONObject();
				
					Jsonloc1.put("fest_links", fest_links);
					Jsonloc1.put("fest_website", fest_website);
					Jsonloc1.put("college_website", college_website);
					Jsonloc1.put("fest_regis_url", fest_regis_url);

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
		else	if (functionId.equalsIgnoreCase("GET_FESTS_REGIS_ACCESS")&& actionId.equalsIgnoreCase("GET_FESTS_REGIS_ACCESS")){   
			String festid = geoObject.getString("ACTION_ID");
			festid= geoObject.getString("FEST_ID");
			
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_Fests_data");

		  
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


				PreparedStatement stmt=con.prepareStatement("select fest_regis_url,fest_Regis_fees,fest_dead_registration,fest_sponsors,fest_guest from fests_data where fest_id = '"+festid+"'"); 
				
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
		
				while (rs.next()) {
					
					String fest_regis_url = values.Nullcheck(rs.getString("fest_regis_url"));
					String fest_Regis_fees = values.Nullcheck(rs.getString("fest_Regis_fees"));
					String fest_dead_registration =values.Nullcheck( rs.getString("fest_dead_registration"));
					String fest_sponsors = values.Nullcheck(rs.getString("fest_sponsors"));
					String fest_guest = values.Nullcheck(rs.getString("fest_guest"));
				
					JSONObject Jsonloc1= new JSONObject();
				
					Jsonloc1.put("fest_regis_url", fest_regis_url);
					Jsonloc1.put("fest_Regis_fees", fest_Regis_fees);
					Jsonloc1.put("fest_dead_registration", fest_dead_registration);
					Jsonloc1.put("fest_sponsors", fest_sponsors);
					Jsonloc1.put("fest_guest", fest_guest);

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
		else	if (functionId.equalsIgnoreCase("GET_FESTS_REACHUS")&& actionId.equalsIgnoreCase("GET_FESTS_REACHUS")){   
			String festid = geoObject.getString("ACTION_ID");
			festid= geoObject.getString("FEST_ID");
			
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_Fests_data");

		  
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


				PreparedStatement stmt=con.prepareStatement("select college_location,fest_reach,Fest_contact_persons from fests_data where fest_id = '"+festid+"'"); 
				
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
		
				while (rs.next()) {
					
					String college_location = values.Nullcheck(rs.getString("college_location"));
					String fest_reach = values.Nullcheck(rs.getString("fest_reach"));
					String Fest_contact_persons = values.Nullcheck(rs.getString("Fest_contact_persons"));
		
				
					JSONObject Jsonloc1= new JSONObject();
				
					Jsonloc1.put("college_location", college_location);
					Jsonloc1.put("fest_reach", fest_reach);
					Jsonloc1.put("Fest_contact_persons", Fest_contact_persons);
					

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
		if (functionId.equalsIgnoreCase("GET_MAPS_FESTDATA")&& actionId.equalsIgnoreCase("GET_MAPS_FESTDATA")){   
			//String festid = geoObject.getString("ACTION_ID");
			
			String CLG_ID= geoObject.getString("CLG_ID");
			
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_Fests_data");

		  
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


				PreparedStatement stmt=con.prepareStatement("select fest_id,fest_college_name,college_website,fest_name,fest_caption,fest_type,fest_description,fest_depart,start_date,end_date ,banner_logos from fests_data where CLG_ID = '"+CLG_ID+"'"); 
				
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
		
				while (rs.next()) {
					
					String fest_id = values.Nullcheck(rs.getString("fest_id"));
					String fest_college_name =values.Nullcheck( rs.getString("fest_college_name"));
					String college_website =values.Nullcheck( rs.getString("college_website"));
					String fest_name = values.Nullcheck(rs.getString("fest_name"));
					String fest_caption = values.Nullcheck(rs.getString("fest_caption"));
					String fest_type =values.Nullcheck( rs.getString("fest_type"));
					String fest_description = values.Nullcheck(rs.getString("fest_description"));
					String fest_depart =values.Nullcheck( rs.getString("fest_depart"));
					
					Date date = rs.getDate("start_date");
					Date date1 = rs.getDate("end_date");
					SimpleDateFormat dt1 = new SimpleDateFormat("yyyyy/MM/dd");
					String startdate = dt1.format(date);
					String enddate = dt1.format(date1);
					String start_date = startdate;
					String end_date = enddate;
					
					String banner_logos = FilePath+rs.getString("banner_logos");
					JSONObject Jsonloc1= new JSONObject();
				
					Jsonloc1.put("fest_id", fest_id);
					
					Jsonloc1.put("fest_college_name", fest_college_name);
					Jsonloc1.put("college_website", college_website);
					Jsonloc1.put("fest_name", fest_name);
					Jsonloc1.put("fest_caption", fest_caption);
					Jsonloc1.put("fest_type", fest_type);
					Jsonloc1.put("fest_description", fest_description);
					Jsonloc1.put("fest_depart", fest_depart);
					
					Jsonloc1.put("start_date", start_date);
					Jsonloc1.put("end_date", end_date);
					Jsonloc1.put("banner_logos", banner_logos);
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