package com.hieu.swd.epharmacy.app.unit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitRequest implements Serializable {

    private String id;

    private String name;

    public static Unit convertToEntity(UnitRequest unitRequest) {
        return new Unit()
                .setId(unitRequest.getId())
                .setName(unitRequest.getName());
    }

}
