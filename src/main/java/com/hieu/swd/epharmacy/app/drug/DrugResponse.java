package com.hieu.swd.epharmacy.app.drug;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DrugResponse implements Serializable {

    private String id;

    private String description;

    private String name;

    private Float cost;

    private String categoryName;

    private String supplierName;

    private String unitName;

    public DrugResponse(Drug drug) {
        this.id = drug.getId();
        this.description = drug.getDescription();
        this.name = drug.getName();
        this.cost = drug.getCost();
        this.categoryName = drug.getCategory().getName();
        this.supplierName = drug.getSupplier().getName();
        this.unitName = drug.getUnit().getName();
    }

    public static List<DrugResponse> convertToObjectList(List<Drug> drugList) {
        List<DrugResponse> drugResponseList = new ArrayList<>();
        for (Drug drug : drugList) {
            drugResponseList.add(new DrugResponse(drug));
        }
        return drugResponseList;
    }

}
