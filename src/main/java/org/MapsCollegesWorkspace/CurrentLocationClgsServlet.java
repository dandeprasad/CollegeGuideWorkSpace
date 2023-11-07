package org.MapsCollegesWorkspace;

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




@WebServlet("/CurrentLocationClgsServlet")
public class CurrentLocationClgsServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter(); 
		
	    Configuration imgconfig = null;
		try {
			imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String FilePath = imgconfig.getString("img_CutoffSecondFragmentAdaptor");
	
		
		
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		
		String functionId = geoObject.getString("FUNCTION_ID");
		
		double userLatitude = geoObject.getDouble("USER_LAT");
		double userLongitude = geoObject.getDouble("USER_LNG");
		
		      if (functionId.equalsIgnoreCase("CURRENT_LOC_CLG_REQ")){
		          
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
			          PreparedStatement stmt=con.prepareStatement("SELECT * FROM (SELECT CLG_WEBSITE,( 3959 * ACOS(COS( RADIANS(?) ) * COS( RADIANS( CLG_LAT ) ) * COS( RADIANS( CLG_LNG ) - RADIANS(?) ) + sin( RADIANS(?) ) * sin( RADIANS( CLG_LAT ) ) ) ) DISTANCE,CLG_NAME,CLG_LAT,CLG_LNG,CLG_ID,CLG_PLACE,CLG_LOGO,CLG_NUMBER FROM ALL_CLGS_VIEW) LOCATION_TABLE WHERE DISTANCE<=100");

						//PreparedStatement stmt=con.prepareStatement("select * from nit_list where NIT_ID=?"); 
						stmt.setDouble(1,userLatitude);
						stmt.setDouble(2,userLongitude);
						stmt.setDouble(3,userLatitude);
						ResultSet rs=stmt.executeQuery(); 
	int inc = 0;
		while (rs.next()) {
			String NAME =rs.getString("CLG_NAME");
			String CLG_NUM = rs.getString("CLG_NUMBER");
			String PLACE = rs.getString("CLG_PLACE");
			String LAT = rs.getString("CLG_LAT");
			String LNG = rs.getString("CLG_LNG");
			String WEBSITE =  rs.getString("CLG_WEBSITE");
			String NIT_ID = rs.getString("CLG_ID");
			String CLG_LOGO = FilePath+rs.getString("CLG_LOGO");

			JSONObject Jsonloc= new JSONObject();
			Jsonloc.put("CLG_NAME", NAME);
			Jsonloc.put("CLG_NUM", CLG_NUM);
			Jsonloc.put("CLG_PLACE", PLACE);
			Jsonloc.put("CLG_LAT", LAT);
			Jsonloc.put("CLG_LNG", LNG);
			Jsonloc.put("CLG_WEBSITE", WEBSITE);
			Jsonloc.put("CLG_ID", NIT_ID);
			Jsonloc.put("CLG_LOGO", CLG_LOGO);

			String x1  = Integer.toString(inc);
			JSOBVAL.put(x1, Jsonloc);
			inc++;
			
		}
		  response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(JSOBVAL.toString());     
		    con.close();   
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close(); 
		
	}
		
	
	}
	
	

}