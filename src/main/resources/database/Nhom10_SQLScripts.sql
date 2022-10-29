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
-------------------------------Tạo chuỗi tăng số tự động-------------------------------------------
CREATE SEQUENCE ChuoiTang
AS INT
START WITH 1
INCREMENT BY 1;
----------------------------Tạo bảng nhân viên------------------------------
 CREATE TABLE NhanVien( 
   maNV VARCHAR(20) DEFAULT 'NV-' + FORMAT((NEXT VALUE FOR ChuoiTang), '0000', 'en-US') primary key,
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
-------------------------------Tạo chuỗi tăng số tự động cho tài khoản-------------------------------------------
CREATE SEQUENCE ChuoiTang1
AS INT
START WITH 1
INCREMENT BY 1;
-----------------------------------Tạo bảng tài khoản-------------------------------------
CREATE TABLE TaiKhoan(	
   maTaiKhoan VARCHAR(20) DEFAULT 'TK-' + FORMAT((NEXT VALUE FOR ChuoiTang1), '00000', 'en-US') foreign key references NhanVien(maNV) primary key,
   tenTaiKhoan VARCHAR(50),
   matKhau NVARCHAR(50) NOT NULL,
   loaiTK NVARCHAR(50) NOT NULL,
   trangThai BIT
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
   diaChi NVARCHAR (250) NOT NULL,
   sdt VARCHAR(12) NOT NULL,
   email NVARCHAR(50),
   phai NVARCHAR(10),
   namSinh Date,
   trangThai BIT 
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho nhà cung cấp------------------------------------------
CREATE SEQUENCE ChuoiTang3
AS INT
START WITH 1
INCREMENT BY 1;
------------------------------------Tạo bảng nhà cung cấp----------------------------------
CREATE TABLE NhaCungCap(
   maNCC NVARCHAR(250) DEFAULT 'NCC-' + FORMAT((NEXT VALUE FOR ChuoiTang3), '00000', 'en-US') primary key,
   tenNCC NVARCHAR (80) NOT NULL,
   diaChi NVARCHAR (250) NULL,
   sdt NVARCHAR (50)  NULL,
   email nvarchar(50) NULL,
   trangThai BIT
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho loại sách------------------------------------------
CREATE SEQUENCE ChuoiTang4
AS INT
START WITH 1
INCREMENT BY 1;
---------------------------------Tạo bảng loại sách----------------------------------------
 CREATE TABLE LoaiS(
   maLoai NVARCHAR(100) DEFAULT 'LS-' + FORMAT((NEXT VALUE FOR ChuoiTang4), '000', 'en-US') primary key,
   tenLoai NVARCHAR (80) NOT NULL,
   trangThai BIT
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho nhà xuất bản------------------------------------------
CREATE SEQUENCE ChuoiTang5
AS INT
START WITH 1
INCREMENT BY 1;
---------------------------------Tạo bảng Nhà xuất bản---------------------------------------
CREATE TABLE NhaXuatBan(
   maNXB NVARCHAR(250) DEFAULT 'NXB-' + FORMAT((NEXT VALUE FOR ChuoiTang5), '000000', 'en-US') primary key,
   tenNXB NVARCHAR (80) NOT NULL,
   diaChi NVARCHAR (250)  NULL,
   sdt NVARCHAR (50)  NULL,
   email nvarchar(50) null,
   trangThai BIT
)
GO
-------------------------------Tạo chuỗi tăng số tự động cho tác giả ------------------------------------------
CREATE SEQUENCE ChuoiTang6
AS INT
START WITH 1
INCREMENT BY 1;
----------------------------------------Tạo bảng Tác giả--------------------------------------------------
CREATE TABLE TacGia(
   maTG NVARCHAR(150) DEFAULT 'TG-' + FORMAT((NEXT VALUE FOR ChuoiTang6), '000000000', 'en-US') primary key,
   tenTG NVARCHAR (80) NOT NULL,
   sdt NVARCHAR (50)  NULL,
   email nvarchar(50) null,
   diaChi NVARCHAR (80)  NULL,
   trangThai BIT
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
   tenSach NVARCHAR (150) NOT NULL,
   soLuong INT NOT NULL,
   giaNhap MONEY NOT NULL,
   nhaXuatBan NVARCHAR(250) NOT NULL,
   namXuatBan INT NULL,
   tacGia NVARCHAR(150) NOT NULL,
   loaiSach NVARCHAR(100) NOT NULL,
   nhaCungCap NVARCHAR(250) NOT NULL,
   giaBan money NOT NULL,
   hinhAnhSach VARCHAR(150) NOT NULL,
   trangThai BIT
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
   tongTien MONEY NOT NULL,
   trangThai BIT
)
GO
-----------------------------------------Tạo bảng CTHD----------------------------------------------------
 CREATE TABLE CTHoaDon(
   maHoaDon VARCHAR(20) NOT NULL,
   maSach VARCHAR(20) NOT NULL,
   soLuong INT,
   VAT FLOAT,
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

INSERT INTO TaiKhoan (taiKhoan, matKhau, loaiTK) VALUES ('QL001','admin', N'Quản lý')

-----------------------------------------Thêm nhân viên------------------------------------------------
select MAX(maNV) AS maNV from NhanVien
INSERT INTO NhanVien(tenNV, diaChi, namSinh, sdt, CMND, phai, chucVu, ngayVaoLam) VALUES (N'Hồ Phước Duy', N'1 Quang Trung, 11, Gò Vấp, TPHCM', '2000-10-26', '0123456789', '01234567891', N'Nam', N'Quản lý', '2022-12-2')

----------------------------------------------------Thêm khách hàng----------------------------------------------------
SELECT * FROM KhachHang
SELECT MAX(maKH) AS maKH FROM KhachHang
INSERT INTO KhachHang (tenKH, diaChi, sdt, email, phai, namSinh, trangThai) VALUES (N'Nguyễn Hữu Tú', N'2 Nguyễn Văn Bảo, 10, Gò Vấp, TPHCM', '0123446798', 'tu@gmail.com', 'Nam', '2002-10-26', 1)

--------------------------------------------------Thêm loại sách------------------------------------------------------
INSERT LoaiS (tenLoai) VALUES ( N'Tiểu thuyết')
INSERT LoaiS (tenLoai) VALUES (N'Truyện ngắn - Tản văn')
INSERT LoaiS (tenLoai) VALUES (N'Light Novel')
INSERT LoaiS (tenLoai) VALUES (N'Ngôn tình')
INSERT LoaiS (tenLoai) VALUES (N'Truyện trinh thám - Kiếm hiệp')
INSERT LoaiS (tenLoai) VALUES (N'Huyền bí - Giả tưởng - Kinh dị' )
INSERT LoaiS (tenLoai) VALUES (N'Tác phẩm kinh điển')
INSERT LoaiS (tenLoai) VALUES (N'Phóng sự - Ký sự - Phê bình văn học')
INSERT LoaiS (tenLoai) VALUES (N'Thơ ca, tục ngữ, ca dao, thành ngữ')
INSERT LoaiS (tenLoai) VALUES (N'Tác giả - tác phẩm')
INSERT LoaiS (tenLoai) VALUES (N'12 cung hoàng đạo')
INSERT LoaiS (tenLoai) VALUES (N'Du ký')
INSERT LoaiS (tenLoai) VALUES (N'Tuổi teen')
INSERT LoaiS (tenLoai) VALUES (N'Hài hước - Truyện cười')
INSERT LoaiS (tenLoai) VALUES (N'Sách Ảnh')
INSERT LoaiS (tenLoai) VALUES (N'Thể loại khác')
INSERT LoaiS (tenLoai) VALUES (N'Quản trị - Lãnh đạo')
INSERT LoaiS (tenLoai) VALUES (N'Nhân vật - Bài học kinh doanh')
INSERT LoaiS (tenLoai) VALUES (N'Marketing - Bán hàng')
INSERT LoaiS (tenLoai) VALUES (N'Khởi nghiệp - Làm giàu')
INSERT LoaiS (tenLoai) VALUES (N'Phân tích kinh tế')
INSERT LoaiS (tenLoai) VALUES (N'Tài chính ngân hàng')
INSERT LoaiS (tenLoai) VALUES (N'Chứng khoán - Bất động sản - Đầu tư')
INSERT LoaiS (tenLoai) VALUES (N'Kế toán - Kiểm toán - Thuế')
INSERT LoaiS (tenLoai) VALUES (N'Nhân sự - Việc làm')
INSERT LoaiS (tenLoai) VALUES (N'Ngoại thương')
INSERT LoaiS (tenLoai) VALUES (N'Truyện thiếu nhi')
INSERT LoaiS (tenLoai) VALUES (N'Kiến thức - Kỹ năng sống cho trẻ')
INSERT LoaiS (tenLoai) VALUES (N'Kiến thức bách khoa')
INSERT LoaiS (tenLoai) VALUES (N'Tô màu - Luyện chữ')
INSERT LoaiS (tenLoai) VALUES (N'Từ điển thiếu nhi')
INSERT LoaiS (tenLoai) VALUES (N'Flashcard - Thẻ học thông minh')
INSERT LoaiS (tenLoai) VALUES (N'Tạp chí thiếu nhi')
INSERT LoaiS (tenLoai) VALUES (N'Sách nói')
INSERT LoaiS (tenLoai) VALUES (N'Sách tham khảo')
INSERT LoaiS (tenLoai) VALUES (N'Mẫu giáo')
INSERT LoaiS (tenLoai) VALUES (N'Sách giáo khoa')
INSERT LoaiS (tenLoai) VALUES (N'Sách giáo viên')
INSERT LoaiS (tenLoai) VALUES (N'Đại học')
INSERT LoaiS (tenLoai) VALUES (N'Kỹ năng sống')
INSERT LoaiS (tenLoai) VALUES (N'Tâm lý')
INSERT LoaiS (tenLoai) VALUES (N'Sách cho tuổi mới lớn')
INSERT LoaiS (tenLoai) VALUES (N'Rèn luyện nhân cách')
INSERT LoaiS (tenLoai) VALUES (N'Chicken Soup - Hạt giống tâm hồn')
INSERT LoaiS (tenLoai) VALUES (N'Manga')
INSERT LoaiS (tenLoai) VALUES (N'Comic - Truyện tranh')
INSERT LoaiS (tenLoai) VALUES (N'Sách ngoại ngữ tiếng Anh')
INSERT LoaiS (tenLoai) VALUES (N'Sách ngoại ngữ tiếng Nhật')
INSERT LoaiS (tenLoai) VALUES (N'Sách ngoại ngữ tiếng Hoa')
INSERT LoaiS (tenLoai) VALUES (N'Sách ngoại ngữ tiếng Hàn')
INSERT LoaiS (tenLoai) VALUES (N'Sách ngoại ngữ tiếng Việt cho người nước ngoài')
INSERT LoaiS (tenLoai) VALUES (N'Sách ngoại ngữ tiếng Pháp')
INSERT LoaiS (tenLoai) VALUES (N'Sách ngoại ngữ tiếng Đức')
INSERT LoaiS (tenLoai) VALUES (N'Y học')
INSERT LoaiS (tenLoai) VALUES (N'Khoa học khác')
INSERT LoaiS (tenLoai) VALUES (N'Tin học')
INSERT LoaiS (tenLoai) VALUES (N'Nông, lâm, ngư nghiệp')
INSERT LoaiS (tenLoai) VALUES (N'Giáo dục')
INSERT LoaiS (tenLoai) VALUES (N'Khoa học vũ trụ')
INSERT LoaiS (tenLoai) VALUES (N'Điện, điện tử')
INSERT LoaiS (tenLoai) VALUES (N'Thiết kế - Kiến trúc - Xây dựng')
INSERT LoaiS (tenLoai) VALUES (N'Lịch sử')
INSERT LoaiS (tenLoai) VALUES (N'Địa lý')
INSERT LoaiS (tenLoai) VALUES (N'Tôn giáo')
INSERT LoaiS (tenLoai) VALUES (N'Cẩm nang làm Cha Mẹ')
INSERT LoaiS (tenLoai) VALUES (N'Phát triển kỹ năng- Trí tuệ cho trẻ')
INSERT LoaiS (tenLoai) VALUES (N'Phương pháp giáo dục trẻ các nước')
INSERT LoaiS (tenLoai) VALUES (N'Dinh dưỡng - Sức khỏe cho trẻ')
INSERT LoaiS (tenLoai) VALUES (N'Giáo dục trẻ tuổi teen')
INSERT LoaiS (tenLoai) VALUES (N'Dành cho mẹ bầu')
INSERT LoaiS (tenLoai) VALUES (N'Triết học - Lý luận chính trị')
INSERT LoaiS (tenLoai) VALUES (N'Luật - Văn bản dưới luật')
INSERT LoaiS (tenLoai) VALUES (N'Nhân vật và sự kiện')
INSERT LoaiS (tenLoai) VALUES (N'Đội - Đoàn - Đảng')
INSERT LoaiS (tenLoai) VALUES (N'Văn kiện')
INSERT LoaiS (tenLoai) VALUES (N'Nấu ăn')
INSERT LoaiS (tenLoai) VALUES (N'Món ăn bài thuốc')
INSERT LoaiS (tenLoai) VALUES (N'Khéo tay')
INSERT LoaiS (tenLoai) VALUES (N'Làm đẹp')
INSERT LoaiS (tenLoai) VALUES (N'Mẹo vặt cẩm nang')
INSERT LoaiS (tenLoai) VALUES (N'Câu chuyện cuộc đời')
INSERT LoaiS (tenLoai) VALUES (N'Nghệ thuật giải trí')
INSERT LoaiS (tenLoai) VALUES (N'Lịch sử')
INSERT LoaiS (tenLoai) VALUES (N'Chính trị')
INSERT LoaiS (tenLoai) VALUES (N'Kinh tế')
INSERT LoaiS (tenLoai) VALUES (N'Thể thao')
INSERT LoaiS (tenLoai) VALUES (N'Văn hóa - Nghệ thuật - Du lịch')
INSERT LoaiS (tenLoai) VALUES (N'Từ điển tiếng Anh-Anh, Anh-Việt, Việt-Anh')
INSERT LoaiS (tenLoai) VALUES (N'Từ điển tiếng Việt')
INSERT LoaiS (tenLoai) VALUES (N'Từ điển tiếng Nhật-Việt, Việt-Nhật')
INSERT LoaiS (tenLoai) VALUES (N'Từ điển Hán-Việt')
INSERT LoaiS (tenLoai) VALUES (N'Từ điển chuyên ngành')
INSERT LoaiS (tenLoai) VALUES (N'Từ điển tiếng Hàn-Việt, Việt-Hàn')
INSERT LoaiS (tenLoai) VALUES (N'Từ điển khác')
INSERT LoaiS (tenLoai) VALUES (N'Từ điển tiếng Đức-Việt, Việt-Đức')
INSERT LoaiS (tenLoai) VALUES (N'Từ điển tiếng Pháp Việt, Việt-Pháp')
INSERT LoaiS (tenLoai) VALUES (N'Phong thủy - Kinh dịch')
INSERT LoaiS (tenLoai) VALUES (N'Âm nhạc')
INSERT LoaiS (tenLoai) VALUES (N'Mỹ thuật')
INSERT LoaiS (tenLoai) VALUES (N'Thời trang')
INSERT LoaiS (tenLoai) VALUES (N'Thủ công - Tạo hình')
INSERT LoaiS (tenLoai) VALUES (N'Thể dục thể thao - giải trí')
INSERT LoaiS (tenLoai) VALUES (N'Báo - Tạp chí')
INSERT LoaiS (tenLoai) VALUES (N'Giáo trình ĐH, CĐ, THCN')
INSERT LoaiS (tenLoai) VALUES (N'Làm vườn - Thú nuôi')
SELECT * FROM LoaiS
  ---------------------------------Thêm nhà  xuất bản-------------------------------------
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Bách khoa Hà Nội', N'Nhà E – Ngõ 17 – Tạ Quang Bửu – Hai Bà Trưng – Hà Nội', '028.38684569', 'nxbbk@hust.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Chính trị Quốc gia Sự thật', N'72 Trần Quốc Thảo - Phường Võ Thị Sáu - Q3 - Tp.HCM', '028.39325410', 'hochiminh@nxbctqg.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Công Thương', N'Tầng 4 - Tòa nhà Bộ Công thương, số 655 Phạm Văn Đồng - Bắc Từ Liêm - Hà Nội', ' (84-4)3934.1562', 'nxbct@moit.gov.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Công an nhân dân', N'100 Yết Kiêu, Phường Nguyễn Du, Quận Hai Bà Trưng, TP Hà Nội', '069.2342969', 'ngotuan38@gmail.com)')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Dân trí', N' Số 9, ngõ 26, phố Hoàng Cầu, phường Ô Chợ Dừa, quận Đống Đa, Hà Nội', '(024).66860751', 'nxbdantri@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Giao thông vận tải', N'80B Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '024-3.9423346 ', 'nxbgtvt@fpt.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Giáo dục Việt Nam', N'Số 81 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '024.38220801', 'nxbgd@moet.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Hàng hải', N'484 Lạch Tray, Kênh Dương, Lê Chân, TP Hải Phòng', '(+84).225.3829109', 'info@vimaru.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Học viện Nông nghiệp', N'Trường Đại học Nông nghiệp Hà Nội - Thị trấn Trâu Quỳ, huyện Gia Lâm, Hà Nội', '+84(24)3876 0325', 'dlanh@vnua.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Hồng Đức', N' Số 65, phố Tràng Thi, Phường Hàng Bông, Quận Hoàn Kiếm, Hà Nội', '0439260024', 'nhaxuatbanhongduc65@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Hội Nhà văn', N'Số 65 Nguyễn Du, quận Hai Bà Trưng, Hà Nội', '(024)3822.2135', 'nxbhoinhavan65@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Khoa học Tự nhiên và Công nghệ', N'Nhà A16 - Số 18 Hoàng Quốc Việt, Cầu Giấy, Hà Nội', '(+84)(24) 2214.9041', 'nxb@vap.ac.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Khoa học và Kỹ thuật', N'Số 70 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '02438220686', 'nhaxuatbankhkt@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Khoa học xã hội', N'26 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', '04.39719073', 'nxbkhxh@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Kim Đồng', N'Số 55 Quang Trung, Nguyễn Du, Hai Bà Trưng, Hà Nội', '(+84) 1900571595', 'cskh_online@nxbkimdong.com.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Kinh tế thành phố Hồ Chí Minh', N'279 Nguyễn Tri Phương – Phường 5 – Quận 10 – TP. Hồ Chí Minh', '(028) 38.575.466', 'nxb@ueh.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Lao động', N'175 Giảng Võ, Q. Đống Đa, Hà Nội', '04.38515380', 'nxblaodong@yahoo.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Lao động - Xã hội', N'Số 36 - Ngõ Hoà Bình 4 - Minh Khai - Hai Bà Trưng - Hà Nội', '024.36246913', 'nxblaodongxahoi@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Lý luận Chính trị', N'56B Quốc Tử Giám, Đống Đa, Hà Nội', '043747.2541', 'nxbllct@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Mỹ thuật', N'44 Hàm Long, Hoàn Kiếm, Hà Nội', '091 235 03 26', '')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Nông nghiệp', N'Số 6, ngõ 167, Phương Mai, Đống Đa, Hà Nội', '04. 38253887', 'nxbnn@yahoo.com.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Phụ nữ', N'39 Hàng Chuối, Q. Hai Bà Trưng, Hà Nội', '(024) 39710717', 'truyenthongvaprnxbpn@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Quân đội nhân dân', N'23 Lý Nam Đế, Hoàn Kiếm, Hà Nội', '024.38455766', 'nxbqdnd@nxbqdnd.com.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Sân khấu', N'51 Trần Hưng Đạo, Quận Hoàn Kiếm, Hà Nội', '9436501/6502', 'nxbsankhau1985@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Thanh niên', N'143 Pasteur, Phường 6, Quận 3, Thành phố Hồ Chí Minh', '028 3910 6963', '')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Thông tin và Truyền thông', N'Tầng 6, Tòa nhà Cục Tần số vô tuyến điện, 115 Trần Duy Hưng, Hà Nội', '024.35772139', 'nxb.tttt@mic.gov.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Thông tấn', N'79 Lý Thường Kiệt - Quận Hoàn Kiếm - Hà Nội', ' 024.39332279', 'nhaxuatbanthongtan@vnanet.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Thế giới', N'46 Trần Hưng Đạo, Hà Nội, Việt Nam', '(84-4) 38253841', 'thegioi@hn.vnn.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Thể thao và Du lịch', N'Số 7, phố Trịnh Hoài Đức, Phường Cát Linh, Quận Đống đa, Hà Nội', '024 3845 6155', '')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Thống kê', N'98 Thụy Khuê, Tây Hồ, Hà Nội', '024. 3847 4185', 'xuatbanthongke.kd@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Thời đại', N'Nhà B15, Lô 2, Khu đô thị Mỹ Đình I, Phường Cầu Diễn, Quận Nam Từ Liêm, Hà Nội', '028 3820 8632', '')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Tri thức', N'Tầng 1 - Tòa nhà VUSTA - 53 Nguyễn Du - Quận Hai Bà Trưng - Hà Nội - Việt Nam', '024 3944 7279', 'lienhe@nxbtrithuc.com.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Tài chính', N'Số 7 Phan Huy Chú, quận Hoàn Kiếm, thành phố Hà Nội', '024.38262767', 'info@fph.gov.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Tài nguyên - Môi trường và Bản đồ Việt Nam', N'85 Nguyễn Chí Thanh, phường Láng Hạ, quận Đống Đa, Hà Nội', '(84-024)38344108', 'Info@bando.com.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Tôn giáo', N'53 Tràng Thi, Hàng Bông, Hoàn Kiếm, Hà Nội', '0804.8106', 'nhaxuatbantongiao@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Tư pháp', N'Số 35 Trần Quốc Toản, Hoàn Kiếm, Hà Nội - Việt Nam', '(84-4) 62632073', 'nxbtp@moj.gov.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Văn hóa - thông tin', N'Số 43 Lò Đúc, Quận Hai Bà Trưng, TP. Hà Nội', '', '')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Văn hóa dân tộc', N'Số 19 Nguyễn Bỉnh Khiêm,Quận Hai Bà Trưng,TP. Hà Nội', '(024)-3.8263070', 'nxbvanhoadantoc@yahoo.com.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Văn học', N'18 Nguyễn Trường Tộ - Ba Đình - Hà Nội', '024.37161518', 'info@nxbvanhoc.com.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Xây dựng', N'37 Lê Đại Hành, Hai Bà Trưng, Tp. Hà Nội', '02437265180', 'banhang@nxbxaydung.com.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Y học', N'Số 352 Đội Cấn, Phường Cống Vị, Quận Ba Đình, TP Hà Nội', '0934547168', 'xuatbanyhoc@fpt.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Âm nhạc', N'Số 61 Lý Thái Tổ, Quận Hoàn Kiếm, TP. Hà Nội', '(024)-3.8256286', '')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đại học Công Nghiệp Thành phố Hồ Chí Minh', N'12 Nguyễn Văn Bảo, P4, Q. Gò Vấp, TP Hồ Chí Minh', ' 02838.940.390 (800)', '')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đại học Cần Thơ', N'Khu II, Trường Đại học Cần Thơ, Đường 3/2, P. Xuân Khánh, Q. Ninh Kiều, TP. Cần Thơ ', '(0292) 3839981', 'dvtho@ctu.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đại học Huế', N'Số 7 Hà Nội, thành phố Huế', '02343.837838', 'nxbdhhue@hueuni.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đại học Kinh tế Quốc dân', N'207 Đường Giải Phóng - Hà Nội', '024.36282487', 'nxb@neu.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đại học Quốc gia Hà Nội', N'16 Hàng Chuối, Hai Bà Trưng, Hà Nội', '024.3972.5997', 'nxb@vnu.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đại học Quốc gia Thành phố Hồ Chí Minh', N'Phòng 501, Nhà Điều hành ĐHQG-HCM, phường Linh Trung, quận Thủ Đức, TP Hồ Chí Minh', '028 6272 6361', 'phathanh.nxb@vnuhcm.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đại học Sư phạm', N'Tầng 6, Toà nhà số 128 đường Xuân Thuỷ, phường Dịch Vọng Hậu, Quận Cầu Giấy, Thành phố Hà Nội', '024.3754.7735', 'hanhchinh.nxb@hnue.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đại học Thái Nguyên', N'Phường Tân Thịnh – thành phố Thái Nguyên – tỉnh Thái Nguyên', '02083.546.116', 'nxb.dhtn@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đại học Vinh', N'Số 182 Lê Duẩn - TP. Vinh - Tỉnh Nghệ An', '(0238)3855269', 'nxbdhv@vinhuni.edu.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đại học sư phạm Thành phố Hồ Chí Minh', N'280 An Dương Vương, Phường 4, Quận 5, TPHCM', '(08)38 301303', 'nxbdhsphcm11@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Hà Nội', N'Số 4, phố Tống Duy Tân, quận Hoàn Kiếm, Hà Nội', '024.38252916', 'vanthu_nxbhn@hanoi.gov.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Hải Phòng', N'Số 5 Nguyễn Khuyến - P. Lương Khánh Thiện - Q.Ngô Quyền - TP. Hải Phòng', '(0225)3855871', '')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Nghệ An', N'Số 37B, Lê Hồng Phong, thành phố Vinh, Nghệ An', '02383.844.748', 'nhaxuatbannghean@gmail.com')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Phương Đông', N'497/10 Sư Vạn Hạnh, Phường 12, Quận 10, Hồ Chí Minh', '0838 683 930', '')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Thanh Hóa', N'248 Trần Phú, P. Ba Đình, Thành phố Thanh Hóa, Việt Nam', '0373 853 548', '')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Thuận Hóa', N'33 Chu Văn An, Phường Phú Hội, Thừa Thiên Huế , Việt Nam', '084 777 4910', '')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Trẻ', N'161B Lý Chính Thắng, Phường Võ Thị Sáu, Quận 3 , TP. Hồ Chí Minh', '(84.028) 39316289', 'hopthubandoc@nxbtre.com.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Tổng hợp Thành phố Hồ Chí Minh', N'62 Nguyễn Thị Minh Khai, phường Đa Kao, quận 1, TPHCM', '(028) 38 256 804', 'tonghop@nxbhcm.com.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Văn hóa- Văn nghệ Thành phố Hồ Chí Minh', N'88-90 Ký Con, P. Nguyễn Thái Bình, Q. 1,Tp. Hồ Chí Minh (TPHCM)', '(028) 38216009', 'nxbvhvn@nxbvanhoavannghe.org.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đà Nẵng', N'Số 03 đường 30 Tháng 4, P. Hòa Cường Bắc, Q.Hải Châu, TP.Đà Nẵng', '(0236) 3.79.78.69', ' xuatban@nxbdanang.vn')
INSERT NhaXuatBan (tenNXB, diaChi, sdt, email) VALUES (N'Đồng Nai', N'1953J, Nguyễn Ái Quốc, phường Trung Dũng, thành phố Biên Hoà, tỉnh Đồng Nai', '02513.946530', 'nxbdongnai@hcm.vnn.vn')
SELECT * FROM NhaXuatBan
-----------------------------------------------------------------------Thêm nhà cung cấp---------------------------------------------------
INSERT INTO NhaCungCap (tenNCC, diaChi, sdt, email) VALUES (N'First News', N'11H Nguyễn Thị Minh Khai, Phường Bến Nghé, Quận 1, Tp. Hồ Chí Minh', '(84.28) 3822 79 79', 'triviet@firstnews.com.vn')
INSERT INTO NhaCungCap (tenNCC, diaChi, sdt, email) VALUES (N'Thái Hà Books', N'Lô B2, tổ dân phố số 1, phường Phúc Diễn, quận Bắc Từ Liêm, Hà Nội', '024 3793 0480', 'Sachthaiha@thaihabooks.com')
INSERT INTO NhaCungCap (tenNCC, diaChi, sdt, email) VALUES (N'Nhã Nam', N' 59 - Đỗ Quang - Trung Hòa - Cầu Giấy - Hà Nội', '0903244248', 'bookstore@nhanam.vn')
INSERT INTO NhaCungCap (tenNCC, diaChi, sdt, email) VALUES (N'ten', N'diachi', 'sdt', 'email')
SELECT * FROM NhaCungCap

