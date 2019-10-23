package edu.mum.mercato.repository;

import edu.mum.mercato.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByCategoryName(String categoryName);
}
