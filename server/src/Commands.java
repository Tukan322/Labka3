package com.company;

import java.io.*;
import java.util.*;

import static java.lang.System.in;

public class Commands {
    public static void help(){
        Server.send("help - вывод доступных команд");
        Server.send("info - вывод информации о коллекции");
        Server.send("show - вывод всех элементов коллекции");
        Server.send("add - добавить новый элемент в коллекцию");
        Server.send("update id - обновить элемент по id");
        Server.send("remove_by_id id - удалить элемент с заданным id");
        Server.send("clear - очистить коллекцию ");
        Server.send("save- сохранить коллекцию в файл");
        Server.send("execute_script file_name - считать скрипт из заданного файла");
        Server.send("exit - выход из программы");
        Server.send("remove_first - удаляет первый элемент в коллекции");
        Server.send("reorder - отсортировать коллекцию в обратном порядке");
        Server.send("sort - отсортировать коллекцию");
        Server.send("remove_all_by_type type - удалить все элементы с типом type");
        Server.send("filter_less_than_annual_turnover annualTurnover - вывести все элементы со значением годового оборота меньше заданного");
        Server.send("filter_greater_than_employees_count employeesCount - вывести все элементы со значением кол-ва сотрудников выше заданного");

    }
    public static void info(ArrayList orgs){
        Server.send("Тип");
        Server.send("ArrayList");
        Server.send("Дата инициализации");
        Server.send(String.valueOf(Organization.getCreationDate()));
        Server.send("Размер коллекции");
        Server.send(String.valueOf(orgs.size()));
    }
    public static void show(Organization org) {
        Server.send("id организации");
        Server.send(org.getId() + "\n" );
        Server.send("Имя организации");
        Server.send(org.getName());
        Server.send(" ");
        Server.send("Координаты организации");
        Server.send("x: " + org.getCoordinatesX());
        Server.send("y: " + org.getCoordinatesY());
        Server.send( " " );
        Server.send("Годовой оборот");
        if(org.getAnnualTurnover() == null)
            Server.send("unknown unit");
        else
            Server.send(org.getAnnualTurnover() + " unit");
        Server.send("");
        Server.send("Полное название");
        Server.send(org.getFullName());
        Server.send("");
        Server.send("Количество работяг");
        if(org.getEmployeesCount() == null)
            Server.send("unknown");
        else
            Server.send(String.valueOf(org.getEmployeesCount()));
        Server.send("");
        Server.send("Тип организации");
        Server.send(String.valueOf(org.getType()));
        Server.send("");
        Server.send("Местоположение организации");
        if(org.getStreet() == null)
            Server.send("Street: unknown");
        else
            Server.send("Street: " + org.getStreet());
        if (org.getTown().isEmpty())
            Server.send("Town: unknown");
        else {
            Server.send("Town: " + org.getTown());
            Server.send("x: " + org.getX());
            Server.send("y: " + org.getY());
            Server.send("z: " + org.getZ());
        }
        Server.send("__________________________________");
        Server.send("Продолжить показ? y/n - y = Да, n = Нет. При любом ином вводе показ продолжится");
    }
    public static void save(Organization org, PrintWriter printWriter){
        printWriter.println(org.getName());
        printWriter.println(org.getCoordinatesX());
        printWriter.println(org.getCoordinatesY());
        if (org.getAnnualTurnover() == null)
            printWriter.println("");
        else
            printWriter.println(org.getAnnualTurnover());
        printWriter.println(org.getFullName());
        if (org.getEmployeesCount() == null)
            printWriter.println("");
        else
            printWriter.println(org.getEmployeesCount());
        printWriter.println(org.getType());
        if (org.getStreet() == null)
            printWriter.println("");
        else
            printWriter.println(org.getStreet());
        printWriter.println(org.getTown());
        if(!org.getTown().isEmpty()) {
            printWriter.println(org.getX());
            printWriter.println(org.getY());
            printWriter.println(org.getZ());
        }
        else{
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
        }
        printWriter.println();
    }
    public static int countFile(String fileName) {
        int count = 0;
        File file = new File(fileName);
        Scanner sc;
        try {
            sc = new Scanner(file);

            while (true) {
                try {
                    sc.nextLine();
                    count+=1;
                } catch (NoSuchElementException ex) {
                    break;
                }
            }
        }
        catch (FileNotFoundException ex){
            //
        }
        return count;
    }
    public static String execute(String fileName){
        File file = new File(fileName);
        String script ="";
        try {
            Scanner sc = new Scanner(file);
            script = sc.nextLine();
        }
        catch (FileNotFoundException ex){
            Server.send("Не удается найти указанный файл");
        }
        return script;
    }
    public static String[] execute(String fileName, int N){
        File file = new File(fileName);
        String[] script = new String[N];
        try {
            Scanner sc = new Scanner(file);
        for(int i = 0; i < N; i++) {
            script[i] = sc.nextLine();
        }
        } catch (FileNotFoundException ex) {
            Server.send("Не удается найти указанный файл");
        }
        return script;
    }

