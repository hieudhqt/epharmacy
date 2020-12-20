package com.hieu.swd.epharmacy.app.account.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hieu.swd.epharmacy.app.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountGeneralRequest implements Serializable {

    @NotBlank
    private String username;

    private String password;

    @Email
    @NotBlank
    private String email;

    private String name;

    private Boolean gender;

    private Date birthDate;

    private String address;

    public static Account convertToEntity(AccountGeneralRequest signUpRequest) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return new Account()
                .setUsername(signUpRequest.getUsername())
                .setPassword(encoder.encode(signUpRequest.getPassword()))
                .setName(signUpRequest.getName())
                .setGender(signUpRequest.getGender())
                .setEmail(signUpRequest.getEmail())
                .setBirthDate(signUpRequest.getBirthDate())
                .setAddress(signUpRequest.getAddress());
    }

    public static Account convertGoogleAccount(AccountGeneralRequest googleAccount) {
        return new Account()
                .setUsername(googleAccount.username)
                .setPassword(googleAccount.password)
                .setName(googleAccount.name)
                .setGender(null)
                .setEmail(googleAccount.email)
                .setBirthDate(null)
                .setAddress(null);
    }

}
