package pnu.pnurestaurant.Dto;

import lombok.Data;
import pnu.pnurestaurant.domain.Review;

@Data
public class ReviewDto {

    private Double rating;
    private String content;

    public ReviewDto(){}

    public ReviewDto(Review review){
        this.rating = review.getRating();
        this.content = review.getContent();
    }

}
