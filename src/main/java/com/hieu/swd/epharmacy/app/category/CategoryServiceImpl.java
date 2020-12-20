package com.hieu.swd.epharmacy.app.category;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public PageResult findAll(int pageNumber, int pageSize) throws Exception{
        PageResult pageResult = categoryRepository.findAll(pageNumber, pageSize);
        List<Category> categoryList = pageResult.getElements();

        if (categoryList.isEmpty()) {
            throw new ObjectNotFoundException("No category found");
        }

        List<CategoryResponse> categoryResponseList = CategoryResponse.convertToObjectList(categoryList);
        pageResult.setElements(categoryResponseList);
        return pageResult;
    }
}
