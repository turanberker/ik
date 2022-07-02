package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.converter.RequestConverter;
import com.mbt.yapikredi.ik.data.EnumRequestStatus;
import com.mbt.yapikredi.ik.dto.CreateRequestModel;
import com.mbt.yapikredi.ik.dto.RequestDetailModel;
import com.mbt.yapikredi.ik.entity.EmployeeAllowanceDayCountEntity;
import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import com.mbt.yapikredi.ik.entity.QRequestEntity;
import com.mbt.yapikredi.ik.entity.RequestEntity;
import com.mbt.yapikredi.ik.exceptions.CheckedException;
import com.mbt.yapikredi.ik.exceptions.ExceptionData;
import com.mbt.yapikredi.ik.exceptions.UncheckedException;
import com.mbt.yapikredi.ik.repository.RequestRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

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
         requestEntity = repository.saveAndFlush(requestEntity);
        return requestConverter.convertToDetailModel(requestEntity);
    }

    private int calculateRequestedDays(LocalDate startDate, LocalDate endDate) {
        int requestedDay = 0;
        LocalDate dateIndex = startDate;
        while (dateIndex.isBefore(endDate)) {
            if (!(dateIndex.getDayOfWeek() == DayOfWeek.SATURDAY || dateIndex.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                dateIndex = dateIndex.plusDays(1);
                requestedDay++;
            }
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
    public RequestDetailModel approve(Long requestId) {
        RequestEntity requestEntity = changeStatus(requestId, EnumRequestStatus.ONAYLANDI);
        return requestConverter.convertToDetailModel(requestEntity);
    }

    @Override
    public RequestDetailModel reject(Long requestId) {
        RequestEntity requestEntity = changeStatus(requestId, EnumRequestStatus.REDDEDILDI);
        return requestConverter.convertToDetailModel(requestEntity);
    }

    private RequestEntity changeStatus(Long requestId, EnumRequestStatus status) {
        RequestEntity entity = repository.getReferenceById(requestId);
        entity.setRequestStatus(status);
        return repository.save(entity);
    }
}
