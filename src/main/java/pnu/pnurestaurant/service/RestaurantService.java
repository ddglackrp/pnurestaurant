package pnu.pnurestaurant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.dto.response.RestaurantWithReviewsDto;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.dto.response.RestaurantResponseDto;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;
import pnu.pnurestaurant.repository.RestaurantRepository;
import pnu.pnurestaurant.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    public List<Restaurant> findRestaurants(){
        return restaurantRepository.findAll();
    }

    public RestaurantResponseDto findRestaurant(Long id){
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 식당(게시물)이 존재하지 않습니다."));

         return new RestaurantResponseDto(restaurant);
    }

    public List<RestaurantResponseDto> findRestaurantsByFoodType(FoodType foodType){
        List<Restaurant> restaurants = restaurantRepository.findByFoodType(foodType);

        return restaurants.stream()
                    .map(RestaurantResponseDto::new)
                    .collect(Collectors.toList());
    }

    public List<RestaurantResponseDto> findRestaurantsByName(String name) {
        List<Restaurant> restaurants = restaurantRepository.findByNameLike(name);

        return restaurants.stream()
                .map(RestaurantResponseDto::new)
                .collect(Collectors.toList());
    }

    public RestaurantResponseDto findRestaurantWithRelation(Long id){
        Restaurant restaurant = restaurantRepository.findByIdWithAllRelation(id).orElseThrow(() -> new EntityNotFoundException("해당 식당(게시물)이 존재하지 않습니다."));

        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto(restaurant);
        restaurantResponseDto.makeReviews(restaurant.getReviews());
        return restaurantResponseDto;
    }

    public RestaurantWithReviewsDto getRestaurantWithReviews(Long id, Pageable pageable){
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 식당(게시물)이 존재하지 않습니다."));
        Page<Review> reviews = reviewRepository.findReviewsByRestaurantId(id, pageable);
        return new RestaurantWithReviewsDto(restaurant, reviews);
    }

    @Transactional
    public void modifyRating(Long id){
        Restaurant findRestaurant = restaurantRepository.findByIdWithAllRelation(id).orElseThrow(() -> new EntityNotFoundException("해당 식당(게시물)이 존재하지 않습니다."));

        List<Review> reviews = findRestaurant.getReviews();
        double sum = reviews.stream().mapToDouble(Review::getRating).sum();
        double newRating = sum/reviews.size();

        findRestaurant.changeStudentRating(newRating);
    }
}
