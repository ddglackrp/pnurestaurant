package pnu.pnurestaurant.domain.restaurant;

import jakarta.persistence.*;

@Entity
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

}
