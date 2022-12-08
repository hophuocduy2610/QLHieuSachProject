------------------------------------Reset Database--------------------------------------------------
USE MASTER
GO
DROP DATABASE QLNhaSach
GO
------------------------------Tạo và sử dụng Database QLNhaSach--------------------------------
CREATE DATABASE QLNhaSach
GO
USE QLNhaSach
GO
----------------------------Tạo bảng nhân viên------------------------------
 CREATE TABLE NhanVien( 
   maNV VARCHAR(200) primary key,
   tenNV NVARCHAR(80) NOT NULL,
   diaChi NVARCHAR(250) NOT NULL,
   namSinh DATE,
   sdt VARCHAR(12) ,
   CMND NVARCHAR(50),
   phai NVARCHAR(10) NOT null,
   chucVu NVARCHAR (50) NOT NULL,
   ngayVaoLam Date NOT NULL,
   trangThai BIT
)

GO
-----------------------------------Tạo bảng tài khoản-------------------------------------
CREATE TABLE TaiKhoan(	
   maTaiKhoan VARCHAR(200) foreign key references NhanVien(maNV) primary key,
   tenTaiKhoan VARCHAR(50),
   matKhau NVARCHAR(50) NOT NULL,
   loaiTK NVARCHAR(50) NOT NULL,
   trangThai BIT
)

GO
--------------------------------------Tạo bảng Khách hàng---------------------------------
 CREATE TABLE KhachHang(
   maKH VARCHAR(200) primary key,
   tenKH NVARCHAR (80) NOT NULL,  
   diaChi NVARCHAR (250) NOT NULL,
   sdt VARCHAR(12) NOT NULL,
   email VARCHAR(50),
   phai NVARCHAR(10),
   namSinh Date,
   trangThai BIT 
)
GO
------------------------------------Tạo bảng nhà cung cấp----------------------------------
CREATE TABLE NhaCungCap(
   maNCC VARCHAR(200) primary key,
   tenNCC NVARCHAR (250) NOT NULL,
   diaChi NVARCHAR (250) NULL,
   sdt NVARCHAR (50)  NULL,
   email VARCHAR(50) NULL,
   trangThai BIT
)
GO

---------------------------------Tạo bảng loại sách----------------------------------------
 CREATE TABLE LoaiS(
   maLoai VARCHAR(100) primary key,
   tenLoai NVARCHAR (80) NOT NULL,
   trangThai BIT
)
GO

---------------------------------Tạo bảng Nhà xuất bản---------------------------------------
CREATE TABLE NhaXuatBan(
   maNXB VARCHAR(200) primary key,
   tenNXB NVARCHAR (80) NOT NULL,
   diaChi NVARCHAR (250)  NULL,
   sdt NVARCHAR (50)  NULL,
   email VARCHAR(50) null,
   trangThai BIT
)
GO

----------------------------------------Tạo bảng Tác giả--------------------------------------------------
CREATE TABLE TacGia(
   maTG VARCHAR(200) primary key,
   tenTG NVARCHAR (80) NOT NULL,
   sdt NVARCHAR (50)  NULL,
   email VARCHAR(50) null,
   diaChi NVARCHAR (80)  NULL,
   trangThai BIT
)
GO

-----------------------------------------Tạo bảng Sách----------------------------------------------------
CREATE TABLE Sach(
   maS VARCHAR(200) primary key,
   tenSach NVARCHAR (150) NOT NULL,
   soLuong INT NOT NULL,
   giaNhap MONEY NOT NULL,
   nhaXuatBan VARCHAR(200) NOT NULL,
   namXuatBan INT NULL,
   tacGia VARCHAR(200) NOT NULL,
   loaiSach VARCHAR(100) NOT NULL,
   nhaCungCap VARCHAR(200) NOT NULL,
   ngayNhap DATE NOT NULL,
   giaBan money NOT NULL,
   VAT DECIMAL NOT NULL,
   hinhAnhSach VARCHAR(150) NOT NULL,
   trangThai BIT
)
GO

