package com.company.players;
import com.company.Custumer.Custumer;
import com.company.vehicles.Vehicle;
import com.company.vehicles.carParts;

import java.util.*;
import java.util.stream.Collectors;

public class Player {
    private double money;

    public List<Vehicle> getPlayerVehicles() {
        return playerVehicles;
    }

    private List<Vehicle> playerVehicles;
    private List<String> checkTransactionHistory;

    public Player(double money) {
        this.money = money;
 
        playerVehicles= new ArrayList<>();
        checkTransactionHistory = new ArrayList<>();
    }

    public void plusMoney(double dolars) {
        if (dolars > 0)
            money += dolars;
        System.out.println("cash " + money);
    }

    public void minusMoney(double dolars) {
        if (dolars > 0)
            money -= dolars;
        System.out.println("cash = " + money);
    }

    public double getMoney() {
        return money;
    }


    public List<String> getCheckTransactionHistory() {
        return checkTransactionHistory;
    }

    public void repairTheVehicle(Vehicle vehicle) {
        Scanner scanner= new Scanner(System.in);
        double repairPrice = 0;
        List<carParts> partsToFix = vehicle.getCarPartsList()
                .stream()
                .filter(carParts::ifBroken)
                .collect(Collectors.toList());
        System.out.println("Parts to fix ");
        partsToFix.forEach(System.out::println);
        System.out.println(" Fix at JanuszVehicles, prices are highest, but you receive 100% guarantee of professional repair.\n" +
                "Fix at  MarianAuto, mid prices, but you have 10% chance to fail and you will have to go to Janusz and pay again,\n" +
                "Fix at PPHU Adrian, the cheapest, there is 20% chance to fail and 2% chance to ruin another element of the vehicle.");

        System.out.println("1)Janusz");
        System.out.println("2)MarianAuto");
        System.out.println("3)PPHU Adrian");

        int a;
        while (true) {
            a = scanner.nextInt();
            if ((a > 3) || (a < 1)) {
                System.out.println(" pick 1-3");
                continue;
            }
            repairPrice = getQuote(a, vehicle );
            System.out.println(" money deducted: " + repairPrice);
            break; }
        vehicle.upValue(partsToFix);
        minusMoney(repairPrice);
        vehicle.getGetVehicleHistory().add("You fixed: " + partsToFix.toString());
        vehicle.upRepairCost(repairPrice);
        partsToFix.forEach(carParts::fixBroken);
        System.out.println("After repairs, you are left with " + getMoney());


    }
    private double getPriceForParts(Vehicle vehicle) {
        double repairCost = 0.0;
        for (carParts p : vehicle.getCarPartsList()) {
            if (p.ifBroken()) {
                switch (p.getName()) {
                    case "Brakes":
                        repairCost += 200;
                        break;
                    case "Dampers":
                        repairCost += 20;
                        break;
                    case "Engine":
                        repairCost += 3000;
                        break;
                    case "Vehicle Body":
                        repairCost += 666;
                        break;
                    case "Gear Box":
                        repairCost += 555;
                        break;
                }
            }
        }
        return repairCost;
    }

    private double getQuote(int repairShop, Vehicle vehicle) {
        double repairPrice = 0;
        double multiplier = 0.0;
        double shopMarge = 0.0;
        double cleaningPrice = 50.0;
        shopMarge = getPriceForParts(vehicle);

        switch (vehicle.getSegment()) {
            case "Standard":
                multiplier = 1.1;
                break;
            case "Budget":
                multiplier = 1.05;
            case "Premium":
                multiplier = 1.5;
        }

        switch (repairShop) {
            case 1:
                shopMarge *= 1.5;
                break;
            case 2:
                shopMarge *= 1.25;
                if (Math.random() * 11 == 1) {
                    shopMarge *= 1.5;
                    System.out.println("MarianAuto failed");
                }
                break;
            case 3:
                shopMarge *= 1.15;
                if (Math.random() * 11 < 3) {
                    if (Math.random() * 101 < 3) {
                        System.out.println("PPHU Adrian failed");
                        List<carParts> p = vehicle.getCarPartsList()
                                .stream()
                                .filter(parts -> !parts.ifBroken())
                                .collect(Collectors.toList());
                        int index = (int) (Math.random() * (p.size() + 1));
                        vehicle.getCarPartsList().get(index).fixBroken();
                        shopMarge = getPriceForParts(vehicle);
                    }
                    shopMarge *= 1.5;
                    System.out.println("Fail");
                }
                break;
        }


        return shopMarge * multiplier + shopMarge + cleaningPrice;
    }

    public boolean buyNewVehicle(Vehicle vehicle){
        double price = vehicle.getValue()*1.02;
        if(playerVehicles.contains(vehicle)){
            System.out.println("Pick a diffrent vehicle");
            return false;
        }
        System.out.println( price + " for vehicle.");
        minusMoney(price);
        playerVehicles.add(vehicle);
        checkTransactionHistory.add("Purchased: " + vehicle);
        return true;
    }

    public boolean sellYourVehicle(Vehicle vehicle, List<Custumer> custumers){

        double price = vehicle.getValue()*1.02;

        if(!playerVehicles.contains(vehicle)){
            System.out.println("Cant sell that vehicle");
            return false;
        }

        System.out.println("0 sell to bank, which custumer u want to sell to?");
        Scanner scanner = new Scanner(System.in);
        int answer = 0;

        while(true){
            try{
                answer = scanner.nextInt();
                if(answer < 0 || answer > custumers.size())
                    System.out.println("Please enter a valid number");
                else
                    break;
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid number");
            }
        }

        Custumer custumer = new Custumer();
        if(answer != 0){
            answer --;
            custumer = custumers.get(answer);
            custumer.minusMoney(price);
            System.out.println("Selling vehicle" + price + " to " + custumer.getName());
            checkTransactionHistory.add("You sold " + vehicle + " to " + custumer + " for " + price);
            plusMoney(price);
        }
        else{
            price *= 0.75;
            System.out.println("Selling this vehicle for " + price * 0.75  + " to the bank.");
            checkTransactionHistory.add("You sold " + vehicle + " to the bank for " + price);
            plusMoney(price);
        }

        checkTransactionHistory.add("You sold: " + vehicle);
        return true;
    }


}