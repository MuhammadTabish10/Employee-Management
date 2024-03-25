package com.example.EmployeeManagement.util;

import com.example.EmployeeManagement.exception.RecordNotFoundException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public static void applyBoldStyle(CellStyle style, Workbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
    }

    public static CellStyle createBoldAndCenteredStyle(Workbook workbook) {
        CellStyle boldStyle = workbook.createCellStyle();
        applyBoldStyle(boldStyle, workbook);
        boldStyle.setAlignment(CellStyle.ALIGN_CENTER);
        return boldStyle;
    }

    public static CellStyle createCenteredStyle(Workbook workbook) {
        CellStyle centeredStyle = workbook.createCellStyle();
        centeredStyle.setAlignment(CellStyle.ALIGN_CENTER);
        return centeredStyle;
    }

    public static CellStyle createHeadingStyle(Workbook workbook) {
        CellStyle headingStyle = workbook.createCellStyle();
        applyBoldStyle(headingStyle, workbook);
        headingStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font headingFont = workbook.createFont();
        headingFont.setFontHeightInPoints((short) 14); // Set font size
        headingFont.setBold(true); // Set bold
        headingStyle.setFont(headingFont);
        return headingStyle;
    }

    public static CellStyle createBoldHeadingStyle(Workbook workbook) {
        CellStyle headingStyle = workbook.createCellStyle();
        applyBoldStyle(headingStyle, workbook);
        headingStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font headingFont = workbook.createFont();
        headingFont.setBold(true); // Set bold
        headingStyle.setFont(headingFont);
        return headingStyle;
    }

    public static void autoSizeColumns(Sheet sheet, int numberOfColumns) {
        for (int i = 0; i < numberOfColumns; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public static void populateRowWithData(Row row, List<Object> rowData, CellStyle style) {
        int colIndex = 0;
        for (Object value : rowData) {
            Cell cell = row.createCell(colIndex++);
            cell.setCellStyle(style);
            setCellValue(cell, value);
        }
    }

    public static void setCellValue(Cell cell, Object value) {
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof java.util.Date) {
            cell.setCellValue((java.util.Date) value);
        }
    }

//    public static void createSectionSheet(Sheet sheet, List<Map<String, Object>> sectionData, Workbook workbook) {
//        CellStyle centeredStyle = createCenteredStyle(workbook);
//        CellStyle boldStyle = createBoldAndCenteredStyle(workbook);
//        CellStyle headingStyle = createHeadingStyle(workbook);
//
//        int rowIndex = 0;
//
//        // Add section heading
//        Row headingRow = sheet.createRow(rowIndex++);
//        Cell headingCell = headingRow.createCell(0);
//        headingCell.setCellValue(sheet.getSheetName());
//        headingCell.setCellStyle(headingStyle); // Set heading style
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, sectionData.get(0).size() - 1));
//
//        // Add section headers
//        Row headerRow = sheet.createRow(rowIndex++);
//        int colIndex = 0;
//        for (String key : sectionData.get(0).keySet()) {
//            Cell cell = headerRow.createCell(colIndex++);
//            cell.setCellValue(key);
//            cell.setCellStyle(boldStyle);
//        }
//
//        // Populate section data rows
//        for (Map<String, Object> record : sectionData) {
//            Row dataRow = sheet.createRow(rowIndex++);
//            populateRowWithData(dataRow, record.values().stream().toList(), centeredStyle);
//        }
//
//        // Auto-size columns
//        autoSizeColumns(sheet, sectionData.get(0).size());
//    }

    public static void createSectionSheet(Sheet sheet, List<Map<String, Object>> sectionData, Workbook workbook)
    {
        if (!sectionData.isEmpty()) {
            CellStyle centeredStyle = createCenteredStyle(workbook);
            CellStyle boldStyle = createBoldAndCenteredStyle(workbook);
            CellStyle headingStyle = createHeadingStyle(workbook);

            int rowIndex = 0;

            // Add section heading
            Row headingRow = sheet.createRow(rowIndex++);
            Cell headingCell = headingRow.createCell(0);
            headingCell.setCellValue(sheet.getSheetName());
            headingCell.setCellStyle(headingStyle); // Set heading style
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, sectionData.get(0).size() - 1));

            // Add section headers
            Row headerRow = sheet.createRow(rowIndex++);
            int colIndex = 0;
            for (String key : sectionData.get(0).keySet()) {
                Cell cell = headerRow.createCell(colIndex++);
                cell.setCellValue(key);
                cell.setCellStyle(boldStyle);
            }

            // Populate section data rows
            for (Map<String, Object> record : sectionData) {
                Row dataRow = sheet.createRow(rowIndex++);
                populateRowWithData(dataRow, record.values().stream().toList(), centeredStyle);
            }

            // Auto-size columns
            autoSizeColumns(sheet, sectionData.get(0).size());
        }
    }
}



