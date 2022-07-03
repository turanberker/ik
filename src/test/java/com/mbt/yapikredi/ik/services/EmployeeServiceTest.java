package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.dto.CreateEmployeeModel;
import com.mbt.yapikredi.ik.dto.EmployeeAllowanceModel;
import com.mbt.yapikredi.ik.dto.EmployeeModel;
import com.mbt.yapikredi.ik.dto.base.PageModel;
import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;


    @MockBean
    private AnnualLeaveAllowanceService allowanceService;

    @Before
    public void setUp() {
        doNothing().when(allowanceService).addEmployeeAllowancesForNewEmployee(any());


    }

    @Test
    public void createTest(){

        CreateEmployeeModel createEmployeeModel=new CreateEmployeeModel();
        createEmployeeModel.setStartDate(LocalDate.now().minusYears(1).minusDays(5));
        createEmployeeModel.setFirstName("Berker");
        createEmployeeModel.setLastName("Turan");

        EmployeeModel employeeModel = employeeService.create(createEmployeeModel);
        Assert.assertNotNull(employeeModel.getId());
    }

    @Test
    public void getEmployeeTest(){
        CreateEmployeeModel createEmployeeModel=new CreateEmployeeModel();
        createEmployeeModel.setStartDate(LocalDate.now().minusYears(1).minusDays(5));
        createEmployeeModel.setFirstName("Berker");
        createEmployeeModel.setLastName("Turan");

        EmployeeModel employeeModel = employeeService.create(createEmployeeModel);

        EmployeeEntity employee = employeeService.getEmployee(employeeModel.getId());
        Assert.assertEquals(employee.getId(),employeeModel.getId());
    }

    @Test
    public void findEmployeeAllowanceListTest(){
        CreateEmployeeModel createEmployeeModel=new CreateEmployeeModel();
        createEmployeeModel.setStartDate(LocalDate.now().minusYears(1).minusDays(5));
        createEmployeeModel.setFirstName("Berker");
        createEmployeeModel.setLastName("Turan");
        EmployeeModel employeeModel = employeeService.create(createEmployeeModel);

        PageModel<EmployeeAllowanceModel> b = employeeService.findEmployeeAllowanceList("B", 10, 0);
        Assert.assertEquals(b.getData().size(),1);
    }
}
