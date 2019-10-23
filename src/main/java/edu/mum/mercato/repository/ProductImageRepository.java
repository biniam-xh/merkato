package edu.mum.mercato.repository;

import edu.mum.mercato.domain.ProductImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, Long> {
}
