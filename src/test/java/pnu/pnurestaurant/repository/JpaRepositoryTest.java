package pnu.pnurestaurant.repository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.restaurant.Address;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

import java.util.List;
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
        assertThat(test.getReviews().get(0).getMember().getMemberName()).isEqualTo("Test");
    }

    @Test
    @DisplayName("ReviewRepository - findByIdWithAllRelation Test")
    void reviewTest(){
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
        assertThat(test.getMember().getMemberName()).isEqualTo("Test");
    }

    @Test
    @DisplayName("ReviewRepository - page Test")
    void pageTest(){
        Restaurant restaurant = Restaurant.builder()
                .name("덮밥장사장")
                .foodType(FoodType.KOREAN)
                .address(new Address("1992050396"))
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(5.0)
                .googleRating(4.0)
                .studentRating(0.0)
                .build();

        restaurantRepository.save(restaurant);

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
            memberRepository.save(member);
            reviewRepository.save(review);
        }

        em.flush();
        em.clear();

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Review> reviews = reviewRepository.findReviewsByRestaurantId(restaurant.getId(), pageRequest);

        List<Review> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(5);
        Member member = content.get(0).getMember();
        log.info("member = {}",member.getMemberName());
    }


}
