package com.hieu.swd.epharmacy.app.account;

import com.hieu.swd.epharmacy.app.PageResult;

public interface AccountRepository {

    PageResult findAll(int pageNumber, int pageSize) throws Exception;

    Account findByUsername(String username) throws Exception;

    boolean add(Account account) throws Exception;

    boolean update(Account account) throws Exception;

    boolean deleteByUsername(String username) throws Exception;

    boolean isUsernameExisted(String username) throws Exception;

    boolean isEmailExisted(String email) throws Exception;
}
