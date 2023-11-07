package org.QuestAndAnswers;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.function.Function;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;

@WebServlet("/QuestionsMainPageData")
public class QuestionsMainPageData extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");  
		//PrintWriter out = response.getWriter(); 
		
		
		
		
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		String workspaceId = geoObject.getString("WORKSPACE_ID");
		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");
	
		
		
		
		
		 if (functionId.equalsIgnoreCase("GET_DATA_HOME"))
	      {
			 if(actionId.equalsIgnoreCase("GET_ALL_QUES_ANS")){
				
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
			        PreparedStatement stmt = con.prepareStatement("select * from userquesans_view group by QUESTION_ID");
			        ResultSet rs = stmt.executeQuery();
			        int inc = 0;
			        while (rs.next())
			        {

			          JSONObject Jsonloc = new JSONObject();
			          Jsonloc.put("QUESTION_ID",  nullcheck(rs.getString("QUESTION_ID")));
			          Jsonloc.put("USERANAME", nullcheck(rs.getString("USERANAME")));
			          Jsonloc.put("USEREMAIL", nullcheck(rs.getString("USEREMAIL")));
			          Jsonloc.put("QUESTION",nullcheck( rs.getString("QUESTION")));
			          Jsonloc.put("QUEST_TAGS",nullcheck (rs.getString("QUEST_TAGS")));
			          Jsonloc.put("TIME_POSTED",nullcheck( rs.getString("TIME_POSTED")));
			          Jsonloc.put("ANSWERS_COUNT", rs.getInt("ANSWERS_COUNT"));
			          Jsonloc.put("FOLLOW_COUNT", rs.getInt("FOLLOW_COUNT"));
			          Jsonloc.put("VIEWS_COUNT", rs.getInt("VIEWS_COUNT"));
			          Jsonloc.put("ANSWER",nullcheck (rs.getString("ANSWER")));
			          Jsonloc.put("LIKE", rs.getInt("LIKE"));
			          Jsonloc.put("UNLIKE", rs.getInt("UNLIKE"));
			          String x1 = Integer.toString(inc);
			          JSOBVAL.put(x1, Jsonloc);
			          inc++;
			        }
			    
			        response.setContentType("application/json");
			        response.setCharacterEncoding("UTF-8");
			        response.getWriter().write(JSOBVAL.toString());
			        con.close();  
			      }
			      catch (Exception e2)
			      {
			        System.out.println(e2);
			      }
			     
			    }}
	}
	 String nullcheck(String val){
		 if(val == null) {
			  val="";
			}
		return val;}

}