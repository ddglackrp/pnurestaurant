package pnu.pnurestaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.repository.ReviewRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Long saveReview(Review review){
        return reviewRepository.save(review);
    }
}
