package com.ua.wozzya.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleQueryClient {
    private String TERMINATOR;
    private final String IP;
    private final int PORT;

    private Socket socket = null;
    private PrintWriter writerToServer = null;
    private BufferedReader fromServerReader = null;
    private BufferedReader clientReader = null;

    public SimpleQueryClient(String ip, int port) {
        IP = ip;
        PORT = port;
        // get stdin object
        clientReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void connect() {
        establishConnection();
        speak();
        System.out.println("speaking");
    }

    private void speak() {

        String msgToServer = "";

        receiveTerminationLine();
        System.out.println("recieved terminator-->" + TERMINATOR);

        do {
            try {
                System.out.println("msg from server-->" + msgToServer);
                //read stdin
                msgToServer = clientReader.readLine();

                //pass msg to server
                System.out.println("sending-->" + msgToServer);
                writerToServer.println(msgToServer);
                writerToServer.flush();

                //read from server
                String fromServerMsg = "";
                System.out.println("waiting for server response");


                while (!(fromServerMsg = fromServerReader.readLine()).equals(TERMINATOR)) {
                    System.out.println(fromServerMsg);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!msgToServer.equals("QUIT"));

        System.out.println("[Disconnecting]");
        disconnect();
    }

    private void receiveTerminationLine() {
        try {
            TERMINATOR = fromServerReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //close all ins/outs
    private void disconnect() {
        writerToServer.close();
        writerToServer.close();
        try {
            clientReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void establishConnection() {
        try {
            // connect
            System.out.println("connected");
            socket = new Socket(IP, PORT);
            // aquire in/out object to speak with server
            fromServerReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writerToServer = new PrintWriter(socket.getOutputStream());

        } catch (UnknownHostException e) {
            System.exit(-2);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
