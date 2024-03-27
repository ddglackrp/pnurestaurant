package pnu.pnurestaurant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pnu.pnurestaurant.Dto.response.RestaurantWithReviewsDto;
import pnu.pnurestaurant.Dto.response.ReviewResponseDto;
import pnu.pnurestaurant.Dto.response.RestaurantResponseDto;
import pnu.pnurestaurant.auth.PrincipalDetails;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.service.RestaurantService;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("")
    public String restaurants(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              Model model)
    {
        model.addAttribute("member",principalDetails.getMember());
        return "restaurant/foodTypeList";
    }

    @GetMapping("/name")
    public String restaurantList(@RequestParam("foodname") String foodName, Model model){
        List<RestaurantResponseDto> restaurants = restaurantService.findRestaurantsByName(foodName);
        model.addAttribute("restaurants", restaurants);
        return "restaurant/restaurantList";
    }

    @GetMapping("/korean")
    public String koreanList(Model model){
        List<RestaurantResponseDto> restaurants = restaurantService.findRestaurantsByFoodType(FoodType.KOREAN);
        model.addAttribute("restaurants", restaurants);
        return "restaurant/restaurantList";
    }

    @GetMapping("/japanese")
    public String japaneseList(Model model){
        List<RestaurantResponseDto> restaurants = restaurantService.findRestaurantsByFoodType(FoodType.JAPANESE);
        model.addAttribute("restaurants", restaurants);
        return "restaurant/restaurantList";
    }

    @GetMapping("/chinese")
    public String chineseList(Model model){
        List<RestaurantResponseDto> restaurants = restaurantService.findRestaurantsByFoodType(FoodType.CHINESE);
        model.addAttribute("restaurants", restaurants);
        return "restaurant/restaurantList";
    }

    @GetMapping("/western")
    public String westernList(Model model){
        List<RestaurantResponseDto> restaurants = restaurantService.findRestaurantsByFoodType(FoodType.WESTERN);
        model.addAttribute("restaurants", restaurants);
        return "restaurant/restaurantList";
    }

//    @GetMapping("/{restaurantId}")
//    public String restaurant(@PathVariable("restaurantId") Long restaurantId,
//                             @AuthenticationPrincipal PrincipalDetails principalDetails,
//                             Model model){
//
//        RestaurantResponseDto findRestaurant = restaurantService.findRestaurantWithRelation(restaurantId);
//
//        model.addAttribute("restaurant", findRestaurant);
//
//        List<ReviewResponseDto> reviewResponseDtos = findRestaurant.getReviews();
//
//        model.addAttribute("reviews", reviewResponseDtos);
//
//        model.addAttribute("UserId", principalDetails.getMember().getId());
//
//        return "restaurant/restaurantInfo";
//    }

    /**
     * 궁금점
     * 내 생각 : 한번에 fetch join 하는 것은 page 라는 목적에 맞지않다.
     * 그래서 내가 구현한 방식은
     * 1. 식당ID를 활용하여 식당 조회 => 쿼리 1번
     * 2. REVIEW 와 RESTAURANT, MEMBER 는 MANY_TO_ONE 관계이므로 FETCH 조인을 하고 조건을 걸어도 될거같다. => 쿼리 2번
     *
     * 여기서 의문점
     * FETCH 조인에 조건을 거는것은 위험할 수 있다. 난 (MANY_TO_ONE 관계라서 걸긴 했다)
     * 아예 JDBC 등을 활용해 NATIVE 쿼리를 이용할까?
     */
    @GetMapping("/{restaurantId}")
    public String restaurant2(@PathVariable("restaurantId") Long restaurantId,
                              @AuthenticationPrincipal PrincipalDetails principalDetails,
                              @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model){

        RestaurantWithReviewsDto findRestaurant = restaurantService.getRestaurantWithReviews(restaurantId, pageable);

        model.addAttribute("restaurant", findRestaurant);

        Page<ReviewResponseDto> reviewResponseDtos = findRestaurant.getReviews();

        model.addAttribute("reviews", reviewResponseDtos);

        model.addAttribute("UserId", principalDetails.getMember().getId());

        return "restaurant/restaurantInfo";
    }



}
