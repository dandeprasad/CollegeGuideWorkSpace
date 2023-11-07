package org.ClgAndroidWorkspace.HomeNewsNotificationsWorkspace;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.sql.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;




@WebServlet("/HomeDataShowDetailsPositionID")
public class HomeDataShowDetailsPositionID extends HttpServlet {

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
		String positionId = geoObject.getString("POSITION_ID");
		String extradata = geoObject.getString("EXTRA_DATA");
		if(workspaceId.equalsIgnoreCase("HOME_WORKSPACE")){
			
		
		if (functionId.equalsIgnoreCase("GET_ALL_NEWS_WORKSPACE")&& actionId.equalsIgnoreCase("GET_ALL_NEWS_WORKSPACE_STRINGS")){   

		 // String geoId =positionId;
		  String StringToSend=null;

			 StringToSend =  positionId;
		
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
				PreparedStatement stmt=con.prepareStatement("select * from news_workspace where NEWSID=?"); 
				stmt.setString(1,StringToSend);
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
				while (rs.next()) {
					String DATA_DETAILS = rs.getString("NEWS_DETAILS");
					String DATA_HEADER = rs.getString("NEWS_HEADER");
					//String NEWS_HEADER = rs.getString("NEWS_HEADER");
					JSONObject Jsonloc1= new JSONObject();
					Jsonloc1.put("DATA_DETAILS", DATA_DETAILS);
					Jsonloc1.put("DATA_HEADER", DATA_HEADER);
					//Jsonloc1.put("NEWS_HEADER", NEWS_HEADER);
					String x1  = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;
					
				}
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(JSOBVAL.toString());     
				    con.close();   
				}catch (Exception e2) {System.out.println(e2);}  
				          
				//out.close(); 
				
			}
		  
		if (functionId.equalsIgnoreCase("GET_ALL_EXAMS_WORKSPACE")&& actionId.equalsIgnoreCase("GET_ALL_EXAMS_WORKSPACE_IMG")){  
			
			
			
			
			
			String filename = null;
			String filePosition = positionId;

				 filename =filePosition+".jpg";

			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String FilePath = config.getString("ExamsImages");	
	

	        File file = new File(FilePath, filename);
	        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	        Files.copy(file.toPath(), response.getOutputStream());	
		}	
		
		if (functionId.equalsIgnoreCase("GET_ALL_EXAMS_WORKSPACE")&& actionId.equalsIgnoreCase("GET_ALL_EXAMS_WORKSPACE_STRINGS")){   

			 // String geoId =positionId;
			  String StringToSend=null;

				 StringToSend =  positionId;
			
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
					PreparedStatement stmt=con.prepareStatement("select * from exams_workspace where EXAMSID=?"); 
					stmt.setString(1,StringToSend);
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String DATA_DETAILS = rs.getString("EXAMS_DETAILS");
						String DATA_HEADER = rs.getString("EXAMS_HEADER");
						//String NEWS_HEADER = rs.getString("NEWS_HEADER");
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("DATA_DETAILS", DATA_DETAILS);
						Jsonloc1.put("DATA_HEADER", DATA_HEADER);
						//Jsonloc1.put("NEWS_HEADER", NEWS_HEADER);
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					//out.close(); 
					
				}
			  
			if (functionId.equalsIgnoreCase("GET_ALL_NEWS_WORKSPACE")&& actionId.equalsIgnoreCase("GET_ALL_NEWS_WORKSPACE_IMG")){  
				
				
				
				
				
				String filename = null;
				String filePosition = positionId;

					 filename =filePosition+".jpg";

				//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
				  Configuration config = null;
					try {
						config = new PropertiesConfiguration("ImagesConfig.properties");
					} catch (ConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					

					String FilePath = config.getString("NewsImages");	
		

		        File file = new File(FilePath, filename);
		        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
		        response.setHeader("Content-Length", String.valueOf(file.length()));
		        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
		        Files.copy(file.toPath(), response.getOutputStream());	
			}	
			

}}}