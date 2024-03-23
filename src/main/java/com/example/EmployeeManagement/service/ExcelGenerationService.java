package com.example.EmployeeManagement.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface ExcelGenerationService {
    List<Map<String, Object>> convertEmployeeToExcelData(Map<String,Object> dataMap);
    void createExcelFile(List<Map<String, Object>> excelData, OutputStream outputStream) throws IOException;
}
