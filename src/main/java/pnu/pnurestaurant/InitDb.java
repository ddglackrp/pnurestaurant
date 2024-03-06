package pnu.pnurestaurant;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.restaurant.Address;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final Init init;

    @PostConstruct
    public void init(){
        init.init1();
        init.init2();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class Init{
        private final EntityManager em;

        public void init1(){
            Restaurant restaurant = Restaurant.builder()
                    .name("교토밥상")
                    .foodType(FoodType.JAPANESE)
                    .address(new Address("1236512943"))
                    .restaurantPictureUrl("일단 보류")
                    .kakaoRating(3.0)
                    .googleRating(4.3)
                    .studentRating(0.0)
                    .build();

            em.persist(restaurant);

        }

        public void init2(){
            Restaurant restaurant = Restaurant.builder()
                    .name("덮밥장사장")
                    .foodType(FoodType.KOREAN)
                    .address(new Address("1992050396"))
                    .restaurantPictureUrl("일단 보류")
                    .kakaoRating(5.0)
                    .googleRating(4.0)
                    .studentRating(0.0)
                    .build();

            em.persist(restaurant);

        }
    }

}
