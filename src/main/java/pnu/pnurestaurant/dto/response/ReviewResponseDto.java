package pnu.pnurestaurant.dto.response;

import lombok.Data;
import pnu.pnurestaurant.domain.Review;

@Data
public class ReviewResponseDto {

    private Long id;
    private Double rating;
    private String content;
    private Long restaurantId;
    private Long memberId;

    public ReviewResponseDto(){}

    public ReviewResponseDto(Review review){
        this.id = review.getId();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.restaurantId = review.getRestaurant().getId();
        this.memberId = review.getMember().getId();
    }
}
