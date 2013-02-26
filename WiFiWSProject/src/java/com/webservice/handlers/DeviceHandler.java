package com.webservice.handlers;

import com.webservice.main.RAM;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class DeviceHandler implements ActionListener {
    
    JButton wiredButton;
    JButton wirelessButton;
    JButton apButton;
    JTextArea txtArea;
    JScrollPane scroller;
    String device;
    RAM ram;
    
    public DeviceHandler(String device, RAM ram) {
        this.device = device;
        this.ram = ram;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new JFrame(device + " Interfaces");
        frame.setLayout(new BorderLayout());

        wiredButton = new JButton("Wired Interfaces");
        wirelessButton = new JButton("Wireless Interfaces");
        apButton = new JButton("Access Points");
        
        txtArea =  new JTextArea(20, 40);
        Font font = new Font("Arial", Font.PLAIN, 14);
        txtArea.setFont(font);
        txtArea.setForeground(Color.WHITE);
        txtArea.setBackground(Color.GRAY);
        txtArea.setLineWrap(false);
        txtArea.setEditable(false);
        
        wiredButton.addActionListener(new WiredInterfaceHandler(txtArea, ram, device));
        wirelessButton.addActionListener(new WirelessInterfaceHandler(txtArea, ram, device));
        apButton.addActionListener(new AccessPointsHandler(txtArea, ram, device));
        
        scroller = new JScrollPane(txtArea);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        JPanel panelButtons = new JPanel();
        panelButtons.add(wiredButton);
        panelButtons.add(wirelessButton);
        panelButtons.add(apButton);
        
        JPanel panelText = new JPanel();
        panelText.add(scroller);
        
        frame.getContentPane().add(BorderLayout.NORTH, panelButtons);
        frame.getContentPane().add(BorderLayout.CENTER, panelText);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocation(1050, 330);
        frame.setVisible(true);
    }
    
}
