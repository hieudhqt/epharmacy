package com.hieu.swd.epharmacy.app.prescriptiondetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDetailsKey implements Serializable {

    @Column(name = "prescription_id")
    private String prescriptionId;

    @Column(name = "drug_id")
    private String drugId;

}
