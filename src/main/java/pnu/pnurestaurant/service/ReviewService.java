package pnu.pnurestaurant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.restaurant.Restaurant;
import pnu.pnurestaurant.dto.request.ReviewRequestDto;
import pnu.pnurestaurant.dto.response.ReviewResponseDto;
import pnu.pnurestaurant.repository.RestaurantRepository;
import pnu.pnurestaurant.repository.ReviewRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void saveReview(ReviewRequestDto reviewRequestDto, Member member, Long restaurantId){

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new EntityNotFoundException("해당 식당(게시물)을 찾을 수 없습니다."));

        Review review = Review.builder()
                .rating(reviewRequestDto.getRating())
                .content(reviewRequestDto.getContent())
                .reviewPictureUrl("일단 보류")
                .build();

        review.makeRelationMember(member);
        review.makeRelationRestaurant(restaurant);

        reviewRepository.save(review);
    }

    public Review findReview(Long id){
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));
    }

    public ReviewResponseDto findReviewWithRelation(Long id){
        Review review = reviewRepository.findByIdWithAllRelation(id).orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));
        return new ReviewResponseDto(review);
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
