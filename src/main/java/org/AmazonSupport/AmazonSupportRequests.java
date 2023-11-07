package org.AmazonSupport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class AmazonSupportRequests {

	public void requestThroughAmazon(HttpServletResponse response, String FilePath, String filename) {
		
		//https://console.aws.amazon.com/s3/
		@SuppressWarnings("deprecation")
		//AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());        
		AmazonS3 s3Client = new AmazonS3Client(DefaultAWSCredentialsProviderChain.getInstance());
		S3Object object = s3Client.getObject(
		                  new GetObjectRequest("elasticbeanstalk-us-east-2-083183914236", FilePath+filename));
		InputStream input = object.getObjectContent();
		// Process the objectData stream.
		
	//	InputStream input = new FileInputStream("https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-083183914236/CLGS_PROJECT/homeadsnews"+filename);

/*		int data = input.read();

		while(data != -1){
		  data = input.read();
		}*/
	

		        //File file = new File("/Images/position_1.png");
		        File file = new File(FilePath, filename);
		       // ServletContext context = getServletContext();
		       // URL resourceUrl = context.getResource("/CollegeGuideWorkSpace/Images/position_1.png");
	//File path = new File("C:/GradleProject/CLGS_PROJECT/CarrerGuidance");
	//getClass().getClassLoader().getResource("resources/images/logo.png");
//	String pathToImageSortBy = "/Images/position_0.png";
//	URL x =getClass().getClassLoader().getResource(pathToImageSortBy);
   // File file = new File("jar:file:/C:/GradleProject/CollegeGuideWorkSpace/build/libs/CollegeGuideWorkSpace.war!/WEB-INF/classes/Images/",filename);
   response.setHeader("Content-Type", object.getObjectMetadata().getContentType());
   
  //  response.setHeader("Content-Length", String.valueOf(file.length()));
 //   response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
   // Files.copy(file.toPath(), response.getOutputStream());	
   // response.getOutputStream().write(data);
    try {
		IOUtils.copy(input, response.getOutputStream());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
		input.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	};
	
	
}