-----------------------------------------Tạo bảng Hóa đơn----------------------------------------------------
 CREATE TABLE HoaDon(
   maHoaDon VARCHAR(200) primary key,
   maNV VARCHAR(200) NOT NULL foreign key references NhanVien(maNV),
   maKhachHang VARCHAR(200) NOT NULL foreign key references KhachHang(maKH),
   ngayLap DATE NOT NULL,
   tongTien MONEY NOT NULL,
   trangThai BIT
)
GO
-----------------------------------------Tạo bảng CTHD----------------------------------------------------
 CREATE TABLE CTHoaDon(
   maHoaDon VARCHAR(200) NOT NULL,
   maSach VARCHAR(200) NOT NULL,
   soLuong INT,
   donGia MONEY,
   thanhTien MONEY,
   trangThai BIT
)
GO
ALTER TABLE CTHoaDon ADD CONSTRAINT CTHD_PK PRIMARY KEY (maHoaDon,maSach);
ALTER TABLE CTHoaDon ADD CONSTRAINT CTHD_FK foreign key (maHoaDon) references HoaDon (maHoaDon)
ALTER TABLE CTHoaDon ADD CONSTRAINT CTHDS_FK foreign key (maSach) references Sach (maS)

ALTER TABLE Sach ADD CONSTRAINT NXB_FK foreign key (nhaXuatBan) references NhaXuatBan (maNXB)
ALTER TABLE Sach ADD CONSTRAINT TG_FK foreign key (tacGia) references TacGia (maTG)
ALTER TABLE Sach ADD CONSTRAINT LS_FK foreign key (loaiSach) references LoaiS (maLoai)
ALTER TABLE Sach ADD CONSTRAINT NCC_FK foreign key (nhaCungCap) references NhaCungCap (maNCC)

-------------------------------------Thêm tài khoản--------------------------------------
SELECT * FROM TaiKhoan
INSERT INTO TaiKhoan (taiKhoan, matKhau, loaiTK) VALUES ('QL001','admin', N'Quản lý')
SELECT * FROM Sach
Select * from NhaXuatBan
select * from LoaiS
select * from NhaCungCap
SELECT * FROM HoaDon
SELECT * FROM CTHoaDon
SELECT maSach, Sach.tenSach, CTHoaDon.soLuong, donGia, thanhTien  FROM CTHoaDon, Sach WHERE CTHoaDon.maSach = Sach.maS AND maHoaDon = 'HD-000000006'

-----------------------------------------Thêm nhân viên------------------------------------------------
SELECT * FROM NhanVien
SELECT * FROM NhanVien WHERE maNV IN  (SELECT maTaiKhoan FROM TaiKhoan WHERE tenTaiKhoan = 'NV002107102022')
select MAX(maNV) AS maNV from NhanVien
INSERT INTO NhanVien(tenNV, diaChi, namSinh, sdt, CMND, phai, chucVu, ngayVaoLam) VALUES (N'Hồ Phước Duy', N'1 Quang Trung, 11, Gò Vấp, TPHCM', '2000-10-26', '0123456789', '01234567891', N'Nam', N'Quản lý', '2022-12-2')

----------------------------------------------------Thêm khách hàng----------------------------------------------------
SELECT * FROM KhachHang
SELECT * FROM TaiKhoan
SELECT * FROM TacGia
SELECT * FROM Sach
SELECT * from NhaXuatBan
SELECT Sach.maS, tenSach, soLuong, NhaXuatBan.tenNXB, TacGia.tenTG, LoaiS.tenLoai, giaNhap, giaBan, namXuatBan, NhaCungCap.tenNCC
FROM Sach, NhaXuatBan, TacGia, LoaiS, NhaCungCap
WHERE Sach.nhaXuatBan = NhaXuatBan.maNXB AND Sach.tacGia = TacGia.maTG 
AND Sach.loaiSach = LoaiS.maLoai AND Sach.trangThai = 1
AND Sach.nhaCungCap = NhaCungCap.maNCC
SELECT MAX(maKH) AS maKH FROM KhachHang
INSERT INTO KhachHang (tenKH, diaChi, sdt, email, phai, namSinh, trangThai) VALUES (N'Nguyễn Hữu Tú', N'2 Nguyễn Văn Bảo, 10, Gò Vấp, TPHCM', '0123446798', 'tu@gmail.com', 'Nam', '2002-10-26', 1)

