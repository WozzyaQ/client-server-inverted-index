package com.ua.wozzya.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Represents abstract client
 * that enters information
 * via STDIN
 */
public abstract class AbstractConsoleClient {
    protected final String ip;
    protected final int port;
    protected final BufferedReader clientIn;

    protected Socket socket;
    protected PrintWriter serverIn;
    protected BufferedReader serverOut;

    protected AbstractConsoleClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        clientIn = new BufferedReader(new InputStreamReader(System.in));
    }

    // establishing connection and starting messaging with server
    protected abstract void connect();

    //close I/Os
    protected void disconnect() {
        try {
            serverOut.close();
            serverIn.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
