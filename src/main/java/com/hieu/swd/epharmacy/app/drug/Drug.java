package com.hieu.swd.epharmacy.app.drug;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hieu.swd.epharmacy.app.category.Category;
import com.hieu.swd.epharmacy.app.prescriptiondetails.PrescriptionDetails;
import com.hieu.swd.epharmacy.app.supplier.Supplier;
import com.hieu.swd.epharmacy.app.unit.Unit;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "drug")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Drug {

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private Float cost;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "drug")
    private List<PrescriptionDetails> prescriptionDetailsList;

//    @ManyToMany
//    @JoinTable(
//            name = "prescription_has_drug",
//            joinColumns = @JoinColumn(name = "drug_id"),
//            inverseJoinColumns = @JoinColumn(name = "prescription_id")
//    )
//    private Set<Prescription> prescriptionList;

}
