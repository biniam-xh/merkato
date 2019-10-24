package edu.mum.mercato.repository;

import edu.mum.mercato.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findAllByProductId(Long id);
    List<Review> findAllByProductIdAndReviewStatus(Long id,Enum e);
}
