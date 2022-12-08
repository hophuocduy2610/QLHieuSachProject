package com.example.nhom10_qlhs.controller;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.MyListener;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.entities.Sach;
import com.example.nhom10_qlhs.entities.SachInTable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SachItemController {

    @FXML
    private ImageView imgSach;

    @FXML
    private Label lblGiaBan;

    @FXML
    private Label lblMaSach;

    @FXML
    private Label lblTenSach;

    @FXML
    private Label lblNamXuatBan;

    @FXML
    private Label lblVAT;

    @FXML
    private TextField txtSoLuong;

    private MyListener myListener;
    private Sach sach;

    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    //Sau khi nhấn vào nút thêm của một cuốn sách nào đó trên giao diện lập hóa đơn
    public void click() throws IOException{
        URL url = new File("target/classes/com/example/nhom10_qlhs/nhap-so-luong.fxml").toURI().toURL();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();


        //Sau khi form nhập số lượng close, truyền dữ liệu sách vào hàm onActionListener
        if(GetData.trangThaiButton.equals("btnOk")){
            GetData.trangThaiButton = "";
            String sql = "SELECT * FROM Sach WHERE maS = ?";
            try {
                connect = ConnectDB.connect();
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, lblMaSach.getText());
                result = prepare.executeQuery();
                if (result.next()){
                    SachInTable sachInTable = new SachInTable(result.getString("maS"), result.getString("tenSach"), GetData.slSach, result.getDouble("giaBan"), result.getDouble("VAT"));
                    myListener.onActionListener(sachInTable);
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }

    //Set dữ liệu vào item sách
    public void setDataSach(Sach sach, MyListener myListener) throws IOException {
        this.sach = sach;
        this.myListener = myListener;
        Image image = new Image(new File(sach.getHinhAnhSach()).toURI().toString());
        lblMaSach.setText(sach.getMaSach());
        imgSach.setImage(image);
        lblTenSach.setText(sach.getTenSach());
        lblGiaBan.setText(String.valueOf(sach.getGiaBan()));
        lblNamXuatBan.setText(String.valueOf(sach.getNamXuatBan()));
        if (sach.getVAT() == null){
            lblVAT.setText("0");
        } else{
            lblVAT.setText(String.valueOf(sach.getVAT()));
        }
        GetData.slSach = sach.getSoLuong();
    }
}
