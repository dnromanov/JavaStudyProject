package util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtils {

    public static void send(String from, String pass, String to, String subject, String msg){
        Session session = Session.getInstance(AppSettings.getInstance().getProps(),
                  new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, pass);
                    }
                  });
          try {
                
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));
                message.setSubject(subject);
                message.setContent(msg, "text/html");
                
                Transport.send(message);
                System.out.println("SEND MSG TO :" + to);
                
            } catch (AddressException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
    }
    
    
    /**
     * Sends an e-mail message from a SMTP host with a list of attached files. 
     * 
     */
    public static void sendEmailWithAttachment(final String userName, final String password, String toAddress,
            String subject, String message, List<File> attachedFiles)
                    throws AddressException, MessagingException {
        // sets SMTP server properties
//      Properties properties = new Properties();
//      properties.put("mail.smtp.host", host);
//      properties.put("mail.smtp.port", port);
//      properties.put("mail.smtp.auth", "true");
//      properties.put("mail.smtp.starttls.enable", "true");
//      properties.put("mail.user", userName);
//      properties.put("mail.password", password);

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(AppSettings.getInstance().getProps(), auth);
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        // adds attachments
        if (attachedFiles != null && attachedFiles.size() > 0) {
            for (File aFile : attachedFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.attachFile(aFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                multipart.addBodyPart(attachPart);
            }
        }
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);

        // sends the e-mail
        Transport.send(msg);
    }
    
    public static void main(String[] args) {
        
        String fromEmail = AppSettings.getInstance().getProperty("mailUser");
        String pass = AppSettings.getInstance().getProperty("mailPass");
        String msg = "<h1><a href='#'>Hello from JAVA APP!</a></h1>";
        String toEmail = "dn_thief@inbox.ru"; 
        String subject = "Denis. This is not SPAM!" + new java.util.Date();
        
       
        //send(fromEmail, pass, toEmail, subject, msg);
        
       try {
        sendEmailWithAttachment(fromEmail, pass, toEmail, subject, msg, null);
    } catch (AddressException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (MessagingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
      
        
    }

}