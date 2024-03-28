package pnu.pnurestaurant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pnu.pnurestaurant.dto.request.ReviewRequestDto;
import pnu.pnurestaurant.dto.response.ReviewResponseDto;
import pnu.pnurestaurant.auth.PrincipalDetails;
import pnu.pnurestaurant.service.RestaurantService;
import pnu.pnurestaurant.service.ReviewService;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;


    @PostMapping("/add/{restaurantId}")
    public String addReview(@ModelAttribute("reviewRequestDto") ReviewRequestDto reviewRequestDto,
                            @RequestParam("restaurantId") Long restaurantId,
                            @AuthenticationPrincipal PrincipalDetails principalDetails
    ){

        reviewService.saveReview(reviewRequestDto, principalDetails.getMember(), restaurantId);
        restaurantService.modifyRating(restaurantId);

        return "redirect:/restaurants/{restaurantId}";
    }

    @GetMapping("/edit/{reviewId}")
    public String checkReview(@PathVariable("reviewId") Long reviewId, Model model){

        ReviewResponseDto reviewResponseDto = reviewService.findReviewWithRelation(reviewId);

        model.addAttribute("review", reviewResponseDto);
        model.addAttribute("restaurantId", reviewResponseDto.getRestaurantId());

        return "review/reviewInfo";
    }

    @PostMapping("/edit/{reviewId}")
    public String updateReview(@PathVariable("reviewId") Long reviewId,
                               @RequestParam("content") String content,
                               @RequestParam("restaurantId") String restaurantId){

        reviewService.modifyReview(reviewId, content);
        return "redirect:/restaurants/" + restaurantId;
    }

    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable("reviewId") Long reviewId,
                               @RequestParam("restaurantId") String restaurantId){

        reviewService.deleteReview(reviewId);
        return "redirect:/restaurants/" + restaurantId;
    }

}
