package com.hieu.swd.epharmacy.app.supplier;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional
    public PageResult findAll(int pageNumber, int pageSize) throws Exception {
        PageResult pageResult = supplierRepository.findAll(pageNumber, pageSize);
        List<Supplier> supplierList = pageResult.getElements();

        if (supplierList.isEmpty()) {
            throw new ObjectNotFoundException("No supplier found");
        }

        List<SupplierResponse> supplierResponseList = SupplierResponse.convertToObjectList(supplierList);
        pageResult.setElements(supplierResponseList);
        return pageResult;
    }

    @Override
    @Transactional
    public SupplierResponse findSupplierById(String id) throws Exception {
        Supplier supplier = supplierRepository.findSupplierById(id);

        if (supplier == null) {
            throw new ObjectNotFoundException("Unit not found - " + id);
        }

        return new SupplierResponse(supplier);
    }

    @Override
    @Transactional
    public boolean add(SupplierRequest supplierRequest) throws Exception {
        Supplier supplier = SupplierRequest.convertToEntity(supplierRequest);
        return supplierRepository.add(supplier);
    }

    @Override
    @Transactional
    public boolean update(SupplierRequest supplierRequest) throws Exception {
        Supplier supplier = SupplierRequest.convertToEntity(supplierRequest);
        return supplierRepository.update(supplier);
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        return supplierRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean isSupplierExisted(String id) throws Exception {
        return supplierRepository.isSupplierExisted(id);
    }
}
