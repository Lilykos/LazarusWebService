package com.webservice.main;

import java.util.ArrayList;

public class RAM {
    
    final Object androidLock = new Object();
    
    private ArrayList<String> wired;
    private ArrayList<String> wireless;
    private ArrayList<String> accessPoints;
    private DBHandler dbHandler;
    private ArrayList<String> androidInfo;
    
    final Object lock = new Object();
    
    public RAM(DBHandler dbHandler) {
        wired = new ArrayList<String>();
        wireless = new ArrayList<String>();
        accessPoints = new ArrayList<String>();
        androidInfo = new ArrayList<String>();
        this.dbHandler = dbHandler;
    }
    
    public ArrayList<String> getWired() {
        synchronized(wired) {
            return wired;
        }
    }
    
    public synchronized ArrayList<String> getWireless() {
        return wireless;
    }
    
    public synchronized ArrayList<String> getAccessPoints() {
        return accessPoints;
    }
    
    public synchronized ArrayList<String> getAndroidInfo() {
        synchronized(androidLock) {
            return androidInfo;
        }
    }
    
    public synchronized void updateWired() {
        wired.clear();
        wired = dbHandler.getWiredInterfaces();
    }
    
    public synchronized void updateWireless() {
        wireless.clear();
        wireless = dbHandler.getWirelessInterfaces();
    }
    
    public synchronized void updateAccessPoints() {
        accessPoints.clear();
        accessPoints = dbHandler.getAccessPoints();
    }
    
    public synchronized void updateAndroidInfo() {
        synchronized (androidLock) {
            androidInfo.clear();
            androidInfo = dbHandler.getAndroidInfo();
        }
    }

    public synchronized void updateRAM(DBHandler dbHandler) {
        updateWired();
        updateWireless();
        updateAccessPoints();
        updateAndroidInfo();
        System.out.println("RAM updated!");
    }
}
