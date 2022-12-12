package com.example.nhom10_qlhs.dao;

import com.example.nhom10_qlhs.connectdb.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NhaXuatBanDAO {
    public Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public String getMaNXBTheoTenNXB(String tenNXB) {
        String maNXB = "";
        String sql = "SELECT maNXB FROM NhaXuatBan WHERE tenNXB = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenNXB);
            result = prepare.executeQuery();
            while (result.next()){
                maNXB = result.getString("maNXB");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return maNXB;
    }

    public String getTenNXBTheoMaNXB(String maNXB) {
        String tenNXB = "";
        String sql = "SELECT tenNXB FROM NhaXuatBan WHERE maNXB = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maNXB);
            result = prepare.executeQuery();
            while (result.next()){
                tenNXB = result.getString("tenNXB");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return tenNXB;
    }
}
