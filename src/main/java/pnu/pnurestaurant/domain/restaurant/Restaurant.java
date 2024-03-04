package pnu.pnurestaurant.domain.restaurant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    @Embedded
    private Address address;

    private String restaurantPictureUrl;

    public Restaurant() {
    }

}
