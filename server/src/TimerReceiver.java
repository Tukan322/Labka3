package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

public class TimerReceiver {
    static Scanner reader = new Scanner(System.in);
    public static void lessTimedReceiver(ArrayList<Organization> orgs){
        final Runnable stuffToDo = new Thread(() -> {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("Collection.csv");
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter printWriter = new PrintWriter(fileWriter);
            String isSave = reader.nextLine();
            if(isSave.equals("y")) for (Organization org : orgs) {
                Commands.save(org, printWriter);
                printWriter.close();
                System.out.println("Сохранение прошло успешно");
            }
            else if (isSave.equals("n")){
                System.out.println("Сохранение было отклонено");
            }


        });
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future future = executor.submit(stuffToDo);
        executor.shutdown(); // This does not cancel the already-scheduled task.

        try {
            future.get(10000, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException | ExecutionException ie) {
            //a
        } catch (TimeoutException te) {
            System.out.println("Сохранение отменено по истечению времени");
        }
    }
    public static void TimedReceiver(){
        final Runnable stuffToDo = new Thread(() -> {
            String isExit = reader.nextLine();
            if(isExit.equals("y")){
                System.out.println("Работа сервера завершается");
                System.exit(1);
            }
            else if (isExit.equals("n")){
                System.out.println("Продолжается работа сервера");
            }
        });
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future future = executor.submit(stuffToDo);
        executor.shutdown(); // This does not cancel the already-scheduled task.

        try {
            future.get(10000, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException | ExecutionException ie) {
            //a
        } catch (TimeoutException te) {
            System.out.println("Время ответа истекло. Работа сервера продолжается.");
        }
    }
}
