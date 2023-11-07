package org.PlacementsWorkspace;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;




@WebServlet("/PlacementsStrings")
public class PlacementsStrings extends HttpServlet {

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
		
		
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		
		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");
		int SnoOfElements = geoObject.getInt("SROW_INDEX");
		int EnoOfElements = geoObject.getInt("EROW_INDEX");
		
		if (functionId.equalsIgnoreCase("GET_PLACE_ONLY")&& actionId.equalsIgnoreCase("GET_ALL_PLACE_STRING")){   

		  String geoId ="ALL_NEWS";
		  String UNIQUE_ID = geoObject.getString("uniqueTosend");
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_Placements");
		  
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


				PreparedStatement stmt=con.prepareStatement("select * from placements_workspace   where CLG_ID= '" +UNIQUE_ID+ "'" ); 
				
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
			int year2017=0,year2016=0,year20177=0;
			JSONObject JSOBVAL2017= new JSONObject();
			JSONObject JSOBVAL2016= new JSONObject();
			JSONObject JSOBVAL20177= new JSONObject();
			ArrayList val2017=new ArrayList();
				while (rs.next()) {
					String PLACE_YEAR = rs.getString("PLACE_YEAR"); 	
					String PLACE_COMPANY = rs.getString("PLACE_COMPANY");
					String PLACE_AMOUNT=rs.getString("PLACE_AMOUNT");

			
					JSONObject Jsonloc1= new JSONObject();
					Jsonloc1.put("PLACE_YEAR", PLACE_YEAR);
					Jsonloc1.put("PLACE_COMPANY", FilePath+PLACE_COMPANY);
					Jsonloc1.put("PLACE_AMOUNT", PLACE_AMOUNT);
		
					String x1  = Integer.toString(inc);
					if(PLACE_YEAR.equalsIgnoreCase("2015")){
						String val1  = Integer.toString(year2017);
						JSOBVAL2017.put(val1, Jsonloc1);
					year2017++;
					}
					if(PLACE_YEAR.equalsIgnoreCase("2016")){
						String val1  = Integer.toString(year2016);
						JSOBVAL2016.put(val1, Jsonloc1);
					year2016++;
					}
					if(PLACE_YEAR.equalsIgnoreCase("2017")){
						String val1  = Integer.toString(year20177);
						JSOBVAL20177.put(val1, Jsonloc1);
					year20177++;
					}
					inc++;
					
				}
				//JSOBVAL.put("2",JSOBVAL20177);
				JSOBVAL.put("0",JSOBVAL2016);
				JSOBVAL.put("1",JSOBVAL2017);
				
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(JSOBVAL.toString());     
				    con.close();   
				}catch (Exception e2) {System.out.println(e2);}  
				          
				out.close(); 
				
			}
		  
		
		if (functionId.equalsIgnoreCase("GET_NEWS_ONLY")&& actionId.equalsIgnoreCase("GET_CLG_NEWS_STRING")){   

			String collegeid = geoObject.getString("COLLEGE_ID");
			
			  
			  
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
						String NEWSID = rs.getString("NEWSID");
						String NEWS_HEADER = rs.getString("NEWS_HEADER");
						Date POSTED_DATE=rs.getDate("POSTED_DATE");
						String TAG_TYPE=rs.getString("TAG_TYPE");
						String TAG_COLOR=rs.getString("TAG_COLOR");
						String FULL_IMAGE=rs.getString("FULL_IMAGE");
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("NEWSID", NEWSID);
						Jsonloc1.put("NEWS_HEADER", NEWS_HEADER);
						Jsonloc1.put("POSTED_DATE", POSTED_DATE.toString());
						Jsonloc1.put("TAG_TYPE", TAG_TYPE);
						Jsonloc1.put("TAG_COLOR", TAG_COLOR);
						Jsonloc1.put("FULL_IMAGE", FULL_IMAGE);
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