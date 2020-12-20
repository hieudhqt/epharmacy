package com.hieu.swd.epharmacy.app.drug;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    public DrugServiceImpl(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    @Override
    @Transactional
    public PageResult findAll(int pageNumber, int pageSize) throws Exception {
        PageResult pageResult = drugRepository.findAll(pageNumber, pageSize);

        List<DrugResponse> drugResponseList = convertToResponseDto(pageResult.getElements());

        pageResult.setElements(drugResponseList);
        return pageResult;
    }

    @Override
    @Transactional
    public DrugResponse findDrugById(String id) throws Exception {
        Drug drug = drugRepository.findDrugById(id);
        if (drug == null) {
            throw new ObjectNotFoundException("Drug not found " + id);
        }
        return new DrugResponse(drug);
    }

    @Override
    @Transactional
    public PageResult findDrugByCategoryId(String cateId, int pageNumber, int pageSize) throws Exception {
        PageResult pageResult = drugRepository.findDrugByCategoryId(cateId, pageNumber, pageSize);

        List<DrugResponse> drugResponseList = convertToResponseDto(pageResult.getElements());
        pageResult.setElements(drugResponseList);
        return pageResult;
    }

    @Override
    @Transactional
    public PageResult findDrugByName(String name, int pageNumber, int pageSize) throws Exception {
        PageResult pageResult = drugRepository.findDrugByName(name, pageNumber, pageSize);

        List<DrugResponse> drugResponseList = convertToResponseDto(pageResult.getElements());
        pageResult.setElements(drugResponseList);
        return pageResult;
    }

    private List<DrugResponse> convertToResponseDto(List<Drug> drugList) throws Exception {
        if (drugList.isEmpty()) {
            throw new ObjectNotFoundException("No drug found");
        }
        List<Drug> newDrugList = new ArrayList<>();
        for (Drug drug : drugList) {
            newDrugList.add(drug);
        }
        return DrugResponse.convertToObjectList(newDrugList);
    }
}
