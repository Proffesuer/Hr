package com.ogoma.hr_core.boundaries.hr.leave_management.services;

import com.ogoma.hr_core.boundaries.hr.leave_management.entities.LeaveType;
import com.ogoma.hr_core.boundaries.hr.leave_management.repositories.LeaveTypesRepository;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveTypesService {
    private final LeaveTypesRepository leaveTypesRepository;

    @Autowired
    public LeaveTypesService(LeaveTypesRepository leaveTypesRepository1) {
        this.leaveTypesRepository = leaveTypesRepository1;
    }

    public List<LeaveType> getAllLeaveTypes() {
        List<LeaveType> leaveTypes = this.leaveTypesRepository.findAll();
        return leaveTypes;
    }

    public Page<LeaveType> getAllLeaveTypes(Pageable pageable) {
        Page<LeaveType> leaveTypes = this.leaveTypesRepository.findAll(pageable);
        return leaveTypes;
    }

    public Optional<LeaveType> getLeaveTypeById(Long id) {
        Optional<LeaveType> leaveType = this.leaveTypesRepository.findById(id);
        return leaveType;
    }

    public LeaveType createLeaveType(LeaveType leaveType) {
        this.leaveTypesRepository.save(leaveType);
        return leaveType;
    }


    public void updateLeaveType(Long id, LeaveType leaveType) {
        LeaveType dbLeaveType = this.leaveTypesRepository.findById(id).orElse(null);
        if (dbLeaveType != null) {
            dbLeaveType.setName(leaveType.getName());
            dbLeaveType.setNumberOfDays(leaveType.getNumberOfDays());
            leaveTypesRepository.save(dbLeaveType);
        }
    }

    public ByteArrayOutputStream generateExcelReport() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        List<LeaveType> leaveTypes = this.leaveTypesRepository.findAll();
        XSSFSheet spreadsheet = (XSSFSheet) workbook.createSheet("Leave Types");
        XSSFRow row = spreadsheet.createRow(0);
        XSSFCell cell;
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Number of Days");
        int rowId = 1;
        for (LeaveType leaveType : leaveTypes) {
            row = spreadsheet.createRow(rowId++);
            cell = row.createCell(1);
            cell.setCellValue(leaveType.getName());
            cell = row.createCell(2);
            cell.setCellValue(leaveType.getNumberOfDays());
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        return byteArrayOutputStream;
    }

    public void removeLeaveTypeById(Long id) {
        this.leaveTypesRepository.deleteById(id);
    }
}
