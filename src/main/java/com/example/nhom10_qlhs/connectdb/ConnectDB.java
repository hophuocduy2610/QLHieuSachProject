package com.example.nhom10_qlhs.connectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectDB {
    public static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    public static ConnectDB getInstance() {
        return instance;
    }

    public static Connection connect() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databasename=QLNhaSach";
        String user = "sasql";
        String password = "123456789";
        con = DriverManager.getConnection(url, user, password);
        return con;
    }
}
