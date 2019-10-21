package edu.mum.mercato.repository;

import edu.mum.mercato.domain.ProductItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends CrudRepository<ProductItem, Long> {
}
