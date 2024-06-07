package com.utc.controllers;


import com.utc.payload.request.CreateNewRequest;
import com.utc.payload.request.UpdateNewRequest;
import com.utc.payload.response.GetAllNewsResponse;
import com.utc.payload.response.GetNewResponse;
import com.utc.payload.response.RestApiResponse;
import com.utc.services.NewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * Project_name : UTC_Java
 *
 * @author : datmt
 * @version : 1.0
 * @since : 6.6.2024
 * Description :
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Validated
public class NewController {

    private static final Logger log = LoggerFactory.getLogger(NewController.class);

    @Autowired
    private final NewService newService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestApiResponse> createNew(@Valid @RequestBody CreateNewRequest createNewRequest) {
        log.info("Request createNew: {}", createNewRequest);
        return newService.create(createNewRequest);
    }

    @PatchMapping("/{new_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestApiResponse> updateNew(
            @PathVariable("new_id") Long id,
            @Valid @RequestBody UpdateNewRequest updateNewRequest
    ) {
        log.info("Request updateNew: {}", updateNewRequest);
        return newService.update(id, updateNewRequest);
    }

    @GetMapping
    public ResponseEntity<GetAllNewsResponse> getNewList(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        log.info("Request getNewList: page={}, size={}", page, size);
        return newService.getAllNews(PageRequest.of(page - 1, size));
    }

    @GetMapping("/{new_id}")
    public ResponseEntity<GetNewResponse> getNewById(@PathVariable("new_id") @Positive Long id) {
        log.info("Request getNew: {}", id);
        return newService.getNewById(id);
    }
}
