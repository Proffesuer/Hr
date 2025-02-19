package com.ogoma.hr_core.boundaries.hr.employee_management.services;

import com.ogoma.hr_core.boundaries.access_control.entities.User;
import com.ogoma.hr_core.boundaries.access_control.services.UserService;
import com.ogoma.hr_core.boundaries.hr.employee_management.entities.Employee;
import com.ogoma.hr_core.boundaries.hr.employee_management.models.EmergencyContactModel;
import com.ogoma.hr_core.boundaries.hr.employee_management.models.EmployeeCreateModel;
import com.ogoma.hr_core.boundaries.hr.employee_management.models.EmployeeQuery;
import com.ogoma.hr_core.boundaries.hr.employee_management.repositories.EmployeeRepository;
import com.ogoma.hr_core.models.requests.PagedDataRequest;
import com.ogoma.hr_core.utils.RandomStringGenerator;
import com.ogoma.hr_core.utils.mail.EmailModel;
import com.ogoma.hr_core.utils.mail.MailSender;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import java.util.*;

@Service
public class EmployeesService {
    private final EmployeeRepository employeeRepository;
    private final MailSender mailSender;
    private final UserService userService;

    public EmployeesService(EmployeeRepository employeeRepository, MailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.employeeRepository = employeeRepository;
        this.userService = userService;
    }

    public Page<EmployeeQuery> getEmployees(PagedDataRequest pagedDataRequest) {
        Pageable pageable = PageRequest.of(pagedDataRequest.getPage(), pagedDataRequest.getPageSize());
        Page<EmployeeQuery> employees = this.employeeRepository.getAllEmployees(pageable);
        return employees;
    }

    public Employee createEmployee(EmployeeCreateModel employeeCreateModel) {
        User user = new User();
        Employee employee = new Employee();
//        List<EmployeeContactAddress> employeeContactAddresses = new ArrayList<>();
//        employeeCreateModel.getContactAddresses().forEach(add -> {
//            EmployeeContactAddress employeeContactAddress = new EmployeeContactAddress();
//            BeanUtils.copyProperties(add, employeeContactAddress);
//            employeeContactAddresses.add(employeeContactAddress);
//        });
        BeanUtils.copyProperties(employeeCreateModel.getBasicInfo(), user);
        BeanUtils.copyProperties(employeeCreateModel.getEmployementDetail(), employee);
//        employee.setEmployeeContactAddresses(employeeContactAddresses);
        String userPassword = RandomStringGenerator.randomStringGenerator(8, true);
        user.setPassword(userPassword);
        employee.setUser(user);
        this.employeeRepository.save(employee);

        //save emergency contacts
        EmergencyContactModel contactModel = employeeCreateModel.getContactModel();
        contactModel.setUserId(user.getId());
        userService.updateEmergencyContact(contactModel);

        sendRegistrationEmail(employeeCreateModel, userPassword);
        return employee;
    }


    private void sendRegistrationEmail(EmployeeCreateModel employeeCreateModel, String password) {
        Map<String, Object> emailTemplateVariables = new HashMap<>();
        emailTemplateVariables.put("employeeDetail", employeeCreateModel);
        emailTemplateVariables.put("password", password);
        EmailModel emailModel = new EmailModel();
        emailModel.setSubject("Employee Registration");
        emailModel.setHtml(true);
        emailModel.setTo(employeeCreateModel.getBasicInfo().getEmail());
        emailModel.setTemplateVariable(emailTemplateVariables);
        emailModel.setTemplatePath("/employee_registration_successful");
        UriComponentsBuilder base = ServletUriComponentsBuilder.fromCurrentContextPath().path("/");
        String url = base.build().toUriString();
        emailTemplateVariables.put("link", url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mailSender.sendMail(emailModel);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void removeEmployee(Long employeeId) {
        this.employeeRepository.deleteById(employeeId);
    }
}
