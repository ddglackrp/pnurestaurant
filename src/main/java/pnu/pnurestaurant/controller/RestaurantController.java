package pnu.pnurestaurant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{restaurantId}")
    public String restaurant(@PathVariable("restaurantId") Long restaurantId,
                             @AuthenticationPrincipal PrincipalDetails principalDetails,
                             Model model){

        RestaurantResponseDto findRestaurant = restaurantService.findRestaurantWithRelation(restaurantId);

        model.addAttribute("restaurant", findRestaurant);

        List<ReviewResponseDto> reviewResponseDtos = findRestaurant.getReviews();

        model.addAttribute("reviews", reviewResponseDtos);

        model.addAttribute("UserId", principalDetails.getMember().getId());

        return "restaurant/restaurantInfo";
    }




}
