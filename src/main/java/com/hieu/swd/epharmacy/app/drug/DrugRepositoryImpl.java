package com.hieu.swd.epharmacy.app.drug;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.app.category.Category;
import com.hieu.swd.epharmacy.app.supplier.Supplier;
import com.hieu.swd.epharmacy.app.unit.Unit;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import java.util.List;

@Repository
@Slf4j
public class DrugRepositoryImpl implements DrugRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public DrugRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PageResult findAll(int pageNumber, int pageSize) {
        Session currentSession = entityManager.unwrap(Session.class);

        PageResult pageResult = new PageResult(pageNumber, pageSize);

        final Long count = countDrugs();
        final int lastPageNumber = pageResult.getLastPageNumber(count);
        final int offset = pageResult.calculateOffset();

        Query<Drug> theQuery = currentSession.createQuery("FROM Drug", Drug.class);
        theQuery.setFirstResult(offset);
        theQuery.setMaxResults(pageResult.getPageSize());
        List<Drug> drugList = theQuery.getResultList();

        pageResult.setElements(drugList)
                .setTotalPages(lastPageNumber)
                .setTotalElements(Math.toIntExact(count));

        return pageResult;
    }

    @Override
    public Drug findDrugById(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Drug> theQuery = currentSession.createQuery("FROM Drug WHERE id=:search");
        theQuery.setParameter("search", id);
        List<Drug> drugList = theQuery.getResultList();
        if (drugList.isEmpty()) {
            return null;
        }
        return drugList.get(0);
    }

    @Override
    public PageResult findDrugByCategoryId(String cateId, int pageNumber, int pageSize) {
        Session currentSession = entityManager.unwrap(Session.class);

        PageResult pageResult = new PageResult(pageNumber, pageSize);

        final Long count = countDrugsByCategoryId(cateId);
        final int lastPageNumber = pageResult.getLastPageNumber(count);
        final int offset = pageResult.calculateOffset();

        Query<Drug> theQuery = currentSession.createQuery("FROM Drug WHERE category.id=:search");
        theQuery.setParameter("search", cateId);
        theQuery.setFirstResult(offset);
        theQuery.setMaxResults(pageResult.getPageSize());
        List<Drug> drugList = theQuery.getResultList();

        pageResult.setElements(drugList)
                .setTotalPages(lastPageNumber);

        return pageResult;
    }

    @Override
    public PageResult findDrugByName(String name, int pageNumber, int pageSize) {
        Session currentSession = entityManager.unwrap(Session.class);

        PageResult pageResult = new PageResult(pageNumber, pageSize);

        final Long count = countDrugsByName(name);
        final int lastPageNumber = pageResult.getLastPageNumber(count);
        final int offset = pageResult.calculateOffset();

        Query<Drug> theQuery = currentSession.createQuery("FROM Drug WHERE name LIKE :search");
        theQuery.setParameter("search", "%" + name + "%");
        theQuery.setFirstResult(offset);
        theQuery.setMaxResults(pageResult.getPageSize());
        List<Drug> drugList = theQuery.getResultList();

        pageResult.setElements(drugList)
                .setTotalPages(lastPageNumber)
                .setTotalElements(Math.toIntExact(count));

        return pageResult;
    }

    private Long countDrugs() {
        Session currentSession = entityManager.unwrap(Session.class);
        final Query countQuery = currentSession.createQuery("SELECT COUNT(id) FROM Drug");
        final Long count = (Long) countQuery.uniqueResult();
        return count;
    }

    private Long countDrugsByCategoryId(String cateId) {
        Session currentSession = entityManager.unwrap(Session.class);
        final Query countQuery = currentSession.createQuery("SELECT COUNT(id) FROM Drug WHERE category.id=:search");
        countQuery.setParameter("search", cateId);
        final Long count = (Long) countQuery.uniqueResult();
        return count;
    }

    private Long countDrugsByName(String name) {
        Session currentSession = entityManager.unwrap(Session.class);
        final Query countQuery = currentSession.createQuery("SELECT COUNT(id) FROM Drug WHERE name LIKE :search");
        countQuery.setParameter("search", name);
        final Long count = (Long) countQuery.uniqueResult();
        return count;
    }
}
