package massEmailer;

import com.sun.mail.smtp.SMTPTransport;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 *
 * @author doraemon
 */
public class Main {
	
	ArrayList<String> accounts = new ArrayList<String>();
	String password = "xlADY{hVQSCH7lP";
	String recipientEmail = "parkercanit@taco.byu.edu";
	String title = "Test Email";
	String message = "";
	
	
	public static void main(String[] args){
		Main main = new Main();
		main.initialize();
		main.spam();
	}

    /**
     * Send email using GMail SMTP server.
     *
     * @param username GMail username
     * @param password GMail password
     * @param recipientEmail TO recipient
     * @param ccEmail CC recipient. Can be empty if there is no CC recipient
     * @param title title of the message
     * @param message message to be sent
     * @throws AddressException if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
     */
    public void Send(String username, String title, String message) throws AddressException, MessagingException {
    	System.out.println("Title: "+title + " " + "Message: "+message);
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        /*
        If set to false, the QUIT command is sent and the connection is immediately closed. If set 
        to true (the default), causes the transport to wait for the response to the QUIT command.

        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
                http://forum.java.sun.com/thread.jspa?threadID=5205249
                smtpsend.java - demo program from javamail
        */
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(username + "@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));


        msg.setSubject(title);
        msg.setText(message, "utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", username, password);
        t.sendMessage(msg, msg.getAllRecipients());      
        t.close();
    }
    
    private void initialize(){
    	accounts.add("canittest1");
    	accounts.add("canittest2");
    	
    	

    }
    
    private void spam(){
    	Scanner scnr = null;
		try {
			scnr = new Scanner( new FileReader("quotes.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
    	for(int size = 0; size<accounts.size(); size++){
        	for(int i = 0; i<420; i++){
				try {
					

					Send(accounts.get(size), "Test Email"+i, scnr.nextLine());
					
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
    	}
    	scnr.close();

    	
    }
}