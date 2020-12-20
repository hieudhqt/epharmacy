package com.hieu.swd.epharmacy.app.supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SupplierResponse implements Serializable {

    private String id;

    private String name;

    private String address;

    public SupplierResponse(Supplier supplier) {
        this.id = supplier.getId();
        this.name = supplier.getName();
        this.address = supplier.getAddress();
    }

    public static List<SupplierResponse> convertToObjectList(List<Supplier> supplierList) {
        List<SupplierResponse> supplierResponseList = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            supplierResponseList.add(new SupplierResponse(supplier));
        }
        return supplierResponseList;
    }
}
