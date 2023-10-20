package com.example.demo;
import java.util.ArrayList;
import java.util.List;

public class GenerateMeals {

    public static List<String> used = new ArrayList<>();

    
    public List<String> allMeals(String meal) {
        List<String> bf = new ArrayList<>();
        List<Integer> elgible = new ArrayList<>();

        for (int i = 0; i < Caller.type.size(); i++) {
            if (Caller.type.get(i).contains(meal)) {
                elgible.add(i);
            }
        }

        double allocatedCal = 1200;
        double allocatedProtein = 50;
        double allocatedCarbs = 66.6;
        double allocatedFat = 26.6;
        
        double calPercent = 1.0;
        double proteinPercent = 1.0;
        double carbPercent = 1.0;
        double fatPercent = 1.0;
        boolean done = false;
        int target = 0;

        while (!done) {

            int calMax = 0;
            double otherMax = 0;
            int index = 0;

            if (target == 0) { //calories

                calMax = 0;
                index = -1;
                for (int i : elgible) {
                    if ((Caller.calories.get(i) > calMax) && (Caller.calories.get(i) < (allocatedCal * calPercent)) && (Caller.carbs.get(i) < (allocatedCarbs * carbPercent)) && (Caller.protein.get(i) < (allocatedProtein * proteinPercent)) && (Caller.fat.get(i) < (allocatedFat * fatPercent)) && !(used.contains(Caller.names.get(i)))) {
                        calMax = Caller.calories.get(i);
                        index = i;
                    }
                }
                if (index == -1) {
                    done = true;
                    break;
                } else {
                bf.add(Caller.names.get(index));
                used.add(Caller.names.get(index));
                }
                //System.out.println("ADDED: " + Caller.names.get(index));

            } else if (target == 1) {  //protein

                otherMax = 0;
                index = -1;
                for (int i : elgible) {
                    if ((Caller.protein.get(i) > otherMax) && (Caller.protein.get(i) < (allocatedProtein * proteinPercent)) && (Caller.carbs.get(i) < (allocatedCarbs * carbPercent)) && (Caller.calories.get(i) < (allocatedCal * calPercent)) && (Caller.fat.get(i) < (allocatedFat * fatPercent))) {
                        otherMax = Caller.protein.get(i);
                        index = i;
                    }
                }
                if (index == -1) {
                    done = true;
                    break;
                } else {
                bf.add(Caller.names.get(index));
                used.add(Caller.names.get(index));
                }
                //System.out.println("ADDED: " + Caller.names.get(index));

            } else if (target == 2) {  //carbs

                otherMax = 0;
                index = -1;
                for (int i : elgible) {
                    if ((Caller.carbs.get(i) > otherMax) && (Caller.carbs.get(i) < (allocatedCarbs * carbPercent)) && (Caller.protein.get(i) < (allocatedProtein * proteinPercent)) && (Caller.calories.get(i) < (allocatedCal * calPercent)) && (Caller.fat.get(i) < (allocatedFat * fatPercent))) {
                        otherMax = Caller.carbs.get(i);
                        index = i;
                    }
                }
                if (index == -1) {
                    done = true;
                    break;
                } else {
                bf.add(Caller.names.get(index));
                used.add(Caller.names.get(index));
                }
                //System.out.println("ADDED: " + Caller.names.get(index));

              } else if (target == 3) {  //fat

                otherMax = 0;
                index = -1;
                for (int i : elgible) {
                    if ((Caller.fat.get(i) > otherMax) && (Caller.fat.get(i) < (allocatedFat * fatPercent)) && (Caller.protein.get(i) < (allocatedProtein * proteinPercent)) && (Caller.calories.get(i) < (allocatedCal * calPercent)) && (Caller.carbs.get(i) < (allocatedCarbs * carbPercent))) {
                        otherMax = Caller.fat.get(i);
                        index = i;
                    }
                }
                if (index == -1) {
                    done = true;
                    break;
                } else {
                bf.add(Caller.names.get(index));
                used.add(Caller.names.get(index));
                }
                //System.out.println("ADDED: " + Caller.names.get(index));

            } else {
                done = true;
            }
            
            calPercent = (((calPercent * allocatedCal) - Caller.calories.get(index)) / allocatedCal);
            proteinPercent = (((proteinPercent * allocatedProtein) - Caller.protein.get(index)) / allocatedProtein);
            carbPercent = (((carbPercent * allocatedCarbs) - Caller.carbs.get(index)) / allocatedCarbs);
            fatPercent = (((fatPercent * allocatedFat) - Caller.fat.get(index)) / allocatedFat);

            //System.out.println("cal:" + calPercent + " protein:" + proteinPercent + " carb:" + carbPercent + " fat:" + fatPercent);
            target = getTarget(calPercent, proteinPercent, carbPercent, fatPercent);

        }

        


        return bf;
    }

    public int getTarget(double cal, double protein, double carb, double fat) {

            double max = Math.max(Math.max(Math.max(cal, protein), carb), fat);
            if (max < .10) {
                System.out.println("Exiting (max = " + max + ")");
                return -1;
            }

            if (max == cal) {
                return 0;
            } else if (max == protein) {
                return 1;
            } else if (max == carb) {
                return 2;
            } else if (max == fat) {
                return 3;
            }
            return -1;
        }






}
