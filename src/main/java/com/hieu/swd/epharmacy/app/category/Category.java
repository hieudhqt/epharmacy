package com.hieu.swd.epharmacy.app.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hieu.swd.epharmacy.app.drug.Drug;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Drug> drugList;

}
