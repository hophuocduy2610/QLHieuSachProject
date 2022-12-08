package com.example.nhom10_qlhs.entities;

import javafx.scene.control.CheckBox;

public class SachInTable {
    private String maSach;
    private String tenSach;
    private int soLuong;
    private Double donGia;
    private Double VAT;
    private Double thanhTien;
    private CheckBox checkBox;

    public SachInTable() {
    }

    public SachInTable(String maSach, String tenSach, int soLuong, Double donGia, Double VAT) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soLuong = soLuong;
        this.donGia = donGia;
        if(VAT == 0){
            this.thanhTien = soLuong*donGia;
        } else if (VAT > 0){
            this.thanhTien = soLuong*donGia *(1+VAT);
        }

        this.VAT = VAT;
        this.checkBox = new CheckBox();
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public Double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(Double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public Double getVAT() {
        return VAT;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
