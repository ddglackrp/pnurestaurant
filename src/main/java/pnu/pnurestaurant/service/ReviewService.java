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

    public Review findOne(Long id){
        return reviewRepository.findById(id);
    }

    public Review findOneFetchAll(Long id){
        return reviewRepository.findByIdtoOne(id);
    }

    @Transactional
    public void updateReview(Long id, String content){
        Review findReview = reviewRepository.findById(id);
        findReview.changeContent(content);
    }

    @Transactional
    public void deleteReview(Long id){
        reviewRepository.deleteById(id);
    }
}
