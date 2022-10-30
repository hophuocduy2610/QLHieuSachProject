package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class XacNhanThanhToanController implements Initializable {
    @FXML
    private Button btnHuy;

    @FXML
    private Button btnThanhToan;

    @FXML
    private Label lblThanhTien;

    @FXML
    private Label lblTienThoi;

    @FXML
    private TextField txtTienKhach;
        @FXML
        public void tinhTienThoi(ActionEvent event){
            double tienThoi = Double.parseDouble(txtTienKhach.getText()) - Double.parseDouble(lblThanhTien.getText());
            lblTienThoi.setText(String.valueOf(tienThoi));
        }
        public void thanhToan() {
            GetData.trangThaiButton = "dathanhtoan";
            btnThanhToan.getScene().getWindow().hide();
        }
        public void huyThanhToan(){
            GetData.trangThaiButton = "";
            btnHuy.getScene().getWindow().hide();
        }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblThanhTien.setText(String.valueOf(GetData.tongThanhTien));
    }
}
