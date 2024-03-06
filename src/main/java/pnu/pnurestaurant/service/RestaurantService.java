package pnu.pnurestaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;
import pnu.pnurestaurant.repository.RestaurantRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> findAllRestaurants(){
        return restaurantRepository.findAll();
    }

    public List<Restaurant> findRestaurantsByFoodType(FoodType foodType){
        return restaurantRepository.findByFoodType(foodType);
    }

    public List<Restaurant> findRestaurantsByName(String name){
        return restaurantRepository.findByName(name);
    }



}
