package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.dao.LoaiSDAO;
import com.example.nhom10_qlhs.dao.NhaCungCapDAO;
import com.example.nhom10_qlhs.dao.SachDAO;
import com.example.nhom10_qlhs.entities.NhaXuatBan;
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

    private SachDAO sachDAO = new SachDAO();
    private NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
    private LoaiSDAO loaiSDAO = new LoaiSDAO();
    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Sach sach;
    private Alert alert;
    private ObservableList<Sach> sachObservableList = FXCollections.observableArrayList();



    //Hiển thị nhân viên lên bảng
    public void showSachs(ObservableList<Sach> saches) {
        colMaSach.setCellValueFactory(new PropertyValueFactory<Sach, String>("maSach"));
        colTenSach.setCellValueFactory(new PropertyValueFactory<Sach, String>("tenSach"));
        //colNgayNhap.setCellValueFactory(new PropertyValueFactory<Sach, Date>("namSinh"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<Sach, Integer>("soLuong"));
        colNhaXuatBan.setCellValueFactory(new PropertyValueFactory<Sach, String>("nhaXuatBan"));
        colNhaCungCap.setCellValueFactory(new PropertyValueFactory<Sach, String>("nhaCungCap"));
        colTacGia.setCellValueFactory(new PropertyValueFactory<Sach, String>("tacGia"));
        colLoaiSach.setCellValueFactory(new PropertyValueFactory<Sach, String>("loaiSach"));
        colNamXuatBan.setCellValueFactory(new PropertyValueFactory<Sach, Integer>("namXuatBan"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<Sach, Double>("giaNhap"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<Sach, Double>("giaBan"));
        colXoa.setCellValueFactory(new PropertyValueFactory<Sach, String>("checkBox"));
        //Phần callback này để tạo ra button edit
        Callback<TableColumn<Sach, String>, TableCell<Sach, String>> cellFactory = param -> {
            //Tạo ra TableCell để chưa button
            final TableCell<Sach, String> editCell = new TableCell<Sach, String>() {
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
                            Sach nv = sachObservableList.get(tblSach.getSelectionModel().getSelectedIndex());
                            sachDAO.capNhatThongTinSach(nv.getMaSach(), txtTenSach.getText(),
                                   Integer.parseInt(txtSoLuong.getText()), cbxNhaXuatBan.getValue(),
                                    cbxNCC.getValue(), txtTacGia.getText(),
                                    cbxLoaiSach.getValue(), Integer.parseInt(txtNamXuatBan.getText()),
                                    Double.parseDouble(txtGiaNhap.getText()), Double.parseDouble(txtGiaBan.getText()));
                        });
                        setGraphic(editButton);
                        setText(null);
                    }
                }
            };
            return editCell;
        };
        colCapNhat.setCellFactory(cellFactory);//Đưa ô chứa button edit vào cột cập nhật
        tblSach.getItems();//bảng sẽ lấy các item ở trên
        tblSach.setItems(saches);//và set lên bảng với list khách hàng đưa vô
    }

    //Tìm kiếm Sách
    public void timKiemSach(){
        String searchKey = txtTimKiem.getText().toLowerCase();//Lấy dữ liệu tìm kiếm và chuyển về chữ thường
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
                alert.setContentText("Không tìm thấy thông tin sách");
                alert.showAndWait();
            }
            //Mấy câu if sau tương tự như radioMa
        }else if (radTenSach.isSelected()){
            sachList = sachDAO.getDSSachTheoTenSach(searchKey);
            if(!sachList.isEmpty()){
                sachObservableList.setAll(sachList);
                showSachs(sachObservableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy thông tin sách");
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
                alert.setContentText("Không tìm thấy thông tin sách");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy thông tin sách");
            alert.showAndWait();
        }
    }

    //Thêm một sách mới
    public void themSach(){
        GetData.trangThai = 1;
        GetData.trangThaiButton = "btnThemNV";

        boolean result = sachDAO.themSach(txtMaS.getText(), txtTenSach.getText(), Integer.valueOf(txtSoLuong.getText()), Double.valueOf(txtGiaNhap.getText()), cbxNhaXuatBan.getValue(),
                Integer.valueOf(txtNamXuatBan.getText()), txtTacGia.getText(), cbxLoaiSach.getValue(), cbxNCC.getValue(),
                Double.valueOf(txtGiaBan.getText()), GetData.linkAnhSach, GetData.trangThai);

            if(result){ //Nếu thực thi thành công thì xuất thông báo
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thành công");
                alert.showAndWait();
            }else { //Sai thì xuất lỗi
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Thêm không thành công");
                alert.showAndWait();
            }
    }

    //Import ảnh sách
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


    //Xóa sách
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
            alert.setContentText("Chọn sản phẩm cần xóa");
            alert.showAndWait();
        }
        sachObservableList.removeAll(dataListRemove);//remove tất cả sách bị xóa trong list đó
    }


    public void clearAll(){
        txtTenSach.setText("");
        datePickerNgayNhap.setValue(null);
        txtSoLuong.setText("");
        cbxLoaiSach.setValue(null);
        txtTacGia.setText("");
        txtGiaNhap.setText("");
        txtGiaBan.setText("");
        cbxNhaXuatBan.setValue(null);
        txtNamXuatBan.setText("");
        txtTimKiem.setText("");
        txtNamXuatBan.setText("");
        radMaSach.setSelected(true);
        tblSach.setItems(null);
    }

    //Load nhà xuất bản
    public void loadNhaXuatBan(){
        cbxNhaXuatBan.setItems(sachDAO.getNhaXuatBan());
    }
    //Load loại sách
    public void loadLoaiSach(){
        cbxLoaiSach.setItems(loaiSDAO.getLoaiSach());
    }

    //Load nhà cung cấp
    public void loadNhaCungCap(){
        cbxNCC.setItems(nhaCungCapDAO.getNhaCungCap());
    }

    public Double tinhGiaBan(Double giaNhap){
        return ((giaNhap) / (100-50))*100;
    }

    public void hienGiaBan(KeyEvent keyEvent){
        Double giaBan = tinhGiaBan(Double.valueOf(txtGiaNhap.getText()));
        txtGiaBan.setText(String.valueOf(Math.round(giaBan)));
    }

    //Tạo mã sách tự động
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
    public void validData (MouseEvent event) {
        if (event.getSource().equals(txtSoLuong)) {
            if(!txtSoLuong.getText().matches("^[0-9]+$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Số lượng phải là số và không được bỏ trống");
                alert.showAndWait();

                txtSoLuong.setStyle("-fx-border-color:#e04040;");
            } else {
                txtSoLuong.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtTacGia)) {
            if(!txtTacGia.getText().matches("^[a-zA-Z_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Tên tác giả không hợp lệ và không được bỏ trống");
                alert.showAndWait();

                txtTacGia.setStyle("-fx-border-color:#e04040;");
            } else {
                txtTacGia.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtTenSach)) {
            if(!txtTenSach.getText().matches("^[a-zA-Z0-9_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Tên sách không hợp lệ và không được bỏ trống");
                alert.showAndWait();

                txtTenSach.setStyle("-fx-border-color:#e04040;");
            } else {
                txtTenSach.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtGiaNhap)) {
            if(!txtGiaNhap.getText().matches("[0-9]{1,13}(\\.[0-9]*)?$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Giá nhập phải là số thực lớn hơn 0 và không được bỏ trống");
                alert.showAndWait();

                txtGiaNhap.setStyle("-fx-border-color:#e04040;");
            } else {
                txtGiaNhap.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtNamXuatBan)) {
            if(!txtNamXuatBan.getText().matches("^[0-9]{4}$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Năm xuất bản phải là số có 4 chữ số và không được bỏ trống");
                alert.showAndWait();

                txtNamXuatBan.setStyle("-fx-border-color:#e04040;");
            } else {
                txtNamXuatBan.setStyle("-fx-border-color:#fff;");
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadNhaXuatBan();
        loadLoaiSach();
        loadNhaCungCap();
        txtMaS.setText(taoMaSach());
    }
}
