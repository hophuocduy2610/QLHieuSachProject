package com.example.nhom10_qlhs.entities;

import javafx.beans.property.ReadOnlyFloatProperty;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.scene.control.CheckBox;

import java.util.Date;

public class Sach {
    private String maSach;
    private String tenSach;

    private int soLuong;
    private Date ngayNhap;
    private double giaNhap;
    private String nhaXuatBan;
    private int namXuatBan;
    private String tacGia;
    private String loaiSach;
    private String nhaCungCap;
    private double giaBan;
    private String hinhAnhSach;
    private CheckBox checkBox;
    private Double thanhTien;

    private Double VAT;

    public Sach() {
    }

    public Sach(String tenSach, int soLuong, double giaNhap, String nhaXuatBan, int namXuatBan, String tacGia, String loaiSach, String nhaCungCap, double giaBan, String hinhAnhSach) {
        this.tenSach = tenSach;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.nhaXuatBan = nhaXuatBan;
        this.namXuatBan = namXuatBan;
        this.tacGia = tacGia;
        this.loaiSach = loaiSach;
        this.nhaCungCap = nhaCungCap;
        this.giaBan = giaBan;
        this.hinhAnhSach = hinhAnhSach;
        this.checkBox = new CheckBox();
    }

    public Sach(String maSach, String tenSach, int soLuong, Date ngayNhap, double giaNhap, String nhaXuatBan, int namXuatBan, String tacGia, String loaiSach, double giaBan, double VAT, String nhaCungCap) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soLuong = soLuong;
        this.ngayNhap = ngayNhap;
        this.giaNhap = giaNhap;
        this.nhaXuatBan = nhaXuatBan;
        this.namXuatBan = namXuatBan;
        this.tacGia = tacGia;
        this.loaiSach = loaiSach;
        this.nhaCungCap = nhaCungCap;
        this.giaBan = giaBan;
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

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    public int getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(int namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getLoaiSach() {
        return loaiSach;
    }

    public void setLoaiSach(String loaiSach) {
        this.loaiSach = loaiSach;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public String getHinhAnhSach() {
        return hinhAnhSach;
    }

    public void setHinhAnhSach(String hinhAnhSach) {
        this.hinhAnhSach = hinhAnhSach;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
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

    @Override
    public String toString() {
        return "Sach{" +
                "maSach='" + maSach + '\'' +
                ", tenSach='" + tenSach + '\'' +
                ", soLuong=" + soLuong +
                ", giaNhap=" + giaNhap +
                ", nhaXuatBan='" + nhaXuatBan + '\'' +
                ", namXuatBan=" + namXuatBan +
                ", tacGia='" + tacGia + '\'' +
                ", loaiSach='" + loaiSach + '\'' +
                ", nhaCungCap='" + nhaCungCap + '\'' +
                ", giaBan=" + giaBan +
                ", hinhAnhSach='" + hinhAnhSach + '\'' +
                ", checkBox=" + checkBox +
                ", thanhTien=" + thanhTien +
                ", VAT=" + VAT +
                '}';
    }
}
