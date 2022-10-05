package com.company;

import java.io.Serializable;

public class Location implements Serializable {
    private Float x; //Поле не может быть null
    private Long y; //Поле не может быть null
    private Float z;
    Float getX(){
        return this.x;
    }
    Long getY(){
        return this.y;
    }
    Float getZ(){
        return this.z;
    }
    void setX(Float x){
        this.x = x;
    }
    void setY(Long y){
        this.y = y;
    }
    void setZ(Float z){
        this.z = z;
    }
}
