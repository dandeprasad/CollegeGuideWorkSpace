package org.UploadWorkspace;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.AmazonSupport.AmazonSupportUploads;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

@WebServlet("/FestsImageUpload")
public class FestsImageUpload extends HttpServlet {

	
	
    private ServletFileUpload uploader = null;
	@Override
	public void init() throws ServletException{
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
		fileFactory.setRepository(filesDir);
		this.uploader = new ServletFileUpload(fileFactory);
	}
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!ServletFileUpload.isMultipartContent(request)){
			throw new ServletException("Content type is not multipart/form-data");
		}
		response.setContentType("text/html");
	
		  response.addHeader("Access-Control-Allow-Origin", "*");
	        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
	        JSONObject JSOBVAL= new JSONObject();     
;
		try {
			List<FileItem> fileItemsList = uploader.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			while(fileItemsIterator.hasNext()){
				FileItem fileItem = fileItemsIterator.next();
				System.out.println("FieldName="+fileItem.getFieldName());
				System.out.println("FileName="+fileItem.getName());
				System.out.println("ContentType="+fileItem.getContentType());
				System.out.println("Size in bytes="+fileItem.getSize());
				
				Configuration imgconfig = null;
				try {
					imgconfig = new PropertiesConfiguration("ImagesConfig.properties");
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String FilePath_upload = imgconfig.getString("img_Fests_data1");
				
			byte[] dande = 	fileItem.get();
			
			   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
			   LocalDateTime now = LocalDateTime.now(); 
		 
		String val=	 "FESTS"+"_"+(dtf.format(now))+".jpg";
		
			AmazonSupportUploads req = new AmazonSupportUploads();
			Boolean check= req.ImageUpload(dande,val,FilePath_upload);
			int inc = 0;
			if(check){
				System.out.println(fileItem.getFieldName()+"success");
				JSONObject Jsonloc1= new JSONObject();
				Jsonloc1.put(fileItem.getFieldName(), val);
				String x1  = Integer.toString(inc);
				JSOBVAL.put(x1, Jsonloc1);
				inc++;}
			

		    		}
	           response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(JSOBVAL.toString());
			    
		} catch (FileUploadException e) {
			System.out.print(e);
		} catch (Exception e) {
		System.out.print(e);
		}
		//out.write("</body></html>");
	}
}
