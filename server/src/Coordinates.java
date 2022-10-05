package com.company;

class Coordinates {
    private long x; //Значение поля должно быть больше -319
    private int y;
    long getX(){
        return this.x;
    }
    void setX(long x){
        this.x = x;
    }
    int getY(){
        return this.y;
    }
    void setY(int y){
        this.y = y;
    }
}
