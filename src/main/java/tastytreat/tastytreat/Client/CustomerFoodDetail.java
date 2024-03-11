package tastytreat.tastytreat.Client;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import tastytreat.tastytreat.classes.Food;
import tastytreat.tastytreat.classes.FoodManager;
import tastytreat.tastytreat.classes.Restaurant;
import tastytreat.tastytreat.classes.RestaurantManager;
import tastytreat.tastytreat.util.OrderDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CustomerFoodDetail
{

    private CustomerMain main;
    public RestaurantManager restaurantManager;
    public FoodManager foodManager;

    void setMain(CustomerMain main) {
        this.main = main;
    }

    @FXML
    private ImageView CompanyLogo;

    @FXML
    private Button FoodButton;

    @FXML
    private ImageView RestaurantImage;

    @FXML
    private Button RestautrantButton;

    @FXML
    private Label TotalFoodInRes;

    //table view

    @FXML
    private TableView<Food> foodTable;
    @FXML
    public TableColumn<Food, String> foodNameColumn;
    @FXML
    public TableColumn<Food, String> foodPriceColumn;
    @FXML
    public TableColumn<Food, String> foodCategoryColumn;
    @FXML
    public TableColumn<Food, String> foodRestaurantIDColumn;
    @FXML
    public TableColumn<Food, Button> foodOrderColumn;
    @FXML
    private Button OrderButton;
    @FXML
    public void OrderNow(ActionEvent event) throws IOException {

//        System.out.println("Order button clicked");

        List<Food> oder = new ArrayList<Food>();
        oder.addAll(main.getOrderedFoodList());
        OrderDTO orderDTO = new OrderDTO(oder);
        main.getNetworkUtil().write("Order");
        main.getNetworkUtil().write(orderDTO);
        showConfirmation();

        //System.out.println(userName+ "username Ordered this foods of the restaurant");
//        System.out.println(orderDTO.getId());
//        for(Food food:orderDTO.getOrderList()){
//            System.out.println(food.getName());
//        }

        //main.getNetworkUtil().write("Hello buddy");
        System.out.println("Order request sent to the server");
        //reset the previous order
        main.getOrderedFoodList().clear();
        System.out.println("Order list cleared");
        //orderDTO.showOrderList();
    }
    public void showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Confirmation");
        alert.setHeaderText("Order Confirmation");
        alert.setContentText("Your order has been placed successfully.");
        alert.showAndWait();
    }

    @FXML
    private ImageView image;

    @FXML
    private Button logOutButton;

    @FXML
    private Label message;

    @FXML
    private Label restaurantName;

    @FXML
    private Label restaurantPrice;

    @FXML
    private Label restaurantScore;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchInput;

    @FXML
    private ChoiceBox<?> searchbybox;

    //details of table view
    void updateFoodTable(List<Food> foodArrayList) {
        ObservableList<Food> fooddata = FXCollections.observableArrayList(
                foodArrayList
        );
        foodNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        foodPriceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice()))
        );
        foodCategoryColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCategory())
        );
        foodRestaurantIDColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        String.valueOf(cellData.getValue().getRestaurantId())
                )
        );
        addButtonToTable();
        foodTable.setItems(fooddata);
    }

    public void addButtonToTable() {
        TableColumn<Food, Void> colBtn = new TableColumn("Order");

        Callback<TableColumn<Food, Void>, TableCell<Food, Void>> cellFactory = new Callback<TableColumn<Food, Void>, TableCell<Food, Void>>() {
            @Override
            public TableCell<Food, Void> call(
                    final TableColumn<Food, Void> param
            ) {
                final TableCell<Food, Void> cell = new TableCell<Food, Void>() {
                    private final Button btn = new Button("Add to Cart");

                    {
                        //table button action

                        btn.setOnAction((ActionEvent event) -> {
                            Food Food = getTableView().getItems().get(getIndex());
                            main.addFood(Food);
                            System.out.println("selectedFood: " + Food);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        foodTable.getColumns().add(colBtn);
    }

    public String userName;


    @FXML
    public void init(String msg, Restaurant restaurant) {
        userName = msg;
        message.setText(msg);
        restaurantName.setText(restaurant.getName());
        restaurantPrice.setText(restaurant.getPrice());
        restaurantScore.setText(String.valueOf(restaurant.getScore()));



        try {
            // Attempt to load the image
            Image img = new Image(CustomerMain.class.getResourceAsStream("/tastytreat/tastytreat/apurbo.jpg"));
            Image logo = new Image(CustomerMain.class.getResourceAsStream("/tastytreat/tastytreat/LOGO.jpg"));
            Image resImage = new Image(CustomerMain.class.getResourceAsStream("/tastytreat/tastytreat/restaurant.jpg"));
            RestaurantImage.setImage(resImage);
            image.setImage(img);
            CompanyLogo.setImage(logo);
            System.out.println("Image set successfully");
        } catch (Exception e) {
            // Handle any exceptions that occur
            System.out.println("Problem occurred while reading image.");
            e.printStackTrace();
            // You might also display an error message to the user here
        }

        restaurantManager = new RestaurantManager(main.getRestaurantList(), main.getFoodList());
        System.out.println("restaurant manager loaded successfully");
        foodManager = new FoodManager(restaurantManager, main.getFoodList());

        // Initialize restaurant manager and food manager
        try {
            //foodManager.showListFoods();
            // Setting values to the table
            List<Food> resFoods = restaurantManager.getFoodsOfRestaurat(restaurant);
            updateFoodTable(resFoods);

            TotalFoodInRes.setText("Total Food " +resFoods.size());

            System.out.println("Food table loaded successfully");
        } catch (Exception e) {
            // Handle exceptions related to initializing managers
            System.out.println("error in getting list from client");
            e.printStackTrace();
            // You might display an error message or log the error here
        }
    }

    @FXML
    void logoutAction(ActionEvent event) {
        try {
            main.showLoginPage();
            System.out.println("Logout successful");
        } catch (Exception e) {
            // Handle exceptions related to navigating to the login page
            e.printStackTrace();
            // You might display an error message or log the error here
        }
    }

    @FXML
    void RestaurantPage(ActionEvent event) {
        try {
            // Just for checking
            main.showResHomePage(userName);
        } catch (Exception e) {
            // Handle exceptions related to navigating to the restaurant page
            e.printStackTrace();
            // You might display an error message or log the error here
        }
    }

    @FXML
    void FoodPage(ActionEvent event) {
        try {
            main.showHomePage(userName);
        } catch (Exception e) {
            // Handle exceptions related to navigating to the food page
            e.printStackTrace();
            // You might display an error message or log the error here
        }
    }






    @FXML
    void searchAction(ActionEvent event) {

    }

}
