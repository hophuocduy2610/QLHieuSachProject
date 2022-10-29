package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class DiaChiController {

    @FXML
    private Button btnThem;

    @FXML
    private TextField txtDuong;

    @FXML
    private TextField txtPhuong;

    @FXML
    private TextField txtQuan;

    @FXML
    private TextField txtSoNha;

    @FXML
    private TextField txtThanhPho;

    public void sendDataToTextFieldDiaChi() {
        if(!(txtSoNha.getText() == "" || txtDuong.getText() == "" || txtPhuong.getText() == "" || txtQuan.getText() == "" || txtThanhPho.getText() == "")){
            String diaChi = txtSoNha.getText() + " "
                    + txtDuong.getText() + ", "
                    + txtPhuong.getText() + ", "
                    + txtQuan.getText() + ", "
                    + txtThanhPho.getText();
            GetData.diaChi = diaChi;
            btnThem.getScene().getWindow().hide();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đầy đủ thông tin");
            alert.showAndWait();
        }

    }
}
