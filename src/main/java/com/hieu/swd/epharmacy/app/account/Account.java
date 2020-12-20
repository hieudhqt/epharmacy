package com.hieu.swd.epharmacy.app.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hieu.swd.epharmacy.app.prescription.Prescription;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    @Column(name = "username")
    @Id
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "email")
    private String email;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "account")
    private List<Prescription> prescriptionList;

}
