package com.tdc.flightsfx.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author TDC
 */
public class DbConnection {

    private final String databaseURL, username, password;
    private Connection con = null;

    public DbConnection(String databaseURL, String username, String password) {

        this.databaseURL = databaseURL;
        this.username = username;
        this.password = password;

    }

    public Connection connect() throws SQLException {

        // Drops connection attempt after 5 seconds if no response from server
        DriverManager.setLoginTimeout(5);
        con = DriverManager.getConnection(databaseURL, username, password);

        return con;
    }

}
