package logic;

import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import exception.MailSenderException;
import common.ServiceLocatorException;

 
public class MailSender{
 
	static Logger logger = Logger.getLogger(MailSender.class.getName());
	static MailSender sender;

    public static void sendEmail(String fromAddress, String toAddress, String subject, String mailBody) {                   
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.cse.unsw.edu.au");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                          new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                               return new PasswordAuthentication("9321hotel@gmail.com", "testhotel");
                            }
                          });

         try {
               Message message = new MimeMessage(session);
               message.setFrom(new InternetAddress(fromAddress));
               message.setRecipients(Message.RecipientType.TO,
                             InternetAddress.parse(toAddress));
               message.setSubject(subject);
               message.setText(mailBody);
               Transport.send(message); 
             } catch (MessagingException e) {
                 throw new RuntimeException(e);
             }
     }
}