--------------------------------------------------Thêm loại sách------------------------------------------------------
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00001', N'Tiểu thuyết')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00002', N'Truyện ngắn - Tản văn')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00003', N'Light Novel')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00004', N'Ngôn tình')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00005', N'Truyện trinh thám - Kiếm hiệp')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00006', N'Huyền bí - Giả tưởng - Kinh dị' )
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00007', N'Tác phẩm kinh điển')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00008', N'Phóng sự - Ký sự - Phê bình văn học')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00009', N'Thơ ca, tục ngữ, ca dao, thành ngữ')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00010', N'Tác giả - tác phẩm')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00011', N'12 cung hoàng đạo')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00012', N'Du ký')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00013', N'Tuổi teen')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00014', N'Hài hước - Truyện cười')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00015', N'Sách Ảnh')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00016', N'Thể loại khác')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00017', N'Quản trị - Lãnh đạo')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00018', N'Nhân vật - Bài học kinh doanh')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00019', N'Marketing - Bán hàng')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00020', N'Khởi nghiệp - Làm giàu')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00021', N'Phân tích kinh tế')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00022', N'Tài chính ngân hàng')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00023', N'Chứng khoán - Bất động sản - Đầu tư')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00024', N'Kế toán - Kiểm toán - Thuế')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00025', N'Nhân sự - Việc làm')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00026', N'Ngoại thương')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00027', N'Truyện thiếu nhi')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00028', N'Kiến thức - Kỹ năng sống cho trẻ')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00029', N'Kiến thức bách khoa')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00030', N'Tô màu - Luyện chữ')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00031', N'Từ điển thiếu nhi')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00032', N'Flashcard - Thẻ học thông minh')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00033', N'Tạp chí thiếu nhi')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00034', N'Sách nói')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00035', N'Sách tham khảo')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00036', N'Mẫu giáo')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00037', N'Sách giáo khoa')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00038', N'Sách giáo viên')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00039', N'Đại học')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00040', N'Kỹ năng sống')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00041', N'Tâm lý')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00042', N'Sách cho tuổi mới lớn')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00043', N'Rèn luyện nhân cách')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00044', N'Chicken Soup - Hạt giống tâm hồn')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00045', N'Manga')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00046', N'Comic - Truyện tranh')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00047', N'Sách ngoại ngữ tiếng Anh')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00048', N'Sách ngoại ngữ tiếng Nhật')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00049', N'Sách ngoại ngữ tiếng Hoa')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00050', N'Sách ngoại ngữ tiếng Hàn')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00051', N'Sách ngoại ngữ tiếng Việt cho người nước ngoài')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00052', N'Sách ngoại ngữ tiếng Pháp')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00053', N'Sách ngoại ngữ tiếng Đức')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00054', N'Y học')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00055', N'Khoa học khác')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00056', N'Tin học')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00057', N'Nông, lâm, ngư nghiệp')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00058', N'Giáo dục')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00059', N'Khoa học vũ trụ')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00060', N'Điện, điện tử')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00061', N'Thiết kế - Kiến trúc - Xây dựng')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00062', N'Lịch sử')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00063', N'Địa lý')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00064', N'Tôn giáo')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00065', N'Cẩm nang làm Cha Mẹ')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00066', N'Phát triển kỹ năng- Trí tuệ cho trẻ')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00067', N'Phương pháp giáo dục trẻ các nước')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00068', N'Dinh dưỡng - Sức khỏe cho trẻ')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00069', N'Giáo dục trẻ tuổi teen')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00070', N'Dành cho mẹ bầu')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00071', N'Triết học - Lý luận chính trị')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00072', N'Luật - Văn bản dưới luật')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00073', N'Nhân vật và sự kiện')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00074', N'Đội - Đoàn - Đảng')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00075', N'Văn kiện')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00076', N'Nấu ăn')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00077', N'Món ăn bài thuốc')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00078', N'Khéo tay')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00079', N'Làm đẹp')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00080', N'Mẹo vặt cẩm nang')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00081', N'Câu chuyện cuộc đời')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00082', N'Nghệ thuật giải trí')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00083', N'Lịch sử')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00084', N'Chính trị')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00085', N'Kinh tế')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00086', N'Thể thao')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00087', N'Văn hóa - Nghệ thuật - Du lịch')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00088', N'Từ điển tiếng Anh-Anh, Anh-Việt, Việt-Anh')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00089', N'Từ điển tiếng Việt')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00090', N'Từ điển tiếng Nhật-Việt, Việt-Nhật')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00091', N'Từ điển Hán-Việt')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00092', N'Từ điển chuyên ngành')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00093', N'Từ điển tiếng Hàn-Việt, Việt-Hàn')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00094', N'Từ điển khác')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00095', N'Từ điển tiếng Đức-Việt, Việt-Đức')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00096', N'Từ điển tiếng Pháp Việt, Việt-Pháp')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00097', N'Phong thủy - Kinh dịch')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00098', N'Âm nhạc')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00099', N'Mỹ thuật')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00100', N'Thời trang')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00101', N'Thủ công - Tạo hình')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00102', N'Thể dục thể thao - giải trí')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00103', N'Báo - Tạp chí')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00104', N'Giáo trình ĐH, CĐ, THCN')
INSERT LoaiS (maLoai, tenLoai) VALUES ('LS00105', N'Làm vườn - Thú nuôi')
SELECT * FROM LoaiS
  ---------------------------------Thêm nhà  xuất bản-------------------------------------
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0001', N'Bách khoa Hà Nội', N'Nhà E – Ngõ 17 – Tạ Quang Bửu – Hai Bà Trưng – Hà Nội', '028.38684569', 'nxbbk@hust.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0002', N'Chính trị Quốc gia Sự thật', N'72 Trần Quốc Thảo - Phường Võ Thị Sáu - Q3 - Tp.HCM', '028.39325410', 'hochiminh@nxbctqg.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0003', N'Công Thương', N'Tầng 4 - Tòa nhà Bộ Công thương, số 655 Phạm Văn Đồng - Bắc Từ Liêm - Hà Nội', ' (84-4)3934.1562', 'nxbct@moit.gov.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0004', N'Công an nhân dân', N'100 Yết Kiêu, Phường Nguyễn Du, Quận Hai Bà Trưng, TP Hà Nội', '069.2342969', 'ngotuan38@gmail.com)')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0005', N'Dân trí', N' Số 9, ngõ 26, phố Hoàng Cầu, phường Ô Chợ Dừa, quận Đống Đa, Hà Nội', '(024).66860751', 'nxbdantri@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0006', N'Giao thông vận tải', N'80B Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '024-3.9423346 ', 'nxbgtvt@fpt.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0007', N'Giáo dục Việt Nam', N'Số 81 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '024.38220801', 'nxbgd@moet.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0008', N'Hàng hải', N'484 Lạch Tray, Kênh Dương, Lê Chân, TP Hải Phòng', '(+84).225.3829109', 'info@vimaru.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0009', N'Học viện Nông nghiệp', N'Trường Đại học Nông nghiệp Hà Nội - Thị trấn Trâu Quỳ, huyện Gia Lâm, Hà Nội', '+84(24)3876 0325', 'dlanh@vnua.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0010', N'Hồng Đức', N' Số 65, phố Tràng Thi, Phường Hàng Bông, Quận Hoàn Kiếm, Hà Nội', '0439260024', 'nhaxuatbanhongduc65@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0011', N'Hội Nhà văn', N'Số 65 Nguyễn Du, quận Hai Bà Trưng, Hà Nội', '(024)3822.2135', 'nxbhoinhavan65@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0012', N'Khoa học Tự nhiên và Công nghệ', N'Nhà A16 - Số 18 Hoàng Quốc Việt, Cầu Giấy, Hà Nội', '(+84)(24) 2214.9041', 'nxb@vap.ac.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0013', N'Khoa học và Kỹ thuật', N'Số 70 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '02438220686', 'nhaxuatbankhkt@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0014', N'Khoa học xã hội', N'26 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', '04.39719073', 'nxbkhxh@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0015', N'Kim Đồng', N'Số 55 Quang Trung, Nguyễn Du, Hai Bà Trưng, Hà Nội', '(+84) 1900571595', 'cskh_online@nxbkimdong.com.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0016', N'Kinh tế thành phố Hồ Chí Minh', N'279 Nguyễn Tri Phương – Phường 5 – Quận 10 – TP. Hồ Chí Minh', '(028) 38.575.466', 'nxb@ueh.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0017', N'Lao động', N'175 Giảng Võ, Q. Đống Đa, Hà Nội', '04.38515380', 'nxblaodong@yahoo.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0018', N'Lao động - Xã hội', N'Số 36 - Ngõ Hoà Bình 4 - Minh Khai - Hai Bà Trưng - Hà Nội', '024.36246913', 'nxblaodongxahoi@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0019', N'Lý luận Chính trị', N'56B Quốc Tử Giám, Đống Đa, Hà Nội', '043747.2541', 'nxbllct@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0020', N'Mỹ thuật', N'44 Hàm Long, Hoàn Kiếm, Hà Nội', '091 235 03 26', '')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0021', N'Nông nghiệp', N'Số 6, ngõ 167, Phương Mai, Đống Đa, Hà Nội', '04. 38253887', 'nxbnn@yahoo.com.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0022', N'Phụ nữ', N'39 Hàng Chuối, Q. Hai Bà Trưng, Hà Nội', '(024) 39710717', 'truyenthongvaprnxbpn@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0023', N'Quân đội nhân dân', N'23 Lý Nam Đế, Hoàn Kiếm, Hà Nội', '024.38455766', 'nxbqdnd@nxbqdnd.com.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0024', N'Sân khấu', N'51 Trần Hưng Đạo, Quận Hoàn Kiếm, Hà Nội', '9436501/6502', 'nxbsankhau1985@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0025', N'Thanh niên', N'143 Pasteur, Phường 6, Quận 3, Thành phố Hồ Chí Minh', '028 3910 6963', '')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0026', N'Thông tin và Truyền thông', N'Tầng 6, Tòa nhà Cục Tần số vô tuyến điện, 115 Trần Duy Hưng, Hà Nội', '024.35772139', 'nxb.tttt@mic.gov.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0027', N'Thông tấn', N'79 Lý Thường Kiệt - Quận Hoàn Kiếm - Hà Nội', ' 024.39332279', 'nhaxuatbanthongtan@vnanet.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0028', N'Thế giới', N'46 Trần Hưng Đạo, Hà Nội, Việt Nam', '(84-4) 38253841', 'thegioi@hn.vnn.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0029', N'Thể thao và Du lịch', N'Số 7, phố Trịnh Hoài Đức, Phường Cát Linh, Quận Đống đa, Hà Nội', '024 3845 6155', '')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0030', N'Thống kê', N'98 Thụy Khuê, Tây Hồ, Hà Nội', '024. 3847 4185', 'xuatbanthongke.kd@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0031', N'Thời đại', N'Nhà B15, Lô 2, Khu đô thị Mỹ Đình I, Phường Cầu Diễn, Quận Nam Từ Liêm, Hà Nội', '028 3820 8632', '')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0032', N'Tri thức', N'Tầng 1 - Tòa nhà VUSTA - 53 Nguyễn Du - Quận Hai Bà Trưng - Hà Nội - Việt Nam', '024 3944 7279', 'lienhe@nxbtrithuc.com.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0033', N'Tài chính', N'Số 7 Phan Huy Chú, quận Hoàn Kiếm, thành phố Hà Nội', '024.38262767', 'info@fph.gov.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0034', N'Tài nguyên - Môi trường và Bản đồ Việt Nam', N'85 Nguyễn Chí Thanh, phường Láng Hạ, quận Đống Đa, Hà Nội', '(84-024)38344108', 'Info@bando.com.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0035', N'Tôn giáo', N'53 Tràng Thi, Hàng Bông, Hoàn Kiếm, Hà Nội', '0804.8106', 'nhaxuatbantongiao@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0036', N'Tư pháp', N'Số 35 Trần Quốc Toản, Hoàn Kiếm, Hà Nội - Việt Nam', '(84-4) 62632073', 'nxbtp@moj.gov.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0037', N'Văn hóa - thông tin', N'Số 43 Lò Đúc, Quận Hai Bà Trưng, TP. Hà Nội', '', '')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0038', N'Văn hóa dân tộc', N'Số 19 Nguyễn Bỉnh Khiêm,Quận Hai Bà Trưng,TP. Hà Nội', '(024)-3.8263070', 'nxbvanhoadantoc@yahoo.com.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0039', N'Văn học', N'18 Nguyễn Trường Tộ - Ba Đình - Hà Nội', '024.37161518', 'info@nxbvanhoc.com.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0040', N'Xây dựng', N'37 Lê Đại Hành, Hai Bà Trưng, Tp. Hà Nội', '02437265180', 'banhang@nxbxaydung.com.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0041', N'Y học', N'Số 352 Đội Cấn, Phường Cống Vị, Quận Ba Đình, TP Hà Nội', '0934547168', 'xuatbanyhoc@fpt.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0042', N'Âm nhạc', N'Số 61 Lý Thái Tổ, Quận Hoàn Kiếm, TP. Hà Nội', '(024)-3.8256286', '')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0043', N'Đại học Công Nghiệp Thành phố Hồ Chí Minh', N'12 Nguyễn Văn Bảo, P4, Q. Gò Vấp, TP Hồ Chí Minh', ' 02838.940.390 (800)', '')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0044', N'Đại học Cần Thơ', N'Khu II, Trường Đại học Cần Thơ, Đường 3/2, P. Xuân Khánh, Q. Ninh Kiều, TP. Cần Thơ ', '(0292) 3839981', 'dvtho@ctu.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0045', N'Đại học Huế', N'Số 7 Hà Nội, thành phố Huế', '02343.837838', 'nxbdhhue@hueuni.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0046', N'Đại học Kinh tế Quốc dân', N'207 Đường Giải Phóng - Hà Nội', '024.36282487', 'nxb@neu.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0047', N'Đại học Quốc gia Hà Nội', N'16 Hàng Chuối, Hai Bà Trưng, Hà Nội', '024.3972.5997', 'nxb@vnu.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0048', N'Đại học Quốc gia Thành phố Hồ Chí Minh', N'Phòng 501, Nhà Điều hành ĐHQG-HCM, phường Linh Trung, quận Thủ Đức, TP Hồ Chí Minh', '028 6272 6361', 'phathanh.nxb@vnuhcm.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0049', N'Đại học Sư phạm', N'Tầng 6 Toà nhà số 128 Xuân Thuỷ, phường Dịch Vọng Hậu, Cầu Giấy, TP Hà Nội', '024.3754.7735', 'hanhchinh.nxb@hnue.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0050', N'Đại học Thái Nguyên', N'Phường Tân Thịnh – thành phố Thái Nguyên – tỉnh Thái Nguyên', '02083.546.116', 'nxb.dhtn@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0051', N'Đại học Vinh', N'Số 182 Lê Duẩn - TP. Vinh - Tỉnh Nghệ An', '(0238)3855269', 'nxbdhv@vinhuni.edu.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0052', N'Đại học sư phạm Thành phố Hồ Chí Minh', N'280 An Dương Vương, Phường 4, Quận 5, TPHCM', '(08)38 301303', 'nxbdhsphcm11@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0053', N'Hà Nội', N'Số 4, phố Tống Duy Tân, quận Hoàn Kiếm, Hà Nội', '024.38252916', 'vanthu_nxbhn@hanoi.gov.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0054', N'Hải Phòng', N'Số 5 Nguyễn Khuyến - P. Lương Khánh Thiện - Q.Ngô Quyền - TP. Hải Phòng', '(0225)3855871', '')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0055', N'Nghệ An', N'Số 37B, Lê Hồng Phong, thành phố Vinh, Nghệ An', '02383.844.748', 'nhaxuatbannghean@gmail.com')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0056', N'Phương Đông', N'497/10 Sư Vạn Hạnh, Phường 12, Quận 10, Hồ Chí Minh', '0838 683 930', '')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0057', N'Thanh Hóa', N'248 Trần Phú, P. Ba Đình, Thành phố Thanh Hóa, Việt Nam', '0373 853 548', '')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0058', N'Thuận Hóa', N'33 Chu Văn An, Phường Phú Hội, Thừa Thiên Huế , Việt Nam', '084 777 4910', '')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0059', N'Trẻ', N'161B Lý Chính Thắng, Phường Võ Thị Sáu, Quận 3 , TP. Hồ Chí Minh', '(84.028) 39316289', 'hopthubandoc@nxbtre.com.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0060', N'Tổng hợp Thành phố Hồ Chí Minh', N'62 Nguyễn Thị Minh Khai, phường Đa Kao, quận 1, TPHCM', '(028) 38 256 804', 'tonghop@nxbhcm.com.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0061', N'Văn hóa- Văn nghệ Thành phố Hồ Chí Minh', N'88-90 Ký Con, P. Nguyễn Thái Bình, Q. 1,Tp. Hồ Chí Minh (TPHCM)', '(028) 38216009', 'nxbvhvn@nxbvanhoavannghe.org.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0062', N'Đà Nẵng', N'Số 03 đường 30 Tháng 4, P. Hòa Cường Bắc, Q.Hải Châu, TP.Đà Nẵng', '(0236) 3.79.78.69', ' xuatban@nxbdanang.vn')
INSERT NhaXuatBan (maNXB, tenNXB, diaChi, sdt, email) VALUES ('NXB0063', N'Đồng Nai', N'1953J, Nguyễn Ái Quốc, phường Trung Dũng, thành phố Biên Hoà, tỉnh Đồng Nai', '02513.946530', 'nxbdongnai@hcm.vnn.vn')
SELECT * FROM NhaXuatBan
-----------------------------------------------------------------------Thêm nhà cung cấp---------------------------------------------------
INSERT INTO NhaCungCap (maNCC, tenNCC, diaChi, sdt, email) VALUES ('NCC0001', N'First News', N'11H Nguyễn Thị Minh Khai, Phường Bến Nghé, Quận 1, Tp. Hồ Chí Minh', '(84.28) 3822 79 79', 'triviet@firstnews.com.vn')
INSERT INTO NhaCungCap (maNCC, tenNCC, diaChi, sdt, email) VALUES ('NCC0002', N'Thái Hà Books', N'Lô B2, tổ dân phố số 1, phường Phúc Diễn, quận Bắc Từ Liêm, Hà Nội', '024 3793 0480', 'Sachthaiha@thaihabooks.com')
INSERT INTO NhaCungCap (maNCC, tenNCC, diaChi, sdt, email) VALUES ('NCC0003', N'Nhã Nam', N' 59 - Đỗ Quang - Trung Hòa - Cầu Giấy - Hà Nội', '0903244248', 'bookstore@nhanam.vn')
INSERT INTO NhaCungCap (maNCC, tenNCC, diaChi, sdt, email) VALUES ('NCC0004', N'ten', N'diachi', 'sdt', 'email')
SELECT * FROM NhaCungCap

