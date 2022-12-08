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
}
