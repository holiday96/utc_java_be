package com.utc.controllers;


import com.utc.payload.request.CreateNewsRequest;
import com.utc.payload.request.UpdateNewsRequest;
import com.utc.payload.response.GetAllNewsResponse;
import com.utc.payload.response.GetNewsResponse;
import com.utc.payload.response.RestApiResponse;
import com.utc.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
public class NewsController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<RestApiResponse> create(@Valid @RequestBody CreateNewsRequest createNewsRequest) {
        log.info("Request addUser: {}", createNewsRequest);
        return newsService.create(createNewsRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestApiResponse> update(@PathVariable Integer id,
                                                  @Valid @RequestBody UpdateNewsRequest updateNewsRequest) {
        log.info("Request addUser: {}", updateNewsRequest);
        return newsService.update(id, updateNewsRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestApiResponse> delete(@PathVariable Integer id) {
        log.info("Request addUser: {}", id);
        return newsService.delete(id);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<GetNewsResponse> get(@PathVariable Integer id) {
        log.info("Request get detail news : {}", id);
        return newsService.get(id);
    }
    @GetMapping
    public ResponseEntity<GetAllNewsResponse> getNewsList(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        log.info("Request getUserList: page={}, size={}", page, size);
        return newsService.gets(PageRequest.of(page - 1, size));
    }
}
