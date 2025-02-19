package com.ogoma.hr_core.boundaries.hr.employee_management;

import com.ogoma.hr_core.boundaries.hr.employee_management.entities.Designation;
import com.ogoma.hr_core.boundaries.hr.employee_management.models.DesignationDto;
import com.ogoma.hr_core.boundaries.hr.employee_management.services.DesignationService;
import com.ogoma.hr_core.models.requests.PagedDataRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

@Controller
public class DesignationsController {
    private final DesignationService designationService;

    public DesignationsController(DesignationService designationService) {
        this.designationService = designationService;
    }

    @RequestMapping(value = "api/designations", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDesignations(PagedDataRequest pagedDataRequest) {
        Page<Designation> designations = this.designationService.getDesignations(pagedDataRequest);
        return ResponseEntity.ok(designations);
    }

    @RequestMapping(value = "api/designations/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDesignationsById(@PathVariable Long id) {
        Optional<Designation> designation = this.designationService.getDesignationById(id);
        return ResponseEntity.of(designation);
    }

    @RequestMapping(value = "api/designations", method = RequestMethod.POST)
    public ResponseEntity<?> createDesignation(@RequestBody @Valid DesignationDto designationDto) {
      Designation  designation = this.designationService.createDesignation(designationDto);
        return ResponseEntity.ok(designation);
    }

    @RequestMapping(value = "api/designations/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDesignation(@PathVariable Long id,@RequestBody @Valid DesignationDto designation) {
        Optional<Designation> designationUpdate = this.designationService.updateDesignation(id, designation);
        return ResponseEntity.of(designationUpdate);
    }

    @RequestMapping(value = "api/designations/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> removeDesignation(@PathVariable("id") Long id){
        this.designationService.removeDesignation(id);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/api/designations/excel-report")
    public ResponseEntity<?> getDesignationReport() throws IOException {
        ByteArrayInputStream byteArrayInputStream = this.designationService.generateReport();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=designation-report.xlsx");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
    }
}
