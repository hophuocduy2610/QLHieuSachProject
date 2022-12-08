package com.example.nhom10_qlhs.entities;

public class NhaXuatBan {
    private String maNXB;
    private String tenNXB;
    private String diaChi;
    private String sdt;
    private String email;

    public NhaXuatBan() {
    }

    public NhaXuatBan(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    public NhaXuatBan(String maNXB, String tenNXB, String diaChi, String sdt, String email) {
        this.maNXB = maNXB;
        this.tenNXB = tenNXB;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
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

    @Override
    public String toString() {
        return "NhaXuatBan{" +
                "maNXB='" + maNXB + '\'' +
                ", tenNXB='" + tenNXB + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", sdt='" + sdt + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
