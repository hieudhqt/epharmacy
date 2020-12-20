package com.hieu.swd.epharmacy.app.unit;

import com.hieu.swd.epharmacy.app.PageResult;

public interface UnitService {

    PageResult findAll(int pageNumber, int pageSize) throws Exception;

    UnitResponse findUnitById(String id) throws Exception;

    boolean add(UnitRequest unitRequest) throws Exception;

    boolean update(UnitRequest unitRequest) throws Exception;

    boolean deleteById(String id) throws Exception;

    boolean isUnitExisted(String id) throws Exception;

}
