package com.dynatrace.dcrum.wsclient.client;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;

import com.dynatrace.dcrum.wsclient.controllers.RumConfiguration;

import adlex.delta.ws.DMIData;
import adlex.delta.ws.DMIData2;
import adlex.delta.ws.DMIService;
import adlex.delta.ws.DMIServiceError;
import adlex.delta.ws.DMIServiceServiceLocator;

public class RumClient {
	
	static String appId = "CVENT";
	static String viewId;
	static String dataSourceId;
	static String[] dimensionIds;
	static String[] metricIds;
	static String[][] dimFilters;
	static String[][] metricFilters;
	static String[][] sort;
	static Integer top;
	static String resolution = "r";
	static String timePeriod;
	static Integer numberOfPeriods;
	static Long timeBegin;
	static Long timeEnd;
	static Long timeout;
	
	public static void main(String[] args) {
		 
		RumConfiguration config = new RumConfiguration();
		try {
			//  create locator 
			DMIServiceServiceLocator locator = new DMIServiceServiceLocator();
			String defURL = "http://cas1.emea.demo.compuware.com";
			String serviceURL = defURL + "/services/DMIService";
			String user = config.getUser(); // replace with username
			String password = config.getPassword(); // replace with password
			
			//Create a DMIService object.
			DMIService service = locator.getDMIService(new URL(serviceURL));
			
			//Authenticate the connection to the server.
			((Stub)service).setUsername(user);
			((Stub)service).setPassword(password);
			
			//Add the getServerUUID method.
			System.out.print("server UUID=");
			System.out.println(service.getServerUUID());
						
			getDimensions(service);
			getMetrics(service);
			
			/*
			//getApplications method.
			String[][] applications = service.getApplications();
			System.out.println("------- getApplications");
			for (int i = 0; i < applications.length; i++) {
			    String[] app = applications[i];
			    System.out.println(app[0] + " : " + app[1]);   
			}
			
			//getResolutions
			viewId= "ClientView"; //ads
			String[] resolutions = service.getResolutions(appId, viewId);
			for (String string : resolutions) {
			     System.out.println(string);
			}
			
			//getDataViews method.
			String[][] dataViews = service.getDataViews(appId);
			System.out.println("------- getDataViews");
			for (int i = 0; i < dataViews.length; i++) {
			    String[] app = dataViews[i];
			    System.out.println(app[0] + " : " + app[1]);   
			}
			
			//getMetrics method.
			String[][] metrics = service.getMetrics(appId, viewId, resolution);
			System.out.println("------- getMetrics");
			for (int i = 0; i < metrics.length; i++) {
			    String[] app = metrics[i];
			    System.out.println(app[0] + " : " + app[1]);   
			}
			
			
			setCriteria();
			
			getDMIData(service);
			getDMIData2(service);
			
			*/
			
			
		} catch (ServiceException e) {
				e.printStackTrace();
		} catch (RemoteException e) {
				e.printStackTrace();
		} catch (MalformedURLException e) {
				e.printStackTrace();
		}
	
	}
	
	private static void getDMIData(DMIService service) throws RemoteException
	{
		//viewId= "ClientView";   
		DMIData data = service.getDMIData(appId, viewId, dataSourceId, dimensionIds,
				metricIds, dimFilters, metricFilters, sort, top, 
				resolution, timePeriod, numberOfPeriods, timeBegin, timeEnd, timeout);
		if (data.isTimeout()) {
			System.out.println("timeout");
			return;
		}   
		showErrors(data); //displays results   
		showResults(data); //displays results
	}
	
	private static void getDMIData2(DMIService service) throws RemoteException
	{
		//viewId= "ClientView";   
		DMIData2 data = service.getDMIData2(appId, viewId, dataSourceId, dimensionIds,
				metricIds, dimFilters, metricFilters, sort, top, 
				resolution, timePeriod, numberOfPeriods, timeBegin, timeEnd, timeout);
		if (data.isTimeout()) {
			System.out.println("timeout");
			return;
		}   
		showErrors(data); //displays results   
		showResultsData2(data); //displays results
	}
	
	private static void getDimensions(DMIService service) throws RemoteException
	{
		String casApp = "CVENT";
		String[][] dataViews = service.getDataViews(casApp);
		for (int j = 0; j < dataViews.length; j++) {
		    String[] view = dataViews[j];
		    // GET RESOLUTIONS
		    String[] resolutions = service.getResolutions(casApp, view[0]);
			for (String res : resolutions) {
				// GET DIMENSIONS
				String[][] dimensions = service.getDimensions(casApp, view[0], res);
				for (int k = 0; k < dimensions.length; k++) {
				    String[] dim = dimensions[k];
				    System.out.println(casApp + ":" + view[0] + ":" + res + ":" + dim[0]+ ":" + dim[1]);
				}
			}
		}
	}
	
