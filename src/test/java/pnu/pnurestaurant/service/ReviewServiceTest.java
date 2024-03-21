package pnu.pnurestaurant.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.restaurant.Address;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;
import pnu.pnurestaurant.Dto.request.ReviewRequestDto;
import pnu.pnurestaurant.repository.RestaurantRepository;
import pnu.pnurestaurant.repository.ReviewRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@Slf4j
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Test
    @DisplayName("리뷰 저장")
    void test1(){
        //given
        Restaurant restaurant = Restaurant.builder()
                .name("덮밥장사장")
                .foodType(FoodType.KOREAN)
                .address(new Address("1992050396"))
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(5.0)
                .googleRating(4.0)
                .studentRating(0.0)
                .build();

        Member member = Member.builder()
                .memberName("test")
                .password("1234")
                .email("test@pusan.ac.kr")
                .role("USER")
                .build();

        ReviewRequestDto reviewRequestDto = ReviewRequestDto
                .builder()
                .rating(2.5)
                .content("테스트용")
                .build();

        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));
        given(reviewRepository.save(any(Review.class))).willReturn(null);

        //when
        reviewService.saveReview(reviewRequestDto, member, restaurant.getId());

        //then
        then(restaurantRepository).should().findById(restaurant.getId());
        then(reviewRepository).should().save(any(Review.class));
    }


}