package tastytreat.tastytreat.util;


import tastytreat.tastytreat.classes.Food;
import tastytreat.tastytreat.classes.Restaurant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FileManager {

    //input files name
    private static final String INPUT_RES_FILE_NAME = "src\\main\\resources\\tastytreat\\tastytreat\\restaurant.txt";
    private static final String INPUT_FOOD_MENU_FILE_NAME = "src\\main\\resources\\tastytreat\\tastytreat\\menu.txt";
    private static final String INPUT_PASSWORD_FILE_NAME = "src\\main\\resources\\tastytreat\\tastytreat\\password.txt";
    private static final String INPUT_RES_PASSWORD_FILE_NAME = "src\\main\\resources\\tastytreat\\tastytreat\\Respassword.txt";

    //read data of restaurant
    public static List<Restaurant> loadsDataOfRestaurant() throws Exception {

        List<Restaurant> list = new ArrayList<Restaurant>();

        BufferedReader br = new BufferedReader(new FileReader(INPUT_RES_FILE_NAME));

        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            String[] array = line.split(",", 0);

            int id = Integer.parseInt(array[0]);
            String name = array[1];
            double score = Double.parseDouble(array[2]);
            String price = array[3];
            String zipCode = array[4];
            String category1 = array[5];
            String category2 = (array.length >= 7) ? array[6] : "";
            String category3 = (array.length >= 8) ? array[7] : "";
            Restaurant r = new Restaurant(id, name, score, price, zipCode, category1, category2, category3);
            list.add(r);
        }
        br.close();
        // for (Restaurant r : list) {
        //   r.showDetailsOfRestaurant();
        // }
        return list;
    }


    //read data of food
    public static List<Food> loadDataForFoods() throws Exception {
        List<Food> list = new ArrayList<Food>();

        BufferedReader br = new BufferedReader(new FileReader(INPUT_FOOD_MENU_FILE_NAME));

        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }

            String[] array = line.split(",", 0);

            int restaurantId = Integer.parseInt(array[0]);
            String category = array[1];
            String name = array[2];
            double price = Double.parseDouble(array[3]);

            Food food = new Food(restaurantId, category, name, price);
            list.add(food);
        }
        br.close();

        return list;
    }


    //saves the restaurant updated data
    public static void saveAndUpdateRestaurants(List<Restaurant> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INPUT_RES_FILE_NAME))) {
            for (Restaurant r : list) {
                String line = String.format("%d,%s,%.2f,%s,%s,%s,%s,%s", r.getId(), r.getName(), r.getScore(), r.getPrice(), r.getZipCode(), r.getCategory1(), r.getCategory2(), r.getCategory3());
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //saves the updated food data
    public static void saveAndUpdateFoods(List<Food> list) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(INPUT_FOOD_MENU_FILE_NAME))) {

            for (Food f : list) {
                String line = String.format("%d,%s,%s,%.2f", f.getRestaurantId(), f.getCategory(), f.getName(), f.getPrice());
                w.write(line);
                w.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //read from the password file
    public static ConcurrentHashMap<String, String> readPasswordsFromFile() throws IOException {

        ConcurrentHashMap<String, String> passwordList = new ConcurrentHashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(INPUT_PASSWORD_FILE_NAME));

        while (true) {
            String line = br.readLine();
            if (line == null) break;

            String name;
            String password;

            String[] properties = line.split(",", 0);

            name = properties[0];
            password = properties[1];

            if (name.length() > 0) {
                passwordList.put(name, password);
            }
        }
        br.close();

        return passwordList;
    }
    public static ConcurrentHashMap<String, String> readResPasswordsFromFile() throws IOException {

        ConcurrentHashMap<String, String> passwordList = new ConcurrentHashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(INPUT_RES_PASSWORD_FILE_NAME));

        while (true) {
            String line = br.readLine();
            if (line == null) break;

            String name;
            String password;

            String[] properties = line.split(",", 0);

            name = properties[0];
            password = properties[1];

            if (name.length() > 0) {
                passwordList.put(name, password);
            }
        }
        br.close();

        return passwordList;
    }
    //write the password after updating the password
    public static void writePasswordsToFile(ConcurrentHashMap<String, String> passwordList) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(INPUT_PASSWORD_FILE_NAME));

        for (String key : passwordList.keySet()) {
            String text = key + "," + passwordList.get(key);
            //System.out.println(text);
            bw.write(text);
            bw.write(System.lineSeparator());
        }

        bw.close();

    }



}
