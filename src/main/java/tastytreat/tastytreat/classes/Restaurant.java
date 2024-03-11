package tastytreat.tastytreat.classes;

import java.io.Serializable;

public class Restaurant implements Serializable {

  private int id;
  private String name;
  private double score;
  private String price;
  private String zipCode;
  private String category1;
  private String category2;
  private String category3;

  // Constructor
  public Restaurant(
    int id,
    String name,
    double score,
    String price,
    String zipCode,
    String category1,
    String category2,
    String category3
  ) {
    this.id = id;
    this.name = name;
    this.score = score;
    this.price = price;
    this.zipCode = zipCode;
    this.category1 = category1;
    this.category2 = category2;
    this.category3 = category3;
  }

  // Getters
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getScore() {
    return score;
  }

  public String getPrice() {
    return price;
  }

  public String getZipCode() {
    return zipCode;
  }

  public String getCategory1() {
    return category1;
  }

  public String getCategory2() {
    return category2;
  }

  public String getCategory3() {
    return category3;
  }

  public void showDetailsOfRestaurant() {
  
    System.out.println("Restaurant ID: " + id);
    System.out.println("Name: " + name);
    System.out.println("Score: " + score);
    System.out.println("Price: " + price);
    System.out.println("Zip Code: " + zipCode);
    System.out.println("Category 1: " + category1);

    if (category2 != null && !category2.isEmpty()) {
      System.out.println("Category 2: " + category2);
    }

    if (category3 != null && !category3.isEmpty()) {
      System.out.println("Category 3: " + category3);
    }
  }


}
