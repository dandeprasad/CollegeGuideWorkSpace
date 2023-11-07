package org.ClgAndroidWorkspace.CollegeImagesWorkspace;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.*;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;




@WebServlet("/CollegeImages")
public class CollegeImages extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  
		//PrintWriter out = response.getWriter(); 
		
		Properties prop = new Properties();
	      String contextPath = request.getContextPath();
String test = request.getRequestURI() + request.getRequestURL();
InputStream input = null;
//File file1 = new File("pathconfig.properties");
//FileInputStream fileInput = new FileInputStream(file1);
//input = new FileInputStream("pathconfig.properties");
//prop.load(input);
//String x = prop.getProperty("test");
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		String workspaceId = geoObject.getString("WORKSPACE_ID");
		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");
		
		if(workspaceId.equalsIgnoreCase("CLGS_WORKSPACE")){
			
		

		  
		if (functionId.equalsIgnoreCase("GET_CLG_HEADER_IMG")){  
			//&& actionId.equalsIgnoreCase("GET_CLG_HEADER_NIT_NIT_ALLAHABAD")
			String filename = null;

        filename = actionId + ".jpg";
			

			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
     //   \CollegeGuideWorkSpace\src\main\webapp\images

        File file = new File("C:/GradleProject/CLGS_PROJECT/NITSlogos/NITHEADER", filename);
	        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	        Files.copy(file.toPath(), response.getOutputStream());	
		}	
		if (functionId.equalsIgnoreCase("GET_CLG_PLACEMENTS")){ 
			//&& actionId.equalsIgnoreCase("GET_CLG_HEADER_NIT_NIT_ALLAHABAD")
			String filename = null;

				 filename ="position_0.png";
			

			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			

	        File file = new File("C:/GradleProject/CLGS_PROJECT/HomeLatestNewsUpdates", filename);
	        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	        Files.copy(file.toPath(), response.getOutputStream());	
		}
		if (functionId.equalsIgnoreCase("GET_CLG_QUESTIONS")){  
			//&& actionId.equalsIgnoreCase("GET_CLG_HEADER_NIT_NIT_ALLAHABAD")
			String filename = null;

				 filename ="position_0.png";
			

			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			

	        File file = new File("C:/GradleProject/CLGS_PROJECT/HomeLatestNewsUpdates", filename);
	        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	        Files.copy(file.toPath(), response.getOutputStream());	
		}
		if (functionId.equalsIgnoreCase("GET_CLG_RANKS")){  
			//&& actionId.equalsIgnoreCase("GET_CLG_HEADER_NIT_NIT_ALLAHABAD")
			String filename = null;

        filename = actionId + ".jpg";
			

			//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			

        File file = new File("C:/GradleProject/CLGS_PROJECT/NITSlogos/NITRANKINGS", filename);
	        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	        Files.copy(file.toPath(), response.getOutputStream());	
		}
		 if (functionId.equalsIgnoreCase("GET_CLG_DESC_DATA"))
	      {
	        String StringToSend = null;
	        
	        StringToSend = actionId;
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
	          PreparedStatement stmt = con.prepareStatement("select NIT_DESC from nit_list where NIT_ID=?");
	          stmt.setString(1, StringToSend);
	          ResultSet rs = stmt.executeQuery();
	          
	          int inc = 0;
	          while (rs.next())
	          {
	            String DATA_DETAILS = rs.getString("NIT_DESC");
	            
	            JSONObject Jsonloc1 = new JSONObject();
	            Jsonloc1.put("DATA_DETAILS", DATA_DETAILS);
	            
	            String x1 = Integer.toString(inc);
	            JSOBVAL.put(x1, Jsonloc1);
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
	      }
		}
	
	

}}