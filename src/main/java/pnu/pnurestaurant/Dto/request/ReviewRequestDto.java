package pnu.pnurestaurant.Dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class ReviewRequestDto {
    private Double rating;
    private String content;

    @Builder
    public ReviewRequestDto(Double rating, String content) {
        this.rating = rating;
        this.content = content;
    }
}
