package tomczak.mail.session.tester;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class EmailSenderController {
	private String email;
	private String jndi ="shopMail";
	private String userProp = "mail.session.mail.smtp.user";
	private String passwordProp = "mail.session.mail.smtp.password";
	private String errorDetail;
	private Object[] properties;
	private int vendorCode = 1;
	
	@PostConstruct
	public void init() {
		System.out.println(this.getClass().getName() + " init");
	}
	
	public String send() {
		if (email.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Empty Email"));
		} 
		if (jndi.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Empty JNDI"));
		}
		if (userProp.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Empty User prop"));
		}
		if (passwordProp.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Empty Password prop"));
		}
		try {
			errorDetail = "";
			EmailSender es = null;
			if (vendorCode==1) {
				es = new AuthenticatedEmailSesnder(email, jndi, currentHost(), userProp, passwordProp);
			} else {
				es = new EmailSender(email, jndi, currentHost());
			}
			es.sendNotice();
			properties = es.getProperties().entrySet().toArray();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your message should be sent. Check our mailbox."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getClass().getName(), e.getMessage()));
			errorDetail = e.getMessage();
			errorDetail += "<br/>";
			for (StackTraceElement se : e.getStackTrace()) {
				errorDetail += se.toString();
				errorDetail += "<br/>";
			}
			e.printStackTrace();
		}
		return null;
	}

	private String currentHost() throws MalformedURLException {
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			return new URL(httpRequest.getScheme(), httpRequest.getServerName(), "").toString();
		}
		return null;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJndi() {
		return jndi;
	}
	public void setJndi(String jndi) {
		this.jndi = jndi;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public String getUserProp() {
		return userProp;
	}

	public void setUserProp(String userProp) {
		this.userProp = userProp;
	}

	public String getPasswordProp() {
		return passwordProp;
	}

	public void setPasswordProp(String passwordProp) {
		this.passwordProp = passwordProp;
	}

	public Object[] getProperties() {
		return properties;
	}

	public void setProperties(Object[] properties) {
		this.properties = properties;
	}

	public int getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(int vendorCode) {
		this.vendorCode = vendorCode;
	}
	
	
}
