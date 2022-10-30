package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


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

    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Alert alert;

    //Tạo tài khoản sau khi thêm nhân viên
    public void taoTaiKhoan() {
        GetData.trangThai = 1;
        if(!(txtMatKhau.getText() == "" || txtXacNhanMatKhau.getText() == "")){
            if(txtXacNhanMatKhau.getText().equals(txtMatKhau.getText().toLowerCase())){
                String sql = "  INSERT INTO TaiKhoan (maTaiKhoan, tenTaiKhoan, matKhau, loaiTK, trangThai) " +
                        "VALUES (?, ?, ?, ?, ?)";
                try {
                    connect = ConnectDB.connect();
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, GetData.maNV);
                    prepare.setString(2, lblTenTK.getText());
                    prepare.setString(3, txtMatKhau.getText());
                    prepare.setString(4, lblLoaiTK.getText());
                    prepare.setInt(5, GetData.trangThai);
                    boolean result = prepare.execute();
                    if(!result){
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Tạo tài khoản thành công");
                        alert.showAndWait();
                    }
                    btnTao.getScene().getWindow().hide();
                }catch (Exception ex){
                    ex.printStackTrace();
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Tạo tài khoản không thành công");
                    alert.showAndWait();
                }
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Xác nhận mật khẩu không trùng khớp, vui lòng kiểm tra lại mật khẩu");
                alert.showAndWait();
            }
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đầy đủ thông tin");
            alert.showAndWait();
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblTenTK.setText(GetData.taiKhoan);
        lblLoaiTK.setText(GetData.chucVu);
    }
}
