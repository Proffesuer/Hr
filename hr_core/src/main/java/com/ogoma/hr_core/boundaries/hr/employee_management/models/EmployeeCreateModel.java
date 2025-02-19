package com.ogoma.hr_core.boundaries.hr.employee_management.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ogoma.hr_core.enums.GenderEnum;
import com.ogoma.hr_core.enums.converters.GenderEnumConverter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Convert;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class EmployeeCreateModel {
    @Valid
    @NotNull
    private BasicInfo basicInfo;
    @Valid
//    @NotNull
//    @NotEmpty
    @JsonProperty("emergencyContact")
    private EmergencyContactModel contactModel;
//    private List<ContactAddress> contactAddresses;
    @Valid
    @NotNull
    private EmployementDetail employementDetail;

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public EmergencyContactModel getContactModel() {
        return contactModel;
    }

    public void setContactModel(EmergencyContactModel contactModel) {
        this.contactModel = contactModel;
    }

    //    public List<ContactAddress> getContactAddresses() {
//        return contactAddresses;
//    }
//
//    public void setContactAddresses(List<ContactAddress> contactAddresses) {
//        this.contactAddresses = contactAddresses;
//    }

    public EmployementDetail getEmployementDetail() {
        return employementDetail;
    }

    public void setEmployementDetail(EmployementDetail employementDetail) {
        this.employementDetail = employementDetail;
    }

    public static class BasicInfo {
        @NotBlank(message = "First name is required")
        private String firstName;
        @NotBlank(message = "last name is required")
        private String lastName;
        @NotBlank(message = "Phone number is required")
        private String phone;
        @NotBlank(message = "Email is required")
        private String email;
        @NotBlank(message = "Identity number is required")
        private String identityNo;
        @NotBlank(message = "Physical address is required")
        private String city;
        private String zipCode;
        @NotBlank(message = "Postal address is required")
        private String postalAddress;
        private Long maritalStatusId;

        @NotNull(message = "Date of birth is required")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date dateOfBirth;
        private String avatarUrl;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIdentityNo() {
            return identityNo;
        }

        public void setIdentityNo(String identityNo) {
            this.identityNo = identityNo;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getPostalAddress() {
            return postalAddress;
        }

        public void setPostalAddress(String postalAddress) {
            this.postalAddress = postalAddress;
        }

        public Long getMaritalStatusId() {
            return maritalStatusId;
        }

        public void setMaritalStatusId(Long maritalStatusId) {
            this.maritalStatusId = maritalStatusId;
        }


        public Date getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getFullName() {
            return this.firstName.concat(" ").concat(this.lastName);
        }
    }

//    public static class ContactAddress {
//        @NotBlank(message = "Employee contact phone is required")
//        private String phoneNumber;
//        @NotBlank(message = "Employee contact name is required")
//        private String name;
//        @NotBlank(message = "Select employee contact relationship")
//        private String relationshipId;
//
//        public String getPhoneNumber() {
//            return phoneNumber;
//        }
//
//        public void setPhoneNumber(String phoneNumber) {
//            this.phoneNumber = phoneNumber;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getRelationshipId() {
//            return relationshipId;
//        }
//
//        public void setRelationshipId(String relationshipId) {
//            this.relationshipId = relationshipId;
//        }
//
//    }

    public static class EmployementDetail {
        @NotNull(message = "Select department")
        private Long departmentId;
        @NotNull(message = "Select designation")
        private Long designationId;
        @NotNull(message = "Joining date is required")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date joiningDate;

        private Long shiftId;
        @NotNull(message = "Salary amount is required")
        @Min(value = 0)
        private BigDecimal salaryAmount;
        private Long supervisorId;
        @NotBlank(message = "Kra pin number is required ")
        @JsonProperty(value = "kraPinNumber")
        private String kraPinNumber;
        @NotBlank(message = "Nhif number is required")
        @JsonProperty(value = "nhifNo")
        private String nhifNumber;
        @NotBlank(message = "Nssf number is required")
        @JsonProperty("nssfNo")
        private String nssfNumber;
        @NotNull(message = "Select gender")
        @Convert(converter = GenderEnumConverter.class)
        private GenderEnum gender;

        @NotBlank(message = "Account number is required")
        @JsonProperty("accountNo")
        private String accountNo;

        @NotNull(message = "Bank is required")
        @JsonProperty("bankId")
        private Long bankId;

        @NotNull(message = "Bank Branch is required")
        @JsonProperty("bankBranchId")
        private Long bankBranchId;

        public Long getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Long departmentId) {
            this.departmentId = departmentId;
        }

        public Long getDesignationId() {
            return designationId;
        }

        public void setDesignationId(Long designationId) {
            this.designationId = designationId;
        }

        public Date getJoiningDate() {
            return joiningDate;
        }

        public void setJoiningDate(Date joiningDate) {
            this.joiningDate = joiningDate;
        }

        public BigDecimal getSalaryAmount() {
            return salaryAmount;
        }

        public void setSalaryAmount(BigDecimal salaryAmount) {
            this.salaryAmount = salaryAmount;
        }

        public Long getSupervisorId() {
            return supervisorId;
        }

        public void setSupervisorId(Long supervisorId) {
            this.supervisorId = supervisorId;
        }

        public String getKraPinNumber() {
            return kraPinNumber;
        }

        public void setKraPinNumber(String kraPinNumber) {
            this.kraPinNumber = kraPinNumber;
        }

        public String getNhifNumber() {
            return nhifNumber;
        }

        public void setNhifNumber(String nhifNumber) {
            this.nhifNumber = nhifNumber;
        }

        public String getNssfNumber() {
            return nssfNumber;
        }

        public void setNssfNumber(String nssfNumber) {
            this.nssfNumber = nssfNumber;
        }

        public Long getShiftId() {
            return shiftId;
        }

        public void setShiftId(Long shiftId) {
            this.shiftId = shiftId;
        }

        public GenderEnum getGender() {
            return gender;
        }

        public void setGender(GenderEnum gender) {
            this.gender = gender;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public Long getBankId() {
            return bankId;
        }

        public void setBankId(Long bankId) {
            this.bankId = bankId;
        }

        public Long getBankBranchId() {
            return bankBranchId;
        }

        public void setBankBranchId(Long bankBranchId) {
            this.bankBranchId = bankBranchId;
        }
    }
}
