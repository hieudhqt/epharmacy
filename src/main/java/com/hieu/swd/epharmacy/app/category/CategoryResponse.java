package com.hieu.swd.epharmacy.app.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CategoryResponse implements Serializable {

    private String id;

    private String name;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public static List<CategoryResponse> convertToObjectList(List<Category> categoryList) {
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryResponseList.add(new CategoryResponse(category));
        }
        return categoryResponseList;
    }
}
