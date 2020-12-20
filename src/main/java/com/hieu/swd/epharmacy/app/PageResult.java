package com.hieu.swd.epharmacy.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageResult<T> implements Serializable {

    public static final int DEFAULT_FIRST_RESULT = 0;
    public static final int DEFAULT_PAGE_SIZE = 100;

    private int pageNumber;

    private int pageSize;

    private int totalElements;

    private int totalPages;

    @ApiModelProperty(notes = "List of response objects")
    private List<T> elements;

    public int getTotalElements() {
        return totalElements;
    }

    public PageResult(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public boolean hasMore() {
        return totalElements > calculateOffset() + pageSize;
    }

    public boolean hasPrevious() {
        return pageNumber > 1 && totalElements > 0;
    }

    public int getLastPageNumber(Long count) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        if (pageSize > count) {
            pageSize = Math.toIntExact(count);
        }
        return (int) (Math.ceil((double) count / pageSize));
    }

    public int calculateOffset() {
        if (pageNumber < 1 || pageSize < 1) {
            pageNumber = 1;
            pageSize = DEFAULT_PAGE_SIZE;
            return DEFAULT_FIRST_RESULT;
        }
        return (pageNumber - 1) * pageSize;
    }

}
