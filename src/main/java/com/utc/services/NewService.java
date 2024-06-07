package com.utc.services;

import com.utc.payload.request.CreateNewRequest;
import com.utc.payload.request.UpdateNewRequest;
import com.utc.payload.response.GetAllNewsResponse;
import com.utc.payload.response.GetNewResponse;
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
public interface NewService {
    ResponseEntity<GetAllNewsResponse> getAllNews(PageRequest pageRequest);

    ResponseEntity<RestApiResponse> create(CreateNewRequest createNewRequest);

    ResponseEntity<RestApiResponse> update(Long id, UpdateNewRequest updateNewRequest);

    ResponseEntity<GetNewResponse> getNewById(Long id);
}
