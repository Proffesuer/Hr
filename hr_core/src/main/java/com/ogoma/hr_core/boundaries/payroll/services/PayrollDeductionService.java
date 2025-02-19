package com.ogoma.hr_core.boundaries.payroll.services;

import com.ogoma.hr_core.boundaries.payroll.entities.PayrollDeduction;
import com.ogoma.hr_core.boundaries.payroll.repositories.PayrollDeductionRepository;
import com.ogoma.hr_core.models.requests.PagedDataRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PayrollDeductionService {
    private final PayrollDeductionRepository payrollDeductionRepository;

    public PayrollDeductionService(PayrollDeductionRepository payrollDeductionRepository) {
        this.payrollDeductionRepository = payrollDeductionRepository;
    }

    public Page<PayrollDeduction> getPayrollDeductions(PagedDataRequest pagedDataRequest) {
        Pageable pageable = PageRequest.of(pagedDataRequest.getPage(),
                pagedDataRequest.getPageSize());
        Page<PayrollDeduction> payrollDeductions =
                this.payrollDeductionRepository.findAll(pageable);
        return payrollDeductions;
    }

    public PayrollDeduction createPayrollDeduction(PayrollDeduction payrollDeduction) {
        payrollDeduction = this.payrollDeductionRepository.save(payrollDeduction);
        return payrollDeduction;
    }

    public Optional<PayrollDeduction> getPayrollDeductionById(Long id) {
        Optional<PayrollDeduction> payrollDeduction =
                this.payrollDeductionRepository.findById(id);
        return payrollDeduction;
    }

    public Optional<PayrollDeduction> updatePayrollDeduction(Long id, PayrollDeduction payrollDeduction) {
        Optional<PayrollDeduction> payrollDeduction1 = this.payrollDeductionRepository.findById(id);
        payrollDeduction1.ifPresent(pd -> {
            pd.setAmount(payrollDeduction.getAmount());
            pd.setName(payrollDeduction.getName());
            pd.setCalculation(payrollDeduction.getCalculation());
            this.payrollDeductionRepository.save(pd);
        });
        return payrollDeduction1;
    }

    public void removePayrollDeduction(Long id) {
        this.payrollDeductionRepository.deleteById(id);
    }
}
