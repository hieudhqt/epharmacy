package com.hieu.swd.epharmacy.app.prescription;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hieu.swd.epharmacy.app.account.Account;
import com.hieu.swd.epharmacy.app.hospital.Hospital;
import com.hieu.swd.epharmacy.app.prescriptiondetails.PrescriptionDetails;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "prescription")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Prescription {

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "doctor_name")
    private String doctorName;

    @ManyToOne
    @JoinColumn(name = "account_username")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "prescription")
    private List<PrescriptionDetails> prescriptionDetailsList;

//    @ManyToMany
//    @JoinTable(
//            name = "prescription_has_drug",
//            joinColumns = @JoinColumn(name = "prescription_id"),
//            inverseJoinColumns = @JoinColumn(name = "drug_id")
//    )
//    private Set<Drug> drugList;

}
