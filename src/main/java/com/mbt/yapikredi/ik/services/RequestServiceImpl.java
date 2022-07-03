package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.converter.RequestConverter;
import com.mbt.yapikredi.ik.data.EnumRequestStatus;
import com.mbt.yapikredi.ik.dto.CreateRequestModel;
import com.mbt.yapikredi.ik.dto.EmployeeAllowanceModel;
import com.mbt.yapikredi.ik.dto.RequestDetailModel;
import com.mbt.yapikredi.ik.dto.RequestListModel;
import com.mbt.yapikredi.ik.dto.base.PageModel;
import com.mbt.yapikredi.ik.entity.*;
import com.mbt.yapikredi.ik.exceptions.CheckedException;
import com.mbt.yapikredi.ik.exceptions.ExceptionData;
import com.mbt.yapikredi.ik.exceptions.UncheckedException;
import com.mbt.yapikredi.ik.repository.RequestRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private final EntityManager em;

    private final RequestRepository repository;

    private final RequestConverter requestConverter;

    private final EmployeeAllowanceDayCountService employeeAllowanceDayCountService;

    private final EmployeeService employeeService;

    public RequestServiceImpl(EntityManager em, RequestRepository repository, RequestConverter requestConverter, EmployeeAllowanceDayCountService employeeAllowanceDayCountService, EmployeeService employeeService) {
        this.em = em;
        this.repository = repository;
        this.requestConverter = requestConverter;
        this.employeeAllowanceDayCountService = employeeAllowanceDayCountService;
        this.employeeService = employeeService;
    }

    @Override
    public RequestDetailModel create(CreateRequestModel createRequestModel) throws CheckedException {

        EmployeeEntity employee = employeeService.getEmployee(createRequestModel.getEmployeeId());

        checkForAwaitingRequests(createRequestModel.getEmployeeId());
        int requestedDay = calculateRequestedDays(createRequestModel.getStartDate(), createRequestModel.getEndDate());
        Period intervalPeriod = Period.between(employee.getStartDate(), LocalDate.now());
        //işe başlayalı bir sene olmadı
        if (intervalPeriod.getYears() == 0) {
            checkForNewEmployee(employee, requestedDay);
        } else {
            checkForOldEmployee(employee, requestedDay);
        }
        RequestEntity requestEntity = requestConverter.convertToEntity(createRequestModel);
        requestEntity.setRequestedCount(requestedDay);
         requestEntity = repository.saveAndFlush(requestEntity);
        return requestConverter.convertToDetailModel(requestEntity);
    }

    private int calculateRequestedDays(LocalDate startDate, LocalDate endDate) {
        int requestedDay = 0;
        LocalDate dateIndex = startDate;
        while (dateIndex.isBefore(endDate)) {
            if (!(dateIndex.getDayOfWeek() == DayOfWeek.SATURDAY || dateIndex.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                requestedDay++;
            }
            dateIndex = dateIndex.plusDays(1);
        }
        return requestedDay;
    }

    private void checkForNewEmployee(EmployeeEntity employee, int requestedDay) throws CheckedException {
        if (requestedDay > 5) {
            throw new CheckedException(ExceptionData.fromBundle("newEmployeeCanExceedFiveDays"));
        }
        EmployeeAllowanceDayCountEntity rightsForAllowance = employeeAllowanceDayCountService.getByEmployeeId(employee.getId());

        if (rightsForAllowance != null && rightsForAllowance.getNumbersOfDays() - requestedDay < -5) {
            //çalışanın izin hak bilgisi var ve avans olarak kullanmış olacağı izin miktarı 5 i geçmişse
            throw new CheckedException(ExceptionData.fromBundle("newEmployeeCanExceedFiveDays"));
        }

    }

    private void checkForOldEmployee(EmployeeEntity employee, int requestedDay) throws CheckedException {
        EmployeeAllowanceDayCountEntity rightsForAllowance = employeeAllowanceDayCountService.getByEmployeeId(employee.getId());
        if (rightsForAllowance == null) {
            //Bir yılını doldurmuş çalışanların AllowanceCalculationJob.calculate jobında bu bilgisinin yazılmış olması gerekiyor
            //Dolayısıyla buraya düşmesi beklemiyor
            throw new UncheckedException(ExceptionData.fromMessage(String.format("%d id li employee nin izin bilgisi bulunamadı", employee.getId())));
        }
        if (rightsForAllowance.getNumbersOfDays() - requestedDay < 0) {
            throw new CheckedException(ExceptionData.fromBundle("notEnoughDays"));
        }
    }

    private void checkForAwaitingRequests(Long employeeId) throws CheckedException {
        QRequestEntity query = QRequestEntity.requestEntity;
        BooleanBuilder bb = new BooleanBuilder(query.employee.id.eq(employeeId));
        bb.and(query.requestStatus.eq(EnumRequestStatus.ONAY_BEKLIYOR));
        boolean exists = repository.exists(bb);
        if (exists) {
            throw new CheckedException(ExceptionData.fromBundle("notCompletedRequestExists"));
        }
    }

    @Override
    public RequestDetailModel approve(Long requestId) throws CheckedException {
        RequestEntity requestEntity = changeStatus(requestId, EnumRequestStatus.ONAYLANDI);
        employeeAllowanceDayCountService.removeDaysFromEmployee(requestEntity.getEmployee(),requestEntity.getRequestedCount());
        return requestConverter.convertToDetailModel(requestEntity);
    }

    @Override
    public RequestDetailModel reject(Long requestId) throws CheckedException {
        RequestEntity requestEntity = changeStatus(requestId, EnumRequestStatus.REDDEDILDI);
        return requestConverter.convertToDetailModel(requestEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<RequestListModel> findRequestsByStatus(EnumRequestStatus requestStatus, Integer pageSize, Integer startPage) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QRequestEntity requestQuery=QRequestEntity.requestEntity;
        if(requestStatus!=null){
            booleanBuilder.and(requestQuery.requestStatus.eq(requestStatus));
        }

        long totalElementCount = repository.count(booleanBuilder);
        List<RequestListModel> data = repository.getQueryFactory().select(Projections.bean(RequestListModel.class,
                        requestQuery.id.as("id"),
                                requestQuery.employee.id.as("employeeId"),
                                requestQuery.employee.firstName.concat(" ").concat(requestQuery.employee.lastName).as("employee"),
                                requestQuery.annualLeaveStart.as("startDate"),
                                requestQuery.annualLeaveEnd.as("endDate"),
                                requestQuery.requestStatus.as("status"),
                                requestQuery.requestedCount.as("requestedCount")
                        )
                )
                .from(requestQuery)
                .innerJoin(requestQuery.employee)
                .where(booleanBuilder)
                .limit(pageSize)
                .offset(startPage)
                .fetch();
        return new PageModel(data, startPage, pageSize, totalElementCount);
    }

    private RequestEntity changeStatus(Long requestId, EnumRequestStatus status) throws CheckedException {
        Optional<RequestEntity> optional = repository.findById(requestId);

        if(optional.isPresent()){
            if(EnumRequestStatus.ONAY_BEKLIYOR.equals(optional.get().getRequestStatus())){
                optional.get().setRequestStatus(status);
                return repository.save(optional.get());
            }else{
                throw new CheckedException(ExceptionData.fromBundle("requestNotAwaiting"));
            }

        }else{
            throw new CheckedException(ExceptionData.fromBundle("requestNotExist"));
        }
    }
}
