/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tdc.flightsfx.flight;

/**
 *
 * @author TDC
 */
public class statusColor {

    private String item = null;
    private String style = null;
    
    private final String departed = "Departed";
    private final String gateClosed = "Closed";
    private final String gateClosing = "Closing";
    private final String boarding = "Boarding at gate";

    public statusColor(String item) {

        this.item = item;

    }

    public String getCellColor() {

        //return null if no row item selected
        if (item == null)
            return null;
        
        if (item.endsWith(gateClosed)) {
            style = "-fx-background-color: rgb(196,0,0);" + "-fx-border-width: 0 0 1 0;" + "-fx-border-color: rgb(0,0,0);";
        } else if (item.endsWith(gateClosing)) {
            style = "-fx-background-color: rgb(145,0,245);" + "-fx-border-width: 0 0 1 0;" + "-fx-border-color: rgb(0,0,0);";
        } else if (item.startsWith(boarding)) {
            style = "-fx-background-color: Blue;" + "-fx-border-width: 0 0 1 0;" + "-fx-border-color: rgb(0,0,0);";
        } else if (item.equals(departed)){
             style = "-fx-background-color: Green;" + "-fx-border-width: 0 0 1 0;" + "-fx-border-color: rgb(0,0,0);";
        }

        return style;
    }

}
