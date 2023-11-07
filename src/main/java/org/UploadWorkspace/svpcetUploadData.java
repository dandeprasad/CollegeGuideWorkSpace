package org.UploadWorkspace;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.AdmissionsWorkspace.SMSGateway;
import org.HomeWorkspace.MyUtility;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;


@WebServlet("/svpcetUploadData")
public class svpcetUploadData extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  

		  response.addHeader("Access-Control-Allow-Origin", "*");
	        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");

		 

		MyUtility values = new MyUtility();
		
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String) request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(escapechar(reqTypetesrrt));
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		JSONObject obj2 = new JSONObject(reqTypetesrrt);

		String functionId = geoObject.getString("FUNCTION_ID");
	
		
		if (functionId.equalsIgnoreCase("UPLOAD_NEWS")){
			JSONObject geoObject1 = geoObject.getJSONObject("DATA_TO_SERV");
			java.util.Date dt = new java.util.Date();

			java.text.SimpleDateFormat sdf = 
			     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ZoneId singaporeZoneId = ZoneId.of("Asia/Kolkata");
			sdf.setTimeZone(TimeZone.getTimeZone(singaporeZoneId));
			String currentTime = sdf.format(dt);
			
		
			   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
			   LocalDateTime now = LocalDateTime.now(); 
		 
		String val=	 "NEWS"+"-"+(dtf.format(now))+"";
		String notifytype = "";
		if(geoObject1.getString("optiondata").equalsIgnoreCase("TYPE3")) {
			notifytype="";
		}
		if(geoObject1.getString("optiondata").equalsIgnoreCase("TYPE1")) {
			notifytype="Y";
		}
		if(geoObject1.getString("optiondata").equalsIgnoreCase("TYPE2")) {
			notifytype="N";
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
		          PreparedStatement stmt = con.prepareStatement("insert into svpcet_news_upload(USERMAIL,DATE,NEWS_HEADER,NEWS_CONTENT,NEWS_IMAGES,NEWS_NEW,NEWS_ID,NEWS_TYPE) values(?,?,?,?,?,?,?,?)");
		  
		          stmt.setString(1,"");
		          stmt.setString(2,currentTime);
		          stmt.setString(3,geoObject1.getString("newsheader"));
		          stmt.setString(4,geoObject1.getString("newsdesc"));
		          stmt.setString(5,geoObject1.getString("uploadedimg"));
		          stmt.setString(6,"Y");
		          stmt.setString(7,val);
		          stmt.setString(8,notifytype);
		          
		          

		        
		          //ResultSet rs = stmt.executeQuery();	
		          int i=stmt.executeUpdate();  

		          System.out.println(i+" records inserted");  
		          
		        
					int inc = 0;
					
					if(i>0){
					String RESULTCODE = "000000";
				
				String[] users_to_notify = (config.getString("svpcet_Notify_users")).split("\\|");
				StringBuilder data = new StringBuilder();
					for (int usr=0;usr<users_to_notify.length;usr++) {
						//SMSGateway sms = new SMSGateway();
						//sms.SendMessage("User Uploaded: "+geoObject1.getString("newsheader"),users_to_notify[usr]);
						if(users_to_notify.length-1==usr) {
							data.append("'"+users_to_notify[usr]+"'");
							break;
						}
						data.append("'"+users_to_notify[usr]+"'"+",");
				}
//					PreparedStatement stmt1=con.prepareStatement("select TOKEN_ID from firebase_token where USER_MAIL in ("+data.toString()+")"); 
//					
//					ResultSet rs=stmt1.executeQuery(); 
//				    while (rs.next()) {
//				    	
//						String TOKEN_ID =  values.Nullcheck(rs.getString("TOKEN_ID")).toString(); 
//						EmployeeUpdateNotify dan = new EmployeeUpdateNotify();
//						dan.newsmessage(TOKEN_ID,geoObject1.getString("newsheader").toString(),"Hello! News Uploaded");
//						
//					}
					  con.close();
					
					
					JSONObject Jsonloc1= new JSONObject();
				
				
					Jsonloc1.put("RESULTCODE", RESULTCODE);
					String x1  = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					
					
				
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(JSOBVAL.toString());
					}
					else {
				
			        	String RESULTCODE = "999999";
						
						JSONObject Jsonloc1= new JSONObject();
					
					
						Jsonloc1.put("RESULTCODE", RESULTCODE);
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						
						
					
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());
					    
			        
					}
				  
		        }
		        
		        catch (Exception e2)
		        {
		        	int inc = 0;
		        	String RESULTCODE = "999999";
					
					JSONObject Jsonloc1= new JSONObject();
				
				
					Jsonloc1.put("RESULTCODE", RESULTCODE);
					String x1  = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					
					
				
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(JSOBVAL.toString());
				    
		         e2.printStackTrace();
		        }
			        
		}
		
		
		if (functionId.equalsIgnoreCase("SEND_SVPCETDATA_NEWS_WORKSPACE")){
			JSONObject geoObject1 = geoObject.getJSONObject("DATA_TO_SERV");
			String filterdata=geoObject.getString("filterdata");
			


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
					
					 
					java.util.Date dt = new java.util.Date();

					java.text.SimpleDateFormat sdf = 
					     new java.text.SimpleDateFormat("yyyy-MM-dd");

					String currentTime = sdf.format(dt);
					
			            
			        try
			        {
			          Class.forName(Driver);
			          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);
		          PreparedStatement stmt = con.prepareStatement("insert into notification_workspace(NEWSID,NEWS_HEADER,NEWS_DETAILS,POSTED_DATE,POSITION,TAG_TYPE,FILTER_TYPE,TAG_COLOR,FULL_IMAGE,NEWS_IMAGE) values(?,?,?,?,?,?,?,?,?,?)");
		  
		          stmt.setString(1,geoObject1.getString("NEWSID"));
		          stmt.setString(2,geoObject1.getString("NEWS_HEADER"));
		          stmt.setString(3,geoObject1.getString("NEWS_DETAILS"));
		          stmt.setString(4,currentTime);
		          stmt.setString(5,null);
		          stmt.setString(6,"ALL");
		          stmt.setString(7,filterdata);
		          stmt.setString(8,"872c59");
		          stmt.setString(9,geoObject1.getString("NEWS_TYPE"));
		          stmt.setString(10,geoObject1.getString("NEWS_IMAGE_UPLOAD"));
		          
		        
		          //ResultSet rs = stmt.executeQuery();	
		          int i=stmt.executeUpdate();  

		          System.out.println(i+" records inserted");  
		          
		         
					int inc = 0;
					
					if(i>0){
					String RESULTCODE = "000000";
					
			          PreparedStatement stmt1 = con.prepareStatement("UPDATE svpcet_news_upload set NEWS_NEW='N' where NEWS_ID='"+geoObject1.getString("NEWSID")+"'");
			          //ResultSet rs = stmt.executeQuery();	
			          int i1=stmt1.executeUpdate();  

			          System.out.println(i1+" records inserted");
			          
					JSONObject Jsonloc1= new JSONObject();
				
				
					Jsonloc1.put("RESULTCODE", RESULTCODE);
					String x1  = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					
					
				
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(JSOBVAL.toString());
				    System.out.println("Boolean value"+geoObject.getBoolean("notifyreq"));
				    
				  Boolean data =   geoObject.getBoolean("notifyreq");
				  if(data) {
					  PreparedStatement stmt2 = con.prepareStatement("insert into svpcet_trigger_notification(NOTIFY_ID,APP_KEY,NOTIFY_TITLE,NOTIFY_DETAIL_MSG,NOTIFY_IMAGE,POSTED_DATE,NOTIFY_TYPE,NOTIFY_NEED,UNIQUE_DATA1,DATA2,DATA3) values(?,?,?,?,?,?,?,?,?,?,?)");
					  stmt2.setInt(1,0);
			          stmt2.setString(2,"NEWS_WORKSPACE");
			          stmt2.setString(3,geoObject1.getString("NEWS_HEADER"));
			          stmt2.setString(4,geoObject1.getString("NEWS_DETAILS"));
			          stmt2.setString(5,geoObject1.getString("NEWS_IMAGE_UPLOAD"));
			          stmt2.setString(6,currentTime);
			          stmt2.setString(7,"NEWS_COLLEGEEXPLORE");
			          stmt2.setString(8,"Y");
			          stmt2.setString(9,geoObject1.getString("NEWSID"));
			          stmt2.setString(10,geoObject1.getString("NEWS_HEADER"));
			          stmt2.setString(11,geoObject1.getString("NEWS_DETAILS"));
			        
			          //ResultSet rs = stmt.executeQuery();	
			          int j=stmt2.executeUpdate();  

			          System.out.println(j+" records inserted");  
				  }
					}
					else {
				
			        	String RESULTCODE = "999999";
						
						JSONObject Jsonloc1= new JSONObject();
					
					
						Jsonloc1.put("RESULTCODE", RESULTCODE);
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						
						
					
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());
					    
			        
					}
					 con.close();
		        }
		        
		        catch (Exception e2)
		        {
		        	int inc = 0;
		        	String RESULTCODE = "999999";
					
					JSONObject Jsonloc1= new JSONObject();
				
				
					Jsonloc1.put("RESULTCODE", RESULTCODE);
					String x1  = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					
					
				
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(JSOBVAL.toString());
				    
		         e2.printStackTrace();
		        }
			        
		}
	
		

		
		if (functionId.equalsIgnoreCase("GET_UPLOADED_SVPCET_NEWS")) {

			  String geoId ="ALL_NEWS";
			
		
			  
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
				String FilePath = imgconfig.getString("img_NewsFragmentAdaptor");	

				String Driver = config.getString("Driver");
				String ConnectionSpace = config.getString("ConnectionSpace");
				String Db_user = config.getString("Db_user");
				String Db_password = config.getString("Db_password");
				
		        try
		        {
		          Class.forName(Driver);
		          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);


					PreparedStatement stmt=con.prepareStatement("select * from svpcet_news_upload where NEWS_NEW='Y'"); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String NEWS_ID =  values.Nullcheck(rs.getString("NEWS_ID"));
						String NEWS_HEADER = values.Nullcheck(rs.getString("NEWS_HEADER"));
						Date POSTED_DATE=rs.getDate("DATE");
						
						String NEWS_CONTENT=values.Nullcheck(rs.getString("NEWS_CONTENT"));
						String NEWS_IMAGES=values.Nullcheck(rs.getString("NEWS_IMAGES"));
						String NEWS_IMAGE_UPLOAD=values.Nullcheck(rs.getString("NEWS_IMAGES"));
						String NEWS_TYPE=values.Nullcheck(rs.getString("NEWS_TYPE"));
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("NEWSID", NEWS_ID);
						Jsonloc1.put("NEWS_HEADER", NEWS_HEADER);
						Jsonloc1.put("POSTED_DATE", POSTED_DATE.toString());
					
						Jsonloc1.put("NEWS_DETAILS", NEWS_CONTENT);
						Jsonloc1.put("NEWS_IMAGE",FilePath+NEWS_IMAGES);
						Jsonloc1.put("NEWS_IMAGE_UPLOAD", NEWS_IMAGE_UPLOAD);
						Jsonloc1.put("NEWS_TYPE", NEWS_TYPE);
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					
					
				}
		  
		
				
			
}
	 public String  escapechar(Object object ) {
		 return object.toString().replace("'", "\\'");
	 }
	 }