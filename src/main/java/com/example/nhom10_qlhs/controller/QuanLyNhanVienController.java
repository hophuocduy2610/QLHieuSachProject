package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.dao.LoaiSDAO;
import com.example.nhom10_qlhs.dao.NhaCungCapDAO;
import com.example.nhom10_qlhs.dao.NhaXuatBanDAO;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
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

    @FXML
    private Label lblError;

    private ObservableList<NhanVien> nhanVienObservableList = FXCollections.observableArrayList();

    private NhanVienDAO nhanVienDAO = new NhanVienDAO();

    //Hi???n th??? nh??n vi??n l??n b???ng
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
        //Ph???n callback n??y ????? t???o ra button edit
        Callback<TableColumn<NhanVien, String>, TableCell<NhanVien, String>> cellFactory = param -> {
            //T???o ra TableCell ????? ch??a button
            final TableCell<NhanVien, String> editCell = new TableCell<NhanVien, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //Ki???m tra cell v???a t???o kh??ng ph???i t??? c???t r???ng
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }else {
                        ImageView imgView = new ImageView("D:/PTUDBTLon/src/main/java/com/example/nhom10_qlhs/image/edit-icon.png");
                        imgView.setFitWidth(18);
                        imgView.setFitHeight(18);
                        //T???o action cho button edit
                        final Button editButton = new Button("Edit",imgView);
                        editButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        //C???p nh???t Nh??n vi??n (X??? l?? s??? ki???n c???p nh???t)
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
        colCapNhat.setCellFactory(cellFactory);//????a ?? ch???a button edit v??o c???t c???p nh???t
        tblNhanVien.getItems();//b???ng s??? l???y c??c item ??? tr??n
        tblNhanVien.setItems(nhanViens);//v?? set l??n b???ng v???i list kh??ch h??ng ????a v??
    }

    //T??m ki???m nh??n vi??n
    public void timKiemNhanVien(){
        String searchKey = txtTimKiem.getText().toLowerCase();//L???y d??? li???u t??m ki???m v?? chuy???n v??? ch??? th?????ng
        List<NhanVien> nhanVienList;
        if (radMaNV.isSelected()){//n???u radio maNV ???????c ch???n th??
            nhanVienList = nhanVienDAO.getDSNhanVienTheoMa(searchKey);//l???y danh s??ch kh??ch h??ng theo m??
            if(!nhanVienList.isEmpty()){//N???u danh s??ch kh??ng r???ng th??
                nhanVienObservableList.setAll(nhanVienList);//????a list kh??ch h??ng v?? khachHangObservableList
                showNhanViens(nhanVienObservableList);//Hi???n th??? d??? li???u l??n b???ng
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Kh??ng t??m th???y th??ng tin nh??n vi??n");
                alert.showAndWait();
            }
            //M???y c??u if sau t????ng t??? nh?? radioMa
        }else if (radTenNV.isSelected()){
            nhanVienList = nhanVienDAO.getDSNhanVienTheoTen(searchKey);
            if(!nhanVienList.isEmpty()){
                nhanVienObservableList.setAll(nhanVienList);
                showNhanViens(nhanVienObservableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Kh??ng t??m th???y th??ng tin nh??n vi??n");
                alert.showAndWait();
            }
        } else if (radSdtNV.isSelected()) {
            nhanVienList = nhanVienDAO.getDSNhanVienTheoSDT(searchKey);
            if(!nhanVienList.isEmpty()){
                nhanVienObservableList.setAll(nhanVienList);
                showNhanViens(nhanVienObservableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Kh??ng t??m th???y th??ng tin nh??n vi??n");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Kh??ng t??m th???y th??ng tin nh??n vi??n");
            alert.showAndWait();
        }
    }

    //Th??m m???t nh??n vi??n m???i
    public void themNhanVien(){
        //Ki???m tra r???ng
        if(txtTenNV.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("T??n nh??n vi??n kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtNamSinh.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("N??m sinh kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtNgayVaoLam.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ng??y v??o l??m kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtDiaChi.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("?????a ch??? kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtCMND.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("CMND kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtSDT.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("S??? ??i???n tho???i kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        }

        if(lblError.getText() != "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Th??ng tin nh???p v???n c??n l???i, vui l??ng ki???m tra l???i th??ng tin v???a nh???p");
            alert.showAndWait();
            return;
        }

        if(LocalDate.now().getYear() - txtNamSinh.getValue().getYear() < 15) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("S??? tu???i c???a nh??n vi??n ph???i l???n h??n 15, vui l??ng ki???m tra l???i n??m sinh");
            alert.showAndWait();
            return;
        }
        if(LocalDate.now().compareTo(txtNgayVaoLam.getValue()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ng??y v??o l??m ph???i kh??ng ???????c tr?????c ng??y hi???n t???i");
            alert.showAndWait();
            return;
        }
        boolean result = nhanVienDAO.themNhanVien(txtMaNV.getText(), txtTenNV.getText(), txtDiaChi.getText(), txtSDT.getText(), Date.valueOf(txtNgayVaoLam.getValue()), cbxPhai.getValue(), Date.valueOf(txtNamSinh.getValue()), cbxChucVu.getValue(), txtCMND.getText());
        if(result){ //N???u th???c thi th??nh c??ng th?? xu???t th??ng b??o
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Th??m th??nh c??ng");
                alert.showAndWait();
                taoTuDongTenTaiKhoan(GetData.trangThaiButton, txtSDT.getText());
                GetData.taiKhoan = txtTaiKhoan.getText();
                GetData.chucVu = cbxChucVu.getValue();
                GetData.maNV = txtMaNV.getText();

                NhanVien nhanVienTemp = new NhanVien(txtMaNV.getText(), txtTenNV.getText(), txtDiaChi.getText(),
                        Date.valueOf(txtNamSinh.getValue()), txtSDT.getText(), txtCMND.getText(), cbxPhai.getValue(), cbxChucVu.getValue(), Date.valueOf(txtNgayVaoLam.getValue()));
                nhanVienObservableList.setAll(nhanVienTemp);
                showNhanViens(nhanVienObservableList);
                txtMaNV.setText(taoMaNV());
                clearTextField();
            }else { //Sai th?? xu???t l???i
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Th??m kh??ng th??nh c??ng");
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

    //X??a nh??n vi??n
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
            alert.setContentText("Ch???n s???n ph???m c???n x??a");
            alert.showAndWait();
        }
        nhanVienObservableList.removeAll(dataListRemove);//remove t???t c??? nh???ng Khach h??ng b??? x??a trong list ????
    }



    //Load th??ng tin tr??n b???ng l??n text field
    @FXML
    public void loadHangDuocChonLenTextField(MouseEvent mouseEvent){
        //L???y d??ng kh??ch h??ng ???????c ch???n trong b???ng v?? ????a l??n text field ????? th???c thi ch???nh s???a
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

    //Hi???n form ?????a ch???
    public void hienFormThemDiaChi() throws IOException {
        URL url = new File("target/classes/com/example/nhom10_qlhs/them-dia-chi-gui.fxml").toURI().toURL();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();

        if(!stage.isShowing()){
            txtDiaChi.setText(GetData.diaChi);
            GetData.trangThaiButton = "";
        }
    }

    //T???o m?? Nh??n vi??n t??? ?????ng
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
        stage.initStyle(StageStyle.DECORATED.UNDECORATED);
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
        cbxChucVu.setValue("Qu???n l??");
        txtNgayVaoLam.setValue(null);
        txtSDT.setText("");
        txtTimKiem.setText("");
        showNhanVienTheoNamVaoLamLenBang();
    }
    //L??m m???i
    public void clearTextField() {
        txtTenNV.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtCMND.setText("");
        cbxChucVu.setValue("Qu???n l??");
        txtNgayVaoLam.setValue(null);
        txtSDT.setText("");
    }

    public void validData(KeyEvent event) {
        if (event.getSource().equals(txtTenNV)) {
            if(!txtTenNV.getText().matches("^[a-zA-Z_??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\\ ]+$")) {
                lblError.setText("T??n nh??n vi??n kh??ng ???????c ch???a s??? v?? k?? t??? ?????c bi???t");
                txtTenNV.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtTenNV.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtCMND)) {
            if (!txtCMND.getText().matches("^[0-9]{12}$")) {
                lblError.setText("S??? ch???ng minh ph???i g???m 12 ch??? s???");
                txtCMND.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtCMND.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtSDT)) {
            //Ki???m tra Text S??? ??i???n tho???i
            if(!txtSDT.getText().matches("^\\d{3}[- .]?(\\d{4}){2}$")) {
                lblError.setText("S??? ??i???n tho???i ph???i l?? s??? c?? 11 ch??? s???");
                txtSDT.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtSDT.setStyle("-fx-border-color:#fff;");
            }
        }
    }

    public void showNhanVienTheoNamVaoLamLenBang() {
        List<NhanVien> nhanViens = nhanVienDAO.getDSNhanVienTheoNam(LocalDate.now().getYear());
        nhanVienObservableList.setAll(nhanViens);
        showNhanViens(nhanVienObservableList);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxPhai.setItems(FXCollections.observableArrayList("Nam", "N???", "Kh??c"));
        cbxPhai.getSelectionModel().selectFirst();
        cbxChucVu.setItems(FXCollections.observableArrayList("Qu???n l??", "Nh??n vi??n"));
        cbxChucVu.getSelectionModel().selectFirst();
        radMaNV.setSelected(true);
        txtTaiKhoan.setEditable(false);
        btnThemTK.setMouseTransparent(true);
        txtMaNV.setText(taoMaNV());
        showNhanVienTheoNamVaoLamLenBang();
    }
}
