package com.hieu.swd.epharmacy.app.prescription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PrescriptionResponse implements Serializable {

    private String id;

    private Date createdDate;

    private String hospitalName;

    private String doctorName;

    public PrescriptionResponse(Prescription prescription) {
        this.id = prescription.getId();
        this.createdDate = prescription.getCreatedDate();
        this.hospitalName = prescription.getHospital().getName();
        this.doctorName = prescription.getDoctorName();
    }

    public static List<PrescriptionResponse> convertToObjectList(List<Prescription> prescriptionList) {
        List<PrescriptionResponse> prescriptionResponseList = new ArrayList<>();
        for (Prescription prescription : prescriptionList) {
            prescriptionResponseList.add(new PrescriptionResponse(prescription));
        }
        return prescriptionResponseList;
    }

}
