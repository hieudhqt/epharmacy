package com.hieu.swd.epharmacy.app.prescription;

import com.hieu.swd.epharmacy.app.PageResult;

public interface PrescriptionService {

    PageResult findPrescriptionByUsername(String username, int pageNumber, int pageSize) throws Exception;

    boolean insert(PrescriptionRequest prescriptionRequest) throws Exception;

    boolean deleteByPresId(String id) throws Exception;

    boolean isPrescriptionExisted(String id) throws Exception;

}
