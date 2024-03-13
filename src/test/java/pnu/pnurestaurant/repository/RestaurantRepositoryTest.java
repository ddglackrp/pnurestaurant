package pnu.pnurestaurant.repository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.restaurant.Address;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Transactional

class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("식당 저장")
    void test1(){
        //Given
        Restaurant restaurant = Restaurant.builder()
                .name("교토밥상")
                .foodType(FoodType.JAPANESE)
                .address(Address.builder().locationId("123456").build())
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(3.0)
                .googleRating(4.3)
                .studentRating(0.0)
                .build();
        //When
        restaurantRepository.save(restaurant);

        //Then
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertThat(restaurants.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("식당 조회 - id 이용 조회")
    void test2(){
        //Given
        Restaurant restaurant = Restaurant.builder()
                .name("교토밥상")
                .foodType(FoodType.JAPANESE)
                .address(Address.builder().locationId("123456").build())
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(3.0)
                .googleRating(4.3)
                .studentRating(0.0)
                .build();
        //When
        restaurantRepository.save(restaurant);

        log.info("정상 조회");
        Restaurant findRestaurant1 = restaurantRepository.findById(restaurant.getId());

        log.info("비정상 조회 null 반환");
        Restaurant findRestaurant2 = restaurantRepository.findById(restaurant.getId()+1);

        //Then
        assertThat(findRestaurant1).isEqualTo(restaurant);
        assertThat(findRestaurant2).isNull();
    }

    @Test
    @DisplayName("식당 조회 - 검색어 포함하는 식당 조회")
    void test3(){
        //Given
        Restaurant restaurant1 = Restaurant.builder()
                .name("교토밥상")
                .foodType(FoodType.JAPANESE)
                .address(Address.builder().locationId("123456").build())
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(3.0)
                .googleRating(4.3)
                .studentRating(0.0)
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("집앞밥상")
                .foodType(FoodType.JAPANESE)
                .address(Address.builder().locationId("123456").build())
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(3.0)
                .googleRating(4.3)
                .studentRating(0.0)
                .build();

        //When
        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);

        log.info("DB에 저장된 데이터 중 '밥상' 포함된 데이터가 있음");
        List<Restaurant> case1 = restaurantRepository.findByNameLike("밥상");

        log.info("DB에 저장된 데이터 중 '안알랴줌' 포함된 데이터가 없음");
        List<Restaurant> case2 = restaurantRepository.findByNameLike("안알랴줌");

        //Then
        assertThat(case1.size()).isEqualTo(2);
        assertThat(case2.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("식당 조회 - 음식 종류로 식당 조회")
    void test4(){
        //Given
        Restaurant restaurant1 = Restaurant.builder()
                .name("교토밥상")
                .foodType(FoodType.JAPANESE)
                .address(Address.builder().locationId("123456").build())
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(3.0)
                .googleRating(4.3)
                .studentRating(0.0)
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("집앞밥상")
                .foodType(FoodType.KOREAN)
                .address(Address.builder().locationId("123456").build())
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(3.0)
                .googleRating(4.3)
                .studentRating(0.0)
                .build();

        //When
        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);

        log.info("DB에 저장된 데이터 중 일식인 데이터가 있음 - 1개");
        List<Restaurant> case1 = restaurantRepository.findByFoodType(FoodType.JAPANESE);

        log.info("DB에 저장된 데이터 중 한식인 데이터가 있음 - 1개");
        List<Restaurant> case2 = restaurantRepository.findByFoodType(FoodType.KOREAN);

        log.info("DB에 저장된 데이터 중 중식인 데이터가 없음");
        List<Restaurant> case3 = restaurantRepository.findByFoodType(FoodType.CHINESE);

        //Then
        assertThat(case1.size()).isEqualTo(1);
        assertThat(case2.size()).isEqualTo(1);
        assertThat(case3.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("식당 조회 - id로 조회, 연관관계 엔티티 한번에 조회")
    void test5(){
        //Given
        Restaurant restaurant = Restaurant.builder()
                .name("교토밥상")
                .foodType(FoodType.JAPANESE)
                .address(Address.builder().locationId("123456").build())
                .restaurantPictureUrl("일단 보류")
                .kakaoRating(3.0)
                .googleRating(4.3)
                .studentRating(0.0)
                .build();

        Member member = Member.builder()
                .memberId("user1")
                .password("1234")
                .email("abcd@pusan.ac.kr")
                .role("USER")
                .build();

        Review review = Review.builder()
                .rating(0.0)
                .content("테스트용")
                .reviewPictureUrl("아무 URL")
                .build();

        review.makeRelationMember(member);
        review.makeRelationRestaurant(restaurant);

        restaurantRepository.save(restaurant);
        em.persist(member);
        em.persist(review);
        em.flush();

        //When
        Restaurant findRestaurant = restaurantRepository.findByIdWithReviewAndMember(restaurant.getId());


        //Then
        assertThat(findRestaurant.getReviews().size()).isEqualTo(1);
        assertThat(findRestaurant.getReviews().get(0).getRating()).isEqualTo(0.0);
        assertThat(findRestaurant.getReviews().get(0).getContent()).isEqualTo("테스트용");
    }




}