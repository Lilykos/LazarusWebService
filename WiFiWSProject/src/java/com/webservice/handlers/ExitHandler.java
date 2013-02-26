package com.webservice.handlers;

import com.webservice.main.DBHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitHandler implements ActionListener {
    
    DBHandler dbHandler;
    
    public ExitHandler(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dbHandler.deleteTablesAndDisconnect();
        System.exit(0);
    } 
}
