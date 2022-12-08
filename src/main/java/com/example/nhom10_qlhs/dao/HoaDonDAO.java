package com.example.nhom10_qlhs.dao;

import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.entities.HoaDon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonDAO {

    public Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private HoaDon hoaDon;
    public int demSoHoaDon(){
        String sql = "SELECT COUNT(*) FROM HoaDon";
        int soHD = 0;
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            boolean rs = result.next();
            if (result.next()) {
                soHD = result.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return soHD;
    }

    //Get hóa đơn theo tháng
    public List<HoaDon> getHoaDonTheoThang(int thang) {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE MONTH(ngayLap) = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(thang));
            result = prepare.executeQuery();

            while (result.next()) {
                hoaDon = new HoaDon(result.getString("maHoaDon"), result.getString("maNV"),
                        result.getDate("ngayLap"), result.getString("maKhachHang"),
                        result.getDouble("tongTien"));

                hoaDons.add(hoaDon);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hoaDons;
    }

    //Get hóa đơn theo năm
    public List<HoaDon> getHoaDonTheoNam(int nam) {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE YEAR(ngayLap) = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(nam));
            result = prepare.executeQuery();

            while (result.next()) {
                hoaDon = new HoaDon(result.getString("maHoaDon"), result.getString("maNV"),
                        result.getDate("ngayLap"), result.getString("maKhachHang"),
                        result.getDouble("tongTien"));

                hoaDons.add(hoaDon);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hoaDons;
    }

    //Get hóa đơn theo tháng, năm
    public List<HoaDon> getHoaDonTheoThangNam(int thang, int nam) {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE MONTH(ngayLap) = ? AND YEAR(ngayLap) = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(thang));
            prepare.setString(2, String.valueOf(nam));
            result = prepare.executeQuery();

            while (result.next()) {
                hoaDon = new HoaDon(result.getString("maHoaDon"), result.getString("maNV"),
                        result.getDate("ngayLap"), result.getString("maKhachHang"),
                        result.getDouble("tongTien"));

                hoaDons.add(hoaDon);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hoaDons;
    }

    //Get hóa đơn theo năm
    public List<HoaDon> getHoaDonTheoNgayLap(Date ngayLap) {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE ngayLap = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(ngayLap));
            result = prepare.executeQuery();

            while (result.next()) {
                hoaDon = new HoaDon(result.getString("maHoaDon"), result.getString("maNV"),
                        result.getDate("ngayLap"), result.getString("maKhachHang"),
                        result.getDouble("tongTien"));

                hoaDons.add(hoaDon);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hoaDons;
    }


    //Get tiền nhập theo tháng
    public List<Double> getTienNhapTheoThang(int thang) {
        List<Double> tienNhaps = new ArrayList<>();
        String sql = "SELECT (s.giaNhap*c.soLuong) AS tienNhap\n" +
                "FROM Sach s, CTHoaDon c, HoaDon h\n" +
                "WHERE s.maS = c.maSach AND c.maHoaDon = h.maHoaDon AND MONTH(h.ngayLap) = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(thang));
            result = prepare.executeQuery();

            while (result.next()) {
                tienNhaps.add(result.getDouble(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tienNhaps;
    }

    //Get tiền nhập theo năm
    public List<Double> getTienNhapTheoNam(int nam) {
        List<Double> tienNhaps = new ArrayList<>();
        String sql = "SELECT (s.giaNhap*c.soLuong) AS tienNhap\n" +
                "FROM Sach s, CTHoaDon c, HoaDon h\n" +
                "WHERE s.maS = c.maSach AND c.maHoaDon = h.maHoaDon AND YEAR(h.ngayLap) = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(nam));
            result = prepare.executeQuery();

            while (result.next()) {
                tienNhaps.add(result.getDouble(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tienNhaps;
    }

    //Get tiền nhập theo tháng, năm
    public List<Double> getTienNhapTheoThangNam(int thang, int nam) {
        List<Double> tienNhaps = new ArrayList<>();
        String sql = "SELECT (s.giaNhap*c.soLuong) AS tienNhap\n" +
                "FROM Sach s, CTHoaDon c, HoaDon h\n" +
                "WHERE s.maS = c.maSach AND c.maHoaDon = h.maHoaDon AND MONTH(h.ngayLap) = ? AND YEAR(h.ngayLap) = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(thang));
            prepare.setString(2, String.valueOf(nam));
            result = prepare.executeQuery();

            while (result.next()) {
                tienNhaps.add(result.getDouble(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tienNhaps;
    }
}
