package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.FxmlLoader;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DangNhapController implements Initializable {

    @FXML
    private Button btnDangNhap;

    @FXML
    private PasswordField txtMatKhau;

    @FXML
    private TextField txtTaiKhoan;

    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void login() {
        String sql = "SELECT * FROM TaiKhoan WHERE taiKhoan = ? and matKhau = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txtTaiKhoan.getText());
            prepare.setString(2, txtMatKhau.getText());
            result = prepare.executeQuery();

            Alert alert;

            if (txtTaiKhoan.getText().isEmpty() || txtMatKhau.getText().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Hãy nhập đầy đủ thông tin");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Đăng nhập thành công");
                    alert.showAndWait();
                    //Ẩn form đăng nhập
                    btnDangNhap.getScene().getWindow().hide();
                    //Hiển thị màn hình chính
                    Stage stage = new Stage();
                    FxmlLoader fxmlLoader = new FxmlLoader();
                    BorderPane view = fxmlLoader.getBorderPane("man-hinh-chinh-gui");
                    Scene scene = new Scene(view);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Sai tên tài khoản hoặc mật khẩu, hãy nhập lại");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}