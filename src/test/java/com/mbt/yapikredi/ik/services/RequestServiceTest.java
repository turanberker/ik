package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.data.EnumRequestStatus;
import com.mbt.yapikredi.ik.dto.CreateEmployeeModel;
import com.mbt.yapikredi.ik.dto.CreateRequestModel;
import com.mbt.yapikredi.ik.dto.RequestDetailModel;
import com.mbt.yapikredi.ik.dto.RequestListModel;
import com.mbt.yapikredi.ik.dto.base.PageModel;
import com.mbt.yapikredi.ik.exceptions.CheckedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
public class RequestServiceTest {

    @Autowired
    private RequestService requestService;

    @Autowired
    private EmployeeService employeeService;

    private Long oneYearOldEmployeeId;

    private Long newlyHiredEmployeeId;

    @Before
    public void setUp() {
        CreateEmployeeModel cem = new CreateEmployeeModel();
        cem.setFirstName("Berker");
        cem.setLastName("Turan");
        cem.setStartDate(LocalDate.now().minusDays(1).minusYears(1));
        oneYearOldEmployeeId = employeeService.create(cem).getId();

        cem = new CreateEmployeeModel();
        cem.setFirstName("new Berker");
        cem.setLastName("Turan");
        cem.setStartDate(LocalDate.now().minusDays(1));
        newlyHiredEmployeeId = employeeService.create(cem).getId();
    }

    @Test
    public void createTest_oldEmployee_enoughDays() {
        CreateRequestModel requestModel = new CreateRequestModel();
        requestModel.setStartDate(LocalDate.now().plusDays(1));
        requestModel.setEndDate(LocalDate.now().plusDays(10));
        requestModel.setEmployeeId(oneYearOldEmployeeId);
        try {
            RequestDetailModel requestDetailModel = requestService.create(requestModel);
            Assert.assertNotNull(requestDetailModel.getId());
        } catch (CheckedException e) {
Assert.fail();
        }
    }

    @Test
    public void createTest_oldEmployee_notEnoughDays() {
        CreateRequestModel requestModel = new CreateRequestModel();
        requestModel.setStartDate(LocalDate.now().plusDays(1));
        requestModel.setEndDate(LocalDate.now().plusDays(30));
        requestModel.setEmployeeId(oneYearOldEmployeeId);
        try {
            RequestDetailModel requestDetailModel = requestService.create(requestModel);
            Assert.fail("Exception Atması Gerekiyor");
        } catch (CheckedException e) {
            Assert.assertEquals(e.getExceptionData().getBundle(), "notEnoughDays");
        }
    }


    @Test
    public void createTest_newEmployee_enoughDays() {
        CreateRequestModel requestModel = new CreateRequestModel();
        requestModel.setStartDate(LocalDate.now().plusDays(1));
        requestModel.setEndDate(LocalDate.now().plusDays(6));
        requestModel.setEmployeeId(newlyHiredEmployeeId);
        try {
            RequestDetailModel requestDetailModel = requestService.create(requestModel);
            Assert.assertNotNull(requestDetailModel.getId());
        } catch (CheckedException e) {
            Assert.fail();
        }
    }

    @Test
    public void createTest_newEmployee_notEnoughDays() {
        CreateRequestModel requestModel = new CreateRequestModel();
        requestModel.setStartDate(LocalDate.now().plusDays(1));
        requestModel.setEndDate(LocalDate.now().plusDays(30));
        requestModel.setEmployeeId(newlyHiredEmployeeId);
        try {
            RequestDetailModel requestDetailModel = requestService.create(requestModel);
            Assert.fail("Exception Atması Gerekiyor");
        } catch (CheckedException e) {
            Assert.assertEquals(e.getExceptionData().getBundle(), "newEmployeeCanExceedFiveDays");
        }
    }

    @Test
    public void createTest_withTwoRequest() {
        CreateRequestModel requestModel = new CreateRequestModel();
        requestModel.setStartDate(LocalDate.now().plusDays(1));
        requestModel.setEndDate(LocalDate.now().plusDays(6));
        requestModel.setEmployeeId(newlyHiredEmployeeId);
        try {
            RequestDetailModel requestDetailModel = requestService.create(requestModel);
        } catch (CheckedException e) {
            Assert.fail();
        }

        requestModel = new CreateRequestModel();
        requestModel.setStartDate(LocalDate.now().plusDays(1));
        requestModel.setEndDate(LocalDate.now().plusDays(5));
        requestModel.setEmployeeId(newlyHiredEmployeeId);
        try {
            RequestDetailModel requestDetailModel = requestService.create(requestModel);
            Assert.fail("Exception Atması Gerekiyor");
        } catch (CheckedException e) {
            Assert.assertEquals(e.getExceptionData().getBundle(), "notCompletedRequestExists");
        }
    }

    @Test
    public void approveTest() throws CheckedException {

        CreateRequestModel requestModel = new CreateRequestModel();
        requestModel.setStartDate(LocalDate.now().plusDays(1));
        requestModel.setEndDate(LocalDate.now().plusDays(6));
        requestModel.setEmployeeId(newlyHiredEmployeeId);

        RequestDetailModel requestDetailModel = requestService.create(requestModel);
        RequestDetailModel approve = requestService.approve(requestDetailModel.getId());
        Assert.assertEquals(approve.getStatus(), EnumRequestStatus.ONAYLANDI);
    }

    @Test
    public void reject() throws CheckedException {
        CreateRequestModel requestModel = new CreateRequestModel();
        requestModel.setStartDate(LocalDate.now().plusDays(1));
        requestModel.setEndDate(LocalDate.now().plusDays(6));
        requestModel.setEmployeeId(newlyHiredEmployeeId);

        RequestDetailModel requestDetailModel = requestService.create(requestModel);
        RequestDetailModel approve = requestService.reject(requestDetailModel.getId());
        Assert.assertEquals(approve.getStatus(), EnumRequestStatus.REDDEDILDI);
    }

    @Test
    public void findRequestsByStatus() throws CheckedException {
        CreateRequestModel requestModel = new CreateRequestModel();
        requestModel.setStartDate(LocalDate.now().plusDays(1));
        requestModel.setEndDate(LocalDate.now().plusDays(6));
        requestModel.setEmployeeId(newlyHiredEmployeeId);
        RequestDetailModel requestDetailModel = requestService.create(requestModel);
        PageModel<RequestListModel> requestsByStatus = requestService.findRequestsByStatus(EnumRequestStatus.ONAY_BEKLIYOR, 10, 0);
        Assert.assertEquals(requestsByStatus.getData().size(), 1);

    }
}
