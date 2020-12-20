package com.hieu.swd.epharmacy.app.prescriptiondetails;

import com.hieu.swd.epharmacy.app.drug.Drug;
import com.hieu.swd.epharmacy.app.prescription.Prescription;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "prescription_has_drug")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PrescriptionDetails {

    @EmbeddedId
    private PrescriptionDetailsKey id;

    @ManyToOne
    @MapsId("prescriptionId")
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne
    @MapsId("drugId")
    @JoinColumn(name = "drug_id")
    private Drug drug;

    @Column(name = "dose")
    private Integer dose;

    @Column(name = "frequency")
    private Integer frequency;

    @Column(name = "time_unit")
    private String timeUnit;

    @Column(name = "quantity")
    private Integer quantity;

}
