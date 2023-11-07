package org.UploadWorkspace;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import javax.rmi.CORBA.Util;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.AmazonSupport.AmazonSupportUploads;
import org.HomeWorkspace.UserDataMaintance;
import org.HomeWorkspace.UserLogedInSessMaintaince;
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


@WebServlet("/UserNewsUpload")
public class UserNewsUpload extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter(); 
		
		
		
		JSONObject JSOBVAL= new JSONObject();	
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String reqTypetesrrt = "";
        if(br != null){
        	reqTypetesrrt = br.readLine();
        }
		//String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONArray jsonimages = obj1.getJSONArray("imageList");
//JSONArray jsonimages =new JSONArray(obj1.getString("imageList"));
		
String questionmarks="";
String imagesUploaded="";
Boolean check=true;
Configuration imgconfig = null;
try {
	imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
} catch (ConfigurationException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
String FilePath_upload = imgconfig.getString("img_newupload_images");
String allImages="";

for(int img1=0;img1<jsonimages.length();img1++){
	check=false;
	
	
	imagesUploaded=imagesUploaded.concat(",image_"+img1);
	questionmarks=questionmarks.concat(",?");
	
	
	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
		   LocalDateTime now = LocalDateTime.now(); 
	 
	String val=	 "NEWS"+"_"+(dtf.format(now))+".jpg";
	allImages=val+"|";
	byte[] dande= Base64.decodeBase64(jsonimages.getString(img1));
	AmazonSupportUploads req = new AmazonSupportUploads();
	 check= req.ImageUpload(dande,val,FilePath_upload);
}

java.util.Date dt = new java.util.Date();

java.text.SimpleDateFormat sdf = 
     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
ZoneId singaporeZoneId = ZoneId.of("Asia/Kolkata");
sdf.setTimeZone(TimeZone.getTimeZone(singaporeZoneId));
java.text.SimpleDateFormat sdf1 = 
new java.text.SimpleDateFormat("yyyyMMddHHmmss");
ZoneId singaporeZoneId1 = ZoneId.of("Asia/Kolkata");
sdf1.setTimeZone(TimeZone.getTimeZone(singaporeZoneId1));
String currentTime1 = sdf1.format(dt);
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
	          PreparedStatement stmt = con.prepareStatement("insert into user_uploadnews(USERMAIL,DATE,USERNAME,NEWS_HEADER,NEWS_CONTENT,PH_NUMBER,NEWS_IMAGES) values(?,?,?,?,?,?,?)");
	  
	          stmt.setString(1,obj1.getString("usermail"));
	          stmt.setString(2,currentTime);
	          stmt.setString(3,obj1.getString("username"));
	         
	          stmt.setString(4,obj1.getString("NEWS_HEADER"));
	          stmt.setString(5,obj1.getString("NEWS_DESC"));
	          stmt.setString(6,obj1.getString("USER_PHNO"));
	          stmt.setString(7,allImages);

	        
	          //ResultSet rs = stmt.executeQuery();	
	          int i=stmt.executeUpdate();  

	          System.out.println(i+" records inserted");  
	          
	          con.close();
				int inc = 0;
				
				if(i>0 && check){
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
	         e2.printStackTrace();
	        }
				}
		
			 
			 
			 }
		
		