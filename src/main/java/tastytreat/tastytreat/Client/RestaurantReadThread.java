package tastytreat.tastytreat.Client;

import javafx.application.Platform;

import tastytreat.tastytreat.util.OrderDTO;

public class RestaurantReadThread implements Runnable
{
    Thread thr;
    RestaurantHomeController restaurantHomeController;

    public RestaurantReadThread(RestaurantHomeController restaurantHomeController)
    {
        this.restaurantHomeController = restaurantHomeController;
        this.thr = new Thread(this);
        thr.start();
    }

    @Override
    public void run()
    {

        while (true)
        {
            try
            {
                Object o = restaurantHomeController.resHomecl.getNetworkUtil().read();

                if(o instanceof OrderDTO orderDTO)
                {
                    System.out.println("got the order");
                    Platform.runLater(() -> {
//                        restaurantHomeController.resetTable();
                        restaurantHomeController.updateFoodTable(orderDTO.getOrderList());
                    });
                }
            }
            catch (Exception e)
            {
                System.out.println("Exception in RestaurantReadThread");
            }
        }
    }
}
