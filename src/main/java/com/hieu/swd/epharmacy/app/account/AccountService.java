package com.hieu.swd.epharmacy.app.account;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.app.account.request.AccountGeneralRequest;
import com.hieu.swd.epharmacy.app.account.response.AccountGeneralResponse;

public interface AccountService {

    PageResult findAll(int pageNumber, int pageSize) throws Exception;

    AccountGeneralResponse findByUsername(String username) throws Exception;

    boolean insert(AccountGeneralRequest signUpRequest) throws Exception;

    boolean insertGoogleAccount(AccountGeneralRequest googleAccount) throws Exception;

    boolean update(AccountGeneralRequest updateRequest) throws Exception;

    boolean deleteByUsername(String username) throws Exception;

    boolean isUsernameExisted(String username) throws Exception;

    boolean isEmailExisted(String email) throws Exception;
}
