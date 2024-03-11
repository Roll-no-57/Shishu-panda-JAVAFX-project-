package tastytreat.tastytreat.util;

import tastytreat.tastytreat.classes.Food;
import tastytreat.tastytreat.classes.Restaurant;

import java.io.Serializable;
import java.util.List;

public class DTO implements Serializable {
    private List<Food> foodList;
    private List<Restaurant> restaurantList;

    public DTO(List<Food> foodList, List<Restaurant> restaurantList) {
        this.foodList = foodList;
        this.restaurantList = restaurantList;
    }

    public List<Food> getFoodList() {
        return foodList;
    }
    public List<Restaurant> getRestaurantsList() {return restaurantList;}

}
