package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.DangNhapMain;
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

import java.io.File;
import java.io.IOException;
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
    @FXML
    private Label lblTenKH;

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
                        lblTenKH.setText(result.getString("tenKH"));
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy khách hàng");
                    if(!alert.showAndWait().isEmpty()){
                        hienFormThemKhachHang();
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
    public void hienFormThemKhachHang() throws IOException {
        URL url = new File("target/classes/com/example/nhom10_qlhs/them-khach-hang-gui.fxml").toURI().toURL();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        //Sau khi form thêm khách hàng close, set text vào textField SDT và hiển thị tên khách hàng
        if(!stage.isShowing()){
            if(!lblTenKH.getText().equals(GetData.tenKH) || !txtSDTKH.getText().equals(GetData.sdtKH)){
                lblTenKH.setText(GetData.tenKH);
                txtSDTKH.setText(GetData.sdtKH);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadNgayBan();
        loadThongTinNhanVien();
    }
}
