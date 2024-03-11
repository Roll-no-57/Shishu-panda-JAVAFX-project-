package tastytreat.tastytreat.classes;

import tastytreat.tastytreat.util.FileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantManager {

  private final List<Restaurant> listOfRestaurants = new ArrayList<Restaurant>();
  private final List<Food> listOfFoods = new ArrayList<Food>();

  public RestaurantManager(List<Restaurant> initialListOfRestaurants,List<Food> initiallistOfFoods) {
    listOfRestaurants.addAll(initialListOfRestaurants);
    listOfFoods.addAll(initiallistOfFoods);

  }
  public int gettotalRestaurant(){
    return listOfRestaurants.size();
  }


  public List<Food> getFoodsOfRestaurat(Restaurant restaurant){
    List<Food> list = new ArrayList<Food>();

    for (Food x : listOfFoods) {
      if (x.getRestaurantId() == restaurant.getId()) {
        list.add(x);
      }
    }
    return list;
  }

  private boolean isAddedRestaurant(Restaurant restaurant) {
    for (Restaurant r : listOfRestaurants) {
      if (r.getName().equalsIgnoreCase(restaurant.getName())) {
        return true;
      }
    }
    return false;
  }

  private void addRestaurantToCategory(
    Map<String, List<String>> categoryMap,
    String category,
    String restaurantName
  ) {
    if (!category.isEmpty()) {
      categoryMap
        .computeIfAbsent(category, k -> new ArrayList<>())
        .add(restaurantName);
    }
  }

  public List<Restaurant> getRestaurants() {
    return listOfRestaurants;
  }

  //Search for restaurant
  public List<Restaurant> searchRestaurantByName(String name) {
    List<Restaurant> list = new ArrayList<>();

    for (Restaurant x : listOfRestaurants) {
      if (x.getName().toLowerCase().contains(name.toLowerCase())) {
        list.add(x);
      }
    }
    return list;
  }

  public List<Restaurant> searchRestaurantsByScore(
    double minScore,
    double maxScore
  ) {
    List<Restaurant> list = new ArrayList<Restaurant>();

    for (Restaurant x : listOfRestaurants) {
      if (x.getScore() >= minScore && x.getScore() <= maxScore) {
        list.add(x);
      }
    }
    return list;
  }

  public List<Restaurant> searchRestaurantsByCategory(String category) {
    List<Restaurant> list = new ArrayList<Restaurant>();

    for (Restaurant x : listOfRestaurants) {
      if (
        x.getCategory1().toLowerCase().contains(category.toLowerCase()) ||
        x.getCategory2().toLowerCase().contains(category.toLowerCase()) ||
        x.getCategory3().toLowerCase().contains(category.toLowerCase())
      ) {
        list.add(x);
      }
    }
    return list;
  }

  public List<Restaurant> searchRestaurantsByPrice(String price) {
    List<Restaurant> list = new ArrayList<Restaurant>();

    for (Restaurant x : listOfRestaurants) {
      if (x.getPrice().equals(price)) {
        list.add(x);
      }
    }
    return list;
  }

  public List<Restaurant> searchRestaurantsByZipCode(String zipCode) {
    List<Restaurant> list = new ArrayList<Restaurant>();

    for (Restaurant x : listOfRestaurants) {
      if (x.getZipCode().equals(zipCode)) {
        list.add(x);
      }
    }
    return list;
  }

  
  public Map<String, List<String>> getRestaurantByCategory() {
    Map<String, List<String>> categoryMap = new HashMap<>();

    for (Restaurant restaurant : listOfRestaurants) {
      String category1 = restaurant.getCategory1();
      String category2 = restaurant.getCategory2();
      String category3 = restaurant.getCategory3();

      // Add the restaurant to the respective category lists
      addRestaurantToCategory(categoryMap, category1, restaurant.getName());
      addRestaurantToCategory(categoryMap, category2, restaurant.getName());
      addRestaurantToCategory(categoryMap, category3, restaurant.getName());
    }

    return categoryMap;
  }

  // Add restaurant
  public boolean addRestaurant(Restaurant restaurant) {
    if (isAddedRestaurant(restaurant)) {
      return false;
    }
    listOfRestaurants.add(restaurant);
    FileManager.saveAndUpdateRestaurants(listOfRestaurants);
    return true;
  }

  //show all restaurant
    public void showAllRestaurant() {
        for (Restaurant x : listOfRestaurants) {
            x.showDetailsOfRestaurant();
        }
    }

}
