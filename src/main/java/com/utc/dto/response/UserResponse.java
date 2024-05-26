package com.utc.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@JsonInclude
@Data
@Builder
public class UserResponse {
    Long id;
}
