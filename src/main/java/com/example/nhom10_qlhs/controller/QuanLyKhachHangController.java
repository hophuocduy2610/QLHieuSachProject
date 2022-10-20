package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuanLyKhachHangController implements Initializable {
    @FXML
    private TextField txtSoDT;

    @FXML
    private ComboBox<String> cbxPhai;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtMaKH;

    @FXML
    private DatePicker txtNamSinh;

    @FXML
    private TextField txtTenKH;

    @FXML
    private Button btnThemKH;
    public void loadSDTKH(){
        txtSoDT.setText(GetData.sdtKH);
    }
    public void themKHMoi(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSDTKH();
        cbxPhai.setItems(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
    }
}
