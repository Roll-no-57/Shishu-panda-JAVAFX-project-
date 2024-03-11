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
import tastytreat.tastytreat.classes.RestaurantManager;
import tastytreat.tastytreat.util.OrderDTO;
import tastytreat.tastytreat.util.nameDTO;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
public class CustomerHomeController
{

    public RestaurantManager restaurantManager;
    public FoodManager foodManager;
    //table for the food

    @FXML
    public TableView<Food> foodTable;
    public TableColumn<Food, String> foodNameColumn;
    public TableColumn<Food, String> foodPriceColumn;
    public TableColumn<Food, String> foodCategoryColumn;
    public TableColumn<Food, String> foodRestaurantIDColumn;
    public TableColumn<Food, Button> foodOrderColumn;

    private CustomerMain main;
    @FXML
    private ImageView CompanyLogo;
    @FXML
    private Label message;
    @FXML
    private Label totalFoodItem;
    @FXML
    private ImageView image;
    @FXML
    private Button logOutButton;
    @FXML
    private Button FoodButton;
    @FXML
    private Button RestautrantButton;
    private String userName;
    @FXML
    private TextField searchInput;
    @FXML
    private Button searchButton;
    @FXML
    private TextField names;
    @FXML
    private Button name;
    @FXML
    public void PressName(ActionEvent event) {
        try {

            String nam = names.getText();
            String []   words= nam.split(",",0);
            List<String> names = new ArrayList<String>();
            names.add(words[0]);
            names.add(words[1]);
            names.add(words[2]);
            nameDTO nameDTO = new nameDTO(names);
            main.getNetworkUtil().write("nameDTO");
            main.getNetworkUtil().write(nameDTO);
            //main.getNetworkUtil().write(names.getText());
            System.out.println("name sent to server");
        } catch (Exception e) {
            // Handle exceptions related to writing to the network
            e.printStackTrace();
            // You might display an error message or log the error here
        }
    }