----------------------------------------------------------- Thêm Tác giả-------------------------------------------------------
INSERT INTO TacGia (tenTG, sdt, email, diaChi) VALUES ('Minh Niệm', '0123456789','minhniem@gmail.com', '1 Quang Trung')
INSERT INTO TacGia (tenTG, sdt, email, diaChi) VALUES ('TS David J Lieberman', '0123456789','minhniem@gmail.com', '1 Quang Trung')
INSERT INTO TacGia (tenTG, sdt, email, diaChi) VALUES ('Benjamin Graham', '0123456789','minhniem@gmail.com', '1 Quang Trung')
INSERT INTO TacGia (tenTG, sdt, email, diaChi) VALUES ('Lê Rin', '0123456789','minhniem@gmail.com', '1 Quang Trung')
SELECT * FROM TacGia
---------------------------------------------------------------Thêm sách----------------------------------------------------
INSERT INTO Sach (tenSach, soLuong, giaNhap, nhaXuatBan, namXuatBan, tacGia, loaiSach, nhaCungCap, giaBan, hinhAnhSach)
VALUES (N'Hiểu về trái tim', 20, 100000, N'NXB-000060', 2019, N'TG-000000001', N'LS-028', N'NCC-00001', 140000, 'D:\PTUDBTLon\src\main\java\com\example\nhom10_qlhs\image\hieu-ve-trai-tim.png')

