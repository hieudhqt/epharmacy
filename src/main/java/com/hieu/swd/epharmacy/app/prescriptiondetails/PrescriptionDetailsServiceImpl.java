package com.hieu.swd.epharmacy.app.prescriptiondetails;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PrescriptionDetailsServiceImpl implements PrescriptionDetailsService {

    @Autowired
    private PrescriptionDetailsRepository prescriptionDetailsRepository;

    @Autowired
    public PrescriptionDetailsServiceImpl(PrescriptionDetailsRepository prescriptionDetailsRepository) {
        this.prescriptionDetailsRepository = prescriptionDetailsRepository;
    }

    @Override
    @Transactional
    public PageResult findByPresId(String presId, int pageNumber, int pageSize) throws Exception {
        PageResult pageResult = prescriptionDetailsRepository.findByPresId(presId, pageNumber, pageSize);
        List<PrescriptionDetails> prescriptionDetailsList = pageResult.getElements();

        if (prescriptionDetailsList.isEmpty()) {
            throw new ObjectNotFoundException(presId + " has no detail");
        }

        List<PrescriptionDetailsResponse> prescriptionDetailsResponseList = PrescriptionDetailsResponse.convertToObjectList(prescriptionDetailsList);
        pageResult.setElements(prescriptionDetailsResponseList);
        return pageResult;
    }

    @Override
    @Transactional
    public boolean insert(PrescriptionDetailsRequest prescriptionDetailsRequest, String presId) throws Exception {
        PrescriptionDetails prescriptionDetails = PrescriptionDetailsRequest.convertToEntity(prescriptionDetailsRequest, presId);
        return prescriptionDetailsRepository.insert(prescriptionDetails);
    }

    @Override
    @Transactional
    public boolean deleteByPresId(String id) throws Exception {
        return prescriptionDetailsRepository.deleteByPresId(id);
    }
}
