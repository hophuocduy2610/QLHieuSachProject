package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.dao.*;
import com.example.nhom10_qlhs.entities.NhanVien;
import com.example.nhom10_qlhs.entities.Sach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class QuanLySachController implements Initializable {
    @FXML
    private Button btnThemAnh;

    @FXML
    private ComboBox<String> cbxLoaiSach;

    @FXML
    private ComboBox<String> cbxNhaXuatBan;

    @FXML
    private ComboBox<Double> cbxVAT;

    @FXML
    private ComboBox<String> cbxNCC;
    @FXML
    private DatePicker datePickerNgayNhap;

    @FXML
    private ImageView imvAnhSach;

    @FXML
    private ToggleGroup timkiem;

    @FXML
    private TextField txtGiaBan;

    @FXML
    private TextField txtGiaNhap;

    @FXML
    private TextField txtNamXuatBan;

    @FXML
    private TextField txtSoLuong;

    @FXML
    private TextField txtTacGia;

    @FXML
    private TextField txtTenSach;

    @FXML
    private TableColumn<Sach, String> colCapNhat;

    @FXML
    private TableColumn<Sach, Double> colGiaBan;

    @FXML
    private TableColumn<Sach, Double> colGiaNhap;

    @FXML
    private TableColumn<Sach, String> colLoaiSach;

    @FXML
    private TableColumn<Sach, String> colMaSach;

    @FXML
    private TableColumn<Sach, Integer> colNamXuatBan;

    @FXML
    private TableColumn<Sach, Date> colNgayNhap;

    @FXML
    private TableColumn<Sach, String> colNhaCungCap;

    @FXML
    private TableColumn<Sach, String> colNhaXuatBan;

    @FXML
    private TableColumn<Sach, Integer> colSoLuong;

    @FXML
    private TableColumn<Sach, String> colTacGia;

    @FXML
    private TableColumn<Sach, String> colTenSach;

    @FXML
    private TableColumn<Sach,String> colXoa;


    @FXML
    private TableColumn<Sach, Double> colVAT;

    @FXML
    private TableView<Sach> tblSach;

    @FXML
    private TextField txtTimKiem;

    @FXML
    private RadioButton radMaSach;

    @FXML
    private RadioButton radTacGia;

    @FXML
    private RadioButton radTenSach;

    @FXML
    private TextField txtMaS;

    @FXML
    private Label lblError;


    private SachDAO sachDAO = new SachDAO();
    private NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
    private LoaiSDAO loaiSDAO = new LoaiSDAO();

    private NhaXuatBanDAO nhaXuatBanDAO = new NhaXuatBanDAO();

    private TacGiaDAO tacGiaDAO = new TacGiaDAO();


    private ObservableList<Sach> sachObservableList = FXCollections.observableArrayList();

    Alert alert;



    //Hi???n th??? nh??n vi??n l??n b???ng
    public void showSachs(ObservableList<Sach> saches) {
        colMaSach.setCellValueFactory(new PropertyValueFactory<Sach, String>("maSach"));
        colTenSach.setCellValueFactory(new PropertyValueFactory<Sach, String>("tenSach"));
        colNgayNhap.setCellValueFactory(new PropertyValueFactory<Sach, Date>("ngayNhap"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<Sach, Integer>("soLuong"));
        colNhaXuatBan.setCellValueFactory(new PropertyValueFactory<Sach, String>("nhaXuatBan"));
        colNhaCungCap.setCellValueFactory(new PropertyValueFactory<Sach, String>("nhaCungCap"));
        colTacGia.setCellValueFactory(new PropertyValueFactory<Sach, String>("tacGia"));
        colLoaiSach.setCellValueFactory(new PropertyValueFactory<Sach, String>("loaiSach"));
        colNamXuatBan.setCellValueFactory(new PropertyValueFactory<Sach, Integer>("namXuatBan"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<Sach, Double>("giaNhap"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<Sach, Double>("giaBan"));
        colVAT.setCellValueFactory(new PropertyValueFactory<Sach, Double>("VAT"));
        colXoa.setCellValueFactory(new PropertyValueFactory<Sach, String>("checkBox"));
        //Ph???n callback n??y ????? t???o ra button edit
        Callback<TableColumn<Sach, String>, TableCell<Sach, String>> cellFactory = param -> {
            //T???o ra TableCell ????? ch??a button
            final TableCell<Sach, String> editCell = new TableCell<Sach, String>() {
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
                        editButton.setOnAction(event -> {
                            Sach sach = sachObservableList.get(tblSach.getSelectionModel().getSelectedIndex());
                            sachDAO.capNhatThongTinSach(sach.getMaSach(), txtTenSach.getText(),
                                   Integer.parseInt(txtSoLuong.getText()), cbxNhaXuatBan.getValue(),
                                    cbxNCC.getValue(), txtTacGia.getText(),
                                    cbxLoaiSach.getValue(), Integer.parseInt(txtNamXuatBan.getText()),
                                    Double.parseDouble(txtGiaNhap.getText()), Double.parseDouble(txtGiaBan.getText()));
                            sachObservableList.setAll(sachDAO.getDSSachTheoTenSach(txtTenSach.getText()));
                            clearAll();
                        });
                        setGraphic(editButton);
                        setText(null);
                    }
                }
            };
            return editCell;
        };
        colCapNhat.setCellFactory(cellFactory);//????a ?? ch???a button edit v??o c???t c???p nh???t
        tblSach.getItems();//b???ng s??? l???y c??c item ??? tr??n
        tblSach.setItems(saches);//v?? set l??n b???ng v???i list kh??ch h??ng ????a v??
    }

    //T??m ki???m S??ch
    public void timKiemSach(){
        String searchKey = txtTimKiem.getText().toLowerCase();//L???y d??? li???u t??m ki???m v?? chuy???n v??? ch??? th?????ng
        List<Sach> sachList;
        if (radMaSach.isSelected()){
            sachList = sachDAO.getDSSachTheoMa(searchKey);
            if(!sachList.isEmpty()){
                sachObservableList.setAll(sachList);
                showSachs(sachObservableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Kh??ng t??m th???y th??ng tin s??ch");
                alert.showAndWait();
            }
            //M???y c??u if sau t????ng t??? nh?? radioMa
        }else if (radTenSach.isSelected()){
            sachList = sachDAO.getDSSachTheoTenSach(searchKey);
            if(!sachList.isEmpty()){
                sachObservableList.setAll(sachList);
                showSachs(sachObservableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Kh??ng t??m th???y th??ng tin s??ch");
                alert.showAndWait();
            }
        } else if (radTacGia.isSelected()) {
            sachList = sachDAO.getDSSachTheoTacGia(searchKey);
            if(!sachList.isEmpty()){
                sachObservableList.setAll(sachList);
                showSachs(sachObservableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Kh??ng t??m th???y th??ng tin s??ch");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Kh??ng t??m th???y th??ng tin s??ch");
            alert.showAndWait();
        }
    }

    //Th??m m???t s??ch m???i
    public void themSach(){

        if (datePickerNgayNhap.getValue() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ng??y nh???p kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtSoLuong.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("S??? l?????ng kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtTacGia.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("T??n t??c gi??? kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtTenSach.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("T??n s??ch kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtGiaNhap.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Gi?? nh???p kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtNamXuatBan.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("N??m xu???t b???n kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (imvAnhSach.getImage() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("???nh s??ch c??n tr???ng");
            alert.showAndWait();
            return;
        }

        if(LocalDate.now().compareTo(datePickerNgayNhap.getValue()) < 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ng??y nh???p kh??ng ???????c tr?????c ng??y hi???n t???i");
            alert.showAndWait();
            return;
        }

        if (Integer.valueOf(txtSoLuong.getText()) < 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("S??? l?????ng ph???i l?? s??? l???n h??n 0");
            alert.showAndWait();
            return;
        }

        if (Double.valueOf(txtGiaNhap.getText()) < 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Gi?? nh???p ph???i l?? s??? th???c l???n h??n 0");
            alert.showAndWait();
            return;
        }

        if (lblError.getText() != "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Th??ng tin nh???p v???n c??n l???i, vui l??ng ki???m tra l???i th??ng tin v???a nh???p");
            alert.showAndWait();
            return;
        }

        GetData.trangThai = 1;
        GetData.trangThaiButton = "btnThemNV";
        String loaiS = loaiSDAO.getMaLoaiSachTheoTenLoai(cbxLoaiSach.getValue());
        String nhaXuatBan = nhaXuatBanDAO.getMaNXBTheoTenNXB(cbxNhaXuatBan.getValue());
        String nhaCungCap = nhaCungCapDAO.getMaNCCTheoTen(cbxNCC.getValue());
        String tacGia = tacGiaDAO.getMaTGTheoTenTG(txtTacGia.getText());
        boolean result = sachDAO.themSach(txtMaS.getText(), txtTenSach.getText(), Integer.valueOf(txtSoLuong.getText()), Double.valueOf(txtGiaNhap.getText()), nhaXuatBan,
                Integer.valueOf(txtNamXuatBan.getText()), tacGia, loaiS, nhaCungCap, Date.valueOf(datePickerNgayNhap.getValue()),
                Double.valueOf(txtGiaBan.getText()), cbxVAT.getValue(), GetData.linkAnhSach, GetData.trangThai);

            if(result){ //N???u th???c thi th??nh c??ng th?? xu???t th??ng b??o
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Th??m th??nh c??ng");
                alert.showAndWait();
                Sach sach = new Sach(txtMaS.getText(), txtTenSach.getText(), Integer.valueOf(txtSoLuong.getText()), Double.valueOf(txtGiaNhap.getText()), cbxNhaXuatBan.getValue(),
                        Integer.valueOf(txtNamXuatBan.getText()), txtTacGia.getText(), cbxLoaiSach.getValue(), cbxNCC.getValue(), Date.valueOf(datePickerNgayNhap.getValue()),
                        Double.valueOf(txtGiaBan.getText()), cbxVAT.getValue(),GetData.linkAnhSach);
                sachObservableList.setAll(sach);
                txtMaS.setText(taoMaSach());
                showSachs(sachObservableList);
                clearAll();
            }else { //Sai th?? xu???t l???i
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Th??m kh??ng th??nh c??ng");
                alert.showAndWait();
            }
    }

    //Import ???nh s??ch
    public void importAnhSach(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image file", "*png", "*jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            Image image = new Image(file.toURI().toString());
            imvAnhSach.setImage(image);
            GetData.linkAnhSach = file.getAbsolutePath();
        }
    }


    //X??a s??ch
    @FXML
    public void xoaNhanVien(ActionEvent event) {
        ObservableList<Sach> dataListRemove = FXCollections.observableArrayList();
        Iterator sachIterator = sachObservableList.iterator();

        while(sachIterator.hasNext()) {
            Sach sachTemp = (Sach) sachIterator.next();
            if (sachTemp.getCheckBox().isSelected()) {
                GetData.trangThai = 0;
                sachDAO.capNhatTrangThai(GetData.trangThai, sachTemp.getMaSach());
                dataListRemove.add(sachTemp);
            }
        }

        if(dataListRemove.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Ch???n s???n ph???m c???n x??a");
            alert.showAndWait();
        }
        sachObservableList.removeAll(dataListRemove);//remove t???t c??? s??ch b??? x??a trong list ????
    }


    public void clearAll(){
        txtTenSach.setText("");
        datePickerNgayNhap.setValue(null);
        txtSoLuong.setText("");
        cbxLoaiSach.getSelectionModel().selectFirst();
        txtTacGia.setText("");
        txtGiaNhap.setText("");
        txtGiaBan.setText("");
        cbxNhaXuatBan.getSelectionModel().selectFirst();
        txtNamXuatBan.setText("");
        txtTimKiem.setText("");
        txtNamXuatBan.setText("");
        radMaSach.setSelected(true);
        cbxVAT.getSelectionModel().selectFirst();
        imvAnhSach.setImage(null);
        showSachsLenBang();
    }
    //Load th??ng tin tr??n b???ng l??n text field
    @FXML
    public void loadHangDuocChonLenTextField(MouseEvent mouseEvent){
        //L???y d??ng kh??ch h??ng ???????c ch???n trong b???ng v?? ????a l??n text field ????? th???c thi ch???nh s???a
        Sach sach = tblSach.getItems().get(tblSach.getSelectionModel().getSelectedIndex());
        datePickerNgayNhap.setValue(sach.getNgayNhap().toLocalDate());
        txtSoLuong.setText(String.valueOf(sach.getSoLuong()));
        cbxLoaiSach.setValue(loaiSDAO.getTenLoaiSachTheoMaLoai(sach.getLoaiSach()));
        txtTacGia.setText(tacGiaDAO.getTenTGTheoMaTG(sach.getTacGia()));
        cbxVAT.setValue(sach.getVAT());
        txtTenSach.setText(sach.getTenSach());
        txtGiaNhap.setText(String.valueOf(sach.getGiaNhap()));
        txtGiaBan.setText(String.valueOf(sach.getGiaBan()));
        cbxNhaXuatBan.setValue(nhaXuatBanDAO.getTenNXBTheoMaNXB(sach.getNhaXuatBan()));
        txtNamXuatBan.setText(String.valueOf(sach.getNamXuatBan()));
        cbxNCC.setValue(nhaCungCapDAO.getTenNCCTheoMa(sach.getNhaCungCap()));

    }
    //Load nh?? xu???t b???n
    public void loadNhaXuatBan(){
        cbxNhaXuatBan.setItems(sachDAO.getNhaXuatBan());
    }
    //Load lo???i s??ch
    public void loadLoaiSach(){
        cbxLoaiSach.setItems(loaiSDAO.getLoaiSach());
    }

    //Load nh?? cung c???p
    public void loadNhaCungCap(){
        cbxNCC.setItems(nhaCungCapDAO.getNhaCungCap());
    }

    public Double tinhGiaBan(Double giaNhap){
        Double giaBan = 0.0;
        if (cbxVAT.getValue() == 0.0) {
            giaBan = ((giaNhap) / (100-50))*100;
        } else if (cbxVAT.getValue() == 0.05) {
            giaBan = (((giaNhap) / (100-50))*100)*1.05;
        }
        return giaBan;
    }

    public void hienGiaBan(KeyEvent keyEvent){
        Double giaBan = tinhGiaBan(Double.valueOf(txtGiaNhap.getText()));
        txtGiaBan.setText(String.valueOf(Math.round(giaBan)));
    }

    //T???o m?? s??ch t??? ?????ng
    public String taoMaSach() {

        String maSach = "S";
        int rowCount = sachDAO.demSoSach();
        boolean dup = false;
        do {
            if (rowCount > 999998) {
                maSach = maSach + (rowCount + 1);
            }else if (rowCount > 99998) {
                maSach = maSach + "0" + (rowCount + 1);
            } else if (rowCount > 9998) {
                maSach = maSach + "00" + (rowCount + 1);
            } else if (rowCount > 998){
                maSach = maSach + "000" + (rowCount + 1);
            } else if (rowCount > 98) {
                maSach = maSach + "0000" + (rowCount + 1);
            } else if (rowCount > 8) {
                maSach = maSach + "00000" + (rowCount + 1);
            } else {
                maSach = maSach + "000000" + (rowCount + 1);
            }
        } while (dup);
        return maSach;
    }
    public void validData (KeyEvent event) {
        if (event.getSource().equals(txtSoLuong)) {
            if(!txtSoLuong.getText().matches("^[0-9]+$")) {
                lblError.setText("S??? l?????ng ph???i l?? s???");
                txtSoLuong.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtSoLuong.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtTacGia)) {
            if(!txtTacGia.getText().matches("^[a-zA-Z_??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\\ ]+$")) {
                lblError.setText("T??n t??c gi??? kh??ng ch???a k?? t??? ?????c bi???t v?? s???");
                txtTacGia.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtTacGia.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtTenSach)) {
            if(!txtTenSach.getText().matches("^[a-zA-Z0-9_??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\\ ]+$")) {
                lblError.setText("T??n s??ch kh??ng ch???a k?? t??? ?????c bi???t");
               txtTenSach.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtTenSach.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtGiaNhap)) {
            if(!txtGiaNhap.getText().matches("[0-9]{1,13}(\\.[0-9]*)?$")) {
                lblError.setText("Gi?? nh???p ph???i l?? s??? th???c l???n h??n 0");
                txtGiaNhap.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtGiaNhap.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtNamXuatBan)) {
            if(!txtNamXuatBan.getText().matches("^[0-9]{4}$")) {
                lblError.setText("N??m xu???t b???n ph???i l?? s??? c?? 4 ch??? s???");
                txtNamXuatBan.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtNamXuatBan.setStyle("-fx-border-color:#fff;");
            }
        }
    }

    public void showSachsLenBang() {
        List<Sach> saches = sachDAO.getDSSach();
        sachObservableList.setAll(saches);
        showSachs(sachObservableList);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Double> vats = FXCollections.observableArrayList();
        List vatList = new ArrayList();
        vatList.add(0.0);
        vatList.add(0.05);
        vats.addAll(vatList);
        cbxVAT.setItems(vats);
        cbxVAT.getSelectionModel().selectFirst();
        loadNhaXuatBan();
        loadLoaiSach();
        loadNhaCungCap();
        cbxLoaiSach.getSelectionModel().selectFirst();
        cbxNCC.getSelectionModel().selectFirst();
        cbxNhaXuatBan.getSelectionModel().selectFirst();
        txtMaS.setText(taoMaSach());
        radMaSach.setSelected(true);
        showSachsLenBang();
    }
}
