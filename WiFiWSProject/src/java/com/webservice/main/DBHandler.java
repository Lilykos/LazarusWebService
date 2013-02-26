package com.webservice.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public final class DBHandler {
    
    final Object androidLock = new Object();
    
    private Connection connection;
    private Statement statement;
    private String connectionUrl;
    private String device;
    
    private String username;
    private String password;
    private String url;
    private String timeString;
    private int time;
    
    public DBHandler() {
        getProperties();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connectionUrl = url + username + "?" + "user=" + username + "&password=" + password;
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = null;
            System.out.println("SQL connection enabled!");
            createTables(device);
        } catch (SQLException e) {
            System.out.println("SQL Exception: "+ e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: "+ cE.toString());
        }
    }

    
    
    //**********************************//
    // CREATE TABLES IF THEY DONT EXIST //
    //**********************************//
    
    public synchronized void createTables(String device) {
        this.device = device;
        try {
            statement = connection.createStatement();
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS devices("
                        +"device VARCHAR(40), "
                        +"time DOUBLE)";
            statement.executeUpdate(sqlCreateTable);
            
            sqlCreateTable = "CREATE TABLE IF NOT EXISTS wired_interfaces("
                        +"device VARCHAR(20), "
                        +"name VARCHAR(45), "
                        +"mac VARCHAR(40), "
                        +"ip VARCHAR(20), "
                        +"mask VARCHAR(40), "
                        +"networkAddress VARCHAR(40), "
                        +"bcastAddr VARCHAR(40), "
                        +"defaultGateway VARCHAR(40), "
                        +"mtu VARCHAR(40), "        
                        +"packetErrorRate VARCHAR(40), "
                        +"broadcastRate VARCHAR(40), "
                        +"consumedGauge VARCHAR(40))";
            statement.executeUpdate(sqlCreateTable);
            
            sqlCreateTable = "CREATE TABLE IF NOT EXISTS wireless_interfaces("
                        +"device VARCHAR(20), "
                        +"name VARCHAR(45), "
                        +"mac VARCHAR(40), "
                        +"ip VARCHAR(20), "
                        +"mask VARCHAR(40), "
                        +"networkAddress VARCHAR(40), "
                        +"bcastAddr VARCHAR(40), "
                        +"defaultGateway VARCHAR(40), "
                        +"mtu VARCHAR(40), "        
                        +"packetErrorRate VARCHAR(40), "
                        +"broadcastRate VARCHAR(40), "
                        +"consumedGauge VARCHAR(45), "
                        +"baseStationMAC VARCHAR(40), "
                        +"baseStationESSID VARCHAR(40), "
                        +"channel VARCHAR(40), "
                        +"accessPointSituation VARCHAR(40), "
                        +"signalLevel VARCHAR(40), "
                        +"linkQuality VARCHAR(40), "
                        +"txPower VARCHAR(40), "        
                        +"noiseLevel VARCHAR(40), "
                        +"missedBeacon VARCHAR(40))";
            statement.executeUpdate(sqlCreateTable);
            
            sqlCreateTable = "CREATE TABLE IF NOT EXISTS access_points("
                        +"device VARCHAR(20), "
                        +"mac VARCHAR(45), "
                        +"essid VARCHAR(40), "
                        +"mode VARCHAR(20), "
                        +"signalLevel VARCHAR(40), "
                        +"channel VARCHAR(40))";
            statement.executeUpdate(sqlCreateTable);
            
            sqlCreateTable = "CREATE TABLE IF NOT EXISTS mobiles("
                        +"imei VARCHAR(20), "
                        +"time DOUBLE)";
            statement.executeUpdate(sqlCreateTable);
            
            sqlCreateTable = "CREATE TABLE IF NOT EXISTS mobile_devices("
                        +"imei VARCHAR(15), "
                        +"longitude VARCHAR(20), "
                        +"latitude VARCHAR(20), "
                        +"batteryLevel VARCHAR(5), "
                        +"batteryStatus VARCHAR(20), "
                        +"model VARCHAR(20), "
                        +"sdk VARCHAR(5), "
                        +"manufacturer VARCHAR(20))";
            statement.executeUpdate(sqlCreateTable);
            System.out.println("Tables created!");
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    
    
    //****************************************//
    // UPDATE DEVICES, WIRELESS, WIRED AND AP //
    // ACCORDING TO MONITORDATA OBJECT INFO   //
    //****************************************//
    
    public synchronized void updateDevices(String device, MonitorData md) {
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "DELETE FROM devices WHERE device =  " + "\'" + device + "\';";
            statement.executeUpdate(sqlUpdateTable);
            
            statement = connection.createStatement();
            sqlUpdateTable = "INSERT INTO devices(device, time) "
                    + "VALUES(\'"
                    + device +"\', \'"
                    + md.getTimeChanged() + "\');";
            statement.executeUpdate(sqlUpdateTable);
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
    
    public synchronized void updateInterface(String device, MonitorData md) {  
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "DELETE FROM wired_interfaces WHERE mac =  " + "\'" + md.getMAC() + "\';";
            statement.executeUpdate(sqlUpdateTable);
            
            sqlUpdateTable = "INSERT INTO wired_interfaces(device, name, mac, ip, mask, networkAddress, "
                    + "bcastAddr, defaultGateway, mtu, packetErrorRate, broadcastRate, consumedGauge) "
                    + "VALUES(\'"
                    + device +"\', \'"
                    + md.getName() +"\', \'"
                    + md.getMAC() +"\', \'"
                    + md.getIP() +"\', \'"
                    + md.getMask() +"\', \'"
                    + md.getNetworkAddress() +"\', \'"
                    + md.getBcastAddr() +"\', \'"
                    + md.getDefaultGateway() +"\', \'"
                    + md.getMTU() +"\', \'"
                    + md.getPacketErrorRate() +"\', \'"
                    + md.getBroadcastRate() +"\', \'"
                    + md.getConsumedGauge() + "\');";
            statement.executeUpdate(sqlUpdateTable);
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    public synchronized void updateInterfaceWireless(String device, MonitorData md) {
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "DELETE FROM wireless_interfaces WHERE mac =  " + "\'" + md.getMAC() + "\';";
            statement.executeUpdate(sqlUpdateTable);
            
            sqlUpdateTable = "INSERT INTO wireless_interfaces(device, name, mac, ip, mask, networkAddress, "
                    + "bcastAddr, defaultGateway, mtu, packetErrorRate, broadcastRate, consumedGauge, "
                    + "baseStationMAC, baseStationESSID, channel, accessPointSituation, signalLevel, "
                    + "linkQuality, txPower, noiseLevel, missedBeacon) "
                    + "VALUES(\'"
                    + device +"\', \'"
                    + md.getName() +"\', \'"
                    + md.getMAC() +"\', \'"
                    + md.getIP() +"\', \'"
                    + md.getMask() +"\', \'"
                    + md.getNetworkAddress() +"\', \'"
                    + md.getBcastAddr() +"\', \'"
                    + md.getDefaultGateway() +"\', \'"
                    + md.getMTU() +"\', \'"
                    + md.getPacketErrorRate() +"\', \'"
                    + md.getBroadcastRate() +"\', \'"
                    + md.getConsumedGauge() +"\', \'"
                    + md.getBaseStationMAC() +"\', \'"
                    + md.getBaseStationESSID() +"\', \'"
                    + md.getChannel() +"\', \'"
                    + md.getAccessPointMode() +"\', \'"
                    + md.getSignalLevel() +"\', \'"
                    + md.getLinkQuality() +"\', \'"
                    + md.getTxPower() +"\', \'"
                    + md.getNoiseLevel() +"\', \'"
                    + md.getMissedBeacon() + "\');";
            statement.executeUpdate(sqlUpdateTable);
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    public synchronized void updateAccessPoints(String device, MonitorData md) {
        String sqlUpdateTable;
        String[] info;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "DELETE FROM access_points WHERE device =  " + "\'" + device + "\';";
            statement.executeUpdate(sqlUpdateTable);
            
            for (String ap: md.getMyAP()) {
                info = ap.split(","); 
                sqlUpdateTable = "INSERT INTO access_points(device, mac, essid, mode, signalLevel, channel) "
                        + "VALUES(\'"
                        + device +"\', \'"
                        + info[0] +"\', \'"
                        + info[1] +"\', \'"
                        + info[2] +"\', \'"
                        + info[3] +"\', \'"
                        + info[4] + "\');";
                statement.executeUpdate(sqlUpdateTable);
            }
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    } 

    
    
    //****************************************//
    // DELETE DEVICES, WIRELESS, WIRED AND AP //
    // ACCORDING TO MONITORDATA OBJECT INFO   //
    //****************************************//
    
    public synchronized void deleteInterface(String device, MonitorData md) {  
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "DELETE FROM devices WHERE device =  " + "\'" + device + "\';";
            statement.executeUpdate(sqlUpdateTable);
            
            if (md.isWireless()) {
                sqlUpdateTable = "DELETE FROM wireless_interfaces WHERE mac =  " + "\'" + md.getMAC() + "\';";
            }
            else {
                sqlUpdateTable = "DELETE FROM wired_interfaces WHERE mac =  " + "\'" + md.getMAC() + "\';";
            }
            statement.executeUpdate(sqlUpdateTable);
            
            sqlUpdateTable = "INSERT INTO devices(device, time) "
                    + "VALUES(\'"
                    + device +"\', \'"
                    + md.getTimeChanged() + "\');";
            statement.executeUpdate(sqlUpdateTable);
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }  
    }
    
    public synchronized void deleteDevicesAndInfo(String device) {  
        String sqlUpdateTable;  
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "DELETE FROM devices WHERE device =  " + "\'" + device + "\';";
            statement.executeUpdate(sqlUpdateTable);
            
            sqlUpdateTable = "DELETE FROM access_points WHERE device =  " + "\'" + device + "\';";
            statement.executeUpdate(sqlUpdateTable);
            
            sqlUpdateTable = "DELETE FROM wireless_interfaces WHERE device =  " + "\'" + device + "\';";
            statement.executeUpdate(sqlUpdateTable);
            
            sqlUpdateTable = "DELETE FROM wired_interfaces WHERE device =  " + "\'" + device + "\';";
            statement.executeUpdate(sqlUpdateTable);
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }  
    }
    
    
    
    //******************************************//
    // FIND THE COUNT OF DEVICES TO DELETE THEM //
    //******************************************//
    
    public synchronized ArrayList<String> getDeviceCount() {
        ArrayList<String> deviceCount = new ArrayList<String>();
        ResultSet result = null;
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "SELECT * FROM devices;";
            result = statement.executeQuery(sqlUpdateTable);
            while (result.next()) {
                deviceCount.add(result.getString("device"));
            }
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }  
        return deviceCount;
    }
    
    public synchronized ArrayList<String> devicesToBeDeleted() {
        ArrayList<String> devicesToBeDeleted = new ArrayList<String>();
        ResultSet result = null;
        String sqlUpdateTable;
        double curtime = System.currentTimeMillis()/1000;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "SELECT * FROM devices;";
            result = statement.executeQuery(sqlUpdateTable);
            while (result.next()) {
                if (curtime - result.getDouble("time") > time/1000) {
                    devicesToBeDeleted.add(result.getString("device"));
                }
            }
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }  
        return devicesToBeDeleted;
    }
    
    
    
    //****************************************//
    // GET ALL RELEVANT DEVICE INFO           //
    //****************************************//
    
    public synchronized ArrayList<String> getAccessPoints() {
        ArrayList<String> accessPoints = new ArrayList<String>();
        ResultSet result = null;
        String apResult;
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "SELECT * FROM access_points;";
            result = statement.executeQuery(sqlUpdateTable);
            while (result.next()) {
                apResult = result.getString("device") + ","
                        + result.getString("mac") + ","
                        + result.getString("essid") + ","
                        + result.getString("mode") + ","
                        + result.getString("signalLevel") + ","
                        + result.getString("channel");
                accessPoints.add(apResult);
            }
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return accessPoints;
    }
    
    public synchronized ArrayList<String> getWiredInterfaces() {
        ArrayList<String> wiredInterfaces = new ArrayList<String>();
        ResultSet result = null;
        String wiredResult;
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "SELECT * FROM wired_interfaces;";
            result = statement.executeQuery(sqlUpdateTable);
            while (result.next()) {
                wiredResult = result.getString("device") + ","
                        + result.getString("name") + ","
                        + result.getString("mac") + ","
                        + result.getString("ip") + ","
                        + result.getString("mask") + ","
                        + result.getString("networkAddress") + ","
                        + result.getString("bcastAddr") + ","
                        + result.getString("defaultGateway") + ","
                        + result.getString("mtu") + ","
                        + result.getString("packetErrorRate") + ","
                        + result.getString("broadcastRate") + ","
                        + result.getString("consumedGauge");
                wiredInterfaces.add(wiredResult);
            }
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return wiredInterfaces;
    }
    
    public synchronized ArrayList<String> getWirelessInterfaces() {
        ArrayList<String> wirelessInterfaces = new ArrayList<String>();
        ResultSet result = null;
        String wirelessResult;
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "SELECT * FROM wireless_interfaces;";
            result = statement.executeQuery(sqlUpdateTable);
            while (result.next()) {
                wirelessResult = result.getString("device") + ","
                        + result.getString("name") + ","
                        + result.getString("mac") + ","
                        + result.getString("ip") + ","
                        + result.getString("mask") + ","
                        + result.getString("networkAddress") + ","
                        + result.getString("bcastAddr") + ","
                        + result.getString("defaultGateway") + ","
                        + result.getString("mtu") + ","
                        + result.getString("packetErrorRate") + ","
                        + result.getString("broadcastRate") + ","
                        + result.getString("consumedGauge") + ","
                        + result.getString("baseStationMAC") + ","
                        + result.getString("baseStationESSID") + ","
                        + result.getString("channel") + ","
                        + result.getString("accessPointSituation") + ","
                        + result.getString("signalLevel") + ","
                        + result.getString("linkQuality") + ","
                        + result.getString("txPower") + ","
                        + result.getString("noiseLevel") + ","
                        + result.getString("missedBeacon");
                wirelessInterfaces.add(wirelessResult);
            }
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return wirelessInterfaces;
    }
    
    
    
        
    //****************************************//
    // ALL RELEVANT ANDROID INFO              //
    //****************************************//
    
    public synchronized ArrayList<String> mobilesToBeDeleted() {
        ArrayList<String> mobilesToBeDeleted = new ArrayList<String>();
        ResultSet result = null;
        String sqlUpdateTable;
        double curtime = System.currentTimeMillis()/1000;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "SELECT * FROM mobiles;";
            result = statement.executeQuery(sqlUpdateTable);
            while (result.next()) {
                if (curtime - result.getDouble("time") > time/1000) {
                    mobilesToBeDeleted.add(result.getString("imei"));
                }
            }
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }  
        return mobilesToBeDeleted;
    }
    
    public synchronized void deleteMobilesAndInfo(String device) {  
        String sqlUpdateTable;  
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            
            sqlUpdateTable = "DELETE FROM mobiles WHERE imei =  " + "\'" + device + "\';";
            statement.executeUpdate(sqlUpdateTable);
            
            sqlUpdateTable = "DELETE FROM mobile_devices WHERE imei =  " + "\'" + device + "\';";
            statement.executeUpdate(sqlUpdateTable);
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }  
    }

    public synchronized ArrayList<String> getMobileCount() {
        ArrayList<String> mobileCount = new ArrayList<String>();
        ResultSet result = null;
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "SELECT * FROM mobile_devices;";
            result = statement.executeQuery(sqlUpdateTable);
            while (result.next()) {
                mobileCount.add(result.getString("imei"));
            }
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return mobileCount;
    }
    
    public synchronized void updateMobiles(String device, double time) {
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "DELETE FROM mobiles WHERE imei =  " + "\'" + device + "\';";
            statement.executeUpdate(sqlUpdateTable);
            
            statement = connection.createStatement();
            sqlUpdateTable = "INSERT INTO mobiles(imei, time) "
                    + "VALUES(\'"
                    + device +"\', \'"
                    + time + "\');";
            statement.executeUpdate(sqlUpdateTable);
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
    
    public synchronized void updateAndroidInfo(String device, TerminalData data) {
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "DELETE FROM mobile_devices WHERE imei =  " + "\'" + device + "\';";
            statement.executeUpdate(sqlUpdateTable);
            
            sqlUpdateTable = "INSERT INTO mobile_devices(imei, longitude, latitude, batteryLevel, batteryStatus, model, sdk, manufacturer) "
                    + "VALUES(\'"
                    + device +"\', \'"
                    + data.getLongitude() +"\', \'"
                    + data.getLatitude() +"\', \'"
                    + data.getBatteryLevel() +"\', \'"
                    + data.getBatteryStatus() +"\', \'"
                    + data.getModel() +"\', \'"
                    + data.getSdk() +"\', \'"
                    + data.getManufacturer() + "\');";
            statement.executeUpdate(sqlUpdateTable);
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
    
    public synchronized ArrayList<String> getAndroidInfo() {
        ArrayList<String> androidDevices = new ArrayList<String>();
        ResultSet result = null;
        String androidResult;
        String sqlUpdateTable;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlUpdateTable = "SELECT * FROM mobile_devices;";
            result = statement.executeQuery(sqlUpdateTable);
            while (result.next()) {
                androidResult = result.getString("imei") + ","
                        + result.getString("longitude") + ","
                        + result.getString("latitude") + ","
                        + result.getString("batteryLevel") + ","
                        + result.getString("batteryStatus") + ","
                        + result.getString("model") + ","
                        + result.getString("sdk") + ","
                        + result.getString("manufacturer");
                androidDevices.add(androidResult);
            }
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return androidDevices;
    }
    
    
    
    //****************************************//
    // DELETE EVERYTHING TO CLOSE THE SERVER  //
    //****************************************//
    
    public void deleteTablesAndDisconnect() {
        String sqlDelete;
        ResultSet result;
        try {
            connection = (Connection) DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            sqlDelete = "TRUNCATE TABLE devices;";
            statement.executeUpdate(sqlDelete);

            sqlDelete = "TRUNCATE TABLE wired_interfaces;";
            statement.executeUpdate(sqlDelete);

            sqlDelete = "TRUNCATE TABLE wireless_interfaces;";
            statement.executeUpdate(sqlDelete);

            sqlDelete = "TRUNCATE TABLE access_points;";
            statement.executeUpdate(sqlDelete);
            
            sqlDelete = "TRUNCATE TABLE mobile_devices;";
            statement.executeUpdate(sqlDelete);
        } catch (SQLException ex) {
            ex.getErrorCode();
        } finally {
            try { statement.close();
                connection.close();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
     
       
    public synchronized void getProperties() {
        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream("WSProperties.properties");
            prop.load(input);
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            url = prop.getProperty("url");
            timeString = prop.getProperty("time");
        } catch (IOException ex) {
            System.out.println("Property File NOT FOUND. Aborting...");
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        time = Integer.parseInt(timeString);
    }
    
    public synchronized int getTime() {
        return this.time;
    }
}