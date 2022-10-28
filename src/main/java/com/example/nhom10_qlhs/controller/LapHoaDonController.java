package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.MyListener;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.entities.Sach;
import com.example.nhom10_qlhs.entities.SachInTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    @FXML
    private VBox pnVBox;

    @FXML
    private TableView<SachInTable> tblHoaDon;

    @FXML
    private TableColumn<SachInTable, Double> colDonGia;

    @FXML
    private TableColumn<SachInTable, String> colMaSach;

    @FXML
    private TableColumn<SachInTable, Integer> colSoLuong;

    @FXML
    private TableColumn<SachInTable, String> colTenSach;

    @FXML
    private TableColumn<SachInTable, Double> colThanhTien;

    @FXML
    private TableColumn<SachInTable, String> colXoa;
    List<Sach> saches = new ArrayList<>();
    ObservableList<SachInTable> sachObservableList = FXCollections.observableArrayList();
    private MyListener myListener;

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
    public List<Sach> getDSSach(){
        Sach sach;
        List<Sach> sachList = new ArrayList<>();
        String sql = "SELECT * FROM Sach";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while(result.next()){
                sach = new Sach();
                sach.setMaSach(result.getString("maS"));
                sach.setTenSach(result.getString("tenSach"));
                sach.setSoLuong(result.getInt("soLuong"));
                sach.setGiaNhap(result.getDouble("giaNhap"));
                //sach.setNhaXuatBan(result.getString("nhaXuatBan"));
                sach.setNamXuatBan(result.getInt("namXuatBan"));
                //sach.setTacGia(result.getString("tacGia"));
                sach.setLoaiSach(result.getString("loaiSach"));
                //sach.setNhaCungCap(result.getString("nhaCungCap"));
                sach.setGiaBan(result.getDouble("giaBan"));
                sach.setHinhAnhSach(result.getString("hinhAnhSach"));
                sachList.add(sach);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sachList;
    }
    public void showBooks(SachInTable sach){
        sachObservableList.add(sach);
        System.out.println(sachObservableList);
        colMaSach.setCellValueFactory(new PropertyValueFactory<SachInTable, String>("maSach"));
        colTenSach.setCellValueFactory(new PropertyValueFactory<SachInTable, String>("tenSach"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<SachInTable, Integer>("soLuong"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<SachInTable, Double>("donGia"));
        colThanhTien.setCellValueFactory(new PropertyValueFactory<SachInTable, Double>("thanhTien"));
        colXoa.setCellValueFactory(new PropertyValueFactory<SachInTable, String>("checkBox"));
        tblHoaDon.getItems();
        tblHoaDon.setItems(sachObservableList);
    }
    @FXML
    private void xoaChiTietHoaDon(ActionEvent event) {
        ObservableList<SachInTable> dataListRemove = FXCollections.observableArrayList();
        Iterator var3 = sachObservableList.iterator();
        while(var3.hasNext()) {
            SachInTable bean = (SachInTable) var3.next();
            if (bean.getCheckBox().isSelected()) {
                dataListRemove.add(bean);
            }
        }
        if(dataListRemove.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Chọn sản phẩm cần xóa");
            alert.showAndWait();
        }
        sachObservableList.removeAll(dataListRemove);
    }
    @FXML
    private void huyHoaDon(ActionEvent event){
        lblTenKH.setText("");
        txtSDTKH.setText("");
        tblHoaDon.setItems(null);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadNgayBan();
        loadThongTinNhanVien();
        System.out.println(getDSSach());
        saches = getDSSach();
        System.out.println(saches);
        myListener = new MyListener() {
            @Override
            public void onActionListener(SachInTable sach) {
                System.out.println(sach);
                showBooks(sach);
            }
        };
        //Hiển thị item sách lên scrollpane
        try{
            for (Sach sach : saches) {
                URL url = new File("target/classes/com/example/nhom10_qlhs/sach-item.fxml").toURI().toURL();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(url);
                HBox hBox = fxmlLoader.load();
                SachItemController sachItemController = fxmlLoader.getController();
                sachItemController.setDataSach(sach, myListener);
                pnVBox.getChildren().add(hBox);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
