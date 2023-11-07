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

@WebServlet("/NewsNotificationsLoad.")
public class NewsNotificationsLoad extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String filename = null;
		String reqTypetesrrt = (String)request.getParameter("dandeRequest");
		if (reqTypetesrrt.equals("0")){
			 filename ="mainheader.png";
		}
		else if (reqTypetesrrt.equals("1")){
			 filename ="after10th.png";
		}
		
		else if (reqTypetesrrt.equals("2")){
			filename ="after12th.png";
		}
		else if (reqTypetesrrt.equals("3")){
			filename ="afterEng.png";
		}
		else if (reqTypetesrrt.equals("4")){
			filename ="bank_oppur.png";
		}
		else if (reqTypetesrrt.equals("5")){
			filename ="charted_accounts.png";
		}
		else if (reqTypetesrrt.equals("6")){
			filename ="sports_opper.png";
		}
		else if (reqTypetesrrt.equals("7")){
			filename ="medical_opper.png";
		}
		
		//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
		

        File file = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance", filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());	
		
	}

}