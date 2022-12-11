package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ThemSoLuongController {
    @FXML
    private TextField txtSoLuong;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnHuy;

    @FXML
    private Label errSoLuong;
    Alert alert;

    //Truyền số lượng qua biến tạm Getdata.slSach
    public void sendSoLuong(ActionEvent event) {

        if(txtSoLuong.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Số lượng không được bỏ trống");
            alert.showAndWait();
            return;
        }

        if(errSoLuong.getText() != "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Số lượng không hợp lệ");
            alert.showAndWait();
        }

        if(Integer.valueOf(txtSoLuong.getText()) < 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Số lượng không được là số âm");
            alert.showAndWait();
        }

        GetData.slSach = Integer.parseInt(txtSoLuong.getText());
        GetData.trangThaiButton = "btnOk";
        btnOk.getScene().getWindow().hide();
    }

    //Thoát khỏi giao diện thêm số lượng
    public void huyNhapSoLuong(ActionEvent event){
        GetData.trangThaiButton = "btnHuy";
        btnHuy.getScene().getWindow().hide();
    }

    public void validData (KeyEvent event) {
        if (event.getSource().equals(txtSoLuong)) {
            if(!txtSoLuong.getText().matches("^[0-9]+$")) {
                errSoLuong.setText("Số lượng phải là số không được âm");
                txtSoLuong.setStyle("-fx-border-color:#e04040;");
            } else {
                errSoLuong.setText("");
                txtSoLuong.setStyle("-fx-border-color:#fff;");
            }
        }
    }
}
