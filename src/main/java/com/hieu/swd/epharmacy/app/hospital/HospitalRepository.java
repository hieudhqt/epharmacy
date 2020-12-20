package com.hieu.swd.epharmacy.app.hospital;

import java.util.List;

public interface HospitalRepository {

    List<Hospital> findAll();

    Hospital findHospitalById(String id);

}
