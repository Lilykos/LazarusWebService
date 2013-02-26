package com.webservice.main;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "WebServiceHandler")
public class WebServiceHandler {
    
    private DBHandler dbHandler;
    
    public WebServiceHandler(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @WebMethod(operationName = "setMonitorData")
    public void setMonitorData(@WebParam(name = "device") String device, @WebParam(name = "md") MonitorData md) {
        dbHandler.updateDevices(device, md);
        
        if(md.getIsDeleted()) {
            dbHandler.deleteInterface(device, md);
        }
        else {
            if(md.getAPChanged()) {
                dbHandler.updateAccessPoints(device, md);
            }
            if (md.getInterfaceChanged()) {
                if (md.isWireless()) {
                    dbHandler.updateInterfaceWireless(device, md);
                }
                else {
                    dbHandler.updateInterface(device, md);
                }
            }
        }
    }    
    
    @WebMethod(operationName = "setTerminalData")
    public void setTerminalData(@WebParam(name = "device") String device, @WebParam(name = "data") TerminalData data) {
        System.out.println("Android data sent!");
        
        double currentTime = System.currentTimeMillis();
        double curTime = (double) (currentTime/1000);
        
        dbHandler.updateMobiles(device, curTime);
        dbHandler.updateAndroidInfo(device, data);
    }
}
