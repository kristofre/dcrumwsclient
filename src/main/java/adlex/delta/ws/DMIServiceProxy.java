package adlex.delta.ws;

public class DMIServiceProxy implements adlex.delta.ws.DMIService {
  private String _endpoint = null;
  private adlex.delta.ws.DMIService dMIService = null;
  
  public DMIServiceProxy() {
    _initDMIServiceProxy();
  }
  
  public DMIServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initDMIServiceProxy();
  }
  
  private void _initDMIServiceProxy() {
    try {
      dMIService = (new adlex.delta.ws.DMIServiceServiceLocator()).getDMIService();
      if (dMIService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)dMIService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)dMIService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (dMIService != null)
      ((javax.xml.rpc.Stub)dMIService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public adlex.delta.ws.DMIService getDMIService() {
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService;
  }
  
  public boolean setProperty(java.lang.String propertyName, java.lang.String propertyValue) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.setProperty(propertyName, propertyValue);
  }
  
  public java.lang.String getProperty(java.lang.String propertyName, java.lang.String defValue) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getProperty(propertyName, defValue);
  }
  
  public java.lang.String[][] getProperties(java.lang.String appId, java.lang.String viewId, java.lang.String resolution, java.lang.String idFilter, java.lang.String propFilter) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getProperties(appId, viewId, resolution, idFilter, propFilter);
  }
  
  public java.lang.String getServerUUID() throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getServerUUID();
  }
  
  public java.lang.String[][] getMetrics(java.lang.String appId, java.lang.String viewId, java.lang.String resolution) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getMetrics(appId, viewId, resolution);
  }
  
  public java.lang.String[][] getDimensions(java.lang.String appId, java.lang.String viewId, java.lang.String resolution) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getDimensions(appId, viewId, resolution);
  }
  
  public java.lang.String[] getI18N(java.lang.String lang, java.lang.String[] totranslate) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getI18N(lang, totranslate);
  }
  
  public void registerPropertyProvider(java.lang.Object ifc) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    dMIService.registerPropertyProvider(ifc);
  }
  
  public java.lang.String[] getDimensionValues(java.lang.String appId, java.lang.String viewId, java.lang.String dimId, java.lang.String dataSourceId, java.lang.Integer quant, java.lang.String filter) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getDimensionValues(appId, viewId, dimId, dataSourceId, quant, filter);
  }
  
  public long getLastSampleTime(java.lang.String appId, java.lang.String viewId, java.lang.String resolution) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getLastSampleTime(appId, viewId, resolution);
  }
  
  public long getCurrentTime(java.lang.String appId, java.lang.String viewId, java.lang.String resolution) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getCurrentTime(appId, viewId, resolution);
  }
  
  public java.lang.String saveReportDefinition(java.lang.String appId, java.lang.String viewId, java.lang.String dataSourceId, java.lang.String[] dimensionIds, java.lang.String[] metricIds, java.lang.String[][] dimFilters, java.lang.String[][] metricFilters, java.lang.String[][] sort, java.lang.Integer top, java.lang.String resolution, java.lang.String timePeriod, java.lang.Integer numberOfPeriods, java.lang.Long timeBegin, java.lang.Long timeEnd) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.saveReportDefinition(appId, viewId, dataSourceId, dimensionIds, metricIds, dimFilters, metricFilters, sort, top, resolution, timePeriod, numberOfPeriods, timeBegin, timeEnd);
  }
  
  public void setSaveReportLimit(int number) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    dMIService.setSaveReportLimit(number);
  }
  
  public int getSaveReportLimit() throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getSaveReportLimit();
  }
  
  public int getSaveReportNumber() throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getSaveReportNumber();
  }
  
  public java.lang.String[][] getApplications() throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getApplications();
  }
  
  public java.lang.String[][] getDataViews(java.lang.String appId) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getDataViews(appId);
  }
  
  public adlex.delta.ws.DMIData getDMIData(java.lang.String appId, java.lang.String viewId, java.lang.String dataSourceId, java.lang.String[] dimensionIds, java.lang.String[] metricIds, java.lang.String[][] dimFilters, java.lang.String[][] metricFilters, java.lang.String[][] sort, java.lang.Integer top, java.lang.String resolution, java.lang.String timePeriod, java.lang.Integer numberOfPeriods, java.lang.Long timeBegin, java.lang.Long timeEnd, java.lang.Long timeout) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getDMIData(appId, viewId, dataSourceId, dimensionIds, metricIds, dimFilters, metricFilters, sort, top, resolution, timePeriod, numberOfPeriods, timeBegin, timeEnd, timeout);
  }
  
  public adlex.delta.ws.DMIData3 getDMIData3(java.lang.String appId, java.lang.String viewId, java.lang.String dataSourceId, java.lang.String[] dimensionIds, java.lang.String[] metricIds, java.lang.String[][] dimFilters, java.lang.String[][] metricFilters, java.lang.String[][] sort, java.lang.Integer top, java.lang.String resolution, java.lang.String timePeriod, java.lang.Integer numberOfPeriods, java.lang.Long timeBegin, java.lang.Long timeEnd, java.lang.Long timeout) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getDMIData3(appId, viewId, dataSourceId, dimensionIds, metricIds, dimFilters, metricFilters, sort, top, resolution, timePeriod, numberOfPeriods, timeBegin, timeEnd, timeout);
  }
  
  public adlex.delta.ws.DMIData2 getDMIData2(java.lang.String appId, java.lang.String viewId, java.lang.String dataSourceId, java.lang.String[] dimensionIds, java.lang.String[] metricIds, java.lang.String[][] dimFilters, java.lang.String[][] metricFilters, java.lang.String[][] sort, java.lang.Integer top, java.lang.String resolution, java.lang.String timePeriod, java.lang.Integer numberOfPeriods, java.lang.Long timeBegin, java.lang.Long timeEnd, java.lang.Long timeout) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getDMIData2(appId, viewId, dataSourceId, dimensionIds, metricIds, dimFilters, metricFilters, sort, top, resolution, timePeriod, numberOfPeriods, timeBegin, timeEnd, timeout);
  }
  
  public java.lang.String[] getResolutions(java.lang.String appId, java.lang.String viewId) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getResolutions(appId, viewId);
  }
  
  public java.lang.String[][] getDataSources(java.lang.String appId, java.lang.String viewId) throws java.rmi.RemoteException{
    if (dMIService == null)
      _initDMIServiceProxy();
    return dMIService.getDataSources(appId, viewId);
  }
  
  
}