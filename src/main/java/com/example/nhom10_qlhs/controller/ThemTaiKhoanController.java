package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.dao.TaiKhoanDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class ThemTaiKhoanController implements Initializable {
    @FXML
    private Button btnTao;


    @FXML
    private PasswordField txtMatKhau;

    @FXML
    private Label lblLoaiTK;

    @FXML
    private Label lblTenTK;

    @FXML
    private PasswordField txtXacNhanMatKhau;


    @FXML
    private TextField txtHienMK;

    @FXML
    private TextField txtHienMatKhau;


    @FXML
    private Label errMatKhau;

    @FXML
    private Label errNhapLaiMatKhau;

    @FXML
    private BorderPane pnThemTK;
    private Alert alert;

    TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    //Tạo tài khoản sau khi thêm nhân viên
    public void taoTaiKhoan() {
        GetData.trangThai = 1;
        boolean result = taiKhoanDAO.themTaiKhoan(GetData.maNV,lblTenTK.getText(), txtMatKhau.getText(), lblLoaiTK.getText(), GetData.trangThai);
        if(txtMatKhau.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Mật khẩu không được bỏ trống");
            alert.showAndWait();
            return;
        } else if (txtXacNhanMatKhau.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Xác nhận mật khẩu không được bỏ trống");
            alert.showAndWait();
            return;
        }

        if(errMatKhau.getText() != "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Mật khẩu không hợp lệ");
            alert.showAndWait();
            return;
        } else if (errNhapLaiMatKhau.getText() != "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Xác nhận mật khẩu không hợp lệ");
            alert.showAndWait();
            return;
        }

        if(result){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Tạo tài khoản thành công");
            alert.showAndWait();
        }
        btnTao.getScene().getWindow().hide();
        GetData.trangThai = 0;
        GetData.taiKhoan = "";
        GetData.chucVu = "";
    }

    //Hiển thị mật khẩu
    public void showPasswordMatKhau(MouseEvent mouseEvent){
        txtHienMatKhau.setText(txtMatKhau.getText());
        txtHienMatKhau.setVisible(true);
        txtMatKhau.setVisible(false);
    }
    public void tatShowPasswordMatKhau(MouseEvent mouseEvent){
        txtMatKhau.setText(txtHienMatKhau.getText());
        txtMatKhau.setVisible(true);
        txtHienMatKhau.setVisible(false);
    }

    public void showPasswordXacNhanMatKhau(MouseEvent mouseEvent){
        txtHienMK.setText(txtXacNhanMatKhau.getText());
        txtHienMK.setVisible(true);
        txtXacNhanMatKhau.setVisible(false);
    }
    public void tatShowPasswordXacNhanMatKhau(MouseEvent mouseEvent){
        txtXacNhanMatKhau.setText(txtHienMK.getText());
        txtXacNhanMatKhau.setVisible(true);
        txtHienMK.setVisible(false);
    }

    public void validData (KeyEvent event) {
        if (event.getSource().equals(txtMatKhau)) {
            if (!txtMatKhau.getText().matches("^([A-Za-z0-9]){8,20}$")) {
                errMatKhau.setText("Mật khẩu không hợp lệ và không được bỏ trống");
                txtMatKhau.setStyle("-fx-border-color:#e04040;");
            } else {
                errMatKhau.setText("");
                txtMatKhau.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtXacNhanMatKhau)) {
            if (!txtXacNhanMatKhau.getText().equals(txtMatKhau.getText())) {
                errNhapLaiMatKhau.setText("Mật khẩu chưa trùng khớp");
                txtXacNhanMatKhau.setStyle("-fx-border-color:#e04040;");
            } else {
                errNhapLaiMatKhau.setText("");
                txtXacNhanMatKhau.setStyle("-fx-border-color:#fff;");
            }
        }
    }

    //Exit
    public void exit(ActionEvent event) {
        pnThemTK.getScene().getWindow().hide();
    }

    //Minimize
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) pnThemTK.getScene().getWindow();
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        stage.setIconified(true);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblTenTK.setText(GetData.taiKhoan);
        lblLoaiTK.setText(GetData.chucVu);
    }
}
