package com.restaurant.inventorysystem.util;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
//In my project, I implemented a reusable Excel export utility using Apache POI to generate .xlsx files dynamically.
// Instead of writing Excel generation logic inside each service, I created a separate utility class that accepts a
// list of column headers and a list of row data. In the service layer, I simply convert my DTOs into a list of objects
// and pass them to this utility. The utility handles everything—creating the workbook and sheet, applying header
// styling such as bold text and gray background, formatting date columns, auto-sizing columns, and finally writing
// the Excel file directly to the browser using HttpServletResponse. This approach keeps the code clean, modular, and
// reusable for any module that needs Excel export, like stock reports, sales data, or user lists.
public class ExcelExportUtil {

    public static void generateExcel(HttpServletResponse response,
                                     String sheetName,
                                     List<String> headers,
                                     List<List<Object>> dataRows) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);

        // ----------------------------------------------------------
        // COMPANY NAME, REPORT NAME, TIMESTAMP ROWS
        // ----------------------------------------------------------

        // Bold + Large Font for Title
        XSSFFont titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeight(16);

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);

        // Row 0 -> Company Name
        Row companyRow = sheet.createRow(0);
        Cell companyCell = companyRow.createCell(0);
        companyCell.setCellValue("Shri Restaurant ERP System");
        companyCell.setCellStyle(titleStyle);

        // Row 1 -> Report Name
        Row reportRow = sheet.createRow(1);
        Cell reportCell = reportRow.createCell(0);
        reportCell.setCellValue(sheetName + " Report");
        reportCell.setCellStyle(titleStyle);

        // Row 2 -> Timestamp
        Row timeRow = sheet.createRow(2);
        Cell timeCell = timeRow.createCell(0);

        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        timeCell.setCellValue("Generated On: " + time);

        // Leave one blank line before table header
        int rowIndex = 4;


        // ----------------------------------------------------------
        // HEADER STYLE (Bold + Gray Background)
        // ----------------------------------------------------------

        CellStyle headerStyle = workbook.createCellStyle();

        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Gray background
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Border for headers
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        // ----------------------------------------------------------
        // DATE STYLE
        // ----------------------------------------------------------

        CellStyle dateStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy HH:mm"));


        // ----------------------------------------------------------
        // CREATE HEADER ROW
        // ----------------------------------------------------------

        Row headerRow = sheet.createRow(rowIndex++);
        for (int i = 0; i < headers.size(); i++) {

            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(headerStyle);
        }


        // ----------------------------------------------------------
        // FILL DATA ROWS
        // ----------------------------------------------------------

        for (List<Object> rowData : dataRows) {

            Row row = sheet.createRow(rowIndex++);

            for (int i = 0; i < rowData.size(); i++) {

                Object value = rowData.get(i);
                Cell cell = row.createCell(i);

                if (value == null) {
                    cell.setCellValue("");
                }
                else if (value instanceof Number) {
                    cell.setCellValue(((Number) value).doubleValue());
                }
                else if (value instanceof Timestamp) {
                    cell.setCellValue((Timestamp) value);
                    cell.setCellStyle(dateStyle);
                }
                else if (value instanceof java.time.LocalDateTime) {
                    Timestamp ts = Timestamp.valueOf((java.time.LocalDateTime) value);
                    cell.setCellValue(ts);
                    cell.setCellStyle(dateStyle);
                }
                else {
                    cell.setCellValue(value.toString());
                }
            }
        }


        // ----------------------------------------------------------
        // AUTO-SIZE COLUMNS
        // ----------------------------------------------------------

        for (int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
        }


        // ----------------------------------------------------------
        // RESPONSE DOWNLOAD CONFIG
        // ----------------------------------------------------------

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + sheetName.replace(" ", "_") + ".xlsx"
        );

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
