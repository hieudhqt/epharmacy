package com.hieu.swd.epharmacy.app.account.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {

    private String username;

    private String token;
}
