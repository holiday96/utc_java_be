package com.utc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestApiResponse {
    private Integer status;
    private String message;
}
