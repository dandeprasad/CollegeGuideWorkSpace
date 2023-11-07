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

import org.AmazonSupport.AmazonSupportRequests;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;




@WebServlet("/HomeDataShowDetails")
public class HomeDataShowDetails extends HttpServlet {

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
		int positionId = geoObject.getInt("POSITION_NO");
		String extradata = geoObject.getString("EXTRA_DATA");
		if(workspaceId.equalsIgnoreCase("HOME_WORKSPACE")){
			
		
		if (functionId.equalsIgnoreCase("GET_SHOW_DETAIL")&& actionId.equalsIgnoreCase("GET_ALL_NEWS_STRINGS")){   

		  int geoId =positionId;
		  String StringToSend=null;
		if(geoId==0){
		//  String StringToSend =  Integer.toString(geoId);
			StringToSend =  "newsHeader";
		}
		else {
			 StringToSend =  "news"+Integer.toString(geoId);
		}
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
				PreparedStatement stmt=con.prepareStatement("select * from home_all_news where NEWSID=?"); 
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
		  
		if (functionId.equalsIgnoreCase("GET_SHOW_DETAIL")&& actionId.equalsIgnoreCase("GET_ALL_NEWS_IMAGES")){  
			
			String filename = null;
			int filePosition = positionId;
			if (filePosition==0){
				 filename ="position_0.jpg";
			}
			else if (filePosition==1){
				 filename ="position_1.jpg";
			}
			
			else if (filePosition==2){
				filename ="position_2.jpg";
			}
			else if (filePosition==3){
				filename ="position_3.jpg";
			}
			else if (filePosition==4){
				filename ="position_4.jpg";
			}
			else if (filePosition==5){
				filename ="position_5.jpg";
			}
			else if (filePosition==6){
				filename ="position_6.jpg";
			}
			else if (filePosition==7){
				filename ="position_6.jpg";
			}
			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String FilePath = config.getString("HomeDataShowDetails_GET_ALL_NEWS_IMAGES");	
	
				String imagesupport = config.getString("imagesupport");
				if(imagesupport.equalsIgnoreCase("PRODUCTION")){
				AmazonSupportRequests req=new 	AmazonSupportRequests();
				req.requestThroughAmazon(response,FilePath,filename);
				}	
				else{
	        File file = new File(FilePath, filename);
	        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	        Files.copy(file.toPath(), response.getOutputStream());	}
		}	
		if (functionId.equalsIgnoreCase("GET_SHOW_DETAIL")&& actionId.equalsIgnoreCase("GET_FIRSTLINEAR_DATA")){  
			
			String filename = null;
			int filePosition = positionId;
			if (filePosition==0){
				 filename ="position_0.png";
			}
			else if (filePosition==1){
				 filename ="position_1.jpg";
			}
			
			else if (filePosition==2){
				filename ="position_2.jpg";
			}
			else if (filePosition==3){
				filename ="position_3.jpg";
			}
			else if (filePosition==4){
				filename ="position_4.jpg";
			}
			else if (filePosition==5){
				filename ="position_5.jpg";
			}
			else if (filePosition==6){
				filename ="position_6.jpg";
			}
			else if (filePosition==7){
				filename ="position_6.jpg";
			}
			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String FilePath = config.getString("HomeDataShowDetails_GET_FIRSTLINEAR_DATA");	
	
	

				String imagesupport = config.getString("imagesupport");
				if(imagesupport.equalsIgnoreCase("PRODUCTION")){
				AmazonSupportRequests req=new 	AmazonSupportRequests();
				req.requestThroughAmazon(response,FilePath,filename);
				}	
				else{
	        File file = new File(FilePath, filename);
	        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	        Files.copy(file.toPath(), response.getOutputStream());	}
		}	
		if (functionId.equalsIgnoreCase("GET_SHOW_DETAIL")&& actionId.equalsIgnoreCase("GET_FIRSTLINEAR_DATA_STRINGS")){   

			  int geoId =positionId;
			  String StringToSend=null;

				 StringToSend =  "notify"+Integer.toString(geoId);
			
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


					PreparedStatement stmt=con.prepareStatement("select * from home_all_notifys where NOTIFYS_ID=?"); 
					stmt.setString(1,StringToSend);
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String DATA_HEADER = rs.getString("NOTIFY_HEADER");
						String DATA_DETAILS = rs.getString("NOTIFY_DETAILS");
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
		if (functionId.equalsIgnoreCase("GET_SHOW_DETAIL")&& actionId.equalsIgnoreCase("HOME_SEC_LINEARDATA_STRINGS")){   

			  int geoId =positionId;
			  String StringToSend=null;

				 StringToSend =  "notify"+Integer.toString(geoId);
			
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

					PreparedStatement stmt=con.prepareStatement("select * from home_all_notifys where NOTIFYS_ID=?"); 
					stmt.setString(1,StringToSend);
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String DATA_HEADER = rs.getString("NOTIFY_HEADER");
						String DATA_DETAILS = rs.getString("NOTIFY_DETAILS");
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
		if (functionId.equalsIgnoreCase("GET_SHOW_DETAIL")&& actionId.equalsIgnoreCase("HOME_SEC_LINEARDATA")){  
			
			String filename = null;
			int filePosition = positionId;
			if (filePosition==0){
				 filename ="position_0.png";
			}
			else if (filePosition==1){
				 filename ="position_1.jpg";
			}
			
			else if (filePosition==2){
				filename ="position_2.jpg";
			}
			else if (filePosition==3){
				filename ="position_3.jpg";
			}
			else if (filePosition==4){
				filename ="position_4.jpg";
			}
			else if (filePosition==5){
				filename ="position_5.jpg";
			}
			else if (filePosition==6){
				filename ="position_6.jpg";
			}
			else if (filePosition==7){
				filename ="position_6.jpg";
			}
			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			
		    Configuration config = null;
			try {
				config = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			

			String FilePath = config.getString("HomeDataShowDetails_HOME_SEC_LINEARDATA");	
			String imagesupport = config.getString("imagesupport");
			if(imagesupport.equalsIgnoreCase("PRODUCTION")){
			AmazonSupportRequests req=new 	AmazonSupportRequests();
			req.requestThroughAmazon(response,FilePath,filename);
			}	
			else{
        File file = new File(FilePath, filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());	}
		}	
		
		if (functionId.equalsIgnoreCase("GET_SHOW_DETAIL")&& actionId.equalsIgnoreCase("GET_MAIN_HEADER_ADS_STRINGS")){   

			  int geoId =positionId;
			  String StringToSend=null;

				 StringToSend =  "notify"+Integer.toString(geoId);
			
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

					PreparedStatement stmt=con.prepareStatement("select * from HOME_NEWS_NOTIFY_ADS where NOTIFYS_ID=?"); 
					stmt.setString(1,StringToSend);
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String DATA_HEADER = rs.getString("NOTIFY_HEADER");
						String DATA_DETAILS = rs.getString("NOTIFY_DETAILS");
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
		if (functionId.equalsIgnoreCase("GET_SHOW_DETAIL")&& actionId.equalsIgnoreCase("GET_MAIN_HEADER_ADS_IMG")){  
		
			
			String filename = null;
			int filePosition = positionId;
			if (filePosition==0){
				 filename ="position_0.jpg";
			}
			else if (filePosition==1){
				 filename ="position_1.jpg";
			}
			
			else if (filePosition==2){
				filename ="position_2.jpg";
			}
			else if (filePosition==3){
				filename ="position_3.jpg";
			}
			else if (filePosition==4){
				filename ="position_4.jpg";
			}
			else if (filePosition==5){
				filename ="position_5.jpg";
			}
			else if (filePosition==6){
				filename ="position_6.jpg";
			}
			else if (filePosition==7){
				filename ="position_6.jpg";
			}
			
		    Configuration config = null;
			try {
				config = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			

			String FilePath = config.getString("HomeDataShowDetails_GET_MAIN_HEADER_ADS_IMG");	
			String imagesupport = config.getString("imagesupport");
			if(imagesupport.equalsIgnoreCase("PRODUCTION")){
			AmazonSupportRequests req=new 	AmazonSupportRequests();
			req.requestThroughAmazon(response,FilePath,filename);
			}	
			else{
        File file = new File(FilePath, filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());	}
		}
		
		if (functionId.equalsIgnoreCase("GET_SHOW_DETAIL")&& actionId.equalsIgnoreCase("GET_FESTS_LATEST_STRINGS")){   

			 /* int geoId =positionId;
			  String StringToSend=null;

				 StringToSend =  "notify"+Integer.toString(geoId);*/
			
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

					PreparedStatement stmt=con.prepareStatement("select * from college_fests where CLG_ID=?"); 
					stmt.setString(1,extradata);
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String DATA_HEADER = rs.getString("COLLEGE_NAME");
						String DATA_DETAILS = rs.getString("CLG_FULL_INFO");
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
		if (functionId.equalsIgnoreCase("GET_SHOW_DETAIL")&& actionId.equalsIgnoreCase("GET_FESTS_LATEST_IMG")){  
		
			
			String filename = null;
			int filePosition = positionId;
			if (filePosition==0){
				 filename ="position_0.jpg";
			}
			else if (filePosition==1){
				 filename ="position_1.jpg";
			}
			
			else if (filePosition==2){
				filename ="position_2.jpg";
			}
			else if (filePosition==3){
				filename ="position_3.jpg";
			}
			else if (filePosition==4){
				filename ="position_4.jpg";
			}
			else if (filePosition==5){
				filename ="position_5.jpg";
			}
			else if (filePosition==6){
				filename ="position_6.jpg";
			}
			else if (filePosition==7){
				filename ="position_7.jpg";
			}
			else if (filePosition==8){
				filename ="position_8.jpg";
			}
			else if (filePosition==9){
				filename ="position_9.jpg";
			}
		    Configuration config = null;
			try {
				config = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			

			String FilePath = config.getString("FestsLatestUpdates");	
			String imagesupport = config.getString("imagesupport");
			if(imagesupport.equalsIgnoreCase("PRODUCTION")){
			AmazonSupportRequests req=new 	AmazonSupportRequests();
			req.requestThroughAmazon(response,FilePath,filename);
			}	
			else{
        File file = new File(FilePath, filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());	}
		}	
		
		
		
		
		}
	
	

}}