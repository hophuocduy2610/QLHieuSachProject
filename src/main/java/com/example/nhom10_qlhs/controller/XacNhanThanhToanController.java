package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
        public void tinhTienThoi(KeyEvent event){
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

        public void validData (MouseEvent event) {
            if (event.getSource().equals(txtTienKhach)) {
                if(!txtTienKhach.getText().matches("[0-9]{1,13}(\\.[0-9]*)?$")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Giá nhập phải là số thực lớn hơn 0 và không được bỏ trống");
                    alert.showAndWait();

                    txtTienKhach.setStyle("-fx-border-color:#e04040;");
                } else {
                    txtTienKhach.setStyle("-fx-border-color:#fff;");
                }
            }
        }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblThanhTien.setText(String.valueOf(GetData.tongThanhTien));
    }
}
