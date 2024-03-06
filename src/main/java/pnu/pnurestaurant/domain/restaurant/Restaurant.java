package pnu.pnurestaurant.domain.restaurant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.TimeStamp;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Builder
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

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews = new ArrayList<>();


    private Double googleRating;
    private Double kakaoRating;
    private Double studentRating;
    private String restaurantPictureUrl;


    public Restaurant() {
    }

}
