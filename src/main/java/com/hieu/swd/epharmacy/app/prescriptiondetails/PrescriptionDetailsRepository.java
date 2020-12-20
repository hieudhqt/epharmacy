package com.hieu.swd.epharmacy.app.prescriptiondetails;

import com.hieu.swd.epharmacy.app.PageResult;

public interface PrescriptionDetailsRepository {

    PageResult findByPresId(String presId, int pageNumber, int pageSize) throws Exception;

    boolean insert(PrescriptionDetails prescriptionDetails) throws Exception;

    boolean deleteByPresId(String presId) throws Exception;

}
