package com.hieu.swd.epharmacy.app.unit;

import com.hieu.swd.epharmacy.app.PageResult;

public interface UnitRepository {

    PageResult findAll(int pageNumber, int pageSize) throws Exception;

    Unit findUnitById(String id) throws Exception;

    boolean add(Unit unit) throws Exception;

    boolean update(Unit unit) throws Exception;

    boolean deleteById(String id) throws Exception;

    boolean isUnitExisted(String id) throws Exception;

}
