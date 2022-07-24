package com.ead.notificationhex.core.ports;

import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.PageInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationServicePort {

    NotificationDomain save(NotificationDomain notificationDomain);

    List<NotificationDomain> findAllByUser(UUID userId, PageInfo pageInfo);

    Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
}