	private static void getMetrics(DMIService service) throws RemoteException
	{
		String casApp = "CVENT";
		String[][] dataViews = service.getDataViews(casApp);
		for (int j = 0; j < dataViews.length; j++) {
		    String[] view = dataViews[j];
		    // GET RESOLUTIONS
		    String[] resolutions = service.getResolutions(casApp, view[0]);
			for (String res : resolutions) {
				// GET DIMENSIONS
				String[][] metrics = service.getMetrics(casApp, view[0], res);
				for (int k = 0; k < metrics.length; k++) {
				    String[] met = metrics[k];
				    System.out.println(casApp + ":" + view[0] + ":" + res + ":" + met[0] + ":" + met[1]);
				}
			}
		}
	}
	
	/**
	  * @param data
	  */
	 private static void showResults(DMIData data) {
		 System.out.println("\n---------- getDMIData1 ----------");
	  System.out.print("\ncolumnHeaders: ");
	  String[] columnHeader = data.getColumnHeader();
	  for (int I = 0; I < columnHeader.length; I++) {
	   System.out.print(columnHeader[I]+ ", ");
	  }
	  System.out.print("\nformattedData");
	  String[][] formattedData = data.getFormattedData();
	  for (int I = 0; I < formattedData.length; I++) {
	   System.out.print("\n\t--row no:" + I + " | ");
	   String[] fdata = formattedData[I];
	   for (int j = 0; j < fdata.length; j++) {     
	    System.out.print(fdata[j] + "    "); 
	   }
	  }
	 }
	 private static void showResultsData2(DMIData2 data) {
		 System.out.println("\n---------- getDMIData2 ----------");
		  System.out.print("\ncolumnHeaders: ");
		  String[] columnHeader = data.getColumnHeaderName();
		  String[] columnUnit = data.getColumnUnit();
		  for (int I = 0; I < columnHeader.length; I++) {
		   System.out.print(columnHeader[I]+ ", ");
		  }
		 
		  System.out.print("\nformattedData");
		  String[][] formattedData = data.getFormattedData();
		  for (int I = 0; I < formattedData.length; I++) {
		   System.out.print("\n\t--row no:" + I + " | ");
		   String[] fdata = formattedData[I];
		   for (int j = 0; j < fdata.length; j++) {     
		    System.out.print(fdata[j] + columnUnit[j] + "    "); 
		   }
		  }
		 }
	//b. Add the showErrors method.
	/**
	  * @param dmiServiceError
	  */
	private static boolean showErrors(DMIData data) {
		DMIServiceError[] dmiServiceError = data.getDmiServiceError();
		if (dmiServiceError== null) {
			return false;
		}
		//Data Center Real User Monitoring DMI Web Services Getting Started  
		for (int I = 0; I < dmiServiceError.length; I++) {
		DMIServiceError error = dmiServiceError[I];
		String[] descriptions = error.getErrorDescriptions();
		if (descriptions!= null) {
			System.out.println("-errors");
			for (int j = 0; j < descriptions.length; j++) {
				System.out.println(descriptions[j]);
			}
		}
		descriptions = error.getWarningDescriptions();
		if (descriptions!= null) {
			System.out.println("-warnings");
			for (int j = 0; j < descriptions.length; j++) {
				System.out.println(descriptions[j]);
			}
		}
		descriptions = error.getInfoDescriptions();
		if (descriptions!= null) {
			System.out.println("-info");
			for (int j = 0; j < descriptions.length; j++) {
				System.out.println(descriptions[j]);
			}
		}
		}
	return true;
}
	
	private static void setCriteria() {
		  appId = "CVENT"; //vas
		  viewId= "ClientView"; // 
		  resolution = "r";
		  dataSourceId = "localhost";
		  dimensionIds = new String [] { "appl"};
		  metricIds = new String []{"trans", "netDelay", "abortspc"};  
		  //dimensionIds = new String [] {  "bgAppl","trans"};
		  dimFilters = new String [][]{{"appl","easyTravel*",""}};  
		  metricFilters = new String [][]{{"trans",">","0",""}}; 
		  sort = new String[][]{{"appl","DESC"}};
		  top =10;
		  resolution = "r";
		  timePeriod = "1D";
		  numberOfPeriods = null;
		  timeEnd =  null; 
		  timeBegin = null;
		  timeout = 60000L    ;
		 
		}

}

