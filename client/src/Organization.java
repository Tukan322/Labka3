package com.company;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static com.company.OrganizationType.*;


class Organization implements Serializable {

    static final long serialVersionUID = 101;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates = new Coordinates(); //Поле не может быть null
    static private LocalDate creationDate = LocalDate.now();
    private Double annualTurnover; //Поле может быть null, Значение поля должно быть больше 0
    private String fullName; //Строка не может быть пустой, Значение этого поля должно быть уникальным, Поле не может быть null
    private Long employeesCount; //Поле может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address postalAddress = new Address(); //Поле не может быть null
    private String Stown = "";
    transient Scanner reader = new Scanner(System.in);
    Organization(int id, ArrayList<Organization> orgs, String[] input) {
        this.id = id;
        //parsers

        try{

            this.name = input[0];
            if (input[0].isEmpty())
                Integer.parseInt("S");
            this.coordinates.setX(Long.parseLong(input[1]));
            if (input[1].isEmpty())
                Integer.parseInt("S");
            this.coordinates.setY(Integer.parseInt(input[2]));
            if (input[2].isEmpty())
                Integer.parseInt("S");
            if (input[3].isEmpty())
                this.annualTurnover = null;
            else this.annualTurnover = Double.parseDouble(input[3]);
            this.fullName = input[4];
            for (Organization org:orgs){
                if (org.fullName.equals(this.fullName)) {
                    Integer.parseInt("S");
                }
            }
            if (input[5].isEmpty())
                this.employeesCount = null;
            else this.employeesCount = Long.parseLong(input[5]);
            switch (input[6]) {
                case "TRUST":
                    this.type = TRUST;
                    break;
                case "GOVERNMENT" :
                    this.type = GOVERNMENT;
                    break;
                case "PRIVATE_LIMITED_COMPANY":
                    this.type = PRIVATE_LIMITED_COMPANY;
                    break;
                case "OPEN_JOINT_STOCK_COMPANY":
                    this.type = OPEN_JOINT_STOCK_COMPANY;
                default:
                    Double.parseDouble("sus");
            }
            if (input[7].isEmpty())
                this.postalAddress.setStreet(null);
            else this.postalAddress.setStreet(input[7]);
            if (input[8].isEmpty()) {
                Stown = input[8];
                postalAddress.setTownX(null);
                postalAddress.setTownY(null);
                postalAddress.setTownZ(null);
            }
            else {
                Stown = input[8];
                postalAddress.setTownX(Float.parseFloat(input[9]));
                postalAddress.setTownY(Long.parseLong(input[10]));
                postalAddress.setTownZ(Float.parseFloat(input[11]));
            }
        }
        catch (NumberFormatException ex){
            System.out.println("Ошибка заполнения коллекции. Проверьте корректность введенных в файле данных.");
        }
    }

