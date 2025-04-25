package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.dto.CompanyDTO;
import com.git.Nog022.MeetHub.dto.JoinCompanyDTO;
import com.git.Nog022.MeetHub.entity.Company;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO save(@RequestBody @Validated CompanyDTO companyDTO) {
        return companyService.save(companyDTO);
    }

    @PostMapping("/joinCompany")
    @ResponseStatus(HttpStatus.CREATED)
    public String join(@RequestBody @Validated JoinCompanyDTO dto) {
        return companyService.joinCompany(dto);
    }
}
