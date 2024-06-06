package com.utc.services.impl;

import com.utc.contants.ApiStatus;
import com.utc.contants.ERole;
import com.utc.contants.EStatus;
import com.utc.exception.ResourceNotExistsException;
import com.utc.exception.ValidateException;
import com.utc.models.News;
import com.utc.models.User;
import com.utc.payload.request.CreateNewsRequest;
import com.utc.payload.request.UpdateNewsRequest;
import com.utc.payload.response.*;
import com.utc.repository.NewsRepository;
import com.utc.repository.UserRepository;
import com.utc.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Project_name : UTC_Java
 *
 * @author : datmt
 * @version : 1.0
 * @since : 6.6.2024
 * Description :
 */
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<GetAllNewsResponse> gets(PageRequest pageRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));

        Page<News> result = newsRepository.findAllByUser_Id(user.getId(), pageRequest);

        List<NewsInfoResponse> newsList = result.getContent()
                .stream()
                .map(news -> new NewsInfoResponse(news.getId(), news.getTitle(), news.getContent(), news.getStatus()))
                .collect(Collectors.toList());

        NewsListResponse newsListResponse = new NewsListResponse(
                result.getNumber() + 1,
                result.getSize(),
                result.getTotalPages(),
                newsList
        );
        return ResponseEntity.ok(
                new GetAllNewsResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase(),
                        newsListResponse
                )
        );
    }

    @Override
    public ResponseEntity<RestApiResponse> create(CreateNewsRequest createNewsRequest) {
        newsRepository.findByTitle(createNewsRequest.getTitle())
                .ifPresent(news -> {throw new ValidateException("400", "News already exist with title: " + news.getTitle());});

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));

        News news = new News().setTitle(createNewsRequest.getTitle())
                .setContent(createNewsRequest.getContent())
                .setStatus(EStatus.ACTIVE.code)
                .setModifiedBy(ERole.ROLE_USER.name())
                .setUser(user);

        newsRepository.save(news);

        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

    @Override
    public ResponseEntity<RestApiResponse> update(Integer id, UpdateNewsRequest updateNewsRequest) {
        News newsCurrent = newsRepository.findByIdAndStatus(id, EStatus.ACTIVE.code)
                .orElseThrow(() -> new ResourceNotExistsException(String.format("News with id: %s not exist !", id)));

        newsCurrent.setTitle(updateNewsRequest.getTitle()).setContent(updateNewsRequest.getContent());

        newsRepository.save(newsCurrent);

        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

    @Override
    public ResponseEntity<RestApiResponse> delete(Integer id) {
        News newsCurrent = newsRepository.findByIdAndStatus(id, EStatus.ACTIVE.code)
                .orElseThrow(() -> new ResourceNotExistsException(String.format("News with id: %s not exist !", id)));

        newsRepository.save(newsCurrent.setStatus(EStatus.DELETED.code));

        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

    @Override
    public ResponseEntity<GetNewsResponse> get(Integer id) {
        NewsInfoResponse newsInfoResponse = newsRepository.findByIdAndStatus(id, EStatus.ACTIVE.code)
                .map(news -> new NewsInfoResponse(news.getId(), news.getTitle(), news.getContent(), news.getStatus()))
                .orElseThrow(() -> new ResourceNotExistsException(String.format("News with id: %s not exist !", id)));

        return ResponseEntity.ok(
                new GetNewsResponse().setStatus(ApiStatus.SUCCESS.code)
                        .setMessage(ApiStatus.SUCCESS.toString().toLowerCase())
                        .setResult(newsInfoResponse)
        );
    }
}
