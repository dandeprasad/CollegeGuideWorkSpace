package org.HomeWorkspace;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.AmazonSupport.AmazonSupportUploads;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;
import org.json.JSONTokener;

import mailService.SMSEmailUsingGMailSMTP;
import mailService.SMSEmailUsingSuccessregister;


@WebServlet("/UserRegistrationPassWay")
public class UserRegistrationPassWay extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter(); 
		
		
		
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		 JSONObject json= (JSONObject) new JSONTokener(reqTypetesrrt).nextValue();
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		String workspaceId = geoObject.getString("WORKSPACE_ID");
		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");
		

	
		 if (functionId.equalsIgnoreCase("USER_MANUAL_REGISTER"))
	      {
			 if(geoObject.getString("MODE").equalsIgnoreCase("OTP_TRIGGER")){
				 UserDataMaintance userdata = new UserDataMaintance();
				 userdata.setUSER_EMAIL(geoObject.getString("USER_EMAIL"));
				 userdata.setUSER_PASSWORD(geoObject.getString("USER_PASSWORD"));
				 userdata.setUSER_MOBILENO(geoObject.getString("USER_MOBILENO"));
				 SMSEmailUsingGMailSMTP trigger = new  SMSEmailUsingGMailSMTP();
				 UserDataMaintance returnvalue =  trigger.OTPTRIGGER(userdata);
				 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				 Date date = new Date();
				 String datetosend = dateFormat.format(date); 
				
				
				   

				  String geoId ="ALL_NEWS";
				
				
			
				  
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


						PreparedStatement stmt=con.prepareStatement("INSERT INTO OTP_USER_DATA  VALUES (?, ?,?)"); 
						stmt.setString(1,returnvalue.getUSER_EMAIL());//1 specifies the first parameter in the query  
						stmt.setString(2,returnvalue.getOTP_TRIGGER_VALUE()); 
						stmt.setString(3,datetosend); 
						int i=stmt.executeUpdate();  
						System.out.println(i+" records inserted");  
						  
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
			 else if (geoObject.getString("MODE").equalsIgnoreCase("REGISTER_USER")){
	        String StringToSend = null;
	        
	        StringToSend = actionId;
			  Configuration config = null;
				 UserDataMaintance userdata = new UserDataMaintance();
				 userdata.setUSER_EMAIL(geoObject.getString("USER_EMAIL"));
				 userdata.setUSER_PASSWORD(geoObject.getString("USER_PASSWORD"));
				 userdata.setUSER_MOBILENO(geoObject.getString("USER_MOBILENO"));
				 userdata.setUSR_ENTERD_OTP(geoObject.getString("USER_ENTRD_OTP"));
				Boolean DB_OTP_STATUS =OTPVerification(userdata);
				if(DB_OTP_STATUS){
					
					 
				    
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
	          PreparedStatement stmt = con.prepareStatement("insert into  users_data values(?,?,?)");
	          stmt.setString(1,userdata.getUSER_EMAIL());
	          stmt.setString(2, userdata.getUSER_MOBILENO());
	          stmt.setString(3, userdata.getUSER_PASSWORD());
	        
	          //ResultSet rs = stmt.executeQuery();	
	          int i=stmt.executeUpdate();  

	          System.out.println(i+" records inserted");  
	          
	          con.close();
				int inc = 0;
				
				if(i>0){
				String RESULTCODE = "000000";
			
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
	          System.out.println(e2);
	        }
				}
				else{
					int inc=0;
					String RESULTCODE = "999999";
					
					JSONObject Jsonloc1= new JSONObject();
				
				
					Jsonloc1.put("RESULTCODE", RESULTCODE);
					String x1  = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;
					
				
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(JSOBVAL.toString());
				}	
			 
			 
			 }}
		
		 if (functionId.equalsIgnoreCase("USER_LOGIN_FORALL"))
	      {
			 String actionIdda = geoObject.getString("user_info");
				JSONObject usr_data = new JSONObject(actionIdda);
				String LOGIN_VERIFY_STATUS=null;
				if((usr_data.getString("LOGIN_TYPE")).equalsIgnoreCase("GOOGLE_LOGIN") || (usr_data.getString("LOGIN_TYPE")).equalsIgnoreCase("FACEBOOK_LOGIN")){
					
					 LOGIN_VERIFY_STATUS ="password_true";			
					}
				else{
			 LOGIN_VERIFY_STATUS =	UserLoginVerification(usr_data ,request,response);
				}
	if (LOGIN_VERIFY_STATUS.equalsIgnoreCase("password_true")){
		
		CreateUserSession(usr_data,request,response);
	}
	else if(LOGIN_VERIFY_STATUS.equalsIgnoreCase("password_false")) {

		int inc=0;
		String RESULTCODE = "999999";
		
		JSONObject Jsonloc1= new JSONObject();
	
	
		Jsonloc1.put("RESULTCODE", RESULTCODE);
		String x1  = Integer.toString(inc);
		JSOBVAL.put(x1, Jsonloc1);
		inc++;
		
	
	  response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(JSOBVAL.toString());
		
	}
	else if (LOGIN_VERIFY_STATUS.equalsIgnoreCase("register_false")){
		int inc=0;
		String RESULTCODE = "000012";
	
		JSONObject Jsonloc1= new JSONObject();
	
	
		Jsonloc1.put("RESULTCODE", RESULTCODE);
		String x1  = Integer.toString(inc);
		JSOBVAL.put(x1, Jsonloc1);
		
		
	
	  response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(JSOBVAL.toString());
      System.out.println("No records found");
		
	}
	        
	        
	      }
	}



	private String UserLoginVerification(JSONObject usr_data, HttpServletRequest request, HttpServletResponse response) {


		Configuration config = null;
		String USER_LOGIN_STATUS="";
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
      PreparedStatement stmt = con.prepareStatement("select PASSWORD from users_data where MAIL_ID='"+usr_data.getString("USERNAME_EMAIL")+"'");

      ResultSet rs = stmt.executeQuery();	
   //   int rs=stmt.executeUpdate();  

     
      String PASSWORD=null;
      int count = 0;
    
		while (rs.next()) {	
			  ++count;
			PASSWORD = rs.getString("PASSWORD");

			
		}
	      if (count == 0) {
	    	  USER_LOGIN_STATUS="register_false";
	    		
	         
	      }
	
	      else if((usr_data.getString("PASSWORD")).equalsIgnoreCase(PASSWORD)){
			USER_LOGIN_STATUS="password_true";
		}
		else{
			USER_LOGIN_STATUS="password_false";
		};
      con.close();
    }
    
    catch (Exception e2)
    {
      System.out.println(e2);
    }
		return USER_LOGIN_STATUS;

	
		
	}

	private Boolean OTPVerification(UserDataMaintance userdata) {
		// TODO Auto-generated method stub
		Configuration config = null;
		Boolean USER_COMPARATION=false;
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
      PreparedStatement stmt = con.prepareStatement("select OTP_VALUE from OTP_USER_DATA where MAIL_ID='"+userdata.getUSER_EMAIL()+"'");

      ResultSet rs = stmt.executeQuery();	
   //   int rs=stmt.executeUpdate();  

     
      String OTP_VALUE=null;
		while (rs.next()) {
			 OTP_VALUE = rs.getString("OTP_VALUE");

			
		}
	
		if((userdata.getUSR_ENTERD_OTP()).equalsIgnoreCase(OTP_VALUE)){
			USER_COMPARATION=true;
		};
      con.close();
    }
    
    catch (Exception e2)
    {
      System.out.println(e2);
    }
		return USER_COMPARATION;

	}
	public void CreateUserSession(JSONObject usr_data, HttpServletRequest request, HttpServletResponse response) {
		 HttpSession sessionprevious=request.getSession(false);  
		 if(null !=sessionprevious){
		 sessionprevious.invalidate();
		 }
 
		UserLogedInSessMaintaince usersess = new UserLogedInSessMaintaince();
		usersess.setUSERNAME_EMAIL(usr_data.getString("USERNAME_EMAIL"));
		 HttpSession session=request.getSession(true);  
	      session.setAttribute("userSessionData",usersess); 
	      
/*	      Configuration imgconfig = null;
	      try {
	      	imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
	      } catch (ConfigurationException e) {
	      	// TODO Auto-generated catch block
	      	e.printStackTrace();
	      }
	      String FilePath_upload = imgconfig.getString("img_user_profile_images");
	      if(!usr_data.getString("USER_IMAGE").equalsIgnoreCase("")){
	  	byte[] dande= Base64.decodeBase64(usr_data.getString("USER_IMAGE"));
		AmazonSupportUploads req = new AmazonSupportUploads();
		
		Boolean check= req.ImageUpload(dande,usr_data.getString("USERNAME_EMAIL"),FilePath_upload);
	      }*/
	      java.util.Date dt = new java.util.Date();

	      java.text.SimpleDateFormat sdf = 
	           new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 	 ZoneId singaporeZoneId = ZoneId.of("Asia/Kolkata");
		 sdf.setTimeZone(TimeZone.getTimeZone(singaporeZoneId));
	      String currentTime = sdf.format(dt);
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
        PreparedStatement stmt = con.prepareStatement("insert into  user_session_maintaince values(?,?,?,?,?,?)");
        stmt.setString(1,usr_data.getString("USERNAME_EMAIL"));
        stmt.setString(2, session.getId());
        stmt.setString(3, usr_data.getString("deviceMan"));
        stmt.setString(4, usr_data.getString("deviceName"));
        stmt.setString(5, usr_data.getString("USER_IMAGE"));
        stmt.setString(6,currentTime);
        
        
        
        
      
        //ResultSet rs = stmt.executeQuery();	
        int i=stmt.executeUpdate();  

        System.out.println(i+" records inserted");  
        
        con.close();
			int inc = 0;
			
			if(i>0){
			String RESULTCODE = "000000";
		
			JSONObject Jsonloc1= new JSONObject();
		
			Jsonloc1.put("USER_EMAIL_RETURN", usr_data.getString("USERNAME_EMAIL"));
			Jsonloc1.put("USER_USERNAME", usr_data.getString("USERNAME"));
			Jsonloc1.put("userSessionDataID", session.getId());
			Jsonloc1.put("RESULTCODE", RESULTCODE);
			String x1  = Integer.toString(inc);
			JSOBVAL.put(x1, Jsonloc1);
			
/*			SMSEmailUsingSuccessregister trigger = new  SMSEmailUsingSuccessregister();
		 trigger.SUCCESSFULLREGISTER(usr_data.getString("USERNAME_EMAIL"));
		 */
		  response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(JSOBVAL.toString());
			}
			else{
				int inc1 = 0;
				String RESULTCODE = "12345";
				
				JSONObject Jsonloc1= new JSONObject();
			
				Jsonloc1.put("USER_EMAIL_RETURN","");
				Jsonloc1.put("USER_USERNAME", "");
				Jsonloc1.put("userSessionDataID", session.getId());
				Jsonloc1.put("RESULTCODE", RESULTCODE);
				String x1  = Integer.toString(inc1);
				JSOBVAL.put(x1, Jsonloc1);
				
	/*			SMSEmailUsingSuccessregister trigger = new  SMSEmailUsingSuccessregister();
			 trigger.SUCCESSFULLREGISTER(usr_data.getString("USERNAME_EMAIL"));
			 */
			  response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(JSOBVAL.toString());
			}
		  
      }
      
      catch (Exception e2)
      {
        System.out.println(e2);
      }
			
	      
	}
}