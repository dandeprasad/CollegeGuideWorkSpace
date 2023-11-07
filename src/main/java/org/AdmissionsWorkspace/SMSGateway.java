package org.AdmissionsWorkspace;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.HomeWorkspace.UserDataMaintance;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class SMSGateway {
   public String  SendMessage(String  userdata,String Mail) {
	   
		  Configuration config = null;
			try {
				config = new PropertiesConfiguration("ConfigurePath.properties");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
	  // UserDataMaintance valueToPass =userdata;
      // Recipient's email ID needs to be mentioned.
      String to = Mail;//change accordingly

      // Sender's email ID needs to be mentioned
      String from = config.getString("sms_from_mail");//change accordingly
      final String username = config.getString("sms_from_mail");//change accordingly
      final String password = config.getString("sms_from_mail_password");//change accordingly


		
      // Assuming you are sending email through relay.jangosmtp.net
    //  String host = "smtp.gmail.com";
      String host = config.getString("sms_host");
     
      Properties props = new Properties();
      
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", config.getString("sms_port"));
      
      // Get the Session object.
      Session session = Session.getInstance(props,
      new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
         }
      });

      try {
         // Create a default MimeMessage object.
         Message message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.setRecipients(Message.RecipientType.TO,
         InternetAddress.parse(to));

         // Set Subject: header field
         message.setSubject(userdata);

         // Now set the actual message
         message.setText(userdata);

         // Send message
         Transport.send(message);

       //  System.out.println("OTP triggerd and Sent message successfully....");
         //valueToPass.setOTP_STATUS("000000");
        // valueToPass.setOTP_TRIGGER_VALUE(Integer.toString(Randno));
         return "000000";
      } catch (MessagingException e) {
    	  
            throw new RuntimeException(e);
        	
      }

   }
}





