/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tdc.flightsfx.flight;

import com.tdc.flightsfx.connect.Dbconnection;
import flightsfx.FXMLDocumentController;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Tom Cumper
 */
//
public class UpdateFlightTable {

    private final Dbconnection connection = new Dbconnection("jdbc:mysql://192.168.0.14:3306/Flights", "SvcAccount", "FastC4r");
    private Connection con;

    private CallableStatement stmt = null;
    private ResultSet rs = null;

    private final ObservableList<FlightInfo> flightList = FXCollections.observableArrayList();

    /* Gets data from SQL database and populates into ObservableList */
    public void getTableData(FXMLDocumentController controller) throws SQLException, ParseException, IOException {

        con = connection.connect();

        stmt = con.prepareCall("{CALL `Flights`.`sp_Select_All_Flights`}");
        stmt.execute();
        rs = stmt.getResultSet();
        controller.updateDebugLabel("Updating table");
        while (rs.next()) {

            /*Populate Observable List*/
            flightList.add(new FlightInfo(
                    rs.getString("flight_id"),
                    rs.getString("destination"),
                    rs.getString("airline_Name"),
                    rs.getBinaryStream("logo"),
                    rs.getString("flight_time"),
                    rs.getDate("flight_date_time"),
                    rs.getInt("gate"),
                    controller
            ));
        }

        if (con != null) 
            con.close();
        if (stmt != null) 
            stmt.close();
        if (rs != null) 
            rs.close();
        
        Date date = new Date();
        controller.updateDebugLabel("Table updated: "+date);
        controller.populateTable(flightList);
    }

}
