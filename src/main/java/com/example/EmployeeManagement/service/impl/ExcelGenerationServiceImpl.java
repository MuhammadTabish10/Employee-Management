package com.example.EmployeeManagement.service.impl;

import com.example.EmployeeManagement.model.Attendance;
import com.example.EmployeeManagement.model.Employee;
import com.example.EmployeeManagement.model.Salary;
import com.example.EmployeeManagement.service.ExcelGenerationService;
import com.example.EmployeeManagement.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@SuppressWarnings("ALL")
@Service
public class ExcelGenerationServiceImpl implements ExcelGenerationService {

    @Override
    public void createExcelFile(List<Map<String, Object>> excelData, OutputStream outputStream) throws IOException {
        if (excelData == null || excelData.isEmpty()) {
            throw new IllegalArgumentException("Excel data is empty");
        }

        Map<String, Object> employee = new LinkedHashMap<>();
        List<Map<String, Object>> attendance = new ArrayList<>();
        List<Map<String, Object>> salaries = new ArrayList<>();

        // Reorder data sections
        for (Map<String, Object> data : excelData) {
            if (data.containsKey("employee")) {
                employee = (Map<String, Object>) data.get("employee");
            }
            if (data.containsKey("Attendances")) {
                List<Map<String, Object>> attendanceData = (List<Map<String, Object>>) data.get("Attendances");
                attendance.addAll(attendanceData);
            }
            if (data.containsKey("Salaries")) {
                List<Map<String, Object>> salaryData = (List<Map<String, Object>>) data.get("Salaries");
                salaries.addAll(salaryData);
            }
        }

        Workbook workbook = new XSSFWorkbook();

        // Create Employee Details sheet
        Sheet employeeSheet = workbook.createSheet("Employee Details");

        CellStyle centeredStyle = ExcelUtil.createCenteredStyle(workbook);
        CellStyle boldStyle = ExcelUtil.createBoldAndCenteredStyle(workbook);
        CellStyle headingStyle = ExcelUtil.createHeadingStyle(workbook);

        int rowIndex = 0;

        // Add Employee Details heading
        Row employeeHeadingRow = employeeSheet.createRow(rowIndex++);
        Cell employeeHeadingCell = employeeHeadingRow.createCell(0);
        employeeHeadingCell.setCellValue("Employee Details");
        employeeHeadingCell.setCellStyle(headingStyle);
        employeeSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, employee.size() - 1));

        // Make heading bold and increase font size
        employeeHeadingCell.setCellStyle(headingStyle);

        // Add Employee section
        Row employeeHeaderRow = employeeSheet.createRow(rowIndex++);
        ExcelUtil.populateRowWithData(employeeHeaderRow, new ArrayList<>(employee.keySet()), boldStyle);

        // Populate Employee data rows
        Row employeeDataRow = employeeSheet.createRow(rowIndex++);
        ExcelUtil.populateRowWithData(employeeDataRow, employee.values().stream().toList(), centeredStyle);

        // Auto-size columns
        ExcelUtil.autoSizeColumns(employeeSheet, employee.size());

        // Create Attendance Details sheet
        Sheet attendanceSheet = workbook.createSheet("Attendance Details");
        ExcelUtil.createSectionSheet(attendanceSheet, attendance, workbook);

        // Create Salary Details sheet
        Sheet salarySheet = workbook.createSheet("Salary Details");
        ExcelUtil.createSectionSheet(salarySheet, salaries, workbook);

        workbook.write(outputStream);
        workbook.close();

    }


    @Override
    public List<Map<String, Object>> convertEmployeeToExcelData(Map<String, Object> dataMap) {
        Employee employee = (Employee) dataMap.get("employee");
        List<Attendance> attendances = (List<Attendance>) dataMap.get("attendance");
        List<Salary> salaries = (List<Salary>) dataMap.get("salary");

        List<Map<String, Object>> excelData = new ArrayList<>();

        // Section for employee data
        Map<String, Object> employeeDataSection = new LinkedHashMap<>();
        Map<String, Object> employeeData = new LinkedHashMap<>();

        employeeData.put("Employee ID", employee.getId());
        employeeData.put("First Name", employee.getFirstName());
        employeeData.put("Last Name", employee.getLastName());
        employeeData.put("Phone Number", employee.getPhoneNumber());
        employeeData.put("Address", employee.getAddress());
        employeeData.put("Date of Birth", employee.getDateOfBirth().toString());
        employeeData.put("Hire Date", employee.getHireDate().toString());
        employeeData.put("Status", employee.getStatus().toString());
        employeeData.put("Job Title", employee.getJobTitle().getTitle());
        employeeData.put("Department", employee.getDepartment().getName());

        employeeDataSection.put("employee", employeeData);

        // Combine all attendance records into a single list
        List<Attendance> sortedAttendances = attendances.stream()
                .sorted(Comparator.comparing(Attendance::getDate).reversed())
                .toList();

        List<Map<String, Object>> attendanceList = new ArrayList<>();
        for (Attendance attendance : sortedAttendances) {
            Map<String, Object> attendanceData = new LinkedHashMap<>();
            attendanceData.put("Attendance Date", attendance.getDate().toString());
            attendanceData.put("Time In", attendance.getTimeIn() != null ? attendance.getTimeIn().toString() : "N/A");
            attendanceData.put("Time Out", attendance.getTimeOut() != null ? attendance.getTimeOut().toString() : "N/A");
            attendanceData.put("Attendance Status", attendance.getAttendanceStatus().toString());
            attendanceList.add(attendanceData);
        }
        employeeDataSection.put("Attendances", attendanceList);

        // Combine all salary records into a single list
        List<Salary> sortedSalaries = salaries.stream()
                .sorted(Comparator.comparing(Salary::getCreatedAt).reversed())
                .toList();

        List<Map<String, Object>> salaryList = new ArrayList<>();
        for (Salary salary : sortedSalaries) {
            Map<String, Object> salaryData = new LinkedHashMap<>();
            salaryData.put("Salary Date", salary.getCreatedAt().toString());
            salaryData.put("Salary Amount", salary.getAmount());
            salaryList.add(salaryData);
        }
        employeeDataSection.put("Salaries", salaryList);

        excelData.add(employeeDataSection);

        return excelData;
    }


}



















