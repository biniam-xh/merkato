package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.repository.ReviewRepository;
import edu.mum.mercato.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
}
