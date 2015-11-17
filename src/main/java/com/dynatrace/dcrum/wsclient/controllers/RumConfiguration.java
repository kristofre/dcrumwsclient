package com.dynatrace.dcrum.wsclient.controllers;

public class RumConfiguration {
	private String serverURL;
	private String servicesURL;
	private String user;
	private String password;
	
	public RumConfiguration()
	{
		this.setServerURL(serverURL);
		this.setUser(user);
		this.setPassword(password);
		this.setServicesURL("/services/DMIService/");
	}
	
	public RumConfiguration(String serverURL, String user, String password, String servicesURL)
	{
		this.setServerURL(serverURL);
		this.setUser(user);
		this.setPassword(password);
		this.setServicesURL(servicesURL);
	}
	
	public String getServerURL() {
		return this.serverURL;
	}
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}
	public String getServicesURL() {
		return this.servicesURL;
	}
	public void setServicesURL(String servicesURL) {
		this.servicesURL = servicesURL;
	}
	public String getUser() {
		return this.user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFullURL(){
		return this.serverURL + this.servicesURL;
	}

}
