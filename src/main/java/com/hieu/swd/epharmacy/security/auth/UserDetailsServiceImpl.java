package com.hieu.swd.epharmacy.security.auth;

import com.hieu.swd.epharmacy.app.account.Account;
import com.hieu.swd.epharmacy.app.account.AccountRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @SneakyThrows
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(s);
        if (account == null) {
            throw new UsernameNotFoundException("Invalid username/password");
        }
        return new CustomUserDetails(account);
    }

}
