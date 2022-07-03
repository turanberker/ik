package com.mbt.yapikredi.ik.controller;

import com.mbt.yapikredi.ik.configuration.SwaggerConfiguration;
import com.mbt.yapikredi.ik.data.EnumRequestStatus;
import com.mbt.yapikredi.ik.dto.CreateRequestModel;
import com.mbt.yapikredi.ik.dto.EmployeeAllowanceModel;
import com.mbt.yapikredi.ik.dto.RequestDetailModel;
import com.mbt.yapikredi.ik.dto.RequestListModel;
import com.mbt.yapikredi.ik.dto.base.PageModel;
import com.mbt.yapikredi.ik.exceptions.CheckedException;
import com.mbt.yapikredi.ik.services.RequestService;
import io.swagger.annotations.Api;
import io.swagger.models.Swagger;
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
@RequestMapping("requests")
@Validated
@Api( tags = SwaggerConfiguration.REQUEST_TAG)
public class RequestRestController {

    @Autowired
    private RequestService requestService;

    @PostMapping(name = "New Request",path = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDetailModel> create(@RequestBody @Valid CreateRequestModel createRequestModel) throws CheckedException{
        return ResponseEntity.ok(requestService.create(createRequestModel));
    }

    @PatchMapping(name = "Approve Request", path = "/approve",produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<RequestDetailModel> approve(@RequestParam(name = "requestId") @NotNull @Positive Long requestId) throws CheckedException {
        return ResponseEntity.ok(requestService.approve(requestId));
    }

    @PatchMapping(name = "Reject Request", path = "/reject",produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<RequestDetailModel> reject(@NotNull @Positive Long requestId) throws CheckedException {
        return ResponseEntity.ok(requestService.reject(requestId));
    }

    @GetMapping(name = "GetRequestssByPage", path = "/findAll")
    public ResponseEntity<PageModel<RequestListModel>> findEmployeeAllowanceList(@RequestParam(name = "status", required = false) EnumRequestStatus status,
                                                                                 @RequestParam(name = "pageSize" ,required = false,defaultValue = "10") @Positive @NotNull Integer pageSize,
                                                                                 @RequestParam(name = "startPage",required = false, defaultValue = "0") @PositiveOrZero @NotNull Integer startPage    ) {
        return ResponseEntity.ok(requestService.findRequestsByStatus(status, pageSize, startPage));
    }
}
