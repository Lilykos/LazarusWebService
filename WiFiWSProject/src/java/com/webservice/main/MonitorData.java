package com.webservice.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonitorData")

public class MonitorData {
    
    private boolean interfaceChanged;
    private boolean apChanged;
    private double timeChanged;
    private boolean isWireless;
    private boolean isDeleted;
    
    private String[] myAP;
    
    private String name = null;
    private String mac = null;
    private String ip = null;
    private String mask = null;
    private String networkAddress = null;
    private String bcastAddr = null;
    private String defaultGateway = null;
    private String mtu = null;
    private String packetErrorRate = null;
    private String broadcastRate = null;
    private String consumedGauge = null;
    
    private String baseStationMAC = null;
    private String baseStationESSID = null;
    private String channel = null;
    private String accessPointSituation = null;
    private String signalLevel = null;
    private String linkQuality = null;
    private String txPower = null;
    private String noiseLevel = null;
    private String missedBeacon = null;
    
    
    
    public boolean isWireless() {
        return isWireless; }
    
    public boolean getIsDeleted() {
        return isDeleted; }

    public String[] getMyAP() {
        return myAP; }
    
    public boolean getAPChanged() {
        return apChanged; }

    public boolean getInterfaceChanged() {
        return interfaceChanged; }
    
    public double getTimeChanged() {
        return timeChanged; }
    
    public String getName() {
        return name; }
    
    public String getMAC() {
        return mac; }
    
    public String getIP() {
        return ip; }
    
    public String getMask() {
        return mask; }
    
    public String getNetworkAddress() {
        return networkAddress; }
    
    public String getBcastAddr() {
        return bcastAddr; }
    
    public String getDefaultGateway() {
        return defaultGateway; }
    
    public String getMTU() {
        return mtu; }
    
    public String getPacketErrorRate() {
        return packetErrorRate; }
    
    public String getBroadcastRate() {
        return broadcastRate; }
    
    public String getConsumedGauge() {
        return consumedGauge;}
    
    public String getBaseStationMAC() {
        return baseStationMAC; }
    
    public String getBaseStationESSID() {
        return baseStationESSID; }
    
    public String getChannel() {
        return channel; }
    
    public String getAccessPointMode() {
        return accessPointSituation; }
    
    public String getSignalLevel() {
        return signalLevel; }
    
    public String getLinkQuality() {
        return linkQuality; }
    
    public String getTxPower() {
        return txPower; }
    
    public String getNoiseLevel() {
        return noiseLevel; }
    
    public String getMissedBeacon() {
        return missedBeacon; }  
}