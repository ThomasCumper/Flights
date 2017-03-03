package com.tdc.flightsfx.flightdata;

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
            style = "-fx-background-color: rgb(119, 2, 2);" + "-fx-border-width: 0 0 1 0;" + "-fx-border-color: rgb(0,0,0);";
        } else if (item.endsWith(gateClosing)) {
            style = "-fx-background-color: rgb(81, 2, 119);" + "-fx-border-width: 0 0 1 0;" + "-fx-border-color: rgb(0,0,0);";
        } else if (item.startsWith(boarding)) {
            style = "-fx-background-color: rgb(2, 41, 119);" + "-fx-border-width: 0 0 1 0;" + "-fx-border-color: rgb(0,0,0);";
        } else if (item.equals(departed)){
             style = "-fx-background-color: rgb(2, 119, 4);" + "-fx-border-width: 0 0 1 0;" + "-fx-border-color: rgb(0,0,0);";
        }

        return style;
    }

}
