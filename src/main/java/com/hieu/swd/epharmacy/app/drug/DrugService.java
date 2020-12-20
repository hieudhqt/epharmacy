package com.hieu.swd.epharmacy.app.drug;

import com.hieu.swd.epharmacy.app.PageResult;

public interface DrugService {

    PageResult findAll(int pageNumber, int pageSize) throws Exception;

    DrugResponse findDrugById(String id) throws Exception;

    PageResult findDrugByCategoryId(String cateId, int pageNumber, int pageSize) throws Exception;

    PageResult findDrugByName(String name, int pageNumber, int pageSize) throws Exception;

}
