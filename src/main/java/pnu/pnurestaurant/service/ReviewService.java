package pnu.pnurestaurant.service;

import jakarta.persistence.EntityNotFoundException;
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
    public void saveReview(Review review){
        reviewRepository.save(review);
    }

    public Review findReview(Long id){
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));
    }

    public Review findReviewWithRelation(Long id){
        return reviewRepository.findByIdWithAllRelation(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));
    }

    @Transactional
    public void modifyReview(Long id, String content){
        Review findReview = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));
        findReview.changeContent(content);
    }

    @Transactional
    public void deleteReview(Long id){
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));
        reviewRepository.delete(review);
    }
}
