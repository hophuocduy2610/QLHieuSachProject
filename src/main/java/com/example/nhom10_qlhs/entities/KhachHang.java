package com.example.nhom10_qlhs.entities;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;

import java.sql.Date;

public class KhachHang {
    private String maKH;
    private String tenKH;
    private String diaChi;
    private String sdt;
    private String email;
    private String phai;
    private Date namSinh;

    private CheckBox cbxXoa;
//    private Button btnCapNhat;

    public KhachHang() {
    }

    public KhachHang(String maKH) {
        this.maKH = maKH;
    }

    public KhachHang(String maKH, String tenKH, String diaChi, String sdt, String email, String phai, Date namSinh) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.phai = phai;
        this.namSinh = namSinh;
        this.cbxXoa = new CheckBox();
//        ImageView imgView = new ImageView("D:/PTUDBTLon/src/main/java/com/example/nhom10_qlhs/image/edit-icon.png");
//        imgView.setFitWidth(18);
//        imgView.setFitHeight(18);
//        this.btnCapNhat = new Button("Edit",imgView);
//        this.btnCapNhat.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//        this.btnCapNhat.setOnAction(event -> {
//        });
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhai() {
        return phai;
    }

    public void setPhai(String phai) {
        this.phai = phai;
    }

    public Date getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(Date namSinh) {
        this.namSinh = namSinh;
    }

    public CheckBox getCbxXoa() {
        return cbxXoa;
    }

    public void setCbxXoa(CheckBox cbxXoa) {
        this.cbxXoa = cbxXoa;
    }

//    public Button getBtnCapNhat() {
//        return btnCapNhat;
//    }
//
//    public void setBtnCapNhat(Button btnCapNhat) {
//        this.btnCapNhat = btnCapNhat;
//    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKH='" + maKH + '\'' +
                ", tenKH='" + tenKH + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", sdt='" + sdt + '\'' +
                ", email='" + email + '\'' +
                ", phai='" + phai + '\'' +
                '}';
    }
}
