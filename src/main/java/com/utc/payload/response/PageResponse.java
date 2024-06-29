package com.utc.payload.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PageResponse<T> {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPage;
    private Long totalCount;

    private List<T> data;
}
