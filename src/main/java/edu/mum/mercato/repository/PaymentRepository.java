package edu.mum.mercato.repository;

import edu.mum.mercato.domain.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment,Long> {
}
