package com.scandocviewer.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.scandocviewer.beans.SendEmailData;
import com.scandocviewer.exceptions.ScandocException; 

@Service
public class EmailUtils {
	
	public static Map<String, Object> emailSesssion = new HashMap<>();
	
	@Autowired
	Environment env;
	
	private static final Logger logger = Logger.getLogger(EmailUtils.class); 

	
	public Session getSession(){
		
		 if(checkSMTPConnection()){
			 
			 Properties props = new Properties();
			  
		      props.put(env.getProperty("email.prop.host"), env.getProperty("email.smtp.host.name"));
		      props.put(env.getProperty("email.prop.sendpartial"), "true");  
		      
		     		        
		      Session session = Session.getInstance(props, null);
		      
		       
			  return session;
		 }else{
			  return null; 
		 } 
	  
	}
	
	public void sendEmailWithAttachment(Session session, SendEmailData emailData, String attchmentPath) throws ScandocException{  
			 
		try{ 
	    	  Path path = Paths.get(attchmentPath);

				
		   	  Message message = new MimeMessage(session);
		   	  
		   	  if(StringUtils.isEmpty(emailData.getEmailFrom()))
		   		  throw new ScandocException( env.getProperty("errMsg.email.emalFrom"));
		   	  else if(StringUtils.isEmpty(emailData.getEmailTo()))
		   		  throw new ScandocException( env.getProperty("errMsg.email.emalTo"));
		   	  else if(StringUtils.isEmpty(emailData.getSubject()))
		   		  throw new ScandocException( env.getProperty("errMsg.email.Subject"));
 		   	  
	          message.setFrom(new InternetAddress(emailData.getEmailFrom()));	          
	          message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailData.getEmailTo()));
	          message.setSubject(emailData.getSubject());
	          
	          
	          Multipart multipart = new MimeMultipart();
	          
	          BodyPart messageBodyPart = new MimeBodyPart();
	          
	          // Adding Message Body
			  messageBodyPart.setContent(addDisclaimer(emailData.getMessage()), "text/html"); 
	          multipart.addBodyPart(messageBodyPart); 
	          
	          //Adding message attachment
	          
	          messageBodyPart = new MimeBodyPart();
	          DataSource source = new FileDataSource(attchmentPath);
	          messageBodyPart.setDataHandler(new DataHandler(source));
	          messageBodyPart.setFileName(path.getFileName().toString());
	          multipart.addBodyPart(messageBodyPart);

	          // Send the complete message parts
	          message.setContent(multipart);

	          // Send message
	          Transport.send(message); 
		}catch (Exception e) {
			logger.error("EmailUtils.sendEmailWithAttachment(): Failed :" + e); 			
			throw new ScandocException(e.getMessage());
		}	          
	  
	}
	
	private String addDisclaimer(String message){
		
		message = StringUtils.isEmpty(message)? "" : message.replaceAll("(\r\n|\n)", "<br />");
		
		StringBuilder htmlMsg = new StringBuilder()
									.append("<html>")
									.append("<p>")
									.append(message)
									.append("</p><br/><br/>")
									.append("<Strong>DO NOT REPLY, THIS MAILBOX IS NOT MONITORED, Contact your DB Schenker representative. <Strong>")
									.append("</html>");	
		return htmlMsg.toString();
	}
	
	
	private boolean checkSMTPConnection(){
		boolean connected = false;
		
		if(emailSesssion.containsKey("lastVeifiedTime")){
			Date lastVeifiedTime = (Date) emailSesssion.get("lastVeifiedTime");
			Date currentTim = new Date();
			
			if((currentTim.getTime() - lastVeifiedTime.getTime())/(60*100) < 60){
				connected = true;
			}else{
				emailSesssion.clear();
				connected = verifySMTPConnection();
				if(connected)
					emailSesssion.put("lastVeifiedTime", new Date());
			}
			
		}else{
			connected = verifySMTPConnection();
			if(connected)
				emailSesssion.put("lastVeifiedTime", new Date());
		}
		
		return connected;
	}
	
	
	private boolean verifySMTPConnection(){
		  SMTPClient client;
	        try {
	            client = new SMTPClient();
	            try {
	                client.connect(env.getProperty("email.smtp.host.name")); 
	            } catch (IOException e1) {
 	                return false;
	            } finally {
	                if (client.isConnected()) {
	                    client.disconnect();
	                }
	            }

	        } catch (Exception e) {
 	        }
	        return true;
	}

}
