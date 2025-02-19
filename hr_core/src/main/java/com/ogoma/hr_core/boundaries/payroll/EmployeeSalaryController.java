package com.ogoma.hr_core.boundaries.payroll;

import com.ogoma.hr_core.authentication.CustomUserDetails;
import com.ogoma.hr_core.boundaries.hr.employee_management.entities.Employee;
import com.ogoma.hr_core.boundaries.hr.employee_management.repositories.EmployeeRepository;
import com.ogoma.hr_core.boundaries.payroll.entities.EmployeePayrollAddition;
import com.ogoma.hr_core.boundaries.payroll.entities.EmployeePayrollDeduction;
import com.ogoma.hr_core.boundaries.payroll.models.EmployeeSalaryViewModel;
import com.ogoma.hr_core.boundaries.payroll.models.PayslipViewModel;
import com.ogoma.hr_core.boundaries.payroll.services.EmployeeSalaryService;
import com.ogoma.hr_core.models.requests.PagedDataRequest;
import com.ogoma.hr_core.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EmployeeSalaryController {
    private final EmployeeSalaryService employeeSalaryService;
    private final EmployeeRepository employeeRepository;

    public EmployeeSalaryController(EmployeeSalaryService employeeSalaryService, EmployeeRepository employeeRepository) {
        this.employeeSalaryService = employeeSalaryService;
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping(value = "/api/payslip/me", method = RequestMethod.GET)
    public ModelAndView getPayslip() {
        CustomUserDetails user = SecurityUtils.getCurrentUserDetails();
       Employee employee=
               this.employeeRepository.getOne(user.getId());
        PayslipViewModel payslipViewModel = employeeSalaryService.getPayslipViewModelByEmployeeId(employee.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("payroll/payslip");
        modelAndView.addObject("payslip", payslipViewModel);
        return modelAndView;
    }

    @RequestMapping(value = "api/employee-salary", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployeesSalary(PagedDataRequest pagedDataRequest) {
        Page<EmployeeSalaryViewModel> employeeSalaries =
                this.employeeSalaryService.getEmployeesSalary(pagedDataRequest);
        return ResponseEntity.ok(employeeSalaries);
    }

    @RequestMapping(value = "api/employee-salary/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployeesSalaryByEmployeeId(@PathVariable("id") Long employeeId) {
        EmployeeSalaryViewModel employeeSalaries =
                this.employeeSalaryService.getEmployeesSalaryByEmployeeId(employeeId);
        return ResponseEntity.ok(employeeSalaries);
    }

    @RequestMapping(value = "/api/payslip/{id}", method = RequestMethod.GET)
    public ModelAndView getPayslipByEmployeeId(@PathVariable("id") Long employeeId) {
        PayslipViewModel payslipViewModel = employeeSalaryService.getPayslipViewModelByEmployeeId(employeeId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("payroll/payslip");
        modelAndView.addObject("payslip", payslipViewModel);
        return modelAndView;
    }

    @RequestMapping(value = "/api/employee-payroll-addition", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployeePayrollAllowance(
            @Valid @RequestBody EmployeePayrollAddition employeePayrollAddition) {
        this.employeeSalaryService.createPayrollAddition(employeePayrollAddition);
        return ResponseEntity.ok(employeePayrollAddition);
    }

    @RequestMapping(value = "/api/employee-payroll-addition/{employeeId}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployeePayrollAllowancesByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        List<EmployeePayrollAddition> employeePayrollAdditions
                = this.employeeSalaryService.getPayrollAdditionsByEmployeeId(employeeId);
        return ResponseEntity.ok(employeePayrollAdditions);
    }

    @RequestMapping(value = "/api/employee-payroll-deductions", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployeePayrollDeduction(
            @Valid @RequestBody EmployeePayrollDeduction employeePayrollDeduction) {
        this.employeeSalaryService.createPayrollDeduction(employeePayrollDeduction);
        return ResponseEntity.ok(employeePayrollDeduction);
    }

    @RequestMapping(value = "/api/employee-payroll-deductions/{employeeId}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployeePayrollDeductionByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        List<EmployeePayrollDeduction> employeePayrollDeductions
                = this.employeeSalaryService.getPayrollDeductionsByEmployeeId(employeeId);
        return ResponseEntity.ok(employeePayrollDeductions);
    }

}
