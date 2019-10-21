package edu.mum.mercato.repository;


import edu.mum.mercato.Helper.OrderStatus;
import edu.mum.mercato.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findFirstByBuyer_Id(Long buyerId);
    Optional<Order> findFirstByBuyer_IdAndOrderStatus(Long buyerId, Enum status);
}