----------------------------------------------------------- Thêm Tác giả-------------------------------------------------------
INSERT INTO TacGia (maTG, tenTG, sdt, email, diaChi) VALUES ('TG00001', N'Minh Niệm', '0123456789','minhniem@gmail.com', '1 Quang Trung')
INSERT INTO TacGia (maTG, tenTG, sdt, email, diaChi) VALUES ('TG00002', 'TS David J Lieberman', '0123456789','minhniem@gmail.com', '1 Quang Trung')
INSERT INTO TacGia (maTG, tenTG, sdt, email, diaChi) VALUES ('TG00003', 'Benjamin Graham', '0123456789','minhniem@gmail.com', '1 Quang Trung')
INSERT INTO TacGia (maTG, tenTG, sdt, email, diaChi) VALUES ('TG00004', 'Lê Rin', '0123456789','minhniem@gmail.com', '1 Quang Trung')
SELECT * FROM TacGia
---------------------------------------------------------------Thêm sách----------------------------------------------------
INSERT INTO Sach (maS, tenSach, soLuong, giaNhap, nhaXuatBan, namXuatBan, ngayNhap, tacGia, loaiSach, nhaCungCap, giaBan, hinhAnhSach, VAT, trangThai)
VALUES ('S000001', N'Hiểu về trái tim', 20, 100000, N'NXB0060', 2019, '2022-10-1', N'TG00001', N'LS00028', N'NCC0001', 140000, 'D:\PTUDBTLon\src\main\java\com\example\nhom10_qlhs\image\hieu-ve-trai-tim.png', 0.05, 1)

