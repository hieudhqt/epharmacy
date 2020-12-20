package com.hieu.swd.epharmacy.app.prescription;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    @Transactional
    public PageResult findPrescriptionByUsername(String username, int pageNumber, int pageSize) throws Exception {
        PageResult pageResult = prescriptionRepository.findPrescriptionByUsername(username, pageNumber, pageSize);
        List<Prescription> prescriptionList = pageResult.getElements();

        if (prescriptionList.isEmpty()) {
            throw new ObjectNotFoundException(username + " has no prescription");
        }

        List<PrescriptionResponse> prescriptionResponseList = PrescriptionResponse.convertToObjectList(prescriptionList);
        pageResult.setElements(prescriptionResponseList);
        return pageResult;
    }

    @Override
    @Transactional
    public boolean insert(PrescriptionRequest prescriptionRequest) throws Exception{
        Prescription prescription = PrescriptionRequest.convertToEntity(prescriptionRequest);
        return prescriptionRepository.insert(prescription);
    }

    @Override
    @Transactional
    public boolean deleteByPresId(String id) throws Exception {
        return prescriptionRepository.deleteByPresId(id);
    }

    @Override
    public boolean isPrescriptionExisted(String id) throws Exception {
        return prescriptionRepository.isPrescriptionExisted(id);
    }

}
