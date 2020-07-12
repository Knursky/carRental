package com.company.vehicles;

public class carParts {
    private String name;
    private boolean broken;


    public carParts(String name){
        this.name = name;
        if(Math.random()*3 > 1)
            broken = true;
        else broken = false;
    }

    public boolean ifBroken(){
        return broken;}

    public void fixBroken(){
        broken = !broken;
    }


        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
