package tastytreat.tastytreat.server;

import tastytreat.tastytreat.classes.Food;
import tastytreat.tastytreat.util.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ServerReadThread implements Runnable
{
    private final Thread thr;
    private final NetworkUtil networkUtil;
    public ConcurrentHashMap<String, String> PasswordList;
    public ConcurrentHashMap<String, String> resPasswordList;
    public ConcurrentHashMap<String, NetworkUtil> clientMap = new ConcurrentHashMap<>();
    Server server;




    private void showClientmap(){
        for(String key : clientMap.keySet()){
            System.out.println(key);
        }

    }


    public ServerReadThread(Server server, NetworkUtil networkUtil, ConcurrentHashMap<String, NetworkUtil> clientmap)
    {
        this.server = server;
        this.clientMap = clientmap;
        this.PasswordList = server.getPasswordList();
        this.resPasswordList = server.getresPasswordList();
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run()
    {
        try
        {
            while (true)
            {
//              System.out.println("got the request from client");
                Object recieved = networkUtil.read();

                    if (recieved.equals("Login"))
                    {
//                        System.out.println("got the request for login from client");
                        Object o = networkUtil.read();
                        if (o instanceof LoginDTO)
                        {
//                            System.out.println("got the request for login from client");
                            LoginDTO loginDTO = (LoginDTO) o;
//                          DTO data = (DTO) o;
//                            server.showPasswordList();
                            String password = PasswordList.get(loginDTO.getUserName());
                            System.out.println(loginDTO.getUserName() + " wants to login " + loginDTO.getPassword());
//                            System.out.println("checking password");

                            loginDTO.setStatus(loginDTO.getPassword().equals(password));
//                        System.out.println("LoginDTO.isStatus()");
//                            if (loginDTO.isStatus()) System.out.println("password matched");
//                            else System.out.println("password didn't match");

                            networkUtil.write(loginDTO);
                            if (loginDTO.isStatus())
                            {
//                                System.out.println("Authentication successful");
                                DTO data = new DTO(server.getFoodList(), server.getRestaurantsList());
                                networkUtil.write(data);
//                                System.out.println("data sent to client successfully");
                            }
                            else
                            {
                                System.out.println("Authentication failed");
                            }

                        }
                    }
                    if (recieved.equals("ResLogin"))
                    {
//                        System.out.println("got the request for login from Restaurant");
                        String id = (String) networkUtil.read();
                        clientMap.put(id, networkUtil);
                        System.out.println("got the request for login from Restaurant "+id);
                        Object o = networkUtil.read();
                        if (o instanceof ResLoginDTO)
                        {
//                            System.out.println("got the request for login from Restaurant");
                            ResLoginDTO ResLoginDTO = (ResLoginDTO) o;
//                        DTO data = (DTO) o;
                            // server.showPasswordList();
                            String respassword = resPasswordList.get(ResLoginDTO.getUserName());

                            System.out.println(ResLoginDTO.getUserName() + " wants to login" + ResLoginDTO.getPassword());
//                            System.out.println("checking password");

                            ResLoginDTO.setStatus(ResLoginDTO.getPassword().equals(respassword));
//                        System.out.println("ResLoginDTO.isStatus()");
//                            if (ResLoginDTO.isStatus()) System.out.println("password matched");
//                            else System.out.println("password didn't match");

                            networkUtil.write(ResLoginDTO);
                            if (ResLoginDTO.isStatus())
                            {
//                                System.out.println("Authentication successful");
                                DTO data = new DTO(server.getFoodList(), server.getRestaurantsList());
                                networkUtil.write(data);
//                                System.out.println("data sent to client successfully");
                            }
                            else
                            {
                                System.out.println("Authentication failed");
                            }
                        }
                    }

                if (recieved.equals("Order"))
                {
                    Object o = networkUtil.read();
                    if (o instanceof OrderDTO)
                    {
                        OrderDTO orderDTO = (OrderDTO) o;
                        System.out.println("got the orderDTO");
                        for(Food food : orderDTO.getOrderList()){
                            System.out.println(food.getName());
                        }

                         showClientmap();
                        System.out.println("Username   " + orderDTO.getUserName() + "  Orders the foods to " +  orderDTO.getId());
                        //orderDTO.showOrderList();
                            NetworkUtil nu = clientMap.get(orderDTO.getId());

                            if (nu != null)
                            {
                                System.out.println("sending order to restaurant");
                                nu.write(orderDTO);
                            }
                    }

                }

                    if(recieved.equals("nameDTO")){
                        Object o = networkUtil.read();
                        if(o instanceof nameDTO){
                            nameDTO nameDTO = (nameDTO) o;
                            System.out.println("got the nameDTO");
                            for(String name : nameDTO.getNames()){
                                System.out.println(name);
                            }

                        }
                    }


            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        } finally
        {
            try
            {
                networkUtil.closeConnection();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}



