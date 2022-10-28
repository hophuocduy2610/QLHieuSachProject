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

    public void sendSoLuong(ActionEvent event) {
        if(txtSoLuong.getText() != ""){
            GetData.slSach = Integer.parseInt(txtSoLuong.getText());
            GetData.trangThaiThemSoLuong = "btnOk";
            btnOk.getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Hãy nhập đầy đủ thông tin");
            alert.showAndWait();
        }
    }
    public void huyNhapSoLuong(ActionEvent event){
        btnHuy.getScene().getWindow().hide();
    }
}
