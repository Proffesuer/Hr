package com.ogoma.hr_core.boundaries.payroll;

import com.ogoma.hr_core.boundaries.payroll.entities.PayrollDeduction;
import com.ogoma.hr_core.boundaries.payroll.services.PayrollDeductionService;
import com.ogoma.hr_core.models.requests.PagedDataRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PayrollDeductionController {
    private final PayrollDeductionService payrollDeductionService;

    public PayrollDeductionController(PayrollDeductionService payrollDeductionService) {
        this.payrollDeductionService = payrollDeductionService;
    }

    @RequestMapping(value = "api/payroll-deductions", method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody PayrollDeduction payrollDeduction) {
        payrollDeduction =
                this.payrollDeductionService.createPayrollDeduction(payrollDeduction);
        return ResponseEntity.ok(payrollDeduction);
    }
    @RequestMapping(value = "/api/payroll-deductions", method = RequestMethod.GET)
    public ResponseEntity<?> getPayrollDeductions(PagedDataRequest pagedDataRequest) {
        Page<PayrollDeduction> payrollDeductions =
                this.payrollDeductionService.getPayrollDeductions(pagedDataRequest);
        return ResponseEntity.ok(payrollDeductions);
    }
    @RequestMapping(value = "api/payroll-deductions/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPayrollDeductionById(@PathVariable("id") Long id) {
        Optional<PayrollDeduction> payrollDeduction
                = this.payrollDeductionService.getPayrollDeductionById(id);
        return ResponseEntity.of(payrollDeduction);
    }
    @RequestMapping(value = "api/payroll-deductions/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePayrollDeduction(@PathVariable("id") Long id,
                                                    @Valid @RequestBody PayrollDeduction payrollDeduction) {
        Optional<PayrollDeduction> payrollDeduction1 =
                this.payrollDeductionService.updatePayrollDeduction(id, payrollDeduction);
        return ResponseEntity.of(payrollDeduction1);
    }
    @RequestMapping(value = "api/payroll-deductions/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> removePayrollDeduction(@PathVariable("id") Long id) {
        this.payrollDeductionService.removePayrollDeduction(id);
        return ResponseEntity.ok().build();
    }
}
