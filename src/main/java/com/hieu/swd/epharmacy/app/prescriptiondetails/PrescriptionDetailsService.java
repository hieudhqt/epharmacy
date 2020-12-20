package com.hieu.swd.epharmacy.app.prescriptiondetails;

import com.hieu.swd.epharmacy.app.PageResult;

public interface PrescriptionDetailsService {

    PageResult findByPresId(String presId, int pageNumber, int pageSize) throws Exception;

    boolean insert(PrescriptionDetailsRequest prescriptionDetailsRequest, String presId) throws Exception;

    boolean deleteByPresId(String id) throws Exception;

}