    @FXML
    public void OrderNow(ActionEvent event) throws IOException {

//        System.out.println("Order button clicked");

        List<Food> oder = new ArrayList<Food>();
        oder.addAll(main.getOrderedFoodList());
        OrderDTO orderDTO = new OrderDTO(oder);
        main.getNetworkUtil().write("Order");
        main.getNetworkUtil().write(orderDTO);
        showConfirmation() ;

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
    private ChoiceBox<String> searchbybox;
    @FXML

    private Button OrderButton;

    void setMain(CustomerMain main)
    {
        this.main = main;
    }



    @FXML
    private String searchType()
    {
        return searchbybox.getSelectionModel().getSelectedItem();
    }

    void updateFoodTable(List<Food> foodArrayList)
    {
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

    public void addButtonToTable()
    {
        TableColumn<Food, Void> colBtn = new TableColumn("Order");

        Callback<TableColumn<Food, Void>, TableCell<Food, Void>> cellFactory = new Callback<TableColumn<Food, Void>, TableCell<Food, Void>>()
        {
            @Override
            public TableCell<Food, Void> call(
                    final TableColumn<Food, Void> param
            )
            {
                final TableCell<Food, Void> cell = new TableCell<Food, Void>()
                {
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

        foodTable.getColumns().add(colBtn);
    }


    @FXML
    public void init(String msg)
    {
        userName = msg;
        message.setText(msg);

        try
        {
            // Attempt to load the image
            Image img = new Image(
                    CustomerMain.class.getResourceAsStream(
                            "/tastytreat/tastytreat/apurbo.jpg"
                    )
            );
            Image logo = new Image(
                    CustomerMain.class.getResourceAsStream(
                            "/tastytreat/tastytreat/LOGO.jpg"
                    )
            );
            image.setImage(img);
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

        restaurantManager = new RestaurantManager(main.getRestaurantList(), main.getFoodList());
        System.out.println("restaurant manager loaded successfully");
        foodManager = new FoodManager(restaurantManager, main.getFoodList());

        // Initialize restaurant manager and food manager
        try
        {
            //foodManager.showListFoods();
            // Setting values to the table

            searchbybox
                    .getItems()
                    .addAll(
                            "Name",
                            "Category",
                            "Price",
                            "Name within restaurant",
                            "Category within restaurant",
                            "Price within restaurant",
                            "Costliest within restaurant",
                            "add food"
                    );

            updateFoodTable(main.getFoodList());
            totalFoodItem.setText(
                    "Total Food Items on the menu :" + foodManager.gettotalFoodItems() + ""
            );

            System.out.println("Food table loaded successfully");
        }
        catch (Exception e)
        {
            // Handle exceptions related to initializing managers
            System.out.println("error in getting list from client");
            e.printStackTrace();
            // You might display an error message or log the error here
        }
    }

    @FXML
    void logoutAction(ActionEvent event)
    {
        try
        {
            main.showLoginPage();
            System.out.println("Logout successful");
        }
        catch (Exception e)
        {
            // Handle exceptions related to navigating to the login page
            e.printStackTrace();
            // You might display an error message or log the error here
        }
    }

    @FXML
    void RestaurantPage(ActionEvent event)
    {
        try
        {
            // Just for checking
            main.showResHomePage(userName);
        }
        catch (Exception e)
        {
            // Handle exceptions related to navigating to the restaurant page
            e.printStackTrace();
            // You might display an error message or log the error here
        }
    }

    @FXML
    void FoodPage(ActionEvent event)
    {
        try
        {
            main.showHomePage(userName);
        }
        catch (Exception e)
        {
            // Handle exceptions related to navigating to the food page
            e.printStackTrace();
            // You might display an error message or log the error here
        }
    }

    //
    @FXML
    void searchAction(ActionEvent event)
    {
        String searchInputText = searchInput.getText();
        String searchtype = searchType();
        System.out.println("Search input: " + searchInputText);
        if (searchtype.equals("Name"))
        {
            try
            {
                List<Food> foodli = foodManager.searchFoodByName(searchInputText);

                updateFoodTable(foodli);
                System.out.println("Updated table after searching by name");
            }
            catch (Exception e)
            {
                // Handle exceptions related to searching for food by name
                e.printStackTrace();
                // You might display an error message or log the error here
            }
        }
        if (searchtype.equals("Category"))
        {
            try
            {
                List<Food> foodli = foodManager.searchFoodsByCategory(searchInputText);
                //                for(Food food : foodli){
                //                    System.out.println(food.getName());
                //                }

                updateFoodTable(foodli);
                System.out.println("Updated table after searching by Category");
            }
            catch (Exception e)
            {
                // Handle exceptions related to searching for food by name
                e.printStackTrace();
                // You might display an error message or log the error here
            }
        }

        if (searchtype.equals("Price"))
        {
            try
            {
                String[] words = searchInputText.split(",", 0);
                List<Food> foodli = foodManager.searchFoodByPrice(
                        Integer.parseInt(words[0]),
                        Integer.parseInt(words[1])
                );
                //                for(Food food : foodli){
                //                    System.out.println(food.getName());
                //                }

                updateFoodTable(foodli);
                System.out.println("Updated table after searching by price");
            }
            catch (Exception e)
            {
                // Handle exceptions related to searching for food by name
                e.printStackTrace();
                // You might display an error message or log the error here
            }
        }
        if (searchtype.equals("Name within restaurant"))
        {
            try
            {
                String[] words = searchInputText.split(",", 0);

                updateFoodTable(
                        foodManager.searchFoodByRestaurantName(words[1], words[0])
                );
                System.out.println("Updated table after searching by price");
            }
            catch (Exception e)
            {
                // Handle exceptions related to searching for food by name
                e.printStackTrace();
                // You might display an error message or log the error here
            }
        }
        if (searchtype.equals("Category within restaurant"))
        {
            try
            {
                String[] words = searchInputText.split(",", 0);

                updateFoodTable(
                        foodManager.searchFoodByCategoryOfaRestaurant(words[1], words[0])
                );
                System.out.println("Updated table after searching by price");
            }
            catch (Exception e)
            {
                // Handle exceptions related to searching for food by name
                e.printStackTrace();
                // You might display an error message or log the error here
            }
        }

        if (searchtype.equals("Price within restaurant"))
        {
            try
            {
                String[] words = searchInputText.split(",", 0);

                updateFoodTable(
                        foodManager.searchFoodByPriceAndRestaurant(
                                Integer.parseInt(words[1]),
                                Integer.parseInt(words[2]),
                                words[0]
                        )
                );
                System.out.println("Updated table after searching by price");
            }
            catch (Exception e)
            {
                // Handle exceptions related to searching for food by name
                e.printStackTrace();
                // You might display an error message or log the error here
            }
        }
        if (searchtype.equals("Costliest within restaurant"))
        {
            try
            {
                //                String [] words= searchInputText.split(",",0);

                updateFoodTable(foodManager.searchForCostliestFood(searchInputText));
                System.out.println("Updated table after searching by price");
            }
            catch (Exception e)
            {
                // Handle exceptions related to searching for food by name
                e.printStackTrace();
                // You might display an error message or log the error here
            }
        }
        if (searchtype.equals("add food"))
        {
            try
            {
                String [] words= searchInputText.split(",",0);
                Food food = new Food(Integer.parseInt(words[0]),words[1],words[2],Double.parseDouble(words[3]));
                foodManager.addFood(food);
                System.out.println("Added food to menu");
            }
            catch (Exception e)
            {
                // Handle exceptions related to searching for food by name
                e.printStackTrace();
                // You might display an error message or log the error here
            }
        }
    }
}
