package pnu.pnurestaurant.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantRepository {

    private final EntityManager em;

    @Transactional
    public Long save(Restaurant restaurant){
        em.persist(restaurant);
        return restaurant.getId();
    }

    public Restaurant findOne(Long id){
        return em.find(Restaurant.class, id);
    }

    public List<Restaurant> findAll(){
        return em.createQuery("select r from Restaurant r", Restaurant.class)
                .getResultList();
    }

}
