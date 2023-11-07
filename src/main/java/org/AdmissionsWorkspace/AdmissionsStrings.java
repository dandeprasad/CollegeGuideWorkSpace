package org.AdmissionsWorkspace;


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




@WebServlet("/AdmissionsStrings")
public class AdmissionsStrings extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  
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
		
		if (functionId.equalsIgnoreCase("GET_ADMIS_ONLY")&& actionId.equalsIgnoreCase("GET_CLG_ADMIS_STRING")){   

		  String geoId ="ALL_NEWS";
		  
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_admissions");
			String FilePath1 = imgconfig.getString("img_admissions_logos");

			
		  
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


				PreparedStatement stmt=con.prepareStatement("select * from admissions_workspace ORDER BY POSITION DESC LIMIT "+SnoOfElements+","+EnoOfElements); 
				
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
			MyUtility values =new MyUtility();
				while (rs.next()) {
					String ADMISSIONID =values.Nullcheck( rs.getString("ADMISSIONID"));
					String CLG_HEADER = values.Nullcheck(rs.getString("CLG_HEADER"));
					String ADMISSION_DETAILS=values.Nullcheck(rs.getString("ADMISSION_DETAILS"));
					String POSTED_DATE=values.Nullcheck(rs.getString("POSTED_DATE"));
					String LOCATION=values.Nullcheck(rs.getString("LOCATION"));
					String LOGO_IMAGE=values.Nullcheck(rs.getString("LOGO_IMAGE"));
					String STREAM=values.Nullcheck(rs.getString("STREAM"));
					String ADMISSION_IMAGE=values.Nullcheck(rs.getString("ADMISSION_IMAGE"));
					String ADMISSION_LINK=values.Nullcheck(rs.getString("ADMISSION_LINK"));
					
					
					JSONObject Jsonloc1= new JSONObject();
					Jsonloc1.put("ADMISSIONID",ADMISSIONID);
					Jsonloc1.put("CLG_HEADER", CLG_HEADER);
					Jsonloc1.put("ADMISSION_DETAILS", ADMISSION_DETAILS);
					Jsonloc1.put("POSTED_DATE", POSTED_DATE);
					Jsonloc1.put("LOCATION", LOCATION);
					Jsonloc1.put("LOGO_IMAGE", FilePath1+LOGO_IMAGE);
					Jsonloc1.put("STREAM", STREAM);
					Jsonloc1.put("ADMISSION_IMAGE",FilePath+ADMISSION_IMAGE);
					Jsonloc1.put("ADMISSION_LINK", ADMISSION_LINK);
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


					PreparedStatement stmt=con.prepareStatement("select * from news_workspace where TAG_TYPE='"+collegeid+"' ORDER BY POSTED_DATE DESC LIMIT "+SnoOfElements+","+EnoOfElements); 
					
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