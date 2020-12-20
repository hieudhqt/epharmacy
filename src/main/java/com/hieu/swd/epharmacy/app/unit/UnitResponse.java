package com.hieu.swd.epharmacy.app.unit;

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
public class UnitResponse implements Serializable {

    private String id;

    private String name;

    public UnitResponse(Unit unit) {
        this.id = unit.getId();
        this.name = unit.getName();
    }

    public static List<UnitResponse> convertToObjectList(List<Unit> unitList) {
        List<UnitResponse> unitResponseList = new ArrayList<>();
        for (Unit unit : unitList) {
            unitResponseList.add(new UnitResponse(unit));
        }
        return unitResponseList;
    }

}
