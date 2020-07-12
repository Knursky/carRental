package com.company.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    static final String DB_URL = "jdbc:postgresql://kandula.db.elephantsql.com/qvmxfwsr";

    static final String USER = "qvmxfwsr";
    static final String PASS = "NPTV7f5ZFmtvbuHfEpCvIedGwgPAf0SQ";

    public static void connect() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        Connection conn = DriverManager.getConnection(DB_URL, props);
        System.out.println("connected");


    }
}
