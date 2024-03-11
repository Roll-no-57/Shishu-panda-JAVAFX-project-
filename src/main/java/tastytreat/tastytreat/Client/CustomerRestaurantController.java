package tastytreat.tastytreat.Client;

import java.util.List;

import javafx.scene.control.Button;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tastytreat.tastytreat.classes.FoodManager;
import tastytreat.tastytreat.classes.Restaurant;
import tastytreat.tastytreat.classes.RestaurantManager;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;

import javafx.util.Callback;
import tastytreat.tastytreat.util.OrderDTO;


public class CustomerRestaurantController
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
  private ChoiceBox<String> searchbybox;

  @FXML
  private Button OrderButton;
  @FXML
  public void OrderNow(ActionEvent event)
  {

    OrderDTO orderDTO = new OrderDTO(main.getOrderedFoodList());
    try
    {
      // Check if the NetworkUtil object is null
      if (main.getNetworkUtil() == null)
      {
        System.out.println("NetworkUtil is null. Please initialize it.");
        return; // Exit the method if it's null
      }
      main.getNetworkUtil().write("Order");
      main.getNetworkUtil().write(orderDTO);
      System.out.println("Order request sent to the server");
    }
    catch (Exception e)
    {
      // Handle exceptions related to writing to the network
      e.printStackTrace();
      // You might display an error message or log the error here
    }

    main.resetOrderedFoodList();
  }

  @FXML
  public TableView<Restaurant> resTable;

  public TableColumn<Restaurant, String> resIDColumn;
  public TableColumn<Restaurant, String> resNameColumn;
  public TableColumn<Restaurant, String> resPriceColumn;
  public TableColumn<Restaurant, String> resScoreColumn;
  public TableColumn<Restaurant, String> resCategoryColumn;
  public TableColumn<Restaurant, String> resZipColumn;

  @FXML
  private String searchType() {
    return searchbybox.getSelectionModel().getSelectedItem();
  }

  void updateResTable(List<Restaurant> restaurantArrayList) {
    ObservableList<Restaurant> resdata = FXCollections.observableArrayList(
      restaurantArrayList
    );
    resIDColumn.setCellValueFactory(cellData ->
      new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
    );
    resNameColumn.setCellValueFactory(cellData ->
      new SimpleStringProperty(cellData.getValue().getName())
    );
    resCategoryColumn.setCellValueFactory(cellData ->
      new SimpleStringProperty(cellData.getValue().getCategory1())
    );
    resZipColumn.setCellValueFactory(cellData ->
      new SimpleStringProperty(cellData.getValue().getZipCode())
    );
    resPriceColumn.setCellValueFactory(cellData ->
      new SimpleStringProperty(cellData.getValue().getPrice())
    );
    resScoreColumn.setCellValueFactory(cellData ->
      new SimpleStringProperty(String.valueOf(cellData.getValue().getScore()))
    );

    addButtonToTable();

    resTable.setItems(resdata);
  }



  //add button table
  public void addButtonToTable() {
    TableColumn<Restaurant, Void> colBtn = new TableColumn("Restaurant Details");

    Callback<TableColumn<Restaurant, Void>, TableCell<Restaurant, Void>> cellFactory = new Callback<TableColumn<Restaurant, Void>, TableCell<Restaurant, Void>>() {
      @Override
      public TableCell<Restaurant, Void> call(final TableColumn<Restaurant, Void> param) {
        final TableCell<Restaurant, Void> cell = new TableCell<Restaurant, Void>() {
          private final Button btn = new Button("Details");

          {
            //table button action

            btn.setOnAction((ActionEvent event) -> {
              Restaurant restaurant = getTableView().getItems().get(getIndex());
                try {
                    main.showRestDetailPage(userName,restaurant);
                } catch (Exception e) {
                    // Handle exceptions related to navigating to the restaurant detail page
                    e.printStackTrace();
                    // You might display an error message or log the error here
                }
              System.out.println("selectedRestaurant: " + restaurant);

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

    resTable.getColumns().add(colBtn);

  }


  @FXML
  public void init(String msg) {
    userName = msg;
    message.setText(msg);

    try {
      // Attempt to load the image
      Image img = new Image(
        CustomerMain.class.getResourceAsStream("/tastytreat/tastytreat/apurbo.jpg"));
      Image logo = new Image(
        CustomerMain.class.getResourceAsStream("/tastytreat/tastytreat/LOGO.jpg"));
      image.setImage(img);
      CompanyLogo.setImage(logo);
      System.out.println("Image set successfully");
    } catch (Exception e) {
      // Handle any exceptions that occur
      System.out.println("Problem occurred while reading image.");
      e.printStackTrace();
      // You might also display an error message to the user here
    }
    restaurantManager =
      new RestaurantManager(main.getRestaurantList(), main.getFoodList());
    System.out.println("restaurant manager loaded successfully");
    foodManager = new FoodManager(restaurantManager, main.getFoodList());

    // Initialize restaurant manager and food manager
    try {
      searchbybox
        .getItems()
        .addAll("Name", "Category", "Price", "Score", "Zip code","add restaurant");
      updateResTable(main.getRestaurantList());
      totalFoodItem.setText(
        "Total Restaurants  :" + restaurantManager.gettotalRestaurant() + ""
      );

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
      System.out.println("Restaurant page button clicked");
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

  //
  @FXML
  void searchAction(ActionEvent event) {
    String searchInputText = searchInput.getText();
    String searchtype = searchType();
    System.out.println("Search input: " + searchInputText);
    if (searchtype.equals("Name")) {
      try {
        List<Restaurant> foodli = restaurantManager.searchRestaurantByName(
          searchInputText
        );
        //                for(Food food : foodli){
        //                    System.out.println(food.getName());
        //                }
        updateResTable(foodli);
        System.out.println("Updated table after searching by name");
      } catch (Exception e) {
        // Handle exceptions related to searching for food by name
        e.printStackTrace();
        // You might display an error message or log the error here
      }
    }
    if (searchtype.equals("Score")) {
      try {
        String[] words = searchInputText.split(",", 0);
        List<Restaurant> Restaurantli = restaurantManager.searchRestaurantsByScore(
          Double.parseDouble(words[0]),
          Double.parseDouble(words[1])
        );
        //                for(Restaurant Restaurant : Restaurantli){
        //                    System.out.println(Restaurant.getName());
        //                }

        updateResTable(Restaurantli);
        System.out.println("Updated table after searching by Category");
      } catch (Exception e) {
        // Handle exceptions related to searching for food by name
        e.printStackTrace();
        // You might display an error message or log the error here
      }
    }

    if (searchtype.equals("Price")) {
      try {
        //    String [] words= searchInputText.split(",",0);
        List<Restaurant> Restaurantli = restaurantManager.searchRestaurantsByPrice(
          searchInputText
        );
        //                for(Restaurant Restaurant : Restaurantli){
        //                    System.out.println(Restaurant.getName());
        //                }

        updateResTable(Restaurantli);
        System.out.println("Updated table after searching by price");
      } catch (Exception e) {
        // Handle exceptions related to searching for Restaurant by name
        e.printStackTrace();
        // You might display an error message or log the error here
      }
    }

    if (searchtype.equals("Category")) {
      try {
        //    String [] words= searchInputText.split(",",0);

        updateResTable(
          restaurantManager.searchRestaurantsByCategory(searchInputText)
        );
        System.out.println("Updated table after searching by price");
      } catch (Exception e) {
        // Handle exceptions related to searching for Restaurant by name
        e.printStackTrace();
        // You might display an error message or log the error here
      }
    }

    if (searchtype.equals("Zip code")) {
      try {
        //String [] words= searchInputText.split(",",0);

        updateResTable(
          restaurantManager.searchRestaurantsByZipCode(searchInputText)
        );
        System.out.println("Updated table after searching by price");
      } catch (Exception e) {
        // Handle exceptions related to searching for food by name
        e.printStackTrace();
        // You might display an error message or log the error here
      }
    }
    if (searchtype.equals("add restaurant")) {
      try {
        String [] words= searchInputText.split(",",0);

        Restaurant res = new Restaurant(Integer.parseInt(words[0]),words[1],Double.parseDouble(words[2]),words[3],words[4],words[5],words[6],words[7]);
        restaurantManager.addRestaurant(res);
        System.out.println("Updated table after searching by price");
      } catch (Exception e) {
        // Handle exceptions related to searching for food by name
        e.printStackTrace();
        // You might display an error message or log the error here
      }
    }
  }
}
