package pnu.pnurestaurant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pnu.pnurestaurant.auth.PrincipalDetails;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.restaurant.Restaurant;
import pnu.pnurestaurant.service.RestaurantService;
import pnu.pnurestaurant.service.ReviewService;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;


    @PostMapping("/{restaurantId}")
    public String addReview(@RequestParam("rating") Long rating,
                            @RequestParam("comment") String comment,
                            @RequestParam("restaurantId") Long restaurantId,
                            @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        Restaurant restaurant = restaurantService.findOneWithReviews(restaurantId);

        Review review = Review.builder()
                .rating(Double.valueOf(rating))
                .content(comment)
                .reviewPictureUrl("일단 보류")
                .build();

        review.makeRelationMember(principalDetails.getMember());
        review.makeRelationRestaurant(restaurant);

        reviewService.saveReview(review);

        List<Review> reviews = restaurant.getReviews();
        double sum = reviews.stream().mapToDouble(Review::getRating).sum();
        log.info("average = {}",sum/reviews.size());

        restaurantService.updateRating(restaurantId, sum/reviews.size());
        return "redirect:/restaurants/{restaurantId}";
    }
}
