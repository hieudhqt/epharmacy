package com.hieu.swd.epharmacy.app.prescriptiondetails;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.app.category.Category;
import com.hieu.swd.epharmacy.app.drug.Drug;
import com.hieu.swd.epharmacy.app.supplier.Supplier;
import com.hieu.swd.epharmacy.app.unit.Unit;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class PrescriptionDetailsRepositoryImpl implements PrescriptionDetailsRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public PrescriptionDetailsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PageResult findByPresId(String presId, int pageNumber, int pageSize) {
        // Get Prescription Details and Initialize the association with Drug
        Session currentSession = entityManager.unwrap(Session.class);

        PageResult pageResult = new PageResult(pageNumber, pageSize);

        final Long count = countPrescriptionDetails(presId);
        final int lastPageNumber = pageResult.getLastPageNumber(count);
        final int offset = pageResult.calculateOffset();

        Query<PrescriptionDetails> theQuery = currentSession.createQuery("FROM PrescriptionDetails pd INNER JOIN FETCH pd.drug WHERE pd.id.prescriptionId=:search");
        theQuery.setParameter("search", presId);
        theQuery.setFirstResult(offset);
        theQuery.setMaxResults(pageResult.getPageSize());
        List<PrescriptionDetails> prescriptionDetailsList = theQuery.getResultList();

        pageResult.setElements(prescriptionDetailsList)
                .setTotalPages(lastPageNumber)
                .setTotalElements(Math.toIntExact(count));

        return pageResult;
    }

    @Override
    public boolean insert(PrescriptionDetails prescriptionDetails) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createNativeQuery("INSERT INTO prescription_has_drug (prescription_id, drug_id, dose, frequency, time_unit, quantity) VALUES (?, ?, ?, ?, ?, ?)");
        theQuery.setParameter(1, prescriptionDetails.getPrescription().getId());
        theQuery.setParameter(2, prescriptionDetails.getDrug().getId());
        theQuery.setParameter(3, prescriptionDetails.getDose());
        theQuery.setParameter(4, prescriptionDetails.getFrequency());
        theQuery.setParameter(5, prescriptionDetails.getTimeUnit());
        theQuery.setParameter(6, prescriptionDetails.getQuantity());
        return theQuery.executeUpdate() > 0;
    }

    @Override
    public boolean deleteByPresId(String presId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("DELETE FROM PrescriptionDetails WHERE prescription.id=:deletedId");
        theQuery.setParameter("deletedId", presId);
        return theQuery.executeUpdate() > 0;
    }

    private Long countPrescriptionDetails(String presId) {
        Session currentSession = entityManager.unwrap(Session.class);
        final Query countQuery = currentSession.createQuery("SELECT COUNT(id.prescriptionId) FROM PrescriptionDetails WHERE id.prescriptionId=:search");
        countQuery.setParameter("search", presId);
        final Long count = (Long) countQuery.uniqueResult();
        return count;
    }
}
