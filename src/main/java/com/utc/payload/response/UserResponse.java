package com.utc.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@JsonInclude
@Data
@Builder
public class UserResponse {
    Long id;
}
