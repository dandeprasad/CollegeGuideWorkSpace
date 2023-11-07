package org.AmazonSupport;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AmazonSupportUploads {

	public Boolean ImageUpload(byte[] dande,String filename,String S3path){
		try {

		   
		   InputStream is = new ByteArrayInputStream(dande);
		   InputStream credentialsAsStream = Thread.currentThread().getContextClassLoader()
	                .getResourceAsStream("AwsCredentials.properties");
		@SuppressWarnings("deprecation")
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(credentialsAsStream));
		

		   ObjectMetadata meta = new ObjectMetadata();
		   meta.setContentLength(dande.length);
		   meta.setContentType("image/jpg");
		   String key = S3path+filename;
		  
		//   s3client.setObjectAcl(bucketName, keyName, CannedAccessControlList.PublicRead);

		s3.putObject(new PutObjectRequest("elasticbeanstalk-us-east-2-083183914236",key, is, meta));
		 s3.setObjectAcl("elasticbeanstalk-us-east-2-083183914236", key,CannedAccessControlList.PublicRead);
		return true;
		}
		catch(Exception ex){
			System.out.print(ex);
			return false;
		}
		
	}
}
