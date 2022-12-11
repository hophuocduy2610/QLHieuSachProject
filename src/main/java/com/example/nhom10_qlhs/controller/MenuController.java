package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.FxmlLoader;
import com.example.nhom10_qlhs.GetData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable{
    @FXML
    private BorderPane menuPane;

    @FXML
    private Button btnDangXuat;

    @FXML
    private Label lblChucVu;

    @FXML
    private Label lblTenTaiKhoan;
    @FXML
    private void handleMenuItemAction(ActionEvent actionEvent){
        FxmlLoader fxmlLoader = new FxmlLoader();
        BorderPane view = fxmlLoader.getBorderPane("man-hinh-chinh-gui");
        menuPane.setCenter(view);
    }
    @FXML
    private void handleMenuItem1Action(ActionEvent actionEvent){
        FxmlLoader fxmlLoader = new FxmlLoader();
        BorderPane view = fxmlLoader.getBorderPane("lap-hoa-don-gui");
        menuPane.setCenter(view);
    }
    @FXML
    private void handleMenuItem2Action(ActionEvent actionEvent){
        FxmlLoader fxmlLoader = new FxmlLoader();
        BorderPane view = fxmlLoader.getBorderPane("quan-ly-hoa-don-gui");
        menuPane.setCenter(view);
    }
    @FXML
    private void handleMenuItem3Action(ActionEvent actionEvent){
        FxmlLoader fxmlLoader = new FxmlLoader();
        BorderPane view = fxmlLoader.getBorderPane("quan-ly-sach-gui");
        menuPane.setCenter(view);
    }
    @FXML
    private void handleMenuItem4Action(ActionEvent actionEvent){
        FxmlLoader fxmlLoader = new FxmlLoader();
        BorderPane view = fxmlLoader.getBorderPane("quan-ly-khach-hang-gui");
        menuPane.setCenter(view);
    }
    @FXML
    private void handleMenuItem5Action(ActionEvent actionEvent){
        FxmlLoader fxmlLoader = new FxmlLoader();
        BorderPane view = fxmlLoader.getBorderPane("quan-ly-nhan-vien-gui");
        menuPane.setCenter(view);
    }
    @FXML
    private void handleMenuItem6Action(ActionEvent actionEvent){
        FxmlLoader fxmlLoader = new FxmlLoader();
        BorderPane view = fxmlLoader.getBorderPane("thong-ke-gui");
        menuPane.setCenter(view);
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            if (event.getSource() == btnDangXuat) {
                Stage stage = new Stage();
                FxmlLoader fxmlLoader = new FxmlLoader();
                BorderPane view = fxmlLoader.getBorderPane("dang-nhap-gui");
                Scene scene = new Scene(view);
                stage.initStyle(StageStyle.DECORATED.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                btnDangXuat.getScene().getWindow().hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Hiển thị thông tin tài khoản trên menu
    @FXML
    public void loadTaiKhoanChucVu(){
        lblTenTaiKhoan.setText(GetData.taiKhoan);
        lblChucVu.setText(GetData.chucVu);
    }
    //Exit
    public void exit(ActionEvent event) {
        System.exit(0);
    }

    //Minimize
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) menuPane.getScene().getWindow();
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        stage.setIconified(true);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTaiKhoanChucVu();
    }
}
