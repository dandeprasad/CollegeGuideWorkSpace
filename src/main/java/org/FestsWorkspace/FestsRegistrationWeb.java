package org.FestsWorkspace;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.rmi.CORBA.Util;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.AdmissionsWorkspace.SMSGateway;
import org.AmazonSupport.AmazonSupportUploads;
import org.HomeWorkspace.MyUtility;
import org.HomeWorkspace.UserDataMaintance;
import org.HomeWorkspace.UserLogedInSessMaintaince;
import org.UploadWorkspace.EmployeeUpdateNotify;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;


import mailService.SMSEmailUsingGMailSMTP;


@WebServlet("/FestsRegistrationWeb")
public class FestsRegistrationWeb extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");  

		  response.addHeader("Access-Control-Allow-Origin", "*");
	        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept charset=utf-8");
	        
		PrintWriter out = response.getWriter(); 
		
		String reqTypetesrrt = (String) request.getParameter("ServerData");
		
		JSONObject JSOBVAL= new JSONObject();	
//        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
//        String reqTypetesrrt = "";
//        if(br != null){
//        	reqTypetesrrt = br.readLine();
//        }
		//String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		MyUtility values = new MyUtility();
		

		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
		   LocalDateTime now = LocalDateTime.now(); 
		 String timenow =   (dtf.format(now));
    
		Configuration config = null;
		Connection con=null;    
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
				Statement stmtbatch,stmtbatch1,stmtbatch2 = null;
		        try
		        {
		          Class.forName(Driver);
		          
		       
		           con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);
		          stmtbatch = con.createStatement();
		      	JSONObject paperpres = 	obj1.getJSONObject("paperPersn");

		        int lenghtl = paperpres.length();
		     
		        for (int i = 0; i < lenghtl; i++) {
		        	
		            JSONObject clgdata = (JSONObject) paperpres.get(String.valueOf(i));

		            String query = "insert into fests_paper_list values ('"+escapechar(obj1.get("CLG_NAME"))+"_"+timenow+"','"+escapechar(clgdata.get("PAPER_PRES_EMAIL"))+"','"+escapechar(clgdata.get("PAPER_PRE_BROC"))+"','"+escapechar(clgdata.get("PAPER_PRE_DESC"))+"','"+escapechar(clgdata.get("PAPER_PRE_NAME"))+"','"+escapechar(clgdata.get("PAPER_PRE_RULES_FORMAT"))+"',NOW(),'"+ escapechar(clgdata.get("PAPER_PRES_PHNO"))+"')";
		           
		            stmtbatch.addBatch(query);
					if(i==paperpres.length()-1) 
					{
						stmtbatch.executeBatch();
					}
						
					
		        }
		        stmtbatch.close();
		        
		        stmtbatch1 = (Statement) con.createStatement(); 
		      	JSONObject events = 	obj1.getJSONObject("events");

		     
		        for (int i = 0; i < events.length(); i++) {
		        	
		            JSONObject clgdata = (JSONObject) events.get(String.valueOf(i));

		            String query = "insert into fest_events_list values ('"+escapechar(obj1.get("CLG_NAME"))+"_"+timenow+"','"+escapechar(clgdata.get("EVENT_NAME"))+"','"+escapechar(clgdata.get("EVENT_DESC"))+"','"+escapechar(clgdata.get("EVENT_RULES_FORMAT"))+"','"+escapechar(clgdata.get("EVENT_COORD_NAME"))+"','"+escapechar(clgdata.get("EVENT_COORD_EMAIL"))+"',NOW(),'"+escapechar(clgdata.get("EVENT_COORD_PHNO"))+"')";
		          
		            stmtbatch1.addBatch(query);
					if(i==events.length()-1) 
					{
						stmtbatch1.executeBatch();
					}
						
					
		        }
		        stmtbatch1.close();
		        stmtbatch2 = (Statement) con.createStatement(); 
		      	JSONObject workshops = 	obj1.getJSONObject("workshops");

		     
		        for (int i = 0; i < workshops.length(); i++) {
		        	
		            JSONObject clgdata = (JSONObject) workshops.get(String.valueOf(i));

		            String query = "insert into fests_workshop_list values ('"+escapechar(obj1.get("CLG_NAME"))+"_"+timenow+"','"+escapechar(clgdata.get("WORKSHOP_NAME"))+"','"+escapechar(clgdata.get("WORKSHOP_DESC"))+"','"+escapechar(clgdata.get("WORKSHOP_CO_NAME"))+"','"+escapechar(clgdata.get("WORKSHOP_RULES_FORMAT"))+"','"+escapechar(clgdata.get("WORKSHOP_COOD_EMAIL"))+"',NOW(),'"+escapechar(clgdata.get("WORKSHP_PHNO"))+"')";
		            
		            stmtbatch2.addBatch(query);
					if(i==workshops.length()-1) 
					{
						stmtbatch2.executeBatch();
					}
						
					
		        }
		        stmtbatch2.close();
		          
		        String mainquery = "insert into fests_registration_web(CLG_EMAILID,CLG_LOC,CLG_NAME,CLG_PH_NUM,CLG_TYPE,CLG_WEBSITE,DEADLINE_REG,"
		          		+ "FACEBOOK_LINK,FEST_ACC,FEST_CAPTION,FEST_CONT_PERSN,FEST_DEPRT,FEST_DESC,FEST_EMAIL_ADD,FEST_END_DATE,FEST_GUEST,FEST_NAME,FEST_REG_URL,FEST_SPONSERS"
		          		+ ",FEST_ST_DATE,FEST_TYPE,FEST_WEBSITE,HW_REACH,REG_FEE,TWITTER_LINK,UR_EMAIL_ADDRS,UR_NAME,YOUR_PH_NUMBER,YOUTUBE_LINK,EVENTS_WRKSHP_PAPER_ID,FEST_BANNER,FEST_NEW,CLG_STATE,CLG_CITY)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		   
	          PreparedStatement stmt = con.prepareStatement(mainquery);
	          stmt.setString(1,escapechar(obj1.getString("CLG_EMAILID")));
	          stmt.setString(2,escapechar(obj1.getString("CLG_LOC")));
	          stmt.setString(3,escapechar(obj1.getString("CLG_NAME")));
	          stmt.setString(4, escapechar(obj1.getString("CLG_PH_NUM")));
	          stmt.setString(5,escapechar(obj1.getString("CLG_TYPE")));
	          stmt.setString(6,escapechar(obj1.getString("CLG_WEBSITE")));
	          stmt.setString(7,escapechar(obj1.getString("DEADLINE_REG")));
	          stmt.setString(8,escapechar(obj1.getString("FACEBOOK_LINK")));
	          stmt.setString(9,escapechar(obj1.getString("FEST_ACC")));
	          stmt.setString(10,escapechar(obj1.getString("FEST_CAPTION")));
	          stmt.setString(11,escapechar(obj1.getString("FEST_CONT_PERSN")));
	          stmt.setString(12,escapechar(obj1.getString("FEST_DEPRT")));
	          stmt.setString(13,escapechar(obj1.getString("FEST_DESC")));
	          stmt.setString(14,escapechar(obj1.getString("FEST_EMAIL_ADD")));
	          stmt.setString(15,escapechar(obj1.getString("FEST_END_DATE")));
	          stmt.setString(16,escapechar(obj1.getString("FEST_GUEST")));
	          stmt.setString(17,escapechar(obj1.getString("FEST_NAME")));
	          stmt.setString(18,escapechar(obj1.getString("FEST_REG_URL")));
	          stmt.setString(19,escapechar(obj1.getString("FEST_SPONSERS")));
	          stmt.setString(20,escapechar(obj1.getString("FEST_ST_DATE")));
	          stmt.setString(21,escapechar(obj1.getString("FEST_TYPE")));
	          stmt.setString(22,escapechar(obj1.getString("FEST_WEBSITE")));
	          stmt.setString(23,escapechar(obj1.getString("HW_REACH")));
	    	          stmt.setString(24,escapechar(obj1.getString("REG_FEE")));
	    	          stmt.setString(25,escapechar(obj1.getString("TWITTER_LINK")));
	    	          stmt.setString(26,escapechar(obj1.getString("UR_EMAIL_ADDRS")));
	    	          stmt.setString(27,escapechar(obj1.getString("UR_NAME")));
	    	          stmt.setString(28, escapechar(obj1.getString("YOUR_PH_NUMBER")));
	    	          stmt.setString(29,escapechar(obj1.getString("YOUTUBE_LINK")));
	    	          stmt.setString(30,escapechar(obj1.get("CLG_NAME")+"_"+timenow));
	    	          stmt.setString(31,escapechar(obj1.getString("FEST_BANNER_IMG_SUCCESS")));
	    	          stmt.setString(32,"Y");
	    	          stmt.setString(33,escapechar(obj1.getString("CLG_STATE")));
	    	          stmt.setString(34,escapechar(obj1.getString("CLG_CITY")));

	        		//   fos.write();
	        		// FileOutputStream fos = new FileOutputStream("C:/dande/"+obj1.getString("fest_college_name")+"_"+(dtf.format(now))+".jpg");

	        		// fos.write(dande);
	        		  // fos.close();

	        		
	        
	          //ResultSet rs = stmt.executeQuery();	
	          int i=stmt.executeUpdate();  

	          System.out.println(i+" records inserted");  
	          
	        
				int inc = 0;
				
				if(i>0){
				String RESULTCODE = "000000";
			
				JSONObject Jsonloc1= new JSONObject();
			
			
				Jsonloc1.put("RESULTCODE", RESULTCODE);
				String x1  = Integer.toString(inc);
				JSOBVAL.put(x1, Jsonloc1);
				
				
			

			    
				String[] users_to_notify = (config.getString("Notify_users")).split("\\|");
				StringBuilder data = new StringBuilder();
					for (int usr=0;usr<users_to_notify.length;usr++) {
					//	SMSGateway sms = new SMSGateway();
					//	sms.SendMessage("User Uploaded Fest: "+obj1.getString("FEST_NAME"),users_to_notify[usr]);
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
						dan.newsmessage(TOKEN_ID,obj1.getString("FEST_NAME").toString(),"Hello! Fest Uploaded");
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					  
				        

					    response.getWriter().write(JSOBVAL.toString());
				}
				  con.close();
			  
	        }
	        
	        catch (Exception e2)
	        {
	        	  
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
	        	int inc = 0;
				String RESULTCODE = "999999";
				
				JSONObject Jsonloc1= new JSONObject();
			
			
				Jsonloc1.put("RESULTCODE", RESULTCODE);
				String x1  = Integer.toString(inc);
				JSOBVAL.put(x1, Jsonloc1);
				
				
			
			  response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			  
		        

			    response.getWriter().write(JSOBVAL.toString());
	       System.out.println(e2);  
	     
	        }
				}
		
			 public String  escapechar(Object object ) {
				 return object.toString().replace("'", "\\'");
			 }
			 
			 }
		
		