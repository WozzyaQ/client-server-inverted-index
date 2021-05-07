package com.ua.wozzya.server;

import com.ua.wozzya.index.Index;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IndexServerManager {
    private final int port;

    private ServerSocket serverSocket;
    private final Index index;

    public IndexServerManager(int port, Index index) {
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
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                IndexClientHandler.create(index, clientSocket).start();
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
