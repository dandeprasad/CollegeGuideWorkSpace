package org.UploadWorkspace;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.HomeWorkspace.MyUtility;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CallingJob implements Job
{
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		
		
		MyUtility values = new MyUtility();
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
		

			String Driver = config.getString("Driver");
			String ConnectionSpace = config.getString("ConnectionSpace");
			String Db_user = config.getString("Db_user");
			String Db_password = config.getString("Db_password");
			
	        try
	        {
	          Class.forName(Driver);
	          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);

				java.util.Date dt = new java.util.Date();

				java.text.SimpleDateFormat sdf = 
				     new java.text.SimpleDateFormat("yyyy-MM-dd");

				String currentTime = sdf.format(dt);
				
				PreparedStatement stmt=con.prepareStatement("select * from collegeexplore_notification where POSTED_DATE='"+currentTime+"' and  NOTIFY_NEED='Y'"); 
				
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
		

				while (rs.next()) {
					
					String APP_KEY =  values.Nullcheck(rs.getString("APP_KEY"));
					int  NOTIFY_ID =  rs.getInt("NOTIFY_ID");
					String NOTIFY_TITLE =  values.Nullcheck(rs.getString("NOTIFY_TITLE"));
					
					String NOTIFY_DETAIL_MSG = values.Nullcheck(rs.getString("NOTIFY_DETAIL_MSG"));
					
					Date POSTED_DATE=rs.getDate("POSTED_DATE");
					
					String NOTIFY_IMAGE=values.Nullcheck(rs.getString("NOTIFY_IMAGE"));
					
					String NOTIFY_TYPE=values.Nullcheck(rs.getString("NOTIFY_TYPE"));
					
					String UNIQUE_DATA1=values.Nullcheck(rs.getString("UNIQUE_DATA1"));
					
					String DATA2=values.Nullcheck(rs.getString("DATA2"));
					String DATA3=values.Nullcheck(rs.getString("DATA3"));
					String input=null;
					if (NOTIFY_TYPE.equals("NEWS_COLLEGEEXPLORE")) {
						String FilePath = imgconfig.getString("img_NewsFragmentAdaptor");	
						String imagepath = FilePath+NOTIFY_IMAGE;
						
						 input ="{ \"to\" : \"/topics/NEWS\",\"priority\": \"high\",\"data\": {\"MYDATA\":{ \"USERNAME\": \"\",\"APP_KEY\": \""+APP_KEY+"\",\"TITLE\": \""+NOTIFY_TITLE+"\",\"MESSAGE_DETAIL\": \""+NOTIFY_DETAIL_MSG+"\",\"data\": { \"NEWS_HEADER\": \""+DATA2+"\",\"DATA_IMAGE\":\""+imagepath+"\",\"NEWS_DETAILS\":\""+DATA3+"\",\"NEWS_IMAGE\":\""+imagepath+"\"}}}}";

					}
					else if (NOTIFY_TYPE.equals("EXAMS_COLLEGEEXPLORE")) {
						
						String FilePath = imgconfig.getString("img_HomeExamsFragmentAdaptor");
						String imagepath = FilePath+NOTIFY_IMAGE;
					 input = "{ \"to\" : \"/topics/NEWS\",\"priority\": \"high\",\"data\": {\"MYDATA\":{\"USERNAME\": \"\",\"APP_KEY\": \""+APP_KEY+"\",\"TITLE\": \""+NOTIFY_TITLE+"\",\"MESSAGE_DETAIL\": \""+NOTIFY_DETAIL_MSG+"\",\"data\": {\"EXAM_ID\": \""+UNIQUE_DATA1+"\",\"DATA_IMAGE\":\""+imagepath+"\"}}}}";
					}
	else if (NOTIFY_TYPE.equals("FEST_COLLEGEEXPLORE")) {
						
						String FilePath = imgconfig.getString("img_Fests_data");
						String imagepath = FilePath+NOTIFY_IMAGE;
					
	
	
					 input = "{ \"to\" : \"/topics/NEWS\",\"priority\": \"high\",\"data\": {\"MYDATA\":{\"USERNAME\": \"\",\"APP_KEY\": \"FESTS\",\"TITLE\": \""+NOTIFY_TITLE+"\",\"MESSAGE_DETAIL\": \""+NOTIFY_DETAIL_MSG+"\",\"data\": {\"CLG_ID\": \""+UNIQUE_DATA1+"\",\"BANNER_LOGO\":\""+imagepath+"\",\"FEST_CLG\":\""+NOTIFY_TITLE+"\"}}}}";
	}
					
					if(input!=null) {
						
					
					
					
					   PreparedStatement stmt1 = con.prepareStatement("UPDATE collegeexplore_notification set NOTIFY_NEED='N' where NOTIFY_ID="+NOTIFY_ID+"");
				          //ResultSet rs = stmt.executeQuery();	
				          int i1=stmt1.executeUpdate();  

				          UsersGetNotify req = new UsersGetNotify();
							req.newsmessage(input);
					    
					    
					
					}
				
				 
			}
				
PreparedStatement stmt1=con.prepareStatement("select * from custom_notification where NOTIFY_NEED='Y'"); 
				
				ResultSet rs1=stmt1.executeQuery(); 
				
			
				
				while (rs1.next()) {
					String NOTIFICATION_MESSAGE = values.Nullcheck(rs1.getString("NOTIFICATION_MESSAGE"));
					String NOTIFICATION_HEADER = values.Nullcheck(rs1.getString("NOTIFICATION_HEADER"));
					String NOTIFY_ID = values.Nullcheck(rs1.getString("NOTIFY_ID"));
					
					String input = "{\"to\" : \"/topics/NEWS\",\"priority\": \"high\",\"data\": {\"MYDATA\": {\"APP_KEY\": \"BASIC\",\"TITLE\": \""+NOTIFICATION_HEADER+"\",\"MESSAGE_DETAIL\": \""+NOTIFICATION_MESSAGE+"\"}}}";
					//String input = "{\"to\": \"dnNlSlUKaO4:APA91bH5E_13-sHydhkDhcVd6B9_qQBHEy3GxQseviTHWiaBwaFCvVFJsKsvJCPMUGNddL61ReMDNhdZPJQ3zHCy1lZ9HC0dIobbjFVgFSdiD4u1k8OzIx1SINO_lixUXSLTNJg86Dnh\",\"priority\": \"high\",\"data\": {\"MYDATA\": {\"APP_KEY\": \"BASIC\",\"TITLE\": \""+NOTIFICATION_HEADER+"\",\"MESSAGE_DETAIL\": \""+NOTIFICATION_MESSAGE+"\"}}}";
					
					
					if(input!=null) {
						
						
						
						
						   PreparedStatement stmt2 = con.prepareStatement("UPDATE custom_notification set NOTIFY_NEED='N' where NOTIFY_ID="+NOTIFY_ID+"");
					          //ResultSet rs = stmt.executeQuery();	
					          int i1=stmt2.executeUpdate();  

					          UsersGetNotify req = new UsersGetNotify();
								req.newsmessage(input);
						    
						    
						
						}
					
					
				}
				
				rs.close();
				rs1.close();
			    con.close();  
				}catch (Exception e2) {System.out.println(e2);}  
	        
	        
	        
		
		
	
		
	}
	
}