package pnu.pnurestaurant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pnu.pnurestaurant.Dto.ReviewDto;
import pnu.pnurestaurant.auth.PrincipalDetails;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.domain.restaurant.Restaurant;
import pnu.pnurestaurant.service.RestaurantService;
import pnu.pnurestaurant.service.ReviewService;

import java.util.List;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;


    @PostMapping("/add/{restaurantId}")
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

    @GetMapping("/edit/{reviewId}")
    public String checkReview(@PathVariable("reviewId") Long reviewId, Model model){

        Review findReview = reviewService.findOneFetchAll(reviewId);

        ReviewDto reviewDto = new ReviewDto(findReview);

        model.addAttribute("review", reviewDto);
        model.addAttribute("restaurantId", findReview.getRestaurant().getId());

        return "review/reviewInfo";
    }

    @PostMapping("/edit/{reviewId}")
    public String updateReview(@PathVariable("reviewId") Long reviewId,
                               @RequestParam("content") String content,
                               @RequestParam("restaurantId") String restaurantId){

        reviewService.updateReview(reviewId, content);
        return "redirect:/restaurants/" + restaurantId;
    }

    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable("reviewId") Long reviewId,
                               @RequestParam("restaurantId") String restaurantId){

        reviewService.deleteReview(reviewId);
        return "redirect:/restaurants/" + restaurantId;
    }

}
