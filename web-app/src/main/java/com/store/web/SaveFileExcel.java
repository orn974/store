package com.store.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.store.model.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

public class SaveFileExcel {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Id", "Product name", "Cost", "","Date of manufacture" };
    static String SHEET = "Product";

    public static ByteArrayInputStream tutorialsToExcel(List<Product> tutorials) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            sheet.autoSizeColumn(1);
            // Header
            Row headerRow = sheet.createRow(0);
            XSSFFont font = (XSSFFont) workbook.createFont();
            XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();


            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
                font.setFontHeightInPoints((short)26);

            }

            int rowIdx = 1;
            for (Product tutorial : tutorials) {
                Row row = sheet.createRow(rowIdx++);
                font.setColor((short) 66);
                cellStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, new XSSFColor(new java.awt.Color(1, 122, 203)));
                row.createCell(0).setCellValue(tutorial.getStoreId());
                font.setFontName("Courier New");
                row.createCell(1).setCellValue(tutorial.getProductName());
                font.setUnderline((byte) 1);
                row.createCell(2).setCellValue(tutorial.getCost().toString());
                font.setItalic(true);
                row.createCell(4).setCellValue(tutorial.getProductDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

}