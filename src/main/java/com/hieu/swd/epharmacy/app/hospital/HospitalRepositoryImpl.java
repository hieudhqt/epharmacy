package com.hieu.swd.epharmacy.app.hospital;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class HospitalRepositoryImpl implements HospitalRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public HospitalRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Hospital> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Hospital> theQuery = currentSession.createQuery("FROM Hospital", Hospital.class);
        List<Hospital> hospitals = theQuery.getResultList();
        return hospitals;
    }

    @Override
    public Hospital findHospitalById(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Hospital> theQuery = currentSession.createQuery("FROM Hospital WHERE id=:search");
        theQuery.setParameter("search", id);
        Hospital hospital = theQuery.getSingleResult();
        return hospital;
    }
}
