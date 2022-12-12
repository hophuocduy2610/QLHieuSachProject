package com.example.nhom10_qlhs.dao;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.entities.NhanVien;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private NhanVien nhanVien;


    //Lấy danh sách nhân viên theo Mã
    public List<NhanVien> getDSNhanVienTheoMa(String maNV){
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE maNV = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maNV);
            result = prepare.executeQuery();
            while(result.next()){
                nhanVien = new NhanVien(result.getString("maNV"),
                        result.getString("tenNV"),
                        result.getString("diaChi"),
                        result.getDate("namSinh"),
                        result.getString("sdt"),
                        result.getString("CMND"),
                        result.getString("phai"),
                        result.getString("chucVu"),
                        result.getDate("ngayVaoLam"));
                nhanVienList.add(nhanVien);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return nhanVienList;
    }

    //Lấy danh sách nhân viên theo tên
    public List<NhanVien> getDSNhanVienTheoTen(String tenNV){
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE tenNV = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenNV);
            result = prepare.executeQuery();
            while(result.next()){
                nhanVien = new NhanVien(result.getString("maNV"),
                        result.getString("tenNV"),
                        result.getString("diaChi"),
                        result.getDate("namSinh"),
                        result.getString("sdt"),
                        result.getString("CMND"),
                        result.getString("phai"),
                        result.getString("chucVu"),
                        result.getDate("ngayVaoLam"));
                nhanVienList.add(nhanVien);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return nhanVienList;
    }

    //Lấy danh sách nhân viên theo SDT
    public List<NhanVien> getDSNhanVienTheoSDT(String sdtNV){
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE sdt = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, sdtNV);
            result = prepare.executeQuery();
            while(result.next()){
                nhanVien = new NhanVien(result.getString("maNV"),
                        result.getString("tenNV"),
                        result.getString("diaChi"),
                        result.getDate("namSinh"),
                        result.getString("sdt"),
                        result.getString("CMND"),
                        result.getString("phai"),
                        result.getString("chucVu"),
                        result.getDate("ngayVaoLam"));
                nhanVienList.add(nhanVien);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return nhanVienList;
    }

    //Lấy danh sách nhân viên theo năm hiện tại
    public List<NhanVien> getDSNhanVienTheoNam(int year){
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE YEAR(ngayVaoLam) = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,String.valueOf(year));
            result = prepare.executeQuery();
            while(result.next()){
                nhanVien = new NhanVien(result.getString("maNV"),
                        result.getString("tenNV"),
                        result.getString("diaChi"),
                        result.getDate("namSinh"),
                        result.getString("sdt"),
                        result.getString("CMND"),
                        result.getString("phai"),
                        result.getString("chucVu"),
                        result.getDate("ngayVaoLam"));
                nhanVienList.add(nhanVien);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return nhanVienList;
    }

    //Thêm một nhân viên mới
    public boolean themNhanVien(String maNV, String tenNV, String diaChi, String sdt, Date ngayVaoLam, String phai, Date namSinh, String chucVu, String CMND) {
        GetData.trangThai = 1; //set trạng thái cho nhân viên để dùng trong lúc xóa
        GetData.trangThaiButton = "btnThemNV";
        String sql = "  INSERT INTO NhanVien (maNV, tenNV, diaChi, sdt, ngayVaoLam, phai, namSinh, chucVu, CMND, trangThai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql); // đưa chuỗi sql vô 1 biến prepare để đưa giá trị cần trong câu query
            //Gán giá trị vào từng dấu chấm hỏi trong câu query sql thứ tự tăng dần từ trái sang phải
            prepare.setString(1, maNV);
            prepare.setString(2, tenNV);
            prepare.setString(3, diaChi);
            prepare.setString(4, sdt);
            prepare.setDate(5, ngayVaoLam);
            prepare.setString(6, phai);
            prepare.setDate(7, namSinh);
            prepare.setString(8, chucVu);
            prepare.setString(9, CMND);
            prepare.setInt(10, GetData.trangThai);
            boolean result = prepare.execute();//Thực thi truy vấn sql
            if (!result) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //Cập nhật thông tin nhân viên
    public void capNhatThongTinNhanVien(String maNV,String tenNV, String diaChi, Date namSinh, String sdt,  String CMND, String phai, String chucVu, Date ngayVaoLam){
        String sql = "  UPDATE NhanVien " +
                "SET tenNV = ?, diaChi = ?, namSinh = ?, sdt = ?, CMND = ?, phai = ?, chucVu = ?, ngayVaoLam = ? " +
                "WHERE maNV = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenNV);
            prepare.setString(2, diaChi);
            if(namSinh != null){
                prepare.setDate(3, namSinh);
            }
            prepare.setString(4, sdt);
            prepare.setString(5, CMND);
            prepare.setString(6, phai);
            prepare.setString(7, chucVu);
            if(ngayVaoLam != null){
                prepare.setDate(8, ngayVaoLam);
            }
            prepare.setString(9, maNV);
            prepare.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Cập nhật trạng thái nhân viên đã xóa
    public void capNhatTrangThai(int trangThai, String maNV){
        String sql = "  UPDATE NhanVien SET trangThai = ? WHERE maNV = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, trangThai);
            prepare.setString(2, maNV);
            boolean result = prepare.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Tạo tự động tên tài khoản sau khi thêm nhân viên
    public boolean taoTuDongTenTaiKhoan(String trangThaiButton, String SDTNhanVien){
        if(trangThaiButton.equals("btnThemNV")) {
            String sql = "SELECT maNV + CONVERT(VARCHAR(10), FORMAT(ngayVaoLam, 'ddMMyyyy', 'en-US'))  AS tenTK, maNV from NhanVien WHERE sdt = ?";
            try {
                connect = ConnectDB.connect();
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, SDTNhanVien);
                result = prepare.executeQuery();
                if (result.next()) {
                    GetData.taiKhoan = result.getString("tenTK");
                } else {
                    return false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
    public int demSoNhanVien(){
        String sql = "SELECT COUNT(*) FROM NhanVien";
        int soNV = 0;
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                soNV = result.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return soNV;
    }
}
