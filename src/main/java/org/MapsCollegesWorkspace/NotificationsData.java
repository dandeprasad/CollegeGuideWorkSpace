package org.MapsCollegesWorkspace;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.HomeWorkspace.MyUtility;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;




@WebServlet("/NotificationsData")
public class NotificationsData extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  
		  response.addHeader("Access-Control-Allow-Origin", "*");
	        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");

		PrintWriter out = response.getWriter(); 
	    Configuration imgconfig = null;
		try {
			imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	String FilePath = imgconfig.getString("img_Notifications");
	//	String filename = "newFile.txt";
	//	String workingDirectory = System.getProperty("user.dir");
	//	String absoluteFilePath = "";
		//Scanner s = new Scanner(new File("/home/colle3qj/ex.txt"));
		//absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
		//absoluteFilePath = workingDirectory + File.separator + filename;

	//	System.out.println("Final filepath : " + absoluteFilePath);
		

		JSONObject JSOBVAL= new JSONObject();	
		MyUtility values = new MyUtility();
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		
		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");
		int SnoOfElements = geoObject.getInt("SROW_INDEX");
		int EnoOfElements = geoObject.getInt("EROW_INDEX");
		
		if (functionId.equalsIgnoreCase("GET_NOTIFY_ONLY")&& actionId.equalsIgnoreCase("GET_ALL_NOTIFY_STRING")){   

			String filterkey = geoObject.getString("FILTER_KEY");
		
		
	
		  
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
	          PreparedStatement stmt;
if(filterkey.equalsIgnoreCase("ALL")){
	stmt=con.prepareStatement("select * from collegeexplore_notification ORDER BY POSTED_DATE DESC LIMIT "+SnoOfElements+","+EnoOfElements);		 
}
else{
	 
	 
	 stmt=con.prepareStatement("select * from collegeexplore_notification where FILTER_TYPE= '"+filterkey+"' ORDER BY POSITION DESC LIMIT "+SnoOfElements+","+EnoOfElements);
}
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
				while (rs.next()) {
					String APP_KEY = values.Nullcheck(rs.getString("APP_KEY"));
					String NOTIFY_DETAIL_MSG =values.Nullcheck( rs.getString("NOTIFY_DETAIL_MSG"));
					String NOTIFY_TITLE =values.Nullcheck( rs.getString("NOTIFY_TITLE"));
					Date POSTED_DATE=rs.getDate("POSTED_DATE");
					String NOTIFY_IMAGE=values.Nullcheck(rs.getString("NOTIFY_IMAGE"));
					String NOTIFY_TYPE=values.Nullcheck(rs.getString("NOTIFY_TYPE"));
					String UNIQUE_DATA1=values.Nullcheck(rs.getString("UNIQUE_DATA1"));
					String DATA2=values.Nullcheck(rs.getString("DATA2"));
					String DATA3=values.Nullcheck(rs.getString("DATA3"));
					String DATA4=values.Nullcheck(rs.getString("DATA4"));
					String DATA5=values.Nullcheck(rs.getString("DATA5"));
					String DATA6=values.Nullcheck(rs.getString("DATA6"));
					JSONObject Jsonloc1= new JSONObject();
					Jsonloc1.put("APP_KEY", APP_KEY);
					Jsonloc1.put("NOTIFY_DETAIL_MSG", NOTIFY_DETAIL_MSG);
					Jsonloc1.put("NOTIFY_TITLE", NOTIFY_TITLE);
					Jsonloc1.put("POSTED_DATE", POSTED_DATE.toString());
					
					if (NOTIFY_TYPE.equals("NEWS_COLLEGEEXPLORE")) {
						String FilePath = imgconfig.getString("img_NewsFragmentAdaptor");	
						String imagepath = FilePath+NOTIFY_IMAGE;
						
						Jsonloc1.put("NOTIFY_IMAGE", FilePath+NOTIFY_IMAGE);
					}
		else if (NOTIFY_TYPE.equals("EXAMS_COLLEGEEXPLORE")) {
						
						String FilePath = imgconfig.getString("img_HomeExamsFragmentAdaptor");
						Jsonloc1.put("NOTIFY_IMAGE", FilePath+NOTIFY_IMAGE);
					}
					
		else if (NOTIFY_TYPE.equals("FEST_COLLEGEEXPLORE")) {
			
			String FilePath = imgconfig.getString("img_Fests_data");
			Jsonloc1.put("NOTIFY_IMAGE", FilePath+NOTIFY_IMAGE);


}
					
					Jsonloc1.put("NOTIFY_TYPE", NOTIFY_TYPE);
					Jsonloc1.put("UNIQUE_DATA1", UNIQUE_DATA1);
					Jsonloc1.put("DATA2", DATA2);
					Jsonloc1.put("DATA3", DATA3);
					Jsonloc1.put("DATA4", DATA4);
					Jsonloc1.put("DATA5", DATA5);
					Jsonloc1.put("DATA6", DATA6);
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
		  
			  
	}
	

}