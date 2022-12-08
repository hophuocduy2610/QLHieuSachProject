package com.example.nhom10_qlhs.dao;

import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.entities.SachThongKe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CTHoaDonDAO {
    public Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    //Get số lượng sách theo ngày bán
    public int getSoLuongSachTheoNgayBan(Date ngayBan) {
        String sql = "SELECT SUM(c.soLuong)\n" +
                "FROM Sach s, CTHoaDon c, HoaDon h\n" +
                "WHERE s.maS = c.maSach AND c.maHoaDon = h.maHoaDon AND ngayLap = ?";
        int soLuongSachDaBan = 0;
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(ngayBan));
            result = prepare.executeQuery();
            if (result.next()) {
                soLuongSachDaBan = result.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return soLuongSachDaBan;
    }

    //Get tổng doanh thu bán sách theo ngày bán
    public Double getTongDoanhThuBanSachTheoNgayBan(Date ngayBan) {
        String sql = "SELECT SUM(c.soLuong*s.giaBan)\n" +
                "FROM Sach s, CTHoaDon c, HoaDon h\n" +
                "WHERE s.maS = c.maSach AND c.maHoaDon = h.maHoaDon AND ngayLap = ?";
        double soLuongSachDaBan = 0;
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(ngayBan));
            result = prepare.executeQuery();
            if (result.next()) {
                soLuongSachDaBan = result.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return soLuongSachDaBan;
    }

    public List<SachThongKe> getSachDaBan(Date ngayBan) {
        String sql = "SELECT c.maSach, s.tenSach, c.soLuong, s.giaBan, h.ngayLap, c.thanhTien\n" +
                    "FROM Sach s, CTHoaDon c, HoaDon h\n" +
                    "WHERE s.maS = c.maSach AND h.maHoaDon = c.maHoaDon AND h.ngayLap = ?";
        List<SachThongKe> sachThongKeList = new ArrayList<>();
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(ngayBan));
            result = prepare.executeQuery();
            while (result.next()) {
                String maSach = result.getString("maSach");
                String tenSach = result.getString("tenSach");
                int soLuong = result.getInt("soLuong");
                double giaBan = result.getDouble("giaBan");
                Date ngayLap = result.getDate("ngayLap");
                double thanhTien = result.getDouble("thanhTien");
                SachThongKe sachThongKe = new SachThongKe(maSach, tenSach, soLuong, giaBan, ngayLap, thanhTien);
                sachThongKeList.add(sachThongKe);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sachThongKeList;
    }
}
