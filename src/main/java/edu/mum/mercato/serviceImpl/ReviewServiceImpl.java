package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.Helper.ReviewStatus;
import edu.mum.mercato.domain.Review;
import edu.mum.mercato.repository.ReviewRepository;
import edu.mum.mercato.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review changeStatus(Long id, Enum e) {
        Review review = reviewRepository.findById(id).get();
        review.setReviewStatus(e);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getProductReviews(Long id, Enum e) {
        return reviewRepository.findAllByProductIdAndReviewStatus(id, e);
    }

    @Override
    public List<Review> getProductReviews(Enum e) {
        return reviewRepository.findAllByReviewStatus(e);
    }

    @Override
    public Review findById(long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteReview(long id) {

        reviewRepository.deleteById(id);
    }


}
