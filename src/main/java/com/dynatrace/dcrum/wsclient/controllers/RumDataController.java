package com.dynatrace.dcrum.wsclient.controllers;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;

import adlex.delta.ws.DMIService;
import adlex.delta.ws.DMIServiceServiceLocator;

public class RumDataController {

	DMIServiceServiceLocator serviceLocator = null;
	DMIService dmiService = null;
	
	public RumDataController(RumConfiguration configuration) 
	{
		try {
			serviceLocator = new DMIServiceServiceLocator();
			dmiService = serviceLocator.getDMIService(new URL(configuration.getFullURL()));
			((Stub)dmiService).setUsername(configuration.getUser());
			((Stub)dmiService).setPassword(configuration.getPassword());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
