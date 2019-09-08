package org.mingle.pear.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author mingle
 */
@Data
@Builder
@AllArgsConstructor
public class Page<T> {
    private final int pageSize;
    private final int pageNumber;
    private int totalCount;
    private List<T> items;

    public Page(int pageSize, int pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }
}
