package pnu.pnurestaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pnu.pnurestaurant.domain.restaurant.FoodType;
import pnu.pnurestaurant.domain.restaurant.Restaurant;
import pnu.pnurestaurant.service.RestaurantService;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("")
    public String restaurant(){
        return "restaurant/foodTypeList";
    }

    @GetMapping("/name")
    public String restaurantList(@RequestParam("foodname") String foodName, Model model){
        List<Restaurant> restaurants = restaurantService.findRestaurantsByName(foodName);
        model.addAttribute("restaurants", restaurants);
        return "restaurant/restaurantList";
    }

    @GetMapping("/korean")
    public String koreanList(Model model){
        List<Restaurant> restaurants = restaurantService.findRestaurantsByFoodType(FoodType.KOREAN);
        model.addAttribute("restaurants", restaurants);
        return "restaurant/restaurantList";
    }

    @GetMapping("/japanese")
    public String japaneseList(Model model){
        List<Restaurant> restaurants = restaurantService.findRestaurantsByFoodType(FoodType.JAPANESE);
        model.addAttribute("restaurants", restaurants);
        return "restaurant/restaurantList";
    }

    @GetMapping("/chinese")
    public String chineseList(Model model){
        List<Restaurant> restaurants = restaurantService.findRestaurantsByFoodType(FoodType.CHINESE);
        model.addAttribute("restaurants", restaurants);
        return "restaurant/restaurantList";
    }

    @GetMapping("/western")
    public String westernList(Model model){
        List<Restaurant> restaurants = restaurantService.findRestaurantsByFoodType(FoodType.WESTERN);
        model.addAttribute("restaurants", restaurants);
        return "restaurant/restaurantList";
    }



}
