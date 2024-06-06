package com.utc.services;

import com.utc.payload.request.CreateNewsRequest;
import com.utc.payload.request.UpdateNewsRequest;
import com.utc.payload.response.GetAllNewsResponse;
import com.utc.payload.response.GetNewsResponse;
import com.utc.payload.response.RestApiResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

/**
 * Project_name : UTC_Java
 *
 * @author : datmt
 * @version : 1.0
 * @since : 6.6.2024
 * Description :
 */
public interface NewsService {
    ResponseEntity<GetAllNewsResponse> gets(PageRequest pageRequest);

    ResponseEntity<RestApiResponse> create(CreateNewsRequest createNewsRequest);

    ResponseEntity<RestApiResponse> update(Integer id, UpdateNewsRequest updateNewsRequest);

    ResponseEntity<RestApiResponse> delete(Integer id);

    ResponseEntity<GetNewsResponse> get(Integer id);
}
