package pnu.pnurestaurant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.Dto.response.RestaurantResponseDto;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;
import pnu.pnurestaurant.repository.RestaurantRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> findRestaurants(){
        return restaurantRepository.findAll();
    }

    public RestaurantResponseDto findRestaurant(Long id){
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 식당(게시물)이 존재하지 않습니다."));

         return new RestaurantResponseDto(restaurant);
    }

    public List<RestaurantResponseDto> findRestaurantsByFoodType(FoodType foodType){
        return restaurantRepository.findByFoodType(foodType).stream()
                .map(RestaurantResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<RestaurantResponseDto> findRestaurantsByName(String name) {
        return restaurantRepository.findByNameLike(name).stream()
                .map(RestaurantResponseDto::new)
                .collect(Collectors.toList());
    }

    public RestaurantResponseDto findRestaurantWithRelation(Long id){
        Restaurant restaurant = restaurantRepository.findByIdWithAllRelation(id).orElseThrow(() -> new EntityNotFoundException("해당 식당(게시물)이 존재하지 않습니다."));

        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto(restaurant);
        restaurantResponseDto.makeReviews(restaurant.getReviews());
        return restaurantResponseDto;
    }

    @Transactional
    public void modifyRating(Long id, Double rating){
        Restaurant findRestaurant = restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 식당(게시물)이 존재하지 않습니다."));
        findRestaurant.changeStudentRating(rating);
    }
}
