package com.company;

import java.io.Serializable;

public class Message implements Serializable {
    String msg;
    String[] fields = new String[13];
    int port;
    private static final long serialVersionUID = 1L;

    public Message(String msg){
        this.msg = msg;
    }

    public Message(String msg, int port){
        this.port = port;
        this.msg = msg;
    }
    public Message(){}
    /*public Message(String msg, Organization org){
        this.msg = msg;
        this.org = org;
    }
    */

    public Message(String msg,Organization org, int port){
        this.msg = msg;
        fields[0] = String.valueOf(org.getName());
        fields[1] = String.valueOf(org.getCoordinatesX());
        fields[2] = String.valueOf(org.getCoordinatesY());
        fields[3] = String.valueOf(org.getAnnualTurnover());
        fields[4] = String.valueOf(org.getFullName());
        fields[5] = String.valueOf(org.getEmployeesCount());
        fields[6] = String.valueOf(org.getType());
        fields[7] = String.valueOf(org.getStreet());
        fields[8] = String.valueOf(org.getTown());
        fields[9] = String.valueOf(org.getX());
        fields[10] = String.valueOf(org.getY());
        fields[11] = String.valueOf(org.getZ());
        fields[12] = String.valueOf(org.getId()) ;
        this.port = port;
    }
}
