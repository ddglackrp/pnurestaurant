package pnu.pnurestaurant.domain;

import jakarta.persistence.*;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Board extends TimeStamp{

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "board")
    private List<Restaurant> restaurants = new ArrayList<>();

    private Float googleRating;
    private Float kakaoRating;
    private Float studentRating;

}
