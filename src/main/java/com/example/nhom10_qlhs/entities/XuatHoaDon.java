package com.example.nhom10_qlhs.entities;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.TableView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;

public class XuatHoaDon {
    private static Date date;
    private static TableView<SachInTable> tableView;
    private static String mahd;
    private static String ngayLap;
    private static String tenKH;
    private static String sdt;
    private static String diachi;
    private static String nhanvien;
    private static String tienKhachDua;
    private static String tienThoi;
    private static String tongTien;
    private static String Thue;


    public XuatHoaDon() {

    }

    public XuatHoaDon(TableView tableView, String mahd, String ngayLap, String tenKH, String sdt, String diachi,
                      String nhanvien, String tienKhachDua, String tienThoi, String tongTien, String Thue) {
        super();
        this.mahd = mahd;
        this.ngayLap = ngayLap;
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.diachi = diachi;
        this.nhanvien = nhanvien;
        this.tienKhachDua = tienKhachDua;
        this.tienThoi = tienThoi;
        this.tongTien = tongTien;
        this.Thue = Thue;
        this.tableView = tableView;
    }

    public static void xuatpdf() {
        Document document = new Document(PageSize.A4, 25, 25, 25, 50);
        String fontAria = "C:\\Users\\hocnv\\OneDrive\\Documents\\Nam_3_HK1\\java_phan_tan\\Bai_tap_lon\\Nhom03_QuanLyNhaSachTuNhan\\lib\\vuArial.ttf";

        String fontBold = "C:\\Users\\hocnv\\OneDrive\\Documents\\Nam_3_HK1\\java_phan_tan\\Bai_tap_lon\\Nhom03_QuanLyNhaSachTuNhan\\lib\\vuArialBold.ttf";

        try {

            PdfWriter.getInstance(document, new FileOutputStream(
                    "C:\\Users\\hocnv\\OneDrive\\Documents\\Nam_3_HK1\\java_phan_tan\\Bai_tap_lon\\Nhom03_QuanLyNhaSachTuNhan\\lib\\exportPDF1.pdf"));

            document.open();
            Font fA = new Font(BaseFont.createFont(fontAria, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            Font fB = new Font(BaseFont.createFont(fontBold, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 20);
            Font fBx = new Font(BaseFont.createFont(fontBold, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));

            document.add(new Paragraph(
                    "Hiệu sách DTNP\r\n" + "Sđt: 0989007777\r\n" + "Địa chỉ: 12 Nguyễn Văn bảo,\r\n" + " P3, Q.Gò vấp",
                    fA));

            Paragraph title = new Paragraph("Hóa Đơn Bán Hàng", fB);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            Paragraph ngayLapHD = new Paragraph("Ngày Lập: " + ngayLap, fA);
            Paragraph maHD = new Paragraph("Mã Hóa Đơn: " + mahd, fA);
            Paragraph TenKH = new Paragraph("Khách hàng: " + tenKH, fA);
            Paragraph Sdt = new Paragraph("Sđt: " + sdt, fA);
            Paragraph nhanVien = new Paragraph("Nhân viên: " + nhanvien, fA);
            document.add(new Paragraph("ư"));
            document.add(new Paragraph("ư"));
            document.add(new Paragraph("ư"));
            document.add(maHD);
            document.add(ngayLapHD);
            document.add(TenKH);
            document.add(Sdt);
            document.add(nhanVien);

            PdfPTable Table = new PdfPTable(5);
            Table.setSpacingBefore(25);
            Table.setSpacingAfter(25);
            Table.setWidthPercentage(100);


            PdfPCell cell1, cell2, cell3, cell4, cell5;
            Table.getDefaultCell().setBorder(Rectangle.TOP);

            cell1 = new PdfPCell(new Phrase("STT",fBx));
            cell1.setBorder(Rectangle.TOP);
            Table.addCell(cell1);
            cell2 = new PdfPCell(new Phrase("Sản Phẩm", fBx));
            cell2.setBorder(Rectangle.TOP);
            Table.addCell(cell2);
            cell3 = new PdfPCell(new Phrase("SL",fBx));
            cell3.setBorder(Rectangle.TOP);
            Table.addCell(cell3);
            cell4 = new PdfPCell(new Phrase("Đơn Giá", fBx));
            cell4.setBorder(Rectangle.TOP);
            Table.addCell(cell4);
            cell5 = new PdfPCell(new Phrase("Thành Tiền", fBx));
            cell5.setBorder(Rectangle.TOP);
            Table.addCell(cell5);

            int stt = 0;
            for (int i = 0; i < tableView.getItems().size(); i++) {
                stt = i + 1;
                Table.addCell(String.valueOf(stt));

                Table.addCell(new Phrase(tableView.getItems().get(i).getMaSach(), fA));
                Table.addCell(new Phrase(tableView.getItems().get(i).getTenSach(), fA));
                Table.addCell(new Phrase(String.valueOf(tableView.getItems().get(i).getSoLuong()), fA));
                Table.addCell(new Phrase(String.valueOf(tableView.getItems().get(i).getDonGia()), fA));
                Table.addCell(new Phrase(String.valueOf(tableView.getItems().get(i).getThanhTien()), fA));
            }

            document.add(Table);
            double tongT = (Double.parseDouble(tongTien)/100)* Double.parseDouble(Thue);
            double TienCanTra= Double.parseDouble(tongTien)*1.05;

            Paragraph tongtien = new Paragraph("Tổng tiền Sản Phẩm:"+"                                                                                              " + tongTien, fBx);
            Paragraph ThueVAT = new Paragraph("Thuế VAT:5%"+"                                                                                                          " + String.valueOf(tongT), fBx);
            Paragraph TienSauThue = new Paragraph("Tổng tiền cần thanh toán:"+"                                                                                      " + TienCanTra, fBx);
            Paragraph tienkhachDua = new Paragraph("Tiền Khách đưa:" +"                                                                                                     " + tienKhachDua, fBx);
            Paragraph tienThua = new Paragraph("Tiền thừa:"+"                                                                                                                "  + tienThoi, fBx);
            document.add(tongtien);
            document.add(ThueVAT);
            document.add(TienSauThue);
            document.add(tienkhachDua);
            document.add(tienThua);

            document.close();
            Process p;
            try {
                p = Runtime.getRuntime().exec(
                        "rundll32 url.dll,FileProtocolHandler C:\\Users\\hocnv\\OneDrive\\Documents\\Nam_3_HK1\\java_phan_tan\\Bai_tap_lon\\Nhom03_QuanLyNhaSachTuNhan\\lib\\exportPDF1.pdf");
                p.waitFor();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (IOException | DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
