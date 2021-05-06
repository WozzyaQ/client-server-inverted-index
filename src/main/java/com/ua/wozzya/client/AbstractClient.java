package com.ua.wozzya.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class AbstractClient {
    protected static final int DEFAULT_ATTEMPTS_TO_CONNET = 5;
    protected static final long DEFAULT_PAUSE_BETWEEN_CONNECTS = 200;

    protected final String ip;
    protected final int port;
    protected final BufferedReader clientIn;

    protected Socket socket;
    protected PrintWriter serverIn;
    protected BufferedReader serverOut;

    protected AbstractClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        clientIn = new BufferedReader(new InputStreamReader(System.in));
    }

    // establishing connection and starting messaging with server
    protected abstract void connect();

    //close all the connections
    protected void disconnect() {
        serverIn.close();
        serverIn.close();
        try {
            clientIn.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
