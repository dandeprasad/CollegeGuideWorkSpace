package org.NitsWorkspace;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.HomeWorkspace.MyUtility;
import org.HomeWorkspace.UserDataMaintance;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;

import mailService.SMSEmailUsingGMailSMTP;




@WebServlet("/CollegesStartServlet")
public class CollegesStartServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter(); 
		
		
	    Configuration imgconfiglogos = null;
		try {
			imgconfiglogos = new PropertiesConfiguration("ImagesConfig.properties");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String FilePathlogo = imgconfiglogos.getString("img_CutoffSecondFragmentAdaptor");
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		
		String functionId = geoObject.getString("FUNCTION_ID");
		
		if (functionId.equalsIgnoreCase("TRAINSDATA_REQUEST")){   

		  String geoId = geoObject.getString("COLLEGE_ID");
		
		  
		  
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


				PreparedStatement stmt=con.prepareStatement("select * from nit_list where CLG_ID=?"); 
				stmt.setString(1,geoId);
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
				while (rs.next()) {
					String TRAVEL_INFO_RAIL = rs.getString("TRAVEL_INFO_RAIL");
					
					JSONObject Jsonloc1= new JSONObject();
					Jsonloc1.put("TRAVEL_INFO_RAIL", TRAVEL_INFO_RAIL);
					
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
		  
		

	//	List<String> list = new ArrayList<String>();
	
		
		else	if (functionId.equalsIgnoreCase("BUSESDATA_REQUEST")){   

			  String geoId = geoObject.getString("COLLEGE_ID");
			
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

					PreparedStatement stmt=con.prepareStatement("select TRAVEL_INFO_ROAD from nit_list where CLG_ID=?"); 
					stmt.setString(1,geoId);
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String TRAVEL_INFO_ROAD = rs.getString("TRAVEL_INFO_ROAD");
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("TRAVEL_INFO_ROAD", TRAVEL_INFO_ROAD);
						
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
		
		
		else	if (functionId.equalsIgnoreCase("AIRDATA_REQUEST")){   

			  String geoId = geoObject.getString("COLLEGE_ID");
			
			  
			  
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


					PreparedStatement stmt=con.prepareStatement("select TRAVEL_INFO_AIR from nit_list where CLG_ID=?"); 
					stmt.setString(1,geoId);
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String TRAVEL_INFO_AIR = rs.getString("TRAVEL_INFO_AIR");
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("TRAVEL_INFO_AIR", TRAVEL_INFO_AIR);
						
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
		
		
		else if (functionId.equalsIgnoreCase("CLG_SEARCH_REQUEST")){
	          
			String datafromserver = geoObject.getString("DATA_TO_SERVER");
			
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

			       //   SELECT * FROM ALL_CLGS_VIEW  WHERE CLG_NAME like '%datafromserver%';
						PreparedStatement stmt=con.prepareStatement("SELECT * FROM ALL_CLGS_VIEW  WHERE CLG_NAME like '%"+datafromserver+"%'"); 
						
						ResultSet rs=stmt.executeQuery(); 
						
	int inc = 0;
		while (rs.next()) {
			String NAME = rs.getString("CLG_NAME");
			String PLACE = rs.getString("CLG_PLACE");
			String CLG_NUM = rs.getString("CLG_NUMBER");
			String CLG_LOGO = rs.getString("CLG_LOGO");
			String LAT = rs.getString("CLG_LAT");
			String LNG = rs.getString("CLG_LNG");
			String WEBSITE = rs.getString("CLG_WEBSITE");
			String NIT_ID = rs.getString("CLG_ID");

			JSONObject Jsonloc= new JSONObject();
			Jsonloc.put("CLG_NAME", NAME);
			Jsonloc.put("CLG_NUM", CLG_NUM);
			Jsonloc.put("CLG_PLACE", PLACE);
			Jsonloc.put("CLG_LAT", LAT);
			Jsonloc.put("CLG_LNG", LNG);
			Jsonloc.put("CLG_LOGO",FilePathlogo+ CLG_LOGO);
			Jsonloc.put("CLG_PLACE", PLACE);
			Jsonloc.put("CLG_WEBSITE", WEBSITE);
			Jsonloc.put("CLG_ID", NIT_ID);

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
		          
		else if (functionId.equalsIgnoreCase("ALLNITS_REQUEST")){
		          
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
			String CLG_NUM = rs.getString("CLG_NUMBER");
			String PLACE = rs.getString("CLG_PLACE");
			String CLG_LOGO = rs.getString("CLG_LOGO");
			String LAT = rs.getString("CLG_LAT");
			String LNG = rs.getString("CLG_LNG");
			String WEBSITE = rs.getString("CLG_WEBSITE");
			String NIT_ID = rs.getString("CLG_ID");
			String TRAVEL_INFO_AIR =rs.getString("TRAVEL_INFO_AIR");
			String	TRAVEL_INFO_RAIL=rs.getString("TRAVEL_INFO_RAIL");
			String	TRAVEL_INFO_ROAD=rs.getString("TRAVEL_INFO_ROAD");
			JSONObject Jsonloc= new JSONObject();
			Jsonloc.put("CLG_NUM", CLG_NUM);
			Jsonloc.put("CLG_NAME", NAME);
			Jsonloc.put("CLG_PLACE", PLACE);
			Jsonloc.put("CLG_LAT", LAT);
			Jsonloc.put("CLG_LNG", LNG);
			Jsonloc.put("CLG_LOGO",FilePathlogo+ CLG_LOGO);
			Jsonloc.put("CLG_PLACE", PLACE);
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
		    con.close();   
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close(); 
		
	}
		else if (functionId.equalsIgnoreCase("ALLIIITS_REQUEST")){
	          
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

		String selectTableSQL = "SELECT * from iiit_list";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(selectTableSQL);
	int inc = 0;
	  MyUtility val  = new MyUtility();
		while (rs.next()) {
			String NAME =val.Nullcheck( rs.getString("CLG_NAME"));
			String CLG_NUM = val.Nullcheck( rs.getString("CLG_NUMBER"));
			String PLACE =val.Nullcheck(  rs.getString("CLG_PLACE"));
			String CLG_LOGO = val.Nullcheck( rs.getString("CLG_LOGO"));
			String LAT = val.Nullcheck( rs.getString("CLG_LAT"));
			String LNG =val.Nullcheck(  rs.getString("CLG_LNG"));
			String WEBSITE =val.Nullcheck(  rs.getString("CLG_WEBSITE"));
			String NIT_ID = val.Nullcheck( rs.getString("CLG_ID"));
			String TRAVEL_INFO_AIR =val.Nullcheck( rs.getString("TRAVEL_INFO_AIR"));
			String	TRAVEL_INFO_RAIL=val.Nullcheck( rs.getString("TRAVEL_INFO_RAIL"));
			String	TRAVEL_INFO_ROAD=val.Nullcheck( rs.getString("TRAVEL_INFO_ROAD"));
			JSONObject Jsonloc= new JSONObject();
			Jsonloc.put("CLG_NAME", NAME);
			Jsonloc.put("CLG_NUM", CLG_NUM);
			Jsonloc.put("CLG_PLACE", PLACE);
			Jsonloc.put("CLG_LAT", LAT);
			Jsonloc.put("CLG_LNG", LNG);
			Jsonloc.put("CLG_LOGO",FilePathlogo+ CLG_LOGO);
			Jsonloc.put("CLG_PLACE", PLACE);
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
		    con.close();   
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close(); 
		
	}	
		else if (functionId.equalsIgnoreCase("USERSACTIVITY_CLG_REQUEST")){
			 String geoId = geoObject.getString("EXTRA_COLLEGE_ID");
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
		          PreparedStatement stmt = con.prepareStatement("SELECT * FROM ALL_CLGS_VIEW where CLG_ID=?");
			        stmt.setString(1, geoId);
			        ResultSet rs = stmt.executeQuery();
	int inc = 0;
		while (rs.next()) {
			String NAME = rs.getString("CLG_NAME");
			String CLG_NUM = rs.getString("CLG_NUMBER");
			String PLACE = rs.getString("CLG_PLACE");
			String CLG_LOGO = rs.getString("CLG_LOGO");
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
			Jsonloc.put("CLG_NUM", CLG_NUM);
			Jsonloc.put("CLG_LNG", LNG);
			Jsonloc.put("CLG_LOGO",FilePathlogo+ CLG_LOGO);
			Jsonloc.put("CLG_PLACE", PLACE);
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
		    con.close();   
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close(); 
		
	}
		else if (functionId.equalsIgnoreCase("USERS_FILTER_REQUEST")){
			 String geoId = geoObject.getString("EXTRA_COLLEGE_ID");
			 String state="";
			 String city="";
			 String depart="";
		
			 JSONObject dande = new  JSONObject(geoId);
			 
		if(!("".equalsIgnoreCase(dande.get("STATES").toString()))){
			JSONArray val1=  new JSONArray(dande.get("STATES").toString());
			if(val1.length()!=0){
				 state="CLG_STATE in ("+ "'"+state+ val1.get(0).toString()+"'";
			for (int i = 1; i < val1.length(); i++) {
				state = state+ ",'"+val1.get(i).toString()+"'";
			}
			state=state+")";}
		}
		if(!("".equalsIgnoreCase(dande.get("CITIES").toString()))){
			JSONArray val2= new JSONArray( dande.get("CITIES").toString());
			if(val2.length()!=0){
				city="CLG_PLACE in ("+ "'"+city+ val2.get(0).toString()+"'";
			for (int i = 1; i < val2.length(); i++) {
				city = city+ ",'"+val2.get(i).toString()+"'";
			}
			city=city+")";}
		}
		if(!("".equalsIgnoreCase(dande.get("STREAMS").toString()))){
			JSONArray val3=  new JSONArray( dande.get("STREAMS").toString());
			if(val3.length()!=0){
				depart="DEPARTMENTS in ("+ "'"+depart+ val3.get(0).toString()+"'";
			for (int i = 1; i < val3.length(); i++) {
				depart = depart+",'"+ val3.get(i).toString()+"'";
			}
			depart=depart+")";}
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
		          PreparedStatement stmt = con.prepareStatement("SELECT * FROM ALL_CLGS_VIEW where "+state +" OR "+city);
			        ResultSet rs = stmt.executeQuery();
	int inc = 0;
		while (rs.next()) {
			String NAME = rs.getString("CLG_NAME");
			String CLG_NUM = rs.getString("CLG_NUMBER");
			String PLACE = rs.getString("CLG_PLACE");
			String CLG_LOGO = rs.getString("CLG_LOGO");
			String LAT = rs.getString("CLG_LAT");
			String LNG = rs.getString("CLG_LNG");
			String WEBSITE = rs.getString("CLG_WEBSITE");
			String NIT_ID = rs.getString("CLG_ID");
			String TRAVEL_INFO_AIR =rs.getString("TRAVEL_INFO_AIR");
			String	TRAVEL_INFO_RAIL=rs.getString("TRAVEL_INFO_RAIL");
			String	TRAVEL_INFO_ROAD=rs.getString("TRAVEL_INFO_ROAD");
			JSONObject Jsonloc= new JSONObject();
			Jsonloc.put("CLG_NAME", NAME);
			Jsonloc.put("CLG_NUM", CLG_NUM);
			Jsonloc.put("CLG_PLACE", PLACE);
			Jsonloc.put("CLG_LAT", LAT);
			Jsonloc.put("CLG_LNG", LNG);
			Jsonloc.put("CLG_LOGO",FilePathlogo+ CLG_LOGO);
			Jsonloc.put("CLG_PLACE", PLACE);
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
		    con.close();   
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close(); 
		
	}
		else if (functionId.equalsIgnoreCase("ENVIRONMENT_FILTER")){
			 String geoId = geoObject.getString("EXTRA_COLLEGE_ID");
			 String state="";
			 String city="";
			 String depart="";
		

			JSONArray val1=  new JSONArray(geoId);
			if(val1.length()!=0){
				 state="CLG_STATE in ("+ "'"+state+ val1.get(0).toString()+"'";
			for (int i = 1; i < val1.length(); i++) {
				state = state+ ",'"+val1.get(i).toString()+"'";
			}
			state=state+")";}
		
		
	

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
		          PreparedStatement stmt = con.prepareStatement("SELECT * FROM ALL_CLGS_VIEW where "+state +" OR "+city);
			        ResultSet rs = stmt.executeQuery();
	int inc = 0;
		while (rs.next()) {
			String NAME = rs.getString("CLG_NAME");
			String PLACE = rs.getString("CLG_PLACE");
			String CLG_LOGO = rs.getString("CLG_LOGO");
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
			Jsonloc.put("CLG_LOGO",FilePathlogo+ CLG_LOGO);
			Jsonloc.put("CLG_PLACE", PLACE);
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
		    con.close();   
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close(); 
		
	}
		else if (functionId.equalsIgnoreCase("CLG_STATE_FILTER")){
			 
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
		          PreparedStatement stmt = con.prepareStatement("SELECT DISTINCT(CLG_STATE) FROM ALL_CLGS_VIEW ");
			        ResultSet rs = stmt.executeQuery();

	ArrayList<String> dandeval = new ArrayList<String>();
		while (rs.next()) {
			dandeval.add(rs.getString("CLG_STATE"));

			
		}
		JSOBVAL.put(Integer.toString(0), dandeval);
		  response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(JSOBVAL.toString());     
		    con.close();   
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close(); 
		
	}
		else if (functionId.equalsIgnoreCase("CLG_CITIES_FILTER")){
			 
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
		          PreparedStatement stmt = con.prepareStatement("SELECT DISTINCT(CLG_PLACE) FROM ALL_CLGS_VIEW ");
			        ResultSet rs = stmt.executeQuery();

	ArrayList<String> dandeval = new ArrayList<String>();
		while (rs.next()) {
			dandeval.add(rs.getString("CLG_PLACE"));

			
		}
		JSOBVAL.put(Integer.toString(0), dandeval);
		  response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(JSOBVAL.toString());     
		    con.close();   
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close(); 
		
	}
		else if (functionId.equalsIgnoreCase("ALL_DEEMED_REQUEST")){
        
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

	String selectTableSQL = "SELECT * from deemed_univ_list";
	Statement statement = con.createStatement();
	ResultSet rs = statement.executeQuery(selectTableSQL);
	  MyUtility val  = new MyUtility();
int inc = 0;
	while (rs.next()) {
		String NAME =val.Nullcheck( rs.getString("CLG_NAME"));
		String CLG_NUM =val.Nullcheck( rs.getString("CLG_NUMBER"));
		String PLACE = val.Nullcheck(rs.getString("CLG_PLACE"));
		String CLG_LOGO =val.Nullcheck( rs.getString("CLG_LOGO"));
		String LAT = val.Nullcheck(rs.getString("CLG_LAT"));
		String LNG =val.Nullcheck( rs.getString("CLG_LNG"));
		String WEBSITE =val.Nullcheck( rs.getString("CLG_WEBSITE"));
		String NIT_ID = val.Nullcheck(rs.getString("CLG_ID"));
		String TRAVEL_INFO_AIR =val.Nullcheck(rs.getString("TRAVEL_INFO_AIR"));
		String	TRAVEL_INFO_RAIL=val.Nullcheck(rs.getString("TRAVEL_INFO_RAIL"));
		String	TRAVEL_INFO_ROAD=val.Nullcheck(rs.getString("TRAVEL_INFO_ROAD"));
		JSONObject Jsonloc= new JSONObject();
		Jsonloc.put("CLG_NAME", NAME);
		Jsonloc.put("CLG_PLACE", PLACE);
		Jsonloc.put("CLG_NUM", CLG_NUM);
		Jsonloc.put("CLG_LAT", LAT);
		Jsonloc.put("CLG_LNG", LNG);
		Jsonloc.put("CLG_LOGO",FilePathlogo+ CLG_LOGO);
		Jsonloc.put("CLG_PLACE", PLACE);
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
	    con.close();   
	}catch (Exception e2) {System.out.println(e2);}  
	          
	out.close(); 
	
}
		else if (functionId.equalsIgnoreCase("ALL_IIMS_REQUEST")){
	        
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

		String selectTableSQL = "SELECT * from iim_list";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(selectTableSQL);
		  MyUtility val  = new MyUtility();
	int inc = 0;
		while (rs.next()) {
			String NAME =val.Nullcheck( rs.getString("CLG_NAME"));
			String CLG_NUM =val.Nullcheck( rs.getString("CLG_NUMBER"));
			String PLACE = val.Nullcheck(rs.getString("CLG_PLACE"));
			String CLG_LOGO =val.Nullcheck( rs.getString("CLG_LOGO"));
			String LAT = val.Nullcheck(rs.getString("CLG_LAT"));
			String LNG =val.Nullcheck( rs.getString("CLG_LNG"));
			String WEBSITE =val.Nullcheck( rs.getString("CLG_WEBSITE"));
			String NIT_ID = val.Nullcheck(rs.getString("CLG_ID"));
			String TRAVEL_INFO_AIR =val.Nullcheck(rs.getString("TRAVEL_INFO_AIR"));
			String	TRAVEL_INFO_RAIL=val.Nullcheck(rs.getString("TRAVEL_INFO_RAIL"));
			String	TRAVEL_INFO_ROAD=val.Nullcheck(rs.getString("TRAVEL_INFO_ROAD"));
			JSONObject Jsonloc= new JSONObject();
			Jsonloc.put("CLG_NAME", NAME);
			Jsonloc.put("CLG_PLACE", PLACE);
			Jsonloc.put("CLG_NUM", CLG_NUM);
			Jsonloc.put("CLG_LAT", LAT);
			Jsonloc.put("CLG_LNG", LNG);
			Jsonloc.put("CLG_LOGO",FilePathlogo+ CLG_LOGO);
			Jsonloc.put("CLG_PLACE", PLACE);
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
		    con.close();   
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close(); 
		
	}
		else if (functionId.equalsIgnoreCase("ALLIITS_REQUEST")){
	          
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

		String selectTableSQL = "SELECT * from iit_list";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(selectTableSQL);
	int inc = 0;
	  MyUtility val  = new MyUtility();
		while (rs.next()) {
			String NAME = rs.getString("CLG_NAME");
			String CLG_NUM =val.Nullcheck( rs.getString("CLG_NUMBER"));
			String PLACE = val.Nullcheck( rs.getString("CLG_PLACE"));
			String LAT = val.Nullcheck( rs.getString("CLG_LAT"));
			String LNG = val.Nullcheck( rs.getString("CLG_LNG"));
			String WEBSITE = val.Nullcheck( rs.getString("CLG_WEBSITE"));
			String CLG_LOGO =val.Nullcheck(  rs.getString("CLG_LOGO"));
			String NIT_ID = val.Nullcheck( rs.getString("CLG_ID"));
			String TRAVEL_INFO_AIR =val.Nullcheck( rs.getString("TRAVEL_INFO_AIR"));
			String	TRAVEL_INFO_RAIL=val.Nullcheck( rs.getString("TRAVEL_INFO_RAIL"));
			String	TRAVEL_INFO_ROAD=val.Nullcheck( rs.getString("TRAVEL_INFO_ROAD"));
			JSONObject Jsonloc= new JSONObject();
			Jsonloc.put("CLG_NAME", NAME);
			Jsonloc.put("CLG_NUM", CLG_NUM);
			Jsonloc.put("CLG_PLACE", PLACE);
			Jsonloc.put("CLG_LAT", LAT);
			Jsonloc.put("CLG_LNG", LNG);
			Jsonloc.put("CLG_LOGO", FilePathlogo+ CLG_LOGO);
			Jsonloc.put("CLG_PLACE", PLACE);
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
		    con.close();   
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close(); 
		
	}
		 else if (functionId.equalsIgnoreCase("ALLCLGS_REQUEST_BOTTOMSHT"))
		    {
			 String geoId = geoObject.getString("COLLEGE_ID");
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ConfigurePath.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			    Configuration imgconfiglogos1 = null;
				try {
					imgconfiglogos1 = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String FilePathlogo1 = imgconfiglogos1.getString("img_college_images");
				

				String Driver = config.getString("Driver");
				String ConnectionSpace = config.getString("ConnectionSpace");
				String Db_user = config.getString("Db_user");
				String Db_password = config.getString("Db_password");
				
		        try
		        {
		          Class.forName(Driver);
		          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);
		        PreparedStatement stmt = con.prepareStatement("SELECT CLG_ID,CLG_WEBSITE,CLG_NAME,CLG_LAT,CLG_LNG,CLG_PLACE,CLG_LOGO,CLG_IMAGES,ABOUT_US,CLG_NUMBER FROM ALL_CLGS_VIEW where CLG_ID=?");
		        stmt.setString(1, geoId);
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;
		        MyUtility val  = new MyUtility();
		        while (rs.next())
		        {
					String NAME =val.Nullcheck(rs.getString("CLG_NAME"));
					String ABOUT_US =val.Nullcheck(rs.getString("ABOUT_US"));
					String CLG_NUM = rs.getString("CLG_NUMBER");
					String PLACE = val.Nullcheck(rs.getString("CLG_PLACE"));
					String LAT = val.Nullcheck(rs.getString("CLG_LAT"));
					String LNG = val.Nullcheck(rs.getString("CLG_LNG"));
					String WEBSITE = val.Nullcheck( rs.getString("CLG_WEBSITE"));
					String NIT_ID =val.Nullcheck( rs.getString("CLG_ID"));
						String CLG_LOGO = FilePathlogo+val.Nullcheck(rs.getString("CLG_LOGO"));
						String CLG_IMAGES= val.Nullcheck(rs.getString("CLG_IMAGES"));
						
					
					JSONObject Jsonloc= new JSONObject();
					Jsonloc.put("CLG_NAME", NAME);
					Jsonloc.put("CLG_NUM", CLG_NUM);
					Jsonloc.put("CLG_PLACE", PLACE);
					Jsonloc.put("CLG_LAT", LAT);
					Jsonloc.put("CLG_LNG", LNG);
					Jsonloc.put("CLG_WEBSITE", WEBSITE);
					Jsonloc.put("CLG_ID", NIT_ID);
					Jsonloc.put("CLG_LOGO", CLG_LOGO);
					Jsonloc.put("CLG_IMAGES", CLG_IMAGES);
					Jsonloc.put("ABOUT_US", ABOUT_US);
					
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
		 else if (functionId.equalsIgnoreCase("COLLEGE_ABOUT_US"))
		    {
			 String geoId = geoObject.getString("COLLEGE_ID");
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
		        PreparedStatement stmt = con.prepareStatement("SELECT ABOUT_US FROM ALL_CLGS_VIEW where CLG_ID=?");
		        stmt.setString(1, geoId);
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;
		        while (rs.next())
		        {
					String CLG_DESC =rs.getString("ABOUT_US");
					
					JSONObject Jsonloc= new JSONObject();
					Jsonloc.put("CLG_DESC", CLG_DESC);
					
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
		 else if (functionId.equalsIgnoreCase("USER_VERSIONCODE_BETA"))
		    {
			
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
		        PreparedStatement stmt = con.prepareStatement("SELECT ANDROID_VERSION FROM BETA_VERSION_CODE");
		      
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;

		        while (rs.next())
		        {
					
					
					JSONObject Jsonloc= new JSONObject();
					Jsonloc.put("ANDROID_VERSION", rs.getInt("ANDROID_VERSION"));
				     
			      
			        response.setContentType("application/json");
			        response.setCharacterEncoding("UTF-8");
			        response.getWriter().write(Jsonloc.toString());
			        con.close();    
		      }}
		      catch (Exception e2)
		      {
		        System.out.println(e2);
		      }
		      out.close();
		    }	
		 else if (functionId.equalsIgnoreCase("COLLEGE_REVIEWS"))
		    {
			 String geoId = geoObject.getString("COLLEGE_ID");
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
		        PreparedStatement stmt = con.prepareStatement("SELECT FAC_RATING,PLACE_RATING,ENV_RATING,CAMP_RATING FROM user_reviews where CLG_ID=? ORDER BY DATE desc");
		        stmt.setString(1, geoId);
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;
		       int counter=0,counterindivid=0;
			float fcrating=0,placerating=0,envrating=0,camrating=0,overall=0;
		        while (rs.next())
		        {
					
					
					JSONObject Jsonloc= new JSONObject();
/*					Jsonloc.put("DATE", rs.getDate("DATE"));
					
					Jsonloc.put("USERNAME", rs.getString("USERNAME"));
					Jsonloc.put("USERMAIL", rs.getString("USERMAIL"));
					Jsonloc.put("REVIEW_CAPTION", rs.getString("REVIEW_CAPTION"));
					Jsonloc.put("REVIEW_CONTENT", rs.getString("REVIEW_CONTENT"));
					Jsonloc.put("USER_IMAGE", rs.getString("USER_IMAGE"));
					Jsonloc.put("REVIEW_IMAGES", rs.getString("REVIEW_IMAGES"));
					Jsonloc.put("REVIEW_ID", rs.getString("REVIEW_ID"));
					Jsonloc.put("LIKE_COUNT", rs.getInt("LIKE_COUNT"));
					Jsonloc.put("UNLIKE_COUNT", rs.getInt("UNLIKE_COUNT"));
					*/
					Jsonloc.put("FAC_RATING", rs.getFloat("FAC_RATING"));
					Jsonloc.put("PLACE_RATING", rs.getFloat("PLACE_RATING"));
					Jsonloc.put("ENV_RATING", rs.getFloat("ENV_RATING"));
					Jsonloc.put("CAMP_RATING", rs.getFloat("CAMP_RATING"));
					
					fcrating=fcrating+rs.getFloat("FAC_RATING");
					placerating=placerating+rs.getFloat("PLACE_RATING");
					envrating=envrating+rs.getFloat("ENV_RATING");
					camrating=camrating+rs.getFloat("CAMP_RATING");
					
					overall=overall+rs.getFloat("FAC_RATING")+rs.getFloat("PLACE_RATING")+rs.getFloat("ENV_RATING")+rs.getFloat("CAMP_RATING");
					
					counterindivid=counterindivid+1;
					counter=counter+4;
		          String x1 = Integer.toString(inc);
		          JSOBVAL.put(x1, Jsonloc);
		          inc++;
		        }
		       // String.valueOf((round(overallrating,1))
		       Float  overallrating=overall/counter;
		       Float fcoverall=fcrating/counterindivid;
		       Float plaoverall=placerating/counterindivid;
		       Float envoverall=envrating/counterindivid;
		       Float camoverall=camrating/counterindivid;
		       JSOBVAL.put("overallrating",Double.toString( round(overallrating,1)));
		       JSOBVAL.put("fcoverall", Double.toString(round(fcoverall,1) ));
		       JSOBVAL.put("plaoverall",Double.toString(round(plaoverall,1)  ));
		       JSOBVAL.put("envoverall",Double.toString(round(envoverall,1))  );
		       JSOBVAL.put("camoverall",Double.toString( round(camoverall,1)) );
		       JSOBVAL.put("noofratings", counterindivid);
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
		 else if (functionId.equalsIgnoreCase("COUNT_LIKE_UNLIKE"))
		 { 
			 String REVIEWID = geoObject.getString("REVIEWID");
			 String LIKE_UNLIKE = geoObject.getString("LIKE_UNLIKE");
			 String like="N";
			 int likecount=0;
			 int unlikecount=0;
			 String unlike="N";
			 if(LIKE_UNLIKE.equalsIgnoreCase("LIKE")){
				 like="Y";
				 likecount=1;
			 }
			 if(LIKE_UNLIKE.equalsIgnoreCase("UNLIKE")){
				 unlike="Y";
				 unlikecount=1;
			 }
			 String EMAILID = geoObject.getString("EMAILID");
		
			 java.util.Date dt = new java.util.Date();

			 java.text.SimpleDateFormat sdf = 
					new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 ZoneId singaporeZoneId = ZoneId.of("Asia/Kolkata");
			 sdf.setTimeZone(TimeZone.getTimeZone(singaporeZoneId));
			 String currentTime = sdf.format(dt);

				
				
			
				  
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
			          PreparedStatement stmt2 = con.prepareStatement("SELECT COUNT(REVIEW_ID) TOTALCOUNT FROM user_review_likeunlike where REVIEW_ID=? and USER_REVIEWED=?");
				        stmt2.setString(1, REVIEWID);
				        stmt2.setString(2, EMAILID);
				        ResultSet rs = stmt2.executeQuery();
				        while (rs.next())
				        {
				        	int val = rs.getInt("TOTALCOUNT");
				        	if(!(val>0)){
				        		
				        		
				        		PreparedStatement stmt=con.prepareStatement("INSERT INTO user_review_likeunlike  VALUES (?,?,?,?,?)"); 
								stmt.setString(1,REVIEWID);//1 specifies the first parameter in the query  
								stmt.setString(2,like); 
								stmt.setString(3,unlike); 
								stmt.setString(4,EMAILID); 
								stmt.setString(5,currentTime); 
								int i=stmt.executeUpdate();  
								System.out.println(i+" records inserted");  
								  if(i>0){
										PreparedStatement stmt1=con.prepareStatement("UPDATE user_reviews SET LIKE_COUNT = LIKE_COUNT+"+likecount+", UNLIKE_COUNT= UNLIKE_COUNT+"+unlikecount+" WHERE REVIEW_ID = ?"); 
										stmt1.setString(1,REVIEWID);//1 specifies the first parameter in the query  
										int i1=stmt1.executeUpdate();    
								  }
				        		
				        	}
				        	
				        }
					
						con.close();  
						
						//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
						//Statement statement = con.createStatement();
						//ResultSet rs = statement.executeQuery(selectTableSQL);
					int inc = 0;
				
						
							String RESULTCODE = "000000";
						
							JSONObject Jsonloc1= new JSONObject();
						
						
							Jsonloc1.put("RESULTCODE", RESULTCODE);
							String x1  = Integer.toString(inc);
							JSOBVAL.put(x1, Jsonloc1);
							
							
						
						  response.setContentType("application/json");
						    response.setCharacterEncoding("UTF-8");
						    response.getWriter().write(JSOBVAL.toString());     
						    con.close();   
						}catch (Exception e2) {System.out.println(e2);}  
						          
						out.close(); 
						
					
				
				
				
				
			 }
		 else if (functionId.equalsIgnoreCase("COLLEGE_MAP_REVIEWS")){   

			String collegeid = geoObject.getString("COLLEGE_ID");
			
			int SnoOfElements = geoObject.getInt("SROW_INDEX");
			int EnoOfElements = geoObject.getInt("EROW_INDEX");
			  
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
				String FilePath = imgconfig.getString("img_reviews_images_touser");
				

				String Driver = config.getString("Driver");
				String ConnectionSpace = config.getString("ConnectionSpace");
				String Db_user = config.getString("Db_user");
				String Db_password = config.getString("Db_password");
				
		        try
		        {
		          Class.forName(Driver);
		          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);

		        //  PreparedStatement stmt = con.prepareStatement("SELECT FAC_RATING,PLACE_RATING,ENV_RATING,CAMP_RATING FROM user_reviews where CLG_ID=? ORDER BY DATE desc");
					PreparedStatement stmt=con.prepareStatement("SELECT * FROM user_reviews where CLG_ID='"+collegeid+"' ORDER BY DATE DESC LIMIT "+SnoOfElements+","+EnoOfElements); 
					
					ResultSet rs = stmt.executeQuery();
			        int inc = 0;
			        MyUtility val  = new MyUtility();
			        while (rs.next())
			        {
						
						
						JSONObject Jsonloc= new JSONObject();
						Jsonloc.put("DATE",val.Nullcheck(rs.getTimestamp("DATE").toString()));
						
						Jsonloc.put("USERNAME",val.Nullcheck( rs.getString("USERNAME")));
						Jsonloc.put("USERMAIL", val.Nullcheck(rs.getString("USERMAIL")));
						Jsonloc.put("REVIEW_CAPTION",val.Nullcheck( rs.getString("REVIEW_CAPTION")));
						Jsonloc.put("REVIEW_CONTENT",val.Nullcheck( rs.getString("REVIEW_CONTENT")));
						Jsonloc.put("USER_IMAGE", val.Nullcheck(rs.getString("USER_IMAGE")));
						Jsonloc.put("REVIEW_IMAGES",val.Nullcheck(FilePath+ rs.getString("REVIEW_IMAGES")));
						Jsonloc.put("REVIEW_ID", val.Nullcheck(rs.getString("REVIEW_ID")));
						Jsonloc.put("LIKE_COUNT", (rs.getInt("LIKE_COUNT")));
						Jsonloc.put("UNLIKE_COUNT", rs.getInt("UNLIKE_COUNT"));
						
						Jsonloc.put("FAC_RATING", rs.getFloat("FAC_RATING"));
						Jsonloc.put("PLACE_RATING", rs.getFloat("PLACE_RATING"));
						Jsonloc.put("ENV_RATING", rs.getFloat("ENV_RATING"));
						Jsonloc.put("CAMP_RATING",rs.getFloat("CAMP_RATING"));
						
		
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
	
		 else if (functionId.equalsIgnoreCase("COLLEGE_DEPARTMENTS"))
		    {
			 String geoId = geoObject.getString("COLLEGE_ID");
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
		        PreparedStatement stmt = con.prepareStatement("SELECT * FROM colleges_deparment where CLG_ID=?");
		        stmt.setString(1, geoId);
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;
		        while (rs.next())
		        {
					String CLG_STREAM =rs.getString("CLG_STREAM");
					String STREAM1 =rs.getString("STREAM1");
					String STREAM2 =rs.getString("STREAM2");
					String STREAM3 =rs.getString("STREAM3");
					String STREAM4 =rs.getString("STREAM4");
					String STREAM5 =rs.getString("STREAM5");
					String STREAM6 =rs.getString("STREAM6");
					String STREAM7 =rs.getString("STREAM7");
					String STREAM8 =rs.getString("STREAM8");
					String STREAM9 =rs.getString("STREAM9");
					String STREAM10 =rs.getString("STREAM10");
					String STREAM11=rs.getString("STREAM11");
					String STREAM12=rs.getString("STREAM12");
					String STREAM13=rs.getString("STREAM13");
					String STREAM14=rs.getString("STREAM14");
					String STREAM15=rs.getString("STREAM15");
					String STREAM16=rs.getString("STREAM16");
					String STREAM17=rs.getString("STREAM17");
					String STREAM18=rs.getString("STREAM18");
					String STREAM19=rs.getString("STREAM19");
					String STREAM20=rs.getString("STREAM20");
					String STREAM21=rs.getString("STREAM21");
					String STREAM22=rs.getString("STREAM22");
					String STREAM23=rs.getString("STREAM23");
					String STREAM24=rs.getString("STREAM24");
					String STREAM25=rs.getString("STREAM25");
					
					
					JSONObject Jsonloc= new JSONObject();
					if(!(CLG_STREAM==null))
					Jsonloc.put("CLG_STREAM", CLG_STREAM);
					if(!(STREAM1==null))
						Jsonloc.put("STREAM1", STREAM1);
					if(!(STREAM2==null))
						Jsonloc.put("STREAM2", STREAM2);
					if(!(STREAM3==null))
						Jsonloc.put("STREAM3", STREAM3);
					if(!(STREAM4==null))
						Jsonloc.put("STREAM4", STREAM4);
					if(!(STREAM5==null))
						Jsonloc.put("STREAM5", STREAM5);
					if(!(STREAM6==null))
						Jsonloc.put("STREAM6", STREAM6);
					if(!(STREAM7==null))
						Jsonloc.put("STREAM7", STREAM7);
					if(!(STREAM8==null))
						Jsonloc.put("STREAM8", STREAM8);
					if(!(STREAM9==null))
						Jsonloc.put("STREAM9", STREAM9);
					if(!(STREAM10==null))
						Jsonloc.put("STREAM10", STREAM10);
					if(!(STREAM11==null))
						Jsonloc.put("STREAM11", STREAM11);
					if(!(STREAM12==null))
						Jsonloc.put("STREAM12", STREAM12);
					if(!(STREAM13==null))
						Jsonloc.put("STREAM13", STREAM13);
					if(!(STREAM14==null))
						Jsonloc.put("STREAM14", STREAM14);
					if(!(STREAM15==null))
						Jsonloc.put("STREAM15", STREAM15);
					if(!(STREAM16==null))
						Jsonloc.put("STREAM16", STREAM16);
					if(!(STREAM17==null))
						Jsonloc.put("STREAM17", STREAM17);
					if(!(STREAM18==null))
						Jsonloc.put("STREAM18", STREAM18);
					if(!(STREAM19==null))
						Jsonloc.put("STREAM19", STREAM19);
					if(!(STREAM20==null))
						Jsonloc.put("STREAM20", STREAM20);
					if(!(STREAM21==null))
						Jsonloc.put("STREAM21", STREAM21);
					if(!(STREAM22==null))
						Jsonloc.put("STREAM22", STREAM22);
					if(!(STREAM23==null))
						Jsonloc.put("STREAM23", STREAM23);
					if(!(STREAM24==null))
						Jsonloc.put("STREAM24", STREAM24);
					if(!(STREAM25==null))
						Jsonloc.put("STREAM25", STREAM25);
					
					
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
		 else if (functionId.equalsIgnoreCase("COLLEGE_FESTDATA"))
		    {
			 String geoId = geoObject.getString("COLLEGE_ID");
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
		        PreparedStatement stmt = con.prepareStatement("SELECT CLG_FULL_INFO,FEST_IMAGE FROM college_fests where CLG_ID=?");
		        stmt.setString(1, geoId);
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;
		        while (rs.next())
		        {
					String CLG_FULL_INFO =rs.getString("CLG_FULL_INFO");
					String FEST_IMAGE =rs.getString("FEST_IMAGE");
					
					JSONObject Jsonloc= new JSONObject();
					Jsonloc.put("FEST_INFO", CLG_FULL_INFO);
					Jsonloc.put("FEST_IMAGE", FEST_IMAGE);
					
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
		 else if (functionId.equalsIgnoreCase("COLLEGE_RANKDATA"))
		    {
			 String geoId = geoObject.getString("COLLEGE_ID");
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
		        PreparedStatement stmt = con.prepareStatement("SELECT COLLEGE_NAME,CLG_IMG FROM nit_rankings where CLG_ID=?");
		        stmt.setString(1, geoId);
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;
		        while (rs.next())
		        {
					String CLG_FULL_INFO =rs.getString("COLLEGE_NAME");
					String FEST_IMAGE =rs.getString("CLG_IMG");
					
					JSONObject Jsonloc= new JSONObject();
					Jsonloc.put("FEST_INFO", CLG_FULL_INFO);
					Jsonloc.put("FEST_IMAGE", FEST_IMAGE);
					
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
		 else if (functionId.equalsIgnoreCase("COLLEGE_CUTTOFS"))
		    {
			 String geoId = geoObject.getString("COLLEGE_ID");
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
		        PreparedStatement stmt = con.prepareStatement("SELECT CUTOFFS FROM colleges_cutoffs where CLG_ID=?");
		        stmt.setString(1, geoId);
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;
		        while (rs.next())
		        {
					String CUTOFFS =rs.getString("CUTOFFS");
					
					JSONObject Jsonloc= new JSONObject();
					Jsonloc.put("CUTOFFS", CUTOFFS);
					
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
		
		 else if (functionId.equalsIgnoreCase("COLLEGE_PLACEMENTS"))
		    {
			 String geoId = geoObject.getString("COLLEGE_ID");
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
		        PreparedStatement stmt = con.prepareStatement("SELECT PLACEMENTS FROM colleges_placements where CLG_ID=?");
		        stmt.setString(1, geoId);
		        ResultSet rs = stmt.executeQuery();
		        int inc = 0;
		        while (rs.next())
		        {
					String PLACEMENTS =rs.getString("PLACEMENTS");
					
					JSONObject Jsonloc= new JSONObject();
					Jsonloc.put("PLACEMENTS", PLACEMENTS);
					
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
    private  double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
	

}