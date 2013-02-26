package com.webservice.handlers;

import com.webservice.main.RAM;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;

public class AccessPointsHandler implements ActionListener {

    JTextArea txtArea;
    RAM ram;
    String device;
    
    public AccessPointsHandler(JTextArea txtArea, RAM ram, String device) {
        this.txtArea = txtArea;
        this.ram = ram;
        this.device = device;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] info;
        txtArea.setText("");
        for (String ap: ram.getAccessPoints()) {
            info = ap.split(",");
            if (info != null) {
                if (info[0].equals(device)) {
                    txtArea.append("ESSID : " + info[2] + " MAC : " + info[1] + "\n");
                    txtArea.append("Channel : " + info[5] + " AP Mode : " + info[3] + "\n");
                    txtArea.append("Signal level : " + info[4] + "\n");
                    txtArea.append("-----------------------\n");
                }
            }
        }
    }
}
