package tastytreat.tastytreat.classes;

import java.io.Serializable;

public class Food implements Serializable {

  private int restaurantId;
  private String category;
  private String name;
  private double price;
//  private Button button;

  public Food(int restaurantId, String category, String name, double price) {
    this.restaurantId = restaurantId;
    this.category = category;
    this.name = name;
    this.price = price;
  // this.button = new Button("Add to Cart");
  }

//    public Button getButton() {
//        return button;
//    }
//    public void setButton(Button button) {
//        this.button = button;}
  // public getters
  public int getRestaurantId() {
    return restaurantId;
  }

  public String getCategory() {
    return category;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public void showFoodDetails() {
    System.out.println("Food Name: " + name);
    System.out.println("Food RestaurantId: " + restaurantId);
    System.out.println("Food Category: " + category);
    System.out.println("Food Price: " + price);
  }
//  @Override
//  public String toString() {
//    return  name;
//  }
}
