package com.hieu.swd.epharmacy.response;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse implements Serializable {

    private int status;
    private String description;
    private String message;
    private String timeStamp;

}
