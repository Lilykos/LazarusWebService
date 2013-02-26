package com.webservice.handlers;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class CreditsHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame credits = new JFrame("Credits!");
        JTextArea txtArea = new JTextArea();
        
        Font font = new Font("Verdana", Font.BOLD, 14);
        txtArea.setFont(font);
        txtArea.setForeground(Color.BLACK);
        Color color = new Color(238, 233, 233);
        txtArea.setBackground(color);
        txtArea.setText("                  **\n"
                + "Created by Koutsakis Ilias\n"
                + "and Siamouris Anastasios.\n"
                + "                  **");
        txtArea.setEditable(false);
        
        credits.add(txtArea);
        credits.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        credits.pack();
        credits.setLocation(450, 500);
        credits.setVisible(true);
    }
    
}
