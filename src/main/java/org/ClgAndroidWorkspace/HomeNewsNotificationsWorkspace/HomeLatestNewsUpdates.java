package org.ClgAndroidWorkspace.HomeNewsNotificationsWorkspace;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.AmazonSupport.AmazonSupportRequests;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

@WebServlet("/HomeLatestNewsUpdates")
public class HomeLatestNewsUpdates extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String filename = null;
		String reqTypetesrrt = (String)request.getParameter("dandeRequest");
		if (reqTypetesrrt.equals("0")){
			 filename ="position_0.jpg";
		}
		else if (reqTypetesrrt.equals("1")){
			 filename ="position_1.jpg";
		}
		
		else if (reqTypetesrrt.equals("2")){
			filename ="position_2.jpg";
		}
		else if (reqTypetesrrt.equals("3")){
			filename ="position_3.jpg";
		}
		else if (reqTypetesrrt.equals("4")){
			filename ="position_4.jpg";
		}
		else if (reqTypetesrrt.equals("5")){
			filename ="position_5.jpg";
		}
		else if (reqTypetesrrt.equals("6")){
			filename ="position_6.jpg";
		}
		else if (reqTypetesrrt.equals("7")){
			filename ="position_6.jpg";
		}
		//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
	    Configuration config = null;
		try {
			config = new PropertiesConfiguration("ImagesConfig.properties");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
/*	InputStream input = new FileInputStream("https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-083183914236/CLGS_PROJECT/testnitsnitify/position_0.jpg");

		int data = input.read();

		while(data != -1){
		  data = input.read();
		}*/
		
/*	    FileObject fileObject ="https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-083183914236/CLGS_PROJECT/testnitsnitify/position_0.jpg" ;
	    if (fileObject != null && fileObject.getBinData() != null) {
	        response.setHeader("Content-disposition", "attachment; filename=\"" + fileObject.getFilename() + "\"");
	        response.setContentType(fileObject.getFiletype());
	        response.setContentLength((int)fileObject.getFilesize().intValue());
	        response.getOutputStream().write(fileObject.getBinData());
		
		
		*/
		
		
		String FilePath = config.getString("HomeLatestNewsUpdates");	
		String imagesupport = config.getString("imagesupport");
		if(imagesupport.equalsIgnoreCase("PRODUCTION")){
		AmazonSupportRequests req=new 	AmazonSupportRequests();
		req.requestThroughAmazon(response,FilePath,filename);
		}	
		else{
    File file = new File(FilePath, filename);
    response.setHeader("Content-Type", getServletContext().getMimeType(filename));
    response.setHeader("Content-Length", String.valueOf(file.length()));
    response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
    Files.copy(file.toPath(), response.getOutputStream());	}
      //  response.getOutputStream().write(data);
	}

}