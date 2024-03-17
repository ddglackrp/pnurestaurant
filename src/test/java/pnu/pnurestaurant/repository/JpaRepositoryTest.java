package pnu.pnurestaurant.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.restaurant.Address;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@Slf4j
public class JpaRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EntityManager em;


    @Test
    @DisplayName("RestaurantRepository - findByIdWithAllRelation Test")
    void restaurantTest(){
        //given
        Member member = Member.builder()
                .memberId("Test")
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

        review.makeRelationMember(member);
        review.makeRelationRestaurant(restaurant);
        memberRepository.save(member);
        restaurantRepository.save(restaurant);
        reviewRepository.save(review);

        em.flush();
        em.clear();

        //when
        log.info("fetch join 으로 한번에 조회 - 추가 쿼리 나가면 안됨");
        Optional<Restaurant> restaurants = restaurantRepository.findByIdWithAllRelation(restaurant.getId());

        //Then
        Restaurant test = restaurants.get();
        assertThat(test.getReviews().size()).isEqualTo(1);
        assertThat(test.getReviews().get(0).getRating()).isEqualTo(4.3);
        assertThat(test.getReviews().get(0).getContent()).isEqualTo("맛꿀마");
        assertThat(test.getReviews().get(0).getMember().getMemberId()).isEqualTo("Test");
    }

    @Test
    @DisplayName("ReviewRepository - findByIdWithAllRelation Test")
    void reviewTest(){
        //given
        Member member = Member.builder()
                .memberId("Test")
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

        review.makeRelationMember(member);
        review.makeRelationRestaurant(restaurant);
        memberRepository.save(member);
        restaurantRepository.save(restaurant);
        reviewRepository.save(review);

        em.flush();
        em.clear();

        //when
        log.info("fetch join 으로 한번에 조회 - 추가 쿼리 나가면 안됨");
        Optional<Review> reviews = reviewRepository.findByIdWithAllRelation(review.getId());

        //Then
        Review test = reviews.get();
        assertThat(test.getRating()).isEqualTo(4.3);
        assertThat(test.getRestaurant().getId()).isEqualTo(restaurant.getId());
        assertThat(test.getMember().getMemberId()).isEqualTo("Test");
    }
}
