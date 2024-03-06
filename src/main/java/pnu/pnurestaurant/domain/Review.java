package pnu.pnurestaurant.domain;


import jakarta.persistence.*;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

@Entity
public class Review extends TimeStamp{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private Double rating;

    private String content;

    private String reviewPictureUrl;


}
