package com.example.nhom10_qlhs.dao;

import com.example.nhom10_qlhs.connectdb.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TacGiaDAO {
    public Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public String getMaTGTheoTenTG(String tenTG) {
        String maTG = "";
        String sql = "SELECT maTG FROM TacGia WHERE tenTG = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenTG);
            result = prepare.executeQuery();
            while (result.next()){
                maTG = result.getString("maTG");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return maTG;
    }

    public String getTenTGTheoMaTG(String maTG) {
        String tenTG = "";
        String sql = "SELECT tenTG FROM TacGia WHERE maTG = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maTG);
            result = prepare.executeQuery();
            while (result.next()){
                maTG = result.getString("tenTG");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return maTG;
    }
}
