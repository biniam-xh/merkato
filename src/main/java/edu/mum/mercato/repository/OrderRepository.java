package edu.mum.mercato.repository;


import edu.mum.mercato.Helper.OrderStatus;
import edu.mum.mercato.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findFirstByBuyer_Id(Long buyerId);
    Optional<Order> findFirstByBuyer_IdAndOrderStatus(Long buyerId, Enum status);

    List<Order> findAllByIdIsIn(List<Long> list);
    List<Order> findAllByBuyerIdAndOrderStatus(Long id,Enum status);
    List<Order> findAllByBuyerIdAndOrderStatusNotIn(Long id,List<Enum> status);

}
