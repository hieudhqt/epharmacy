package com.hieu.swd.epharmacy.app.unit;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    @Transactional
    public PageResult findAll(int pageNumber, int pageSize) throws Exception {
        PageResult pageResult = unitRepository.findAll(pageNumber, pageSize);
        List<Unit> unitList = pageResult.getElements();

        if (unitList.isEmpty()) {
            throw new ObjectNotFoundException("No unit found");
        }

        List<UnitResponse> unitResponseList = UnitResponse.convertToObjectList(unitList);
        pageResult.setElements(unitResponseList);
        return pageResult;
    }

    @Override
    @Transactional
    public UnitResponse findUnitById(String id) throws Exception {
        Unit unit = unitRepository.findUnitById(id);

        if (unit == null) {
            throw new ObjectNotFoundException("Unit not found - " + id);
        }

        return new UnitResponse(unit);
    }

    @Override
    @Transactional
    public boolean add(UnitRequest unitRequest) throws Exception {
        Unit unit = UnitRequest.convertToEntity(unitRequest);
        return unitRepository.add(unit);
    }

    @Override
    @Transactional
    public boolean update(UnitRequest unitRequest) throws Exception {
        Unit unit = UnitRequest.convertToEntity(unitRequest);
        return unitRepository.update(unit);
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        return unitRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean isUnitExisted(String id) throws Exception {
        return unitRepository.isUnitExisted(id);
    }

}
