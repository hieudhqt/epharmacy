package com.hieu.swd.epharmacy.app.drug;

import com.hieu.swd.epharmacy.app.PageResult;

public interface DrugRepository {

    PageResult findAll(int pageNumber, int pageSize) throws Exception;

    Drug findDrugById(String id) throws Exception;

    PageResult findDrugByCategoryId(String cateId, int pageNumber, int pageSize) throws Exception;

    PageResult findDrugByName(String name, int pageNumber, int pageSize) throws Exception;

}
