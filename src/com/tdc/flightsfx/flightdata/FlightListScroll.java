/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tdc.flightsfx.flightdata;

import com.sun.javafx.scene.control.skin.TableViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.tdc.flightsfx.ui.FXMLDocumentController;
import javafx.scene.control.TableView;

/**
 *
 * @author TDC
 */
public class FlightListScroll {

    private FXMLDocumentController doc;
    private TableView<FlightInfo> view;
    
    private static int currentSelection = 0;

    public FlightListScroll(FXMLDocumentController doc, TableView<FlightInfo> view) {

        this.doc = doc;
        this.view = view;
    }
    
    
    public void scroll(){
        
        int visible = getNumberOfVisibleRows();
        System.out.println(currentSelection+" of "+doc.getRowCount());
        if(currentSelection >= doc.getRowCount())
            currentSelection =0;
        
        view.scrollTo(currentSelection);
        
        currentSelection += (visible+1);
        
    }


    private int getNumberOfVisibleRows() {

        try {
            VirtualFlow<?> vf = loadVirtualFlow();
            return (vf.getLastVisibleCell().getIndex()) - vf.getFirstVisibleCell().getIndex();
        } catch (Exception ex) {
            return 0;
        }
        
    }

    private VirtualFlow<?> loadVirtualFlow() {

        return (VirtualFlow<?>) ((TableViewSkin<FlightInfo>) view.getSkin()).getChildren().get(1);
    }

}
