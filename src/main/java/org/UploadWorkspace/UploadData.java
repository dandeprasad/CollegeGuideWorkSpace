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


@WebServlet("/UploadData")
public class UploadData extends HttpServlet {

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
		          PreparedStatement stmt = con.prepareStatement("insert into news_upload(USERMAIL,DATE,NEWS_HEADER,NEWS_CONTENT,NEWS_IMAGES,NEWS_NEW,NEWS_ID,NEWS_UPLOADER_NAME,NEWS_REF_URL) values(?,?,?,?,?,?,?,?,?)");
		  
		          stmt.setString(1,"");
		          stmt.setString(2,currentTime);
		          stmt.setString(3,geoObject1.getString("newsheader"));
		          stmt.setString(4,geoObject1.getString("newsdesc"));
		          stmt.setString(5,geoObject1.getString("uploadedimg"));
		          stmt.setString(6,"Y");
		          stmt.setString(7,(geoObject1.getString("newsheader")).replace(" ", "-")+"-"+val);
		          stmt.setString(8,geoObject1.getString("newsUploaderName"));
		          stmt.setString(9,geoObject1.getString("newsRefUrl"));

		        
		          //ResultSet rs = stmt.executeQuery();	
		          int i=stmt.executeUpdate();  

		          System.out.println(i+" records inserted");  
		          
		        
					int inc = 0;
					
