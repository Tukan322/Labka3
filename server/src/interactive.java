package com.company;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class interactive {
    public static void mode() throws IOException, ClassNotFoundException {
        ArrayList<Organization> orgs = new ArrayList<>();
        Server.create();
        System.out.println("Сервер активен");
        Message obj = (Message) Deserializer.deserialize(Server.receive());
        Server.setPort(obj.port);
        Server.send("Сервер был запущен");
        String o = "";
        String checker;
        String checker2;
        int id = 0;
        int vId;
        String fileName;
        try {
            fileName = System.getenv("fileName");
            int N = Commands.countFile(fileName);
            String[] input = Commands.execute(fileName, N);
            String[] vInput = new String[13];
            for (int i = 0; i < N; i++) {
                if (!(i % 13 == 12)) {
                    vInput[i % 13] = input[i];
                } else {
                    orgs.add(new Organization(id, orgs, vInput));
                    id += 1;
                }
            }
        } catch (IndexOutOfBoundsException | NullPointerException ex) {
            System.out.println("ошибка автоматического заполнения из указанного файла");
        }
        String type;
        //boolean isFirst = true;
        while (true) {
            //if (o.equals("Client connected")) isFirst = true;
            System.out.println();
            System.out.println("Ожидаю команду.");
            //System.out.println("Ожидаю команду. Введите save для сохранения коллекции.");
            //System.out.println("Коллекция сохраняется");
            System.out.println(Server.getPort());
            Server.send("Введите комманду. help для справки");
            obj = (Message) Deserializer.deserialize(Server.receive());
            Server.setPort(obj.port);
            o = obj.msg;

            System.out.println(o);
            try {
                int split = o.indexOf(' ');
                checker = o.substring(0, split);
                checker2 = o.substring(split + 1);
            } catch (IndexOutOfBoundsException ex) {
                checker2 = "";
                checker = o;
            }
            switch (checker) {
                case("spam"):
                    for(int i = 0; i <200; i++)
                        Server.send("spam");
                    break;
                case ("help"):
                    Commands.help();
                    break;
                case ("info"):
                    Commands.info(orgs);
                    break;
                case ("show"):
                    Server.send("Выполняется команда show");
                    for (Organization org : orgs) {
                        Commands.show(org);
                        String isContinue = ((Message) Deserializer.deserialize(Server.receive())).msg;
                        if (isContinue.equals("n")) break;
                    }
                    break;
                case ("add"):{
                    boolean isEnd = false;
                    Organization org = new Organization();
                    org.setId(id);
                    org.setName(obj.fields[0]);
                    org.setCoordinatesX(Integer.parseInt(obj.fields[1]));
                    org.setCoordinatesY(Integer.parseInt(obj.fields[2]));
                    org.setCreationDate(LocalDate.now());
                    org.setAnnualTurnover(Double.parseDouble(obj.fields[3]));
                    org.setFullName(obj.fields[4]);
                    for (Organization oneOrg:orgs){
                        if (org.getFullName().equals(oneOrg.getFullName())){
                            Server.send("Введенное полное имя уже занято. Повторите попытку");
                            isEnd = true;
                        }
                    }
                    if (isEnd) break;
                    org.setEmployeesCount(Long.parseLong(obj.fields[5]));
                    switch (obj.fields[6]) {
                        case ("TRUST"):
                            org.setType(OrganizationType.TRUST);
                            break;
                        case("GOVERNMENT"):
                            org.setType(OrganizationType.GOVERNMENT);
                            break;
                        case("PRIVATE_LIMITED_COMPANY")  :
                            org.setType(OrganizationType.PRIVATE_LIMITED_COMPANY);
                            break;
                        case("OPEN_JOINT_STOCK_COMPANY"):
                            org.setType(OrganizationType.OPEN_JOINT_STOCK_COMPANY);
                            break;
                        default:Server.send("");
                    }
                    org.setStreet(obj.fields[7]);
                    org.setTown(obj.fields[8]);
                    org.setX(Float.parseFloat(obj.fields[9]));
                    org.setY(Long.parseLong(obj.fields[10]));
                    org.setZ(Float.parseFloat(obj.fields[11]));
                    orgs.add(org);
                    id += 1;
                    break;
                }
                case ("update"): {
                    try {
                        boolean isEnd = false;
                        vId = Integer.parseInt(obj.fields[12]);
                        orgs.remove(vId);
                        Organization org = new Organization();
                        org.setId(vId);
                        org.setName(obj.fields[0]);
                        org.setCoordinatesX(Integer.parseInt(obj.fields[1]));
                        org.setCoordinatesY(Integer.parseInt(obj.fields[2]));
                        org.setCreationDate(LocalDate.now());
                        org.setAnnualTurnover(Double.parseDouble(obj.fields[3]));
                        org.setFullName(obj.fields[4]);
                        for (Organization oneOrg:orgs){
                            if (org.getFullName().equals(oneOrg.getFullName())){
                                Server.send("Введенное полное имя уже занято. Повторите попытку");
                                isEnd = true;
                                Server.send("Элемент, который вы пытались обновить был удален");
                            }
                        }
                        if (isEnd) break;
                        org.setEmployeesCount(Long.parseLong(obj.fields[5]));
                        switch (obj.fields[6]) {
                            case ("TRUST"):
                                org.setType(OrganizationType.TRUST);
                                break;
                            case("GOVERNMENT"):
                                org.setType(OrganizationType.GOVERNMENT);
                                break;
                            case("PRIVATE_LIMITED_COMPANY")  :
                                org.setType(OrganizationType.PRIVATE_LIMITED_COMPANY);
                                break;
                            case("OPEN_JOINT_STOCK_COMPANY"):
                                org.setType(OrganizationType.OPEN_JOINT_STOCK_COMPANY);
                                break;
                            default:Server.send("");
                        }
                        org.setStreet(obj.fields[7]);
                        org.setTown(obj.fields[8]);
                        org.setX(Float.parseFloat(obj.fields[9]));
                        org.setY(Long.parseLong(obj.fields[10]));
                        org.setZ(Float.parseFloat(obj.fields[11]));
                        orgs.add(org);
                        id += 1;
                        break;
                    } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                        Server.send("Неверный ввод id или элемент с таким id отсутствует. Попробуйте снова.");
                    }
                    break;
                }
                case ("remove_by_id"):
                    try {
                        boolean k = true;
                        vId = Integer.parseInt(checker2);
                        Organization wrong = new Organization();
                        for (Organization org : orgs)
                            if (vId == org.getId()) {
                                wrong = org;
                                k = false;
                            }
                        orgs.remove(wrong);
                        if (k) Server.send("Элемент с таким id отсутствует");
                    } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                        Server.send("Неверный ввод id или элемент с таким id отсутствует. Попробуйте снова.");
                    }
                    break;
                case ("clear"):
                    orgs.clear();
                    break;
                case ("save"): {
                    //System.out.println("Хотите ли вы сохранить коллекцию. y/n. y = да, n = нет");
                    //TimerReceiver.lessTimedReceiver(orgs);
                }
                break;
                case ("execute_script"):
                    fileName = checker2;
                    orgs = Commands.executeScript(orgs, id, fileName);
                    orgs.sort(Comparator.comparing(Organization::getId));
                    for (Organization org : orgs)
                        id = org.getId();
                    id += 1;
                    break;
                case ("exit"):
                    System.out.println("Клиент отключен");
                    FileWriter fileWriter = new FileWriter("Collection.csv");
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    for (Organization org : orgs)
                        Commands.save(org, printWriter);
                    printWriter.close();
                    System.out.println("Коллекция была сохранена");
                    //System.out.println("Желаете ли вы завершить работу сервера? y/n. y = да, n = нет.");
                    //TimerReceiver.TimedReceiver();
                    break;
                case ("remove_first"):
                    try {
                        orgs.remove(0);
                    } catch (IndexOutOfBoundsException ex) {
                        Server.send("Произошла ошибка. Невозможно удалить первый элемент в пустой коллекции");
                    }
                    break;
                case ("reorder"):
                    Collections.reverse(orgs);
                    break;
                case ("sort"):
                    orgs.sort(Comparator.comparing(Organization::getId));
                    break;
                case ("remove_all_by_type"):
                    type = checker2;
                    ArrayList<Organization> copy = new ArrayList<>();
                    for (Organization org : orgs) {
                        if (!org.getStringType().equals(type)) {
                            copy.add(org);
                        }
                        orgs = copy;
                    }
                    break;
                case ("filter_less_than_annual_turnover"):
                    int anTurn = Integer.parseInt(checker2);

                    for (Organization org : orgs) {
                        try {
                            if (org.getAnnualTurnover() < anTurn)
                                    Commands.show(org);
                                    String isContinue = ((Message) Deserializer.deserialize(Server.receive())).msg;
                                    if (isContinue.equals("n")) break;
                        } catch (NullPointerException ex) {
                            //
                        }
                    }

                    break;
                case ("filter_greater_than_employees_count"):
                    int emCount = Integer.parseInt(checker2);
                    for (Organization org : orgs) {
                        if (org.getEmployeesCount() > emCount)
                                Commands.show(org);
                                String isContinue = ((Message) Deserializer.deserialize(Server.receive())).msg;
                                if (isContinue.equals("n")) break;
                    }
                    break;
                case ("ShowId"):
                    Server.send(String.valueOf(id));
                case ("StrTypes"):
                    for (Organization org : orgs) Server.send(org.getStringType());
                default: {
                    if(!o.equals("Client connected")) {
                        System.out.println("Ошибка ввода");
                        Server.send("Ошибка ввода.");
                    }
                }
            }
            //isFirst = false;
        }

    }
}