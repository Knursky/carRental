package com.company.Custumer;

import java.util.List;

public class Custumer {
    int id;
    String name;
    String surname;
    String[] vehicle;
    String[] brands;
    boolean buyDamaged;
    String[] segment;
    double money;

    public Custumer(int id, String name,String surname, String[] vehicle, String[] brands,
                    boolean buyDamaged, String[] segment, double money) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.vehicle = vehicle;
        this.brands = brands;
        this.buyDamaged = buyDamaged;
        this.segment = segment;
        this.money = money;
    }

    public Custumer() {

    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String[] getVehicle() {
        return vehicle;
    }

    public void setVehicle(String[] vehicle) {
        this.vehicle = vehicle;
    }

    public String[] getSegment() {
        return segment;
    }

    public List<String> getSegmentsList() {
        return List.of(segment);
    }

    public void setSegment(String[] segment) {
        this.segment = segment;
    }

    public String[] getBrands() {
        return brands;
    }

    public List<String> getBrandsList() {
        return List.of(brands);
    }

    public void setBrands(String[] brands) {
        this.brands = brands;
    }

    public boolean buyDamaged() { return buyDamaged; }

    public void setBuyDamaged(boolean buyDamaged) {
        this.buyDamaged = buyDamaged;
    }



    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void minusMoney(double price) {
        if (price >= 0)
            money -= price;
    }
}