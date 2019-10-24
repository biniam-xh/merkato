package edu.mum.mercato.repository;

import edu.mum.mercato.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

//    @Query(value = "SELECT * FROM Product WHERE title =?")
    Product findByTitleAndCategory_CategoryName(String title, String categoryName);


    List<Product>  findAllBySellerId(long id);
}
