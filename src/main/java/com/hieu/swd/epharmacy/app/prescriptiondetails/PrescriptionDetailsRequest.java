package com.hieu.swd.epharmacy.app.prescriptiondetails;

import com.hieu.swd.epharmacy.app.drug.Drug;
import com.hieu.swd.epharmacy.app.prescription.Prescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PrescriptionDetailsRequest implements Serializable {

    private Integer dose;

    private Integer frequency;

    private String timeUnit;

    private Integer quantity;

    private String drugId;

    public static PrescriptionDetails convertToEntity(PrescriptionDetailsRequest prescriptionDetailsRequest, String presId) {
        return new PrescriptionDetails()
                .setPrescription(new Prescription().setId(presId))
                .setDrug(new Drug().setId(prescriptionDetailsRequest.getDrugId()))
                .setDose(prescriptionDetailsRequest.getDose())
                .setFrequency(prescriptionDetailsRequest.getFrequency())
                .setTimeUnit(prescriptionDetailsRequest.getTimeUnit())
                .setQuantity(prescriptionDetailsRequest.getQuantity());
    }

    public static List<PrescriptionDetails> convertToEntityList(List<PrescriptionDetailsRequest> prescriptionDetailsRequestList, String presId) {
        List<PrescriptionDetails> prescriptionDetailsList = new ArrayList<>();
        for (PrescriptionDetailsRequest prescriptionDetailsRequest : prescriptionDetailsRequestList) {
            prescriptionDetailsList.add(PrescriptionDetailsRequest.convertToEntity(prescriptionDetailsRequest, presId));
        }
        return prescriptionDetailsList;
    }

}
