package com.hieu.swd.epharmacy.app.account.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GoogleLoginRequest {

    @NotBlank
    private String idToken;

}
