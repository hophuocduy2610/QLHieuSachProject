package com.example.nhom10_qlhs.dao;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TaiKhoanDAO {
    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public boolean timTaiKhoan(String username, String password) {
        String sql = "SELECT * FROM TaiKhoan WHERE tenTaiKhoan = ? and matKhau = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username);
            prepare.setString(2, password);
            result = prepare.executeQuery();
            if(result.next()) {
                GetData.taiKhoan = username;
                GetData.chucVu = result.getString("loaiTK");
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
