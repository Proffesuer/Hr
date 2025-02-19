package com.ogoma.hr_core.boundaries.accounting.requests;
import com.ogoma.hr_core.boundaries.accounting.entities.Expense;
import com.ogoma.hr_core.boundaries.accounting.enums.ExpenseStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ExpenseRequest {
    @NotBlank(message = "Item mame is required")
    private String itemName;
    @NotBlank(message = "Purchase From is required")
    private String purchaseFrom;
    @NotNull(message = "Purchase date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date purchaseDate;
    @NotNull(message = "Expense amount is required")
    private BigDecimal amount;
    @NotNull(message = "Select Employee")
    private Long purchasedById;
    @Enumerated(EnumType.STRING)
    private ExpenseStatus status;

    @NotNull(message = "Select payment method")
    @Enumerated(EnumType.STRING)
    private Expense.PaymentMethods paidBy;
    private List<MultipartFile> attachments;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPurchaseFrom() {
        return purchaseFrom;
    }

    public void setPurchaseFrom(String purchaseFrom) {
        this.purchaseFrom = purchaseFrom;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getPurchasedById() {
        return purchasedById;
    }
    public void setPurchasedById(Long purchasedById) {
        this.purchasedById = purchasedById;
    }
    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    public Expense.PaymentMethods getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(Expense.PaymentMethods paidBy) {
        this.paidBy = paidBy;
    }

    public List<MultipartFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MultipartFile> attachments) {
        this.attachments = attachments;
    }
}