    public static ArrayList<Organization> executeScript(ArrayList<Organization> orgs, int id, String fileName){
        String type;
        int vId;
        Scanner reader = new Scanner(in);
        try {
            int N = Commands.countFile(fileName);
            int i = 0;
            if (i == N) return orgs;
            String[] commands = Commands.execute(fileName, N);
            while (i < N) {
                commands[i] = commands[i].trim() + " ";
                int space = commands[i].indexOf(' ');
                String sCom = commands[i].substring(0, space);
                commands[i] = commands[i].substring(space).trim();
                Server.send(sCom + " " + commands[i]);
                i++;
                switch (sCom) {
                    case ("help"):
                        Commands.help();
                        break;
                    case ("info"):
                        Commands.info(orgs);
                        break;
                    case ("show"):
                        for (Organization org : orgs) {
                            Commands.show(org);
                        }
                        break;
                    case ("add"):try
                    {
                        String[] vInput = new String[12];
                        for (int j = 0; j < 12; j++) {
                            vInput[j] = commands[i];
                            Server.send(i + " " + vInput[j]);
                            i++;
                        }
                        Server.send("");
                        orgs.add(new Organization(id, orgs, vInput));
                        id += 1;
                    }
                    catch (IndexOutOfBoundsException ex){
                        Server.send("Ошибка ввода. Проверьте корректность данных. Попробуйте использовать команду add, и проверить правильность порядка заполнения файла.");
                    }


                        break;
                    case ("update"):
                        try {
                            vId = Integer.parseInt(commands[i-1]);
                            Server.send(String.valueOf(vId));
                            orgs.remove(vId);
                            String[] vInput = new String[12];
                            for(int j = 0; j < 12; j++){
                                vInput[j] = commands[i];
                                Server.send(commands[i]);
                                i++;
                            }
                            orgs.add(new Organization(vId, orgs, vInput));
                        } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                            Server.send("Неверный ввод id или элемент с таким id отсутствует. Попробуйте снова.");
                            ex.printStackTrace();
                        }
                        break;
                    case ("remove_by_id"):
                        try {
                            boolean k = true;
                            Organization wrong = new Organization();
                            vId = Integer.parseInt(commands[i]);
                            for(Organization org:orgs){
                                if (org.getId() == vId){
                                    wrong = org;
                                    k = false;
                                }
                            }
                            orgs.remove(wrong);
                            if (k) Server.send("Элемент с таким id отсутсвует");
                            else{
                                Server.send("Элемент был успешно удален");
                            }
                        } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                            Server.send("Неверный ввод id или элемент с таким id отсутствует. Попробуйте снова.");
                        }
                        break;
                    case ("clear"):
                        orgs.clear();
                        break;
                    case ("save"): {
                        FileWriter fileWriter = new FileWriter("Collection.txt");
                        PrintWriter printWriter = new PrintWriter(fileWriter);
                        for (Organization org : orgs)
                            Commands.save(org, printWriter);
                    }
                    break;
                    case ("execute_script"):
                        String oldFileName = fileName;
                        fileName = commands[i-1];
                        if (!oldFileName.equals(fileName))
                        orgs = Commands.executeScript(orgs, id, fileName);
                        for(Organization org:orgs)
                            id = org.getId();
                        id +=1;
                        break;
                    case ("exit"):
                        System.exit(1);
                        break;
                    case ("remove_first"):
                        orgs.remove(0);
                        break;
                    case ("reorder"):
                        Collections.reverse(orgs);
                        break;
                    case ("sort"):
                        orgs.sort(Comparator.comparing(Organization::getId));
                        break;
                    case ("remove_all_by_type"):
                        type = reader.next();
                        ArrayList<Organization> copy = new ArrayList<>();
                        for (Organization org : orgs) {
                            if (!org.getStringType().equals(type)) {
                                copy.add(org);
                            }
                            orgs = copy;
                        }
                        break;
                    case ("filter_less_than_annual_turnover"):
                        int anTurn = Integer.parseInt(commands[i]);
                        for (Organization org : orgs) {
                            if (org.getAnnualTurnover() > anTurn)
                                Commands.show(org);
                        }
                        break;
                    case ("filter_greater_than_employees_count"):
                        int emCount = Integer.parseInt(commands[i]);
                        for (Organization org : orgs) {
                            if (org.getEmployeesCount() > emCount)
                                Commands.show(org);
                        }
                        break;
                    default:
                        Server.send("Ошибка ввода.");
                }
            }
        }
        catch (IndexOutOfBoundsException | IOException ex){
            Server.send("Ошибка выполнения скрипта из указанного файла");
            ex.printStackTrace();
        }
        return orgs;
    }
}
