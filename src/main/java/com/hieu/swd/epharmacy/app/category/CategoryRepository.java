package com.hieu.swd.epharmacy.app.category;

import com.hieu.swd.epharmacy.app.PageResult;

public interface CategoryRepository {

    PageResult findAll(int pageNumber, int pageSize) throws Exception;

    Category findCategoryById(String id) throws Exception;

}