					if(i>0){
					String RESULTCODE = "000000";
				
				String[] users_to_notify = (config.getString("Notify_users")).split("\\|");
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
					PreparedStatement stmt1=con.prepareStatement("select TOKEN_ID from firebase_token where USER_MAIL in ("+data.toString()+")"); 
					
					ResultSet rs=stmt1.executeQuery(); 
				    while (rs.next()) {
				    	
						String TOKEN_ID =  values.Nullcheck(rs.getString("TOKEN_ID")).toString(); 
						EmployeeUpdateNotify dan = new EmployeeUpdateNotify();
						dan.newsmessage(TOKEN_ID,geoObject1.getString("newsheader").toString(),"Hello! News Uploaded");
						
					}
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
		if (functionId.equalsIgnoreCase("SEND_DATA_FESTS_WORKSPACE")){
			JSONObject geoObject1 = geoObject.getJSONObject("DATA_TO_SERV");
			JSONObject geoObject2 = geoObject.getJSONObject("extra_data");
			//String filterdata=geoObject.getString("filterdata");
			


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
			          String mainquery = "insert into fests_data(fest_id,fest_college_name,college_website,college_location,fest_name,fest_caption,fest_email_add,fest_type,fest_description,fest_depart,start_date,end_date,fest_event,fest_workshop,fest_paper,fest_guest,fest_sponsors,fest_dead_registration,fest_Regis_fees,fest_website,fest_regis_url,fest_links,fest_reach,Fest_contact_persons,banner_logos,college_extra_images,CLG_ID,INTERNAL_FESTTYPE,INTERNAL_COLLEGE_TYPE,INTERNAL_DEPT_TYPE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			          
		          PreparedStatement stmt = con.prepareStatement(mainquery);
		  
		          stmt.setString(1,escapechar(geoObject1.getString("FEST_ID")));
		          stmt.setString(2,escapechar(geoObject1.getString("CLG_NAME")));
		          stmt.setString(3,escapechar(geoObject1.getString("CLG_WEBSITE")));
		          stmt.setString(4,escapechar(geoObject1.getString("CLG_LOC")));
		          stmt.setString(5,escapechar(geoObject1.getString("FEST_NAME")));
		          stmt.setString(6,escapechar(geoObject1.getString("FEST_CAPTION")));
		          stmt.setString(7,escapechar(geoObject1.getString("FEST_EMAIL_ADD")));
		          stmt.setString(8,escapechar(geoObject1.getString("FEST_TYPE")));
		          stmt.setString(9,escapechar(geoObject1.getString("FEST_DESC")));
		          stmt.setString(10,escapechar(geoObject1.getString("FEST_DEPRT")));
		          stmt.setString(11,escapechar(geoObject1.getString("FEST_ST_DATE")));
		          stmt.setString(12,escapechar(geoObject1.getString("FEST_END_DATE")));
		          stmt.setString(13,escapechar(geoObject1.getString("EVENTS_DATA")));
		          stmt.setString(14,escapechar(geoObject1.getString("WORKSHOP_DATA")));
		          stmt.setString(15,escapechar(geoObject1.getString("PAPER_PRES_DATA")));
		          stmt.setString(16,escapechar(geoObject1.getString("FEST_GUEST")));
		          stmt.setString(17,escapechar(geoObject1.getString("FEST_SPONSERS")));
		          stmt.setString(18,escapechar(geoObject1.getString("DEADLINE_REG")));
		          stmt.setString(19,escapechar(geoObject1.getString("REG_FEE")));
		          stmt.setString(20,escapechar(geoObject1.getString("FEST_WEBSITE")));
		          stmt.setString(21,escapechar(geoObject1.getString("FEST_REG_URL")));
		          stmt.setString(22,"");
		          stmt.setString(23,escapechar(geoObject1.getString("HW_REACH")));
		          stmt.setString(24,escapechar(geoObject1.getString("FEST_CONT_PERSN")));
		          stmt.setString(25,escapechar(geoObject1.getString("FEST_BANNER_VERIFIED")));
		          stmt.setString(26,"");
		          
		          stmt.setString(27,escapechar(geoObject2.getString("UNIQUE_CLG_ID")));
		          stmt.setString(28,escapechar(geoObject2.getString("FEST_TYPE")));
		          stmt.setString(29,escapechar(geoObject2.getString("CLG_TYPE")));
		          stmt.setString(30,escapechar(geoObject2.getString("DEPRT_TYPE")));
		                   
		          
		          
		          
		          
		          
		          //ResultSet rs = stmt.executeQuery();	
		          int i=stmt.executeUpdate();  

		          System.out.println(i+" records inserted");  
		          
		         
					int inc = 0;
					
					if(i>0){
					String RESULTCODE = "000000";
					
			          PreparedStatement stmt1 = con.prepareStatement("UPDATE fests_registration_web set FEST_NEW='N' where EVENTS_WRKSHP_PAPER_ID='"+geoObject1.getString("FEST_ID")+"'");
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
				    System.out.println("Boolean value"+geoObject2.getBoolean("NOTIFY_CND"));
				    
				  Boolean data =   geoObject2.getBoolean("NOTIFY_CND");
				  if(data) {
					  PreparedStatement stmt2 = con.prepareStatement("insert into collegeexplore_notification(NOTIFY_ID,APP_KEY,NOTIFY_TITLE,NOTIFY_DETAIL_MSG,NOTIFY_IMAGE,POSTED_DATE,NOTIFY_TYPE,NOTIFY_NEED,UNIQUE_DATA1,DATA2,DATA3) values(?,?,?,?,?,?,?,?,?,?,?)");
					  stmt2.setInt(1,0);
			          stmt2.setString(2,"FESTS");
			          stmt2.setString(3,geoObject1.getString("FEST_NAME"));
			          stmt2.setString(4,geoObject1.getString("FEST_DESC"));
			          stmt2.setString(5,geoObject1.getString("FEST_BANNER_VERIFIED"));
			          stmt2.setString(6,currentTime);
			          stmt2.setString(7,"FEST_COLLEGEEXPLORE");
			          stmt2.setString(8,"Y");
			          stmt2.setString(9,geoObject1.getString("FEST_ID"));
			          stmt2.setString(10,"");
			          stmt2.setString(11,"");
			        
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
		if (functionId.equalsIgnoreCase("SEND_DATA_NEWS_WORKSPACE")){
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
		          PreparedStatement stmt = con.prepareStatement("insert into news_workspace(NEWSID,NEWS_HEADER,NEWS_DETAILS,POSTED_DATE,POSITION,TAG_TYPE,FILTER_TYPE,TAG_COLOR,FULL_IMAGE,NEWS_IMAGE,WEBSITE_LINKS) values(?,?,?,?,?,?,?,?,?,?,?)");
		  
		          stmt.setString(1,geoObject1.getString("NEWSID"));
		          stmt.setString(2,geoObject1.getString("NEWS_HEADER"));
		          stmt.setString(3,geoObject1.getString("NEWS_DETAILS"));
		          stmt.setString(4,currentTime);
		          stmt.setString(5,null);
		          stmt.setString(6,"ALL");
		          stmt.setString(7,filterdata);
		          stmt.setString(8,"872c59");
		          stmt.setString(9,"Y");
		          stmt.setString(10,geoObject1.getString("NEWS_IMAGE_UPLOAD"));
		          stmt.setString(11,geoObject1.getString("NEWS_REF_URL"));
		        
		          //ResultSet rs = stmt.executeQuery();	
		          int i=stmt.executeUpdate();  

		          System.out.println(i+" records inserted");  
		          
		         
					int inc = 0;
					
					if(i>0){
					String RESULTCODE = "000000";
					
			          PreparedStatement stmt1 = con.prepareStatement("UPDATE news_upload set NEWS_NEW='N' where NEWS_ID='"+geoObject1.getString("NEWSID")+"'");
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
					  PreparedStatement stmt2 = con.prepareStatement("insert into collegeexplore_notification(NOTIFY_ID,APP_KEY,NOTIFY_TITLE,NOTIFY_DETAIL_MSG,NOTIFY_IMAGE,POSTED_DATE,NOTIFY_TYPE,NOTIFY_NEED,UNIQUE_DATA1,DATA2,DATA3) values(?,?,?,?,?,?,?,?,?,?,?)");
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
		if (functionId.equalsIgnoreCase("GET_UPLOADED_FESTS")) {

		
			
		
			  
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
				String FilePath = imgconfig.getString("img_Fests_data");	

				String Driver = config.getString("Driver");
				String ConnectionSpace = config.getString("ConnectionSpace");
				String Db_user = config.getString("Db_user");
				String Db_password = config.getString("Db_password");
				
		        try
		        {
		          Class.forName(Driver);
		          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);

		          
		      	PreparedStatement stmt4=con.prepareStatement("select DISTINCT(CLG_ID) from ALL_CLGS_VIEW"); 
				
				ResultSet rs4=stmt4.executeQuery(); 		
				JSONArray clgkeys = new JSONArray();
				while (rs4.next()) {
					clgkeys.put(values.Nullcheck(rs4.getString("CLG_ID")));
				}
				
				

					PreparedStatement stmt=con.prepareStatement("select * from fests_registration_web where FEST_NEW='Y'"); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String CLG_EMAILID =  values.Nullcheck(rs.getString("CLG_EMAILID"));
						String CLG_LOC = values.Nullcheck(rs.getString("CLG_LOC"));
						String CLG_NAME=values.Nullcheck(rs.getString("CLG_NAME"));
						String CLG_PH_NUM=values.Nullcheck(rs.getString("CLG_PH_NUM"));
						String CLG_TYPE=values.Nullcheck(rs.getString("CLG_TYPE"));
						String CLG_WEBSITE =  values.Nullcheck(rs.getString("CLG_WEBSITE"));
						String DEADLINE_REG = values.Nullcheck(rs.getString("DEADLINE_REG"));
						String FACEBOOK_LINK=values.Nullcheck(rs.getString("FACEBOOK_LINK"));
						String FEST_ACC=values.Nullcheck(rs.getString("FEST_ACC"));
						String FEST_CAPTION=values.Nullcheck(rs.getString("FEST_CAPTION"));
						String FEST_CONT_PERSN =  values.Nullcheck(rs.getString("FEST_CONT_PERSN"));
						String FEST_DEPRT = values.Nullcheck(rs.getString("FEST_DEPRT"));
						String FEST_DESC=values.Nullcheck(rs.getString("FEST_DESC"));
						String FEST_EMAIL_ADD=values.Nullcheck(rs.getString("FEST_EMAIL_ADD"));
						String FEST_END_DATE=values.Nullcheck(rs.getString("FEST_END_DATE"));
						String FEST_GUEST =  values.Nullcheck(rs.getString("FEST_GUEST"));
						String FEST_NAME = values.Nullcheck(rs.getString("FEST_NAME"));
						String FEST_REG_URL=values.Nullcheck(rs.getString("FEST_REG_URL"));
						String FEST_SPONSERS=values.Nullcheck(rs.getString("FEST_SPONSERS"));
						String FEST_ST_DATE=values.Nullcheck(rs.getString("FEST_ST_DATE"));
						
						
						String FEST_TYPE = values.Nullcheck(rs.getString("FEST_TYPE"));
						String FEST_WEBSITE=values.Nullcheck(rs.getString("FEST_WEBSITE"));
						String HW_REACH=values.Nullcheck(rs.getString("HW_REACH"));
						String REG_FEE=values.Nullcheck(rs.getString("REG_FEE"));
						String TWITTER_LINK =  values.Nullcheck(rs.getString("TWITTER_LINK"));
						String UR_EMAIL_ADDRS = values.Nullcheck(rs.getString("UR_EMAIL_ADDRS"));
						String UR_NAME=values.Nullcheck(rs.getString("UR_NAME"));
						String YOUR_PH_NUMBER=values.Nullcheck(rs.getString("YOUR_PH_NUMBER"));
						String YOUTUBE_LINK=values.Nullcheck(rs.getString("YOUTUBE_LINK"));
						String EVENTS_WRKSHP_PAPER_ID=values.Nullcheck(rs.getString("EVENTS_WRKSHP_PAPER_ID"));
						String FEST_BANNER=values.Nullcheck(rs.getString("FEST_BANNER"));
						String FEST_ID=values.Nullcheck(rs.getString("EVENTS_WRKSHP_PAPER_ID"));
						
						
						PreparedStatement stmt1=con.prepareStatement("select * from fests_workshop_list where id='"+EVENTS_WRKSHP_PAPER_ID+"'"); 
						
						ResultSet rs1=stmt1.executeQuery(); 	
						StringBuilder workshp = new StringBuilder();
						int inc1 = 1;
						while (rs1.next()) {
							if(rs1.getString("WORKSHOP_NAME")!=null && rs1.getString("WORKSHOP_NAME")!="") {
							workshp =  workshp.append("<b>"+inc1+": WORKSHOP NAME:</b> "+values.Nullcheck(rs1.getString("WORKSHOP_NAME"))+"<br>");
							if(rs1.getString("WORKSHOP_DESC")!=null && rs1.getString("WORKSHOP_DESC")!="")
							workshp =  workshp.append("<b>WORKSHOP DESC:</b> "+values.Nullcheck(rs1.getString("WORKSHOP_DESC"))+"<br>");
							if(rs1.getString("WORKSHOP_CO_NAME")!=null && rs1.getString("WORKSHOP_CO_NAME")!="")
							workshp =  workshp.append("<b>WORKSHOP CO-ORDINATION NAME:</b> "+values.Nullcheck(rs1.getString("WORKSHOP_CO_NAME"))+"<br>");
							if(rs1.getString("WORKSHOP_RULES_FORMAT")!=null && rs1.getString("WORKSHOP_RULES_FORMAT")!="")
							workshp =  workshp.append("<b>WORKSHOP RULES:</b> "+values.Nullcheck(rs1.getString("WORKSHOP_RULES_FORMAT"))+"<br>");
							if(rs1.getString("WORKSHOP_COOD_EMAIL")!=null && rs1.getString("WORKSHOP_COOD_EMAIL")!="")
								workshp =  workshp.append("WORKSHOP CO-ORDINATION EMAIL: "+values.Nullcheck(rs1.getString("WORKSHOP_COOD_EMAIL"))+"<br>");
							if(rs1.getString("WORKSHP_PHNO")!=null && rs1.getString("WORKSHP_PHNO")!="")
								workshp =  workshp.append("<b>WORKSHOP PH:</b>"+values.Nullcheck(rs1.getString("WORKSHP_PHNO"))+"<br>");
							
							
							inc1++;
						
							
							}
							
						}
						
						PreparedStatement stmt2=con.prepareStatement("select * from fests_paper_list where id='"+EVENTS_WRKSHP_PAPER_ID+"'"); 
						
						ResultSet rs2=stmt2.executeQuery(); 	
						StringBuilder paper = new StringBuilder();
						int inc2 = 1;
						while (rs2.next()) {
							if(rs2.getString("PAPER_PRE_NAME")!=null && rs2.getString("PAPER_PRE_NAME")!="") {
								paper =  paper.append("<b>"+inc2+": PAPER PRESENTATION NAME:</b> "+values.Nullcheck(rs2.getString("PAPER_PRE_NAME"))+"<br>");
							if(rs2.getString("PAPER_PRE_DESC")!=null && rs2.getString("PAPER_PRE_DESC")!="")
								paper =  paper.append("<b>PAPER PRESENTATION DESC:</b> "+values.Nullcheck(rs2.getString("PAPER_PRE_DESC"))+"<br>");
							if(rs2.getString("PAPER_PRE_BROC")!=null && rs2.getString("PAPER_PRE_BROC")!="")
								paper =  paper.append("<b>PAPER PRESENTATION BROCHURE:</b> "+values.Nullcheck(rs2.getString("PAPER_PRE_BROC"))+"<br>");
							if(rs2.getString("PAPER_PRE_RULES_FORMAT")!=null && rs2.getString("PAPER_PRE_RULES_FORMAT")!="")
								paper =  paper.append("<b>PAPER PRESENTATION RULES AND FORMAT:</b>"+values.Nullcheck(rs2.getString("PAPER_PRE_RULES_FORMAT"))+"<br>");
							if(rs2.getString("PAPER_PRES_EMAIL")!=null && rs2.getString("PAPER_PRES_EMAIL")!="")
								paper =  paper.append("<b>PAPER PRESENTATION EMAIL:</b>"+values.Nullcheck(rs2.getString("PAPER_PRES_EMAIL"))+"<br>");
							if(rs2.getString("PAPER_PRES_PHNO")!=null && rs2.getString("PAPER_PRES_PHNO")!="")
								paper =  paper.append("<b>PAPER PRESENTATION PHONE:</b>"+values.Nullcheck(rs2.getString("PAPER_PRES_PHNO"))+"<br>");
							inc2++;
						}}
						
						PreparedStatement stmt3=con.prepareStatement("select * from fest_events_list where id='"+EVENTS_WRKSHP_PAPER_ID+"'"); 
						
						ResultSet rs3=stmt3.executeQuery(); 	
						StringBuilder events = new StringBuilder();
						int inc3 = 1;
						while (rs3.next()) {
							if(rs3.getString("EVENT_NAME")!=null && rs3.getString("EVENT_NAME")!="") {
								events =  events.append("<b>"+inc3+": EVENT NAME:</b> "+values.Nullcheck(rs3.getString("EVENT_NAME"))+"<br>");
							if(rs3.getString("EVENT_DESC")!=null && rs3.getString("EVENT_DESC")!="")
								events =  events.append("<b>EVENT DESCRIPTION: </b>"+values.Nullcheck(rs3.getString("EVENT_DESC"))+"<br>");
							if(rs3.getString("EVENT_COORD_NAME")!=null && rs3.getString("EVENT_COORD_NAME")!="")
								events =  events.append("<b>EVENT COORDINATION NAME:</b> "+values.Nullcheck(rs3.getString("EVENT_COORD_NAME"))+"<br>");
							if(rs3.getString("EVENT_RULES_FORMAT")!=null && rs3.getString("EVENT_RULES_FORMAT")!="")
								events =  events.append("<b>EVENTS RULES AND FORMAT:</b>"+values.Nullcheck(rs3.getString("EVENT_RULES_FORMAT"))+"<br>");
							if(rs3.getString("EVENT_COORD_EMAIL")!=null && rs3.getString("EVENT_COORD_EMAIL")!="")
								events =  events.append("<b>EVENT COORDINATION EMAIL:</b>"+values.Nullcheck(rs3.getString("EVENT_COORD_EMAIL"))+"<br>");
							if(rs3.getString("EVENT_COORD_PHNO")!=null && rs3.getString("EVENT_COORD_PHNO")!="")
								events =  events.append("<b>EVENT COORDINATION PHONE:</b>"+values.Nullcheck(rs3.getString("EVENT_COORD_PHNO"))+"<br>");
							inc3++;
						}}
						

						//Date POSTED_DATE=rs.getDate("DATE");
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("CLG_EMAILID", CLG_EMAILID);
						Jsonloc1.put("CLG_LOC", CLG_LOC);
						Jsonloc1.put("CLG_NAME", CLG_NAME);
					    Jsonloc1.put("CLG_PH_NUM", CLG_PH_NUM);
						Jsonloc1.put("CLG_TYPE",CLG_TYPE);
						Jsonloc1.put("CLG_WEBSITE", CLG_WEBSITE);
						Jsonloc1.put("DEADLINE_REG", DEADLINE_REG);
						Jsonloc1.put("FACEBOOK_LINK", FACEBOOK_LINK);
						Jsonloc1.put("FEST_ACC", FEST_ACC);
						Jsonloc1.put("FEST_CAPTION", FEST_CAPTION);
						Jsonloc1.put("FEST_CONT_PERSN", FEST_CONT_PERSN);
						Jsonloc1.put("FEST_DEPRT", FEST_DEPRT);
						Jsonloc1.put("FEST_DESC", FEST_DESC);
						Jsonloc1.put("FEST_EMAIL_ADD", FEST_EMAIL_ADD);
						Jsonloc1.put("FEST_END_DATE", FEST_END_DATE);
						Jsonloc1.put("FEST_GUEST", FEST_GUEST);
						Jsonloc1.put("FEST_NAME", FEST_NAME);
						Jsonloc1.put("FEST_REG_URL", FEST_REG_URL);
						Jsonloc1.put("FEST_SPONSERS", FEST_SPONSERS);
						Jsonloc1.put("FEST_ST_DATE", FEST_ST_DATE);
						Jsonloc1.put("FEST_TYPE", FEST_TYPE);
						Jsonloc1.put("FEST_WEBSITE", FEST_WEBSITE);
						Jsonloc1.put("HW_REACH", HW_REACH);
						Jsonloc1.put("REG_FEE", REG_FEE);
						Jsonloc1.put("TWITTER_LINK", TWITTER_LINK);
						Jsonloc1.put("UR_EMAIL_ADDRS", UR_EMAIL_ADDRS);
						Jsonloc1.put("UR_NAME", UR_NAME);
						Jsonloc1.put("YOUR_PH_NUMBER", YOUR_PH_NUMBER);
						Jsonloc1.put("YOUTUBE_LINK", YOUTUBE_LINK);
						Jsonloc1.put("FEST_BANNER", FilePath+FEST_BANNER);
						Jsonloc1.put("FEST_BANNER_VERIFIED",FEST_BANNER);
						Jsonloc1.put("WORKSHOP_DATA", workshp.toString());
						Jsonloc1.put("PAPER_PRES_DATA", paper.toString());
						Jsonloc1.put("EVENTS_DATA", events.toString());
						Jsonloc1.put("FEST_ID", FEST_ID.toString());
						Jsonloc1.put("CLG_KEYS", clgkeys.toString());
						
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
		
		
		if (functionId.equalsIgnoreCase("GET_UPLOADED_NEWS")) {

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


					PreparedStatement stmt=con.prepareStatement("select * from news_upload where NEWS_NEW='Y'"); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						String NEWS_ID =  values.Nullcheck(rs.getString("NEWS_ID"));
						String NEWS_UPLOADER_NAME =  values.Nullcheck(rs.getString("NEWS_UPLOADER_NAME"));
						String NEWS_REF_URL =  values.Nullcheck(rs.getString("NEWS_REF_URL"));
						String NEWS_HEADER = values.Nullcheck(rs.getString("NEWS_HEADER"));
						Date POSTED_DATE=rs.getDate("DATE");
						
						String NEWS_CONTENT=values.Nullcheck(rs.getString("NEWS_CONTENT"));
						String NEWS_IMAGES=values.Nullcheck(rs.getString("NEWS_IMAGES"));
						String NEWS_IMAGE_UPLOAD=values.Nullcheck(rs.getString("NEWS_IMAGES"));
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("NEWSID", NEWS_ID);
						Jsonloc1.put("NEWS_HEADER", NEWS_HEADER);
						Jsonloc1.put("POSTED_DATE", POSTED_DATE.toString());
					
						Jsonloc1.put("NEWS_DETAILS", NEWS_CONTENT);
						Jsonloc1.put("NEWS_IMAGE",FilePath+NEWS_IMAGES);
						Jsonloc1.put("NEWS_IMAGE_UPLOAD", NEWS_IMAGE_UPLOAD);
						Jsonloc1.put("NEWS_UPLOADER_NAME", NEWS_UPLOADER_NAME);
						Jsonloc1.put("NEWS_REF_URL", NEWS_REF_URL);
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