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

import java.util.ArrayList;
import java.util.List;

public class RestaurantHomeController
{

    public RestaurantManager restaurantManager;
    public FoodManager foodManager;
    public Restaurant restaurant;
    public TableColumn<Food, String> deliveredFoodNameColumn;

    public TableColumn<Food, String> OrderFoodNameColumn;
    public List<Food> OrderedFoodList = new ArrayList<Food>();
    public List<Food> DeliveredFoodList = new ArrayList<Food>();
    RestaurantMain resHomecl;
    String userName;
    @FXML
    private ImageView CompanyLogo;
    @FXML
    private TableView<Food> DeliveredFoods;
    @FXML
    private TableView<Food> OrderedFoods;
    @FXML
    private Label TotalFoodInRes;

    @FXML
    private Label TotalFoodOrdered;

    @FXML
    private Label TotalFoodServed;

    @FXML
    private Button logOutButton;

    @FXML
    private ImageView resImage;

    @FXML
    private Label restaurantName;

    @FXML
    private Label restaurantPrice;

    @FXML
    private Label restaurantScore;


    @FXML
    private TextField searchInput;

    void setMain(RestaurantMain res)
    {
        resHomecl = res;
    }

    public void updateFoodTable(List<Food> foodArrayList)
    {
        OrderedFoodList.addAll(foodArrayList);
        ObservableList<Food> fooddata = FXCollections.observableArrayList(OrderedFoodList);
        OrderFoodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        TotalFoodOrdered.setText("Total Food Ordered :" + OrderedFoodList.size() + "");
        OrderedFoods.setItems(fooddata);

    }



    public void updateDeliverList(Food food){
        DeliveredFoodList.add(food);
        ObservableList<Food> foodDeliverdata = FXCollections.observableArrayList(DeliveredFoodList);
        deliveredFoodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        TotalFoodServed.setText("Total Food Served :" + DeliveredFoodList.size() + "");
        DeliveredFoods.setItems(foodDeliverdata);
    }


//    public void resetTable(List<Food> foodArrayList)
//    {
//        foodArrayList.clear();
//        ObservableList<Food> fooddata = FXCollections.observableArrayList(foodArrayList);
//        OrderFoodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
//
//        addButtonToTable();
//        OrderedFoods.setItems(fooddata);
//    }

    public void addButtonToTable()
    {
        TableColumn<Food, Void> colBtn = new TableColumn("Status");

        Callback<TableColumn<Food, Void>, TableCell<Food, Void>> cellFactory = new Callback<TableColumn<Food, Void>, TableCell<Food, Void>>()
        {
            @Override
            public TableCell<Food, Void> call(final TableColumn<Food, Void> param)
            {
                final TableCell<Food, Void> cell = new TableCell<Food, Void>()
                {
                    private final Button btn = new Button("Deliver");
                    {
                        //table button action
                        btn.setOnAction((ActionEvent event) -> {
                            Food food = getTableView().getItems().get(getIndex());

                            updateDeliverList(food);
                            System.out.println("selectedFood: " + food);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (empty)
                        {
                            setGraphic(null);
                        }
                        else
                        {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        OrderedFoods.getColumns().add(colBtn);
    }


    public void init(Restaurant res)
    {
        try
        {
            if (res != null)
            {
                this.restaurant = res;
            }
            else
            {
                System.out.println("restaurant is null");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        userName = restaurant.getName();
//        message.setText(userName);
        restaurantName.setText(restaurant.getName());
        restaurantPrice.setText(restaurant.getPrice());
        restaurantScore.setText(String.valueOf(restaurant.getScore()));

        restaurantManager = new RestaurantManager(resHomecl.getRestaurantList(), resHomecl.getFoodList());
        System.out.println("restaurant manager loaded successfully");
        foodManager = new FoodManager(restaurantManager, resHomecl.getFoodList());

        try
        {

            Image logo = new Image(CustomerMain.class.getResourceAsStream("/tastytreat/tastytreat/LOGO.jpg"));
            Image mage = new Image(CustomerMain.class.getResourceAsStream("/tastytreat/tastytreat/restaurant.jpg"));
            resImage.setImage(mage);
            CompanyLogo.setImage(logo);
            System.out.println("Image set successfully");
        }
        catch (Exception e)
        {
            // Handle any exceptions that occur
            System.out.println("Problem occurred while reading image.");
            e.printStackTrace();
            // You might also display an error message to the user here
        }

        //update the table
        //updateFoodTable(foodManager.getFoods());
        addButtonToTable();

        TotalFoodOrdered.setText("Total Food Ordered :" + OrderedFoodList.size() + "");
        TotalFoodServed.setText("Total Food Served :" + 0 + "");

        new RestaurantReadThread(this);
    }


    @FXML
    void logoutAction(ActionEvent event)
    {
        try
        {
            resHomecl.showResLoginPage();
            System.out.println("Logout successful");
        }
        catch (Exception e)
        {
            // Handle exceptions related to navigating to the login page
            e.printStackTrace();
            // You might display an error message or log the error here
        }
    }


}
