package com.ogoma.hr_core.boundaries.notifications.services;

import com.ogoma.hr_core.boundaries.notifications.entities.Notification;
import com.ogoma.hr_core.boundaries.notifications.repositories.NotificationsRepository;
import com.ogoma.hr_core.models.requests.PagedDataRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;

    public NotificationsService(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    public Page<Notification> findNotificationByUserId(Long userId, PagedDataRequest pagedDataRequest) {
        PageRequest pageRequest = PageRequest.of(pagedDataRequest.getPage(), pagedDataRequest.getPageSize());
        Page<Notification> notifications = this.notificationsRepository.findAllByUserId(userId, pageRequest);
        return notifications;
    }

    public void createNotification(Notification notification) {
        this.notificationsRepository.save(notification);
    }
}