INSERT INTO Sach (tenSach, soLuong, giaNhap, nhaXuatBan, namXuatBan, tacGia, loaiSach, nhaCungCap, giaBan, hinhAnhSach)
VALUES (N'Đọc vị bất kì ai', 50, 50000, N'NXB-000017', 2019, N'TG-000000002', N'LS-028', N'NCC-00002', 80000, 'D:\PTUDBTLon\src\main\java\com\example\nhom10_qlhs\image\doc-vi-bat-ki-ai.jpg')

INSERT INTO Sach (tenSach, soLuong, giaNhap, nhaXuatBan, namXuatBan, tacGia, loaiSach, nhaCungCap, giaBan, hinhAnhSach)
VALUES (N'Nhà Đầu Tư Thông Minh (Tái Bản 2020)', 50, 100000, N'NXB-000028', 2020, N'TG-000000003', N'LS-023', N'NCC-00003', 145000, 'D:\PTUDBTLon\src\main\java\com\example\nhom10_qlhs\image\nha-dau-tu-thong-minh.jpg')

INSERT INTO Sach (tenSach, soLuong, giaNhap, nhaXuatBan, namXuatBan, tacGia, loaiSach, nhaCungCap, giaBan, hinhAnhSach)
VALUES (N'Việt Nam Miền Ngon', 35, 100000, N'NXB-000018', 2019, N'TG-000000004', N'LS-076', N'NCC-00002', 135000, 'D:\PTUDBTLon\src\main\java\com\example\nhom10_qlhs\image\viet-nam-mien-ngon.jpg')
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