package com.utc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
/**
 * Project_name : UTC_Java
 *
 * @author : datmt
 * @version : 1.0
 * @since : 6.6.2024
 * Description :
 */
@Data
@AllArgsConstructor
public class NewListResponse {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPage;
    private List<NewInfoResponse> newsList;
}
