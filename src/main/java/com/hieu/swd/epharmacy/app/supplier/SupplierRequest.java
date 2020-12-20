package com.hieu.swd.epharmacy.app.supplier;

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
public class SupplierRequest implements Serializable {

    private String id;

    private String name;

    private String address;

    public static Supplier convertToEntity(SupplierRequest supplierRequest) {
        return new Supplier()
                .setId(supplierRequest.getId())
                .setName(supplierRequest.getName())
                .setAddress(supplierRequest.getAddress());
    }

}
