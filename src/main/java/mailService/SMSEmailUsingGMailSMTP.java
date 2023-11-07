package mailService;
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

public class SMSEmailUsingGMailSMTP {
   public UserDataMaintance  OTPTRIGGER(UserDataMaintance userdata) {
	   UserDataMaintance valueToPass =userdata;
      // Recipient's email ID needs to be mentioned.
      String to = valueToPass.getUSER_EMAIL();//change accordingly

      // Sender's email ID needs to be mentioned
      String from = "do-not-reply@collegeexplore.in";//change accordingly
      final String username = "do-not-reply@collegeexplore.in";//change accordingly
      final String password = "Abc1234567890";//change accordingly

      // Assuming you are sending email through relay.jangosmtp.net
    //  String host = "smtp.gmail.com";
      String host = "smtp-relay.gmail.com";
     
      Properties props = new Properties();
      
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "587");
      
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
         message.setSubject("OTP for College Explore Verification #NoReply");

         Random rnd = new Random();
         int Randno = 100000 + rnd.nextInt(900000);
         // Now set the actual message
         message.setText("Please Use the  OTP for Verification to register for COLLEGEEXPLORE OPT:"+Randno+" ");

         // Send message
         Transport.send(message);

         System.out.println("OTP triggerd and Sent message successfully....");
         valueToPass.setOTP_STATUS("000000");
         valueToPass.setOTP_TRIGGER_VALUE(Integer.toString(Randno));
         return valueToPass;
      } catch (MessagingException e) {
    	  
            throw new RuntimeException(e);
        	
      }

   }
}





