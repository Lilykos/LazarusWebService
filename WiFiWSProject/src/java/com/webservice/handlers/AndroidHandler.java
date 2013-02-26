package com.webservice.handlers;

import com.webservice.main.RAM;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class AndroidHandler implements ActionListener {
    
    JTextArea txtArea;
    JScrollPane scroller;
    String device;
    RAM ram;
    
    public AndroidHandler(String device, RAM ram) {
        this.device = device;
        this.ram  = ram;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new JFrame(device + " Info");
        frame.setLayout(new BorderLayout());
        
        txtArea =  new JTextArea(20, 40);
        Font font = new Font("Arial", Font.PLAIN, 14);
        txtArea.setFont(font);
        txtArea.setForeground(Color.WHITE);
        txtArea.setBackground(Color.GRAY);
        txtArea.setLineWrap(false);
        txtArea.setEditable(false);
        
        getAndroidInfo();
        
        scroller = new JScrollPane(txtArea);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      
        JPanel panelText = new JPanel();
        panelText.add(scroller);
        
        frame.getContentPane().add(BorderLayout.CENTER, panelText);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocation(1050, 150);
        frame.setVisible(true);
    }

    public void getAndroidInfo() {
        String[] info;
        txtArea.setText("");
        for (String str: ram.getAndroidInfo()) {
            info = str.split(",");
            if (info != null) {
                if (info[0].equals(device)) {
                    txtArea.append("GPS Info\n");
                    txtArea.append("-----------------------\n");
                    txtArea.append("Longitude: " + info[1] + "\n");
                    txtArea.append("Latitude: " + info[2] + "\n");
                    txtArea.append("\n");
                    txtArea.append("Battery Info\n");
                    txtArea.append("-----------------------\n");
                    txtArea.append("Battery Level: " + info[3] + "\n");
                    txtArea.append("Battery Status: " + info[4] + "\n");
                    txtArea.append("\n");
                    txtArea.append("GeneralInfo\n");
                    txtArea.append("-----------------------\n");
                    txtArea.append("Model: " + info[5] + "\n");
                    txtArea.append("Sdk " + info[6] + "\n");
                    txtArea.append("Manufacturer: " + info[7] + "\n");
                }
            }
        }
    } 
}
