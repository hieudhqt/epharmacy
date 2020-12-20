package com.hieu.swd.epharmacy.app.prescription;

import com.hieu.swd.epharmacy.app.account.Account;
import com.hieu.swd.epharmacy.app.hospital.Hospital;
import com.hieu.swd.epharmacy.app.prescriptiondetails.PrescriptionDetailsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PrescriptionRequest implements Serializable {

    private String id;

    private Date createdDate;

    private String username;

    private String hospitalId;

    private String doctorName;

    private List<PrescriptionDetailsRequest> prescriptionDetailsRequestList;

    public static Prescription convertToEntity(PrescriptionRequest prescriptionRequest) {
        return new Prescription()
                .setId(prescriptionRequest.getId())
                .setCreatedDate(prescriptionRequest.getCreatedDate())
                .setDoctorName(prescriptionRequest.doctorName)
                .setAccount(new Account().setUsername(prescriptionRequest.getUsername()))
                .setHospital(new Hospital().setId(prescriptionRequest.getHospitalId()));
    }

}
