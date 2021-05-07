package com.ua.wozzya.server;

import com.ua.wozzya.index.Index;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents server that handles
 * multiples connection
 */
public class MultiConnectionIndexServer {
    private final int port;

    private ServerSocket serverSocket;
    private final Index index;

    //should be supplied with ready index
    public MultiConnectionIndexServer(int port, Index index) {
        if (!index.isReady()) {
            throw new IllegalStateException("index should be built before supplying");
        }
        this.index = index;
        this.port = port;
    }

    public void start() {
        init();
        accept();
        close();
    }

    private void accept() {
        Socket clientSocket;
        int id = 0;
        while (true) {
            try {
                //wait on client
                clientSocket = serverSocket.accept();

                //on client acceptance - handle
                IndexClientHandler.create(index, clientSocket, ++id).start();
                System.out.println("[clinet has been accepted]");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
