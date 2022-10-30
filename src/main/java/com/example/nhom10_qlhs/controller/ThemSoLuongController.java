package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ThemSoLuongController {
    @FXML
    private TextField txtSoLuong;

    @FXML
    private Button btnOk;
    @FXML
    private Button btnHuy;

    //Truyền số lượng qua biến tạm Getdata.slSach
    public void sendSoLuong(ActionEvent event) {
        if(txtSoLuong.getText() != ""){
            GetData.slSach = Integer.parseInt(txtSoLuong.getText());
            GetData.trangThaiButton = "btnOk";
            btnOk.getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Hãy nhập đầy đủ thông tin");
            alert.showAndWait();
        }
    }

    //Thoát khỏi giao diện thêm số lượng
    public void huyNhapSoLuong(ActionEvent event){
        GetData.trangThaiButton = "btnHuy";
        btnHuy.getScene().getWindow().hide();
    }
}
