package org.FestsWorkspace;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.AmazonSupport.AmazonSupportRequests;
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



@WebServlet("/FestsLatestUpdates")
public class FestsLatestUpdates extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  
		
		  response.addHeader("Access-Control-Allow-Origin", "*");
	        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
		
		
		
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
		if (functionId.equalsIgnoreCase("GET_LINEAR_DATA")&& actionId.equalsIgnoreCase("CLGS_IN_CITIES")){  
			
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
				String FilePath = imgconfig.getString("img_FestsSlidingImage_Adapter");

				String Driver = config.getString("Driver");
				String ConnectionSpace = config.getString("ConnectionSpace");
				String Db_user = config.getString("Db_user");
				String Db_password = config.getString("Db_password");
				
		        try
		        {
		          Class.forName(Driver);
		          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);
		        PreparedStatement stmt = con.prepareStatement("SELECT * FROM home_fests_notify_ads order by POSITION DESC");
		    
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;
		        while (rs.next())
		        {
					String NOTIFYS_ID =rs.getString("NOTIFYS_ID");
					String NOTIFY_HEADER =rs.getString("NOTIFY_HEADER");
					String NOTIFY_DETAILS =rs.getString("NOTIFY_DETAILS");
					String NOTIFY_IMG =FilePath+rs.getString("NOTIFY_IMG");
					
					
					JSONObject Jsonloc= new JSONObject();
					Jsonloc.put("NOTIFYS_ID", NOTIFYS_ID);
					Jsonloc.put("NOTIFY_HEADER", NOTIFY_HEADER);
					Jsonloc.put("NOTIFY_DETAILS", NOTIFY_DETAILS);
					Jsonloc.put("NOTIFY_IMG", NOTIFY_IMG);
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