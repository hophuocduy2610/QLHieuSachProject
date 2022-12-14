package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.dao.KhachHangDAO;
import com.example.nhom10_qlhs.entities.KhachHang;

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
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
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
    private DatePicker txtNamSinh;

    @FXML
    private TextField txtTenKH;

    @FXML
    private TableView<KhachHang> tblKhachHang;
    @FXML
    private TableColumn<KhachHang, String> colCapNhat;

    @FXML
    private TableColumn<KhachHang, String> colDiaChi;

    @FXML
    private TableColumn<KhachHang, String> colEmail;

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
    private TableColumn<KhachHang, String> colXoa;

    @FXML
    private TextField txtTimKiem;
    ObservableList<KhachHang> khachHangObservableList = FXCollections.observableArrayList();
    @FXML
    private RadioButton radMaKH;

    @FXML
    private RadioButton radSDT;

    @FXML
    private RadioButton radTenKH;

    @FXML
    private TextField txtMaKH;

    @FXML
    private Label lblError;

    private KhachHangDAO khachHangDAO = new KhachHangDAO();

    Alert alert;

    //Hi???n th??? kh??ch h??ng l??n b???ng
    public void showKhachHangs(ObservableList<KhachHang> khachHangList) {
        colMaKH.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("maKH"));
        colTenKH.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("tenKH"));
        colNamSinh.setCellValueFactory(new PropertyValueFactory<KhachHang, Date>("namSinh"));
        colPhai.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("phai"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("diaChi"));
        colEmail.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("email"));
        colSDT.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("sdt"));
        colXoa.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("cbxXoa"));
        //Ph???n callback n??y ????? t???o ra button edit
        Callback<TableColumn<KhachHang, String>, TableCell<KhachHang, String>> cellFactory = param -> {
            //T???o ra TableCell ????? ch??a button
            final TableCell<KhachHang, String> editCell = new TableCell<KhachHang, String>() {
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
                            KhachHang kh = khachHangObservableList.get(tblKhachHang.getSelectionModel().getSelectedIndex());
                            if(txtNamSinh.getValue() != null){
                                khachHangDAO.capNhatThongTinKhachHang(kh.getMaKH(), txtTenKH.getText(),
                                        Date.valueOf(txtNamSinh.getValue()), cbxPhai.getValue(),
                                        txtDiaChi.getText(), txtEmail.getText(), txtSoDT.getText());
                            }else{
                                khachHangDAO.capNhatThongTinKhachHang(kh.getMaKH(), txtTenKH.getText(),
                                        kh.getNamSinh(), cbxPhai.getValue(),
                                        txtDiaChi.getText(), txtEmail.getText(), txtSoDT.getText());
                            }
                            khachHangObservableList.setAll(khachHangDAO.getDSKhachHangTheoTen(txtTenKH.getText()));
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
        tblKhachHang.getItems();//b???ng s??? l???y c??c item ??? tr??n
        tblKhachHang.setItems(khachHangList);//v?? set l??n b???ng v???i list kh??ch h??ng ????a v??
    }

    //T??m ki???m kh??ch h??ng
    public void timKiemKhachHang(){
        String searchKey = txtTimKiem.getText().toLowerCase();//L???y d??? li???u t??m ki???m v?? chuy???n v??? ch??? th?????ng
        List<KhachHang> khachHangList;
            if (radMaKH.isSelected()){//n???u radio maKH ???????c ch???n th??
                khachHangList = khachHangDAO.getDSKhachHangTheoMa(searchKey);//l???y danh s??ch kh??ch h??ng theo m??
                if(!khachHangList.isEmpty()){//N???u danh s??ch kh??ng r???ng th??
                    khachHangObservableList.setAll(khachHangList);//????a list kh??ch h??ng v?? khachHangObservableList
                    showKhachHangs(khachHangObservableList);//Hi???n th??? d??? li???u l??n b???ng
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Kh??ng t??m th???y kh??ch h??ng");
                    alert.showAndWait();
                }
                //M???y c??u if sau t????ng t??? nh?? radioMa
            }else if (radTenKH.isSelected()){
                khachHangList = khachHangDAO.getDSKhachHangTheoTen(searchKey);
                if(!khachHangList.isEmpty()){
                    khachHangObservableList.setAll(khachHangList);
                    showKhachHangs(khachHangObservableList);
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Kh??ng t??m th???y kh??ch h??ng");
                    alert.showAndWait();
                }
            } else if (radSDT.isSelected()) {
                khachHangList = khachHangDAO.getDSKhachHangTheoSDT(searchKey);
                if(!khachHangList.isEmpty()){
                    khachHangObservableList.setAll(khachHangList);
                    showKhachHangs(khachHangObservableList);
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Kh??ng t??m th???y kh??ch h??ng");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Kh??ng t??m th???y kh??ch h??ng");
                alert.showAndWait();
            }
    }

    //Hi???n form ?????a ch???
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

    //Th??m m???t kh??ch h??ng m???i
    public void themKhachHang(){

        if(txtTenKH.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("T??n kh??ch h??ng kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtNamSinh.getValue() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("N??m sinh kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtDiaChi.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("?????a ch??? kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtEmail.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Email kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        } else if (txtSoDT.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("S??? ??i???n tho???i kh??ng ???????c b??? tr???ng");
            alert.showAndWait();
            return;
        }

        if(lblError.getText() != "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Th??ng tin nh???p v???n c??n l???i, vui l??ng ki???m tra l???i th??ng tin v???a nh???p");
            alert.showAndWait();
            return;
        }

        GetData.trangThai = 1; //set tr???ng th??i cho kh??ch h??ng ????? d??ng trong l??c x??a
        boolean rs = khachHangDAO.themKhachHang(txtMaKH.getText(), txtTenKH.getText(), txtDiaChi.getText(), txtSoDT.getText(), txtEmail.getText(), cbxPhai.getValue(), txtNamSinh.getValue().toString(), GetData.trangThai);

       if(rs) {
           alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Message");
           alert.setHeaderText(null);
           alert.setContentText("Th??m th??nh c??ng");
           alert.showAndWait();
           KhachHang khachHang = new KhachHang(txtMaKH.getText(), txtTenKH.getText(), txtDiaChi.getText(), txtSoDT.getText(), txtEmail.getText(), cbxPhai.getValue(), Date.valueOf(txtNamSinh.getValue()));
           khachHangObservableList.setAll(khachHang);
           txtMaKH.setText(taoMaKH());
           clearTextField();
       } else {
           alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Th??m kh??ng th??nh c??ng");
           alert.showAndWait();
       }
    }


    //X??a kh??ch h??ng, x??a n??y th?? t chuy???n tr???ng th??i c???a kh??ch h??ng v?? khi truy v???n th?? s??? kh??ng g???i ???????c th???ng n??y l??n
    @FXML
    public void xoaKhachHang(ActionEvent event) {//Khi click chu???t v??o button x??a th?? ch???y h??m n??y
        ObservableList<KhachHang> dataListRemove = FXCollections.observableArrayList();//kh???i t???o m???t list s??? b??? x??a
        Iterator khachHangItem = khachHangObservableList.iterator();// l???y ra m???t t???p c??c kh??ch h??ng trong ObservableList c??n ObservableList l?? g?? th?? t ch???u =)))

        while(khachHangItem.hasNext()) {//Duy???t qua khachHangItem, n???u ??? v??? tr?? ti???p theo trong khachHangItem v???n c??n ph???n t???
            KhachHang khachHangTemp = (KhachHang) khachHangItem.next();//L???y gi?? tr??? t???i v??? tr?? next ???? ????a v??o bi???n t???m KhachHang
            if (khachHangTemp.getCbxXoa().isSelected()) {//N???u CheckBox ???????c ch???n th??
                GetData.trangThai = 0;//chuy???n tr???ng th??i v??? 0
                khachHangDAO.capNhatTrangThai(GetData.trangThai, khachHangTemp.getMaKH());//????a v?? h??m capNhatTrangThai
                dataListRemove.add(khachHangTemp);//????a kh??ch h??ng ???? b??? x??a v??o danh s??ch b??? x??a
            }
        }

        if(dataListRemove.size() == 0){//N???u danh s??ch b??? x??a kh??ng c?? th?? v???n ch??a c?? ph???n t??? n??o ???????c x??a
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Ch???n s???n ph???m c???n x??a");
            alert.showAndWait();
        }
        khachHangObservableList.removeAll(dataListRemove);//remove t???t c??? nh???ng Khach h??ng b??? x??a trong list ????
    }

    //T???o s??? kh??ch h??ng t??? ?????ng
    public String taoMaKH() {

        String maKH = "KH";
        int rowCount = khachHangDAO.demSoKhachHang();
        boolean dup = false;
        do {
            if (rowCount > 999998) {
                maKH = maKH + (rowCount + 1);
            }else if (rowCount > 99998) {
                maKH = maKH + "0" + (rowCount + 1);
            } else if (rowCount > 9998) {
                maKH = maKH + "00" + (rowCount + 1);
            } else if (rowCount > 998){
                maKH = maKH + "000" + (rowCount + 1);
            } else if (rowCount > 98) {
                maKH = maKH + "0000" + (rowCount + 1);
            } else if (rowCount > 8) {
                maKH = maKH + "00000" + (rowCount + 1);
            } else {
                maKH = maKH + "000000" + (rowCount + 1);
            }
        } while (dup);
        return maKH;
    }

    //Load th??ng tin tr??n b???ng l??n text field
    @FXML
    public void loadHangDuocChonLenTextField(MouseEvent mouseEvent){
        //L???y d??ng kh??ch h??ng ???????c ch???n trong b???ng v?? ????a l??n text field ????? th???c thi ch???nh s???a
        KhachHang kh = tblKhachHang.getItems().get(tblKhachHang.getSelectionModel().getSelectedIndex());
        txtTenKH.setText(kh.getTenKH());
        txtNamSinh.setValue(kh.getNamSinh().toLocalDate());
        cbxPhai.setValue(kh.getPhai());
        txtDiaChi.setText(kh.getDiaChi());
        txtEmail.setText(kh.getEmail());
        txtSoDT.setText(kh.getSdt());
    }

    public void clearTextField(){
        txtTenKH.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSoDT.setText("");
        showKhachHangsLenBang();
    }

    //Validation Data
    public void validata(KeyEvent event) {
        //Ki???m tra text T??n kh??ch h??ng
        if (event.getSource().equals(txtTenKH)) {
            if(!txtTenKH.getText().matches("^[a-zA-Z_??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\\ ]+$")) {
                lblError.setText("T??n ph???i kh??ng ???????c ch???a s??? v?? k?? t??? ?????c bi???t");
                txtTenKH.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtTenKH.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtEmail)) {
            //Ki???m tra Text Email
            if(!txtEmail.getText().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
                    "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
               lblError.setText("Email theo m???u username@domain.com");
                txtEmail.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtEmail.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtSoDT)) {
            //Ki???m tra Text S??? ??i???n tho???i
            if(!txtSoDT.getText().matches("^\\d{3}[- .]?(\\d{4}){2}$")) {
               lblError.setText("S??? ??i???n tho???i ph???i l?? s??? c?? 11 ch??? s???");
                txtSoDT.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtSoDT.setStyle("-fx-border-color:#fff;");
            }
        }
    }

    public void showKhachHangsLenBang() {
        List<KhachHang> khachHangs = khachHangDAO.getAllKhachHang();
        khachHangObservableList.setAll(khachHangs);
        showKhachHangs(khachHangObservableList);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxPhai.setItems(FXCollections.observableArrayList("Nam", "N???", "Kh??c"));
        radMaKH.setSelected(true);
        txtMaKH.setText(taoMaKH());
        showKhachHangsLenBang();
    }
}
