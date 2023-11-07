package org.ClgAndroidWorkspace;


import java.io.File;
import java.io.IOException;
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

@WebServlet("/CarrerGuidanceload")
public class CarrerGuidanceload extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String filename = null;
		String reqTypetesrrt = (String)request.getParameter("dandeRequest");
		if (reqTypetesrrt.equals("0")){
			 filename ="mainheader.png";
		}
		else if (reqTypetesrrt.equals("1")){
			 filename ="after_12th.png";
		}
		
		else if (reqTypetesrrt.equals("2")){
			filename ="after_grad_arts.png";
		}
		else if (reqTypetesrrt.equals("3")){
			filename ="after_grad_commerce.png";
		}
		else if (reqTypetesrrt.equals("4")){
			filename ="after_grad_science.png";
		}
		else if (reqTypetesrrt.equals("5")){
			filename ="after_medical.png";
		}
		else if (reqTypetesrrt.equals("6")){
			filename ="banking.png";
		}
		else if (reqTypetesrrt.equals("7")){
			filename ="engineering.png";
		}
		else if (reqTypetesrrt.equals("8")){
			filename ="law.png";
		}
		else if (reqTypetesrrt.equals("9")){
			filename ="medical.png";
		}
		  Configuration config = null;
			try {
				config = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			

			String FilePath = config.getString("CarrerGuidanceload");	

		//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
			
			
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
		
	}

}