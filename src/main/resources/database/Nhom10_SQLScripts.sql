﻿------------------------------------Reset Database--------------------------------------------------
USE MASTER
DROP DATABASE QLNhaSach
------------------------------Tạo và sử dụng Database QLNhaSach--------------------------------
CREATE DATABASE QLNhaSach
GO
USE QLNhaSach
GO
-------------------------------Tạo chuỗi tăng số tự động-------------------------------------------
CREATE SEQUENCE ChuoiTang
AS INT
START WITH 1
INCREMENT BY 1;
----------------------------Tạo bảng nhân viên------------------------------
 CREATE TABLE NhanVien( 
   maNV VARCHAR(20) DEFAULT 'NV-' + FORMAT((NEXT VALUE FOR ChuoiTang), '000000000', 'en-US') primary key,
   tenNV NVARCHAR(80) NOT NULL,
   diaChi NVARCHAR(80) NOT NULL,
   namSinh DATE,
   sdt VARCHAR(12) ,
   CMND NVARCHAR(50),
   phai NVARCHAR(10) NOT null,
   chucVu NVARCHAR (50) NOT NULL,
   ngayVaoLam Date NOT NULL
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho tài khoản-------------------------------------------
CREATE SEQUENCE ChuoiTang1
AS INT
START WITH 1
INCREMENT BY 1;
-----------------------------------Tạo bảng tài khoản-------------------------------------
CREATE TABLE TaiKhoan(
   maTaiKhoan VARCHAR(20) DEFAULT 'TK-' + FORMAT((NEXT VALUE FOR ChuoiTang1), '000000000', 'en-US') foreign key references NhanVien(maNV) primary key,
   tenTaiKhoan VARCHAR(50),
   matKhau NVARCHAR(50) NOT NULL,
   loaiTK NVARCHAR(50) NOT NULL
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho khách hàng-------------------------------------------
CREATE SEQUENCE ChuoiTang2
AS INT
START WITH 1
INCREMENT BY 1;
--------------------------------------Tạo bảng Khách hàng---------------------------------
 CREATE TABLE KhachHang(
   maKH VARCHAR(20) DEFAULT 'KH-' + FORMAT((NEXT VALUE FOR ChuoiTang2), '000000000', 'en-US') primary key,
   tenKH NVARCHAR (80) NOT NULL,  
   diaChi NVARCHAR (80) NOT NULL,
   sdt VARCHAR(12) NOT NULL,
   email NVARCHAR(50),
   phai NVARCHAR(10),
   namSinh Date
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho nhà cung cấp------------------------------------------
CREATE SEQUENCE ChuoiTang3
AS INT
START WITH 1
INCREMENT BY 1;
------------------------------------Tạo bảng nhà cung cấp----------------------------------
CREATE TABLE NhaCungCap(
   maNCC VARCHAR(20) DEFAULT 'NCC-' + FORMAT((NEXT VALUE FOR ChuoiTang3), '000000000', 'en-US') primary key,
   tenNCC NVARCHAR (80) NOT NULL,
   diaChi NVARCHAR (80) NULL,
   sdt NVARCHAR (50)  NULL,
   email nvarchar(50) NULL
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho loại sách------------------------------------------
CREATE SEQUENCE ChuoiTang4
AS INT
START WITH 1
INCREMENT BY 1;
---------------------------------Tạo bảng loại sách----------------------------------------
 CREATE TABLE LoaiS(
   maLoai VARCHAR(20) DEFAULT 'LS-' + FORMAT((NEXT VALUE FOR ChuoiTang4), '000000000', 'en-US') primary key,
   tenLoai NVARCHAR (80) NOT NULL
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho nhà xuất bản------------------------------------------
CREATE SEQUENCE ChuoiTang5
AS INT
START WITH 1
INCREMENT BY 1;
---------------------------------Tạo bảng Nhà xuất bản---------------------------------------
CREATE TABLE NhaXuatBan(
   maNXB VARCHAR(20) DEFAULT 'NXB-' + FORMAT((NEXT VALUE FOR ChuoiTang5), '000000000', 'en-US') primary key,
   tenNXB NVARCHAR (80) NOT NULL,
   diaChi NVARCHAR (80)  NULL,
   sdt NVARCHAR (50)  NULL,
   email nvarchar(50) null
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho tác giả ------------------------------------------
CREATE SEQUENCE ChuoiTang6
AS INT
START WITH 1
INCREMENT BY 1;
----------------------------------------Tạo bảng Tác giả--------------------------------------------------
CREATE TABLE TacGia(
   maTG VARCHAR(20) DEFAULT 'TG-' + FORMAT((NEXT VALUE FOR ChuoiTang6), '000000000', 'en-US') primary key,
   tenTG NVARCHAR (80) NOT NULL,
   sdt NVARCHAR (50)  NULL,
   email nvarchar(50) null,
   diaChi NVARCHAR (80)  NULL,
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho sách------------------------------------------
CREATE SEQUENCE ChuoiTang7
AS INT
START WITH 1
INCREMENT BY 1;
-----------------------------------------Tạo bảng Sách----------------------------------------------------
CREATE TABLE Sach(
   maS VARCHAR(20) DEFAULT 'S-' + FORMAT((NEXT VALUE FOR ChuoiTang7), '000000000', 'en-US') primary key,
   tenSach NVARCHAR (80) NOT NULL,
   soLuong INT NOT NULL,
   giaNhap MONEY NOT NULL,
   nhaXuatBan VARCHAR(20) NOT NULL foreign key references NhaXuatBan(maNXB),
   namXuatBan INT NULL,
   tacGia VARCHAR(20) NOT NULL foreign key references TacGia(maTG),
   loaiSach VARCHAR(20) NOT NULL foreign key references LoaiS(maLoai),
   nhaCungCap VARCHAR(20) NOT NULL foreign key references NhaCungCap(maNCC),
   giaBan money NOT NULL,
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho hóa đơn------------------------------------------
CREATE SEQUENCE ChuoiTang8
AS INT
START WITH 1
INCREMENT BY 1;
-----------------------------------------Tạo bảng Hóa đơn----------------------------------------------------
 CREATE TABLE HoaDon(
   maHoaDon VARCHAR(20) DEFAULT 'HD-' + FORMAT((NEXT VALUE FOR ChuoiTang8), '000000000', 'en-US') primary key,
   maNV VARCHAR(20) NOT NULL foreign key references NhanVien(maNV),
   maNhanVien NVARCHAR(50),
   ngayLap DATE NOT NULL,
   maKhachHang VARCHAR(20) NOT NULL foreign key references KhachHang(maKH),
   tongTien MONEY NOT NULL
)
GO
-----------------------------------------Tạo bảng CTHD----------------------------------------------------
 CREATE TABLE CTHoaDon(
   maHoaDon VARCHAR(20) NOT NULL,
   maSach VARCHAR(20) NOT NULL,
   soLuong INT,
   VAT FLOAT,
   donGia MONEY,
   thanhTien MONEY
)
GO
ALTER TABLE CTHoaDon ADD CONSTRAINT CTHD_PK PRIMARY KEY (maHoaDon,maSach);
ALTER TABLE CTHoaDon ADD CONSTRAINT CTHD_FK foreign key (maHoaDon) references HoaDon (maHoaDon)
ALTER TABLE CTHoaDon ADD CONSTRAINT CTHDS_FK foreign key (maSach) references Sach (maS)

--Thêm tài khoản
INSERT INTO TaiKhoan (taiKhoan, matKhau, loaiTK) VALUES ('QL001','admin', N'Quản lý')
--Thêm nhân viên
select MAX(maNV) AS maNV from NhanVien
INSERT INTO NhanVien(tenNV, diaChi, namSinh, sdt, CMND, phai, chucVu, ngayVaoLam) VALUES (N'Hồ Phước Duy', N'1 Quang Trung, 11, Gò Vấp, TPHCM', '2000-10-26', '0123456789', '01234567891', N'Nam', N'Quản lý', '2022-12-2')
--Thêm khách hàng
SELECT * FROM KhachHang
SELECT MAX(maKH) AS maKH FROM KhachHang
INSERT INTO KhachHang (tenKH, diaChi, sdt, email, phai, namSinh) VALUES (N'Nguyễn Văn Tú', N'2 Nguyễn Văn Bảo, 10, Gò Vấp, TPHCM', '0123446798', 'tu@gmail.com', 'Nam', '2002-10-26')
  --Lấy ra Hóa đơn có mã hóa đơn theo ngày
  
select 'HD' + MaHD +  CONVERT(VARCHAR(10), FORMAT(ROW_NUMBER() OVER(PARTITION BY NgayLap ORDER BY MaHD),'0000','en-US')) AS MaHD from tabletest
--------------------------------------FUNCTION CONVERT DATE--------------------------------------------------------
CONVERT(VARCHAR(10), FORMAT(CURDATE(), 'ddMMyyyy', 'en-US'))