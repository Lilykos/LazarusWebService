package com.webservice.main;

import com.webservice.handlers.AndroidHandler;
import com.webservice.handlers.CreditsHandler;
import com.webservice.handlers.DeviceHandler;
import com.webservice.handlers.ExitHandler;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GUI extends JFrame implements Runnable {
    
    DBHandler dbHandler;
    JTabbedPane tabbedPane;
    JPanel mobiles;
    JPanel devices;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem exitButton;
    JMenuItem creditsButton;
    ArrayList<JButton> deviceButtons;
    ArrayList<String> deviceNames;
    ArrayList<String> devicesDel;
    
    ArrayList<JButton> mobileButtons;
    ArrayList<String> mobileNames;
    ArrayList<String> mobileDel;
    JButton button;
    
    RAM ram;
    int repeatTime;
    
    
    public GUI(DBHandler dbHandler) {
        super("Lazarus: The WiFi Monitoring Program");
        deviceButtons = new ArrayList<JButton>();
        mobileButtons = new ArrayList<JButton>();
        this.dbHandler = dbHandler;
        this.repeatTime = dbHandler.getTime();
        ram = new RAM(dbHandler);
        
        tabbedPane = new JTabbedPane();
        devices = new JPanel();
        tabbedPane.addTab("PC/Laptop Devices", devices);
        mobiles = new JPanel();
        tabbedPane.addTab("Mobiles", mobiles);
        add(tabbedPane);
        
        menuBar = new JMenuBar();
        menu = new JMenu("Main Menu");
        exitButton = new JMenuItem("Exit");
        exitButton.addActionListener(new ExitHandler(dbHandler));
        
        creditsButton = new JMenuItem("Credits");
        creditsButton.addActionListener(new CreditsHandler());
        
        menu.add(exitButton);
        menu.add(creditsButton);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(320, 240);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void run() {
        int size;
        deviceNames = new ArrayList<String>();
        devicesDel = new ArrayList<String>();
        
        mobileNames = new ArrayList<String>();
        mobileDel = new ArrayList<String>();
        
        while (Thread.currentThread().isAlive()) {
            ram.updateRAM(dbHandler);
            
            
            //**********************************//
            //        DEVICE GUI REFRESH        //
            //**********************************//
            devicesDel.clear();
            devicesDel = dbHandler.devicesToBeDeleted();
            size = devicesDel.size();
            for (int i = size - 1; i >= 0; i--) {
                for (int j = 0; j < deviceNames.size(); j++) {
                    if (deviceNames.get(j).equals(devicesDel.get(i))) {
                        deviceNames.remove(j);
                        devices.remove(j);
                        deviceButtons.remove(j);
                        dbHandler.deleteDevicesAndInfo(devicesDel.get(i));
                    }
                }
            }
            for (String dev: dbHandler.getDeviceCount()) {
                if (!deviceNames.contains(dev)) {
                    button = new JButton(dev);
                    deviceNames.add(dev);
                    button.addActionListener(new DeviceHandler(dev, ram));
                    deviceButtons.add(button);
                    devices.add(button);
                }
            }
            
            
            
            //**********************************//
            //        ANDROID GUI REFRESH       //
            //**********************************//
            
            mobileDel.clear();
            mobileDel = dbHandler.mobilesToBeDeleted();
            size = mobileDel.size();
            for (int i = size - 1; i >= 0; i--) {
                for (int j = 0; j < mobileNames.size(); j++) {
                    if (mobileNames.get(j).equals(mobileDel.get(i))) {
                        mobileNames.remove(j);
                        mobiles.remove(j);
                        mobileButtons.remove(j);
                        dbHandler.deleteMobilesAndInfo(mobileDel.get(i));
                    }
                }
            }
            for (String dev: dbHandler.getMobileCount()) {
                if (!mobileNames.contains(dev)) {
                    button = new JButton(dev);
                    mobileNames.add(dev);
                    button.addActionListener(new AndroidHandler(dev, ram));
                    mobileButtons.add(button);
                    mobiles.add(button);
                }
            }
            repaint();
            try { Thread.sleep(repeatTime);
            } catch (InterruptedException ex) { ex.printStackTrace(); }
        }
    }
}