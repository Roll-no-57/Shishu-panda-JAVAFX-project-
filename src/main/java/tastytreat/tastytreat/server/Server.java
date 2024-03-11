package tastytreat.tastytreat.server;

import javafx.application.Platform;
import tastytreat.tastytreat.classes.Food;
import tastytreat.tastytreat.classes.Restaurant;
import tastytreat.tastytreat.util.FileManager;
import tastytreat.tastytreat.util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Server
{
    public ConcurrentHashMap<String, String> passwordList;
    public ConcurrentHashMap<String, String> respasswordList;
    public List<Food> foodList;
    public List<Restaurant> restaurantList;
    public ConcurrentHashMap<String, NetworkUtil> clientMap = new ConcurrentHashMap<>();
    private ServerSocket serverSocket;


    void loadData() throws Exception {

        passwordList = FileManager.readPasswordsFromFile();
        respasswordList = FileManager.readResPasswordsFromFile();
//        System.out.println("Passwords loaded successfully");
//        showPasswordList();
        foodList = FileManager.loadDataForFoods();
//        System.out.println("foods loaded successfully");
//        showFoodList();
        restaurantList = FileManager.loadsDataOfRestaurant();
//        System.out.println("restaurants loaded successfully");
    }

    Server() throws Exception
    {
//        System.out.println("Server starts..............");
//        Platform.runLater(() -> {
////                        restaurantHomeController.resetTable();
//            restaurantHomeController.updateFoodTable(orderDTO.getOrderList());
//        });
        //loads the data from text file to server socket


        loadData();
        //accepts clients
        try
        {
            serverSocket = new ServerSocket(4444);
//            System.out.println("server starts..............");
            while (true)
            {
//                System.out.println("Waiting for client request..............");
                Socket clientSocket = serverSocket.accept();
//                System.out.println("server accepts a client");
                serve(clientSocket);
            }
        }
        catch (Exception e)
        {
            System.out.println("Server starts:" + e);
        }
    }

    public static void main(String[] args) throws Exception
    {
        new Server();
    }

    //getters and setters
    public ConcurrentHashMap<String, String> getPasswordList()
    {
        return passwordList;
    }

    public ConcurrentHashMap<String, String> getresPasswordList()
    {
        return respasswordList;
    }

    void showPasswordList()
    {
        for (String key : respasswordList.keySet())
        {
            System.out.println(key + " " + respasswordList.get(key));
        }
    }

    private void showFoodList()
    {
        for (Food food : foodList)
        {
            System.out.println(food.getRestaurantId() + " " + food.getName());
        }
    }

    public List<Food> getFoodList()
    {
        return foodList;
    }

    public List<Restaurant> getRestaurantsList()
    {
        return restaurantList;
    }

    public void serve(Socket clientSocket) throws IOException
    {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);

        new ServerReadThread(this, networkUtil, clientMap);
    }

}
