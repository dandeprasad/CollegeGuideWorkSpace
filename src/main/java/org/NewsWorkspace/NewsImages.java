package org.NewsWorkspace;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/NewsImages")
public class NewsImages extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		String workspaceId = geoObject.getString("WORKSPACE_ID");
		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");
		int positionId = geoObject.getInt("POSITION_NO");
		int SnoOfElements = geoObject.getInt("SROW_INDEX");
		int EnoOfElements = geoObject.getInt("EROW_INDEX");
	//	String reqTypetesrrt = (String)request.getParameter("dandeRequest");
	
			String valId=callposition(positionId);
			if(valId!=null){
                String   Imageid =valId;
		
		//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
	    Configuration config = null;
		try {
			config = new PropertiesConfiguration("ImagesConfig.properties");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		

		String FilePath = config.getString("NewsImages");	
		String imagesupport = config.getString("imagesupport");
		if(imagesupport.equalsIgnoreCase("PRODUCTION")){
		AmazonSupportRequests req=new 	AmazonSupportRequests();
		req.requestThroughAmazon(response,FilePath,Imageid);
		}	
		else{
    File file = new File(FilePath, Imageid);
    response.setHeader("Content-Type", getServletContext().getMimeType(Imageid));
    response.setHeader("Content-Length", String.valueOf(file.length()));
    response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
    Files.copy(file.toPath(), response.getOutputStream());	}
		
	}
	}
	private String callposition(int reqTypetesrrt) {
		// TODO Auto-generated method stub
		String Imageid = null;

			int StringToSend =  reqTypetesrrt;
		
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
				PreparedStatement stmt=con.prepareStatement("select NEWSID from news_workspace where POSITION=?"); 
				stmt.setInt(1,StringToSend);
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
				while (rs.next()) {
				Imageid = rs.getString("NEWSID");
					
				}
			    con.close();
	}catch (Exception e2) {System.out.println(e2);}
			return Imageid;  

}}