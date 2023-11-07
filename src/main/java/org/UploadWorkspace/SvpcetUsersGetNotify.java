package org.UploadWorkspace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

public class SvpcetUsersGetNotify {

	// http://localhost:8080/RESTfulExample/json/product/post
	public void newsmessage(String messageTosend) {

	  try {

		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "key=AAAAKcwLucs:APA91bH7-354w1AA94L3R3x6eeVAY39Zp1NEKcVEmBdsXJ2CBYFgVHUfIaYtTVNdgajEuqINibiNPAqQod1BprJxv28COTt_G36ikF_ooFzSiUXphcEfGPw8y_TiZ_WQ3wNOt-LlSlfz");
		
		//String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
//String input ="{ \"to\" : \"/topics/NEWS\",\"priority\": \"high\",\"data\": {\"MYDATA\":{ \"USERNAME\": \"\",\"APP_KEY\": \"NEWS_WORKSPACE\",\"TITLE\": \"MET Entrance is going to be held on 19 APRIL 2018\",\"MESSAGE_DETAIL\": \"The  MU OET  entrance examination will be conducted in offline mode. There would be negative marking so marks would be deducted for any incorrect answers. The exam pattern according to the marks in the tabular form for the  MU OET  2018 Test is given as follows\",\"data\": { \"NEWS_HEADER\": \"MET Entrance is going to be held on 19 APRIL 2018\",\"DATA_IMAGE\":\"https://elasticbeanstalk-us-east-2-083183914236.s3.us-east-2.amazonaws.com/ImagesLocal/NIT_Trichy_logo.jpg\",\"NEWS_DETAILS\":\"The  MU OET  entrance examination will be conducted in offline mode. There would be negative marking so marks would be deducted for any incorrect answers. The exam pattern according to the marks in the tabular form for the  MU OET  2018 Test is given as follows\",\"NEWS_IMAGE\":\"https://elasticbeanstalk-us-east-2-083183914236.s3.us-east-2.amazonaws.com/ImagesLocal/NIT_Trichy_logo.jpg\"}}}}";
//String input = "{\"to\": \""+tOKEN_ID+"\",\"priority\": \"high\",\"data\": {\"MYDATA\": {\"APP_KEY\": \"BASIC\",\"TITLE\": \""+Type+"\",\"MESSAGE_DETAIL\": \""+notifyheader+"\"}}}";

String input = messageTosend;
System.out.println("message sent to fcm"+input);
		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }

	}

}