package com.hieu.swd.epharmacy.app.account;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.app.account.request.AccountGeneralRequest;
import com.hieu.swd.epharmacy.app.account.response.AccountGeneralResponse;
import com.hieu.swd.epharmacy.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public PageResult findAll(int pageNumber, int pageSize) throws Exception {
        PageResult pageResult = accountRepository.findAll(pageNumber, pageSize);
        List<Account> accountList = pageResult.getElements();

        if (accountList.isEmpty()) {
            throw new ObjectNotFoundException("No account found");
        }

        List<AccountGeneralResponse> accountResponseList = AccountGeneralResponse.convertToObjectList(accountList);
        pageResult.setElements(accountResponseList);
        return pageResult;
    }

    @Override
    @Transactional
    public AccountGeneralResponse findByUsername(String username) throws Exception {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new ObjectNotFoundException("Username not found - " + username);
        }

        return new AccountGeneralResponse(account);
    }

    @Override
    @Transactional
    public boolean insert(AccountGeneralRequest signUpRequest) throws Exception {
        Account account = AccountGeneralRequest.convertToEntity(signUpRequest);
        return accountRepository.add(account);
    }

    @Override
    @Transactional
    public boolean insertGoogleAccount(AccountGeneralRequest googleAccount) throws Exception {
        Account account = AccountGeneralRequest.convertGoogleAccount(googleAccount);
        return accountRepository.add(account);
    }

    @Override
    @Transactional
    public boolean update(AccountGeneralRequest updateRequest) throws Exception {
        Account account = AccountGeneralRequest.convertToEntity(updateRequest);
        return accountRepository.update(account);
    }

    @Override
    @Transactional
    public boolean deleteByUsername(String username) throws Exception {
        return accountRepository.deleteByUsername(username);
    }

    @Override
    @Transactional
    public boolean isUsernameExisted(String username) throws Exception {
        return accountRepository.isUsernameExisted(username);
    }

    @Override
    @Transactional
    public boolean isEmailExisted(String email) throws Exception {
        return accountRepository.isEmailExisted(email);
    }
}
