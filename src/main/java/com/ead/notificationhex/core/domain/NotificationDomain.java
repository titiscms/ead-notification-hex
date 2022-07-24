package com.ead.notificationhex.core.domain;

import com.ead.notificationhex.core.domain.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDomain {

    private UUID notificationId;
    private UUID userId;
    private String title;
    private String message;
    private LocalDateTime creationDate;
    private NotificationStatus notificationStatus;
}
