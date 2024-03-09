package pnu.pnurestaurant.Dto;

import lombok.Data;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.domain.Review;

@Data
public class ReviewDto {

    private Long id;
    private Double rating;
    private String content;
    private Member member;

    public ReviewDto(){}

    public ReviewDto(Review review){
        this.id = review.getId();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.member = review.getMember();
    }

}
