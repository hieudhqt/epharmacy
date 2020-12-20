package com.hieu.swd.epharmacy.app.hospital;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hieu.swd.epharmacy.app.prescription.Prescription;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "hospital")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hospital {

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "hospital")
    private List<Prescription> prescriptionList;

}
