package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.dao.NhanVienDAO;
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

    @FXML
    private TextField txtMaNV;

    private NhanVien nhanVien;
    private ObservableList<NhanVien> nhanVienObservableList = FXCollections.observableArrayList();

    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

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

                            nhanVienDAO.capNhatThongTinNhanVien(nv.getMaNV(), txtTenNV.getText(),
                                    txtDiaChi.getText(), Date.valueOf(txtNamSinh.getValue()),
                                    txtSDT.getText(), txtCMND.getText(),
                                    cbxPhai.getValue(), cbxChucVu.getValue(), Date.valueOf(txtNgayVaoLam.getValue()));

                            nhanVienObservableList.setAll(nhanVienDAO.getDSNhanVienTheoTen(txtTenNV.getText()));
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
            khachHangList = nhanVienDAO.getDSNhanVienTheoMa(searchKey);//lấy danh sách khách hàng theo mã
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
            khachHangList = nhanVienDAO.getDSNhanVienTheoTen(searchKey);
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
            khachHangList = nhanVienDAO.getDSNhanVienTheoSDT(searchKey);
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
        boolean result = nhanVienDAO.themNhanVien(txtMaNV.getText(), txtTenNV.getText(), txtDiaChi.getText(), txtSDT.getText(), Date.valueOf(txtNgayVaoLam.getValue()), cbxPhai.getValue(), Date.valueOf(txtNamSinh.getValue()), cbxChucVu.getValue(), txtCMND.getText());
        if(result){ //Nếu thực thi thành công thì xuất thông báo
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
    }

    public void taoTuDongTenTaiKhoan(String trangThaiButton, String SDTNhanVien) {
        if(nhanVienDAO.taoTuDongTenTaiKhoan(trangThaiButton, SDTNhanVien)) {
            txtTaiKhoan.setText(GetData.taiKhoan);
            GetData.maNV = txtMaNV.getText();
            btnThemTK.setMouseTransparent(false);
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
                nhanVienDAO.capNhatTrangThai(GetData.trangThai, nhanVienTemp.getMaNV());
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

    //Tạo mã Nhân viên tự động
    public String taoMaNV() {
        String maNV = "NV";
        int rowCount = nhanVienDAO.demSoNhanVien();
        boolean dup = false;
        do {
            if (rowCount > 998){
                maNV = maNV + (rowCount + 1);
            } else if (rowCount > 98) {
                maNV = maNV + "0" + (rowCount + 1);
            } else if (rowCount > 8) {
                maNV = maNV + "00" + (rowCount + 1);
            } else {
                maNV = maNV + "000" + (rowCount + 1);
            }
        } while (dup);
        return maNV;
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
    public void clearAll() {
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
    public void clearTextField() {
        txtTenNV.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtCMND.setText("");
        cbxChucVu.setValue("Quản lý");
        txtNgayVaoLam.setValue(null);
        txtSDT.setText("");
    }

    public void validData(MouseEvent event) {
        if (event.getSource().equals(txtTenNV)) {
            if(!txtTenNV.getText().matches("^[a-zA-Z_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Tên phải có dấu và không được bỏ trống");
                alert.showAndWait();

                txtTenNV.setStyle("-fx-border-color:#e04040;");
            } else {
                txtTenNV.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtNamSinh)) {
//            if(txtNamSinh.getValue() == null) {
////                Alert alert = new Alert(Alert.AlertType.ERROR);
////                alert.setTitle("Error");
//                alert.setHeaderText(null);
////                alert.setContentText("Năm sinh không được bỏ trống");
////                alert.showAndWait();
//
//                txtNamSinh.setStyle("-fx-border-color: #e04040;");
//            } else {
//                txtNamSinh.setStyle("-fx-border-color: #fff;");
//            }
        }  else if (event.getSource().equals(txtNgayVaoLam)) {
//            if(txtNgayVaoLam.getValue() == null) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText(null);
//                alert.setContentText("Ngày vào làm không được bỏ trống");
//                alert.showAndWait();
//
//                txtNgayVaoLam.setStyle("-fx-border-color: #e04040;");
//            } else {
//                txtNgayVaoLam.setStyle("-fx-border-color: #fff;");
//            }
        }  else if (event.getSource().equals(txtDiaChi)) {
            //Kiểm tra Text Địa chỉ
            if(!txtDiaChi.getText().matches("^[a-zA-Z0-9_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Địa chỉ không được bỏ trống");
                alert.showAndWait();

                txtDiaChi.setStyle("-fx-border-color:#e04040;");
            } else {
                txtDiaChi.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtCMND)) {
            if (!txtCMND.getText().matches("^[0-9]{12}$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Số chứng minh phải gồm 12 chữ số và không được bỏ trống");
                alert.showAndWait();

                txtCMND.setStyle("-fx-border-color:#e04040;");
            } else {
                txtCMND.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtSDT)) {
            //Kiểm tra Text Số điện thoại
            if(!txtSDT.getText().matches("^\\d{3}[- .]?(\\d{4}){2}$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Số điện thoại phải là số có 11 chữ số và không được bỏ trống");
                alert.showAndWait();

                txtSDT.setStyle("-fx-border-color:#e04040;");
            } else {
                txtSDT.setStyle("-fx-border-color:#fff;");
            }
        }
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
        txtMaNV.setText(taoMaNV());
    }
}
