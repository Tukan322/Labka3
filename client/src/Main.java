package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException{
        Organization org = null;
        int id = -1;
        int ind;
        String checker;
        boolean isCreate = false;
        Scanner reader = new Scanner(System.in);
        int port = 0;
        String msg;

        while (!isCreate) {
            try {
                System.out.println("Введите порт на котором будет принимать клиент (это третий коммит)");
                port = Integer.parseInt(reader.nextLine());
                isCreate = Client.create(port);
                break;
            }
            catch (IllegalArgumentException numberFormatException){
                System.out.println("Неверный ввод. попробуйте снова");
            }
        }
        System.out.println("Клиент запущен");
        boolean isFirst = true;
        while (true) {
            Client.send(new Message("Client connected", port));
            TimerReceiver.timedReceiver();
            msg = Client.getMessage();

            if (msg != null) break;
            System.out.println("Ответ от сервера отсутствует");
        }
        //boolean isWait=false;
        while (true) {

            Client.showMessage();
            if (!Client.getIsGot() && !isFirst) {
                System.out.println("Ответ от сервера отсутсвует. Попробуйте ввести команду снова");
            }
            String sending = reader.nextLine();
            try{
                ind = sending.indexOf(' ');
                checker = sending.substring(0, ind);
                id = Integer.parseInt(sending.substring(ind + 1));
                System.out.println(id);
            }
            catch (IndexOutOfBoundsException ex){
                checker = sending;
            }
            catch (NumberFormatException ex){
                ex.printStackTrace();
                checker = sending;
                id = -1;
            }
            switch (checker) {

                case ("add"):org = new Organization();
                    break;
                case ("update"):{
                    org = new Organization();
                    while (true) {
                        if (id == -1) {
                            System.out.println("Неверный ввод id. Попробуйте снова");
                            id = Integer.parseInt(reader.nextLine());
                        } else {
                            org.setId(id);
                            break;
                        }
                    }
                }
            }
            System.out.println();
            if (org == null) Client.send(new Message(sending, port));
            else
            Client.send(new Message(sending, org, port));
            //if(sending.equals("save")){
            //    for(int i = 0; i < 10; i++){
            //        TimerReceiver.waitForASec();
            //        isWait = true;
            //        if (i==9) System.out.println("Введите команду");
            //   }
            // }
            //else isWait=false;
            if (sending.equals("exit")) System.exit(1);
            isFirst = false;
        }
    }
}
