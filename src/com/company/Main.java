package com.company;

import com.company.Custumer.Custumer;
import com.company.Database.JDBCConnector;
import com.company.players.Player;
import com.company.vehicles.Vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;


    public class Main {
        static Scanner scanner = new Scanner(System.in);

        static List<Vehicle> vehicleList;
        static List<Vehicle> vehicleSelected;
        static List<Custumer> allCustumers;


        public static void main(String[] args) throws SQLException {
           JDBCConnector.connect();
         /*  JDBCConnector.saveCarsToDB();
            JDBCConnector.CustumersDB();*/
            vehicleList = getAllVehicles();
            allCustumers = getAllCustumers();
            vehicleSelected = new ArrayList<>(vehicleList);
            vehicleList.removeAll(vehicleSelected);

            System.out.println("Enter starting money");
            double beginningCash = 0.0;
            while (true) {
                try {
                    beginningCash = scanner.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("error, enter correct number");
                    scanner.next();
                }

            }
            Player mainPlayer = new Player(beginningCash);
            displayMainMenu();
            int answer;
            while (true) {
                try {
                    answer = scanner.nextInt();
                    playTheGame(answer, mainPlayer);
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Choose a proper number");
                }
            }


        }

        static void playTheGame(int answer, Player player) {
            switch (answer) {
                case 1:
                    System.out.println("Displaying available vehicles:");
                    vehicleSelected.forEach(System.out::println);
                    break;
                case 2:
                    buyVehicle(player);
                    break;
                case 3:
                    System.out.println("Displaying all vehicles owned by player:");
                    player.getPlayerVehicles().forEach(System.out::println);
                    break;
                case 4:
                    if (player.getPlayerVehicles().size() == 0) {
                        System.out.println("you don't have any vehicles to repair");
                        break;
                    }
                    System.out.println("Vehicles to repair: ");
                    makeRepairs(player);
                    break;
                case 5:
                    System.out.println("Custumers");
                    allCustumers.forEach(System.out::println);
                    break;
                case 6:
                    System.out.println("Sell a vehicle to potential custumer");
                    sellAVehicle(player);
                    break;
                case 7:
                    System.out.println("You're too good you don't need advertisements");
                case 8:
                    System.out.println("Player's cash:\n" + player.getMoney());
                    break;
                case 9:
                    System.out.println("Check transactions history of player: ");
                    player.getCheckTransactionHistory().forEach(System.out::println);
                    break;
                case 10:
                    System.out.println("Check vehicle's repair history ");
                    for (Vehicle c : player.getPlayerVehicles()) {
                        System.out.println("Repair history for: " + c +
                                "\n" + c.getGetVehicleHistory());
                    }
                    break;
                case 11:
                    System.out.println("Check how much you spent to fix and clean your vehicles ");
                    for (Vehicle c : player.getPlayerVehicles()) {
                        System.out.println("Repair costs for: " + c +
                                "\n" + c.getRepairCost());
                    }
                    break;
                case 12:
                    System.out.println("Thanks for playing :)");
                    return;
                default:
                    System.out.println("Please choose a different number");
            }
            displayMainMenu();
            try {
                answer = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("That's not a valid choice");
            }
            playTheGame(answer, player);
        }

        private static void sellAVehicle(Player player) {
            System.out.println("Choose a vehicle to sell");
            int answer = 0;
            for (int i = 0; i < player.getPlayerVehicles().size(); i++)
                System.out.println(i + ".     " + player.getPlayerVehicles().get(i));

            //Print all of the player's vehicles
            player.getPlayerVehicles().forEach(System.out::println);
            System.out.println("Please type a number to choose a vehicle.");
            while (true) {
                try {
                    answer = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Choose a valid number pls");
                }
                scanner.next();
            }
            Vehicle vehicle  = player.getPlayerVehicles().get(answer);
            List<Custumer> custumers = findPotentialCustumers(vehicle);
            player.sellYourVehicle(vehicle, custumers);
        }

        private static void makeRepairs(Player player) {
            System.out.println("Choose a vehicle to repair");
            int answer = 0;
            for (int i = 0; i < player.getPlayerVehicles().size(); i++)
                System.out.println(i + ".     " + player.getPlayerVehicles().get(i));

            //Print all of the player's vehicles
            player.getPlayerVehicles().forEach(System.out::println);
            System.out.println("Please type a number to choose a vehicle.");
            while (true) {
                try {
                    answer = scanner.nextInt();
                    if ((answer < player.getPlayerVehicles().size()) && answer >= 0)
                        break;
                } catch (InputMismatchException e) {
                    System.out.println("Choose a valid number pls");
                }
                scanner.next();
            }
            player.repairTheVehicle(player.getPlayerVehicles().get(answer));
        }

        private static void buyVehicle(Player player) {
            int answer = 0;
            System.out.println("The vehicles available are:");
            vehicleSelected.forEach(System.out::println);
            System.out.println("Please type 0-4 to choose a vehicle, all decisions are final.");
            while (true) {
                try {
                    answer = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Choose a valid number pls");
                }
                scanner.next();
            }
            if (player.buyNewVehicle(vehicleSelected.get(answer))) {
                vehicleSelected.remove(answer);
                restockVehicles();
            }

        }

        static void restockVehicles() {
            if (vehicleList.size() == 0) {
                System.out.println("You are down to " + vehicleSelected.size() + " vehicles.");
                return;
            }
            for (int i = vehicleSelected.size(); i < 5; i++) {
                Vehicle vehicle = vehicleList.get((int) (Math.random() * vehicleList.size()));
                vehicleSelected.add(vehicle);
                vehicleList.remove(vehicle);
                if (vehicleList.size() == 0) {
                    System.out.println("You are down to " + vehicleSelected.size() + " vehicles.");
                    return;
                }
            }
        }


        static void displayMainMenu() {
            System.out.println("\nMain Menu, choose a number:" +
                    "1 for list of the vehicles you can buy\n" +
                    "2 buy a vehicle\n" +
                    "3 list of owned vehicles\n" +
                    "4 repair a vehicle\n" +
                    "5 view potential custumers\n" +
                    "6 ell a vehicle to a potential custumer\n" +
                    "7 buy an advertising\n" +
                    "8 check your account balance\n" +
                    "9 check transactions history\n" +
                    "10 check a vehicle's repair history \n" +
                    "11 check how much you spent to fix and clean a vehicle.\n" +
                    "12 Quit");
        }

        static List<Vehicle> getAllVehicles() {
            List<Vehicle> vehicles = new ArrayList<>();
            try {
                JDBCConnector.connect();
                String sql = "Select * from \"Vehicles\"";
                ResultSet rs = JDBCConnector.getDataFromTable(sql);
                while (rs.next()) {
                    Vehicle tempVehicle = new Vehicle();
                    tempVehicle.setBrand(rs.getString(1));
                    tempVehicle.setColor(rs.getString(2));
                    tempVehicle.setMileage(rs.getLong(3));
                    tempVehicle.setSegment(rs.getString(4));
                    tempVehicle.setValue(rs.getDouble(5));
                    tempVehicle.setId(rs.getInt(6));
                    vehicles.add(tempVehicle);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return vehicles;
        }

        static List<Custumer> getAllCustumers() {
            List<Custumer> custumers = new ArrayList<>();
            try {
                JDBCConnector.connect();
                String sql = "Select * from \"Custumers\"";
                ResultSet rs = JDBCConnector.getDataFromTable(sql);
                while (rs.next()) {
                    Custumer custumer = new Custumer();
                    custumer.setId(rs.getInt(1));
                    custumer.setName(rs.getString(2));
                    custumer.setSurname(rs.getString(3));
                    custumer.setVehicle((String[]) rs.getArray(4).getArray());
                    custumer.setBrands((String[]) rs.getArray(5).getArray());
                    custumer.setBuyDamaged(rs.getBoolean(6));
                    custumer.setSegment((String[]) rs.getArray(7).getArray());
                    custumer.setMoney(rs.getDouble(8));
                    custumers.add(custumer);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return custumers;
        }

        static List<Custumer> findPotentialCustumers(Vehicle c){
            List<Custumer> potentialCustumers = new ArrayList<>();
            System.out.println("Potential custumers for your: " + c);
            potentialCustumers = allCustumers
                    .stream()
                    .filter(custumer -> custumer.getMoney() > c.getValue())
                    .filter(custumer -> custumer.getBrandsList().contains(c.getBrand()))
                    .filter(custumer -> custumer.getSegmentsList().contains(c.getSegment()))
                    .collect(Collectors.toList());
            if(c.ifBroken())
                potentialCustumers = potentialCustumers
                        .stream()
                        .filter(Predicate.not(Custumer::buyDamaged))
                        .collect(Collectors.toList());
            return potentialCustumers;

        }
    }