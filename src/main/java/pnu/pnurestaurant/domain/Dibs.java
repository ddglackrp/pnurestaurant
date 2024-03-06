package pnu.pnurestaurant.domain;

import jakarta.persistence.*;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

@Entity
public class Dibs {

    @Id
    @GeneratedValue
    private Long dibsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
