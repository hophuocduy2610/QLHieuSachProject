package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
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
        //Sau khi form địa chỉ close, set text vào textField địa chỉ
        if(!stage.isShowing()){
            txtDiaChi.setText(GetData.diaChi);
        }
    }
    public void themKhachHang(){
        String sql = "  INSERT INTO KhachHang (tenKH, diaChi, sdt, email, phai, namSinh) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txtTenKH.getText());
            prepare.setString(2, txtDiaChi.getText());
            prepare.setString(3, txtSoDT.getText());
            prepare.setString(4, txtEmail.getText());
            prepare.setString(5, cbxPhai.getValue());
            prepare.setString(6, txtNamSinh.getValue().toString());
            boolean result = prepare.execute();
            if(!result){
                GetData.tenKH = txtTenKH.getText();
                GetData.sdtKH = txtSoDT.getText();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thành công");
                alert.showAndWait();
                btnThem.getScene().getWindow().hide();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Thêm không thành công");
                alert.showAndWait();
                btnThem.getScene().getWindow().hide();
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
    public void clearFormThongTinKhachHang(){
        txtTenKH.setText("");
        txtNamSinh.setValue(null);
        cbxPhai.setValue("Nam");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSoDT.setText("");
    }

    public void validData (MouseEvent event) {
        if (event.getSource().equals(txtTenKH)) {
            if(!txtTenKH.getText().matches("^[a-zA-Z_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Tên phải có dấu và không được bỏ trống");
                alert.showAndWait();

                txtTenKH.setStyle("-fx-border-color:#e04040;");
            } else {
                txtTenKH.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtDiaChi)) {
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
        } else if (event.getSource().equals(txtEmail)) {
            //Kiểm tra Text Email
            if(!txtEmail.getText().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
                    "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Email không được bỏ trống và theo mẫu username@domain.com");
                alert.showAndWait();

                txtEmail.setStyle("-fx-border-color:#e04040;");
            } else {
                txtEmail.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtSoDT)) {
            //Kiểm tra Text Số điện thoại
            if(!txtSoDT.getText().matches("^\\d{3}[- .]?(\\d{4}){2}$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Số điện thoại phải là số có 11 chữ số và không được bỏ trống");
                alert.showAndWait();

                txtSoDT.setStyle("-fx-border-color:#e04040;");
            } else {
                txtSoDT.setStyle("-fx-border-color:#fff;");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxPhai.setItems(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
    }
}
