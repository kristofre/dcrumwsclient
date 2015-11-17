package com.dynatrace.dcrum.wsclient.client;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.naming.ldap.StartTlsRequest;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.dynatrace.dcrum.wsclient.controllers.RumConfiguration;

import adlex.delta.ws.DMIData;
import adlex.delta.ws.DMIData2;
import adlex.delta.ws.DMIService;
import adlex.delta.ws.DMIServiceError;
import adlex.delta.ws.DMIServiceServiceLocator;

public class RumWsClient {
	
	static String appId = "CVENT";
	static String viewId;
	static String dataSourceId;
	static String[] dimensionIds;
	static String[] metricIds;
	static String[][] dimFilters;
	static String[][] metricFilters;
	static String[][] sort;
	static Integer top;
	static String resolution;
	static String timePeriod;
	static Integer numberOfPeriods;
	static Long timeBegin;
	static Long timeEnd;
	static Long timeout;
	
	static HashMap<String, String> resoHM = initResolutions();
	
	public static void main(String[] args) {
		
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		Option casOption = new Option("s", "server", true, "target CAS (eg: http://mycas.internal) [required]");
		Option userOption = new Option("u", "user", true, "user name [required]");
		Option pwdOption = new Option("p", "password", true, "password [required]");
		casOption.setRequired(true);
		userOption.setRequired(true);
		pwdOption.setRequired(true);
		options.addOption(casOption);
		options.addOption(userOption);
		options.addOption(pwdOption);
		options.addOption("a", "api", true, "api URI [optional, defaults to /services/DMIService]");
		RumConfiguration config = new RumConfiguration();
		try
		{
			CommandLine line = parser.parse( options, args );

			
			
			String server = "";
			String user = "";
			String password = "";
			if (line.hasOption("server")) config.setServerURL(line.getOptionValue("server"));

			if (line.hasOption("user")) config.setUser(line.getOptionValue("user"));
			
			if (line.hasOption("password")) config.setPassword(line.getOptionValue("password"));
			
			config.setServicesURL("/services/DMIService");
			
			start(config);

			
		} catch (ParseException e) {
			System.out.println( e.getMessage());
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("profilevalidator", options);
		}
		
		
		
	
	}
	
	private static void start(RumConfiguration config)
	{
	//  create locator  
			DMIServiceServiceLocator locator = new DMIServiceServiceLocator();
			try {
				/*String defURL = "http://cas1.emea.demo.compuware.com";
				String serviceURL = defURL + "/services/DMIService";*/
				
				//String qURL = "URL (eg: <http://mycas.internal> (without <>): "
				//String qUser = "Username: ";
				//String qPwd = "Password: ";
				//String 
				
				//Create a DMIService object.
				DMIService service = locator.getDMIService(new URL(config.getFullURL()));
				System.out.println("URL: [" + config.getFullURL() + "]");
				
				//4. Authenticate the connection to the server.
				((Stub)service).setUsername(config.getUser());
				((Stub)service).setPassword(config.getPassword());
				System.out.println("USER: [" + config.getUser() + "]");
				System.out.println("PASS: [" + config.getPassword() + "]");
				//5. Add the getServerUUID method.
				System.out.print("server UUID=");
				
				System.out.println(service.getServerUUID());
				
				printDataViewsToFile(service);
				printDimensionsToFile(service);
				printMetricsToFile(service);
				/*setCriteria();
				
				getDMIData(service);
				getDMIData2(service);*/
				
			} catch (ServiceException e) {
					e.printStackTrace();
			} catch (RemoteException e) {
					e.printStackTrace();
			} catch (MalformedURLException e) {
					e.printStackTrace();
			}
	}
	
