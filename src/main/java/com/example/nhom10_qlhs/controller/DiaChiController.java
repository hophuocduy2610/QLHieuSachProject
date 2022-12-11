package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class DiaChiController {

    @FXML
    private Button btnThem;

    @FXML
    private TextField txtDuong;

    @FXML
    private TextField txtPhuong;

    @FXML
    private TextField txtQuan;

    @FXML
    private TextField txtSoNha;

    @FXML
    private TextField txtThanhPho;

    @FXML
    private Label errDuong;

    @FXML
    private Label errPhuong;

    @FXML
    private Label errQuan;

    @FXML
    private Label errSoNha;

    @FXML
    private Label errThanhPho;

    @FXML
    private BorderPane pnBorderpane;


    public void sendDataToTextFieldDiaChi() {
        if (txtSoNha.getText() == ""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Số nhà không được bỏ trống");
            alert.showAndWait();
            return;
        } else if (txtDuong.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tên đường không được bỏ trống");
            alert.showAndWait();
            return;
        } else if (txtPhuong.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tên đường không được bỏ trống");
            alert.showAndWait();
            return;
        } else if (txtQuan.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Quận không được bỏ trống");
            alert.showAndWait();
            return;
        } else if (txtThanhPho.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Thành phố không được bỏ trống ");
            alert.showAndWait();
            return;
        }

        if(errSoNha.getText() != "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Số nhà không hợp lệ");
            alert.showAndWait();
            return;
        } else if (errDuong.getText() != "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Đường không hợp lệ");
            alert.showAndWait();
            return;
        } else if (txtPhuong.getText() != "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Phường không hợp lệ");
            alert.showAndWait();
            return;
        } else if (txtQuan.getText() != "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Quận không hợp lệ");
            alert.showAndWait();
            return;
        } else if (txtThanhPho.getText() != "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Thành phố không hợp lệ");
            alert.showAndWait();
            return;
        }
        String diaChi = txtSoNha.getText() + " "
                + txtDuong.getText() + ", "
                + txtPhuong.getText() + ", "
                + txtQuan.getText() + ", "
                + txtThanhPho.getText();
        GetData.diaChi = diaChi;
        GetData.trangThaiButton = "btnDiaChi";
        btnThem.getScene().getWindow().hide();

    }

    public void validData(KeyEvent event) {
        if(event.getSource().equals(txtSoNha)) {
            if (!txtSoNha.getText().matches("^[0-9/]+$")) {
                errSoNha.setText("Số nhà phải theo mẫu 1/2/3");
                txtSoNha.setStyle("-fx-border-color:#e04040;");
            } else {
                errSoNha.setText("");
                txtSoNha.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtDuong)){
            if (!txtDuong.getText().matches("^[a-zA-Z0-9_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                errDuong.setText("Tên đường không được chứa kí tự đặc biệt");
                txtDuong.setStyle("-fx-border-color:#e04040;");
            } else {
                errDuong.setText("");
                txtDuong.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtPhuong)) {
            if (!txtPhuong.getText().matches("^[a-zA-Z0-9_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                errPhuong.setText("Tên phường không được chứa kí tự đặc biệt");
                txtPhuong.setStyle("-fx-border-color:#e04040;");

            } else {
                errPhuong.setText("");
                txtPhuong.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtQuan)) {
            if (!txtQuan.getText().matches("^[a-zA-Z0-9_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                errQuan.setText("Tên quận không được chứa kí tự đặc biệt");
                txtQuan.setStyle("-fx-border-color:#e04040;");
            } else {
                errQuan.setText("");
                txtQuan.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtThanhPho)) {
            if (!txtThanhPho.getText().matches("^[a-zA-Z0-9_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                errThanhPho.setText("Tên thành phố không được chứa kí tự đặc biệt");
                txtThanhPho.setStyle("-fx-border-color:#e04040;");
            } else {
                errThanhPho.setText("");
                txtThanhPho.setStyle("-fx-border-color:#fff;");
            }
        }
    }

    //Exit
    public void exit(ActionEvent event) {
        pnBorderpane.getScene().getWindow().hide();
    }

    //Minimize
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) pnBorderpane.getScene().getWindow();
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        stage.setIconified(true);
    }

}
