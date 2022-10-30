package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.MyListener;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.entities.CTHD;
import com.example.nhom10_qlhs.entities.HoaDon;
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
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @FXML
    private TextField txtMaSach;
    List<Sach> saches = new ArrayList<>();
    ObservableList<SachInTable> sachObservableList = FXCollections.observableArrayList();
    private MyListener myListener;

    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    //Tìm thông tin khách hàng
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

    //Load thông tin nhân viên lên giao diện lập hóa đơn
    public void loadThongTinNhanVien(){
        String sql = "SELECT * FROM NhanVien WHERE maNV IN ( SELECT maTaiKhoan FROM TaiKhoan WHERE tenTaiKhoan = ? )";
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

    //Load ngày bán theo ngày hiện tại lên giao diện lập hóa đơn
    public void loadNgayBan(){
        lblNgayBan.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
    }

    //Hiện form thêm khách hàng khi không tìm thấy khách hàng
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

    //Lấy danh sách tất cả sách
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
                sach.setNamXuatBan(result.getInt("namXuatBan"));
                sach.setLoaiSach(result.getString("loaiSach"));
                sach.setGiaBan(result.getDouble("giaBan"));
                sach.setHinhAnhSach(result.getString("hinhAnhSach"));
                sachList.add(sach);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sachList;
    }

    //Hiển thị sách lên bảng hóa đơn
    public void showBooks(SachInTable sach){
        sachObservableList.add(sach);
        colMaSach.setCellValueFactory(new PropertyValueFactory<SachInTable, String>("maSach"));
        colTenSach.setCellValueFactory(new PropertyValueFactory<SachInTable, String>("tenSach"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<SachInTable, Integer>("soLuong"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<SachInTable, Double>("donGia"));
        colThanhTien.setCellValueFactory(new PropertyValueFactory<SachInTable, Double>("thanhTien"));
        colXoa.setCellValueFactory(new PropertyValueFactory<SachInTable, String>("checkBox"));
        tblHoaDon.getItems();
        tblHoaDon.setItems(sachObservableList);
    }

    //Xóa chi tiết hóa đơn
    @FXML
    private void xoaChiTietHoaDon(ActionEvent event) {
        ObservableList<SachInTable> dataListRemove = FXCollections.observableArrayList();
        Iterator sachItem = sachObservableList.iterator();

        while(sachItem.hasNext()) {
            SachInTable bean = (SachInTable) sachItem.next();
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

    //Hủy hóa đơn
    @FXML
    private void huyHoaDon(ActionEvent event){
        lblTenKH.setText("");
        txtSDTKH.setText("");
        tblHoaDon.setItems(null);
    }

    public void hienThiXacNhanThanhToan() throws IOException {
        tinhTongThanhTien();
        URL url = new File("target/classes/com/example/nhom10_qlhs/xac-nhan-thanh-toan.fxml").toURI().toURL();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        if(GetData.trangThaiButton.equals("dathanhtoan")) {
            System.out.println("Ngày bán trước khi thêm hóa đơn: " + GetData.ngayBan);
            HoaDon hd = new HoaDon(null, lblMaNV.getText(), GetData.ngayBan, lblMaKH.getText(), GetData.tongThanhTien);
            themHoaDon(hd);
            System.out.println("Ngày bán sau khi thêm hóa đơn: " + GetData.ngayBan);
            themCTHD(tblHoaDon, lblMaNV.getText(), GetData.ngayBan, lblMaKH.getText(), GetData.tongThanhTien);
            capNhatSoLuong(tblHoaDon);
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Hóa đơn chưa được thanh toán");
            alert.showAndWait();
        }
    }
    public void clearAll(){
        lblMaKH.setText("");
        lblTenKH.setText("");
        txtSDTKH.setText("");
        txtMaSach.setText("");
        tblHoaDon.setItems(null);
    }
    public void themCTHD(TableView<SachInTable> tblHoaDon, String maNV, Date ngayBan, String maKH, Double tongThanhTien){
        String sql = "SELECT maHoaDon from HoaDon " +
                "WHERE maNV = ? AND ngayLap = ? AND maKhachHang = ? AND tongTien = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maNV);
            prepare.setDate(2, ngayBan);
            System.out.println("Ngày bán ở themCTHD method: " + ngayBan);
            prepare.setString(3, maKH);
            prepare.setDouble(4, tongThanhTien);
            result = prepare.executeQuery();
            while (result.next()) {
                GetData.maHD = result.getString("maHoaDon");
                GetData.trangThai = 1;
                for(int i = 0; i < tblHoaDon.getItems().size(); i++){
                    SachInTable sachInTable = tblHoaDon.getItems().get(i);
                    String query = "  INSERT INTO CTHoaDon (maHoaDon, maSach, soLuong, donGia, thanhTien, trangThai) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";
                    try {
                        connect = ConnectDB.connect();
                        prepare = connect.prepareStatement(query);
                        prepare.setString(1, GetData.maHD);
                        prepare.setString(2, sachInTable.getMaSach());
                        prepare.setInt(3, sachInTable.getSoLuong());
                        prepare.setDouble(4, sachInTable.getDonGia());
                        prepare.setDouble(5, sachInTable.getThanhTien());
                        prepare.setInt(6, GetData.trangThai);
                        prepare.execute();
                    }catch (Exception ex){
                        ex.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Quá trình thêm chi tiết hóa đơn không thành công");
                        alert.showAndWait();
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Lấy mã hóa đơn không thành công");
            alert.showAndWait();
        }
    }
    public void themHoaDon(HoaDon hd){
        GetData.trangThai = 1;
        String sql = "  INSERT INTO HoaDon (maNV, ngayLap, maKhachHang, tongTien, trangThai) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, hd.getMaNV());
            prepare.setDate(2, hd.getNgayLap());
            prepare.setString(3, hd.getMaKhachHang());
            prepare.setDouble(4, hd.getTongThanhTien());
            prepare.setDouble(5, GetData.trangThai);
            prepare.execute();//Thực thi truy vấn sql
        }catch (Exception ex){
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Quá trình thêm hóa đơn vào hệ thống xảy ra lỗi");
            alert.showAndWait();
        }
    }
    public void capNhatSoLuong(TableView<SachInTable> tblHoaDon){
        for(int i = 0; i < tblHoaDon.getItems().size(); i++){
            SachInTable sachInTable = tblHoaDon.getItems().get(i);
            String sql = "  SELECT soLuong " +
                    "FROM Sach " +
                    "WHERE maS = ?";
            try {
                connect = ConnectDB.connect();
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, sachInTable.getMaSach());
                result = prepare.executeQuery();
                while (result.next()){
                    String query = "  UPDATE Sach " +
                            "SET soLuong = ? " +
                            "WHERE maS = ?";
                    try {
                        int soLuongConLai = result.getInt("soLuong") - sachInTable.getSoLuong();
                        System.out.println(soLuongConLai);
                        connect = ConnectDB.connect();
                        prepare = connect.prepareStatement(query);
                        prepare.setInt(1, soLuongConLai);
                        prepare.setString(2, sachInTable.getMaSach());
                        prepare.execute();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
    public void tinhTongThanhTien(){
        GetData.tongThanhTien = 0;
        for(int i = 0; i < tblHoaDon.getItems().size(); i++){
            SachInTable sachInTable = tblHoaDon.getItems().get(i);
            GetData.tongThanhTien += sachInTable.getThanhTien();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadNgayBan();
        loadThongTinNhanVien();
        saches = getDSSach();
        //Sau khi SachItemController truyền dữ liệu sách vào hàm onActionLisener thì myListener sẽ nhận được dữ liệu
        myListener = new MyListener() {
            @Override
            public void onActionListener(SachInTable sach) {
                //Hiển thị sách lên bảng hóa đơn
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
        try{
            ZoneId z = ZoneId.of( "America/Montreal" );
            LocalDate today = LocalDate.now( z );
            GetData.ngayBan = Date.valueOf(today);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
