package com.utc.services.impl;

import com.utc.contants.ApiStatus;
import com.utc.contants.EStatus;
import com.utc.exception.ResourceNotExistsException;
import com.utc.exception.ValidateException;
import com.utc.models.New;
import com.utc.models.User;
import com.utc.payload.request.CreateNewRequest;
import com.utc.payload.request.UpdateNewRequest;
import com.utc.payload.response.*;
import com.utc.repository.NewRepository;
import com.utc.repository.UserRepository;
import com.utc.services.NewService;
import com.utc.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
public class NewServiceImpl implements NewService {

    private static final Logger log = LoggerFactory.getLogger(NewServiceImpl.class);
    @Autowired
    private final MessageSource messageSource;

    @Autowired
    private final NewRepository newRepository;

    @Autowired
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<GetAllNewsResponse> getAllNews(PageRequest pageRequest) {
        Page<New> result = newRepository.findAll(pageRequest);

        List<NewInfoResponse> newsList = result.getContent()
                .stream()
                .map(aNew -> new NewInfoResponse(
                        aNew.id(),
                        aNew.title(),
                        aNew.content(),
                        aNew.status())
                )
                .collect(Collectors.toList());

        NewListResponse newListResponse = new NewListResponse(
                result.getNumber() + 1,
                result.getSize(),
                result.getTotalPages(),
                newsList
        );
        return ResponseEntity.ok(
                new GetAllNewsResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase(),
                        newListResponse
                )
        );
    }

//    @Override
//    public ResponseEntity<GetAllNewsResponse> getNewsByUserId(PageRequest pageRequest) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
//
//        Page<New> result = newRepository.findAllByUserId(user.getId(), pageRequest);
//
//        List<NewInfoResponse> newsList = result.getContent()
//                .stream()
//                .map(aNew -> new NewInfoResponse(
//                        aNew.id(),
//                        aNew.title(),
//                        aNew.content(),
//                        aNew.status())
//                )
//                .collect(Collectors.toList());
//
//        NewListResponse newListResponse = new NewListResponse(
//                result.getNumber() + 1,
//                result.getSize(),
//                result.getTotalPages(),
//                newsList
//        );
//        return ResponseEntity.ok(
//                new GetAllNewsResponse(
//                        ApiStatus.SUCCESS.code,
//                        ApiStatus.SUCCESS.toString().toLowerCase(),
//                        newListResponse
//                )
//        );
//    }

    @Override
    public ResponseEntity<RestApiResponse> create(CreateNewRequest createNewRequest) {
        newRepository.findByTitle(createNewRequest.getTitle())
                .ifPresent(aNew -> {
                    throw new ValidateException(
                            ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                            MessageUtils.getProperty(messageSource, "new_already_exist")
                    );
                });

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));

        New aNew = new New()
                .userId(user.getId())
                .title(createNewRequest.getTitle())
                .content(createNewRequest.getContent())
                .status(EStatus.ACTIVE.code)
                .modifiedBy(userDetails.getUsername());

        newRepository.save(aNew);

        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

    @Override
    public ResponseEntity<RestApiResponse> update(Long newId, UpdateNewRequest updateNewRequest) {
        New newCurrent = newRepository.findById(newId)
                .orElseThrow(() -> new ResourceNotExistsException(
                        String.format(MessageUtils.getProperty(messageSource, "new_not_found"), newId)
                ));

        if (StringUtils.isNotBlank(updateNewRequest.getTitle())) {
            newCurrent.title(updateNewRequest.getTitle());
        }
        if (StringUtils.isNotBlank(updateNewRequest.getContent())) {
            newCurrent.content(updateNewRequest.getContent());
        }
        if (updateNewRequest.getStatus() != null) {
            newCurrent.status(updateNewRequest.getStatus());
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        newCurrent.modifiedBy(username);

        newRepository.save(newCurrent);

        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

    @Override
    public ResponseEntity<GetNewResponse> getNewById(Long id) {
        NewInfoResponse newInfoResponse = newRepository.findById(id)
                .map(aNew -> new NewInfoResponse(
                        aNew.id(),
                        aNew.title(),
                        aNew.content(),
                        aNew.status())
                )
                .orElseThrow(() -> new ResourceNotExistsException(
                        String.format(MessageUtils.getProperty(messageSource, "new_not_found"), id)
                ));

        return ResponseEntity.ok(
                new GetNewResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase(),
                        newInfoResponse
                )
        );
    }
}
