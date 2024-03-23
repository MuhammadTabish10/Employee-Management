package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.service.EmployeeService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.EmployeeManagement.service.ExcelGenerationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ExcelGenerationService excelGenerationService;

    public EmployeeController(EmployeeService employeeService, ExcelGenerationService excelGenerationService) {
        this.employeeService = employeeService;
        this.excelGenerationService = excelGenerationService;
    }

    @PostMapping("/employee")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        EmployeeDto employee = employeeService.save(employeeDto);
        return ResponseEntity.ok(employee);
    }
    @GetMapping("/employee/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(@PathVariable Boolean status){
        List<EmployeeDto> employees = employeeService.getAllEmployee(status);
        return ResponseEntity.ok(employees);
    }
    @GetMapping("/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id){
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }
    @PutMapping("/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employeeDto){
        EmployeeDto employee = employeeService.update(id,employeeDto);
        return ResponseEntity.ok(employee);
    }
    @DeleteMapping("/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id){
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/employee/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> setEmployeeStatusToActiveById(@PathVariable Long id) {
        employeeService.setToActive(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employee/excel/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void getEmployeeExcel(
            @PathVariable Long id,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=employee.xlsx");

        Map<String,Object> map = employeeService.getEmployeeDataForExcel(id,startDate,endDate);
        List<Map<String, Object>> excelData = excelGenerationService.convertEmployeeToExcelData(map);

        OutputStream outputStream = response.getOutputStream();
        excelGenerationService.createExcelFile(excelData, outputStream);
        outputStream.close();
    }
}
