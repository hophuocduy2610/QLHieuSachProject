package com.example.nhom10_qlhs.dao;

import com.example.nhom10_qlhs.connectdb.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NhaCungCapDAO {
    public Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    //Get nhà cung cấp
    public ObservableList<String> getNhaCungCap(){
        ObservableList<String> nccList = FXCollections.observableArrayList();
        String sql = "SELECT tenNCC FROM NhaCungCap";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()){
                nccList.add(result.getString("tenNCC"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return nccList;
    }

    public String getMaNCCTheoTen(String tenNCC) {
        String maNCC = "";
        String sql = "SELECT maNCC FROM NhaCungCap WHERE tenNCC = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenNCC);
            result = prepare.executeQuery();
            while (result.next()){
                maNCC = result.getString("maNCC");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return maNCC;
    }
    public String getTenNCCTheoMa(String maNCC) {
        String tenNCC = "";
        String sql = "SELECT tenNCC FROM NhaCungCap WHERE maNCC = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maNCC);
            result = prepare.executeQuery();
            while (result.next()){
                tenNCC = result.getString("tenNCC");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return tenNCC;
    }
}
