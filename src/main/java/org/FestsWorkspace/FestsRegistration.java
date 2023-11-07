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


@WebServlet("/FestsRegistration")
public class FestsRegistration extends HttpServlet {

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
String FilePath_upload = imgconfig.getString("img_FestsRegistration");
String allImages="";

for(int img1=0;img1<jsonimages.length();img1++){
	check=false;
	
	
	imagesUploaded=imagesUploaded.concat(",image_"+img1);
	questionmarks=questionmarks.concat(",?");
	
	
	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
		   LocalDateTime now = LocalDateTime.now(); 
	 
	String val=	  obj1.getString("fest_college_name")+"_"+(dtf.format(now))+".jpg";
	allImages=val+"|";
	byte[] dande= Base64.decodeBase64(jsonimages.getString(img1));
	AmazonSupportUploads req = new AmazonSupportUploads();
	 check= req.ImageUpload(dande,val,FilePath_upload);
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
	          PreparedStatement stmt = con.prepareStatement("insert into fests_registration(fest_college_name,college_website,college_location,fest_name,fest_caption,fest_email_add,fest_type,"
	          		+ "fest_description,fest_depart,start_date,end_date,fest_event,fest_workshop,fest_paper,fest_guest,fest_sponsors,fest_dead_registration,fest_Regis_fees,fest_website"
	          		+ ",fest_regis_url,fest_links,fest_reach,Fest_contact_persons"+imagesUploaded+")   values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"+questionmarks+")");
	          stmt.setString(1,obj1.getString("fest_college_name"));
	          stmt.setString(2,obj1.getString("college_website"));
	          stmt.setString(3,obj1.getString("college_location"));
	          stmt.setString(4,obj1.getString("fest_name"));
	          stmt.setString(5,obj1.getString("fest_caption"));
	          stmt.setString(6,obj1.getString("fest_email_add"));
	          stmt.setString(7,obj1.getString("fest_type"));
	          stmt.setString(8,obj1.getString("fest_description"));
	          stmt.setString(9,obj1.getString("fest_depart"));
	          stmt.setString(10,obj1.getString("start_date"));
	          stmt.setString(11,obj1.getString("end_date"));
	          stmt.setString(12,obj1.getString("fest_event"));
	          stmt.setString(13,obj1.getString("fest_workshop"));
	          stmt.setString(14,obj1.getString("fest_paper"));
	          stmt.setString(15,obj1.getString("fest_guest"));
	          stmt.setString(16,obj1.getString("fest_sponsors"));
	          stmt.setString(17,obj1.getString("fest_dead_registration"));
	          stmt.setString(18,obj1.getString("fest_Regis_fees"));
	          stmt.setString(19,obj1.getString("fest_website"));
	          stmt.setString(20,obj1.getString("fest_regis_url"));
	          stmt.setString(21,obj1.getString("fest_links"));
	          stmt.setString(22,obj1.getString("fest_reach"));
	          stmt.setString(23,obj1.getString("Fest_contact_persons"));
	    
	        for(int img=0;img<jsonimages.length();img++){
	        	 stmt.setString(24+img,jsonimages.getString(img));

	        		//   fos.write();
	        		// FileOutputStream fos = new FileOutputStream("C:/dande/"+obj1.getString("fest_college_name")+"_"+(dtf.format(now))+".jpg");

	        		// fos.write(dande);
	        		  // fos.close();

	        		}
	        
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
		
		