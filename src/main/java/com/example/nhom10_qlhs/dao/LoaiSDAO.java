package com.example.nhom10_qlhs.dao;

import com.example.nhom10_qlhs.connectdb.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoaiSDAO {
    public Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    //Get loại sách
    public ObservableList<String> getLoaiSach(){
        ObservableList<String> loaiSachList = FXCollections.observableArrayList();
        String sql = "SELECT tenLoai FROM LoaiS";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()){
                loaiSachList.add(result.getString("tenLoai"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return loaiSachList;
    }

    public String getMaLoaiSachTheoTenLoai(String tenLoai) {
        String maLoaiS = "";
        String sql = "SELECT maLoai FROM LoaiS WHERE tenLoai = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenLoai);
            result = prepare.executeQuery();
            while (result.next()){
                maLoaiS = result.getString("maLoai");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return maLoaiS;
    }

    public String getTenLoaiSachTheoMaLoai(String maLoai) {
        String tenLoaiS = "";
        String sql = "SELECT tenLoai FROM LoaiS WHERE maLoai = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maLoai);
            result = prepare.executeQuery();
            while (result.next()){
                tenLoaiS = result.getString("tenLoai");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return tenLoaiS;
    }
}
