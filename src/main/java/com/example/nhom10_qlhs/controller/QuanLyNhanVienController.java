package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.entities.KhachHang;
import com.example.nhom10_qlhs.entities.NhanVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class QuanLyNhanVienController implements Initializable {

    @FXML
    private ComboBox<String> cbxChucVu;

    @FXML
    private ComboBox<String> cbxPhai;

    @FXML
    private DatePicker txtNgayVaoLam;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtCMND;

    @FXML
    private DatePicker txtNamSinh;

    @FXML
    private TextField txtSDT;

    @FXML
    private TextField txtTaiKhoan;

    @FXML
    private TextField txtTenNV;

    @FXML
    private TextField txtTimKiem;

    @FXML
    private TableView<NhanVien> tblNhanVien;

    @FXML
    private TableColumn<NhanVien, String> colCMND;

    @FXML
    private TableColumn<NhanVien, String> colCapNhat;

    @FXML
    private TableColumn<NhanVien, String> colChucVu;

    @FXML
    private TableColumn<NhanVien, String> colDiaChi;

    @FXML
    private TableColumn<NhanVien, String> colMaNV;

    @FXML
    private TableColumn<NhanVien, Date> colNamSinh;

    @FXML
    private TableColumn<NhanVien, Date> colNgayVaoLam;

    @FXML
    private TableColumn<NhanVien, String> colPhai;

    @FXML
    private TableColumn<NhanVien, String> colSdtNV;

    @FXML
    private TableColumn<NhanVien, String> colTenNV;

    @FXML
    private TableColumn<NhanVien, String> colXoa;

    @FXML
    private Button btnThemTK;

    @FXML
    private RadioButton radMaNV;

    @FXML
    private RadioButton radSdtNV;

    @FXML
    private RadioButton radTenNV;

    private NhanVien nhanVien;
    private ObservableList<NhanVien> nhanVienObservableList = FXCollections.observableArrayList();
    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    //Lấy danh sách nhân viên theo Mã
    public List<NhanVien> getDSNhanVienTheoMa(String maNV){
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE maNV = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maNV);
            result = prepare.executeQuery();
            while(result.next()){
                nhanVien = new NhanVien(result.getString("maNV"),
                        result.getString("tenNV"),
                        result.getString("diaChi"),
                        result.getDate("namSinh"),
                        result.getString("sdt"),
                        result.getString("CMND"),
                        result.getString("phai"),
                        result.getString("chucVu"),
                        result.getDate("ngayVaoLam"));
                nhanVienList.add(nhanVien);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return nhanVienList;
    }

    //Lấy danh sách nhân viên theo tên
    public List<NhanVien> getDSNhanVienTheoTen(String tenNV){
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE tenNV = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenNV);
            result = prepare.executeQuery();
            while(result.next()){
                nhanVien = new NhanVien(result.getString("maNV"),
                        result.getString("tenNV"),
                        result.getString("diaChi"),
                        result.getDate("namSinh"),
                        result.getString("sdt"),
                        result.getString("CMND"),
                        result.getString("phai"),
                        result.getString("chucVu"),
                        result.getDate("ngayVaoLam"));
                nhanVienList.add(nhanVien);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return nhanVienList;
    }

    //Lấy danh sách nhân viên theo SDT
    public List<NhanVien> getDSNhanVienTheoSDT(String sdtNV){
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE sdt = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, sdtNV);
            result = prepare.executeQuery();
            while(result.next()){
                nhanVien = new NhanVien(result.getString("maNV"),
                        result.getString("tenNV"),
                        result.getString("diaChi"),
                        result.getDate("namSinh"),
                        result.getString("sdt"),
                        result.getString("CMND"),
                        result.getString("phai"),
                        result.getString("chucVu"),
                        result.getDate("ngayVaoLam"));
                nhanVienList.add(nhanVien);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return nhanVienList;
    }

    //Hiển thị nhân viên lên bảng
    public void showNhanViens(ObservableList<NhanVien> nhanViens) {
        colMaNV.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("maNV"));
        colTenNV.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("tenNV"));
        colNamSinh.setCellValueFactory(new PropertyValueFactory<NhanVien, Date>("namSinh"));
        colPhai.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("phai"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("diaChi"));
        colCMND.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("CMND"));
        colSdtNV.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("sdt"));
        colChucVu.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("chucVu"));
        colNgayVaoLam.setCellValueFactory(new PropertyValueFactory<NhanVien, Date>("ngayVaoLam"));
        colXoa.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("cbXoa"));
        //Phần callback này để tạo ra button edit
        Callback<TableColumn<NhanVien, String>, TableCell<NhanVien, String>> cellFactory = param -> {
            //Tạo ra TableCell để chưa button
            final TableCell<NhanVien, String> editCell = new TableCell<NhanVien, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //Kiểm tra cell vừa tạo không phải từ cột rỗng
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }else {
                        ImageView imgView = new ImageView("D:/PTUDBTLon/src/main/java/com/example/nhom10_qlhs/image/edit-icon.png");
                        imgView.setFitWidth(18);
                        imgView.setFitHeight(18);
                        //Tạo action cho button edit
                        final Button editButton = new Button("Edit",imgView);
                        editButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        editButton.setOnAction(event -> {
                            NhanVien nv = nhanVienObservableList.get(tblNhanVien.getSelectionModel().getSelectedIndex());
                            if(txtNamSinh.getValue() != null){
                                if(txtNgayVaoLam != null){
                                    capNhatThongTinNhanVien(nv.getMaNV(), txtTenNV.getText(),
                                            txtDiaChi.getText(), Date.valueOf(txtNamSinh.getValue()),
                                            txtSDT.getText(), txtCMND.getText(),
                                            cbxPhai.getValue(), cbxChucVu.getValue(), Date.valueOf(txtNgayVaoLam.getValue()));
                                }else {
                                    capNhatThongTinNhanVien(nv.getMaNV(), txtTenNV.getText(),
                                            txtDiaChi.getText(), Date.valueOf(txtNamSinh.getValue()),
                                            txtSDT.getText(), txtCMND.getText(),
                                            cbxPhai.getValue(), cbxChucVu.getValue(), nv.getNgayVaoLam());
                                }
                            }else if(txtNamSinh.getValue() == null){
                                if(txtNgayVaoLam!=null){
                                    capNhatThongTinNhanVien(nv.getMaNV(), txtTenNV.getText(),
                                            txtDiaChi.getText(), nv.getNamSinh(),
                                            txtSDT.getText(), txtCMND.getText(),
                                            cbxPhai.getValue(), cbxChucVu.getValue(), Date.valueOf(txtNgayVaoLam.getValue()));
                                }else {
                                    capNhatThongTinNhanVien(nv.getMaNV(), txtTenNV.getText(),
                                            txtDiaChi.getText(), nv.getNamSinh(),
                                            txtSDT.getText(), txtCMND.getText(),
                                            cbxPhai.getValue(), cbxChucVu.getValue(), nv.getNgayVaoLam());
                                }
                            }
                            nhanVienObservableList.setAll(getDSNhanVienTheoTen(txtTenNV.getText()));
                            clearTextField();
                        });
                        setGraphic(editButton);
                        setText(null);
                    }
                }
            };
            return editCell;
        };
        colCapNhat.setCellFactory(cellFactory);//Đưa ô chứa button edit vào cột cập nhật
        tblNhanVien.getItems();//bảng sẽ lấy các item ở trên
        tblNhanVien.setItems(nhanViens);//và set lên bảng với list khách hàng đưa vô
    }

    //Tìm kiếm nhân viên
    public void timKiemNhanVien(){
        String searchKey = txtTimKiem.getText().toLowerCase();//Lấy dữ liệu tìm kiếm và chuyển về chữ thường
        List<NhanVien> khachHangList;
        if (radMaNV.isSelected()){//nếu radio maNV được chọn thì
            khachHangList = getDSNhanVienTheoMa(searchKey);//lấy danh sách khách hàng theo mã
            if(!khachHangList.isEmpty()){//Nếu danh sách không rỗng thì
                nhanVienObservableList.setAll(khachHangList);//Đưa list khách hàng vô khachHangObservableList
                showNhanViens(nhanVienObservableList);//Hiển thị dữ liệu lên bảng
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy thông tin nhân viên");
                alert.showAndWait();
            }
            //Mấy câu if sau tương tự như radioMa
        }else if (radTenNV.isSelected()){
            khachHangList = getDSNhanVienTheoTen(searchKey);
            if(!khachHangList.isEmpty()){
                nhanVienObservableList.setAll(khachHangList);
                showNhanViens(nhanVienObservableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy thông tin nhân viên");
                alert.showAndWait();
            }
        } else if (radSdtNV.isSelected()) {
            khachHangList = getDSNhanVienTheoSDT(searchKey);
            if(!khachHangList.isEmpty()){
                nhanVienObservableList.setAll(khachHangList);
                showNhanViens(nhanVienObservableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy thông tin nhân viên");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy thông tin nhân viên");
            alert.showAndWait();
        }
    }

    //Thêm một nhân viên mới
    public void themNhanVien(){
        GetData.trangThai = 1; //set trạng thái cho nhân viên để dùng trong lúc xóa
        GetData.trangThaiButton = "btnThemNV";
        String sql = "  INSERT INTO NhanVien (tenNV, diaChi, sdt, ngayVaoLam, phai, namSinh, chucVu, CMND, trangThai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql); // đưa chuỗi sql vô 1 biến prepare để đưa giá trị cần trong câu query
            //Gán giá trị vào từng dấu chấm hỏi trong câu query sql thứ tự tăng dần từ trái sang phải
            prepare.setString(1, txtTenNV.getText());
            prepare.setString(2, txtDiaChi.getText());
            prepare.setString(3, txtSDT.getText());
            prepare.setDate(4, Date.valueOf(txtNgayVaoLam.getValue()));
            prepare.setString(5, cbxPhai.getValue());
            prepare.setString(6, txtNamSinh.getValue().toString());
            prepare.setString(7, cbxChucVu.getValue());
            prepare.setString(8, txtCMND.getText());
            prepare.setInt(9, GetData.trangThai);
            boolean result = prepare.execute();//Thực thi truy vấn sql

            if(!result){ //Nếu thực thi thành công thì xuất thông báo
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thành công");
                alert.showAndWait();
                taoTuDongTenTaiKhoan(GetData.trangThaiButton, txtSDT.getText());
                GetData.taiKhoan = txtTaiKhoan.getText();
                GetData.chucVu = cbxChucVu.getValue();
                clearTextField();
            }else { //Sai thì xuất lỗi
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

    //Cập nhật thông khách hàng
    public void capNhatThongTinNhanVien(String maNV,String tenNV, String diaChi, Date namSinh, String sdt,  String CMND, String phai, String chucVu, Date ngayVaoLam){
        String sql = "  UPDATE NhanVien " +
                "SET tenNV = ?, diaChi = ?, namSinh = ?, sdt = ?, CMND = ?, phai = ?, chucVu = ?, ngayVaoLam = ? " +
                "WHERE maNV = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenNV);
            prepare.setString(2, diaChi);
            if(namSinh != null){
                prepare.setDate(3, namSinh);
            }
            prepare.setString(4, sdt);
            prepare.setString(5, CMND);
            prepare.setString(6, phai);
            prepare.setString(7, chucVu);
            if(ngayVaoLam != null){
                prepare.setDate(8, ngayVaoLam);
            }
            prepare.setString(9, maNV);
            prepare.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Xóa nhân viên
    @FXML
    public void xoaNhanVien(ActionEvent event) {
        ObservableList<NhanVien> dataListRemove = FXCollections.observableArrayList();
        Iterator nhanVienIterator = nhanVienObservableList.iterator();

        while(nhanVienIterator.hasNext()) {
            NhanVien nhanVienTemp = (NhanVien) nhanVienIterator.next();
            if (nhanVienTemp.getCbXoa().isSelected()) {
                GetData.trangThai = 0;
                capNhatTrangThai(GetData.trangThai, nhanVienTemp.getMaNV());
                dataListRemove.add(nhanVienTemp);
            }
        }

        if(dataListRemove.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Chọn sản phẩm cần xóa");
            alert.showAndWait();
        }
        nhanVienObservableList.removeAll(dataListRemove);//remove tất cả những Khach hàng bị xóa trong list đó
    }

    //Cập nhật trạng thái khách hàng đã xóa
    public void capNhatTrangThai(int trangThai, String maNV){
        String sql = "  UPDATE NhanVien SET trangThai = ? WHERE maNV = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, trangThai);
            prepare.setString(2, maNV);
            boolean result = prepare.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Load thông tin trên bảng lên text field
    @FXML
    public void loadHangDuocChonLenTextField(MouseEvent mouseEvent){
        //Lấy dòng khách hàng được chọn trong bảng và đưa lên text field để thực thi chỉnh sửa
        NhanVien nv = tblNhanVien.getItems().get(tblNhanVien.getSelectionModel().getSelectedIndex());
        txtTenNV.setText(nv.getTenNV());
        txtNamSinh.setValue(nv.getNamSinh().toLocalDate());
        cbxPhai.setValue(nv.getPhai());
        txtNgayVaoLam.setValue(nv.getNgayVaoLam().toLocalDate());
        txtDiaChi.setText(nv.getDiaChi());
        txtCMND.setText(nv.getCMND());
        cbxChucVu.setValue(nv.getChucVu());
        txtSDT.setText(nv.getSdt());
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

        if(!stage.isShowing()){
            txtDiaChi.setText(GetData.diaChi);
            GetData.trangThaiButton = "";
        }
    }

    //Tạo tự động tên tài khoản sau khi thêm nhân viên
    public void taoTuDongTenTaiKhoan(String trangThaiButton, String SDTNhanVien){
        if(trangThaiButton.equals("btnThemNV")) {
            String sql = "SELECT CONVERT(VARCHAR(20), REPLACE(maNV, '-', '')) + CONVERT(VARCHAR(10), FORMAT(ngayVaoLam, 'ddMMyyyy', 'en-US'))  AS tenTK, maNV from NhanVien WHERE sdt = ?";
            try {
                connect = ConnectDB.connect();
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, SDTNhanVien);
                result = prepare.executeQuery();
                while (result.next()) {
                    txtTaiKhoan.setText(result.getString("tenTK"));
                    GetData.maNV = result.getString("maNV");
                    btnThemTK.setMouseTransparent(false);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void hienFormThemTaiKhoan() throws IOException {
        URL url = new File("target/classes/com/example/nhom10_qlhs/them-tai-khoan-gui.fxml").toURI().toURL();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();

        if(!stage.isShowing()){
            txtTaiKhoan.setText("");
        }
    }
    public void clearAll(){
        txtTenNV.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtCMND.setText("");
        cbxChucVu.setValue("Quản lý");
        txtNgayVaoLam.setValue(null);
        txtSDT.setText("");
        txtTimKiem.setText("");
        tblNhanVien.setItems(null);
    }
    public void clearTextField(){
        txtTenNV.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtCMND.setText("");
        cbxChucVu.setValue("Quản lý");
        txtNgayVaoLam.setValue(null);
        txtSDT.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxPhai.setItems(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
        cbxPhai.getSelectionModel().selectFirst();
        cbxChucVu.setItems(FXCollections.observableArrayList("Quản lý", "Nhân viên"));
        cbxChucVu.getSelectionModel().selectFirst();
        radMaNV.setSelected(true);
        txtTaiKhoan.setEditable(false);
        btnThemTK.setMouseTransparent(true);
    }
}