	private static void printDataViewsToFile(DMIService service) throws RemoteException
	{
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("dataviews.txt", "UTF-8");
			String[][] applications = service.getApplications();
			for (int i = 0; i < applications.length; i++) {
				String[] casApp = applications[i];
				String[][] dataViews = service.getDataViews(casApp[0]);
				for (int j = 0; j < dataViews.length; j++) {
				    String[] view = dataViews[j];
				    System.out.println(casApp[1] + ":" + casApp[0] + ":" +view[0] + ":" + view[1]);
				    writer.println(casApp[1] + ":" + casApp[0] + ":" + view[0] + ":" + view[1]);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
		
	}
	
	private static void getDMIData(DMIService service) throws RemoteException
	{
		viewId= "ClientView";   
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
		viewId= "ClientView";   
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
	
	private static void printDimensionsToFile(DMIService service) throws RemoteException
	{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("dimensions.txt", "UTF-8");
			String[][] applications = service.getApplications();
			for (int i = 0; i < applications.length; i++) {
				String[] casApp = applications[i];
				String[][] dataViews = service.getDataViews(casApp[0]);
				for (int j = 0; j < dataViews.length; j++) {
				    String[] view = dataViews[j];
				    // GET RESOLUTIONS
				    String[] resolutions = service.getResolutions(casApp[0], view[0]);
					for (String res : resolutions) {
						// GET DIMENSIONS
						String[][] dimensions = service.getDimensions(casApp[0], view[0], res);
						for (int k = 0; k < dimensions.length; k++) {
						    String[] dim = dimensions[k];
						    System.out.println(casApp[1] + ":" + casApp[0] + ":" + view[1] + ":" + view[0] + ":" + resoHM.get(res) + ":" + res + ":" + dim[0]+ ":" + dim[1]);
						    writer.println(casApp[1] + ":" + casApp[0] + ":" + view[1] + ":" + view[0] + ":" + resoHM.get(res) + ":" + res + ":" + dim[0]+ ":" + dim[1]);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
	private static void printMetricsToFile(DMIService service) throws RemoteException
	{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("metrics.txt", "UTF-8");
			String[][] applications = service.getApplications();
			for (int i = 0; i < applications.length; i++) {
				String[] casApp = applications[i];
				String[][] dataViews = service.getDataViews(casApp[0]);
				for (int j = 0; j < dataViews.length; j++) {
					String[] view = dataViews[j];
					// GET RESOLUTIONS
					String[] resolutions = service.getResolutions(casApp[0], view[0]);
					for (String res : resolutions) {
						// GET METRICS
						String[][] metrics = service.getMetrics(casApp[0], view[0], res);
						for (int k = 0; k < metrics.length; k++) {
							String[] met = metrics[k];
							System.out.println(casApp[1] + ":" + casApp[0] + ":" + view[1] + ":" + view[0] + ":" + resoHM.get(res) + ":" + res + ":" + met[0] + ":" + met[1]);
							writer.println(casApp[1] + ":" + casApp[0] + ":" + view[1] + ":" + view[0] + ":" + resoHM.get(res) + ":" + res + ":" + met[0] + ":" + met[1]);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
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
		  viewId= "ClientView"; //ads 
		  resolution = "r";
		  dataSourceId = "localhost";
		  dimensionIds = new String [] { "appl"};
		  metricIds = new String []{"trans", "netDelay", "abortspc"};  
		  //dimensionIds = new String [] {  "bgAppl","trans"};
		  dimFilters = new String [][]{{"appl","easyTravel*",""}};  
		  metricFilters = null;
		  sort = new String[][]{{"appl","DESC"}};
		  top =10;
		  resolution = "d";
		  timePeriod = "1D";
		  numberOfPeriods = null;
		  timeEnd =  null; 
		  timeBegin = null;
		  timeout = 60000L    ;
		 
		}

	private static HashMap<String, String> initResolutions()
	{
		HashMap<String, String> resos = new HashMap<String, String>();
		resos.put("r", "one period");
		resos.put("1", "one hour");
		resos.put("6", "6 hours");
		resos.put("d", "one day");
		resos.put("w", "one week");
		resos.put("m", "one month");
		return resos;
	}
	
}

