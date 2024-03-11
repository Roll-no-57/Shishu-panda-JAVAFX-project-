package tastytreat.tastytreat.classes;

import tastytreat.tastytreat.util.FileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodManager {

  private List<Food> listOfFoods = new ArrayList<Food>();
  private RestaurantManager restaurantManager;
//  private int totalFoodItems;

  public FoodManager(
    RestaurantManager restaurantManager,
    List<Food> initialListOfFoods
  ) {
    this.restaurantManager = restaurantManager;
    listOfFoods.addAll(initialListOfFoods);
  }

  private boolean isAddedFood(Food food) {
    for (Food r : listOfFoods) {
      if (r.getName().equalsIgnoreCase(food.getName()) && r.getCategory().equalsIgnoreCase(food.getCategory())) {
        return true;
      }
    }
    for(Restaurant r : restaurantManager.getRestaurants()) {
      if(food.getRestaurantId()==r.getId()){
        return true;
      }
    }
    return false;
  }

  // Search for food items
  public List<Food> searchFoodByName(String name) {
    List<Food> list = new ArrayList<Food>();

    for (Food x : listOfFoods) {
      if (x.getName().toLowerCase().contains(name.toLowerCase())) {
        list.add(x);
      } 
    }
    return list;
  }

  //search for food items by restaurant name
  public List<Food> searchFoodByRestaurantName(
    String foodName,
    String restaurantName
  ) {
    List<Restaurant> listRes = restaurantManager.searchRestaurantByName(restaurantName);
    
    List<Food> listFood = new ArrayList<Food>();

    for(Restaurant restaurant : listRes){

      for(Food food : listOfFoods){
        if(restaurant.getId()==food.getRestaurantId() 
        && food.getName().toLowerCase().contains(foodName.toLowerCase())){
          listFood.add(food);
        }
      }
    }
    
    return listFood;
  }

  //search food by category
  public List<Food> searchFoodsByCategory(String category) {
    List<Food> list = new ArrayList<Food>();

    for (Food x : listOfFoods) {
      if (x.getCategory().toLowerCase().contains(category.toLowerCase())) {
        list.add(x);
      } 
    }
    return list;
  }

  public List<Food> searchFoodByCategoryOfaRestaurant(
    String category,
    String restaurantName
  ) {
    List<Food> list = new ArrayList<Food>();
    List<Restaurant> listRes = restaurantManager.searchRestaurantByName(restaurantName);

   for(Restaurant restaurant : listRes){

      for(Food food : listOfFoods){
        if(restaurant.getId()==food.getRestaurantId() 
        && food.getCategory().toLowerCase().contains(category.toLowerCase())){
          list.add(food);
        }
      }
    }


    return list;
  }

  public List<Food> searchFoodByPrice(int minPrice, int maxPrice) {
    List<Food> list = new ArrayList<Food>();
    for (Food x : listOfFoods) {
      if (x.getPrice() >= minPrice && x.getPrice() <= maxPrice) {
        list.add(x);
      }
    }
    return list;
  }

  public List<Food> searchFoodByPriceAndRestaurant(
    int minPrice,
    int maxPrice,
    String restaurantName
  ) {
    List<Restaurant> listRes = restaurantManager.searchRestaurantByName(restaurantName);
    List<Food> list = new ArrayList<Food>();
    

    for (Restaurant x : listRes) {
      for(Food y : listOfFoods){
        if(x.getId()==y.getRestaurantId() && y.getPrice()>=minPrice && y.getPrice()<=maxPrice ){
          list.add(y);
        }
      }
    }
    return list;
  }

  public List<Food> searchForCostliestFood(String restaurantName) {
    List<Food> list = new ArrayList<Food>();
    //using sorting
    List<Restaurant> listRes = restaurantManager.searchRestaurantByName(restaurantName);


    double maxPrice = 0;

    for (Restaurant x : listRes) {
      for(Food y : listOfFoods){
        if(x.getId()==y.getRestaurantId() && y.getPrice()>maxPrice ){
          maxPrice = y.getPrice();
        }
      }
    }


    for (Food food : listOfFoods) {
      if (food.getPrice() == maxPrice) {
        list.add(food);
      }
    }

    return list;
  }

  public Map<String, Integer> totalFoodItemsOnRestaurants() {
    Map<String, Integer> restaurantFoodCountMap = new HashMap<>();

    for (Restaurant restaurant : restaurantManager.getRestaurants()) {
      int totalItemsCount = 0;
      for (Food food : listOfFoods) {
        if (food.getRestaurantId() == restaurant.getId()) totalItemsCount++;
      }

      restaurantFoodCountMap.put(restaurant.getName(), totalItemsCount);
    }

    return restaurantFoodCountMap;
  }

  // Add food item
  public boolean addFood(Food food) {
    if (isAddedFood(food)) {
      return false;
    }
    listOfFoods.add(food);
    FileManager.saveAndUpdateFoods(listOfFoods);
    return true;
  }

  public List<Food> getFoods() {
    return listOfFoods;
  }

  public void  showListFoods(){

    for(Food food : listOfFoods) {
      food.showFoodDetails();
    }
  }

  public int gettotalFoodItems() {
    return listOfFoods.size();
  }

}
