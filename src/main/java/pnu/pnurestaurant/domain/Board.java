package pnu.pnurestaurant.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pnu.pnurestaurant.domain.restaurant.Restaurant;
import static lombok.AccessLevel.PRIVATE;


@Entity
@Builder
@Getter
@AllArgsConstructor(access = PRIVATE)
public class Board extends TimeStamp{

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    private Double googleRating;
    private Double kakaoRating;
    private Double studentRating;

    public Board() {
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setRelationRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }

}
