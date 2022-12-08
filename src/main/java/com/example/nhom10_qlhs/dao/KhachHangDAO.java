package com.example.nhom10_qlhs.dao;

import com.example.nhom10_qlhs.GetData;
import com.example.nhom10_qlhs.connectdb.ConnectDB;
import com.example.nhom10_qlhs.entities.KhachHang;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private KhachHang khachHang;

    //Lấy danh sách khách hàng theo Mã
    public List<KhachHang> getDSKhachHangTheoMa(String maKH){
        List<KhachHang> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE maKH = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, maKH);
            result = prepare.executeQuery();
            while(result.next()){
                khachHang = new KhachHang(result.getString("maKH"),
                        result.getString("tenKH"),
                        result.getString("diaChi"),
                        result.getString("sdt"),
                        result.getString("email"),
                        result.getString("phai"),
                        result.getDate("namSinh"));
                khachHangList.add(khachHang);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return khachHangList;
    }

    //Lấy danh sách khách hàng theo tên
    public List<KhachHang> getDSKhachHangTheoTen(String tenKH){
        List<KhachHang> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE tenKH = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenKH);
            result = prepare.executeQuery();
            while(result.next()){
                khachHang = new KhachHang(result.getString("maKH"),
                        result.getString("tenKH"),
                        result.getString("diaChi"),
                        result.getString("sdt"),
                        result.getString("email"),
                        result.getString("phai"),
                        result.getDate("namSinh"));
                khachHangList.add(khachHang);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return khachHangList;
    }

    //Lấy danh sách khách hàng theo SDT
    public List<KhachHang> getDSKhachHangTheoSDT(String sdtKH){
        List<KhachHang> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE sdt = ? AND trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, sdtKH);
            result = prepare.executeQuery();
            while(result.next()){
                khachHang = new KhachHang(result.getString("maKH"),
                        result.getString("tenKH"),
                        result.getString("diaChi"),
                        result.getString("sdt"),
                        result.getString("email"),
                        result.getString("phai"),
                        result.getDate("namSinh"));
                khachHangList.add(khachHang);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return khachHangList;
    }


    //Lấy danh sách tất cả khách hàng
    public List<KhachHang> getAllKhachHang(){
        List<KhachHang> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE trangThai = 1";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while(result.next()){
                khachHang = new KhachHang(result.getString("maKH"),
                        result.getString("tenKH"),
                        result.getString("diaChi"),
                        result.getString("sdt"),
                        result.getString("email"),
                        result.getString("phai"),
                        result.getDate("namSinh"));
                khachHangList.add(khachHang);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return khachHangList;
    }
    public int demSoKhachHang(){
        String sql = "SELECT COUNT(*) FROM KhachHang";
        int soKH = 0;
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            boolean rs = result.next();
            if (result.next()) {
                soKH = result.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return soKH;
    }

    public Boolean themKhachHang(String maKH, String tenKH, String diaChi, String sdt, String email, String phai, String namSinh, int trangThai){
        String sql = "  INSERT INTO KhachHang (maKH, tenKH, diaChi, sdt, email, phai, namSinh, trangThai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql); // đưa chuỗi sql vô 1 biến prepare để đưa giá trị cần trong câu query
            //Gán giá trị vào từng dấu chấm hỏi trong câu query sql thứ tự tăng dần từ trái sang phải
            prepare.setString(1, maKH);
            prepare.setString(2, tenKH);
            prepare.setString(3, diaChi);
            prepare.setString(4, sdt);
            prepare.setString(5, email);
            prepare.setString(6, phai);
            prepare.setString(7, namSinh);
            prepare.setInt(8, trangThai);
            boolean result = prepare.execute();//Thực thi truy vấn sql

            if(!result){ //Nếu thực thi thành công thì xuất thông báo
                return true;
            }else { //Sai thì xuất lỗi
                return false;
            }
        }catch (SQLException ex) {
            return false;
        }
    }

    //Cập nhật trạng thái khách hàng đã xóa
    public void capNhatTrangThai(int trangThai, String maKH){//Sau khi đưa trạng thái và maKH vào đây thì
        String sql = "UPDATE KhachHang SET trangThai = ? WHERE maKH = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);//đưa câu truy vấn vô hàm prepare
            //set dữ liệu vào từng dấu chấm hỏi ở câu sql trên
            prepare.setInt(1, trangThai);
            prepare.setString(2, maKH);
            boolean result = prepare.execute();//thực thi truy vấn
            //Sau khi làm xong hàm này thì sẽ đưa khachHangTemp vô list bị xóa
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Cập nhật thông tin khách hàng
    public void capNhatThongTinKhachHang(String maKH,String tenKH, Date namSinh, String phai, String diaChi, String email, String sdt){
        String sql = "  UPDATE KhachHang " +
                "SET tenKH = ?, namSinh = ?, phai = ?, diaChi = ?, email = ?, sdt = ? " +
                "WHERE maKH = ?";
        try {
            connect = ConnectDB.connect();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tenKH);
            if(namSinh != null){
                prepare.setDate(2, namSinh);
            }
            prepare.setString(3, phai);
            prepare.setString(4, diaChi);
            prepare.setString(5, email);
            prepare.setString(6, sdt);
            prepare.setString(7, maKH);
            prepare.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
