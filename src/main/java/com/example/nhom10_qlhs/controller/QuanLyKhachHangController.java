package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.entities.KhachHang;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Date;
import java.util.ResourceBundle;
public class QuanLyKhachHangController implements Initializable {
    @FXML
    private TextField txtSoDT;

    @FXML
    private ComboBox<String> cbxPhai;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblMaKH;

    @FXML
    private DatePicker txtNamSinh;

    @FXML
    private TextField txtTenKH;

    @FXML
    private Button btnThemKH;

    @FXML
    private TableColumn<KhachHang, String> colDiaChi;

    @FXML
    private TableColumn<KhachHang, String> colEmail;

    @FXML
    private TableColumn<KhachHang, String> colHanhDong;

    @FXML
    private TableColumn<KhachHang, String> colMaKH;

    @FXML
    private TableColumn<KhachHang, Date> colNamSinh;

    @FXML
    private TableColumn<KhachHang, String> colPhai;

    @FXML
    private TableColumn<KhachHang, String> colSDT;

    @FXML
    private TableColumn<KhachHang, String> colTenKH;

    @FXML
    private TableView<KhachHang> tblKhachHang;

    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    ObservableList<KhachHang>  khachHangs = FXCollections.observableArrayList();
    public void loadSDTKH(){
        txtSoDT.setText(GetData.sdtKH);
    }
    //Làm mới dữ liệu trong bảng
    public void refreshTable(){
        String  query = "SELECT * FROM KhachHang";
        try {
            connect = ConnectDB.connect();
            khachHangs.clear();
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            while (result.next()){
                khachHangs.add(new KhachHang(result.getString("maKH"),
                            result.getString("tenKH"),
                            result.getString("diaChi"),
                            result.getString("sdt"),
                            result.getString("email"),
                            result.getString("phai"),
                            result.getDate("namSinh")));
                tblKhachHang.setItems(khachHangs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //Thêm dữ liệu vào bảng
    public void loadDataTable(){
        refreshTable();

    }
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
            System.out.println(cbxPhai.getValue());
            prepare.setString(6, txtNamSinh.getValue().toString());
            System.out.println(txtNamSinh.getValue().toString());
            boolean result = prepare.execute();
            if(!result){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thành công");
                alert.showAndWait();
                clearFormThongTinKhachHang();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Thêm không thành công");
                alert.showAndWait();
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
    public void loadMaKH(){
        String sql = "SELECT MAX(maKH) AS MaKH FROM KhachHang";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if(result.next()){
                lblMaKH.setText(result.getString("MaKH"));
            }else {
                lblMaKH.setText("");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void clearFormThongTinKhachHang(){
        lblMaKH.setText("");
        txtTenKH.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSoDT.setText("");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSDTKH();
        cbxPhai.setItems(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
        loadMaKH();
    }
}
