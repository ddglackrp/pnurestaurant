package pnu.pnurestaurant.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RestaurantRepository {

    private final EntityManager em;

    @Transactional
    public Long save(Restaurant restaurant){
        em.persist(restaurant);
        return restaurant.getId();
    }

    public Restaurant findById(Long id){
        return em.find(Restaurant.class, id);
    }

    public List<Restaurant> findAll(){
        return em.createQuery("select r from Restaurant r", Restaurant.class)
                .getResultList();
    }

    public List<Restaurant> findByNameLike(String name){
        return em.createQuery("select r from Restaurant r where r.name like concat('%', :name, '%')", Restaurant.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Restaurant> findByFoodType(FoodType foodType){
        return em.createQuery("select r from Restaurant r where r.foodType = :foodType", Restaurant.class)
                .setParameter("foodType", foodType)
                .getResultList();
    }

    public Restaurant findByIdWithReviewAndMember(Long id){
        List<Restaurant> restaurants = em.createQuery("select r from Restaurant r" +
                        " left join fetch r.reviews rr" +
                        " left join fetch rr.member" +
                        " where r.id = :id", Restaurant.class)
                        .setParameter("id", id)
                        .getResultList();

        if(restaurants.isEmpty()){
            return null;
        }else{
            return restaurants.get(0);
        }
    }
}
