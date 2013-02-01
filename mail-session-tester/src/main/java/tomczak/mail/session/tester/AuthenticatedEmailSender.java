package tomczak.mail.session.tester;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.naming.NamingException;

public class AuthenticatedEmailSender extends EmailSender {

	private String userProp;
	private String passwordProp;

	public AuthenticatedEmailSender(String email, String jndi, String host, String userProp, String passwordProp) {
		super(email, jndi, host);
		this.userProp = userProp;
		this.passwordProp = passwordProp;
	}
	
	@Override
	protected Session produceSession() throws NamingException {
		Session session = super.produceSession();
		Properties props = session.getProperties();
		String user = props.getProperty(userProp);
		String password = props.getProperty(passwordProp);
		
		return Session.getInstance(props, new MailAuthenticator(user,password));
	}
	
	private class MailAuthenticator extends Authenticator{
		private PasswordAuthentication auth;
		public MailAuthenticator(String user, String password) {
			this.auth = new PasswordAuthentication(user, password);
		}
		
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return auth;
		}
	}
}
