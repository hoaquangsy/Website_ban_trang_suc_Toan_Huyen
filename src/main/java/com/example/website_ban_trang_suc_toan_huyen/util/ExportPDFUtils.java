package com.example.website_ban_trang_suc_toan_huyen.util;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ExportPdfDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class ExportPDFUtils {
    public ByteArrayInputStream exportPdf(List<ExportPdfDTO> exportPdfDTOS, BigDecimal total, String userName,Long orderCode, String dis) {
        // tạo một document
        Document document = new Document();
        ByteArrayOutputStream ot = new ByteArrayOutputStream();
        try {
            // khởi tạo một PdfWriter truyền vào document và FileOutputStream
            PdfWriter.getInstance(document, ot);

            // mở file để thực hiện viết
            document.open();
            // thêm nội dung sử dụng add function

            Font font = new Font(BaseFont.createFont("arial/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            font.setColor(220, 20, 60);
            font.setStyle(Font.BOLD);


            Paragraph paragraph1 = new Paragraph("CÔNG TY TNHH VÀNG                                   BẠC GIẤY ĐẢM BẢO VÀNG",
                    new Font(font));
            paragraph1.setIndentationLeft(50);
            paragraph1.setIndentationRight(50);
            paragraph1.setAlignment(Element.ALIGN_LEFT);
            paragraph1.setSpacingAfter(0);


            Paragraph paragraph2 = new Paragraph("___ TOẢN HUYỀN ___                                       uy tín quý hơn vàng", new Font(font));
            paragraph2.setSpacingBefore(5);
            paragraph2.setAlignment(Element.ALIGN_LEFT);
            paragraph2.setIndentationLeft(55);
            paragraph2.setIndentationRight(55);

            font.setStyle(Font.NORMAL);
            font.setSize(11.5f);
            Paragraph paragraph3 = new Paragraph("DC: Khu Minh Thanh - Xã Minh Đài - Huyện Tân Sơn - tỉnh Phú Thọ \nSĐT: 0978.154.115- 0986.344.292", new Font(font));
            paragraph3.setSpacingBefore(10);
            paragraph3.setAlignment(Element.ALIGN_LEFT);
            paragraph3.setIndentationLeft(55);
            paragraph3.setIndentationRight(55);

            font.setStyle(Font.BOLD);
            Paragraph paragraph4 = new Paragraph("CHUYÊN MUA BÁN NỮ TRANG VÀNG 999 - VÀNG TÂY CÁC LOẠI", new Font(font));
            paragraph4.setSpacingBefore(5);
            paragraph4.setAlignment(Element.ALIGN_CENTER);
            paragraph4.setIndentationLeft(55);
            paragraph4.setIndentationRight(55);

            font.setSize(10.5f);
            Paragraph paragraph5 = new Paragraph("Bán cho ông (bà): +"+userName +"                                            Địa chỉ:  "+dis, new Font(font));
            paragraph5.setSpacingBefore(15);
            paragraph5.setAlignment(Element.ALIGN_LEFT);
            paragraph5.setIndentationLeft(55);
            paragraph5.setIndentationRight(55);

            Paragraph paragraph12 = new Paragraph("                   Mã Hóa Đơn: "+orderCode,new Font(font));
            paragraph5.setSpacingBefore(15);
            paragraph5.setAlignment(Element.ALIGN_LEFT);
            paragraph5.setIndentationLeft(55);
            paragraph5.setIndentationRight(55);
            // table
            PdfPTable t = new PdfPTable(7);
            t.setSpacingBefore(25);
            t.setSpacingAfter(25);


            PdfPCell c1 = new PdfPCell(new Phrase("Tên hàng", new Font(font)));
            t.addCell(c1);
            PdfPCell c2 = new PdfPCell(new Phrase("Số lượng", new Font(font)));
            t.addCell(c2);
            PdfPCell c3 = new PdfPCell(new Phrase("Tuổi vàng", new Font(font)));
            t.addCell(c3);
            PdfPCell c4 = new PdfPCell(new Phrase("Trọng lượng", new Font(font)));
            t.addCell(c4);
            PdfPCell c5 = new PdfPCell(new Phrase("Đơn giá", new Font(font)));
            t.addCell(c5);
            PdfPCell c6 = new PdfPCell(new Phrase("Tiền công", new Font(font)));
            t.addCell(c6);
            PdfPCell c7 = new PdfPCell(new Phrase("Thành tiền", new Font(font)));
            t.addCell(c7);
            exportPdfDTOS.forEach(exportPdfDTO -> {
                t.addCell(new Phrase(exportPdfDTO.getName(), new Font(font)));
                t.addCell(exportPdfDTO.getQuantity().toString());
                t.addCell(exportPdfDTO.getAge().toString());
                t.addCell(exportPdfDTO.getWight().toString());
                t.addCell(exportPdfDTO.getPrice().toString());
                t.addCell(exportPdfDTO.getWage().toString());
                t.addCell(exportPdfDTO.getTotal().toString());
            });
//           // tong
            font.setStyle(Font.BOLD);
            NumberFormat currencyFormat02 = NumberFormat.getCurrencyInstance(Locale.getDefault());
            currencyFormat02.setMinimumFractionDigits(0);
            Paragraph paragraphTong = new Paragraph("Tổng tiền: " + currencyFormat02.format(total), new Font(font));
            paragraphTong.setSpacingBefore(5);
            paragraphTong.setAlignment(Element.ALIGN_LEFT);
            paragraphTong.setIndentationLeft(55);
            paragraphTong.setIndentationRight(55);

            // lưu ý
            font.setStyle(Font.BOLD);
            Paragraph paragraph6 = new Paragraph("LƯU Ý: ", new Font(font));
            paragraph6.setSpacingBefore(5);
            paragraph6.setAlignment(Element.ALIGN_LEFT);
            paragraph6.setIndentationLeft(55);
            paragraph6.setIndentationRight(55);
            //+
            font.setStyle(Font.NORMAL);
            Paragraph paragraph7 = new Paragraph("Thưa quý khách chúng tôi có giá ưu đãi cho khách hàng mua \n - Quý khách giữ lại giấy đảm bảo để tiện mua bán, đổi: ", new Font(font));
            paragraph7.setSpacingBefore(5);
            paragraph7.setAlignment(Element.ALIGN_LEFT);
            paragraph7.setIndentationLeft(55);
            paragraph7.setIndentationRight(55);

            font.setStyle(Font.BOLD);
            Paragraph paragraph8 = new Paragraph("Rất hân hạnh được phục vụ quý khách ! ", new Font(font));
            paragraph8.setSpacingBefore(5);
            paragraph8.setAlignment(Element.ALIGN_LEFT);
            paragraph8.setIndentationLeft(55);
            paragraph8.setIndentationRight(55);

            font.setStyle(Font.NORMAL);
            Date date = new Date();// the date instance
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Paragraph paragraph9 = new Paragraph("Tân Sơn, ngày:" + new Date().getDate() + " tháng:" + (calendar.get(Calendar.MONTH) + 1 ) + " năm: " + calendar.get(Calendar.YEAR), new Font(font));
            paragraph9.setSpacingBefore(5);
            paragraph9.setAlignment(Element.ALIGN_RIGHT);
            paragraph9.setIndentationLeft(55);
            paragraph9.setIndentationRight(55);

            font.setStyle(Font.BOLD);
            Paragraph paragraph10 = new Paragraph("ĐẠI DIỆN CÔNG TY", new Font(font));
            paragraph10.setSpacingBefore(5);
            paragraph10.setAlignment(Element.ALIGN_RIGHT);
            paragraph10.setIndentationLeft(55);
            paragraph10.setIndentationRight(75);

            font.setStyle(Font.NORMAL);
            Paragraph paragraph11 = new Paragraph("Huyền", new Font(font));
            paragraph11.setSpacingBefore(5);
            paragraph11.setAlignment(Element.ALIGN_RIGHT);
            paragraph11.setIndentationLeft(55);
            paragraph11.setIndentationRight(115);

            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
            document.add(paragraph12);
            document.add(t);
            document.add(paragraphTong);
            document.add(paragraph6);
            document.add(paragraph7);
            document.add(paragraph8);
            document.add(paragraph9);
            document.add(paragraph10);
            document.add(paragraph11);

            // đóng file
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(ot.toByteArray());
    }
}
