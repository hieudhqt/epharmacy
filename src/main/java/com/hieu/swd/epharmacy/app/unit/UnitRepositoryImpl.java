package com.hieu.swd.epharmacy.app.unit;

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
public class UnitRepositoryImpl implements UnitRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public UnitRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PageResult findAll(int pageNumber, int pageSize) {
        Session currentSession = entityManager.unwrap(Session.class);

        PageResult pageResult = new PageResult(pageNumber, pageSize);

        final Long count = countUnits();
        final int lastPageNumber = pageResult.getLastPageNumber(count);
        final int offset = pageResult.calculateOffset();

        Query<Unit> theQuery = currentSession.createQuery("FROM Unit", Unit.class);
        theQuery.setFirstResult(offset);
        theQuery.setMaxResults(pageResult.getPageSize());
        List<Unit> unitList = theQuery.getResultList();

        pageResult.setElements(unitList)
                .setTotalPages(lastPageNumber)
                .setTotalElements(Math.toIntExact(count));

        return pageResult;
    }

    @Override
    public Unit findUnitById(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Unit> theQuery = currentSession.createQuery("FROM Unit WHERE id=:search");
        theQuery.setParameter("search", id);
        List<Unit> unitList = theQuery.getResultList();
        if (unitList.isEmpty()) {
            return null;
        }
        return unitList.get(0);
    }

    @Override
    public boolean add(Unit unit) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createNativeQuery("INSERT INTO unit(id, name) VALUES(?, ?)");
        theQuery.setParameter(1, unit.getId());
        theQuery.setParameter(2, unit.getName());
        return theQuery.executeUpdate() > 0;
    }

    @Override
    public boolean update(Unit unit) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("UPDATE Unit SET name=:newName WHERE id=:updatingId");
        theQuery.setParameter("newName", unit.getName());
        theQuery.setParameter("updatingId", unit.getId());
        return theQuery.executeUpdate() > 0;
    }

    @Override
    public boolean deleteById(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("DELETE FROM Unit WHERE id=:requiredId");
        theQuery.setParameter("requiredId", id);
        int result = theQuery.executeUpdate();
        return result > 0;
    }

    @Override
    public boolean isUnitExisted(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<String> theQuery = currentSession.createQuery("SELECT 1 FROM Unit WHERE id=:search");
        theQuery.setParameter("search", id);
        boolean isExisted = theQuery.uniqueResult() != null;
        return isExisted;
    }

    private Long countUnits() {
        Session currentSession = entityManager.unwrap(Session.class);
        final Query countQuery = currentSession.createQuery("SELECT COUNT(id) FROM Unit");
        final Long count = (Long) countQuery.uniqueResult();
        return count;
    }
}