//    @Override
//    public void createExcelFile(List<Map<String, Object>> excelData, OutputStream outputStream) throws IOException {
//        if (excelData == null || excelData.isEmpty()) {
//            throw new IllegalArgumentException("Excel data is empty");
//        }
//
//        Map<String, Object> employee = new LinkedHashMap<>();
//        List<Map<String, Object>> attendance = new ArrayList<>();
//        List<Map<String, Object>> salaries = new ArrayList<>();
//
//        // Reorder data sections
//        for (Map<String, Object> data : excelData) {
//            if (data.containsKey("employee")) {
//                employee = (Map<String, Object>) data.get("employee");
//            }
//            if (data.containsKey("Attendances")) {
//                List<Map<String, Object>> attendanceData = (List<Map<String, Object>>) data.get("Attendances");
//                attendance.addAll(attendanceData);
//            }
//            if (data.containsKey("Salaries")) {
//                List<Map<String, Object>> salaryData = (List<Map<String, Object>>) data.get("Salaries");
//                salaries.addAll(salaryData);
//            }
//        }
//
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Employee Data");
//
//        CellStyle centeredStyle = workbook.createCellStyle();
//        centeredStyle.setAlignment(CellStyle.ALIGN_CENTER);
//
//        int rowIndex = 0;
//
//        // Add Employee Details heading
//        Row employeeHeadingRow = sheet.createRow(rowIndex++);
//        Cell employeeHeadingCell = employeeHeadingRow.createCell(0);
//        employeeHeadingCell.setCellValue("Employee Details");
//        employeeHeadingCell.setCellStyle(centeredStyle);
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, employee.size() - 1));
//
//        // Make heading bold and increase font size
//        Font headingFont = workbook.createFont();
//        headingFont.setBold(true);
//        headingFont.setFontHeightInPoints((short) 14); // Set font size
//        CellStyle headingStyle = workbook.createCellStyle();
//        headingStyle.setFont(headingFont);
//        headingStyle.setAlignment(CellStyle.ALIGN_LEFT);
//        employeeHeadingCell.setCellStyle(headingStyle);
//
//
//        // Add Employee section
//        Row employeeHeaderRow = sheet.createRow(rowIndex++);
//        int colIndex = 0;
//        for (String key : employee.keySet()) {
//            Cell cell = employeeHeaderRow.createCell(colIndex++);
//            cell.setCellValue(key);
//
//            // Apply bold style to header row
//            CellStyle boldStyle = workbook.createCellStyle();
//            Font font = workbook.createFont();
//            font.setBold(true);
//            boldStyle.setFont(font);
//            boldStyle.setAlignment(CellStyle.ALIGN_CENTER);
//            cell.setCellStyle(boldStyle);
//        }
//
//        // Populate Employee data rows
//        Row employeeDataRow = sheet.createRow(rowIndex++);
//        colIndex = 0;
//        for (Object value : employee.values()) {
//            Cell cell = employeeDataRow.createCell(colIndex++);
//            cell.setCellStyle(centeredStyle);
//            if (value instanceof String) {
//                cell.setCellValue((String) value);
//            } else if (value instanceof Double) {
//                cell.setCellValue((Double) value);
//            } else if (value instanceof Integer) {
//                cell.setCellValue((Integer) value);
//            } else if (value instanceof Long) {
//                cell.setCellValue((Long) value);
//            } else if (value instanceof java.util.Date) {
//                cell.setCellValue((java.util.Date) value);
//                CreationHelper createHelper = workbook.getCreationHelper();
//                CellStyle cellStyle = workbook.createCellStyle();
//                cellStyle.setDataFormat(
//                        createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
//                cell.setCellStyle(cellStyle);
//            }
//        }
//
//        // Insert empty row as separator
//        sheet.createRow(rowIndex++);
//
//        // Add Attendance Details heading
//        Row attendanceHeadingRow = sheet.createRow(rowIndex++);
//        Cell attendanceHeadingCell = attendanceHeadingRow.createCell(0);
//        attendanceHeadingCell.setCellValue("Attendance Details");
//        attendanceHeadingCell.setCellStyle(centeredStyle);
//        sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 0, attendance.get(0).size() - 1));
//
//        // Make heading bold and increase font size
//        CellStyle attendanceHeadingStyle = workbook.createCellStyle();
//        attendanceHeadingStyle.setFont(headingFont);
//        attendanceHeadingStyle.setAlignment(CellStyle.ALIGN_LEFT);
//        attendanceHeadingCell.setCellStyle(attendanceHeadingStyle);
//
//        // Add Attendance section
//        Row attendanceHeaderRow = sheet.createRow(rowIndex++);
//        colIndex = 0;
//        for (String key : attendance.get(0).keySet()) {
//            Cell cell = attendanceHeaderRow.createCell(colIndex++);
//            cell.setCellValue(key);
//
//            // Apply bold style to header row
//            CellStyle boldStyle = workbook.createCellStyle();
//            Font font = workbook.createFont();
//            font.setBold(true);
//            boldStyle.setFont(font);
//            boldStyle.setAlignment(CellStyle.ALIGN_CENTER);
//            cell.setCellStyle(boldStyle);
//        }
//
//        // Populate Attendance data rows
//        for (Map<String, Object> attendanceRecord : attendance) {
//            Row attendanceDataRow = sheet.createRow(rowIndex++);
//            colIndex = 0;
//            for (Object value : attendanceRecord.values()) {
//                Cell cell = attendanceDataRow.createCell(colIndex++);
//                cell.setCellStyle(centeredStyle);
//                if (value instanceof String) {
//                    cell.setCellValue((String) value);
//                } else if (value instanceof Double) {
//                    cell.setCellValue((Double) value);
//                } else if (value instanceof Integer) {
//                    cell.setCellValue((Integer) value);
//                } else if (value instanceof Long) {
//                    cell.setCellValue((Long) value);
//                } else if (value instanceof java.util.Date) {
//                    cell.setCellValue((java.util.Date) value);
//                    CreationHelper createHelper = workbook.getCreationHelper();
//                    CellStyle cellStyle = workbook.createCellStyle();
//                    cellStyle.setDataFormat(
//                            createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
//                    cell.setCellStyle(cellStyle);
//                }
//            }
//        }
//
//        // Insert empty row as separator
//        sheet.createRow(rowIndex++);
//
//        // Add Salary Details heading
//        Row salaryHeadingRow = sheet.createRow(rowIndex++);
//        Cell salaryHeadingCell = salaryHeadingRow.createCell(0);
//        salaryHeadingCell.setCellValue("Salary Details");
//        salaryHeadingCell.setCellStyle(centeredStyle);
//        sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 0, salaries.get(0).size() - 1));
//
//        // Make heading bold and increase font size
//        CellStyle salaryHeadingStyle = workbook.createCellStyle();
//        salaryHeadingStyle.setFont(headingFont);
//        salaryHeadingStyle.setAlignment(CellStyle.ALIGN_LEFT);
//        salaryHeadingCell.setCellStyle(salaryHeadingStyle);
//
//        // Add Salary section
//        Row salaryHeaderRow = sheet.createRow(rowIndex++);
//        colIndex = 0;
//        for (String key : salaries.get(0).keySet()) {
//            Cell cell = salaryHeaderRow.createCell(colIndex++);
//            cell.setCellValue(key);
//
//            // Apply bold style to header row
//            CellStyle boldStyle = workbook.createCellStyle();
//            Font font = workbook.createFont();
//            font.setBold(true);
//            boldStyle.setFont(font);
//            boldStyle.setAlignment(CellStyle.ALIGN_CENTER);
//            cell.setCellStyle(boldStyle);
//        }
//
//        // Populate Salary data rows
//        for (Map<String, Object> salaryRecord : salaries) {
//            Row salaryDataRow = sheet.createRow(rowIndex++);
//            colIndex = 0;
//            for (Object value : salaryRecord.values()) {
//                Cell cell = salaryDataRow.createCell(colIndex++);
//                cell.setCellStyle(centeredStyle);
//                if (value instanceof String) {
//                    cell.setCellValue((String) value);
//                } else if (value instanceof Double) {
//                    cell.setCellValue((Double) value);
//                } else if (value instanceof Integer) {
//                    cell.setCellValue((Integer) value);
//                } else if (value instanceof Long) {
//                    cell.setCellValue((Long) value);
//                } else if (value instanceof java.util.Date) {
//                    cell.setCellValue((java.util.Date) value);
//                    CreationHelper createHelper = workbook.getCreationHelper();
//                    CellStyle cellStyle = workbook.createCellStyle();
//                    cellStyle.setDataFormat(
//                            createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
//                    cell.setCellStyle(cellStyle);
//                }
//            }
//        }
//
//        // Auto-size columns
//        for (int i = 0; i < employee.size(); i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        workbook.write(outputStream);
//        workbook.close();
//    }
//
