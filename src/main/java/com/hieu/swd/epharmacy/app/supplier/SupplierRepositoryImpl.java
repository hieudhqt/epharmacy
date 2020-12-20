package com.hieu.swd.epharmacy.app.supplier;

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
public class SupplierRepositoryImpl implements SupplierRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public SupplierRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PageResult findAll(int pageNumber, int pageSize) {
        Session currentSession = entityManager.unwrap(Session.class);

        PageResult pageResult = new PageResult(pageNumber, pageSize);

        final Long count = countSuppliers();
        final int lastPageNumber = pageResult.getLastPageNumber(count);
        final int offset = pageResult.calculateOffset();

        Query<Supplier> theQuery = currentSession.createQuery("FROM Supplier", Supplier.class);
        theQuery.setFirstResult(offset);
        theQuery.setMaxResults(pageResult.getPageSize());
        List<Supplier> supplierList = theQuery.getResultList();

        pageResult.setElements(supplierList)
                .setTotalPages(lastPageNumber)
                .setTotalElements(Math.toIntExact(count));

        return pageResult;
    }

    @Override
    public Supplier findSupplierById(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Supplier> theQuery = currentSession.createQuery("FROM Supplier WHERE id=:search");
        theQuery.setParameter("search", id);
        List<Supplier> supplierList = theQuery.getResultList();
        if (supplierList.isEmpty()) {
            return null;
        }
        return supplierList.get(0);
    }

    @Override
    public boolean add(Supplier supplier) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createNativeQuery("INSERT INTO supplier(id, name, address) VALUES(?, ?, ?)");
        theQuery.setParameter(1, supplier.getId());
        theQuery.setParameter(2, supplier.getName());
        theQuery.setParameter(3, supplier.getAddress());
        return theQuery.executeUpdate() > 0;
    }

    @Override
    public boolean update(Supplier supplier) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("UPDATE Supplier SET name=:newName, address=:newAddress WHERE id=:updatingId");
        theQuery.setParameter("newName", supplier.getName());
        theQuery.setParameter("newAddress", supplier.getAddress());
        theQuery.setParameter("updatingId", supplier.getId());
        return theQuery.executeUpdate() > 0;
    }

    @Override
    public boolean deleteById(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("DELETE FROM Supplier WHERE id=:requiredId");
        theQuery.setParameter("requiredId", id);
        int result = theQuery.executeUpdate();
        return result > 0;
    }

    @Override
    public boolean isSupplierExisted(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<String> theQuery = currentSession.createQuery("SELECT 1 FROM Supplier WHERE id=:search");
        theQuery.setParameter("search", id);
        boolean isExisted = theQuery.uniqueResult() != null;
        return isExisted;
    }

    private Long countSuppliers() {
        Session currentSession = entityManager.unwrap(Session.class);
        final Query countQuery = currentSession.createQuery("SELECT COUNT(id) FROM Supplier ");
        final Long count = (Long) countQuery.uniqueResult();
        return count;
    }
}
