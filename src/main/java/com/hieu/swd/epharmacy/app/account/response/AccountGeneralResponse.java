package com.hieu.swd.epharmacy.app.account.response;

import com.hieu.swd.epharmacy.app.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AccountGeneralResponse {

    private String username;

    private String name;

    private Boolean gender;

    private String email;

    private Date birthDate;

    private String address;

    public AccountGeneralResponse(Account account) {
        this.username = account.getUsername();
        this.name = account.getName();
        this.gender = account.getGender();
        this.email = account.getEmail();
        this.birthDate = account.getBirthDate();
        this.address = account.getAddress();
    }

    public static List<AccountGeneralResponse> convertToObjectList(List<Account> accountList) {
        List<AccountGeneralResponse> accountResponseList = new ArrayList<>();
        for (Account account : accountList) {
            accountResponseList.add(new AccountGeneralResponse(account));
        }
        return accountResponseList;
    }
}
