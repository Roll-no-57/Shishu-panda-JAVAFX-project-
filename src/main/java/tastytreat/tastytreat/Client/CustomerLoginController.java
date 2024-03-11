package tastytreat.tastytreat.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tastytreat.tastytreat.classes.Restaurant;
import tastytreat.tastytreat.util.DTO;
import tastytreat.tastytreat.util.LoginDTO;

import java.io.IOException;

public class CustomerLoginController
{

    private CustomerMain customer;
   private String path ;

    @FXML
    private TextField userText;

    @FXML
    private ImageView LoginImage;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;


    @FXML
    private Button loginButton;

    void setCustomer(CustomerMain customer) {
        this.customer = customer;
    }

    @FXML
    void loginAction(ActionEvent event) {
        String userName = userText.getText();
        String password = passwordText.getText();
        LoginDTO loginDTO = new LoginDTO();
        System.out.println(userName + " " + password +"wants to login");
        loginDTO.setUserName(userName);
        loginDTO.setPassword(password);

        try {
            // Check if the NetworkUtil object is null
            if (customer.getNetworkUtil() == null) {
                System.out.println("NetworkUtil is null. Please initialize it.");
                return; // Exit the method if it's null
            }

            customer.getNetworkUtil().write("Login");
            customer.getNetworkUtil().write(loginDTO);
            System.out.println("Login request sent to the server");

            Object received = customer.getNetworkUtil().read();
            if (received instanceof LoginDTO) {
                if (((LoginDTO) received).isStatus()) {
                    System.out.println("Login successful");

                    DTO loadedData = (DTO) customer.getNetworkUtil().read();

                    customer.setFoodList(loadedData.getFoodList());
                    customer.setRestaurantList(loadedData.getRestaurantsList());
                    System.out.println("Data loaded  successfully");
                    for(Restaurant food : customer.getRestaurantList()) {
                        System.out.println(food.getId() + " " +food.getName());
                        }

                    try {
                        //getting error
                       customer.showHomePage(loginDTO.getUserName());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in login or reading");
            e.printStackTrace();
        }
    }

    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    void setMain(CustomerMain main, String path) {
        this.customer = main;
        this.path = path;
        Image home = new Image(CustomerMain.class.getResourceAsStream(path));
        LoginImage.setImage(home);
    }
}
