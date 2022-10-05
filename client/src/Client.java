package com.company;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Client {
    public static boolean isGot;
    public static DatagramChannel datagramChannel;
    public static String msg;
    static {
        try {
            datagramChannel = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean create(int port) throws IOException {
        SocketAddress it = new InetSocketAddress(InetAddress.getLocalHost(), port);
        boolean isBind = false;
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(false);
        try {
            dc.bind(it);
            isBind = true;
        } catch (SocketException e){
            System.out.println("Ошибка при занятии порта.");
            isBind = false;
        }
        datagramChannel = dc;
        return isBind;
    }

    public static void send() {
        Scanner reader = new Scanner(System.in);
        String msg = "";
        try {
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.bind(null);

            SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), 3344);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.write(20);
            objectOutputStream.flush();
            objectOutputStream.close();

            msg = reader.nextLine();
            byte[] buff = msg.getBytes();
            byteArrayOutputStream.close();

            datagramChannel.send(ByteBuffer.wrap(buff), serverAddress);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (msg.equals("exit")) {
            System.exit(1);
        }
    }

    public static void send(Message msg) {
        try {
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.bind(null);

            SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), 3344);
            Message o = msg;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            objectOutputStream.close();

            byte[] buff = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            datagramChannel.configureBlocking(false);
            datagramChannel.send(ByteBuffer.wrap(buff), serverAddress);
            datagramChannel.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (msg.equals("exit")) System.exit(1);
    }

    public static byte[] receive() {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1000000);
            byte[] bytes;
            while(true) {
                InetSocketAddress socketAddress = (InetSocketAddress) datagramChannel.receive(byteBuffer);
                if (socketAddress!= null) {
                    byteBuffer.flip();
                    int limit = byteBuffer.limit();
                    bytes = new byte[limit];
                    byteBuffer.get(bytes,0,limit);
                    byteBuffer.clear();
                    return bytes;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new byte[0];
    }

    public static void showMessage(){
        setIsGot(false);
        while (true) {
            TimerReceiver.lessTimedReceiver();
            if (msg == null) break;
        }
    }

    public static void setMessage(String message){

        msg = message;
    }

    public static String getMessage(){
        return msg;
    }

    public static void setIsGot(boolean o){
        isGot = o;
    }

    public static boolean getIsGot(){
        return isGot;
    }
}
