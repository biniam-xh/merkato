package edu.mum.mercato.repository;

import edu.mum.mercato.domain.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification,Long> {
    //List<Notification> findAllByUsersContainingI_IdOrUsers(Long id);
}
