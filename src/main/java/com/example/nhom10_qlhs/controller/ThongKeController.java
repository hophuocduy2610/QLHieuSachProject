package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.dao.CTHoaDonDAO;
import com.example.nhom10_qlhs.dao.HoaDonDAO;
import com.example.nhom10_qlhs.entities.HoaDon;
import com.example.nhom10_qlhs.entities.KhachHang;
import com.example.nhom10_qlhs.entities.SachThongKe;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ThongKeController implements Initializable {
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private ComboBox<String> cbxNam;

    @FXML
    private ComboBox<String> cbxThang;

    @FXML
    private Label lblLoiNhuan;

    @FXML
    private Label lblTienBanDuoc;

    @FXML
    private Label lblTongTienNhap;

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
    private CTHoaDonDAO ctHoaDonDAO = new CTHoaDonDAO();

    private double tienBanDuoc;

    private double tienNhap;

    XYChart.Series data = new XYChart.Series();
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private DatePicker dpThoiGianTKSach;

    @FXML
    private Label lblSoSachDaBan;

    @FXML
    private TableColumn<SachThongKe, String> colMaSach;

    @FXML
    private TableColumn<SachThongKe, String> colTenSach;

    @FXML
    private TableColumn<SachThongKe, Integer> colSoLuong;

    @FXML
    private TableColumn<SachThongKe, Double> colGiaBan;

    @FXML
    private TableColumn<SachThongKe, Date> colNgayBan;

    @FXML
    private TableColumn<SachThongKe, Double> colTongTienSach;

    @FXML
    private TableView<SachThongKe> tblDSSach;
    @FXML
    private Label lblTongDoanhThuBanSach;
    private Double tongTien = 0.0;
    public void thongKeDoanhThu(ActionEvent event) {

        if (cbxThang.getValue() == "" && cbxNam.getValue() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn tháng năm để xem thống kê");
            alert.showAndWait();
        } else if (cbxThang.getValue() != "" && cbxNam.getValue() == "") {
            resetData();
            List<HoaDon> hoaDons = new ArrayList<>();
            hoaDons = hoaDonDAO.getHoaDonTheoThang(Integer.valueOf(cbxThang.getValue()));

            for (HoaDon hoaDon : hoaDons) {
                tienBanDuoc += hoaDon.getTongThanhTien();
            }
            lblTienBanDuoc.setText(tienBanDuoc + "đ");

            for (Double tienNhapTemp : hoaDonDAO.getTienNhapTheoThang(Integer.valueOf(cbxThang.getValue())))
            {
                tienNhap += tienNhapTemp;
            }
            lblTongTienNhap.setText(tienNhap + "đ");

            lblLoiNhuan.setText(String.valueOf(tienBanDuoc - tienNhap) +"đ");

            data.setName("Doanh thu bán được trong tháng");
            x.setLabel("Tháng");

            XYChart.Data dt = new XYChart.Data(cbxThang.getValue(), tienBanDuoc);

            dt.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(dt);
                    }
                }
            });

            data.getData().add(dt);

            barChart.getData().clear();
            barChart.setAnimated(false);
            barChart.getData().add(data);
        } else if (cbxThang.getValue() == "" && cbxNam.getValue() != "") {
            resetData();
            List<HoaDon> hoaDons = new ArrayList<>();
            hoaDons = hoaDonDAO.getHoaDonTheoNam(Integer.valueOf(cbxNam.getValue()));

            for (HoaDon hoaDon : hoaDons) {
                tienBanDuoc += hoaDon.getTongThanhTien();
            }
            lblTienBanDuoc.setText(tienBanDuoc + "đ");

            for (Double tienNhapTemp : hoaDonDAO.getTienNhapTheoNam(Integer.valueOf(cbxNam.getValue())))
            {
                tienNhap += tienNhapTemp;
            }
            lblTongTienNhap.setText(tienNhap + "đ");

            lblLoiNhuan.setText(String.valueOf(tienBanDuoc - tienNhap) +"đ");

            data.setName("Doanh thu bán được trong năm");
            x.setLabel("Năm");

            XYChart.Data dt = new XYChart.Data(""+cbxNam.getValue(), tienBanDuoc);

            dt.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(dt);
                    }
                }
            });

            data.getData().add(dt);

            barChart.getData().clear();
            barChart.setAnimated(false);
            barChart.getData().add(data);
        } else if (cbxThang.getValue() != "" && cbxNam.getValue() != "") {
            resetData();
            List<HoaDon> hoaDons = new ArrayList<>();
            hoaDons = hoaDonDAO.getHoaDonTheoThangNam(Integer.valueOf(cbxThang.getValue()), Integer.valueOf(cbxNam.getValue()));

            for (HoaDon hoaDon : hoaDons) {
                tienBanDuoc += hoaDon.getTongThanhTien();
            }
            lblTienBanDuoc.setText(tienBanDuoc + "đ");

            for (Double tienNhapTemp : hoaDonDAO.getTienNhapTheoThangNam(Integer.valueOf(cbxThang.getValue()), Integer.valueOf(cbxNam.getValue())))
            {
                tienNhap += tienNhapTemp;
            }
            lblTongTienNhap.setText(tienNhap + "đ");

            lblLoiNhuan.setText(String.valueOf(tienBanDuoc - tienNhap) +"đ");

            data.setName("Doanh thu bán được trong tháng " + cbxThang.getValue() + " năm " + cbxNam.getValue());
            x.setLabel("Tháng " + cbxThang.getValue() + " năm " + cbxNam.getValue());

            XYChart.Data dt = new XYChart.Data(""+cbxThang.getValue() +"/"+cbxNam.getValue(), tienBanDuoc);

            dt.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(dt);
                    }
                }
            });

            data.getData().add(dt);
            barChart.getData().clear();
            barChart.setAnimated(false);
            barChart.getData().add(data);
        }
    }

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

           //Lấy số hóa đơn từ list
           lblSoHDDaBan.setText(hoaDonList.size() + " hóa đơn");

           //Tính tổng thành tiền
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

    public void thongKeSach(ActionEvent event) {
        if (dpThoiGianTKSach.getValue() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn thời gian để xem thống kê");
            alert.showAndWait();
        } else {
            Date ngayBan = Date.valueOf(dpThoiGianTKSach.getValue());

            int soSachDaBan = ctHoaDonDAO.getSoLuongSachTheoNgayBan(ngayBan);
            lblSoSachDaBan.setText(String.valueOf(soSachDaBan));

            double tongDoanhThu = ctHoaDonDAO.getTongDoanhThuBanSachTheoNgayBan(ngayBan);
            lblTongDoanhThuBanSach.setText(String.valueOf(tongDoanhThu) + " đ");

            List<SachThongKe> sachThongKeList = ctHoaDonDAO.getSachDaBan(ngayBan);
            ObservableList<SachThongKe> sachThongKes = FXCollections.observableArrayList();
            sachThongKes.addAll(sachThongKeList);
            loadSachDaBanVaoBang(sachThongKes);
        }
    }

    public void loadSachDaBanVaoBang (ObservableList<SachThongKe> sachThongKes) {
        colMaSach.setCellValueFactory(new PropertyValueFactory<SachThongKe, String>("maSach"));
        colTenSach.setCellValueFactory(new PropertyValueFactory<SachThongKe, String>("tenSach"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<SachThongKe, Integer>("soLuong"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<SachThongKe, Double>("giaBan"));
        colNgayBan.setCellValueFactory(new PropertyValueFactory<SachThongKe, Date>("ngayBan"));
        colTongTienSach.setCellValueFactory(new PropertyValueFactory<SachThongKe, Double>("tongTien"));
        tblDSSach.setItems(sachThongKes);
    }
    public void resetData() {
        tienNhap = 0;
        tienBanDuoc = 0;
    }

    private void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
                Group parentGroup = (Group) parent;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                dataText.setLayoutX(
                        Math.round(
                                bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                        )
                );
                dataText.setLayoutY(
                        Math.round(
                                bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                        )
                );
            }
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> year = FXCollections.observableArrayList();
        ObservableList<String> month = FXCollections.observableArrayList();

        month.add("");
        for(int i = 1; i <= 12; i++) {
            month.add(String.valueOf(i));
        }
        cbxThang.setItems(month);
        cbxThang.getSelectionModel().selectFirst();

        year.add("");
        for(int i = 1900; i <= LocalDate.now().getYear(); i++) {
            year.add(String.valueOf(i));
        }
        cbxNam.setItems(year);
        cbxNam.getSelectionModel().selectFirst();
    }
}
