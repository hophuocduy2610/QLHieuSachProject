package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.FxmlLoader;
import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.dao.TaiKhoanDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class DangNhapController implements Initializable{

    @FXML
    private Button btnDangNhap;

    @FXML
    private PasswordField txtMatKhau;

    @FXML
    private TextField txtHienMatKhau;


    @FXML
    private TextField txtTaiKhoan;


    @FXML
    private BorderPane dangNhapBorderPane;

    @FXML
    private Label errMatKhau;

    @FXML
    private Label errTaiKhoan;

    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    private Alert alert;

    public void login() {
        boolean result = taiKhoanDAO.timTaiKhoan(txtTaiKhoan.getText(), txtMatKhau.getText());
        if(errTaiKhoan.getText() != ""){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tên tài khoản không hợp lệ, vui lòng nhập lại");
            alert.showAndWait();
            return;
        } else if (errMatKhau.getText() != "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Mật khẩu không hợp lệ, vui lòng nhập lại");
            alert.showAndWait();
            return;
        }
        if (result) {
            //Ẩn form đăng nhập
            btnDangNhap.getScene().getWindow().hide();

            if (GetData.chucVu.equals("Quản lý")) {
                //Hiển thị màn hình chính
                Stage stage = new Stage();
                FxmlLoader fxmlLoader = new FxmlLoader();
                BorderPane menu = fxmlLoader.getBorderPane("menu-gui");
                BorderPane view = fxmlLoader.getBorderPane("man-hinh-chinh-gui");
                menu.setCenter(view);
                Scene scene = new Scene(menu, 1280, 720);
                stage.initStyle(StageStyle.DECORATED.UNDECORATED);
                stage.setScene(scene);
                stage.show();
            } else {
                //Hiển thị màn hình chính
                Stage stage = new Stage();
                FxmlLoader fxmlLoader = new FxmlLoader();
                BorderPane menu = fxmlLoader.getBorderPane("menu-gui-nhanvien");
                BorderPane view = fxmlLoader.getBorderPane("man-hinh-chinh-gui");
                menu.setCenter(view);
                Scene scene = new Scene(menu, 1280, 720);
                stage.initStyle(StageStyle.DECORATED.UNDECORATED);
                stage.setScene(scene);
                stage.show();
            }

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Sai tên tài khoản hoặc mật khẩu, hãy nhập lại");
            alert.showAndWait();
        }
    }

    public void validData(KeyEvent event) {
        if(event.getSource().equals(txtTaiKhoan)){
            if (!txtTaiKhoan.getText().matches("^([A-Za-z0-9]){8,20}$")) {
                errTaiKhoan.setText("* Tên tài khoản phải lớn hơn 8 kí tự");
                txtTaiKhoan.setStyle("-fx-border-color:#e04040;");
            } else {
                errTaiKhoan.setText("");
                txtTaiKhoan.setStyle("-fx-border-color:#fff;");
            }
        } else {
            if (!txtMatKhau.getText().matches("^([A-Za-z0-9]){8,20}$")) {
                errMatKhau.setText("* Mật khẩu phải lớn hơn 8 kí tự");
                txtMatKhau.setStyle("-fx-border-color:#e04040;");
                txtHienMatKhau.setStyle("-fx-border-color:#e04040;");
            } else {
                errMatKhau.setText("");
                txtMatKhau.setStyle("-fx-border-color:#fff;");
                txtHienMatKhau.setStyle("-fx-border-color:#fff;");
            }
        }
    }

    //Exit
    public void exit(ActionEvent event) {
        System.exit(0);
    }

    //Minimize
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) dangNhapBorderPane.getScene().getWindow();
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        stage.setIconified(true);
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}