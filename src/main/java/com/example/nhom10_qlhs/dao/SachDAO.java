package com.example.nhom10_qlhs.dao;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.entities.Sach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SachDAO {

    public Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Sach sach;

    //Lấy danh sách tất cả sách
    public List<Sach> getDSSach(){
        Sach sach;
        List<Sach> sachList = new ArrayList<>();
        String sql = "SELECT * FROM Sach";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while(result.next()){
                sach = new Sach();
                sach.setMaSach(result.getString("maS"));
                sach.setTenSach(result.getString("tenSach"));
                sach.setSoLuong(result.getInt("soLuong"));
                sach.setGiaNhap(result.getDouble("giaNhap"));
                sach.setNamXuatBan(result.getInt("namXuatBan"));
                sach.setLoaiSach(result.getString("loaiSach"));
                sach.setGiaBan(result.getDouble("giaBan"));
                sach.setHinhAnhSach(result.getString("hinhAnhSach"));
                sachList.add(sach);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sachList;
    }

    //Lấy danh sách Sách theo Mã
    public List<Sach> getDSSachTheoMa(String maSach){
        List<Sach> sachList = new ArrayList<>();
        String sql = "SELECT Sach.maS, tenSach, soLuong, NhaXuatBan.tenNXB, TacGia.tenTG, LoaiS.tenLoai, namXuatBan, ngayNhap, giaNhap, giaBan, NhaCungCap.tenNCC, VAT\n" +
                "FROM Sach, NhaXuatBan, TacGia, LoaiS, NhaCungCap\n" +
                "WHERE Sach.nhaXuatBan = NhaXuatBan.maNXB AND Sach.tacGia = TacGia.maTG " +
                "AND Sach.loaiSach = LoaiS.maLoai AND maS = ? " +
                "AND Sach.nhaCungCap = NhaCungCap.maNCC AND Sach.trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maSach);
            result = prepare.executeQuery();
            while(result.next()){
                sach = new Sach(result.getString("maS"),
                        result.getString("tenSach"),
                        result.getInt("soLuong"),
                        result.getDate("ngayNhap"),
                        result.getDouble("giaNhap"),
                        result.getString("tenNXB"),
                        result.getInt("namXuatBan"),
                        result.getString("tenTG"),
                        result.getString("tenLoai"),
                        result.getDouble("giaBan"),
                        result.getDouble("VAT"),
                        result.getString("tenNCC"));
                sachList.add(sach);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sachList;
    }

    //Lấy danh sách Sách theo tên
    public List<Sach> getDSSachTheoTenSach(String tenSach){
        List<Sach> sachList = new ArrayList<>();
        String sql = "SELECT Sach.maS, tenSach, soLuong, NhaXuatBan.tenNXB, TacGia.tenTG, LoaiS.tenLoai, namXuatBan, ngayNhap, giaNhap, giaBan, NhaCungCap.tenNCC, VAT\n" +
                "FROM Sach, NhaXuatBan, TacGia, LoaiS, NhaCungCap \n" +
                "WHERE Sach.nhaXuatBan = NhaXuatBan.maNXB AND Sach.tacGia = TacGia.maTG " +
                "AND Sach.loaiSach = LoaiS.maLoai AND tenSach = ? " +
                "AND Sach.nhaCungCap = NhaCungCap.maNCC AND Sach.trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenSach);
            result = prepare.executeQuery();
            while(result.next()){
                sach = new Sach(result.getString("maS"),
                        result.getString("tenSach"),
                        result.getInt("soLuong"),
                        result.getDate("ngayNhap"),
                        result.getDouble("giaNhap"),
                        result.getString("tenNXB"),
                        result.getInt("namXuatBan"),
                        result.getString("tenTG"),
                        result.getString("tenLoai"),
                        result.getDouble("giaBan"),
                        result.getDouble("VAT"),
                        result.getString("tenNCC"));
                sachList.add(sach);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sachList;
    }

    //Lấy danh sách Sách theo tác giả
    public List<Sach> getDSSachTheoTacGia(String tenTG){
        List<Sach> sachList = new ArrayList<>();
        String sql = "SELECT Sach.maS, tenSach, soLuong, NhaXuatBan.tenNXB, TacGia.tenTG, LoaiS.tenLoai, namXuatBan, ngayNhap, giaNhap, giaBan, NhaCungCap.tenNCC, VAT\n" +
                "FROM Sach, NhaXuatBan, TacGia, LoaiS, NhaCungCap\n" +
                "WHERE Sach.nhaXuatBan = NhaXuatBan.maNXB AND Sach.tacGia = TacGia.maTG \n" +
                "AND Sach.loaiSach = LoaiS.maLoai AND tenTG =  ?\n" +
                "AND Sach.nhaCungCap = NhaCungCap.maNCC AND Sach.trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenTG);
            result = prepare.executeQuery();
            while(result.next()){
                sach = new Sach(result.getString("maS"),
                        result.getString("tenSach"),
                        result.getInt("soLuong"),
                        result.getDate("ngayNhap"),
                        result.getDouble("giaNhap"),
                        result.getString("tenNXB"),
                        result.getInt("namXuatBan"),
                        result.getString("tenTG"),
                        result.getString("tenLoai"),
                        result.getDouble("giaBan"),
                        result.getDouble("VAT"),
                        result.getString("tenNCC"));
                sachList.add(sach);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sachList;
    }

    public boolean themSach(String maS, String tenSach, int soLuong, double giaNhap, String nhaXuatBan, int namXuatBan, String tacGia, String loaiSach, String nhaCungCap,  double giaBan, String hinhAnhSach, int trangThai) {
        String sql = "  INSERT INTO Sach (maS, tenSach, soLuong, " +
                "giaNhap, nhaXuatBan, namXuatBan, tacGia, " +
                "loaiSach, nhaCungCap, giaBan, hinhAnhSach, trangThai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maS);
            prepare.setString(2, tenSach);
            prepare.setInt(3, soLuong);
            prepare.setDouble(4, giaNhap);
            prepare.setString(5, nhaXuatBan);
            prepare.setInt(6, namXuatBan);
            prepare.setString(7, tacGia);
            prepare.setString(8, loaiSach);
            prepare.setString(9, nhaCungCap);
            prepare.setDouble(10, giaBan);
            prepare.setString(11, hinhAnhSach);
            prepare.setInt(12, trangThai);
            boolean result = prepare.execute();//Thực thi truy vấn sql

            if (!result) { //Nếu thực thi thành công thì xuất thông báo
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //Cập nhật thông tin sách
    public void capNhatThongTinSach(String maSach, String tenSach, int soLuong, String tenNXB, String tenNCC,  String tenTG, String loaiSach, int namXB, Double giaNhap, Double giaBan){
        String sql = "UPDATE Sach\n" +
                "SET tenSach = ?,\n" +
                "soLuong = ?,\n" +
                "nhaXuatBan = (SELECT maNXB FROM NhaXuatBan WHERE tenNXB = N'?'),\n" +
                "nhaCungCap = (SELECT maNCC FROM NhaCungCap WHERE tenNCC = N'?'),\n" +
                "tacGia = (SELECT maTG FROM TacGia WHERE tenTG = N'?'),\n" +
                "loaiSach = (SELECT maLoai FROM LoaiS WHERE tenLoai = N'?'),\n" +
                "namXuatBan = ?,\n" +
                "giaNhap = ?,\n" +
                "giaBan = ?\n" +
                "WHERE maS = ? ";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenSach);
            prepare.setInt(2, soLuong);
            prepare.setString(3, tenNXB);
            prepare.setString(4, tenNCC);
            prepare.setString(5, tenTG);
            prepare.setString(6, loaiSach);
            prepare.setInt(7, namXB);
            prepare.setDouble(8, giaNhap);
            prepare.setDouble(9, giaBan);
            prepare.setString(10, maSach);
            prepare.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Cập nhật trạng thái Sách đã xóa
    public void capNhatTrangThai(int trangThai, String maSach){
        String sql = "  UPDATE Sach SET trangThai = ? WHERE maS = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, trangThai);
            prepare.setString(2, maSach);
            boolean result = prepare.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Get Nhà xuất bản
    public ObservableList<String> getNhaXuatBan(){
        ObservableList<String> tenNXBList = FXCollections.observableArrayList();
        String sql = "SELECT tenNXB FROM NhaXuatBan";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()){
                tenNXBList.add(result.getString("tenNXB"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return tenNXBList;
    }

    //Đếm số sách
    public int demSoSach(){
        String sql = "SELECT COUNT(*) FROM Sach";
        int soSach = 0;
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                soSach = result.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return soSach;
    }

}