INSERT INTO Sach (maS, tenSach, soLuong, giaNhap, nhaXuatBan, namXuatBan, ngayNhap, tacGia, loaiSach, nhaCungCap, giaBan, hinhAnhSach, VAT, trangThai)
VALUES ('S000002', N'Đọc vị bất kì ai', 50, 50000, N'NXB0017', 2019, '2022-10-1', N'TG00002', N'LS00028', N'NCC0002', 80000, 'D:\PTUDBTLon\src\main\java\com\example\nhom10_qlhs\image\doc-vi-bat-ki-ai.jpg', 0.05, 1)

INSERT INTO Sach (maS, tenSach, soLuong, giaNhap, nhaXuatBan, namXuatBan, ngayNhap, tacGia, loaiSach, nhaCungCap, giaBan, hinhAnhSach, VAT, trangThai)
VALUES ('S000003', N'Nhà Đầu Tư Thông Minh (Tái Bản 2020)', 50, 100000, N'NXB0003', 2020, '2022-10-1', N'TG00003', N'LS00023', N'NCC0003', 145000, 'D:\PTUDBTLon\src\main\java\com\example\nhom10_qlhs\image\nha-dau-tu-thong-minh.jpg', 0.05, 1)

INSERT INTO Sach (maS, tenSach, soLuong, giaNhap, nhaXuatBan, namXuatBan, ngayNhap, tacGia, loaiSach, nhaCungCap, giaBan, hinhAnhSach, VAT, trangThai)
VALUES ('S000004', N'Việt Nam Miền Ngon', 35, 100000, N'NXB0018', 2019, '2022-10-1', N'TG00004', N'LS00076', N'NCC0002', 135000, 'D:\PTUDBTLon\src\main\java\com\example\nhom10_qlhs\image\viet-nam-mien-ngon.jpg', 0.05, 1)
---------------------------------Thêm hóa đơn-----------------------------------

--Lấy ra Hóa đơn có mã hóa đơn theo ngày
  
select 'HD' + MaHD +  CONVERT(VARCHAR(10), FORMAT(ROW_NUMBER() OVER(PARTITION BY NgayLap ORDER BY MaHD),'0000','en-US')) AS MaHD from tabletest
--------------------------------------FUNCTION CONVERT DATE--------------------------------------------------------
CONVERT(VARCHAR(10), FORMAT(CURDATE(), 'ddMMyyyy', 'en-US'))
--------------------------------------Công thức tính giá bán lẻ--------------------------------------------------
Giá bán lẻ = [(Giá vốn) / (100 – % lợi nhuận mong muốn)] x 100
Ví dụ: 1 sản phẩm giá gốc của bạn là 60.000 VND, bạn muốn thu lợi nhuận 50%, vậy thì bạn sẽ có giá bán là: 
[(60.000 / (100 – 50)] x 100 = 120.000 VND

 DELETE FROM KhachHang
WHERE maKH = 'KH-000000002'
select * from KhachHang