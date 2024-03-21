package pnu.pnurestaurant.Dto.response;

import lombok.Data;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private FoodType foodType;
    private String locationId;
    private Double googleRating;
    private Double kakaoRating;
    private Double studentRating;
    private List<ReviewResponseDto> reviews;


    public RestaurantResponseDto(Restaurant restaurant){
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.foodType = restaurant.getFoodType();
        this.locationId = restaurant.getAddress().getLocationId();
        this.googleRating = restaurant.getGoogleRating();
        this.kakaoRating = restaurant.getKakaoRating();
        this.studentRating = restaurant.getStudentRating();
    }

    public void makeReviews(List<Review> reviews){
        this.reviews = reviews.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }
}
