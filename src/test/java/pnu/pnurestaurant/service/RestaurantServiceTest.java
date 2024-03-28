package pnu.pnurestaurant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pnu.pnurestaurant.dto.response.RestaurantResponseDto;
import pnu.pnurestaurant.dto.response.RestaurantWithReviewsDto;
import pnu.pnurestaurant.dto.response.ReviewResponseDto;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.restaurant.Address;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;
import pnu.pnurestaurant.repository.RestaurantRepository;
import pnu.pnurestaurant.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@Slf4j
class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("식당 조회 - 단건")
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

        //when
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));

        given(restaurantRepository.findById(2L)).willReturn(Optional.ofNullable(null));

        //then
        /**
         * 정상 조회
         */
        RestaurantResponseDto findDto = restaurantService.findRestaurant(1L);
        assertThat(findDto.getFoodType()).isEqualTo(restaurant.getFoodType());
        assertThat(findDto.getLocationId()).isEqualTo(restaurant.getAddress().getLocationId());
        assertThat(findDto.getKakaoRating()).isEqualTo(restaurant.getKakaoRating());

        /**
         * 비정상 조회
         */
        assertThatThrownBy(() -> restaurantService.findRestaurant(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("해당 식당(게시물)이 존재하지 않습니다.");

    }

    @Test
    @DisplayName("식당 조회 - 음식 종류")
    void test2(){
        //given
        Restaurant r1 = Restaurant.builder()
                .name("한식1")
                .foodType(FoodType.KOREAN)
                .address(new Address("1992050396"))
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(5.0)
                .googleRating(4.0)
                .studentRating(0.0)
                .build();

        Restaurant r2 = Restaurant.builder()
                .name("한식2")
                .foodType(FoodType.KOREAN)
                .address(new Address("1992050396"))
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(5.0)
                .googleRating(4.0)
                .studentRating(0.0)
                .build();

        Restaurant r3 = Restaurant.builder()
                .name("일식1")
                .foodType(FoodType.JAPANESE)
                .address(new Address("1992050396"))
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(5.0)
                .googleRating(4.0)
                .studentRating(0.0)
                .build();

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(r1);
        restaurants.add(r2);

        given(restaurantRepository.findByFoodType(FoodType.KOREAN)).willReturn(restaurants);

        given(restaurantRepository.findByFoodType(FoodType.CHINESE)).willReturn(new ArrayList<>());

        //when
        //정상 테스트
        List<RestaurantResponseDto> test1 = restaurantService.findRestaurantsByFoodType(FoodType.KOREAN);

        //비정상 테스트
        List<RestaurantResponseDto> test2 = restaurantService.findRestaurantsByFoodType(FoodType.CHINESE);

        //then
        assertThat(test1.size()).isEqualTo(2);
        assertThat(test2).isEmpty();


    }


    @Test
    @DisplayName("식당 조회 - 음식점 이름")
    void test3(){
        //given
        Restaurant r1 = Restaurant.builder()
                .name("한식1")
                .foodType(FoodType.KOREAN)
                .address(new Address("1992050396"))
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(5.0)
                .googleRating(4.0)
                .studentRating(0.0)
                .build();

        Restaurant r2 = Restaurant.builder()
                .name("한식2")
                .foodType(FoodType.KOREAN)
                .address(new Address("1992050396"))
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(5.0)
                .googleRating(4.0)
                .studentRating(0.0)
                .build();

        Restaurant r3 = Restaurant.builder()
                .name("일식1")
                .foodType(FoodType.JAPANESE)
                .address(new Address("1992050396"))
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(5.0)
                .googleRating(4.0)
                .studentRating(0.0)
                .build();

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(r1);
        restaurants.add(r2);

        given(restaurantRepository.findByNameLike("한식")).willReturn(restaurants);

        given(restaurantRepository.findByNameLike("중식")).willReturn(new ArrayList<>());

        //when
        //정상 테스트
        List<RestaurantResponseDto> test1 = restaurantService.findRestaurantsByName("한식");

        //비정상 테스트
        List<RestaurantResponseDto> test2 = restaurantService.findRestaurantsByName("중식");

        //then
        assertThat(test1.size()).isEqualTo(2);
        assertThat(test2).isEmpty();
    }

    @Test
    @DisplayName("식당 조회 - 연관관계 전부 조회 : 리뷰 있을때")
    void test4(){
        //given
        Member member = Member.builder()
                .memberName("Test")
                .password("1234")
                .email("test@pusan.ac.kr")
                .role("USER")
                .build();

        Review review = Review.builder()
                .rating(4.3)
                .content("맛꿀마")
                .reviewPictureUrl("일단 보류")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("덮밥장사장")
                .foodType(FoodType.KOREAN)
                .address(new Address("1992050396"))
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(5.0)
                .googleRating(4.0)
                .studentRating(0.0)
                .build();

        review.makeRelationRestaurant(restaurant);
        review.makeRelationMember(member);

        //when
        given(restaurantRepository.findByIdWithAllRelation(1L)).willReturn(Optional.of(restaurant));
        given(restaurantRepository.findByIdWithAllRelation(2L)).willReturn(Optional.ofNullable(null));

        //then
        //정상조회
        RestaurantResponseDto testDto = restaurantService.findRestaurantWithRelation(1L);

        assertThat(testDto.getName()).isEqualTo("덮밥장사장");
        assertThat(testDto.getReviews().size()).isEqualTo(1);
        assertThat(testDto.getReviews().get(0).getContent()).isEqualTo("맛꿀마");

        //비정상 조회
        assertThatThrownBy(() -> restaurantService.findRestaurantWithRelation(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("해당 식당(게시물)이 존재하지 않습니다.");

    }

    @Test
    @DisplayName("식당 조회 - 연관관계 전부 조회 : 리뷰 없을때")
    void test5(){
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

        //when
        given(restaurantRepository.findByIdWithAllRelation(1L)).willReturn(Optional.of(restaurant));

        //then
        RestaurantResponseDto testDto = restaurantService.findRestaurantWithRelation(1L);

        assertThat(testDto.getName()).isEqualTo("덮밥장사장");
        assertThat(testDto.getReviews()).isEmpty();
    }

    @Test
    @DisplayName("식당 별점 변경")
    void test6(){
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

        Review review = Review.builder()
                .rating(2.0)
                .content("haha")
                .build();

        review.makeRelationRestaurant(restaurant);

        given(restaurantRepository.findByIdWithAllRelation(1L)).willReturn(Optional.of(restaurant));
        given(restaurantRepository.findByIdWithAllRelation(2L)).willReturn(Optional.ofNullable(null));

        //when
        restaurantService.modifyRating(1L);

        //then
        assertThat(restaurant.getStudentRating()).isEqualTo(2.0);

        assertThatThrownBy(() -> restaurantService.modifyRating(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("해당 식당(게시물)이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("식당 조회 review page 기능")
    void test7(){
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

        List<Review> reviews = new ArrayList<>();

        for(int i=0; i<20; i++){
            Review review = Review.builder()
                    .rating(4.3)
                    .content("맛꿀마 " + i)
                    .reviewPictureUrl("일단 보류")
                    .build();

            Member member = Member.builder()
                    .memberName("Test " + i)
                    .password("1234")
                    .email("test@pusan.ac.kr")
                    .role("USER")
                    .build();

            review.makeRelationMember(member);
            review.makeRelationRestaurant(restaurant);
            reviews.add(review);
        }

        PageRequest pageRequest = PageRequest.of(0, 5,
                Sort.by(Sort.Direction.DESC,"createdAt"));

        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));

        given(reviewRepository.findReviewsByRestaurantId(1L, pageRequest))
                .willReturn(new PageImpl<>(reviews.subList(0, 5), pageRequest, reviews.size()));

        //when
        RestaurantWithReviewsDto restaurantWithReviews = restaurantService.getRestaurantWithReviews(1L, pageRequest);

        //then
        String name = restaurantWithReviews.getName();
        Page<ReviewResponseDto> reviewpage = restaurantWithReviews.getReviews();
        List<ReviewResponseDto> content = reviewpage.getContent();

        assertThat(name).isEqualTo("덮밥장사장");
        assertThat(reviewpage.getTotalElements()).isEqualTo(20);
        assertThat(reviewpage.getTotalPages()).isEqualTo(4);
        assertThat(reviewpage.isFirst()).isTrue();
        assertThat(reviewpage.hasNext()).isTrue();
        assertThat(content.size()).isEqualTo(5);
        assertThat(content.get(4).getContent()).isEqualTo("맛꿀마 4");
    }

}