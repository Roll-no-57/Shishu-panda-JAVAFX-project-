package tastytreat.tastytreat.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tastytreat.tastytreat.classes.Food;
import tastytreat.tastytreat.classes.Restaurant;
import tastytreat.tastytreat.util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerMain extends Application
{
    public List<Food> foodList;
    public List<Restaurant> restaurantList;
    //orders
    List<Food> orderedFoodList = new ArrayList<Food>();
    private Stage stage;
    private NetworkUtil networkUtil;
    public NetworkUtil getNetworkUtil()
    {
        return networkUtil;
    }

    public static void main(String[] args)
    {
        // This will launch the JavaFX application
        launch();
    }

    public void addFood(Food food)
    {
        orderedFoodList.add(food);
    }
    public void showOrderedFoodList()
    {
        for(Food food : orderedFoodList)
        {
            System.out.println(food.getName());
        }
    }

    public List<Food> getOrderedFoodList()
    {
        showOrderedFoodList();
        return orderedFoodList;
    }
    public void resetOrderedFoodList()
    {
        orderedFoodList.clear();
    }

    List<Food> getFoodList()
    {
        return foodList;
    }

    //getters and setters
    void setFoodList(List<Food> foodList)
    {
        this.foodList = foodList;
    }

    List<Restaurant> getRestaurantList()
    {
        return restaurantList;
    }

    void setRestaurantList(List<Restaurant> restaurantList)
    {
        this.restaurantList = restaurantList;
    }

    public Stage getStage()
    {
        return stage;
    }



    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;
        connectToServer();
//        StartPage();
        showLoginPage();
    }

    private void connectToServer() throws IOException
    {
        String serverAddress = "127.0.0.1";
        int serverPort = 4444;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
//        new ReadThread(this);
    }

    public void showLoginPage() throws Exception
    {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/tastytreat/tastytreat/login.fxml"));
//        loader.setLocation(getClass().getResource("E:\\CompetitiveProgramming\\OOPs\\LoginServer\\src\\Main_java\\resources\\login.fxml"));
        Parent root = loader.load();
        String path = "/tastytreat/tastytreat/wall.jpg";
        // Loading the controller
        CustomerLoginController controller = loader.getController();
        controller.setMain(this, path);

        // Set the primary stage
        stage.setTitle("Customer Login Page");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();
    }

    public void showHomePage(String userName) throws Exception
    {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/tastytreat/tastytreat/home.fxml"));
        System.out.println("Home page fxml file loaded");
//        loader.setLocation(getClass().getResource("/package_name/your_fxml_file.fxml"));

//        loader.setLocation(getClass().getResource("E:\\CompetitiveProgramming\\OOPs\\LoginServer\\src\\Main_java\\resources\\home.fxml"));
        Parent root = loader.load();
//        System.out.println("fxml file loaded");

        // Loading the controller
        CustomerHomeController controller = loader.getController();
        controller.setMain(this);
        controller.init(userName);


        // Set the primary stage
        stage.setTitle("Client Home Page");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();
    }

    public void showResHomePage(String userName) throws Exception
    {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/tastytreat/tastytreat/ClientrestaurantPage.fxml"));
        System.out.println("ResHome page fxml file loaded");
//        loader.setLocation(getClass().getResource("/package_name/your_fxml_file.fxml"));

//        loader.setLocation(getClass().getResource("E:\\CompetitiveProgramming\\OOPs\\LoginServer\\src\\Main_java\\resources\\home.fxml"));
        Parent root = loader.load();
//        System.out.println("fxml file loaded");

        // Loading the controller
        CustomerRestaurantController controller = loader.getController();
        controller.setMain(this);
        controller.init(userName);


        // Set the primary stage
        stage.setTitle("Client Restaurant Home Page");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();
    }

    public void showRestDetailPage(String userName, Restaurant restaurant) throws Exception
    {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/tastytreat/tastytreat/ClientRestDetail.fxml"));
        System.out.println("ResHomeDetail page fxml file loaded");
//        loader.setLocation(getClass().getResource("/package_name/your_fxml_file.fxml"));

//        loader.setLocation(getClass().getResource("E:\\CompetitiveProgramming\\OOPs\\LoginServer\\src\\Main_java\\resources\\home.fxml"));
        Parent root = loader.load();
//        System.out.println("fxml file loaded");

        // Loading the controller
        CustomerFoodDetail controller = loader.getController();
        controller.setMain(this);
        controller.init(userName, restaurant);


        // Set the primary stage
        stage.setTitle("Client Restaurant detail Home Page");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();
    }

    public void showAlert()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }
}
