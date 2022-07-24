package com.ead.notificationhex.adapters.outbound.persistence;

import com.ead.notificationhex.adapters.outbound.persistence.NotificationJpaRepository;
import com.ead.notificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.PageInfo;
import com.ead.notificationhex.core.domain.enums.NotificationStatus;
import com.ead.notificationhex.core.ports.NotificationPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class NotificationPersistencePortImpl implements NotificationPersistencePort {

    private final NotificationJpaRepository notificationJpaRepository;

    private final ModelMapper modelMapper;

    public NotificationPersistencePortImpl(NotificationJpaRepository notificationJpaRepository, ModelMapper modelMapper) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public NotificationDomain save(NotificationDomain notificationDomain) {
        NotificationEntity notificationEntitySaved = notificationJpaRepository.save(modelMapper.map(notificationDomain, NotificationEntity.class));
        return modelMapper.map(notificationEntitySaved, NotificationDomain.class);
    }

    @Override
    public List<NotificationDomain> findAllByUserIdAndNotificationsStatus(UUID userId, NotificationStatus notificationStatus, PageInfo pageInfo) {
        PageRequest pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize());
        return notificationJpaRepository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageable)
                .stream().map(entity -> modelMapper.map(entity, NotificationDomain.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        Optional<NotificationEntity> notificationEntity = notificationJpaRepository.findByNotificationIdAndUserId(notificationId, userId);
        if (notificationEntity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(modelMapper.map(notificationEntity.get(), NotificationDomain.class));
    }
}
