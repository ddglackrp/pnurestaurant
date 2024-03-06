package pnu.pnurestaurant.Dto;

import lombok.Builder;
import lombok.Data;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

@Data
public class RestaurantDto {
    private String name;

    private FoodType foodType;

    private String locationId;
    private Double googleRating;
    private Double kakaoRating;
    private Double studentRating;

    public RestaurantDto() {
    }

//    public RestaurantDto(Restaurant restaurant){
//        this.name = restaurant.getName();
//        this.foodType = restaurant.getFoodType();
//        this.locationId = restaurant.getAddress().
//    }
}
