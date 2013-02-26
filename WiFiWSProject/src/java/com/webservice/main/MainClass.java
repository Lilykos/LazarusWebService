package com.webservice.main;

import javax.xml.ws.Endpoint;

public class MainClass {

    public static void main(String[] args) {
        DBHandler dbHandler = new DBHandler();
        GUI gui = new GUI(dbHandler);
        Endpoint.publish("http://0.0.0.0:8090/WifiWSProject/WebServiceHandler", new WebServiceHandler(dbHandler));
        
        Thread guiThread = new Thread(gui);
        guiThread.start();
    }
}
