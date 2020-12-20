package com.hieu.swd.epharmacy.app.category;

import com.hieu.swd.epharmacy.app.PageResult;

public interface CategoryService {

    PageResult findAll(int pageNumber, int pageSize) throws Exception;

}
