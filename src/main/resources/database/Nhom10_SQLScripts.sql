CREATE DATABASE QLNhaSach
GO
USE QLNhaSach
GO
 CREATE TABLE NhanVien(
   maNV  NVARCHAR(50) primary key ,
   tenNV NVARCHAR(80) NOT NULL,
   diaChi NVARCHAR(80) NOT NULL,
   namSinh DATE,
   sdt VARCHAR(12) ,
   CMND NVARCHAR(50),
   phai NVARCHAR(10) NOT null,
   chucVu NVARCHAR (50) NOT NULL

)
GO
CREATE TABLE TaiKhoan(
   taiKhoan NVARCHAR(50) primary key foreign key references NhanVien(maNV),
   matKhau NVARCHAR(50) NOT NULL,
   loaiTK NVARCHAR(50) NOT NULL
)
GO
 CREATE TABLE KhachHang(
   maKH  NVARCHAR (50) primary key,
   tenKH NVARCHAR (80) NOT NULL,  
   diaChi NVARCHAR (80) NOT NULL,
   sdt VARCHAR(12) NOT NULL,
   email NVARCHAR(50),
   phai NVARCHAR(10)
)
GO
CREATE TABLE NhaCungCap(
   maNCC NVARCHAR(50) primary key,
   tenNCC NVARCHAR (80) NOT NULL,
   diaChi NVARCHAR (80)  NULL,
   sdt NVARCHAR (50)  NULL,
   email nvarchar(50) null
)
GO
 CREATE TABLE LoaiS(
   maLoai  NVARCHAR (50) primary key,
   tenLoai NVARCHAR (80) NOT NULL
)
GO
CREATE TABLE NhaXuatBan(
   maNXB NVARCHAR(50) primary key,
   tenNXB NVARCHAR (80) NOT NULL,
   diaChi NVARCHAR (80)  NULL,
   sdt NVARCHAR (50)  NULL,
   email nvarchar(50) null
)
GO
CREATE TABLE TacGia(
   maTG NVARCHAR(50) primary key,
   tenTG NVARCHAR (80) NOT NULL,
   sdt NVARCHAR (50)  NULL,
   email nvarchar(50) null,
   diaChi NVARCHAR (80)  NULL,
)
GO
CREATE TABLE Sach(
   maSach NVARCHAR(10) primary key,
   tenSach NVARCHAR (80) NOT NULL,
   soLuong int NOT NULL,
   giaNhap money NOT NULL,
   nhaXuatBan NVARCHAR(50) NOT NULL foreign key references NhaXuatBan(maNXB),
   namXuatBan int NULL,
   tacGia NVARCHAR(50) NOT NULL foreign key references TacGia(maTG),
   loaiSach NVARCHAR (50) NOT NULL foreign key references LoaiS(maLoai),
   nhaCungCap NVARCHAR(50) foreign key references NhaCungCap(maNCC),
   giaBan money NOT NULL,
)
GO
 CREATE TABLE HoaDon(
   maHoaDon  NVARCHAR (50) primary key,
   maNhanVien NVARCHAR (50) NOT NULL foreign key references NhanVien(maNV),  
   ngayLap DATE NOT NULL,
   maKhachHang NVARCHAR (50) foreign key references KhachHang(maKH),
   tongTien money NOT NULL
)
GO
 CREATE TABLE CTHoaDon(
   maHoaDon  NVARCHAR (50) not null ,
   maSach NVARCHAR(10) not null ,
   soLuong int,
   VAT float,
   donGia money,
   thanhTien money
)
GO

ALTER TABLE CTHoaDon ADD CONSTRAINT CTHD_PK PRIMARY KEY (maHoaDon,maSach);
Alter Table CTHoaDon add constraint CTHD_FK foreign key (maHoaDon) references HoaDon (maHoaDon)
Alter Table CTHoaDon add constraint CTHDS_FK foreign key (maSach) references Sach (maSach)

--Thêm tài khoản
INSERT INTO TaiKhoan (taiKhoan, matKhau, loaiTK) VALUES ('QL001','admin', N'Quản lý')
--Thêm nhân viên
INSERT INTO NhanVien (maNV, tenNV, diaChi, namSinh, sdt, CMND, phai, chucVu) VALUES ('QL001', N'Hồ Phước Duy', N'1 Quang Trung, 11, Gò Vấp, TPHCM', '2000-10-26', '0123456789', '01234567891', N'Nam', N'Quản lý')
--Thêm khách hàng
INSERT INTO KhachHang (maKH, tenKH, diaChi, sdt, email, phai) VALUES ('KH001',N'Nguyễn Văn Tú', N'2 Nguyễn Văn Bảo, 10, Gò Vấp, TPHCM', '0123446798', 'tu@gmail.com', 'Nam')
