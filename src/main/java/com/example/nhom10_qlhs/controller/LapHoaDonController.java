package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.FxmlLoader;
import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LapHoaDonController implements Initializable {

    @FXML
    private Button btnTimKH;

    @FXML
    private TextField txtSDTKH;

    @FXML
    private Label lblMaKH;

    @FXML
    private Label lblTenKhachHang;

    @FXML
    private Label lblNgayBan;
    @FXML
    private Label lblMaNV;

    @FXML
    private Label lblTenNV;

    @FXML
    private Label lblChucVu;

    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    public void timKH(ActionEvent actionEvent){
        String sql = "SELECT * FROM KhachHang WHERE sdt = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txtSDTKH.getText());
            result = prepare.executeQuery();
            Alert alert;
            if (txtSDTKH.getText().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Không được bỏ trống");
                alert.showAndWait();
            }else {
                if (result.next()) {
                    lblMaKH.setText(result.getString("maKH"));
                    lblTenKhachHang.setText(result.getString("tenKH"));
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy khách hàng");
                    if(!alert.showAndWait().isEmpty()){
                        Stage stage = new Stage();
                        GetData.sdtKH = txtSDTKH.getText();
                        FxmlLoader fxmlLoader = new FxmlLoader();
                        BorderPane manHinhChinhPane = fxmlLoader.getBorderPane("man-hinh-chinh-gui");
                        BorderPane view = fxmlLoader.getBorderPane("quan-ly-khach-hang-gui");
                        manHinhChinhPane.setCenter(view);
                        stage.setScene(new Scene(manHinhChinhPane));
                        stage.show();
                        btnTimKH.getScene().getWindow().hide();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadThongTinNhanVien(){
        String sql = "SELECT * FROM NhanVien WHERE maNV = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, GetData.taiKhoan);
            result = prepare.executeQuery();
            if(result.next()){
                lblMaNV.setText(result.getString("maNV"));
                lblTenNV.setText(result.getString("tenNV"));
                lblChucVu.setText(result.getString("chucVu"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void loadNgayBan(){
        lblNgayBan.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadNgayBan();
        loadThongTinNhanVien();
    }
}
