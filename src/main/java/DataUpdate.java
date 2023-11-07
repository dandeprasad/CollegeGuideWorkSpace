import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class DataUpdate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		  
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

	 
	 
	 stmt=con.prepareStatement("select DISTINCT(INST_NAME),INST_CODE from AP_EAMCET_CUTOFFS where CUTOFF_YEAR=2018");

				ResultSet rs=stmt.executeQuery(); 
				while (rs.next()) {
					String IstCode =rs.getString("INST_CODE");
					String InstName = rs.getString("INST_NAME");
					  PreparedStatement stmt1 = con.prepareStatement("UPDATE AP_EAMCET_CUTOFFS  SET INST_NAME ='"+InstName+"' where INST_CODE='"+IstCode+"' and CUTOFF_YEAR=2017 ");
					  

			        
			          //ResultSet rs = stmt.executeQuery();	
			          int i=stmt1.executeUpdate();  

			          System.out.println(i+" records inserted");  
				}
		
			    con.close(); 
	        }
catch (Exception e2) {System.out.println(e2);}  

							
						}		
	

}
