package org.NitsWorkspace;

import java.io.IOException;
import java.io.PrintWriter;
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




@WebServlet("/NitStartServlet")
public class NitStartServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter(); 
		
		
		JSONObject JSOBVAL= new JSONObject();	


		  
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
		          

		String selectTableSQL = "SELECT * from nit_list";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(selectTableSQL);
	int inc = 0;
		while (rs.next()) {
			
			String NAME = rs.getString("CLG_NAME");
			String PLACE = rs.getString("CLG_PLACE");
			String LAT = rs.getString("CLG_LAT");
			String LNG = rs.getString("CLG_LNG");
			String WEBSITE = rs.getString("CLG_WEBSITE");
			String NIT_ID = rs.getString("CLG_ID");
			String TRAVEL_INFO_AIR =rs.getString("TRAVEL_INFO_AIR");
			String	TRAVEL_INFO_RAIL=rs.getString("TRAVEL_INFO_RAIL");
			String	TRAVEL_INFO_ROAD=rs.getString("TRAVEL_INFO_ROAD");
			JSONObject Jsonloc= new JSONObject();
			Jsonloc.put("CLG_NAME", NAME);
			Jsonloc.put("CLG_PLACE", PLACE);
			Jsonloc.put("CLG_LAT", LAT);
			Jsonloc.put("CLG_LNG", LNG);
			Jsonloc.put("CLG_WEBSITE", WEBSITE);
			Jsonloc.put("CLG_ID", NIT_ID);
			Jsonloc.put("TRAVEL_INFO_AIR", TRAVEL_INFO_AIR);
			Jsonloc.put("TRAVEL_INFO_RAIL", TRAVEL_INFO_RAIL);
			Jsonloc.put("TRAVEL_INFO_ROAD", TRAVEL_INFO_ROAD);
			String x1  = Integer.toString(inc);
			JSOBVAL.put(x1, Jsonloc);
			inc++;
			
		}
		  response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(JSOBVAL.toString());     
		        
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close(); 
		
	}}
	
	
