package com.company;

class Address {
    private String street; //Поле может быть null
    private Location town = new Location(); //Поле может быть null

    String getStreet(){
        return street;
    }
    void setStreet(String street){
        this.street = street;
    }
    Float getTownX(){
        return town.getX();
    }
    Long getTownY(){
        return town.getY();
    }
    Float getTownZ(){
        return town.getZ();
    }
    void setTownX(Float x){
        town.setX(x);
    }
    void setTownY(Long y){
        town.setY(y);
    }
    void setTownZ(Float z){
        town.setZ(z);
    }

}
