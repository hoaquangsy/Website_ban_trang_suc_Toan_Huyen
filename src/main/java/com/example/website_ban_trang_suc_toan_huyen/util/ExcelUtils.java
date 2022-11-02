package com.example.website_ban_trang_suc_toan_huyen.util;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.OrderDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ExcelUtils {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<OrderDTO> listOrder;

    public ExcelUtils(List<OrderDTO> listOrders) {
        this.listOrder = listOrders;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Order");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        createCell(row, 0, "Order ID", style);
        createCell(row, 1, "Order Code", style);
        createCell(row, 2, "Create_At", style);
        createCell(row, 3, "Create_By", style);
        createCell(row, 4, "Last_Modified_At", style);
        createCell(row, 5, "Last_Modified_By", style);
        createCell(row, 6, "Customer Money", style);
        createCell(row, 7, "Event ID", style);
        createCell(row, 8, "Payment Method", style);
        createCell(row, 9, "Transport Fee", style);
        createCell(row, 10, "Total", style);
        createCell(row, 11, "Status", style);
        createCell(row, 12, "PurchaseType", style);
        createCell(row, 13, "Address", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof UUID || value instanceof Instant){
            cell.setCellValue(String.valueOf(value));
        }else if (value instanceof Long){
            cell.setCellValue(String.valueOf(value));
        }else if (value instanceof BigDecimal){
            cell.setCellValue(((BigDecimal) value).toPlainString());
        }
        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (OrderDTO order : listOrder) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, order.getId(), style);
            createCell(row, columnCount++, order.getOrderCode(), style);
            createCell(row, columnCount++, order.getCreateAt(), style);
            createCell(row, columnCount++, order.getCreateBy(), style);
            createCell(row, columnCount++, order.getLastModifiedAt(), style);
            createCell(row, columnCount++, order.getLastModifiedBy(), style);
            createCell(row, columnCount++, order.getCustomerMoney(), style);
            createCell(row, columnCount++, order.getEventId(), style);
            createCell(row, columnCount++, order.getPaymentMethod().toString(), style);
            createCell(row, columnCount++, order.getTransportFee(), style);
            createCell(row, columnCount++, order.getTotal(), style);
            createCell(row, columnCount++, order.getStatus().toString(), style);
            createCell(row, columnCount++, order.getPurchaseType().toString(), style);
            createCell(row, columnCount++, order.getAddress(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
