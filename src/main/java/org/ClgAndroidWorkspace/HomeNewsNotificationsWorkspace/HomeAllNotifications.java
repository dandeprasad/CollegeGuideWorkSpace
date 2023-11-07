package org.ClgAndroidWorkspace.HomeNewsNotificationsWorkspace;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.AmazonSupport.AmazonSupportRequests;
import org.HomeWorkspace.MyUtility;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;



@WebServlet("/HomeAllNotifications")
public class HomeAllNotifications extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  
		  response.addHeader("Access-Control-Allow-Origin", "*");
	        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
			MyUtility values = new MyUtility();

		
		
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		String workspaceId = geoObject.getString("WORKSPACE_ID");
		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");
		int positionId = geoObject.getInt("POSITION_NO");
		if(workspaceId.equalsIgnoreCase("HOME_WORKSPACE")){
			
		

		  
		if (functionId.equalsIgnoreCase("GET_LINEAR_DATA")&& actionId.equalsIgnoreCase("HOME_NIT_NEWS_NOTIFY_ID")){  
			
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
				

				String FilePath = config.getString("HomeAllNotifications_HOME_NIT_NEWS_NOTIFY_ID");	

	        File file = new File(FilePath, filename);
	        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	        Files.copy(file.toPath(), response.getOutputStream());	
		}	
/*		if (functionId.equalsIgnoreCase("GET_LINEAR_DATA")&& actionId.equalsIgnoreCase("CLGS_IN_CITIES")){  
			
			String filename = null;
			int filePosition = positionId;
			if (filePosition==0){
				 filename ="hyd_charminar.jpg";
			}
			else if (filePosition==1){
				 filename ="chennai_rail.jpg";
			}
			
			else if (filePosition==2){
				filename ="mumbai_gateway.jpg";
			}
			else if (filePosition==3){
				filename ="banglore_shiva.jpg";
			}
			else if (filePosition==4){
				filename ="delhi_redfort.jpg";
			}
			else if (filePosition==5){
				filename ="kerela_houseboat.jpg";
			}
			else if (filePosition==6){
				filename ="bengal_tiger.jpg";
			}
			else if (filePosition==7){
				filename ="jaipur_amberfort.jpg";
			}
			else if (filePosition==8){
				filename ="lucknow_baraimab.jpg";
			}
			else if (filePosition==9){
				filename ="pune_sinhagad.jpg";
			}
			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String FilePath = config.getString("HomeAllNotifications_CLGS_IN_CITIES");	

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
		}*/
		if (functionId.equalsIgnoreCase("GET_LINEAR_DATA")&& actionId.equalsIgnoreCase("CLGS_ALL_APPS")){
			PrintWriter out = response.getWriter(); 
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_home_apps_icons");
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
		        PreparedStatement stmt = con.prepareStatement("SELECT * FROM home_app_icons");
		    
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;
		        JSONObject valuestosend = new JSONObject();
		        while (rs.next())
		        {
					String ICON_ID =rs.getString("ICON_ID");
					String ICON_IMAGE =rs.getString("ICON_IMAGE");
					
					valuestosend.put(ICON_ID,FilePath+ICON_IMAGE);
					
			
		         
		        }
		    
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        response.getWriter().write(valuestosend.toString());
		        con.close();  
		      }
		      catch (Exception e2)
		      {
		        System.out.println(e2);
		      }
		      out.close();
		    }
		if (functionId.equalsIgnoreCase("GET_LINEAR_DATA")&& actionId.equalsIgnoreCase("SPLASH_SCREEN_IMGS")){
			PrintWriter out = response.getWriter(); 
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_home_apps_icons");
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
		        PreparedStatement stmt = con.prepareStatement("SELECT * FROM home_splash_images order by POSITION");
		    
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;
		        JSONObject valuestosend = new JSONObject();
		        while (rs.next())
		        {
					String ICON_ID =rs.getString("ICON_ID");
					String ICON_IMAGE =rs.getString("ICON_IMAGE");
					
					valuestosend.put(ICON_ID,FilePath+ICON_IMAGE);
					
			
		         
		        }
		    
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        response.getWriter().write(valuestosend.toString());
		        con.close();  
		      }
		      catch (Exception e2)
		      {
		        System.out.println(e2);
		      }
		      out.close();
		    }
		if (functionId.equalsIgnoreCase("GET_NEWS_ONLY")&& actionId.equalsIgnoreCase("GET_ALL_NEWS_STRING")){   

			  String geoId ="ALL_NEWS";
			
				PrintWriter out = response.getWriter(); 
		
			  
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
				String FilePath = imgconfig.getString("img_NewsFragmentAdaptor");	

				String Driver = config.getString("Driver");
				String ConnectionSpace = config.getString("ConnectionSpace");
				String Db_user = config.getString("Db_user");
				String Db_password = config.getString("Db_password");
				
		        try
		        {
		          Class.forName(Driver);
		          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);


					PreparedStatement stmt=con.prepareStatement("select * from news_workspace ORDER BY POSITION DESC LIMIT 24"); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String NEWSID =  values.Nullcheck(rs.getString("NEWSID"));
						String NEWS_HEADER = values.Nullcheck(rs.getString("NEWS_HEADER"));
						Date POSTED_DATE=rs.getDate("POSTED_DATE");
						String TAG_TYPE=values.Nullcheck(rs.getString("TAG_TYPE"));
						String TAG_COLOR=values.Nullcheck(rs.getString("TAG_COLOR"));
						String FULL_IMAGE=values.Nullcheck(rs.getString("FULL_IMAGE"));
						String NEWS_DETAILS=values.Nullcheck(rs.getString("NEWS_DETAILS"));
						String NEWS_IMAGE=values.Nullcheck(rs.getString("NEWS_IMAGE"));
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("NEWSID", NEWSID);
						Jsonloc1.put("NEWS_HEADER", NEWS_HEADER);
						Jsonloc1.put("POSTED_DATE", POSTED_DATE.toString());
						Jsonloc1.put("TAG_TYPE", TAG_TYPE);
						Jsonloc1.put("TAG_COLOR", TAG_COLOR);
						Jsonloc1.put("FULL_IMAGE", FULL_IMAGE);
						Jsonloc1.put("NEWS_DETAILS", NEWS_DETAILS);
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
		if (functionId.equalsIgnoreCase("GET_FESTS_NOTIFICATIONS")&& actionId.equalsIgnoreCase("GET_ALL_FESTNEWS_STRING")){   

			  String geoId ="ALL_NEWS";
			
			  
			  PrintWriter out = response.getWriter(); 
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


					PreparedStatement stmt=con.prepareStatement("select * from fests_data where start_date between CURDATE() and  DATE_ADD(CURDATE(), INTERVAL 90 DAY) ORDER BY start_date ASC LIMIT 25"); 
					
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
		
		if (functionId.equalsIgnoreCase("GET_ADMIS_ONLY")&& actionId.equalsIgnoreCase("GET_CLG_ADMIS_STRING")){   

			  String geoId ="ALL_NEWS";
			  PrintWriter out = response.getWriter(); 
			    Configuration imgconfig = null;
				try {
					imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String FilePath = imgconfig.getString("img_admissions");

			  
			  
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


					PreparedStatement stmt=con.prepareStatement("select * from admissions_workspace ORDER BY POSITION DESC LIMIT 8"); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
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
						Jsonloc1.put("LOGO_IMAGE", FilePath+LOGO_IMAGE);
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
		
		if (functionId.equalsIgnoreCase("GET_EXAMS_ONLY") && actionId.equalsIgnoreCase("GET_CLG_EXAMS_STRING")) {
			PrintWriter out = response.getWriter(); 
			String geoId = "ALL_NEWS";

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
			String FilePath = imgconfig.getString("img_HomeExamsFragmentAdaptor");

			String Driver = config.getString("Driver");
			String ConnectionSpace = config.getString("ConnectionSpace");
			String Db_user = config.getString("Db_user");
			String Db_password = config.getString("Db_password");

			try {
				Class.forName(Driver);
				Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);

				PreparedStatement stmt = con
						.prepareStatement("select * from exams_workspace ORDER BY POSITION DESC LIMIT 25");

				ResultSet rs = stmt.executeQuery();

				// String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from
				// nit_list";
				// Statement statement = con.createStatement();
				// ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
				while (rs.next()) {
					String EXAMID = values.Nullcheck(rs.getString("EXAMID"));
					String CLG_NAME = values.Nullcheck(rs.getString("CLG_NAME"));
					String EXAM_DETAILS = values.Nullcheck(rs.getString("EXAM_DETAILS"));
					String POSTED_DATE = rs.getString("POSTED_DATE");
					String EXAM_NAME = values.Nullcheck(rs.getString("EXAM_NAME"));
					String EXAM_DATE = values.Nullcheck(rs.getString("EXAM_START_DATE"));
					String OTHER_IMP_DETAILS = values.Nullcheck(rs.getString("OTHER_IMP_DETAILS"));
					String LOGO_IMAGE = values.Nullcheck(FilePath + rs.getString("LOGO_IMAGE"));
					String REG_START_DATE = values.Nullcheck(rs.getString("REG_START_DATE"));
					String RESULT_DATE =values.Nullcheck( rs.getString("RESULT_DATE"));
					String REG_END_DATE = values.Nullcheck(rs.getString("REG_END_DATE"));

					JSONObject Jsonloc1 = new JSONObject();
					Jsonloc1.put("EXAMID", EXAMID);
					Jsonloc1.put("CLG_NAME", CLG_NAME);
					Jsonloc1.put("EXAM_DETAILS", EXAM_DETAILS);
					Jsonloc1.put("POSTED_DATE", POSTED_DATE);
					Jsonloc1.put("EXAM_NAME", EXAM_NAME);
					Jsonloc1.put("EXAM_DATE", EXAM_DATE);
					Jsonloc1.put("OTHER_IMP_DETAILS", OTHER_IMP_DETAILS);
					Jsonloc1.put("LOGO_IMAGE", LOGO_IMAGE);
					Jsonloc1.put("REG_START_DATE", REG_START_DATE);
					Jsonloc1.put("RESULT_DATE", RESULT_DATE);
					Jsonloc1.put("REG_END_DATE", REG_END_DATE);
					String x1 = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;

				}
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSOBVAL.toString());
				con.close();
			} catch (Exception e2) {
				System.out.println(e2);
			}

			out.close();

		}
		
		if (functionId.equalsIgnoreCase("GET_LINEAR_DATA")&& actionId.equalsIgnoreCase("CLGS_TOP_STATES")){  
			
			String filename = null;
			int filePosition = positionId;
			if (filePosition==0){
				 filename ="andhrapradesh.jpg";
			}
			else if (filePosition==1){
				 filename ="tamilnadu.jpg";
			}
			
			else if (filePosition==2){
				filename ="maharashtra.jpg";
			}
			else if (filePosition==3){
				filename ="karnataka.jpg";
			}
			else if (filePosition==4){
				filename ="telangana.jpg";
			}
			else if (filePosition==5){
				filename ="uttarpradesh.jpg";
			}
			else if (filePosition==6){
				filename ="madhyapradesh.jpg";
			}
			else if (filePosition==7){
				filename ="punjab.jpg";
			}
			else if (filePosition==8){
				filename ="rajasthan.jpg";
			}
			else if (filePosition==9){
				filename ="westbengal.jpg";
			}
			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String FilePath = config.getString("HomeAllNotifications_CLGS_TOP_STATES");	

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
		
		if (functionId.equalsIgnoreCase("GET_LINEAR_DATA")&& actionId.equalsIgnoreCase("STREAM_WISE_CLGS")){  
			
			String filename = null;
			int filePosition = positionId;
			if (filePosition==0){
				 filename ="computerscience.jpg";
			}
			else if (filePosition==1){
				 filename ="electronics.jpg";
			}
			
			else if (filePosition==2){
				filename ="electricaleng.jpg";
			}
			else if (filePosition==3){
				filename ="mechanicaleng.jpg";
			}
			else if (filePosition==4){
				filename ="civileng.jpg";
			}
			else if (filePosition==5){
				filename ="chemicaleng.jpg";
			}
			else if (filePosition==6){
				filename ="biotechnology.jpg";
			}
			else if (filePosition==7){
				filename ="metallurgicaleng.jpg";
			}
			else if (filePosition==8){
				filename ="mineraleng.jpg";
			}
			else if (filePosition==9){
				filename ="aerospaceeng.jpg";
			}
			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String FilePath = config.getString("HomeAllNotifications_STREAM_WISE_CLGS");	

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
		
		if (functionId.equalsIgnoreCase("GET_LINEAR_DATA")&& actionId.equalsIgnoreCase("RATING_WISE_CLGS")){  
			
			String filename = null;
			int filePosition = positionId;
			if (filePosition==0){
				 filename ="computerscience.jpg";
			}
			else if (filePosition==1){
				 filename ="electronics.jpg";
			}
			
			else if (filePosition==2){
				filename ="electricaleng.jpg";
			}
			else if (filePosition==3){
				filename ="mechanicaleng.jpg";
			}
			else if (filePosition==4){
				filename ="civileng.jpg";
			}
			else if (filePosition==5){
				filename ="chemicaleng.jpg";
			}
			else if (filePosition==6){
				filename ="biotechnology.jpg";
			}
			else if (filePosition==7){
				filename ="metallurgicaleng.jpg";
			}
			else if (filePosition==8){
				filename ="mineraleng.jpg";
			}
			else if (filePosition==9){
				filename ="aerospaceeng.jpg";
			}
			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String FilePath = config.getString("HomeAllNotifications_RATING_WISE_CLGS");	

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
		if (functionId.equalsIgnoreCase("GET_LINEAR_DATA")&& actionId.equalsIgnoreCase("MAIN_ADS_HEADER_ID")){
			PrintWriter out = response.getWriter(); 
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_SlidingImage_Adapter");
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
		        PreparedStatement stmt = con.prepareStatement("SELECT * FROM home_news_notify_ads order by POSITION DESC");
		    
		        ResultSet rs = stmt.executeQuery();
		        MyUtility danchcek= new MyUtility();
		        int inc = 0;
		        while (rs.next())
		        {
					String NOTIFYS_ID =danchcek.Nullcheck(rs.getString("NOTIFYS_ID"));
					String NOTIFY_HEADER =danchcek.Nullcheck(rs.getString("NOTIFY_HEADER"));
					String NOTIFY_DETAILS =danchcek.Nullcheck(rs.getString("NOTIFY_DETAILS"));
					String NOTIFY_IMG =danchcek.Nullcheck(rs.getString("NOTIFY_IMG"));
					String AD_TYPE  =danchcek.Nullcheck(rs.getString("AD_TYPE"));
					String TYPE_ID  =danchcek.Nullcheck(rs.getString("TYPE_ID"));
					String FEST_IMAGE  =danchcek.Nullcheck(rs.getString("FEST_IMAGE"));
					String FEST_CLG_NAME  =danchcek.Nullcheck(rs.getString("FEST_CLG_NAME"));
					String NEWS_HEADER  =danchcek.Nullcheck(rs.getString("NEWS_HEADER"));
					String NEWS_DETAILS  =danchcek.Nullcheck(rs.getString("NEWS_DETAILS"));
					String NEWS_IMAGE  =danchcek.Nullcheck(rs.getString("NEWS_IMAGE"));
					
					
					
					JSONObject Jsonloc= new JSONObject();
					Jsonloc.put("NOTIFYS_ID", NOTIFYS_ID);
					Jsonloc.put("NOTIFY_HEADER", NOTIFY_HEADER);
					Jsonloc.put("NOTIFY_DETAILS", NOTIFY_DETAILS);
					Jsonloc.put("NOTIFY_IMG", FilePath+NOTIFY_IMG);
					Jsonloc.put("AD_TYPE", AD_TYPE);
					Jsonloc.put("TYPE_ID", TYPE_ID);
					Jsonloc.put("FEST_IMAGE", FEST_IMAGE);
					Jsonloc.put("FEST_CLG_NAME", FEST_CLG_NAME);
					Jsonloc.put("NEWS_HEADER", NEWS_HEADER);
					Jsonloc.put("NEWS_DETAILS", NEWS_DETAILS);
					Jsonloc.put("NEWS_IMAGE", NEWS_IMAGE);
		          String x1 = Integer.toString(inc);
		          JSOBVAL.put(x1, Jsonloc);
		          inc++;
		        }
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        response.getWriter().write(JSOBVAL.toString());
		        con.close();  
		      }
		      catch (Exception e2)
		      {
		        System.out.println(e2);
		      }
		      out.close();
		    }	
		}
	
	

}}