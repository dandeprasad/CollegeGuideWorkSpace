package org.CutoffsWorkspace;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.HomeWorkspace.MyUtility;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;




@WebServlet("/CutoffStreams")
public class CutoffStreams extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  
		  response.addHeader("Access-Control-Allow-Origin", "*");
	        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");

		PrintWriter out = response.getWriter(); 
	//	String filename = "newFile.txt";
	//	String workingDirectory = System.getProperty("user.dir");
	//	String absoluteFilePath = "";
		//Scanner s = new Scanner(new File("/home/colle3qj/ex.txt"));
		//absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
		//absoluteFilePath = workingDirectory + File.separator + filename;

	//	System.out.println("Final filepath : " + absoluteFilePath);
		
		
		JSONObject JSOBVAL= new JSONObject();	
		
		String reqTypetesrrt = (String)request.getParameter("ServerData");
		JSONObject obj1 = new JSONObject(reqTypetesrrt);
		JSONObject geoObject = obj1.getJSONObject("datatohost");
		
		String functionId = geoObject.getString("FUNCTION_ID");
		String actionId = geoObject.getString("ACTION_ID");
		
	
		
		if (functionId.equalsIgnoreCase("GET_STREAMS_ONLY")&& actionId.equalsIgnoreCase("GET_CLG_STREAMS_STRING")){   
			int SnoOfElements = geoObject.getInt("SROW_INDEX");
			int EnoOfElements = geoObject.getInt("EROW_INDEX");
		    Configuration imgconfig = null;
			try {
				imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FilePath = imgconfig.getString("img_CutoffSecondFragmentAdaptor");

		  
		  String UNIQUE_ID = geoObject.getString("uniqueTosend");
		  Configuration config = null;
			try {
				config = new PropertiesConfiguration("ConfigurePath.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			if(UNIQUE_ID.equalsIgnoreCase("NIT")){
				UNIQUE_ID="nit_list";
			}
			else if(UNIQUE_ID.equalsIgnoreCase("IIT")){
				UNIQUE_ID="iit_list";
			}
			else if(UNIQUE_ID.equalsIgnoreCase("IIIT")){
				UNIQUE_ID="iiit_list";
			}
			else if(UNIQUE_ID.equalsIgnoreCase("DEEMED")){
				UNIQUE_ID="deemed_univ_list";
			}
			String Driver = config.getString("Driver");
			String ConnectionSpace = config.getString("ConnectionSpace");
			String Db_user = config.getString("Db_user");
			String Db_password = config.getString("Db_password");
			
	        try
	        {
	          Class.forName(Driver);
	          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);


				PreparedStatement stmt=con.prepareStatement("select CLG_ID,CLG_NAME,CLG_LOGO,DEPARTMENTS from "+UNIQUE_ID+" ORDER BY CLG_ID DESC LIMIT "+SnoOfElements+","+EnoOfElements); 
				
				ResultSet rs=stmt.executeQuery(); 
				
				//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
				//Statement statement = con.createStatement();
				//ResultSet rs = statement.executeQuery(selectTableSQL);
			int inc = 0;
				while (rs.next()) {
					String Departments;
					String logo;
					if(rs.getString("DEPARTMENTS")==null){
						 Departments="";
					}
					else{
						Departments=rs.getString("DEPARTMENTS");
					}
					if(rs.getString("CLG_LOGO")==null){
						 logo="";
					}
					else{
						logo=rs.getString("CLG_LOGO");
					}
					JSONObject Jsonloc1= new JSONObject();
					Jsonloc1.put("CLG_NAME", rs.getString("CLG_NAME"));
					Jsonloc1.put("CLG_LOGO", FilePath+logo);
					Jsonloc1.put("DEPARTMENTS",Departments);
					Jsonloc1.put("CLG_ID", rs.getString("CLG_ID"));
					String x1  = Integer.toString(inc);
					JSOBVAL.put(x1, Jsonloc1);
					inc++;
					
				}
				  response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(JSOBVAL.toString());     
				    con.close();   
				}catch (Exception e2) {System.out.println(e2);}  
				          
				out.close(); 
				
			}
		
		
		
		if (functionId.equalsIgnoreCase("GET_CUTOFF_DETAILS")&& actionId.equalsIgnoreCase("GET_CUTOFF_DETAILS_STRING")){   

			  MyUtility check=new MyUtility();
			  
			  String UNIQUE_ID = geoObject.getString("uniqueTosend");
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


					PreparedStatement stmt=con.prepareStatement("select * from cutoffs_detail_colleges  where COLLEGE_ID= '" +UNIQUE_ID+ "' ORDER BY ROUND ,COURSE ASC "); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("ROUND", check.Nullcheck(rs.getString("ROUND")));
						Jsonloc1.put("COURSE",check.Nullcheck( rs.getString("COURSE")));
						Jsonloc1.put("CATEGORY",check.Nullcheck(rs.getString("CATEGORY")));
						Jsonloc1.put("QUOTA_OS_OR",check.Nullcheck( rs.getString("QUOTA_OS_OR")));
						Jsonloc1.put("QUOTA_OS_CR", check.Nullcheck(rs.getString("QUOTA_OS_CR")));
						Jsonloc1.put("QUOTA_HS_OR", check.Nullcheck(rs.getString("QUOTA_HS_OR")));
						Jsonloc1.put("QUOTA_HS_CR", check.Nullcheck(rs.getString("QUOTA_HS_CR")));
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					out.close(); 
					
				}
		if (functionId.equalsIgnoreCase("GET_CUTOFF_DETAILS")&& actionId.equalsIgnoreCase("GET_CUTOFF_DETAILS_IIT")){   

			  MyUtility check=new MyUtility();
			  
			  String UNIQUE_ID = geoObject.getString("uniqueTosend");
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


					PreparedStatement stmt=con.prepareStatement("select * from cutoffs_detail_colleges_iits  where COLLEGE_ID= '" +UNIQUE_ID+ "' ORDER BY ROUND ,COURSE ASC "); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("ROUND", check.Nullcheck(rs.getString("ROUND")));
						Jsonloc1.put("COLLEGE_ID",check.Nullcheck( rs.getString("COLLEGE_ID")));
						Jsonloc1.put("COLLEGE_NAME",check.Nullcheck(rs.getString("COLLEGE_NAME")));
						Jsonloc1.put("COURSE",check.Nullcheck( rs.getString("COURSE")));
						Jsonloc1.put("CATEGORY", check.Nullcheck(rs.getString("CATEGORY")));
						Jsonloc1.put("QUOTA", check.Nullcheck(rs.getString("QUOTA")));
						Jsonloc1.put("OPENING_RANK", check.Nullcheck(rs.getString("OPENING_RANK")));
						Jsonloc1.put("CLOSING_RANK", check.Nullcheck(rs.getString("CLOSING_RANK")));
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					out.close(); 
					
				}
		if (functionId.equalsIgnoreCase("GET_CUTOFF_DETAILS")&& actionId.equalsIgnoreCase("GET_CUTOFF_DETAILS_IIIT")){   

			  MyUtility check=new MyUtility();
			  
			  String UNIQUE_ID = geoObject.getString("uniqueTosend");
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


					PreparedStatement stmt=con.prepareStatement("select * from cutoffs_detail_colleges_iiits  where COLLEGE_ID= '" +UNIQUE_ID+ "' ORDER BY ROUND ,COURSE ASC "); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("ROUND", check.Nullcheck(rs.getString("ROUND")));
						Jsonloc1.put("COLLEGE_ID",check.Nullcheck( rs.getString("COLLEGE_ID")));
						Jsonloc1.put("COLLEGE_NAME",check.Nullcheck(rs.getString("COLLEGE_NAME")));
						Jsonloc1.put("COURSE",check.Nullcheck( rs.getString("COURSE")));
						Jsonloc1.put("CATEGORY", check.Nullcheck(rs.getString("CATEGORY")));
						Jsonloc1.put("QUOTA", check.Nullcheck(rs.getString("QUOTA")));
						Jsonloc1.put("OPENING_RANK", check.Nullcheck(rs.getString("OPENING_RANK")));
						Jsonloc1.put("CLOSING_RANK", check.Nullcheck(rs.getString("CLOSING_RANK")));
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					out.close(); 
					
				}
		if (functionId.equalsIgnoreCase("GET_RANKING_DETAILS")&& actionId.equalsIgnoreCase("GET_RANKINGS_MHRD")){   
			  MyUtility check=new MyUtility();
			  
			//  String UNIQUE_ID = geoObject.getString("uniqueTosend");
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


					PreparedStatement stmt=con.prepareStatement("select * from ranks_mhrd   ORDER BY CLG_RANK ASC "); 
					
					ResultSet rs=stmt.executeQuery(); 
					
					//String selectTableSQL = "SELECT TRAVEL_INFO_RAIL from nit_list";
					//Statement statement = con.createStatement();
					//ResultSet rs = statement.executeQuery(selectTableSQL);
				int inc = 0;
					while (rs.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("CLG_ID", check.Nullcheck(rs.getString("CLG_ID")));
						Jsonloc1.put("CLG_NAME",check.Nullcheck( rs.getString("CLG_NAME")));
						Jsonloc1.put("CLG_CITY",check.Nullcheck(rs.getString("CLG_CITY")));
						Jsonloc1.put("CLG_STATE",check.Nullcheck( rs.getString("CLG_STATE")));
						Jsonloc1.put("CLG_SCORE", check.Nullcheck(rs.getString("CLG_SCORE")));
						Jsonloc1.put("CLG_RANK", String.valueOf(rs.getInt("CLG_RANK")));
						Jsonloc1.put("CLG_YEAR", String.valueOf(rs.getInt("CLG_YEAR")));
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					out.close(); 
					
				}
		//Ap eamcet cutoffs
		
		if (functionId.equalsIgnoreCase("GET_AP_EAMCET_DATA")&& actionId.equalsIgnoreCase("GET_AP_EAMCET_INSTCODE")){   

			  MyUtility check=new MyUtility();
			  
			
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


					PreparedStatement stmt=con.prepareStatement("select DISTINCT(INST_CODE) from AP_EAMCET_CUTOFFS"); 
					
					ResultSet rs=stmt.executeQuery(); 
				int inc = 0;
					while (rs.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("AP_INST_CODE", check.Nullcheck(rs.getString("INST_CODE")));
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					out.close(); 
					
				}
		
		if (functionId.equalsIgnoreCase("GET_AP_EAMCET_DATA")&& actionId.equalsIgnoreCase("GET_AP_EAMCET_INSTNAME")){   

			  MyUtility check=new MyUtility();
			  
			
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


					PreparedStatement stmt=con.prepareStatement("select DISTINCT(INST_NAME) from AP_EAMCET_CUTOFFS"); 
					
					ResultSet rs=stmt.executeQuery(); 
				int inc = 0;
					while (rs.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("AP_INST_NAME", check.Nullcheck(rs.getString("INST_NAME")));
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					out.close(); 
					
				}	
		
		if (functionId.equalsIgnoreCase("GET_AP_EAMCET_DATA")&& actionId.equalsIgnoreCase("AP_EAMCET_CUTOFFMAIN_DATA")){   

			MyUtility check=new MyUtility();	
			// String UNIQUE_ID = geoObject.getString("uniqueTosend");
			
			StringBuffer formedQuery  =new StringBuffer("SELECT * FROM AP_EAMCET_CUTOFFS where ");
			 JSONObject UNIQUE_ID = geoObject.getJSONObject("uniqueTosend");
			 
			 if((boolean) UNIQUE_ID.get("AP_INST_CODE_CB")){
				 if(!((UNIQUE_ID.get("AP_INST_CODE")).toString()).equalsIgnoreCase("select"))
				 formedQuery = formedQuery.append("INST_CODE='"+UNIQUE_ID.get("AP_INST_CODE") +"' AND ");
			 }
			 if((boolean) UNIQUE_ID.get("AP_INST_NAME_CB")){
				 if(!((UNIQUE_ID.get("AP_INST_NAME")).toString()).equalsIgnoreCase("select"))
				 formedQuery = formedQuery.append("INST_NAME='"+UNIQUE_ID.get("AP_INST_NAME")+"' AND ");
			 }
			 if((boolean) UNIQUE_ID.get("AP_INST_TYPE_CB")){
				 if(!((UNIQUE_ID.get("AP_INST_TYPE")).toString()).equalsIgnoreCase("select"))
				 formedQuery = formedQuery.append("CLG_TYPE='"+UNIQUE_ID.get("AP_INST_TYPE")+"' AND ");
			 }
			 if((boolean) UNIQUE_ID.get("AP_INST_REG_CB")){
				 if(!((UNIQUE_ID.get("AP_INST_REG")).toString()).equalsIgnoreCase("select"))	
				 formedQuery = formedQuery.append("REG='"+UNIQUE_ID.get("AP_INST_REG")+"' AND ");
			 }
			 if((boolean) UNIQUE_ID.get("AP_INST_DIST_CB")){
				 if(!((UNIQUE_ID.get("AP_INST_DIST")).toString()).equalsIgnoreCase("select"))
				 formedQuery = formedQuery.append("DIST='"+UNIQUE_ID.get("AP_INST_DIST")+"' AND ");
			 }
			 if((boolean) UNIQUE_ID.get("AP_INST_PLACE_CB")){
				 if(!((UNIQUE_ID.get("AP_INST_PLACE")).toString()).equalsIgnoreCase("select"))
				 formedQuery = formedQuery.append("PLACE='"+UNIQUE_ID.get("AP_INST_PLACE")+"' AND ");
			 }
			 if((boolean) UNIQUE_ID.get("AP_INST_AFFLI_CB")){
				 if(!((UNIQUE_ID.get("AP_INST_AFFLI")).toString()).equalsIgnoreCase("select"))
				 formedQuery = formedQuery.append("AFLIA_UNIV='"+UNIQUE_ID.get("AP_INST_AFFLI")+"' AND ");
			 }
			 if((boolean) UNIQUE_ID.get("AP_INST_ESTYEAR_CB")){
				 if(!((UNIQUE_ID.get("AP_INST_ESTYEAR")).toString()).equalsIgnoreCase("select"))
				 formedQuery = formedQuery.append("EST_YEAR='"+UNIQUE_ID.get("AP_INST_ESTYEAR")+"' AND ");
			 }
			 if((boolean) UNIQUE_ID.get("AP_INST_BRNCHCODE_CB")){
				 if(!((UNIQUE_ID.get("AP_INST_BRNCHCODE")).toString()).equalsIgnoreCase("select"))
				 formedQuery = formedQuery.append("BRANCH_CODE='"+UNIQUE_ID.get("AP_INST_BRNCHCODE")+"' AND ");
			 }
			 if((boolean) UNIQUE_ID.get("AP_INST_CUTYEAR_CB")){
					if(!((UNIQUE_ID.get("AP_INST_CUTYEAR")).toString()).equalsIgnoreCase("select"))
				 formedQuery = formedQuery.append("CUTOFF_YEAR='"+UNIQUE_ID.get("AP_INST_CUTYEAR")+"' AND ");
			 }
			 String remove = " AND ";
			 String FinalQuery=formedQuery.toString();
			 FinalQuery = FinalQuery.substring(0, formedQuery.length() - remove.length())+"ORDER BY INST_CODE ";
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


					PreparedStatement stmt=con.prepareStatement(FinalQuery.toString()); 
					
					ResultSet rs=stmt.executeQuery(); 
				int inc = 0;
					while (rs.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("OUTPUT_AP_INST_NAME", check.Nullcheck(rs.getString("INST_NAME")));
						Jsonloc1.put("OUTPUT_AP_INST_CODE", check.Nullcheck(rs.getString("INST_CODE")));
						Jsonloc1.put("OUTPUT_AP_CLG_TYPE", check.Nullcheck(rs.getString("CLG_TYPE")));
						Jsonloc1.put("OUTPUT_AP_CLG_REG", check.Nullcheck(rs.getString("REG")));
						Jsonloc1.put("OUTPUT_AP_CLG_DIST", check.Nullcheck(rs.getString("DIST")));
						Jsonloc1.put("OUTPUT_AP_CLG_PLACE", check.Nullcheck(rs.getString("PLACE")));
						Jsonloc1.put("OUTPUT_AP_CLG_AFLIA_UNIV", check.Nullcheck(rs.getString("AFLIA_UNIV")));
						Jsonloc1.put("OUTPUT_AP_CLG_EST_YEAR", check.Nullcheck(rs.getString("EST_YEAR")));
						Jsonloc1.put("OUTPUT_AP_CLG_BRANCH_CODE", check.Nullcheck(rs.getString("BRANCH_CODE")));
						Jsonloc1.put("OUTPUT_AP_CLG_OC_BOYS", rs.getLong("OC_BOYS"));
						Jsonloc1.put("OUTPUT_AP_CLG_OC_GIRLS", rs.getLong("OC_GIRLS"));
						Jsonloc1.put("OUTPUT_AP_CLG_SC_BOYS", rs.getLong("SC_BOYS"));
						Jsonloc1.put("OUTPUT_AP_CLG_SC_GIRLS", rs.getLong("SC_GIRLS"));
						Jsonloc1.put("OUTPUT_AP_CLG_ST_BOYS", rs.getLong("ST_BOYS"));
						Jsonloc1.put("OUTPUT_AP_CLG_ST_GIRLS", rs.getLong("ST_GIRLS"));
						Jsonloc1.put("OUTPUT_AP_CLG_BCA_BOYS", rs.getLong("BCA_BOYS"));
						Jsonloc1.put("OUTPUT_AP_CLG_BCA_GIRLS",rs.getLong("BCA_GIRLS"));
						Jsonloc1.put("OUTPUT_AP_CLG_BCB_BOYS", rs.getLong("BCB_BOYS"));
						Jsonloc1.put("OUTPUT_AP_CLG_BCB_GIRLS", rs.getLong("BCB_GIRLS"));
						Jsonloc1.put("OUTPUT_AP_CLG_BCC_BOYS", rs.getLong("BCC_BOYS"));
						Jsonloc1.put("OUTPUT_AP_CLG_BCC_GIRLS", rs.getLong("BCC_GIRLS"));
						Jsonloc1.put("OUTPUT_AP_CLG_BCD_BOYS", rs.getLong("BCD_BOYS"));
						Jsonloc1.put("OUTPUT_AP_CLG_BCD_GIRLS", rs.getLong("BCD_GIRLS"));
						Jsonloc1.put("OUTPUT_AP_CLG_BCE_BOYS", rs.getLong("BCE_BOYS"));
						Jsonloc1.put("OUTPUT_AP_CLG_BCE_GIRLS", rs.getLong("BCE_GIRLS"));
						Jsonloc1.put("OUTPUT_AP_CLG_CUTOFF_YEAR", rs.getLong("CUTOFF_YEAR"));
						Jsonloc1.put("OUTPUT_AP_CLG_CUTOFF_FEE", rs.getLong("COLLEGE_FEES"));
						String x1  = Integer.toString(inc);
						JSOBVAL.put(x1, Jsonloc1);
						inc++;
						
					}
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					out.close(); 
					
				}
		
		if (functionId.equalsIgnoreCase("GET_AP_EAMCET_DATA")&& actionId.equalsIgnoreCase("GET_AP_EAMCET_INST_OTHERS")){   

			  MyUtility check=new MyUtility();
			  
			
			  Configuration config = null;
				try {
					config = new PropertiesConfiguration("ConfigurePath.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				JSONObject JSON_CLG_TYPE= new JSONObject();
				JSONObject JSON_CLG_REG= new JSONObject();
				JSONObject JSON_CLG_DIST= new JSONObject();
				JSONObject JSON_CLG_PLC= new JSONObject();
				JSONObject JSON_CLG_ALF_UV= new JSONObject();
				JSONObject JSON_CLG_EST= new JSONObject();
				JSONObject JSON_CLG_BRANCHCODE= new JSONObject();
				JSONObject JSON_CLG_CUTOFFYEAR= new JSONObject();
			
				String Driver = config.getString("Driver");
				String ConnectionSpace = config.getString("ConnectionSpace");
				String Db_user = config.getString("Db_user");
				String Db_password = config.getString("Db_password");
				
		        try
		        {
		          Class.forName(Driver);
		          Connection con = DriverManager.getConnection(ConnectionSpace, Db_user, Db_password);


					PreparedStatement stmt=con.prepareStatement("select DISTINCT(CLG_TYPE) from AP_EAMCET_CUTOFFS"); 
					
					ResultSet rs=stmt.executeQuery(); 
				int inc = 0;
					while (rs.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("AP_INST_TYPE", check.Nullcheck(rs.getString("CLG_TYPE")));
						String x1  = Integer.toString(inc);
						JSON_CLG_TYPE.put(x1, Jsonloc1);
						inc++;
						
					}
					PreparedStatement stmt1=con.prepareStatement("select DISTINCT(REG) from AP_EAMCET_CUTOFFS"); 
					
					ResultSet rs1=stmt1.executeQuery(); 
				int inc1 = 0;
					while (rs1.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("AP_INST_REG", check.Nullcheck(rs1.getString("REG")));
						String x1  = Integer.toString(inc1);
						JSON_CLG_REG.put(x1, Jsonloc1);
						inc1++;
						
					}		
	PreparedStatement stmt2=con.prepareStatement("select DISTINCT(DIST) from AP_EAMCET_CUTOFFS"); 
					
					ResultSet rs2=stmt2.executeQuery(); 
				int inc2 = 0;
					while (rs2.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("AP_INST_DIST", check.Nullcheck(rs2.getString("DIST")));
						String x1  = Integer.toString(inc2);
						JSON_CLG_DIST.put(x1, Jsonloc1);
						inc2++;
						
					}
				
				
	PreparedStatement stmt4=con.prepareStatement("select DISTINCT(PLACE) from AP_EAMCET_CUTOFFS"); 
					
					ResultSet rs4=stmt4.executeQuery(); 
				int inc4 = 0;
					while (rs4.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("AP_INST_PLACE", check.Nullcheck(rs4.getString("PLACE")));
						String x1  = Integer.toString(inc4);
						JSON_CLG_PLC.put(x1, Jsonloc1);
						inc4++;
						
					}
					
	PreparedStatement stmt5=con.prepareStatement("select DISTINCT(AFLIA_UNIV) from AP_EAMCET_CUTOFFS"); 
					
					ResultSet rs5=stmt5.executeQuery(); 
				int inc5 = 0;
					while (rs5.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("AP_INST_AFLIA_UNIV", check.Nullcheck(rs5.getString("AFLIA_UNIV")));
						String x1  = Integer.toString(inc5);
						JSON_CLG_ALF_UV.put(x1, Jsonloc1);
						inc5++;
						
					}

	PreparedStatement stmt7=con.prepareStatement("select DISTINCT(EST_YEAR) from AP_EAMCET_CUTOFFS"); 
					
					ResultSet rs7=stmt7.executeQuery(); 
				int inc7 = 0;
					while (rs7.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("AP_INST_EST_YEAR", rs7.getLong("EST_YEAR"));
						String x1  = Integer.toString(inc7);
						JSON_CLG_EST.put(x1, Jsonloc1);
						inc7++;
						
					}
					
	PreparedStatement stmt8=con.prepareStatement("select DISTINCT(BRANCH_CODE) from AP_EAMCET_CUTOFFS"); 
					
					ResultSet rs8=stmt8.executeQuery(); 
				int inc8 = 0;
					while (rs8.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("AP_INST_BRANCH_CODE", check.Nullcheck(rs8.getString("BRANCH_CODE")));
						String x1  = Integer.toString(inc8);
						JSON_CLG_BRANCHCODE.put(x1, Jsonloc1);
						inc8++;
						
					}
	PreparedStatement stmt9=con.prepareStatement("select DISTINCT(CUTOFF_YEAR) from AP_EAMCET_CUTOFFS"); 
					
					ResultSet rs9=stmt9.executeQuery(); 
				int inc9 = 0;
					while (rs9.next()) {
						
						JSONObject Jsonloc1= new JSONObject();
						Jsonloc1.put("AP_INST_CUTOFF_YEAR", rs9.getInt("CUTOFF_YEAR"));
						String x1  = Integer.toString(inc9);
						JSON_CLG_CUTOFFYEAR.put(x1, Jsonloc1);
						inc9++;
						
					}

					JSOBVAL.put("0", JSON_CLG_TYPE);
					JSOBVAL.put("1", JSON_CLG_REG);
					JSOBVAL.put("2", JSON_CLG_DIST);
					JSOBVAL.put("3", JSON_CLG_PLC);
					JSOBVAL.put("4", JSON_CLG_ALF_UV);
					JSOBVAL.put("5", JSON_CLG_EST);
					JSOBVAL.put("6", JSON_CLG_BRANCHCODE);
					JSOBVAL.put("7", JSON_CLG_CUTOFFYEAR);
					
					  response.setContentType("application/json");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(JSOBVAL.toString());     
					    con.close();   
					}catch (Exception e2) {System.out.println(e2);}  
					          
					out.close(); 
					
				}	
		

}}