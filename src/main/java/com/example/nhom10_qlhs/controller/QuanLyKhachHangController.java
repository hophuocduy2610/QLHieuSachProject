package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.ZoneId;
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
    private Label lblMaKH;

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
    private KhachHang khachHang;

    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    //Lấy danh sách khách hàng theo Mã
    public List<KhachHang> getDSKhachHangTheoMa(String maKH){
        List<KhachHang> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE maKH = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maKH);
            result = prepare.executeQuery();
            while(result.next()){
                khachHang = new KhachHang(result.getString("maKH"),
                        result.getString("tenKH"),
                        result.getString("diaChi"),
                        result.getString("sdt"),
                        result.getString("email"),
                        result.getString("phai"),
                        result.getDate("namSinh"));
                khachHangList.add(khachHang);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return khachHangList;
    }

    //Lấy danh sách khách hàng theo tên
    public List<KhachHang> getDSKhachHangTheoTen(String tenKH){
        List<KhachHang> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE tenKH = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenKH);
            result = prepare.executeQuery();
            while(result.next()){
                khachHang = new KhachHang(result.getString("maKH"),
                        result.getString("tenKH"),
                        result.getString("diaChi"),
                        result.getString("sdt"),
                        result.getString("email"),
                        result.getString("phai"),
                        result.getDate("namSinh"));
                khachHangList.add(khachHang);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return khachHangList;
    }

    //Lấy danh sách khách hàng theo SDT
    public List<KhachHang> getDSKhachHangTheoSDT(String sdtKH){
        List<KhachHang> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE sdt = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, sdtKH);
            result = prepare.executeQuery();
            while(result.next()){
                khachHang = new KhachHang(result.getString("maKH"),
                        result.getString("tenKH"),
                        result.getString("diaChi"),
                        result.getString("sdt"),
                        result.getString("email"),
                        result.getString("phai"),
                        result.getDate("namSinh"));
                khachHangList.add(khachHang);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return khachHangList;
    }

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
                                capNhatThongTinKhachHang(kh.getMaKH(), txtTenKH.getText(),
                                        Date.valueOf(txtNamSinh.getValue()), cbxPhai.getValue(),
                                        txtDiaChi.getText(), txtEmail.getText(), txtSoDT.getText());
                            }else{
                                capNhatThongTinKhachHang(kh.getMaKH(), txtTenKH.getText(),
                                        kh.getNamSinh(), cbxPhai.getValue(),
                                        txtDiaChi.getText(), txtEmail.getText(), txtSoDT.getText());
                            }
                            khachHangObservableList.setAll(getDSKhachHangTheoTen(txtTenKH.getText()));
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
                khachHangList = getDSKhachHangTheoMa(searchKey);//lấy danh sách khách hàng theo mã
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
                khachHangList = getDSKhachHangTheoTen(searchKey);
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
                khachHangList = getDSKhachHangTheoSDT(searchKey);
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
        GetData.trangThai = 1; //set trạng thái cho khách hàng để dùng trong lúc xóa
        String sql = "  INSERT INTO KhachHang (tenKH, diaChi, sdt, email, phai, namSinh, trangThai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql); // đưa chuỗi sql vô 1 biến prepare để đưa giá trị cần trong câu query
            //Gán giá trị vào từng dấu chấm hỏi trong câu query sql thứ tự tăng dần từ trái sang phải
            prepare.setString(1, txtTenKH.getText());
            prepare.setString(2, txtDiaChi.getText());
            prepare.setString(3, txtSoDT.getText());
            prepare.setString(4, txtEmail.getText());
            prepare.setString(5, cbxPhai.getValue());
            prepare.setString(6, txtNamSinh.getValue().toString());
            prepare.setInt(7, GetData.trangThai);
            boolean result = prepare.execute();//Thực thi truy vấn sql

            if(!result){ //Nếu thực thi thành công thì xuất thông báo
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thành công");
                alert.showAndWait();
                clearAll();
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

    //Cập nhật trạng thái khách hàng đã xóa
    public void capNhatTrangThai(int trangThai, String maKH){//Sau khi đưa trạng thái và maKH vào đây thì
        String sql = "  UPDATE KhachHang SET trangThai = ? WHERE maKH = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);//đưa câu truy vấn vô hàm prepare
            //set dữ liệu vào từng dấu chấm hỏi ở câu sql trên
            prepare.setInt(1, trangThai);
            prepare.setString(2, maKH);
            boolean result = prepare.execute();//thực thi truy vấn
            //Sau khi làm xong hàm này thì sẽ đưa khachHangTemp vô list bị xóa
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Cập nhật thông tin khách hàng
    public void capNhatThongTinKhachHang(String maKH,String tenKH, Date namSinh, String phai, String diaChi, String email, String sdt){
        String sql = "  UPDATE KhachHang " +
                "SET tenKH = ?, namSinh = ?, phai = ?, diaChi = ?, email = ?, sdt = ? " +
                "WHERE maKH = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenKH);
            if(namSinh != null){
                prepare.setDate(2, namSinh);
            }
            prepare.setString(3, phai);
            prepare.setString(4, diaChi);
            prepare.setString(5, email);
            prepare.setString(6, sdt);
            prepare.setString(7, maKH);
            prepare.execute();
        }catch (Exception ex){
            ex.printStackTrace();
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
                capNhatTrangThai(GetData.trangThai, khachHangTemp.getMaKH());//đưa vô hàm capNhatTrangThai
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

    //Xóa dữ liệu trên các text field và set dữ liệu table về null
    public void clearAll(){
        lblMaKH.setText("");
        txtTenKH.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSoDT.setText("");
        tblKhachHang.setItems(null);
        txtTimKiem.setText("");
    }
    public void clearTextField(){
        lblMaKH.setText("");
        txtTenKH.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSoDT.setText("");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxPhai.setItems(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
        radMaKH.setSelected(true);
    }
}
