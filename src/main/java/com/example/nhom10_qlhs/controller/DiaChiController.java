package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


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

    private Alert alert;

    public void sendDataToTextFieldDiaChi() {
        if(!(txtSoNha.getText() == "" || txtDuong.getText() == "" || txtPhuong.getText() == "" || txtQuan.getText() == "" || txtThanhPho.getText() == "")){
            String diaChi = txtSoNha.getText() + " "
                    + txtDuong.getText() + ", "
                    + txtPhuong.getText() + ", "
                    + txtQuan.getText() + ", "
                    + txtThanhPho.getText();
            GetData.diaChi = diaChi;
            GetData.trangThaiButton = "btnDiaChi";
            btnThem.getScene().getWindow().hide();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đầy đủ thông tin");
            alert.showAndWait();
        }
    }

    public void validData(MouseEvent event) {
        if(event.getSource().equals(txtSoNha)) {
            if (!txtSoNha.getText().matches("^[0-9/]+$")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Số nhà không hợp lệ và không được bỏ trống \nSố nhà phải theo mẫu 1/2/3");
                alert.showAndWait();

                txtSoNha.setStyle("-fx-border-color:#e04040;");
            } else {
                txtSoNha.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtDuong)){
            if (!txtDuong.getText().matches("^[a-zA-Z0-9_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Tên đường không hợp lệ và không được bỏ trống");
                alert.showAndWait();
                txtDuong.setStyle("-fx-border-color:#e04040;");
            } else {
                txtDuong.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtPhuong)) {
            if (!txtPhuong.getText().matches("^[a-zA-Z0-9_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Tên phường không hợp lệ và không được bỏ trống");
                alert.showAndWait();
                txtPhuong.setStyle("-fx-border-color:#e04040;");

            } else {
                txtPhuong.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtQuan)) {
            if (!txtQuan.getText().matches("^[a-zA-Z0-9_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Tên quận không hợp lệ và không được bỏ trống");
                alert.showAndWait();
                txtQuan.setStyle("-fx-border-color:#e04040;");
            } else {
                txtQuan.setStyle("-fx-border-color:#fff;");
            }
        } else if (event.getSource().equals(txtThanhPho)) {
            if (!txtThanhPho.getText().matches("^[a-zA-Z0-9_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Tên thành phố không hợp lệ và không được bỏ trống");
                alert.showAndWait();
                txtThanhPho.setStyle("-fx-border-color:#e04040;");
            } else {
                txtThanhPho.setStyle("-fx-border-color:#fff;");
            }
        }
    }
}
