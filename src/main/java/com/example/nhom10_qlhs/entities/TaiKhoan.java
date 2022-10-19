package com.example.nhom10_qlhs.entities;

public class TaiKhoan {
    private NhanVien taiKhoan;
    private String matKhauTK;
    private String loaiTK;

    public TaiKhoan() {
    }

    public TaiKhoan(NhanVien taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public TaiKhoan(NhanVien taiKhoan, String matKhauTK, String loaiTK) {
        this.taiKhoan = taiKhoan;
        this.matKhauTK = matKhauTK;
        this.loaiTK = loaiTK;
    }

    public NhanVien getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(NhanVien taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhauTK() {
        return matKhauTK;
    }

    public void setMatKhauTK(String matKhauTK) {
        this.matKhauTK = matKhauTK;
    }

    public String getLoaiTK() {
        return loaiTK;
    }

    public void setLoaiTK(String loaiTK) {
        this.loaiTK = loaiTK;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "taiKhoan=" + taiKhoan +
                ", matKhauTK='" + matKhauTK + '\'' +
                ", loaiTK='" + loaiTK + '\'' +
                '}';
    }
}
