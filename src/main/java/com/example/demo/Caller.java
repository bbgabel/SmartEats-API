package com.example.demo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Caller {
    private Connection connection;

    public static ArrayList<String> names = new ArrayList<>();
    public static ArrayList<Integer> calories = new ArrayList<>();
    public static ArrayList<Double> protein = new ArrayList<>();
    public static ArrayList<Double> carbs = new ArrayList<>();
    public static ArrayList<Double> fat = new ArrayList<>();
    public static ArrayList<String> servingSize = new ArrayList<>();
    public static ArrayList<String> type = new ArrayList<>();
    
    public Caller() {
        try {
            String url = "jdbc:sqlite:/Users/bbgabel/smarteats-db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void useData(Calculations calculator) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM data");


            while (resultSet.next()) {
                names.add(resultSet.getString("Name"));
                calories.add(resultSet.getInt("Calories"));
                protein.add(resultSet.getDouble("Protein"));
                carbs.add(resultSet.getDouble("Carbs"));
                fat.add(resultSet.getDouble("Fat"));
                servingSize.add(resultSet.getString("Serving"));
                type.add(resultSet.getString("Type"));

            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        GenerateMeals gen = new GenerateMeals();
        List<String> bf = gen.allMeals("B", calculator);
        List<String> l = gen.allMeals("L", calculator);
        List<String> d = gen.allMeals("D", calculator);
        System.out.println(bf);
        System.out.println(l);
        System.out.println(d);

        int totalCal = 0;

        for (String i : bf) {
            int index = names.indexOf(i);
            totalCal += calories.get(index);
        }
        for (String i : l) {
            int index = names.indexOf(i);
            totalCal += calories.get(index);
        }
        for (String i : d) {
            int index = names.indexOf(i);
            totalCal += calories.get(index);
        }
        System.out.println("Goal: " + calculator.userCals +  ", Achieved: " + totalCal);

    }

}
