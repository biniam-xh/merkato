package edu.mum.mercato.repository;

import edu.mum.mercato.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
