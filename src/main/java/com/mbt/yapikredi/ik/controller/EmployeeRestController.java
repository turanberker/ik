package com.mbt.yapikredi.ik.controller;

import com.mbt.yapikredi.ik.configuration.SwaggerConfiguration;
import com.mbt.yapikredi.ik.dto.CreateEmployeeModel;
import com.mbt.yapikredi.ik.dto.EmployeeAllowanceModel;
import com.mbt.yapikredi.ik.dto.EmployeeModel;
import com.mbt.yapikredi.ik.dto.base.PageModel;
import com.mbt.yapikredi.ik.services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("employees")
@Validated
@Api(tags = SwaggerConfiguration.EMPLOYEE_TAG)
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(name = "GetEmployeesByPage", path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Çalışanların listesinin döndüğü endpointtir")
    public ResponseEntity<PageModel<EmployeeAllowanceModel>> findEmployeeAllowanceList(@RequestParam(name = "name", required = false) String name,
                                                                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") @Positive @NotNull Integer pageSize,
                                                                                       @RequestParam(name = "startPage", required = false, defaultValue = "0") @PositiveOrZero @NotNull Integer startPage) {
        return ResponseEntity.ok(employeeService.findEmployeeAllowanceList(name, pageSize, startPage));
    }

    @PostMapping(name = "CreateEmployee", path = "/create")
    @ApiOperation(value = "Yeni çalışan kaydetmek için kullanılan endpointtir")
    public ResponseEntity<EmployeeModel> create(@Valid @RequestBody CreateEmployeeModel model) {
        return ResponseEntity.ok(employeeService.create(model));
    }
}
