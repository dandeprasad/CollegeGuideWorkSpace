package org.NitsWorkspace;


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




@WebServlet("/NitLogosData")
public class NitLogosData extends HttpServlet {

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
			
		

		  
		if (functionId.equalsIgnoreCase("NIT_LOGOS_DATA")&& actionId.equalsIgnoreCase("GET_NIT_LOGOS_DATA")){  
			
			String filename = null;
			int filePosition = positionId;
			if (filePosition==0){
				 filename ="nit_ag.jpg";
			}
			else if (filePosition==1){
				 filename ="nit_allahabad.jpg";
			}
			
			else if (filePosition==2){
				filename ="nit_andhra.jpg";
			}
			else if (filePosition==3){
				filename ="nit_ar_prdsh.jpg";
			}
			else if (filePosition==4){
				filename ="nit_bhopal.jpg";
			}
			else if (filePosition==5){
				filename ="nit_ca.jpg";
			}
			else if (filePosition==6){
				filename ="nit_del.jpg";
			}
			else if (filePosition==7){
				filename ="nit_dgpr.jpg";
			}
			else if (filePosition==8){
				filename ="nit_goa.jpg";
			}
			else if (filePosition==9){
				filename ="nit_hampr.jpg";
			}
			else if (filePosition==10){
				filename ="nit_jaipur.jpg";
			}
			else if (filePosition==11){
				filename ="nit_jal.jpg";
			}
			else if (filePosition==12){
				filename ="nit_jamspr.jpg";
			}
			else if (filePosition==13){
				filename ="nit_kuru.jpg";
			}
			
			else if (filePosition==14){
				filename ="nit_mani.jpg";
			}
			else if (filePosition==15){
				filename ="nit_mega.jpg";
			}
			else if (filePosition==16){
				filename ="nit_miz.jpg";
			}
			else if (filePosition==17){
				filename ="nit_naglnd.jpg";
			}
			else if (filePosition==18){
				filename ="nit_nagpur.jpg";
			}
			else if (filePosition==19){
				filename ="nit_patna.jpg";
			}
			else if (filePosition==20){
				filename ="nit_py.jpg";
			}
			else if (filePosition==21){
				filename ="nit_raipur.jpg";
			}
			else if (filePosition==22){
				filename ="nit_rourk.jpg";
			}
			else if (filePosition==23){
				filename ="nit_sikkim.jpg";
			}
			else if (filePosition==24){
				filename ="nit_silchar.jpg";
			}
			else if (filePosition==25){
				filename ="nit_sringr.jpg";
			}
			else if (filePosition==26){
				filename ="nit_surat.jpg";
			}
			else if (filePosition==27){
				filename ="nit_surutkal.jpg";
			}
			else if (filePosition==28){
				filename ="nit_trichy.jpg";
			}
			else if (filePosition==29){
				filename ="nit_uttarak.jpg";
			}
			else if (filePosition==30){
				filename ="nit_wangl.jpg";
			}
			else if (filePosition==31){
				filename ="nit_dgpr.jpg";
			}
			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String FilePath = config.getString("NitLogosData_GET_NIT_LOGOS_DATA");	

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
				

				String FilePath = config.getString("HomeAllNotifications_LISTALLNNOTIFICATIONSID");	

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
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

				String FilePath = config.getString("HomeAllNotifications_MAIN_ADS_HEADER_ID");	

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