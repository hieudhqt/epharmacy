package com.hieu.swd.epharmacy.app.category;

import com.hieu.swd.epharmacy.app.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PageResult findAll(int pageNumber, int pageSize) {
        Session currentSession = entityManager.unwrap(Session.class);

        PageResult pageResult = new PageResult(pageNumber, pageSize);

        final Long count = countCategories();
        final int lastPageNumber = pageResult.getLastPageNumber(count);
        final int offset = pageResult.calculateOffset();

        Query<Category> theQuery = currentSession.createQuery("FROM Category ORDER BY name ASC", Category.class);
        theQuery.setFirstResult(offset);
        theQuery.setMaxResults(pageResult.getPageSize());
        List<Category> categoryList = theQuery.getResultList();

        pageResult.setElements(categoryList)
                .setTotalPages(lastPageNumber)
                .setTotalElements(Math.toIntExact(count));

        return pageResult;
    }

    @Override
    public Category findCategoryById(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Category> theQuery = currentSession.createQuery("FROM Category WHERE id=:search");
        theQuery.setParameter("search", id);
        List<Category> categoryList = theQuery.getResultList();
        if (categoryList.isEmpty()) {
            return null;
        }
        return categoryList.get(0);
    }

    private Long countCategories() {
        Session currentSession = entityManager.unwrap(Session.class);
        final Query countQuery = currentSession.createQuery("SELECT COUNT(id) FROM Category");
        final Long count = (Long) countQuery.uniqueResult();
        return count;
    }
}