    Organization(){
        this.id = -1;
        System.out.println("Введите имя организации");
        while(true) {
            boolean k = true;
            this.name = reader.nextLine();
            if (name.isEmpty()){
                System.out.println("Имя не должно быть пустым. Попробуйте снова.");
                k = false;
            }
            if (k) break;
        }
        System.out.println("Введите координату x.");
        while(true){
            try{
                this.coordinates.setX(Long.parseLong(new String(reader.nextLine())));
            }
            catch (NumberFormatException ex){
                this.coordinates.setX(-320);
            }
            if (this.coordinates.getX() > -319)
                break;
            System.out.println("повторите ввод координаты х");
        }
        System.out.println("Введите координату y");
        while(true) {
            try {
                this.coordinates.setY(Integer.parseInt(new String(reader.nextLine())));
            }
            catch (NumberFormatException ex){
                this.coordinates.setY(-320);
            }
            if (this.coordinates.getY() > -319)
                break;
            System.out.println("повторите ввод координаты y");
        }
        System.out.println("Введите годовой оборот организации");
        while(true) {
            String line = new String(reader.nextLine());
            if (line.isEmpty()) {
                this.annualTurnover = null;
                break;
            }
            else{
                try {
                    this.annualTurnover = Double.parseDouble(line);
                } catch (NumberFormatException ex) {
                    System.out.println("Ваш ввод" + " " + line);
                    System.out.println("Годовой оборот должен быть числом большим 0. Повторите ввод годового оборота");
                    annualTurnover = -1.0;
                }
                if (annualTurnover > 0)
                    break;
            }
        }
        System.out.println("Введите полное название организации");
        while(true){
            boolean k = true;
            this.fullName = new String(reader.nextLine());
            if (this.fullName.isEmpty())
                k=false;
            if (k)
                break;
            System.out.println("Полное имя должно быть уникальным и не пустым. Повторите ввод полного имени");
        }
        System.out.println("Введите кол-во работников в организации");
        while(true) {
            String line = new String(reader.nextLine());
            if (line.isEmpty()) {
                this.employeesCount = null;
                break;
            }
            else{
                try {
                    this.employeesCount = Long.parseLong(line);
                } catch (NumberFormatException ex) {
                    System.out.println("Кол-во рабочих должно быть числом большим 0. Повторите ввод кол-ва рабочих");
                    employeesCount = (long)-1;
                }
                if (employeesCount > 0)
                    break;
            }
        }
        System.out.println("Введите тип организации. Один из 4:\n    GOVERNMENT,\n" +
                "    TRUST,\n" +
                "    PRIVATE_LIMITED_COMPANY,\n" +
                "    OPEN_JOINT_STOCK_COMPANY.");
        while (true){
            int k;
            switch (new String(reader.nextLine())) {
                case ("TRUST"):
                    this.type = TRUST;
                    k = 0;
                    break;
                case ("GOVERNMENT"):
                    this.type = GOVERNMENT;
                    k = 0;
                    break;
                case ("PRIVATE_LIMITED_COMPANY"):
                    this.type = PRIVATE_LIMITED_COMPANY;
                    k = 0;
                    break;
                case ("OPEN_JOINT_STOCK_COMPANY"):
                    this.type = OPEN_JOINT_STOCK_COMPANY;
                    k = 0;
                    break;
                default:
                    k = 1;
                    System.out.println("Неудачный ввод типа организации. попробуйте снова");
            }
            if (k == 0) break;
        }
        System.out.println("Введите улицу, на которой находится организация");
            this.postalAddress.setStreet(new String(reader.nextLine()));
            if (getStreet().isEmpty())
            this.postalAddress.setStreet(null);
        System.out.println("Введите название города в котором находится организация");
            Stown = new String(reader.nextLine());
        if (!Stown.isEmpty()) {
            System.out.println("Введите координаты города:");
            System.out.println("Координата х");
        }
        while(true) {
            boolean k = true;
            if (Stown.isEmpty()){
                postalAddress.setTownX(null);
                break;
            }
            try {
                postalAddress.setTownX(Float.parseFloat(new String(reader.nextLine())));
            }
            catch (NumberFormatException ex) {
                System.out.println("Неверный ввод координаты x города. Повторите ввод.");
                k = false;
            }
            if (k) break;
        }
        if (!Stown.isEmpty())
        System.out.println("Координата y");
        while(true){
            boolean k = true;
            if (Stown.isEmpty()){
                postalAddress.setTownY(null);
                break;
            }
            try{
                postalAddress.setTownY(Long.parseLong(reader.nextLine()));
            }
            catch (NumberFormatException ex){
                System.out.println("Неверный ввод координаты y города. Число должно быть целым. Повторите ввод.");
                k = false;
            }
            if(k) break;
        }
        if (!Stown.isEmpty())
        System.out.println("Координата z");
        while(true){
            boolean k = true;
            if (Stown.isEmpty()){
                postalAddress.setTownZ(null);
                break;
            }
            System.out.println();
            try{
                postalAddress.setTownZ(Float.parseFloat(new String(reader.nextLine())));
            }
            catch (NumberFormatException ex){
                System.out.println("Неверный ввод координаты z города. Повторите ввод.");
                k = false;
            }
            if(k) break;
        }
        System.out.println("end");
    }
    //setters
    public void setId(int id){
        this.id = id;
    }

    //GETTERS
    static LocalDate getCreationDate(){ return creationDate;}
    int getId(){
        return this.id;
    }
    String getName(){
        return this.name;
    }
    Long getCoordinatesX(){
        return coordinates.getX();
    }
    int getCoordinatesY(){
        return coordinates.getY();
    }
    Double getAnnualTurnover(){
        return this.annualTurnover;
    }
    String getFullName(){
        return this.fullName;
    }
    Long getEmployeesCount(){
        return employeesCount;
    }
    OrganizationType getType() {
        return this.type;
    }
    String getStringType(){return this.type.toString();}
    String getStreet(){
        return postalAddress.getStreet();
    }
    String getTown(){ return Stown;}
    Float getX(){
        return postalAddress.getTownX();
    }
    Long getY(){
        return postalAddress.getTonwY();
    }
    Float getZ(){
        return postalAddress.getTownZ();
    }

    //end of getters
}