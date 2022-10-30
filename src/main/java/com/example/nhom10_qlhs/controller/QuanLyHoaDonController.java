package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.entities.CTHD;
import com.example.nhom10_qlhs.entities.HoaDon;
import com.example.nhom10_qlhs.entities.NhanVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuanLyHoaDonController {

    @FXML
    private RadioButton radMaHD;

    @FXML
    private RadioButton radMaNV;

    @FXML
    private RadioButton radNgayLap;

    @FXML
    private ToggleGroup timkiem;

    @FXML
    private TextField txtTimKiem;

    @FXML
    private TableColumn<HoaDon, String> colKhachHang;

    @FXML
    private TableColumn<HoaDon, String> colMaHD;

    @FXML
    private TableColumn<HoaDon, Date> colNgayLap;

    @FXML
    private TableColumn<HoaDon, String> colNhanVien;

    @FXML
    private TableColumn<HoaDon, Double> colTongTien;

    @FXML
    private TableView<HoaDon> tblDSHoaDon;


    @FXML
    private TableColumn<CTHD, Double> colDonGia;

    @FXML
    private TableColumn<CTHD, String> colMaSach;

    @FXML
    private TableColumn<CTHD, Integer> colSoLuong;

    @FXML
    private TableColumn<CTHD, String> colTenSach;

    @FXML
    private TableColumn<CTHD, Double> colThanhTien;

    @FXML
    private TableView<CTHD> tblCTHD;

    @FXML
    private DatePicker datePicker;

    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private HoaDon hoaDon;
    private ObservableList<HoaDon> hoaDonObservableList = FXCollections.observableArrayList();
    private ObservableList<CTHD> cthdObservableList = FXCollections.observableArrayList();
    //Lấy danh sách hóa đơn theo Mã hóa đơn
    public List<HoaDon> getDSHoaDonTheoMa(String maHD){
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maHD);
            result = prepare.executeQuery();
            while(result.next()){
                hoaDon = new HoaDon(result.getString("maHoaDon"),
                        result.getString("maNV"),
                        result.getDate("ngayLap"),
                        result.getString("maKhachHang"),
                        result.getDouble("tongTien"));
                System.out.println(hoaDon);
                hoaDons.add(hoaDon);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return hoaDons;
    }

    //Lấy danh sách hóa đơn theo ngày lập
    public List<HoaDon> getDSHoaDonTheoNgayLap(Date ngayLap){
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE ngayLap = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setDate(1, ngayLap);
            result = prepare.executeQuery();
            while(result.next()){
                hoaDon = new HoaDon(result.getString("maHoaDon"),
                        result.getString("maNV"),
                        result.getDate("ngayLap"),
                        result.getString("maKhachHang"),
                        result.getDouble("tongTien"));
                hoaDons.add(hoaDon);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return hoaDons;
    }

    //Lấy danh sách hóa đơn theo mã nhân viên
    public List<HoaDon> getDSHoaDonTheoMaNV(String maNV){
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE maNV = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maNV);
            result = prepare.executeQuery();
            while(result.next()){
                hoaDon = new HoaDon(result.getString("maHoaDon"),
                        result.getString("maNV"),
                        result.getDate("ngayLap"),
                        result.getString("maKhachHang"),
                        result.getDouble("tongTien"));
                hoaDons.add(hoaDon);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return hoaDons;
    }


    //Hiển thị khách hàng lên bảng
    public void showHoaDons(ObservableList<HoaDon> hoaDons){
        colMaHD.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("maHD"));
        colNhanVien.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("maNV"));
        colNgayLap.setCellValueFactory(new PropertyValueFactory<HoaDon, Date>("ngayLap"));
        colKhachHang.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("maKhachHang"));
        colTongTien.setCellValueFactory(new PropertyValueFactory<HoaDon, Double>("tongThanhTien"));
        tblDSHoaDon.getItems();
        tblDSHoaDon.setItems(hoaDons);
    }
    public void showCTHD(ObservableList<CTHD> cthds){
        colMaSach.setCellValueFactory(new PropertyValueFactory<CTHD, String>("maSach"));
        colTenSach.setCellValueFactory(new PropertyValueFactory<CTHD, String>("tenSach"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<CTHD, Integer>("soLuong"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<CTHD, Double>("donGia"));
        colThanhTien.setCellValueFactory(new PropertyValueFactory<CTHD, Double>("thanhTien"));
        tblCTHD.getItems();
        tblCTHD.setItems(cthds);
    }
    //Tìm kiếm khách hàng
    public void timKiemKhachHang(){
        String searchKey = txtTimKiem.getText().toLowerCase();
        List<HoaDon> hoaDons;
        if (radMaHD.isSelected()){
            hoaDons = getDSHoaDonTheoMa(searchKey);
            if(!hoaDons.isEmpty()){
                hoaDonObservableList.setAll(hoaDons);
                showHoaDons(hoaDonObservableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy hóa đơn");
                alert.showAndWait();
            }
        }else if (radNgayLap.isSelected()){
            hoaDons = getDSHoaDonTheoNgayLap(Date.valueOf(datePicker.getValue()));
            if(!hoaDons.isEmpty()){
                hoaDonObservableList.setAll(hoaDons);
                showHoaDons(hoaDonObservableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy hóa đơn");
                alert.showAndWait();
            }
        } else if (radMaNV.isSelected()) {
            hoaDons = getDSHoaDonTheoMaNV(searchKey);
            if(!hoaDons.isEmpty()){
                hoaDonObservableList.setAll(hoaDons);
                showHoaDons(hoaDonObservableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy hóa đơn");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy khách hàng");
            alert.showAndWait();
        }
    }
    public void hienThiCTHD(MouseEvent mouseEvent){
        tblCTHD.setItems(null);
        HoaDon hd = tblDSHoaDon.getItems().get(tblDSHoaDon.getSelectionModel().getSelectedIndex());
        String sql = "SELECT maSach, Sach.tenSach, CTHoaDon.soLuong, donGia, thanhTien  " +
                "FROM CTHoaDon, Sach " +
                "WHERE CTHoaDon.maSach = Sach.maS AND maHoaDon = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, hd.getMaHD());
            result = prepare.executeQuery();
            while (result.next()){
                CTHD cthd = new CTHD(result.getString("maSach"),
                        result.getInt("soLuong"),
                        result.getDouble("donGia"),
                        result.getDouble("thanhTien"),
                        result.getString("tenSach"));
                if(cthdObservableList.size() == 0){
                    cthdObservableList.addAll(cthd);
                }else{
                    cthdObservableList.removeAll(cthd);
                    cthdObservableList.addAll(cthd);
                }
                showCTHD(cthdObservableList);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void showDatePicker(MouseEvent event){
            datePicker.setVisible(true);
            txtTimKiem.setVisible(false);
    }

    public void hideDatePicker(MouseEvent event){
            datePicker.setVisible(false);
            txtTimKiem.setVisible(true);
    }
}
