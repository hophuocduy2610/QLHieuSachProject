package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.dao.KhachHangDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ThemKhachHangController implements Initializable {
    @FXML
    private Button btnThem;
    @FXML
    private ComboBox<String> cbxPhai;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker txtNamSinh;

    @FXML
    private TextField txtSoDT;

    @FXML
    private TextField txtTenKH;

    @FXML
    private BorderPane pnThemKhachHang;

    @FXML
    private Label errEmail;

    @FXML
    private Label errSDT;

    @FXML
    private Label errTenKH;

    @FXML
    private TextField txtMaKH;

    KhachHangDAO khachHangDAO = new KhachHangDAO();

    Alert alert;
    //Hiện form địa chỉ
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
        //Sau khi form địa chỉ close, set text vào textField địa chỉ
        if(!stage.isShowing()){
            txtDiaChi.setText(GetData.diaChi);
        }
    }
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

        if(errTenKH.getText() != "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tên khách hàng không hợp lệ");
            alert.showAndWait();
            return;
        } else if (errEmail.getText() != "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Email khách hàng không hợp lệ");
            alert.showAndWait();
            return;
        } else if (errSDT.getText() != "") {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Số điện thoại không hợp lệ");
            alert.showAndWait();
            return;
        }

        boolean result = khachHangDAO.themKhachHang(txtMaKH.getText(), txtTenKH.getText(), txtDiaChi.getText(), txtSoDT.getText(), txtEmail.getText(), cbxPhai.getValue(), String.valueOf(txtNamSinh.getValue()), 1);
        if(!result){
            GetData.tenKH = txtTenKH.getText();
            GetData.sdtKH = txtSoDT.getText();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Thêm thành công");
            alert.showAndWait();
            btnThem.getScene().getWindow().hide();
        }else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Thêm không thành công");
            alert.showAndWait();
        }
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
    public void clearFormThongTinKhachHang(){
        txtTenKH.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSoDT.setText("");
    }

    public void validData (KeyEvent event) {
        if (event.getSource().equals(txtTenKH)) {
            if(!txtTenKH.getText().matches("^[a-zA-Z_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                errTenKH.setText("Tên khách hàng không được chứa số và kí tự đặc biệt");
                txtTenKH.setStyle("-fx-border-color:#e04040;");
            } else {
                errTenKH.setText("");
                txtTenKH.setStyle("-fx-border-color:#fff;");
            }
        }  else if (event.getSource().equals(txtEmail)) {
            //Kiểm tra Text Email
            if(!txtEmail.getText().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
                    "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
                errEmail.setText("Email theo mẫu username@domain.com");
                txtEmail.setStyle("-fx-border-color:#e04040;");
            } else {
                errEmail.setText("");
                txtEmail.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtSoDT)) {
            //Kiểm tra Text Số điện thoại
            if(!txtSoDT.getText().matches("^\\d{3}[- .]?(\\d{4}){2}$")) {
                errSDT.setText("Số điện thoại phải là số có 11 chữ số");
                txtSoDT.setStyle("-fx-border-color:#e04040;");
            } else {
                errSDT.setText("");
                txtSoDT.setStyle("-fx-border-color:#fff;");
            }
        }
    }

    //Exit
    public void exit(ActionEvent event) {
        pnThemKhachHang.getScene().getWindow().hide();
    }

    //Minimize
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) pnThemKhachHang.getScene().getWindow();
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxPhai.setItems(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
        txtMaKH.setText(taoMaKH());
    }
}
