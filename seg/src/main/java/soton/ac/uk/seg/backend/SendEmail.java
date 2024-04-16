package soton.ac.uk.seg.backend;



import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;

public class SendEmail {



    public void SendMail( String recipientEmail, String passwordeee){
        String senderEmail = "ae2798884@gmail.com";
        String senderPassword = "H_#sCScQy4.)*W~";



        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587"; 

        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
  
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            message.setSubject("This is your password! ");
            message.setText(passwordeee);

            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }

    }



}
