package com.webservice.handlers;

import com.webservice.main.RAM;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;

public class WiredInterfaceHandler implements ActionListener {

    JTextArea txtArea;
    RAM ram;
    String device;
    
    public WiredInterfaceHandler(JTextArea txtArea, RAM ram, String device) {
        this.txtArea = txtArea;
        this.ram = ram;
        this.device = device;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] info;
        txtArea.setText("");
        for (String ap: ram.getWired()) {
            info = ap.split(",");
            if (info != null) {
                if (info[0].equals(device)) {
                    txtArea.append("Interface Name is " + info[1] + "\n");
                    txtArea.append("\tMAC               : " + info[2] + "\n");
                    txtArea.append("\tIP                : " + info[3] + "\n");
                    txtArea.append("\tBcast Address     : " + info[6] + "\n");
                    txtArea.append("\tDefault Gateway   : " + info[7] + "\n");
                    txtArea.append("\tMask              : " + info[4] + "\n");
                    txtArea.append("\tMTU               : " + info[8] + "\n");
                    txtArea.append("\tNetwork Adr       : " + info[5] + "\n");
                    txtArea.append("\tPacket Error Rate : " + info[9] + "\n");
                    txtArea.append("\tBrcast Rate       : " + info[10] + "\n");
                    txtArea.append("\tConsumed Gauge    : " + info[11] + "\n");
                    txtArea.append("-----------------------\n");
                }
            }
        }
    }
    
}
