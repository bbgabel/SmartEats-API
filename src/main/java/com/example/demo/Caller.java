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
    Calculations calc;
    
    public Caller() {
        try {
            String url = "jdbc:sqlite:/Users/bbgabel/smarteats-db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void useData(Calculations calculator) {
        calc = calculator;
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
        List<String> bf;
        List<String> l;
        List<String> d;
        System.out.println(names.size());

            bf = gen.allMeals("B", calculator);
            l = gen.allMeals("L", calculator);
            d = gen.allMeals("D", calculator);

            int test = 0;
            while (!checkMargin(bf)) {
                bf = gen.allMeals("B", calculator);
                test++;
            }
            System.out.println("(breakfast) Algorithm completed with [" + test + "] attemps!");
            System.out.println(bf);

            
            test = 0;
            
            while (!checkMargin(l)) {
                bf = gen.allMeals("L", calculator);
                test++;
            }
            System.out.println("(lunch) Algorithm completed with [" + test + "] attemps!");
            


            System.out.println(bf);
            System.out.println(l);


    }

    public boolean checkMargin(List<String> meal) {

        int totalCal = 0;
        int totalP = 0;
        int totalC = 0;
        int totalF = 0;
        boolean validCal = false;
        boolean validP = false;
        boolean validC = false;
        boolean validF = false;

        for (String i : meal) {
            int index = names.indexOf(i);
            totalCal += calories.get(index);
            totalP += protein.get(index);
            totalC += carbs.get(index);
            totalF += fat.get(index);
        }

        if ((totalCal / (calc.userCals / 3)) < 1.2 && (totalCal / (calc.userCals / 3)) > .8) {
            validCal = true;
        }
        if ((totalP / (calc.protein / 3)) < 1.2 && (totalP / (calc.protein / 3)) > .8) {
            validP = true;
        }
        if ((totalC / (calc.carbs / 3)) < 1.2 && (totalC / (calc.carbs / 3)) > .8) {
            validC = true;
        }
        if ((totalF / (calc.fat / 3)) < 1.2 && (totalF / (calc.fat / 3)) > .8) {
            validF = true;
        }

        if (validCal && validP && validC && validF) {
            System.out.println("Calories acheived: " + totalCal + " | desired: " + calc.userCals / 3);
            System.out.println("Protein acheived: " + totalP + " | desired: " + calc.protein / 3);
            System.out.println("Carbs acheived: " + totalC + " | desired: " + calc.carbs / 3);
            System.out.println("Fat acheived: " + totalF + " | desired: " + calc.fat / 3);
            return true;
        } else {
            return false;
        }

    }

    

}
