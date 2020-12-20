package com.hieu.swd.epharmacy.app.prescriptiondetails;

import com.hieu.swd.epharmacy.app.drug.DrugResponse;
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
public class PrescriptionDetailsResponse implements Serializable {

    private Integer dose;

    private Integer frequency;

    private String timeUnit;

    private Integer quantity;

    private DrugResponse drugResponse;

    public PrescriptionDetailsResponse(PrescriptionDetails prescriptionDetails) {
        this.dose = prescriptionDetails.getDose();
        this.frequency = prescriptionDetails.getFrequency();
        this.timeUnit = prescriptionDetails.getTimeUnit();
        this.quantity = prescriptionDetails.getQuantity();
        this.drugResponse = new DrugResponse(prescriptionDetails.getDrug());
    }

    public static List<PrescriptionDetailsResponse> convertToObjectList(List<PrescriptionDetails> prescriptionDetailsList) {
        List<PrescriptionDetailsResponse> prescriptionDetailsResponseList = new ArrayList<>();
        for (PrescriptionDetails prescriptionDetails : prescriptionDetailsList) {
            prescriptionDetailsResponseList.add(new PrescriptionDetailsResponse(prescriptionDetails));
        }
        return prescriptionDetailsResponseList;
    }

}
