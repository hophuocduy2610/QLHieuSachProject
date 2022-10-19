package com.example.nhom10_qlhs.entities;

import java.util.Date;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private String diaChi;
    private Date namSinh;
    private String CMND;
    private String phai;
    private String chucVu;

    public NhanVien() {
    }

    public NhanVien(String maNV) {
        this.maNV = maNV;
    }

    public NhanVien(String maNV, String tenNV, String diaChi, Date namSinh, String CMND, String phai, String chucVu) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.diaChi = diaChi;
        this.namSinh = namSinh;
        this.CMND = CMND;
        this.phai = phai;
        this.chucVu = chucVu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Date getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(Date namSinh) {
        this.namSinh = namSinh;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getPhai() {
        return phai;
    }

    public void setPhai(String phai) {
        this.phai = phai;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNV='" + maNV + '\'' +
                ", tenNV='" + tenNV + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", namSinh=" + namSinh +
                ", CMND='" + CMND + '\'' +
                ", phai='" + phai + '\'' +
                ", chucVu='" + chucVu + '\'' +
                '}';
    }
}
