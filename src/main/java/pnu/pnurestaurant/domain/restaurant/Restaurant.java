package pnu.pnurestaurant.domain.restaurant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.TimeStamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@AllArgsConstructor(access = PRIVATE)
public class Restaurant extends TimeStamp {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    @Embedded
    private Address address;

    private Double googleRating;

    private Double kakaoRating;

    private Double studentRating;

    private String restaurantPictureUrl;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews = new ArrayList<>();

    protected Restaurant() {
    }

    @Builder
    public Restaurant(String name, FoodType foodType, Address address, Double googleRating, Double kakaoRating, Double studentRating, String restaurantPictureUrl) {
        this.name = name;
        this.foodType = foodType;
        this.address = address;
        this.googleRating = googleRating;
        this.kakaoRating = kakaoRating;
        this.studentRating = studentRating;
        this.restaurantPictureUrl = restaurantPictureUrl;
    }

    public void changeStudentRating(Double rating){
        this.studentRating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && foodType == that.foodType && Objects.equals(address, that.address) && Objects.equals(googleRating, that.googleRating) && Objects.equals(kakaoRating, that.kakaoRating) && Objects.equals(studentRating, that.studentRating) && Objects.equals(restaurantPictureUrl, that.restaurantPictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, foodType, address, googleRating, kakaoRating, studentRating, restaurantPictureUrl);
    }
}
