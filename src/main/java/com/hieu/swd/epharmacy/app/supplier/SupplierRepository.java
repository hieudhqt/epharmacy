package com.hieu.swd.epharmacy.app.supplier;

import com.hieu.swd.epharmacy.app.PageResult;

public interface SupplierRepository {

    PageResult findAll(int pageNumber, int pageSize) throws Exception;

    Supplier findSupplierById(String id) throws Exception;

    boolean add(Supplier supplier) throws Exception;

    boolean update(Supplier supplier) throws Exception;

    boolean deleteById(String id) throws Exception;

    boolean isSupplierExisted(String id) throws Exception;

}
