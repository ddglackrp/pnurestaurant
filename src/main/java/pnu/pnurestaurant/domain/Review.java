package pnu.pnurestaurant.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import pnu.pnurestaurant.domain.restaurant.Restaurant;

@Entity
@Getter
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

    public Review() {
    }
    @Builder
    public Review(Double rating, String content, String reviewPictureUrl) {
        this.rating = rating;
        this.content = content;
        this.reviewPictureUrl = reviewPictureUrl;
    }

    /**
     * 연관관계 메소드
     */
    public void makeRelationMember(Member member){
        this.member = member;
        //member.getReview.add(this);
    }

    public void makeRelationRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
        restaurant.getReviews().add(this);
    }

    public void changeContent(String content){
        this.content = content;
    }


}
