package tomczak.mail.session.tester;

import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EmailSender {
	private Properties properties;
	private String host;
	private String email;
	private String jndi;
	
	public EmailSender(String email, String jndi, String host) {
		this.email = email;
		this.jndi = jndi;
		this.host = host;
	}
	
	protected Session produceSession() throws NamingException {
		InitialContext ctx = new javax.naming.InitialContext();
		return (Session) ctx.lookup(jndi);
	}
	
	public void sendNotice() throws MessagingException, NamingException {
		Session session = produceSession();
		properties = session.getProperties();
        Message message = new MimeMessage(session);
        message.setFrom();
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email, false));
        message.setSubject(String.format("Test Message to confirm '%s' mail session is working correctly.",jndi));
        DateFormat dateFormatter = DateFormat
                .getDateTimeInstance(DateFormat.LONG,
                DateFormat.SHORT);
        Date timeStamp = new Date();

        String messageText = String.format("This is a test message.%n This message was send from %n")
                + String.format("\t %s%n%n", host)
                + dateFormatter.format(timeStamp);
        message.setText(messageText);

        message.setSentDate(timeStamp);

        Transport.send(message);
	}
	Properties getProperties() {
		return properties;
	}
}
