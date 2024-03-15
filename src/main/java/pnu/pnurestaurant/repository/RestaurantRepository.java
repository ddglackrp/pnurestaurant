package pnu.pnurestaurant.repository;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {



    List<Restaurant> findByNameLike(String name);


    List<Restaurant> findByFoodType(FoodType foodType);


    @Query("select r from Restaurant r" +
            " left join fetch r.reviews rr" +
            " left join fetch rr.member" +
            " where r.id = :id")
    Optional<Restaurant> findByIdWithAllRelation(@Param("id") Long id);
}
