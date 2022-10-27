package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ThemKhachHangController implements Initializable {
    @FXML
    private Button btnThem;
    @FXML
    private ComboBox<String> cbxPhai;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker txtNamSinh;

    @FXML
    private TextField txtSoDT;

    @FXML
    private TextField txtTenKH;
    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    //Hiện form địa chỉ
    public void hienFormThemDiaChi() throws IOException {
        URL url = new File("target/classes/com/example/nhom10_qlhs/them-dia-chi-gui.fxml").toURI().toURL();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        //Sau khi form địa chỉ close, set text vào textField địa chỉ
        if(!stage.isShowing()){
            txtDiaChi.setText(GetData.diaChi);
        }
    }
    public void themKhachHang(){
        String sql = "  INSERT INTO KhachHang (tenKH, diaChi, sdt, email, phai, namSinh) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txtTenKH.getText());
            prepare.setString(2, txtDiaChi.getText());
            prepare.setString(3, txtSoDT.getText());
            prepare.setString(4, txtEmail.getText());
            prepare.setString(5, cbxPhai.getValue());
            prepare.setString(6, txtNamSinh.getValue().toString());
            boolean result = prepare.execute();
            if(!result){
                GetData.tenKH = txtTenKH.getText();
                GetData.sdtKH = txtSoDT.getText();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thành công");
                alert.showAndWait();
                btnThem.getScene().getWindow().hide();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Thêm không thành công");
                alert.showAndWait();
                btnThem.getScene().getWindow().hide();
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Thêm không thành công");
            alert.showAndWait();
        }
    }
    public void clearFormThongTinKhachHang(){
        txtTenKH.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSoDT.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxPhai.setItems(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
    }
}
