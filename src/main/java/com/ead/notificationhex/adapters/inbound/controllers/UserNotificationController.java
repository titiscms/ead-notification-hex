package com.ead.notificationhex.adapters.inbound.controllers;

import com.ead.notificationhex.adapters.configs.security.UserDetailsImpl;
import com.ead.notificationhex.adapters.dtos.NotificationDto;
import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.PageInfo;
import com.ead.notificationhex.core.ports.NotificationServicePort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserNotificationController {

    @Autowired
    private NotificationServicePort notificationServicePort;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(path = "/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationDomain>> getAllNotificationsByUser(@PathVariable UUID userId,
                                                                              @PageableDefault(page = 0, size = 10, sort = "notificationId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                              Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.info("Authentication userId {}", userDetails.getUserId());
        PageInfo pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize());
        List<NotificationDomain> notificationDomainList = notificationServicePort.findAllByUser(userId, pageInfo);
        PageImpl<NotificationDomain> notificationDomainPage = new PageImpl<>(notificationDomainList, pageable, notificationDomainList.size());
        return ResponseEntity.status(HttpStatus.OK).body(notificationDomainPage);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping(path = "/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable UUID userId, @PathVariable UUID notificationId, @RequestBody @Valid NotificationDto notificationDto) {
        Optional<NotificationDomain> notificationDomainOptional = notificationServicePort.findByNotificationIdAndUserId(notificationId, userId);
        if (notificationDomainOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found!");
        }
        notificationDomainOptional.get().setNotificationStatus(notificationDto.getNotificationStatus());
        NotificationDomain notificationDomainSaved = notificationServicePort.save(notificationDomainOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(notificationDomainSaved);
    }
}
