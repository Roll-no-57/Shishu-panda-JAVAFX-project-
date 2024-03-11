package tastytreat.tastytreat.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import tastytreat.tastytreat.classes.*;
import tastytreat.tastytreat.util.*;

public class RestaurantMain extends Application {
    private Stage stage;
    private NetworkUtil networkUtil;
    public List<Food> foodList;
    public List<Restaurant> restaurantList;


    //getters and setters
    void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }
    List<Food> getFoodList(){
        return foodList;
    }

    List<Restaurant> getRestaurantList(){
        return restaurantList;
    }
    void setRestaurantList(List<Restaurant> restaurantList) {this.restaurantList=restaurantList;
    }

    public Stage getStage() {
        return stage;
    }
    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        System.out.println("successfully connected to server");
        showResLoginPage();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 4444;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
//        new ReadThread(this);
    }



    public void showResLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/tastytreat/tastytreat/ResLogin.fxml"));
//        loader.setLocation(getClass().getResource("E:\\CompetitiveProgramming\\OOPs\\LoginServer\\src\\Main_java\\resources\\login.fxml"));
        Parent root = loader.load();
        String path = "/tastytreat/tastytreat/wall.jpg";
        // Loading the controller
        RestaurantLoginController controller = loader.getController();
        controller.setMain(this,path);

        // Set the primary stage
        stage.setTitle("Restaurant Login Page");
        stage.setScene(new Scene(root, 900,550));
        stage.show();
    }
    public Restaurant getRestaurant(String userName){
        for(Restaurant x: restaurantList){
            if(x.getId()==Integer.parseInt(userName)){
                return x;
            }
        }
        return null;
    }

    public void showResClientHomePage(String userName) throws Exception {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/tastytreat/tastytreat/RestaurantClientHome.fxml"));
        System.out.println("Home page fxml file loaded");
//        loader.setLocation(getClass().getResource("/package_name/your_fxml_file.fxml"));
            
//        loader.setLocation(getClass().getResource("E:\\CompetitiveProgramming\\OOPs\\LoginServer\\src\\Main_java\\resources\\home.fxml"));
        Parent root = loader.load();
//        System.out.println("fxml file loaded");
        Restaurant cl = getRestaurant(userName);

        // Loading the controller
        RestaurantHomeController controller = loader.getController();
        controller.setMain(this);
        controller.init(cl);
        // Set the primary stage
        stage.setTitle("Client Home Page");
        stage.setScene(new Scene(root, 900,550));
        stage.show();
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }



    public static void main(String[] args) {
        // This will launch the JavaFX application
        launch();
    }
}
