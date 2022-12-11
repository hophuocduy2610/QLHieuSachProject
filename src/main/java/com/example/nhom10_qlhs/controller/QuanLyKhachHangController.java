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

    //Hiển thị khách hàng lên bảng
    public void showKhachHangs(ObservableList<KhachHang> khachHangList) {
        colMaKH.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("maKH"));
        colTenKH.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("tenKH"));
        colNamSinh.setCellValueFactory(new PropertyValueFactory<KhachHang, Date>("namSinh"));
        colPhai.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("phai"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("diaChi"));
        colEmail.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("email"));
        colSDT.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("sdt"));
        colXoa.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("cbxXoa"));
        //Phần callback này để tạo ra button edit
        Callback<TableColumn<KhachHang, String>, TableCell<KhachHang, String>> cellFactory = param -> {
            //Tạo ra TableCell để chưa button
            final TableCell<KhachHang, String> editCell = new TableCell<KhachHang, String>() {
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
        colCapNhat.setCellFactory(cellFactory);//Đưa ô chứa button edit vào cột cập nhật
        tblKhachHang.getItems();//bảng sẽ lấy các item ở trên
        tblKhachHang.setItems(khachHangList);//và set lên bảng với list khách hàng đưa vô
    }

    //Tìm kiếm khách hàng
    public void timKiemKhachHang(){
        String searchKey = txtTimKiem.getText().toLowerCase();//Lấy dữ liệu tìm kiếm và chuyển về chữ thường
        List<KhachHang> khachHangList;
            if (radMaKH.isSelected()){//nếu radio maKH được chọn thì
                khachHangList = khachHangDAO.getDSKhachHangTheoMa(searchKey);//lấy danh sách khách hàng theo mã
                if(!khachHangList.isEmpty()){//Nếu danh sách không rỗng thì
                    khachHangObservableList.setAll(khachHangList);//Đưa list khách hàng vô khachHangObservableList
                    showKhachHangs(khachHangObservableList);//Hiển thị dữ liệu lên bảng
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy khách hàng");
                    alert.showAndWait();
                }
                //Mấy câu if sau tương tự như radioMa
            }else if (radTenKH.isSelected()){
                khachHangList = khachHangDAO.getDSKhachHangTheoTen(searchKey);
                if(!khachHangList.isEmpty()){
                    khachHangObservableList.setAll(khachHangList);
                    showKhachHangs(khachHangObservableList);
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy khách hàng");
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
                    alert.setContentText("Không tìm thấy khách hàng");
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

    //Thêm một khách hàng mới
    public void themKhachHang(){

        if(txtTenKH.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tên khách hàng không được bỏ trống");
            alert.showAndWait();
            return;
        } else if (txtNamSinh.getValue() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Năm sinh không được bỏ trống");
            alert.showAndWait();
            return;
        } else if (txtDiaChi.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Địa chỉ không được bỏ trống");
            alert.showAndWait();
            return;
        } else if (txtEmail.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Email không được bỏ trống");
            alert.showAndWait();
            return;
        } else if (txtSoDT.getText() == "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Số điện thoại không được bỏ trống");
            alert.showAndWait();
            return;
        }

        if(lblError.getText() != "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Thông tin nhập vẫn còn lỗi, vui lòng kiểm tra lại thông tin vừa nhập");
            alert.showAndWait();
            return;
        }

        GetData.trangThai = 1; //set trạng thái cho khách hàng để dùng trong lúc xóa
        boolean rs = khachHangDAO.themKhachHang(txtMaKH.getText(), txtTenKH.getText(), txtDiaChi.getText(), txtSoDT.getText(), txtEmail.getText(), cbxPhai.getValue(), txtNamSinh.getValue().toString(), GetData.trangThai);

       if(rs) {
           alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Message");
           alert.setHeaderText(null);
           alert.setContentText("Thêm thành công");
           alert.showAndWait();
       } else {
           alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Thêm không thành công");
           alert.showAndWait();
       }
    }


    //Xóa khách hàng, xóa này thì t chuyển trạng thái của khách hàng và khi truy vấn thì sẽ không gọi được thằng này lên
    @FXML
    public void xoaKhachHang(ActionEvent event) {//Khi click chuột vào button xóa thì chạy hàm này
        ObservableList<KhachHang> dataListRemove = FXCollections.observableArrayList();//khởi tạo một list sẽ bị xóa
        Iterator khachHangItem = khachHangObservableList.iterator();// lấy ra một tập các khách hàng trong ObservableList còn ObservableList là gì thì t chịu =)))

        while(khachHangItem.hasNext()) {//Duyệt qua khachHangItem, nếu ở vị trí tiếp theo trong khachHangItem vẫn còn phần tử
            KhachHang khachHangTemp = (KhachHang) khachHangItem.next();//Lấy giá trị tại vị trí next đó đưa vào biến tạm KhachHang
            if (khachHangTemp.getCbxXoa().isSelected()) {//Nếu CheckBox được chọn thì
                GetData.trangThai = 0;//chuyển trạng thái về 0
                khachHangDAO.capNhatTrangThai(GetData.trangThai, khachHangTemp.getMaKH());//đưa vô hàm capNhatTrangThai
                dataListRemove.add(khachHangTemp);//Đưa khách hàng đã bị xóa vào danh sách bị xóa
            }
        }

        if(dataListRemove.size() == 0){//Nếu danh sách bị xóa không có thì vẫn chưa có phần tử nào được xóa
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Chọn sản phẩm cần xóa");
            alert.showAndWait();
        }
        khachHangObservableList.removeAll(dataListRemove);//remove tất cả những Khach hàng bị xóa trong list đó
    }

    //Tạo số khách hàng tự động
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

    //Load thông tin trên bảng lên text field
    @FXML
    public void loadHangDuocChonLenTextField(MouseEvent mouseEvent){
        //Lấy dòng khách hàng được chọn trong bảng và đưa lên text field để thực thi chỉnh sửa
        KhachHang kh = tblKhachHang.getItems().get(tblKhachHang.getSelectionModel().getSelectedIndex());
        txtTenKH.setText(kh.getTenKH());
        txtNamSinh.setValue(kh.getNamSinh().toLocalDate());
        cbxPhai.setValue(kh.getPhai());
        txtDiaChi.setText(kh.getDiaChi());
        txtEmail.setText(kh.getEmail());
        txtSoDT.setText(kh.getSdt());
    }

    public void clearTextField(){
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSoDT.setText("");
    }

    //Validation Data
    public void validata(KeyEvent event) {
        //Kiểm tra text Tên khách hàng
        if (event.getSource().equals(txtTenKH)) {
            if(!txtTenKH.getText().matches("^[a-zA-Z_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                lblError.setText("Tên phải không được chứa số và kí tự đặc biệt");
                txtTenKH.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtTenKH.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtEmail)) {
            //Kiểm tra Text Email
            if(!txtEmail.getText().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
                    "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
               lblError.setText("Email theo mẫu username@domain.com");
                txtEmail.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtEmail.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtSoDT)) {
            //Kiểm tra Text Số điện thoại
            if(!txtSoDT.getText().matches("^\\d{3}[- .]?(\\d{4}){2}$")) {
               lblError.setText("Số điện thoại phải là số có 11 chữ số");
                txtSoDT.setStyle("-fx-border-color:#e04040;");
            } else {
                lblError.setText("");
                txtSoDT.setStyle("-fx-border-color:#fff;");
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxPhai.setItems(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
        radMaKH.setSelected(true);
        txtMaKH.setText(taoMaKH());
        List<KhachHang> khachHangs = khachHangDAO.getAllKhachHang();
        ObservableList<KhachHang> khachHangObservableListTemp = FXCollections.observableArrayList();
        khachHangObservableListTemp.addAll(khachHangs);
        showKhachHangs(khachHangObservableListTemp);
    }
}
