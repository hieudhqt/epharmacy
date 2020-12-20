package com.hieu.swd.epharmacy.app.account;

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
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public AccountRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PageResult findAll(int pageNumber, int pageSize) {
        PageResult pageResult = new PageResult(pageNumber, pageSize);

        final Long count = countAccounts();
        final int lastPageNumber = pageResult.getLastPageNumber(count);
        final int offset = pageResult.calculateOffset();

        Session currentSession = entityManager.unwrap(Session.class);
        Query<Account> theQuery = currentSession.createQuery("FROM Account", Account.class);
        theQuery.setFirstResult(offset);
        theQuery.setMaxResults(pageResult.getPageSize());
        List<Account> accountList = theQuery.getResultList();

        pageResult.setElements(accountList)
                .setTotalPages(lastPageNumber)
                .setTotalElements(Math.toIntExact(count));

        return pageResult;
    }

    @Override
    public Account findByUsername(String username) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Account> theQuery = currentSession.createQuery("FROM Account WHERE username=:search");
        theQuery.setParameter("search", username);
        List<Account> accountList = theQuery.getResultList();
        if (accountList.isEmpty()) {
            return null;
        }
        return accountList.get(0);
    }

    @Override
    public boolean add(Account account) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createNativeQuery("INSERT INTO account(username, password, name, gender, email, birth_date, address) VALUES(?, ?, ?, ?, ?, ?, ?)");
        theQuery.setParameter(1, account.getUsername());
        theQuery.setParameter(2, account.getPassword());
        theQuery.setParameter(3, account.getName());
        theQuery.setParameter(4, account.getGender());
        theQuery.setParameter(5, account.getEmail());
        theQuery.setParameter(6, account.getBirthDate());
        theQuery.setParameter(7, account.getAddress());
        return theQuery.executeUpdate() > 0;
    }

    @Override
    public boolean update(Account account) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("UPDATE Account SET name=:newName, password=:newPassword, address=:newAddress, gender=:newGender, birthDate=:newBirthdate WHERE username=:updatingUsername");
        theQuery.setParameter("newName", account.getName());
        theQuery.setParameter("newPassword", account.getPassword());
        theQuery.setParameter("newAddress", account.getAddress());
        theQuery.setParameter("newGender", account.getGender());
        theQuery.setParameter("newBirthdate", account.getBirthDate());
        theQuery.setParameter("updatingUsername", account.getUsername());
        return theQuery.executeUpdate() > 0;
    }

    @Override
    public boolean deleteByUsername(String username) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("DELETE FROM Account WHERE username=:requiredUsername");
        theQuery.setParameter("requiredUsername", username);
        int result = theQuery.executeUpdate();
        return result > 0;
    }

    @Override
    public boolean isUsernameExisted(String username) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<String> theQuery = currentSession.createQuery("SELECT 1 FROM Account WHERE username=:search");
        theQuery.setParameter("search", username);
        boolean isExisted = theQuery.uniqueResult() != null;
        return isExisted;
    }

    @Override
    public boolean isEmailExisted(String email) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<String> theQuery = currentSession.createQuery("SELECT 1 FROM Account WHERE email=:search");
        theQuery.setParameter("search", email);
        boolean isExisted = theQuery.uniqueResult() != null;
        return isExisted;
    }

    private Long countAccounts() {
        Session currentSession = entityManager.unwrap(Session.class);
        final Query countQuery = currentSession.createQuery("SELECT COUNT(id) FROM Account");
        final Long count = (Long) countQuery.uniqueResult();
        return count;
    }
}
