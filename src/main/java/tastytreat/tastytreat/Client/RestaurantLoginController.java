package tastytreat.tastytreat.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tastytreat.tastytreat.util.DTO;
import tastytreat.tastytreat.util.ResLoginDTO;

import java.io.IOException;

public class RestaurantLoginController
{
    private RestaurantMain customer;
    private String path;

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

    void setCustomer(RestaurantMain customer)
    {
        this.customer = customer;
    }

    @FXML
    void loginAction(ActionEvent event)
    {
        String userName = userText.getText();
        String password = passwordText.getText();
        ResLoginDTO ResLoginDTO = new ResLoginDTO();

        System.out.println(userName + " " + password + "  wants to login");
        ResLoginDTO.setUserName(userName);
        ResLoginDTO.setPassword(password);

        try
        {
            // Check if the NetworkUtil object is null
            if (customer.getNetworkUtil() == null)
            {
                System.out.println("NetworkUtil is null. Please initialize it.");
                return; // Exit the method if it's null
            }

            customer.getNetworkUtil().write("ResLogin");
            customer.getNetworkUtil().write(ResLoginDTO.getUserName());
            customer.getNetworkUtil().write(ResLoginDTO);

            System.out.println("Login request sent to the server");
            Object received = null;
            try
            {
                received = customer.getNetworkUtil().read();
            }
            catch (Exception e)
            {
                System.out.println("Error in reading");
                e.printStackTrace();
            }
            if (received instanceof ResLoginDTO)
            {
                if (((ResLoginDTO) received).isStatus())
                {
                    System.out.println("Login successful");
//                    System.out.println("Loading data from server");
                    DTO loadedData = (DTO) customer.getNetworkUtil().read();

                    customer.setFoodList(loadedData.getFoodList());
                    customer.setRestaurantList(loadedData.getRestaurantsList());
                    System.out.println("Data loaded  successfully");
//                    for(Restaurant food : customer.getRestaurantList()) {
//                        System.out.println(food.getId() + " " +food.getName());
//                    }

                    try
                    {
                        //getting error
                        customer.showResClientHomePage(ResLoginDTO.getUserName());
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Error in login or reading");
            e.printStackTrace();
        }
    }

    @FXML
    void resetAction(ActionEvent event)
    {
        userText.setText(null);
        passwordText.setText(null);

    }


    void setMain(RestaurantMain main, String path)
    {
        this.customer = main;
        this.path = path;
        Image home = new Image(CustomerMain.class.getResourceAsStream(path));
        LoginImage.setImage(home);
    }
}
