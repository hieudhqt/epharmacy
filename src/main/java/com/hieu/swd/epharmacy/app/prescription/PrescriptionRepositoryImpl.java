package com.hieu.swd.epharmacy.app.prescription;

import com.hieu.swd.epharmacy.app.PageResult;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PrescriptionRepositoryImpl implements PrescriptionRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public PrescriptionRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PageResult findPrescriptionByUsername(String username, int pageNumber, int pageSize) {
        Session currentSession = entityManager.unwrap(Session.class);

        PageResult pageResult = new PageResult(pageNumber, pageSize);

        final Long count = countAccounts();
        final int lastPageNumber = pageResult.getLastPageNumber(count);
        final int offset = pageResult.calculateOffset();

        Query<Prescription> theQuery = currentSession.createQuery("SELECT DISTINCT p FROM Prescription p INNER JOIN FETCH p.hospital WHERE p.account.username=:search ORDER BY p.createdDate ASC");
        theQuery.setFirstResult(offset);
        theQuery.setMaxResults(pageResult.getPageSize());
        theQuery.setParameter("search", username);
        List<Prescription> prescriptionList = theQuery.getResultList();

        pageResult.setElements(prescriptionList)
                .setTotalPages(lastPageNumber)
                .setTotalElements(Math.toIntExact(count));

        return pageResult;
    }

    @Override
    public boolean insert(Prescription prescription) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createNativeQuery("INSERT INTO prescription (id, created_date, account_username, doctor_name, hospital_id) VALUES (?, ?, ?, ?, ?)");
        theQuery.setParameter(1, prescription.getId());
        theQuery.setParameter(2, prescription.getCreatedDate());
        theQuery.setParameter(3, prescription.getAccount().getUsername());
        theQuery.setParameter(4, prescription.getDoctorName());
        theQuery.setParameter(5, prescription.getHospital().getId());
        return theQuery.executeUpdate() > 0;
    }

    @Override
    public boolean deleteByPresId(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("DELETE FROM Prescription WHERE id=:deletedId");
        theQuery.setParameter("deletedId", id);
        return theQuery.executeUpdate() > 0;
    }

    @Override
    public boolean isPrescriptionExisted(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("SELECT 1 FROM Prescription WHERE id=:search");
        theQuery.setParameter("search", id);
        return theQuery.uniqueResult() != null;
    }

    private Long countAccounts() {
        Session currentSession = entityManager.unwrap(Session.class);
        final Query countQuery = currentSession.createQuery("SELECT COUNT(id) FROM Prescription");
        final Long count = (Long) countQuery.uniqueResult();
        return count;
    }
}
