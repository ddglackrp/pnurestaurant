package pnu.pnurestaurant.Dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

import java.util.List;

@Data
public class RestaurantWithReviewsDto {
    private Long id;
    private String name;
    private FoodType foodType;
    private String locationId;
    private Double googleRating;
    private Double kakaoRating;
    private Double studentRating;
    private Page<ReviewResponseDto> reviews;

    public RestaurantWithReviewsDto(Restaurant restaurant, Page<Review> reviews){
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.foodType = restaurant.getFoodType();
        this.locationId = restaurant.getAddress().getLocationId();
        this.googleRating = restaurant.getGoogleRating();
        this.kakaoRating = restaurant.getKakaoRating();
        this.studentRating = restaurant.getStudentRating();

        this.reviews = reviews.map(ReviewResponseDto::new);

    }
}
