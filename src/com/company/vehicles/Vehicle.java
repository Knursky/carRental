package com.company.vehicles;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private String brand;
    private String color;
    private long mileage;
    private String segment;
    private double value;
    private int id;




    private final List<carParts> carPartsList;


    private List<String> getVehicleHistory;
    private double repairCost;

    public Vehicle(String brand, String color, long mileage, String segment, double value, int id) {
        this();
        this.brand = brand;
        this.color = color;
        this.mileage = mileage;
        this.segment = segment;
        this.value = value;
        this.id = id;
    }

    public Vehicle() {
        carPartsList = new ArrayList<>(List.of(
                new carParts("Brakes"),
                new carParts("Dampers"),
                new carParts("Engine"),
                new carParts("Car Body"),
                new carParts("Gearbox")));
        getVehicleHistory = new ArrayList<>();
    }

    public double getValue() {
        return value;
    }

    public void upValue(double value) {
        if (value > 0)
            this.value += value;
    }

    public void upValue(List<carParts> carparts) {
        for (carParts carpart  : carparts ) {
            switch (carpart.getName()) {
                case "Brakes":
                    upValue(value * 0.1);
                    break;
                case "Dampers":
                    upValue(value * 0.2);
                    break;
                case "Engine":
                    upValue(value);
                    break;
                case "Car body":
                case "Gearbox":
                    upValue(value * 0.5);
            }
        }
    }

    public void minusValue(double value) {
        if (value > 0)
            this.value -= value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public void upMillage(int km) {
        if (km <= 0)
            System.out.println("Error km must be higher then 0");
        else this.mileage += km;

    }

    public String getColor() {
        System.out.println("Car color is " + color);
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public List<carParts> getCarPartsList() {
        return carPartsList;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }


    public List<String> getGetVehicleHistory() {
        return getVehicleHistory;
    }

    public double getRepairCost() {
        return repairCost;
    }

    public void upRepairCost(double repairCost) {
        if (repairCost >= 0)
            this.repairCost = repairCost;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return color + " " + segment + " " + brand + " "+ value + " " + mileage ;


    }

    public boolean ifBroken() {
        for(carParts p : carPartsList){
            if(p.ifBroken())
                return true;
        }
        return false;
    }
}
