package org.ExamsWorkspace;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.ZoneId;
import java.util.Scanner;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.HomeWorkspace.MyUtility;
import org.HomeWorkspace.UserLogedInSessMaintaince;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;

@WebServlet("/ExamsStrings")
public class ExamsStrings extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");  
		  response.addHeader("Access-Control-Allow-Origin", "*");
	        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");

		PrintWriter out = response.getWriter();
		// String filename = "newFile.txt";
		// String workingDirectory = System.getProperty("user.dir");
		// String absoluteFilePath = "";
		// Scanner s = new Scanner(new File("/home/colle3qj/ex.txt"));
		// absoluteFilePath = workingDirectory +
		// System.getProperty("file.separator") + filename;
		// absoluteFilePath = workingDirectory + File.separator + filename;

		// System.out.println("Final filepath : " + absoluteFilePath);

		JSONObject JSOBVAL = new JSONObject();
		MyUtility values = new MyUtility();
		String reqTypetesrrt = (String) request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");

		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");

		if (functionId.equalsIgnoreCase("GET_EXAMS_ONLY") && actionId.equalsIgnoreCase("GET_CLG_EXAMS_STRING")) {
			int SnoOfElements = geoObject.getInt("SROW_INDEX");
			int EnoOfElements = geoObject.getInt("EROW_INDEX");
			String geoId = "ALL_NEWS";

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
			String FilePath = imgconfig.getString("img_HomeExamsFragmentAdaptor");

			String Driver = config.getString("Driver");
			String ConnectionSpace = config.getString("ConnectionSpace");
			String Db_user = config.getString("Db_user");
			String Db_password = config.getString("Db_password");

			try {
				Class.forName(Driver);
				Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);

				PreparedStatement stmt = con
						.prepareStatement("select * from exams_workspace ORDER BY POSITION DESC LIMIT " + SnoOfElements
								+ "," + EnoOfElements);

				ResultSet rs = stmt.executeQuery();

				// String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from
				// nit_list";
				// Statement statement = con.createStatement();
				// ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
				while (rs.next()) {
					String EXAMID = values.Nullcheck(rs.getString("EXAMID"));
					String CLG_NAME =values.Nullcheck( rs.getString("CLG_NAME"));
					String EXAM_DETAILS =values.Nullcheck( rs.getString("EXAM_DETAILS"));
					String POSTED_DATE = values.Nullcheck(rs.getString("POSTED_DATE"));
					String EXAM_NAME =values.Nullcheck( rs.getString("EXAM_NAME"));
					String EXAM_DATE = values.Nullcheck(rs.getString("EXAM_START_DATE"));
					String OTHER_IMP_DETAILS = values.Nullcheck(rs.getString("OTHER_IMP_DETAILS"));
					String LOGO_IMAGE = values.Nullcheck(FilePath + rs.getString("LOGO_IMAGE"));
					String REG_START_DATE = values.Nullcheck(rs.getString("REG_START_DATE"));
					String RESULT_DATE = values.Nullcheck(rs.getString("RESULT_DATE"));
					String REG_END_DATE = values.Nullcheck(rs.getString("REG_END_DATE"));

					JSONObject Jsonloc1 = new JSONObject();
					Jsonloc1.put("EXAMID", EXAMID);
					Jsonloc1.put("CLG_NAME", CLG_NAME);
					Jsonloc1.put("EXAM_DETAILS", EXAM_DETAILS);
					Jsonloc1.put("POSTED_DATE", POSTED_DATE);
					Jsonloc1.put("EXAM_NAME", EXAM_NAME);
					Jsonloc1.put("EXAM_DATE", EXAM_DATE);
					Jsonloc1.put("OTHER_IMP_DETAILS", OTHER_IMP_DETAILS);
					Jsonloc1.put("LOGO_IMAGE", LOGO_IMAGE);
					Jsonloc1.put("REG_START_DATE", REG_START_DATE);
					Jsonloc1.put("RESULT_DATE", RESULT_DATE);
					Jsonloc1.put("REG_END_DATE", REG_END_DATE);
					String x1 = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;

				}
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSOBVAL.toString());
				con.close();
			} catch (Exception e2) {
				System.out.println(e2);
			}

			out.close();

		}
		if (functionId.equalsIgnoreCase("GET_EXAMS_ONLY") && actionId.equalsIgnoreCase("SET_NOTIFICATIONS_EXAM")) {
		
			
			String exam_id =geoObject.getString("EXAM_ID");
		String token_id=	geoObject.getString("TOKEN_ID");
		String 	user_mail=geoObject.getString("USER_MAIL");
		      java.util.Date dt = new java.util.Date();

		      java.text.SimpleDateFormat sdf = 
		           new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 	 ZoneId singaporeZoneId = ZoneId.of("Asia/Kolkata");
			 sdf.setTimeZone(TimeZone.getTimeZone(singaporeZoneId));
		      String currentTime = sdf.format(dt);
				JSONObject JSOBVAL1= new JSONObject();			
				 
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
	        PreparedStatement stmt = con.prepareStatement("insert into  particular_exam_notify values(?,?,?,?)");
	        stmt.setString(1,user_mail);
	        stmt.setString(2, token_id);
	        stmt.setString(3,exam_id);
	        stmt.setString(4,currentTime);

	        
	        
	        
	        
	      
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
				JSOBVAL1.put(x1, Jsonloc1);
				
	/*			SMSEmailUsingSuccessregister trigger = new  SMSEmailUsingSuccessregister();
			 trigger.SUCCESSFULLREGISTER(usr_data.getString("USERNAME_EMAIL"));
			 */
			  response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(JSOBVAL1.toString());
				}
			  
	      }
	      
	      catch (Exception e2)
	      {
	        System.out.println(e2);
	      }
				
		      
		
			
		}
		
		if (functionId.equalsIgnoreCase("GET_EXAMS_ONLY") && actionId.equalsIgnoreCase("GET_CLG_EXAMS_RESULTS")) {

			String geoId = "EXAM_ID";

			String examid = geoObject.getString("EXAM_ID");

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
			String FilePath = imgconfig.getString("img_HomeExamsFragmentAdaptor");

			String Driver = config.getString("Driver");
			String ConnectionSpace = config.getString("ConnectionSpace");
			String Db_user = config.getString("Db_user");
			String Db_password = config.getString("Db_password");

			try {
				Class.forName(Driver);
				Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);

				PreparedStatement stmt = con.prepareStatement(
						"select EXAMS_RESULT_PAGE from exams_workspace where EXAMID ='" + examid + "'");

				ResultSet rs = stmt.executeQuery();

				// String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from
				// nit_list";
				// Statement statement = con.createStatement();
				// ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
				MyUtility check = new MyUtility();
				while (rs.next()) {

					String EXAMS_RESULT_PAGE =check.Nullcheck( rs.getString("EXAMS_RESULT_PAGE"));

					

					JSONObject Jsonloc1 = new JSONObject();

					Jsonloc1.put("EXAMS_RESULT_PAGE", EXAMS_RESULT_PAGE);

			

					String x1 = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;

				}
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSOBVAL.toString());
				con.close();
			} catch (Exception e2) {
				System.out.println(e2);
			}

			out.close();

		}
		if (functionId.equalsIgnoreCase("GET_EXAMS_ONLY") && actionId.equalsIgnoreCase("GET_CLG_EXAMS_PREPARE")) {

			String geoId = "EXAM_ID";

			String examid = geoObject.getString("EXAM_ID");

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
			String FilePath = imgconfig.getString("img_HomeExamsFragmentAdaptor");

			String Driver = config.getString("Driver");
			String ConnectionSpace = config.getString("ConnectionSpace");
			String Db_user = config.getString("Db_user");
			String Db_password = config.getString("Db_password");

			try {
				Class.forName(Driver);
				Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);

				PreparedStatement stmt = con.prepareStatement(
						"select EXAM_SYLLABUS,QUEST_PAPER_ANALYS from exams_workspace where EXAMID ='" + examid + "'");

				ResultSet rs = stmt.executeQuery();

				// String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from
				// nit_list";
				// Statement statement = con.createStatement();
				// ResultSet rs = statement.executeQuery(selectTableSQL);
				MyUtility check = new MyUtility();
				
				int inc = 0;
				while (rs.next()) {

					String EXAM_SYLLABUS =check.Nullcheck (rs.getString("EXAM_SYLLABUS"));

					String QUEST_PAPER_ANALYS = check.Nullcheck (rs.getString("QUEST_PAPER_ANALYS"));

					JSONObject Jsonloc1 = new JSONObject();

					Jsonloc1.put("EXAM_SYLLABUS", EXAM_SYLLABUS);

					Jsonloc1.put("QUEST_PAPER_ANALYS", QUEST_PAPER_ANALYS);

					String x1 = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;

				}
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSOBVAL.toString());
				con.close();
			} catch (Exception e2) {
				System.out.println(e2);
			}

			out.close();

		}
		if (functionId.equalsIgnoreCase("GET_EXAMS_ONLY") && actionId.equalsIgnoreCase("GET_CLG_EXAMS_DATES")) {

			String geoId = "EXAM_ID";

			String examid = geoObject.getString("EXAM_ID");

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
			String FilePath = imgconfig.getString("img_HomeExamsFragmentAdaptor");

			String Driver = config.getString("Driver");
			String ConnectionSpace = config.getString("ConnectionSpace");
			String Db_user = config.getString("Db_user");
			String Db_password = config.getString("Db_password");

			try {
				Class.forName(Driver);
				Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);

				PreparedStatement stmt = con.prepareStatement(
						"select REG_START_DATE,REG_END_DATE,ADMIT_CARD_DATE,EXAM_START_DATE,EXAM_END_DATE,PAPER_BASED_EXAM_DATE,COMPUTER_BASED_EXAM_DATE,RESULT_DATE,TEST_CENTRE_DETAILS from exams_workspace where EXAMID ='"
								+ examid + "'");

				ResultSet rs = stmt.executeQuery();

				// String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from
				// nit_list";
				// Statement statement = con.createStatement();
				// ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
				MyUtility check = new MyUtility();
				while (rs.next()) {

					String REG_START_DATE = check.Nullcheck(rs.getString("REG_START_DATE"));
					String REG_END_DATE =  check.Nullcheck(rs.getString("REG_END_DATE"));

					String ADMIT_CARD_DATE = check.Nullcheck( rs.getString("ADMIT_CARD_DATE"));

					String EXAM_START_DATE =  check.Nullcheck(rs.getString("EXAM_START_DATE"));

					String EXAM_END_DATE = check.Nullcheck( rs.getString("EXAM_END_DATE"));

					String PAPER_BASED_EXAM_DATE =  check.Nullcheck(rs.getString("PAPER_BASED_EXAM_DATE"));
					String COMPUTER_BASED_EXAM_DATE =  check.Nullcheck(rs.getString("COMPUTER_BASED_EXAM_DATE"));

					String RESULT_DATE = check.Nullcheck( rs.getString("RESULT_DATE"));
					String TEST_CENTRE_DETAILS = check.Nullcheck( rs.getString("TEST_CENTRE_DETAILS"));

					JSONObject Jsonloc1 = new JSONObject();

					Jsonloc1.put("REG_START_DATE", REG_START_DATE);

					Jsonloc1.put("REG_END_DATE", REG_END_DATE);

					Jsonloc1.put("ADMIT_CARD_DATE", ADMIT_CARD_DATE);

					Jsonloc1.put("EXAM_START_DATE", EXAM_START_DATE);

					Jsonloc1.put("EXAM_END_DATE", EXAM_END_DATE);

					Jsonloc1.put("PAPER_BASED_EXAM_DATE", PAPER_BASED_EXAM_DATE);
					Jsonloc1.put("COMPUTER_BASED_EXAM_DATE", COMPUTER_BASED_EXAM_DATE);
					Jsonloc1.put("RESULT_DATE", RESULT_DATE);
					Jsonloc1.put("TEST_CENTRE_DETAILS", TEST_CENTRE_DETAILS);
				

					String x1 = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;

				}
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSOBVAL.toString());
				con.close();
			} catch (Exception e2) {
				System.out.println(e2);
			}

			out.close();

		}
		if (functionId.equalsIgnoreCase("GET_EXAMS_ONLY") && actionId.equalsIgnoreCase("GET_CLG_EXAMS_OVERVIEW")) {

			String geoId = "EXAM_ID";

			String examid = geoObject.getString("EXAM_ID");

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
			String FilePath = imgconfig.getString("img_HomeExamsFragmentAdaptor");

			String Driver = config.getString("Driver");
			String ConnectionSpace = config.getString("ConnectionSpace");
			String Db_user = config.getString("Db_user");
			String Db_password = config.getString("Db_password");

			try {
				Class.forName(Driver);
				Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);

				PreparedStatement stmt = con.prepareStatement(
						"select LOGO_IMAGE,CLG_NAME,EXAM_NAME,REG_START_DATE,REG_END_DATE,EXAM_OVERVIEW from exams_workspace where EXAMID ='"
								+ examid + "'");

				ResultSet rs = stmt.executeQuery();

				// String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from
				// nit_list";
				// Statement statement = con.createStatement();
				// ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
				while (rs.next()) {

					MyUtility check = new MyUtility();
					String CLG_NAME =check.Nullcheck(rs.getString("CLG_NAME"));

					String EXAM_NAME = check.Nullcheck(rs.getString("EXAM_NAME"));

					String LOGO_IMAGE =check.Nullcheck( FilePath + rs.getString("LOGO_IMAGE"));
					String REG_START_DATE =check.Nullcheck( rs.getString("REG_START_DATE"));
					String EXAM_OVERVIEW =check.Nullcheck( rs.getString("EXAM_OVERVIEW"));
					String REG_END_DATE = check.Nullcheck(rs.getString("REG_END_DATE"));

					JSONObject Jsonloc1 = new JSONObject();

					Jsonloc1.put("CLG_NAME", CLG_NAME);

					Jsonloc1.put("EXAM_NAME", EXAM_NAME);

					Jsonloc1.put("LOGO_IMAGE", LOGO_IMAGE);
					Jsonloc1.put("REG_START_DATE", REG_START_DATE);
					Jsonloc1.put("EXAM_OVERVIEW", EXAM_OVERVIEW);
					Jsonloc1.put("REG_END_DATE", REG_END_DATE);
					String x1 = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;

				}
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSOBVAL.toString());
				con.close();
			} catch (Exception e2) {
				System.out.println(e2);
			}

			out.close();

		}
		if (functionId.equalsIgnoreCase("GET_NEWS_ONLY") && actionId.equalsIgnoreCase("GET_CLG_NEWS_STRING")) {

			String collegeid = geoObject.getString("COLLEGE_ID");

			int SnoOfElements = geoObject.getInt("SROW_INDEX");
			int EnoOfElements = geoObject.getInt("EROW_INDEX");

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

			try {
				Class.forName(Driver);
				Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);

				PreparedStatement stmt = con.prepareStatement("select * from news_workspace where TAG_TYPE='"
						+ collegeid + "' ORDER BY POSITION DESC LIMIT " + SnoOfElements + "," + EnoOfElements);

				ResultSet rs = stmt.executeQuery();

				// String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from
				// nit_list";
				// Statement statement = con.createStatement();
				// ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
				while (rs.next()) {
					String NEWSID = rs.getString("NEWSID");
					String NEWS_HEADER = rs.getString("NEWS_HEADER");
					String POSTED_DATE = rs.getString("POSTED_DATE");
					String TAG_TYPE = rs.getString("TAG_TYPE");
					String TAG_COLOR = rs.getString("TAG_COLOR");
					String FULL_IMAGE = rs.getString("FULL_IMAGE");
					JSONObject Jsonloc1 = new JSONObject();
					Jsonloc1.put("NEWSID", NEWSID);
					Jsonloc1.put("NEWS_HEADER", NEWS_HEADER);
					Jsonloc1.put("POSTED_DATE", POSTED_DATE);
					Jsonloc1.put("TAG_TYPE", TAG_TYPE);
					Jsonloc1.put("TAG_COLOR", TAG_COLOR);
					Jsonloc1.put("FULL_IMAGE", FULL_IMAGE);
					String x1 = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;

				}
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSOBVAL.toString());
				con.close();
			} catch (Exception e2) {
				System.out.println(e2);
			}

			out.close();

		}

	}
}