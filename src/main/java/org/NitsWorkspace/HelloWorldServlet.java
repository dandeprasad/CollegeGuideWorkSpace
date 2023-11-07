package org.NitsWorkspace;
import java.io.*;  
import java.sql.*;  
import javax.servlet.ServletException;  
import javax.servlet.http.*;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;  
  
public class HelloWorldServlet extends HttpServlet {  
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException{

  
response.setContentType("text/html");  
PrintWriter out = response.getWriter();  
          
String n=request.getParameter("userName");  
String p=request.getParameter("userPass");  
String e=request.getParameter("userEmail");  
String c=request.getParameter("userCountry");  
Configuration config = null;
	try {
		config = new PropertiesConfiguration("ConfigurePath.properties");
	} catch (ConfigurationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}	
	

	String Driver = config.getString("Driver");
	String ConnectionSpace = config.getString("ConnectionSpace");
	String Db_user = config.getString("Db_user");
	String Db_password = config.getString("Db_password");
	
  try
  {
    Class.forName(Driver);
    Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);
PreparedStatement ps=con.prepareStatement(  
"insert into registeruser values(?,?,?,?)");  
  
ps.setString(1,n);  
ps.setString(2,p);  
ps.setString(3,e);  
ps.setString(4,c);  
          
int i=ps.executeUpdate();  
con.close();
if(i>0)  
out.print("You are successfully registered...");  
      
          
}catch (Exception e2) {System.out.println(e2);}  
          
out.close();  
}  
  
}