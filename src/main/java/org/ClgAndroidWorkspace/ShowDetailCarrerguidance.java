package org.ClgAndroidWorkspace;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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




@WebServlet("/ShowDetailCarrerguidance")
public class ShowDetailCarrerguidance extends HttpServlet {

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
		//int positionId = geoObject.getInt("POSITION_NO");
		if(workspaceId.equalsIgnoreCase("HOME_WORKSPACE")){
			
		
			
			if (functionId.equalsIgnoreCase("CARRER_GUIDANCE")){   


			  String StringToSend=actionId;
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


					PreparedStatement stmt=con.prepareStatement("select CARRER_INFORMATION from CARRER_GUIDANCE_DATA where CARRER_ID=?"); 
					stmt.setString(1,StringToSend);
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String DATA_DETAILS = rs.getString("CARRER_INFORMATION");
						//String DATA_HEADER = rs.getString("NEWS_HEADER");
						//String NEWS_HEADER = rs.getString("NEWS_HEADER");
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("DATA_DETAILS", DATA_DETAILS);
						//Jsonloc1.put("DATA_HEADER", DATA_HEADER);
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
			  
			
		
		
		}
	
	

}}