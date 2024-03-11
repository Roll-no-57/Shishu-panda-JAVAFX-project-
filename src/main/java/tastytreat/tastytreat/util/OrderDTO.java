package tastytreat.tastytreat.util;

import tastytreat.tastytreat.classes.Food;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import java.util.List;

public class OrderDTO implements Serializable {

    private List<Food> orderList=new ArrayList<Food>();
   private String userName;


    public OrderDTO(List<Food> orderList) {
        this.userName = userName;
        this.orderList = orderList;
        System.out.println("showing the order list in orderDTO constructor");
        showOrderList();
    }
    public String getUserName() {
        return userName;
    }


    public String getId(){
        Food firstFood = orderList.get(0);
        System.out.println("first food id is "+firstFood.getRestaurantId());
        return String.valueOf(firstFood.getRestaurantId());
    }


    public List<Food> getOrderList() {
        return orderList;
    }
    public void setOrderList(List<Food> orderList) {
        this.orderList = orderList;
    }
    public void showOrderList(){
        for(Food food : orderList){
            System.out.println(food.getName());
        }
    }

}
