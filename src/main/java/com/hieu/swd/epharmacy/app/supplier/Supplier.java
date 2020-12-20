package com.hieu.swd.epharmacy.app.supplier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hieu.swd.epharmacy.app.drug.Drug;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "supplier")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Supplier {

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "supplier")
    private List<Drug> drugList;
}
