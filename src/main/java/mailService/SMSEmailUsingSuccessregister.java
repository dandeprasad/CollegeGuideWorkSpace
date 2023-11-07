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

public class SMSEmailUsingSuccessregister {
   public UserDataMaintance  SUCCESSFULLREGISTER(String userdata) {
	 
      // Recipient's email ID needs to be mentioned.
      String to =  userdata;//change accordingly

      // Sender's email ID needs to be mentioned
      String from = "teamcollegeexplore@collegeexplore.in";//change accordingly
      final String username = "teamcollegeexplore@collegeexplore.in";//change accordingly
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
         message.setSubject("CollegeExplore");


         message.setText("Thanks for registering with us -Team:CollegeExplore");

         // Send message
         Transport.send(message);

         System.out.println("message Sent  successfully....");

         return null;
      } catch (MessagingException e) {
    	  
            throw new RuntimeException(e);
        	
      }

   }
}





