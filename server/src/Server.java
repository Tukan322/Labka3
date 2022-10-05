package com.company;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {

    static int port;
    public static DatagramChannel datagramChannel;

    public static void create() throws IOException {
        SocketAddress it = new InetSocketAddress(InetAddress.getLocalHost(), 3344);

        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(false);
        try {
            dc.bind(it);
        } catch (BindException e){
            System.out.println("Порт занят,отключаюсь.");
            System.exit(0);
        }
        datagramChannel = dc;
    }

    public static byte[] receive() {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(10000);
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
        } catch (IOException ignored) { }
        return new byte[0];
    }

    public static void send(String msg) {
        try {
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.bind(null);

            SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
            Message o = new Message(msg);
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

    public static void setPort(int p){
        port = p;
    }

    public static int getPort(){
        return port;
    }
}