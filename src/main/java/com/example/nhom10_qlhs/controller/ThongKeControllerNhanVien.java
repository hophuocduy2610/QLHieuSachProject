package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.dao.HoaDonDAO;
import com.example.nhom10_qlhs.entities.HoaDon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ThongKeControllerNhanVien implements Initializable {
    @FXML
    private DatePicker dpThoiGianTKHoaDon;

    @FXML
    private Label lblSoHDDaBan;

    @FXML
    private Label lblTongDoanhThuTKHD;

    @FXML
    private TableView<HoaDon> tblDSHoaDon;


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

    private Alert alert;

    private HoaDonDAO hoaDonDAO = new HoaDonDAO();

    private Double tongTien = 0.0;

    public void thongKeHoaDon(ActionEvent event) {
       if (dpThoiGianTKHoaDon.getValue() == null) {
           alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Vui lòng chọn thời gian để xem thống kê");
           alert.showAndWait();
       } else {
           tongTien = 0.0;
           ObservableList<HoaDon> hoaDons = FXCollections.observableArrayList();
           List<HoaDon> hoaDonList = hoaDonDAO.getHoaDonTheoNgayLap(Date.valueOf(dpThoiGianTKHoaDon.getValue()));
           hoaDons.addAll(hoaDonList);

           loadHoaDonVaoBang(hoaDons);

           lblSoHDDaBan.setText(hoaDonList.size() + " hóa đơn");

           for (HoaDon hoaDon : hoaDonList) {
               tongTien += hoaDon.getTongThanhTien();
               lblTongDoanhThuTKHD.setText(tongTien + " đ");
           }
       }
    }

    public void loadHoaDonVaoBang(ObservableList<HoaDon> hoaDons) {
        colMaHD.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("maHD"));
        colNhanVien.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("maNV"));
        colNgayLap.setCellValueFactory(new PropertyValueFactory<HoaDon, Date>("ngayLap"));
        colKhachHang.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("maKhachHang"));
        colTongTien.setCellValueFactory(new PropertyValueFactory<HoaDon, Double>("tongThanhTien"));
        tblDSHoaDon.setItems(hoaDons);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
