package com.example.nhom10_qlhs.entities;

import java.sql.Date;

public class HoaDon {
    String maHD;
    String maNV;
    Date ngayLap;
    String maKhachHang;
    double tongThanhTien;

    public HoaDon() {
    }


    public HoaDon(String maHD, String maNV, Date ngayLap, String maKhachHang, double tongThanhTien) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.ngayLap = ngayLap;
        this.maKhachHang = maKhachHang;
        this.tongThanhTien = tongThanhTien;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public double getTongThanhTien() {
        return tongThanhTien;
    }

    public void setTongThanhTien(double tongThanhTien) {
        this.tongThanhTien = tongThanhTien;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHD='" + maHD + '\'' +
                ", maNV='" + maNV + '\'' +
                ", ngayLap=" + ngayLap +
                ", maKhachHang='" + maKhachHang + '\'' +
                ", tongThanhTien=" + tongThanhTien +
                '}';
    }
}
