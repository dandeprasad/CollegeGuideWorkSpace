package org.FestsWorkspace;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

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
import org.json.JSONObject;




@WebServlet("/FestAllNotifications")
public class FestAllNotifications extends HttpServlet {

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
		if (functionId.equalsIgnoreCase("GET_LINEAR_DATA")&& actionId.equalsIgnoreCase("LISTALLNNOTIFICATIONSID")){  
			String filename = null;
			int filePosition = positionId;
			if (filePosition==0){
				 filename ="fests_nits.png";
			}
			else if (filePosition==1){
				 filename ="fests_iits.png";
			}
			
			else if (filePosition==2){
				filename ="fests_deemed.png";
			}
			else if (filePosition==3){
				filename ="state_colleges.png";
			}
			else if (filePosition==4){
				filename ="fests_cse.png";
			}
			else if (filePosition==5){
				filename ="fests_ece.png";
			}
			else if (filePosition==6){
				filename ="fests_eee.png";
			}
			else if (filePosition==7){
				filename ="fests_mech.png";
			}
			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			
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
		if (functionId.equalsIgnoreCase("GET_LINEAR_DATA")&& actionId.equalsIgnoreCase("MAIN_ADS_HEADER_ID")){  
			
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

			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String FilePath = config.getString("FestAllNotifications_MAIN_ADS_HEADER_ID");	

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