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

public class FestsCallingJob implements Job
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


				
				PreparedStatement stmt=con.prepareStatement("select * from fests_data where start_date =  CURRENT_DATE+ INTERVAL 8 DAY"); 
				
				ResultSet rs=stmt.executeQuery(); 
				

		

				while (rs.next()) {
					
					String NOTIFY_TITLE =  "7 days to go: "+values.Nullcheck(rs.getString("fest_name"));
					
					String NOTIFY_DETAIL_MSG = values.Nullcheck(rs.getString("fest_description"));
					
					
					String NOTIFY_IMAGE=values.Nullcheck(rs.getString("banner_logos"));
					
					
					String UNIQUE_DATA1=values.Nullcheck(rs.getString("fest_id"));
					
					String input=null;
			
	
						
						String FilePath = imgconfig.getString("img_Fests_data");
						String imagepath = FilePath+NOTIFY_IMAGE;
					
	
	
					 input = "{ \"to\" : \"/topics/NEWS\",\"priority\": \"high\",\"data\": {\"MYDATA\":{\"USERNAME\": \"\",\"APP_KEY\": \"FESTS\",\"TITLE\": \""+NOTIFY_TITLE+"\",\"MESSAGE_DETAIL\": \""+NOTIFY_DETAIL_MSG+"\",\"data\": {\"CLG_ID\": \""+UNIQUE_DATA1+"\",\"BANNER_LOGO\":\""+imagepath+"\",\"FEST_CLG\":\""+NOTIFY_TITLE+"\"}}}}";
	
					
						if(input!=null) {

						          UsersGetNotify req = new UsersGetNotify();
									req.newsmessage(input);
							    
							    
							
							}
				
				 
			}
				
			PreparedStatement stmt1=con.prepareStatement("select * from fests_data where start_date =  CURRENT_DATE+ INTERVAL 2 DAY"); 
				
				ResultSet rs1=stmt1.executeQuery(); 
				

		

				while (rs1.next()) {
					
					String NOTIFY_TITLE = "2 days to go: "+ values.Nullcheck(rs1.getString("fest_name"));
					
					String NOTIFY_DETAIL_MSG = values.Nullcheck(rs1.getString("fest_description"));
					
					
					String NOTIFY_IMAGE=values.Nullcheck(rs1.getString("banner_logos"));
					
					
					String UNIQUE_DATA1=values.Nullcheck(rs1.getString("fest_id"));
					
					String input=null;
			
	
						
						String FilePath = imgconfig.getString("img_Fests_data");
						String imagepath = FilePath+NOTIFY_IMAGE;
					
	
	
					 input = "{ \"to\" : \"/topics/NEWS\",\"priority\": \"high\",\"data\": {\"MYDATA\":{\"USERNAME\": \"\",\"APP_KEY\": \"FESTS\",\"TITLE\": \""+NOTIFY_TITLE+"\",\"MESSAGE_DETAIL\": \""+NOTIFY_DETAIL_MSG+"\",\"data\": {\"CLG_ID\": \""+UNIQUE_DATA1+"\",\"BANNER_LOGO\":\""+imagepath+"\",\"FEST_CLG\":\""+NOTIFY_TITLE+"\"}}}}";
	
					
						if(input!=null) {

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