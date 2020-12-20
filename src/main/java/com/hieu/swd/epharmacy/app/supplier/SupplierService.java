package com.hieu.swd.epharmacy.app.supplier;

import com.hieu.swd.epharmacy.app.PageResult;

public interface SupplierService {

    PageResult findAll(int pageNumber, int pageSize) throws Exception;

    SupplierResponse findSupplierById(String id) throws Exception;

    boolean add(SupplierRequest supplier) throws Exception;

    boolean update(SupplierRequest supplier) throws Exception;

    boolean deleteById(String id) throws Exception;

    boolean isSupplierExisted(String id) throws Exception;

}
