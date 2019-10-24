package edu.mum.mercato.service;

import edu.mum.mercato.domain.Review;

import java.util.List;

public interface ReviewService {
    Review save(Review review);
    Review changeStatus(Long id, Enum e);
    List<Review> getProductReviews(Long id, Enum e);
    List<Review> getProductReviews(Enum e);
    Review findById(long id);
    void deleteReview(long id);
}
