package org.login.tester;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginManager {
	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		
		//HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext();
		
		//request.getSession();
		HttpSession session = (HttpSession)externalContext.getSession(false);
		session.invalidate();
		return "?faces-redirect=true";
	}
	
	public boolean isLogged() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		
		return externalContext.getUserPrincipal() != null;
	}
	
	public Object getSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		
		return externalContext.getSession(false);
	}
	public String getRemoteUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		
		return externalContext.getRemoteUser();
	}
	public String getAuthType() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		
		return externalContext.getAuthType();
	}
	public boolean isRoleManager() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		
		return externalContext.isUserInRole("Manager");
	}
}
